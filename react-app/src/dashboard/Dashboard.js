import React, { useState, useEffect } from "react";
import axios from "axios";

import Button from "@mui/material/Button";
import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Grid from "@mui/material/Grid";
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import BarChartIcon from '@mui/icons-material/BarChart';

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
        axios.post('/api/dashboard/getOnboardingLink', {
            host: window.location.origin
          })
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

        <Box style={{ width: '60%', height: '50%'}} textAlign="left" >
            <Grid container padding={1} >
                <Grid item xs={2} >
                    <Typography variant="body" style={{ fontWeight: "bold" }} color="textSecondary">
                    Type:
                    </Typography>
                </Grid>
                <Grid item xs={10}>
                    <Typography variant="body" color="textSecondary">
                    {data.type}
                    </Typography>
                </Grid>
            </Grid>
            <Grid container spacing={2} padding={1}>
                <Grid item xs={2} >
                    <Typography variant="body" style={{ fontWeight: "bold" }} color="textSecondary">
                    Name:
                    </Typography>
                </Grid>
                <Grid item xs={10}>
                    <Typography variant="body" color="textSecondary">
                    {data.name}
                    </Typography>
                </Grid>
            </Grid>
            <Grid container spacing={2} padding={1}>
                <Grid item xs={2} >
                    <Typography variant="body" style={{ fontWeight: "bold" }} color="textSecondary">
                    Country:
                    </Typography>
                </Grid>
                <Grid item xs={10}>
                    <Typography variant="body" color="textSecondary">
                    {data.location}
                    </Typography>
                </Grid>
            </Grid>
        </Box>

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
                <img src="/status-valid-image.svg" alt="Verification successful" style={{ width: '30%', height: '30%' }} />
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
                <img src="/status-pending-image.svg" alt="Verification pending" style={{ width: '30%', height: '30%' }} />
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
                <img src="/status-invalid-image.svg" alt="Verification invalid" style={{ width: '30%', height: '30%' }} />
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