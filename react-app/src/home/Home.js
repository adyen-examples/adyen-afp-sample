import * as React from 'react';

import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';

import Footer from "../layout/Footer.js";
import Banner from "../layout/Banner.js";


const card1 = (
    <React.Fragment>
      <CardContent sx={{height: 200}}>
        <div>
            <img src="/onboarding.png" alt="Users onboarding" style={{ width: '10%', height: '10%' }}/>
        </div>
        <br/>
        <Typography variant="h4"  gutterBottom>
          Fast onboarding
        </Typography>
        <Typography variant="body1" color="text.secondary">
          Onboard on your platform new customers quickly, securely and efficiently.
        </Typography>
      </CardContent>
    </React.Fragment>
  );

const card2 = (
    <React.Fragment>
      <CardContent sx={{height: 200}}>
        <div>
            <img src="/payments.png" alt="Support for multiple payment methods" style={{ width: '10%', height: '10%' }}/>
        </div>
        <br/>
        <Typography variant="h4"  gutterBottom>
          Easy payments
        </Typography>
        <Typography variant="body1" color="text.secondary">
          Provide multiple payment options, no hidden fees, secure online integration.
        </Typography>
      </CardContent>
    </React.Fragment>
  );

const card3 = (
    <React.Fragment>
      <CardContent sx={{height: 200}}>
        <div>
            <img src="/online-shopping.png" alt="Online shopping" style={{ width: '10%', height: '10%' }}/>
        </div>
        <br/>
        <Typography variant="h4" gutterBottom>
          Online shopping
        </Typography>
        <Typography variant="body1" color="text.secondary">
          Let your customers easily setup a web page to accept online orders.
        </Typography>
      </CardContent>
    </React.Fragment>
  );

function Home() {
  return (
  <div>
    <Container maxWidth="xl">
        <Grid container justify="center">
            <Banner/>
            <Grid container spacing={5}>
                <Grid item xs={4}>
                    <Card >{card1}</Card>
                </Grid>

                <Grid item xs={4}>
                    <Card >{card2}</Card>
                </Grid>

                <Grid item xs={4}>
                    <Card >{card3}</Card>
                </Grid>

            </Grid>
        </Grid>
        <br/><br/>
        <Container maxWidth="xl" sx={{display: "flex", justifyContent: "center"}}>
            <Box sx={{ width:"70%", minHeight:"70px", backgroundColor: "#F2F3F4", display: "flex", justifyContent: "center"}}>
                 <Stack spacing={2} direction="row" alignItems="center">
                      <Typography variant="body1" style={{ fontWeight: "bold" }} sx={{ mb: 1.5 }}>
                        Start accepting orders
                      </Typography>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <Button href="/signup" variant="contained"
                        sx={{ backgroundColor: "#0abf53", color: "white", "&:hover": {backgroundColor: "green"} }}>
                            Create an account
                      </Button>
                      <Link href="/login" color="#0abf53" underline="hover" variant="body1">(I have already an account)</Link>
                  </Stack>
            </Box>
        </Container>

    </Container>

    <Footer/>

    </div>
  );

}

export default Home;