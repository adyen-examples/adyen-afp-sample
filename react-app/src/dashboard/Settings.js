import * as React from 'react';

import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Toolbar from '@mui/material/Toolbar';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

import AdyenKyc from '@adyen/kyc-components';
import '@adyen/kyc-components/styles.css';


export default function Settings() {

    async function sessionRequest() {
        try {
            const response = await fetch('/api/dashboard/getTransferInstruments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.status} ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error("sessionRequest error: ", error);
            throw error;
        }
    }

    async function initializeCore() {

        const adyenKycHandler = new AdyenKyc({
            locale: 'en-US',
            country: 'US',
            environment: 'test', // test or live 'https://test.adyen.com',
            sdkToken: await sessionRequest, //token,
            sessionRequest //getSdkToken
          });

        const legalEntityId = ""

        const manageTransferInstrumentComponent = adyenKycHandler
            .create('manageTransferInstrumentComponent', {
             legalEntityId: "legalEntityId",
             onAdd: (legalEntityId) => {console.log("onAdd")},
             onEdit: (transferInstrumentId, legalEntityId) => { console.log("onEdit")},
             onRemoveSuccess: (transferInstrumentId, legalEntityId) => {console.log("onRemoveSuccess")}, // Optional
             //onInitiateRemove: (console.log("onInitiateRemove")) => {} // Optional. The minimum library version is 3.30
            })
            .mount('#manage-transfer-instrument-container'); // Mount to the container you created

    }

    initializeCore();

//    function getSdkToken() {
//      return fetch('https://api.yourcompany.com/adyen-onboarding-sdk-token',
//            { "policy": { "roles": ["manageTransferInstrumentComponent"] } });
//    }

    return (
        <Box sx={{ display: 'flex' }}>

          <DashboardHeader/>

          <DashboardDrawer/>

            <Box
                  display="flex"
                  flexDirection="column"
                  alignItems="center"
                  width="100%"
                  sx={{ p: 3 }}
            >

                <Toolbar />

                <Divider sx={{ padding: 1 }}>
                    <Chip label="Settings" variant="elevated" sx={{ minWidth: 100, fontSize: "20px", backgroundColor: "#0abf53", color: "white" }}/>
                </Divider>

                <Paper sx={{ width: '100%' }}>
                    <div id="manage-transfer-instrument-container"></div>
                </Paper>

                <br/><br/>

            </Box>
        </Box>
  );
}