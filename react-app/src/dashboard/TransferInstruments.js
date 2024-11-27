import * as React from 'react';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Toolbar from '@mui/material/Toolbar';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

import AdyenKyc from '@adyen/kyc-components';
import '@adyen/kyc-components/styles.css';

export default function TransferInstruments() {

    const navigate = useNavigate()

    const [session, setSession] = React.useState(null)
    const manageContainerRef = React.useRef(null);

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
  
    async function getUser() {
        try {
            const response = await fetch('/api/dashboard/getUser', {
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
            console.error("getUser error: ", error);
            throw error;
        }
    }
  
    React.useEffect(() => {
        async function getSession() {
          const session = await sessionRequest();
          setSession(session)
        }
        getSession()
    }, [])
  
    React.useEffect(() => {
        function getSdkToken() {
          const sessionResponse = sessionRequest();

          return sessionResponse.token;
        }
  
        async function loadComponent() {

            const user = await getUser();

            const adyenKycHandler = new AdyenKyc({
                locale: 'en-US',
                country: 'US',
                environment: 'https://test.adyen.com',
                sdkToken: session.token,
                getSdkToken
            });

            adyenKycHandler
                .create('manageTransferInstrumentComponent', {
                    legalEntityId: user.legalEntityId,
                        onAdd: (legalEntityId) => {
                            console.log("onAdd");
                            navigate("/addTransferInstrument");
                        },
                        onEdit: (transferInstrumentId, legalEntityId) => {
                            console.log("onEdit");
                        },
                        onRemoveSuccess: (transferInstrumentId, legalEntityId) => {
                            console.log("onRemoveSuccess");
                        },
                })
            .mount(manageContainerRef.current);
        }
  
    if(session) loadComponent()
  
    },[session, navigate])
  
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
                      <Chip label="Transfer Instruments" variant="elevated" sx={{ minWidth: 100, fontSize: "20px", backgroundColor: "#0abf53", color: "white" }}/>
                  </Divider>
  
                  <Paper sx={{ width: '100%', display: session ? 'fixed': 'none'}}>
                      <div ref={manageContainerRef}></div>
                  </Paper>
                  <br/><br/>
  
              </Box>
          </Box>
    );
  }
