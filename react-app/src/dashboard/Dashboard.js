import React, { useState, useEffect } from "react";
import axios from "axios";

import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer'
import Container from '@mui/material/Container';
import Chip from '@mui/material/Chip';
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

import { useNavigate } from 'react-router-dom';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

const drawerWidth = 240;

export default function Dashboard() {

    const navigate = useNavigate()

    const [data, setData] = useState([]);

    useEffect(() => {
        axios.post('/api/dashboard/getAccountHolder')
          .then((response) => {
            setData(response.data);
          })
          .catch((error) => {
            console.error('API request error:', error);
            navigate('/');
          });
    }, []);

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

        <Divider>
            <Chip label="Status" sx={{ fontSize: "20px" }}/>
        </Divider>
        <br/><br/>

        {data.id}


      </Box>
    </Box>
  );
}