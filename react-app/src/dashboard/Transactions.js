import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Toolbar from '@mui/material/Toolbar';
import Divider from '@mui/material/Divider';

import Paper from '@mui/material/Paper';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

import { AdyenPlatformExperience, TransactionsOverview } from '@adyen/adyen-platform-experience-web';
import "@adyen/adyen-platform-experience-web/adyen-platform-experience-web.css";

export default function Products() {

    async function sessionRequest() {
        try {
            const response = await fetch('/api/dashboard/getSessionToken', {
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
        const core = await AdyenPlatformExperience({
            environment: 'test', // test or live
            locale: 'en-US',
            async onSessionCreate() {
                const session = await sessionRequest();
                return session;
            }
        });
        const transactionsOverview = new TransactionsOverview({ core: core, preferredLimit: 10, allowLimitSelection: true });

        transactionsOverview.mount('#transactions-overview-container');
    }

    initializeCore();

    return (
        <Box sx={{ display: 'flex' }}>

            <DashboardHeader/>
            <DashboardDrawer/>

            <Box
                display="flex"
                flexDirection="column"
                alignItems="center"
                width="100%"
                sx={{ p: 3 }}>

                <Toolbar />

                <Divider sx={{ padding: 1 }}>
                    <Chip label="My Transactions" variant="elevated" sx={{ minWidth: 100, fontSize: "20px", backgroundColor: "#0abf53", color: "white" }}/>
                </Divider>

                <Paper sx={{ width: '100%' }}>
                    <div id="transactions-overview-container"></div>
                </Paper>

            </Box>
        </Box>
  );
}
