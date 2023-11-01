import React, { useState } from "react";
import axios from "axios";

import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import InputLabel from '@mui/material/InputLabel';
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Link from '@mui/material/Link';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';

import Footer from "../layout/Footer.js";
import SmallBanner from "../layout/SmallBanner.js";
import LoginInfo from "./LoginInfo.js";
import ErrorMessage from "../util/ErrorMessage.js";

function Login() {

    const [formData, setFormData] = useState({
        username: "",
        password: "",
    });

    const [invalidLogin, setInvalidLogin] = useState(false);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData({
        ...formData,
        [name]: value,
        });

    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);

        setInvalidLogin(false);


        axios.post('/api/login', formData)
            .then((response) => {
            const statusCode = response.status;
            console.log(statusCode);

        //this.props.history.push('/dashboard');
    })
    .catch((error) => {
        // Handle request error, if any
        console.error('Request error:', error);
        setInvalidLogin(true);
      });
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
                    Login
                </Typography>
            </Box>
            { invalidLogin && <ErrorMessage msg="Invalid credentials"/>}

            <Box textAlign="end">
                <LoginInfo/>
            </Box>

    <form onSubmit={handleSubmit}>
    <Box sx={{ mb: 1 }}>
    <InputLabel>Username *</InputLabel>
      <TextField
        name="username"
        value={formData.username}
        onChange={handleChange}
        fullWidth
        required
      />
      </Box>

<Box sx={{ mb: 1 }}>
    <InputLabel>Password *</InputLabel>
      <TextField
        name="password"
        type="password"
        value={formData.password}
        onChange={handleChange}
        fullWidth
        required
      />
</Box>

<Box textAlign="right">
      <Button
        type="submit"
        variant="contained"
        sx={{ backgroundColor: "#0abf53", color: "white", "&:hover": {backgroundColor: "green"} }}
      >
        Submit
      </Button>
      </Box>
      <Grid item>
                      <Link href="/signup" color="#0abf53" underline="hover" variant="body1">
                        {"Don't have an account? Sign Up"}
                      </Link>
                    </Grid>

    </form>

            </Container>
            <Footer/>
            </div>


  );
}


export default Login;