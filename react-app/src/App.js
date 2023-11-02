import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Home from './home/Home.js';
import Signup from './signup/Signup.js';
import Dashboard from './dashboard/Dashboard.js';
import Products from './dashboard/Products.js';
import Payments from './dashboard/Payments.js';
import Payouts from './dashboard/Payouts.js';
import Login from './signup/Login.js';

function App() {
  return (
    <Router>
         <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/signup" element={<Signup />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/dashboard" element={<Dashboard />} />
          <Route exact path="/products" element={<Products />} />
          <Route exact path="/payments" element={<Payments />} />
          <Route exact path="/payouts" element={<Payouts />} />
         </Routes>
    </Router>
  );
}

export default App;
