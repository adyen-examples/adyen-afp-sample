import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Home from './home/Home.js';
import Signup from './signup/Signup.js';
import Dashboard from './dashboard/Dashboard.js';
import Products from './dashboard/Products.js';
import Transactions from './dashboard/Transactions.js';
import Payouts from './dashboard/Payouts.js';
import Settings from './dashboard/Settings.js';
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
          <Route exact path="/transactions" element={<Transactions />} />
          <Route exact path="/payouts" element={<Payouts />} />
          <Route exact path="/settings" element={<Settings />} />
         </Routes>
    </Router>
  );
}

export default App;
