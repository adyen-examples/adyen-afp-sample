import * as React from 'react';

import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";
import ProductList from "./ProductList.js"

export default function Products() {

  return (
    <Box sx={{ display: 'flex' }}>

      <DashboardHeader/>

      <DashboardDrawer />

        <Box
              display="flex"
              flexDirection="column"
              alignItems="center"
              width="100%"
              sx={{ p: 3 }}
        >

        <Toolbar />

        <Divider>
            <Chip label="My Products" sx={{ fontSize: "20px" }}/>
        </Divider>
        <ProductList/>
      </Box>
    </Box>
  );
}