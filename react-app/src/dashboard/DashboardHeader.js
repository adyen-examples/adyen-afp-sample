import * as React from 'react';

import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

const drawerWidth = 240;

export default function DashboardHeader() {
  return (
      <AppBar
        position="fixed"
        sx={{ background: "white", width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
      >
        <Toolbar>
            <Typography variant="h4" component="div" sx={{ flexGrow: 1, fontWeight: "bold",  }} color="textSecondary">
                Dashboard
            </Typography>

        </Toolbar>
      </AppBar>
  );
}