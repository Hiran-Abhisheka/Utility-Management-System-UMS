import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import CustomerManagement from './components/CustomerManagement';
import MeterManagement from './components/MeterManagement';
import MeterReading from './components/MeterReading';
import Billing from './components/Billing';
import Payment from './components/Payment';
import Reports from './components/Reports';
import './App.css';

const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(user.role)) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/" element={<Login />} />
            <Route
              path="/dashboard"
              element={
                <ProtectedRoute>
                  <Dashboard />
                </ProtectedRoute>
              }
            />
            <Route
              path="/customers"
              element={
                <ProtectedRoute allowedRoles={['ADMIN']}>
                  <CustomerManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/meters"
              element={
                <ProtectedRoute allowedRoles={['ADMIN']}>
                  <MeterManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/readings"
              element={
                <ProtectedRoute allowedRoles={['METER_READER']}>
                  <MeterReading />
                </ProtectedRoute>
              }
            />
            <Route
              path="/billing"
              element={
                <ProtectedRoute allowedRoles={['CASHIER']}>
                  <Billing />
                </ProtectedRoute>
              }
            />
            <Route
              path="/payments"
              element={
                <ProtectedRoute allowedRoles={['CASHIER']}>
                  <Payment />
                </ProtectedRoute>
              }
            />
            <Route
              path="/reports"
              element={
                <ProtectedRoute allowedRoles={['MANAGER']}>
                  <Reports />
                </ProtectedRoute>
              }
            />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
