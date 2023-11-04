import React, { useState } from "react";
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import TextField from "@mui/material/TextField";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Button from "@mui/material/Button";

function IndividualSignupForm() {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        country: "",
        agreeTerms: false,
    });

    const handleChange = (event) => {
        const { name, value, checked } = event.target;
        setFormData({
        ...formData,
        [name]: name === "agreeTerms" ? checked : value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
    };


    return (
    <form onSubmit={handleSubmit}>
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
        name="country"
        value={formData.country}
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