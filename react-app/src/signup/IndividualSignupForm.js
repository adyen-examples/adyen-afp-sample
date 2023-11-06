import React, { useState } from "react";
import axios from "axios";

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import TextField from "@mui/material/TextField";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Button from "@mui/material/Button";
import { useNavigate } from 'react-router-dom';

import ErrorMessage from "../util/ErrorMessage.js";

function IndividualSignupForm() {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        countryCode: "",
        agreeTerms: false,
    });
    const [invalidSignup, setInvalidSignup] = useState(false);
    const navigate = useNavigate()

    const handleChange = (event) => {
        const { name, value, checked } = event.target;
        setFormData({
        ...formData,
        [name]: name === "agreeTerms" ? checked : value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        setInvalidSignup(false);

        axios.post('/api/signup/individual', formData)
            .then((response) => {
                navigate('/dashboard');
        })
        .catch((error) => {
            console.error('Request error:', error);
            setInvalidSignup(true);
        });
    };

    return (
    <form onSubmit={handleSubmit}>

        {invalidSignup && <ErrorMessage msg="An error has occurred"/>}

    <Box sx={{ mb: 1 }}>
    <InputLabel>First name *</InputLabel>
      <TextField
        name="firstName"
        value={formData.firstName}
        onChange={handleChange}
        fullWidth
        required
      />
      </Box>

<Box sx={{ mb: 1 }}>
    <InputLabel>Last name *</InputLabel>
      <TextField
        name="lastName"
        value={formData.lastName}
        onChange={handleChange}
        fullWidth
        required
      />
</Box>

<Box sx={{ mb: 1 }}>
<InputLabel>Country *</InputLabel>
      <Select
        name="countryCode"
        value={formData.countryCode}
        onChange={handleChange}
        fullWidth
        required
      >
        <MenuItem value="US">USA</MenuItem>
        <MenuItem value="CA">Canada</MenuItem>
        <MenuItem value="FR">France</MenuItem>
        <MenuItem value="DE">Germany</MenuItem>
        <MenuItem value="IT">Italy</MenuItem>
        <MenuItem value="NL">The Netherlands</MenuItem>
        <MenuItem value="UK">United Kingdom</MenuItem>
      </Select>
</Box>
      <FormControlLabel
        control={
          <Checkbox
            name="agreeTerms"
            checked={formData.agreeTerms}
            onChange={handleChange}
          />
        }
        label="I agree to the terms and conditions"
      />
<Box textAlign="right">
      <Button
        type="submit"
        variant="contained"
        sx={{ backgroundColor: "#0abf53", color: "white", "&:hover": {backgroundColor: "green"} }}
        disabled={!formData.agreeTerms}
      >
        Submit
      </Button>
</Box>
    </form>

  );
}


export default IndividualSignupForm;