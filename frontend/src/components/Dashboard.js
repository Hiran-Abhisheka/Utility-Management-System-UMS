import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Dashboard.css';

const Dashboard = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [stats, setStats] = useState({
    totalCustomers: 0,
    totalMeters: 0,
    pendingBills: 0,
    totalRevenue: 0
  });

  useEffect(() => {
    if (user) {
      fetchStats();
    }
  }, [user]);

  const fetchStats = async () => {
    try {
      const [customersRes, metersRes, billsRes] = await Promise.all([
        axios.get('http://localhost:8081/api/customers'),
        axios.get('http://localhost:8081/api/meters'),
        axios.get('http://localhost:8081/api/bills')
      ]);

      setStats({
        totalCustomers: customersRes.data.length,
        totalMeters: metersRes.data.length,
        pendingBills: billsRes.data.filter(bill => bill.status === 'PENDING').length,
        totalRevenue: billsRes.data
          .filter(bill => bill.status === 'PAID')
          .reduce((sum, bill) => sum + bill.amount, 0)
      });
    } catch (error) {
      console.error('Error fetching stats:', error);
    }
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  if (!user) return <div>Please login</div>;

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>Utility Management System</h1>
          <div className="user-info">
            <span>Welcome, {user.username}</span>
            <span className="user-role">({user.role})</span>
            <button onClick={handleLogout} className="logout-btn">Logout</button>
          </div>
        </div>
      </header>

      <nav className="dashboard-nav">
        <div className="nav-container">
          {user.role === 'ADMIN' && (
            <>
              <Link to="/customers" className="nav-link">Customer Management</Link>
              <Link to="/meters" className="nav-link">Meter Management</Link>
            </>
          )}
          {user.role === 'METER_READER' && (
            <Link to="/readings" className="nav-link">Meter Reading</Link>
          )}
          {user.role === 'CASHIER' && (
            <>
              <Link to="/billing" className="nav-link">Billing</Link>
              <Link to="/payments" className="nav-link">Payments</Link>
            </>
          )}
          {user.role === 'MANAGER' && (
            <Link to="/reports" className="nav-link">Reports</Link>
          )}
        </div>
      </nav>

      <main className="dashboard-main">
        <div className="stats-grid">
          <div className="stat-card">
            <h3>Total Customers</h3>
            <div className="stat-value">{stats.totalCustomers}</div>
          </div>
          <div className="stat-card">
            <h3>Total Meters</h3>
            <div className="stat-value">{stats.totalMeters}</div>
          </div>
          <div className="stat-card">
            <h3>Pending Bills</h3>
            <div className="stat-value">{stats.pendingBills}</div>
          </div>
          <div className="stat-card">
            <h3>Total Revenue</h3>
            <div className="stat-value">Rs{stats.totalRevenue.toFixed(2)}</div>
          </div>
        </div>

        <div className="quick-actions">
          <h2>Quick Actions</h2>
          <div className="actions-grid">
            {user.role === 'Admin' && (
              <>
                <Link to="/customers" className="action-card">
                  <h3>Manage Customers</h3>
                  <p>Add, edit, or view customer information</p>
                </Link>
                <Link to="/meters" className="action-card">
                  <h3>Manage Meters</h3>
                  <p>Install and maintain utility meters</p>
                </Link>
              </>
            )}
            {user.role === 'Meter Reader' && (
              <Link to="/readings" className="action-card">
                <h3>Record Readings</h3>
                <p>Take and submit meter readings</p>
              </Link>
            )}
            {user.role === 'Cashier' && (
              <>
                <Link to="/billing" className="action-card">
                  <h3>Generate Bills</h3>
                  <p>Create and manage customer bills</p>
                </Link>
                <Link to="/payments" className="action-card">
                  <h3>Process Payments</h3>
                  <p>Handle customer payments</p>
                </Link>
              </>
            )}
            {user.role === 'Manager' && (
              <Link to="/reports" className="action-card">
                <h3>View Reports</h3>
                <p>Analyze system performance and data</p>
              </Link>
            )}
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;