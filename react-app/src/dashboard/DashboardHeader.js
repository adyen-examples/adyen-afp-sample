import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer'
import Container from '@mui/material/Container';

import Link from '@mui/material/Link';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import BarChartIcon from '@mui/icons-material/BarChart';
import IconButton from '@mui/material/IconButton';
import AccountCircle from '@mui/icons-material/AccountCircle';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import PaymentsIcon from '@mui/icons-material/Payments';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import SettingsApplicationsIcon from '@mui/icons-material/SettingsApplications';
import LogoutIcon from '@mui/icons-material/Logout';

import { Link as RouterLink } from "react-router-dom";

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

            <div>
                <IconButton>
                    <AccountCircle fontSize="large"/>
                </IconButton>
            </div>
        </Toolbar>
      </AppBar>
  );
}