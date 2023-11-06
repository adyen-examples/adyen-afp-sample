import React from "react";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Link from "@mui/material/Link";

const Footer = () => {
  return (
    <table style={{width:"100%", position: "fixed", bottom: "2px"}}>
        <tr>
            <td>
            <Typography variant="body2" color="textSecondary" align="left">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <Link href="/" color="textSecondary" underline="hover" >Home</Link>
                {' • Policy • Cookies'}
            </Typography>
            </td>
            <td>
             <Grid container spacing={1} justifyContent="flex-end">
                  <Grid item>
                    <Typography variant="body2" color="textSecondary" >
                      Global (English)
                    </Typography>
                  </Grid>
                  <Grid item>
                    <img src="/united-states.png" alt="US flag" style={{ width: '17px', height: '17px' }}/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                  </Grid>
                </Grid>
            </td>
        </tr>
    </table>
  );
};

export default Footer;
