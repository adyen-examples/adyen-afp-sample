import React, { useState, useEffect } from "react";
import axios from "axios";

import Alert from '@mui/material/Alert';
import Button from "@mui/material/Button";
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer'
import Container from '@mui/material/Container';
import Chip from '@mui/material/Chip';
import Link from '@mui/material/Link';
import Grid from "@mui/material/Grid";
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
import InfoIcon from '@mui/icons-material/Info';
import AccountCircle from '@mui/icons-material/AccountCircle';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import PaymentsIcon from '@mui/icons-material/Payments';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import SettingsApplicationsIcon from '@mui/icons-material/SettingsApplications';
import LogoutIcon from '@mui/icons-material/Logout';
import ErrorIcon from '@mui/icons-material/Error';
import { Link as RouterLink } from "react-router-dom";

import { useNavigate } from 'react-router-dom';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

export default function Dashboard() {

    const navigate = useNavigate()

    const [data, setData] = useState([]);

    useEffect(() => {
        axios.post('/api/dashboard/getUser')
          .then((response) => {
            setData(response.data);
          })
          .catch((error) => {
            console.error('API request error:', error);
            navigate('/');
          });

    }, []);

    const generateOnboardingLink = () => {
        axios.post('/api/dashboard/getOnboardingLink')
          .then((response) => {
            window.location.href = response.data;
          })
          .catch((error) => {
            console.error('API request error:', error);
          });
      };

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
        <br/>

        {/*
        Display relevant message based on AccountHolderStatus value
        */}
        {data.status === "VALID" && (<VerificationSuccess handleClick={generateOnboardingLink}/>) }
        {data.status === "INVALID" && (<VerificationInvalid handleClick={generateOnboardingLink}/>) }
        {data.status === "PENDING" && (<VerificationPending handleClick={generateOnboardingLink}/>) }

      </Box>
    </Box>
  );
}

const VerificationSuccess = ({ handleClick }) => {
    return(

        <Box style={{ width: '70%', height: '50%'}} textAlign="center" >
            <div>
                <img src="/verification-success.png" alt="Verification successful" style={{ width: '30%', height: '30%' }} />
            </div>
            <Typography variant="body" style={{ fontWeight: "bold" }} color="textSecondary">
                The verification has been successfully completed. You can start accepting orders!
            </Typography>
            <br/><br/>
            <Button
                type="submit"
                variant="contained"
                sx={{ backgroundColor: "#0abf53", borderRadius: 25, color: "white", "&:hover": {backgroundColor: "green"} }}
                onClick={handleClick}
                >
                    Check your information
            </Button>
        </Box>
    );
}

const VerificationPending = ({ handleClick }) => {
    return(

        <Box style={{ width: '70%', height: '50%'}} textAlign="center" >
            <div>
                <img src="/verification-pending.png" alt="Verification pending" style={{ width: '30%', height: '30%' }} />
            </div>
            <Typography variant="body" style={{ fontWeight: "bold" }} color="textSecondary">
                We are reviewing the details you provided and we will send you a notification as soon as the
                verification is completed.
                Click below if you wish to review and edit the information you have submitted.

            </Typography>
            <br/><br/>
            <Button
                type="submit"
                variant="contained"
                sx={{ backgroundColor: "#0abf53", borderRadius: 25, color: "white", "&:hover": {backgroundColor: "green"} }}
                onClick={handleClick}
                >
                    Update your information
            </Button>
        </Box>
    );
}

const VerificationInvalid = ({ handleClick }) => {
    return(

        <Box style={{ width: '70%', height: '50%'}} textAlign="center" >
            <div>
                <img src="/verification-invalid.png" alt="Verification invalid" style={{ width: '30%', height: '30%' }} />
            </div>
            <Typography variant="body" style={{ fontWeight: "bold" }} color="textSecondary">
                The verification unfortunately is not successful.
                Click below to check what information is missing or incomplete.

            </Typography>
            <br/><br/>
            <Button
                type="submit"
                variant="contained"
                sx={{ backgroundColor: "#0abf53", borderRadius: 25, color: "white", "&:hover": {backgroundColor: "green"} }}
                onClick={handleClick}
                >
                    Correct your information
            </Button>
        </Box>
    );
}