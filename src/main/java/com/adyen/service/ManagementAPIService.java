package com.adyen.service;

import com.adyen.Client;
import com.adyen.config.ApplicationProperty;
import com.adyen.enums.Environment;
import com.adyen.model.StoreConfiguration;
import com.adyen.model.management.*;
import com.adyen.service.management.AccountStoreLevelApi;
import com.adyen.service.management.SplitConfigurationMerchantLevelApi;
import com.adyen.util.AddressHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Wraps the Adyen Management API: manage stores and other configuration settings
 * It requires the Checkout/Management API key
 *
 * https://docs.adyen.com/api-explorer/Management
 */
@Service
public class ManagementAPIService {

    private final Logger log = LoggerFactory.getLogger(ManagementAPIService.class);

    private Client apiClient = null;

    @Autowired
    private ApplicationProperty applicationProperty;

    @Autowired
    private AddressHandler addressHandler;

    /**
     * Create Store to route the payments through.
     * Create and assign a default SplitConfiguration to the store
     * @param storeConfiguration
     * @return
     */
    public Store createStore(StoreConfiguration storeConfiguration) {
        Store store = null;

        try {

            SplitConfiguration splitConfiguration = createSplitConfiguration(
                    getApplicationProperty().getMerchantAccount(), getDefaultSplitConfiguration());

            StoreCreationWithMerchantCodeRequest storeCreationWithMerchantCodeRequest = new StoreCreationWithMerchantCodeRequest()
                    .merchantId(getApplicationProperty().getMerchantAccount())
                    .address(getAddressHandler().getStoreLocation(storeConfiguration.getAddress()))
                    .description("Web store for BL " + storeConfiguration.getBusinessLineId())
                    .businessLineIds(List.of(storeConfiguration.getBusinessLineId()))
                    .phoneNumber("+1 2343445345")
                    .reference("storeReference-0001")
                    .shopperStatement(storeConfiguration.getStoreName())
                    .splitConfiguration(new StoreSplitConfiguration()
                            .splitConfigurationId(splitConfiguration.getSplitConfigurationId())
                            .balanceAccountId(storeConfiguration.getBalanceAccountId()));

            store = getAccountStoreLevelApi().createStore(storeCreationWithMerchantCodeRequest);
            log.info("Store created id:{}, description:'{}'", store.getId(), store.getDescription());


        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create Store: " + e.getMessage());
        }

        return store;
    }

    /**
     * Define the default SplitConfiguration to apply to all sub-merchants
     * @return
     */
    SplitConfiguration getDefaultSplitConfiguration() {
        return new SplitConfiguration()
                .description("Default Split Configuration for sub-merchants")
                .rules(List.of(
                        new SplitConfigurationRule()
                                .currency("ANY")
                                .paymentMethod("ANY")
                                .fundingSource(SplitConfigurationRule.FundingSourceEnum.ANY)
                                .shopperInteraction(SplitConfigurationRule.ShopperInteractionEnum.ECOMMERCE)
                                .splitLogic(new SplitConfigurationLogic()
                                        .adyenFees(SplitConfigurationLogic.AdyenFeesEnum.DEDUCTFROMONEBALANCEACCOUNT)
                                        .acquiringFees(SplitConfigurationLogic.AcquiringFeesEnum.DEDUCTFROMONEBALANCEACCOUNT)
                                        .commission(new Commission()
                                                .fixedAmount(10L)
                                                .variablePercentage(100L))
                                )
                ));
    }

    /**
     * Create a SplitConfiguration
     * @param merchantAccount
     * @param splitConfigurationInfo
     * @return
     */
    public SplitConfiguration createSplitConfiguration(String merchantAccount, SplitConfiguration splitConfigurationInfo) {

        SplitConfiguration splitConfiguration = null;

        try {
            splitConfiguration = splitConfigurationMerchantLevelApi().createSplitConfiguration(
                    merchantAccount, splitConfigurationInfo);
            log.info("SplitConfiguration created id:{}, description:'{}'",
                    splitConfiguration.getSplitConfigurationId(), splitConfiguration.getDescription());


        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create SplitConfiguration: " + e.getMessage());
        }

        return splitConfiguration;
    }

    // AccountStoreLevelApi handler
    private AccountStoreLevelApi getAccountStoreLevelApi() {
        return new AccountStoreLevelApi(getApiClient());
    }

    private SplitConfigurationMerchantLevelApi splitConfigurationMerchantLevelApi() {
        return new SplitConfigurationMerchantLevelApi(getApiClient());
    }


    // create client to access the Checkout and Management API
    private Client getApiClient() {
        if (apiClient == null) {
            // create once
            apiClient = new Client(
                    applicationProperty.getApiKey(),
                    Environment.TEST); // change to LIVE on prod
        }

        return apiClient;
    }

    public ApplicationProperty getApplicationProperty() {
        return applicationProperty;
    }

    public void setApplicationProperty(ApplicationProperty applicationProperty) {
        this.applicationProperty = applicationProperty;
    }

    public AddressHandler getAddressHandler() {
        return addressHandler;
    }

    public void setAddressHandler(AddressHandler addressHandler) {
        this.addressHandler = addressHandler;
    }
}
