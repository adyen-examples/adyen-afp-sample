import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Toolbar from '@mui/material/Toolbar';
import Divider from '@mui/material/Divider';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

export default function Settings() {
    return (
        <Box sx={{ display: 'flex' }}>
            <DashboardHeader/>
            <DashboardDrawer/>

            <Box
                flexDirection="column"
                alignItems="center"
                width="100%"
                sx={{ p: 3 }}
            >

                <Toolbar />

                <Divider sx={{ padding: 1 }}>
                    <Chip label="Settings" variant="elevated" sx={{ minWidth: 100, fontSize: "20px", backgroundColor: "#0abf53", color: "white" }}/>
                </Divider>
                <br/><br/>
            </Box>
        </Box>
    );
}
