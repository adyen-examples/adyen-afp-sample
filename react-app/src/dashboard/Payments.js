import React, { useState, useEffect } from 'react';
import axios from "axios";



import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';

import Toolbar from '@mui/material/Toolbar';
import Divider from '@mui/material/Divider';
import { useNavigate } from 'react-router-dom';

import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';

import DashboardHeader from "./DashboardHeader.js";
import DashboardDrawer from "./DashboardDrawer.js";

export default function Products() {

    const navigate = useNavigate()

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [rows, setRows] = useState([]);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.post('/api/dashboard/getTransactions');
            setRows(response.data);
          } catch (error) {
            console.error('API request error:', error);
            navigate('/');
          }
        };

        fetchData();
    }, []);

  return (
    <Box sx={{ display: 'flex' }}>

      <DashboardHeader/>

      <DashboardDrawer/>

        <Box
              display="flex"
              flexDirection="column"
              alignItems="center"
              width="100%"
              sx={{ p: 3 }}
        >

        <Toolbar />

        <Divider sx={{ padding: 1 }}>
            <Chip label="My Transactions" variant="elevated" sx={{ minWidth: 100, fontSize: "20px", backgroundColor: "#0abf53", color: "white" }}/>
        </Divider>
            <Paper sx={{ width: '100%' }}>
                <TableContainer>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                {columns.map((column) => (
                                    <TableCell
                                      key={column.id}
                                      align={column.align}
                                      style={{ minWidth: column.minWidth }}
                                      sx={{ fontSize: "18px", fontWeight: "bold"  }}
                                    >
                                        {column.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((row) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                                        {columns.map((column) => {
                                        const value = row[column.id];
                                        return (
                                            <TableCell key={column.id} align={column.align}
                                                sx={{ fontSize: "15px",
                                                        color: value === 'Outgoing' ? '#1573C1' : value === 'Incoming' ? '#0A912E' : 'black',
                                                        fontWeight: value === 'Outgoing' ? 'bold' : value === 'Incoming' ? 'bold' : 'normal'
                                                    }}
                                            >
                                                {column.format && typeof value === 'number'? column.format(value) : value}
                                            </TableCell>
                                            );
                                        })}
                                    </TableRow>
                                );
                                })}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[10, 25]}
                    component="div"
                    count={rows.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>


      </Box>
    </Box>
  );
}

const columns = [
    { id: 'id', label: 'ID', minWidth: 100 },
    { id: 'status', label: 'Status', minWidth: 120 },
    { id: 'type', label: 'Type', minWidth: 120 },
    { id: 'created', label: 'Created', minWidth: 220 },
    { id: 'amount', label: 'Amount', minWidth: 150 },
];