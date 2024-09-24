import React from "react";
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

import Footer from "../layout/Footer.js";
import SmallBanner from "../layout/SmallBanner.js";
import SoleProprietorshipSignupForm from "./SoleProprietorshipSignupForm.js";
import OrganizationSignupForm from "./OrganizationSignupForm.js";

import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';

function Signup() {

    const [value, setValue] = React.useState('individual');

    const handleTabChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <div>

        <Container maxWidth="sm">
            <Grid container justify="center">
                <SmallBanner/>
            </Grid>
            <br/>
            <Box textAlign="center">
                <Typography variant="h4" style={{ fontWeight: "bold" }} color="textSecondary">
                    Create an account
                </Typography>
            </Box>

            <Tabs value={value}
                onChange={handleTabChange} aria-label="disabled tabs example"
                textColor="secondary" indicatorColor="secondary">
                <Tab value="soleproprietorship" label="Sole Proprietorship" />
               <Tab value="organization" label="Organization" />
            </Tabs>
            <br/>

            <Box hidden={value !== "soleproprietorship"}>
                <SoleProprietorshipSignupForm/>
            </Box>
            <Box hidden={value !== "organization"}>
                <OrganizationSignupForm/>
            </Box>
        </Container>
        <Footer/>
        </div>

  );
}





export default Signup;