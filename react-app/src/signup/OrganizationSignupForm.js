import React, { useState } from "react";
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';

import TextField from "@mui/material/TextField";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Button from "@mui/material/Button";

function OrganizationSignupForm() {
    const [formData, setFormData] = useState({
        legalName: "",
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
                <InputLabel>Legal name *</InputLabel>
                <TextField
                    name="legalName"
                    value={formData.legalName}
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
                    <MenuItem value="usa">USA</MenuItem>
                    <MenuItem value="canada">Canada</MenuItem>
                    <MenuItem value="canada">France</MenuItem>
                    <MenuItem value="canada">Germany</MenuItem>
                    <MenuItem value="canada">Italy</MenuItem>
                    <MenuItem value="canada">The Netherlands</MenuItem>
                    <MenuItem value="canada">United Kingdom</MenuItem>
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

export default OrganizationSignupForm;