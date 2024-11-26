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
    const [session, setSession] = React.useState(null)
    const containerRef = React.useRef(null);
  
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
          console.log("refresh token", sessionResponse.token);
  
          return sessionResponse.token;
      }
  
        async function loadComponent() {
            const user = await getUser();
            console.log("session exists", session)
      
            const adyenKycHandler = new AdyenKyc({
                locale: 'en-US',
                country: 'US',
                environment: 'https://test.adyen.com',
                sdkToken: session.token, //token,
                getSdkToken
              });
      
              
                const manageTransferInstrumentComponent = adyenKycHandler
                  .create('manageTransferInstrumentComponent', {
                    legalEntityId: user.legalEntityId,
                    onAdd: (legalEntityId) => {
                      console.log("onAdd");
                    },
                    onEdit: (transferInstrumentId, legalEntityId) => {
                      console.log("onEdit");
                    },
                    onRemoveSuccess: (transferInstrumentId, legalEntityId) => {
                      console.log("onRemoveSuccess");
                    }, // Optional
                  })
                  .mount(containerRef.current); // Mount to the container you created
           
            }
  
        if(session) loadComponent()
  
      },[session])
  
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
  
                  <Paper sx={{ width: '100%', display: session ? 'fixed': 'none'}}>
                      <div ref={containerRef}></div>
                  </Paper>
                  <br/><br/>
  
              </Box>
          </Box>
    );
  }
