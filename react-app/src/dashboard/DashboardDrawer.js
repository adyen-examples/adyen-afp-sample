import * as React from 'react';
import axios from "axios";

import Drawer from '@mui/material/Drawer'
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Home from '@mui/icons-material/Home';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import PaymentsIcon from '@mui/icons-material/Payments';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import SettingsApplicationsIcon from '@mui/icons-material/SettingsApplications';
import LogoutIcon from '@mui/icons-material/Logout';

import { Link as RouterLink, useNavigate } from "react-router-dom";

const drawerWidth = 240;

export default function DashboardDrawer() {

    const navigate = useNavigate()

    const handleLogout = () => {
        axios.post('/api/logout')
            .then((response) => {
                navigate('/');
        })
        .catch((error) => {
            console.error('Request error:', error);
        });
    };
    return (
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="permanent"
        anchor="left"
      >
        <Toolbar />
        <Divider />
        <List>
            <ListItem disablePadding button component={RouterLink} to={"/dashboard/"}>
              <ListItemButton>
                <ListItemIcon>
                  <Home />
                </ListItemIcon>
                <ListItemText primary="Home" />
              </ListItemButton>
            </ListItem>
            <ListItem disablePadding button component={RouterLink} to={"/products/"}>
              <ListItemButton>
                <ListItemIcon>
                  <RestaurantIcon />
                </ListItemIcon>
                <ListItemText primary="Products" />
              </ListItemButton>
            </ListItem>
            <ListItem disablePadding button component={RouterLink} to={"/transactions/"}>
              <ListItemButton>
                <ListItemIcon>
                  <PaymentsIcon />
                </ListItemIcon>
                <ListItemText primary="Transactions" />
              </ListItemButton>
            </ListItem>
            <ListItem disablePadding button component={RouterLink} to={"/payouts/"}>
              <ListItemButton>
                <ListItemIcon>
                  <AccountBalanceIcon />
                </ListItemIcon>
                <ListItemText primary="Payouts" />
              </ListItemButton>
            </ListItem>
        </List>
        <Divider />
        <List>
            <ListItem disablePadding button component={RouterLink} to={"/settings/"}>
              <ListItemButton>
                <ListItemIcon>
                  <SettingsApplicationsIcon />
                </ListItemIcon>
                <ListItemText primary="Settings" />
              </ListItemButton>
            </ListItem>
            <ListItem disablePadding button onClick={() => handleLogout()}>
              <ListItemButton>
                <ListItemIcon>
                   <LogoutIcon />
                </ListItemIcon>
                <ListItemText>
                    Logout
                </ListItemText>
              </ListItemButton>
            </ListItem>
        </List>
      </Drawer>

  );
}