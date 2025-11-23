import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Bar, Pie, Line } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement,
} from 'chart.js';
import './Reports.css';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement
);

const Reports = () => {
  const [customers, setCustomers] = useState([]);
  const [bills, setBills] = useState([]);
  const [payments, setPayments] = useState([]);
  const [meters, setMeters] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [customersRes, billsRes, paymentsRes, metersRes] = await Promise.all([
        axios.get('http://localhost:8081/api/customers'),
        axios.get('http://localhost:8081/api/bills'),
        axios.get('http://localhost:8081/api/payments'),
        axios.get('http://localhost:8081/api/meters')
      ]);

      setCustomers(customersRes.data);
      setBills(billsRes.data);
      setPayments(paymentsRes.data);
      setMeters(metersRes.data);
    } catch (error) {
      console.error('Error fetching report data:', error);
    } finally {
      setLoading(false);
    }
  };

  // Customer type distribution
  const customerTypeData = {
    labels: ['Household', 'Business', 'Government'],
    datasets: [{
      data: [
        customers.filter(c => c.type === 'Household').length,
        customers.filter(c => c.type === 'Business').length,
        customers.filter(c => c.type === 'Government').length,
      ],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
      hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
    }],
  };

  // Monthly revenue (mock data for now)
  const revenueData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
    datasets: [{
      label: 'Revenue (Rs)',
      data: [1200, 1900, 3000, 5000, 2000, 3000],
      backgroundColor: 'rgba(54, 162, 235, 0.6)',
      borderColor: 'rgba(54, 162, 235, 1)',
      borderWidth: 1,
    }],
  };

  // Payment methods distribution
  const paymentMethodData = {
    labels: ['Cash', 'Card', 'Online', 'Cheque'],
    datasets: [{
      data: [
        payments.filter(p => p.method === 'Cash').length,
        payments.filter(p => p.method === 'Card').length,
        payments.filter(p => p.method === 'Online').length,
        payments.filter(p => p.method === 'Cheque').length,
      ],
      backgroundColor: ['#4CAF50', '#2196F3', '#FF9800', '#9C27B0'],
      hoverBackgroundColor: ['#4CAF50', '#2196F3', '#FF9800', '#9C27B0'],
    }],
  };

  // Bill status distribution
  const billStatusData = {
    labels: ['Paid', 'Pending', 'Overdue'],
    datasets: [{
      label: 'Number of Bills',
      data: [
        bills.filter(b => b.status === 'PAID').length,
        bills.filter(b => b.status === 'PENDING').length,
        bills.filter(b => b.status === 'OVERDUE').length,
      ],
      backgroundColor: ['rgba(75, 192, 192, 0.6)', 'rgba(255, 206, 86, 0.6)', 'rgba(255, 99, 132, 0.6)'],
      borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 206, 86, 1)', 'rgba(255, 99, 132, 1)'],
      borderWidth: 1,
    }],
  };

  const totalRevenue = bills
    .filter(bill => bill.status === 'PAID')
    .reduce((sum, bill) => sum + (bill.amount || 0), 0);

  const totalOutstanding = bills
    .filter(bill => bill.status !== 'PAID')
    .reduce((sum, bill) => sum + (bill.outstandingBalance || 0), 0);

  if (loading) {
    return <div className="loading">Loading reports...</div>;
  }

  return (
    <div className="reports">
      <div className="header">
        <h2>Management Reports</h2>
        <Link to="/dashboard" className="back-btn">← Back to Dashboard</Link>
      </div>

      <div className="summary-cards">
        <div className="summary-card">
          <h3>Total Customers</h3>
          <div className="summary-value">{customers.length}</div>
        </div>
        <div className="summary-card">
          <h3>Total Meters</h3>
          <div className="summary-value">{meters.length}</div>
        </div>
        <div className="summary-card">
          <h3>Total Revenue</h3>
          <div className="summary-value">Rs{totalRevenue.toFixed(2)}</div>
        </div>
        <div className="summary-card">
          <h3>Outstanding Amount</h3>
          <div className="summary-value">Rs{totalOutstanding.toFixed(2)}</div>
        </div>
      </div>

      <div className="charts-grid">
        <div className="chart-card">
          <h3>Customer Distribution by Type</h3>
          <div className="chart-container">
            <Pie data={customerTypeData} options={{ responsive: true, maintainAspectRatio: false }} />
          </div>
        </div>

        <div className="chart-card">
          <h3>Monthly Revenue Trend</h3>
          <div className="chart-container">
            <Line data={revenueData} options={{ responsive: true, maintainAspectRatio: false }} />
          </div>
        </div>

        <div className="chart-card">
          <h3>Payment Methods</h3>
          <div className="chart-container">
            <Pie data={paymentMethodData} options={{ responsive: true, maintainAspectRatio: false }} />
          </div>
        </div>

        <div className="chart-card">
          <h3>Bill Status Overview</h3>
          <div className="chart-container">
            <Bar data={billStatusData} options={{ responsive: true, maintainAspectRatio: false }} />
          </div>
        </div>
      </div>

      <div className="reports-table">
        <h3>Recent Bills</h3>
        <div className="table-container">
          <table className="reports-table-content">
            <thead>
              <tr>
                <th>Bill ID</th>
                <th>Customer</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Due Date</th>
                <th>Outstanding</th>
              </tr>
            </thead>
            <tbody>
              {bills.slice(0, 10).map(bill => (
                <tr key={bill.id}>
                  <td>{bill.id}</td>
                  <td>{bill.customer?.name}</td>
                  <td>Rs{bill.amount?.toFixed(2)}</td>
                  <td>
                    <span className={`status-badge ${bill.status?.toLowerCase()}`}>
                      {bill.status || 'PENDING'}
                    </span>
                  </td>
                  <td>{bill.dueDate ? new Date(bill.dueDate).toLocaleDateString() : '-'}</td>
                  <td>Rs{bill.outstandingBalance?.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Reports;