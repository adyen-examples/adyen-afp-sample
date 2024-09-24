package com.adyen.service;

import com.adyen.Client;
import com.adyen.config.ApplicationProperty;
import com.adyen.enums.Environment;
import com.adyen.model.AccountHolderStatus;
import com.adyen.model.StoreConfiguration;
import com.adyen.model.TransactionItem;
import com.adyen.model.balanceplatform.*;
import com.adyen.model.legalentitymanagement.BusinessLine;
import com.adyen.model.legalentitymanagement.BusinessLineInfo;
import com.adyen.model.legalentitymanagement.WebData;
import com.adyen.model.management.*;
import com.adyen.model.transfers.TransactionSearchResponse;
import com.adyen.service.balanceplatform.AccountHoldersApi;
import com.adyen.service.balanceplatform.BalanceAccountsApi;
import com.adyen.service.legalentitymanagement.BusinessLinesApi;
import com.adyen.service.management.AccountStoreLevelApi;
import com.adyen.service.transfers.TransactionsApi;
import com.adyen.util.TransactionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    /**
     * Create Store to route the payments through.
     * Create and assign a default SplitConfiguration
     * @param storeConfiguration
     * @return
     */
    public Store createStore(StoreConfiguration storeConfiguration) {
        Store store = null;

        try {
            StoreCreationWithMerchantCodeRequest storeCreationWithMerchantCodeRequest = new StoreCreationWithMerchantCodeRequest()
                    .merchantId(getApplicationProperty().getMerchantAccount())
                    .address(new StoreLocation()
                            .country(storeConfiguration.getCountryCode()))
                    .description("Web store for BL " + storeConfiguration.getBusinessLineId())
                    .businessLineIds(List.of(storeConfiguration.getBusinessLineId()))
                    .phoneNumber("+1 2343445345")
                    .reference("storeReference-0001")
                    .shopperStatement(storeConfiguration.getStoreName())
                    .splitConfiguration(new StoreSplitConfiguration()
                            .splitConfigurationId(getDefaultSplitConfiguration().getSplitConfigurationId())
                            .balanceAccountId(storeConfiguration.getBalanceAccountId()));

            store = getAccountStoreLevelApi().createStore(storeCreationWithMerchantCodeRequest);

        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException("Cannot create Store: " + e.getMessage());
        }

        return store;
    }

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

    // AccountStoreLevelApi handler
    private AccountStoreLevelApi getAccountStoreLevelApi() {
        return new AccountStoreLevelApi(getApiClient());
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

}
