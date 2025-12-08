import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './Billing.css';

const Billing = () => {
  const [bills, setBills] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [meters, setMeters] = useState([]);
  const [readings, setReadings] = useState([]);
  const [form, setForm] = useState({
    customerId: '',
    meterId: '',
    readingId: '',
    billingPeriod: '',
    dueDate: ''
  });
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchBills();
    fetchCustomers();
    fetchMeters();
    fetchReadings();
  }, []);

  const fetchBills = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/bills');
      setBills(response.data);
    } catch (error) {
      console.error('Error fetching bills:', error);
    }
  };

  const fetchCustomers = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/customers');
      setCustomers(response.data);
    } catch (error) {
      console.error('Error fetching customers:', error);
    }
  };

  const fetchMeters = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/meters');
      setMeters(response.data);
    } catch (error) {
      console.error('Error fetching meters:', error);
    }
  };

  const fetchReadings = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/readings');
      setReadings(response.data);
    } catch (error) {
      console.error('Error fetching readings:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const customer = customers.find(c => c.id == form.customerId);
      const meter = meters.find(m => m.id == form.meterId);
      const reading = readings.find(r => r.id == form.readingId);

      await axios.post('http://localhost:8081/api/bills', {
        ...form,
        customer,
        meter,
        reading,
        dueDate: form.dueDate || new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0] // 30 days from now
      });

      fetchBills();
      setForm({
        customerId: '',
        meterId: '',
        readingId: '',
        billingPeriod: '',
        dueDate: ''
      });
    } catch (error) {
      console.error('Error creating bill:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredBills = bills.filter(bill =>
    bill.meter?.customer?.fullName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    bill.meter?.meterNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
    bill.status?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="billing">
      <div className="header">
        <h2>Billing Management</h2>
        <Link to="/dashboard" className="back-btn">← Back to Dashboard</Link>
      </div>

      <div className="content">
        <div className="form-section">
          <h3>Generate New Bill</h3>
          <form onSubmit={handleSubmit} className="billing-form">
            <div className="form-group">
              <label htmlFor="customerId">Customer *</label>
              <select
                id="customerId"
                value={form.customerId}
                onChange={(e) => setForm({ ...form, customerId: e.target.value })}
                required
              >
                <option value="">Select Customer</option>
                {customers.map(customer => (
                  <option key={customer.id} value={customer.id}>
                    {customer.fullName} ({customer.customerType})
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="meterId">Meter *</label>
              <select
                id="meterId"
                value={form.meterId}
                onChange={(e) => setForm({ ...form, meterId: e.target.value })}
                required
              >
                <option value="">Select Meter</option>
                {meters
                  .filter(meter => !form.customerId || meter.customer?.id == form.customerId)
                  .map(meter => (
                    <option key={meter.id} value={meter.id}>
                      {meter.meterNumber} - {meter.utilityType?.utilityName}
                    </option>
                  ))}
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="readingId">Reading *</label>
              <select
                id="readingId"
                value={form.readingId}
                onChange={(e) => setForm({ ...form, readingId: e.target.value })}
                required
              >
                <option value="">Select Reading</option>
                {readings
                  .filter(reading => !form.meterId || reading.meter?.id == form.meterId)
                  .map(reading => (
                    <option key={reading.id} value={reading.id}>
                      {reading.currentReading} units on {new Date(reading.readingDate).toLocaleDateString()}
                    </option>
                  ))}
              </select>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="billingPeriod">Billing Period</label>
                <input
                  type="text"
                  id="billingPeriod"
                  placeholder="e.g., January 2024"
                  value={form.billingPeriod}
                  onChange={(e) => setForm({ ...form, billingPeriod: e.target.value })}
                />
              </div>
              <div className="form-group">
                <label htmlFor="dueDate">Due Date</label>
                <input
                  type="date"
                  id="dueDate"
                  value={form.dueDate}
                  onChange={(e) => setForm({ ...form, dueDate: e.target.value })}
                />
              </div>
            </div>

            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? 'Generating...' : 'Generate Bill'}
            </button>
          </form>
        </div>

        <div className="table-section">
          <div className="table-header">
            <h3>Bill History</h3>
            <input
              type="text"
              placeholder="Search bills..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>

          <div className="table-container">
            <table className="billing-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Customer</th>
                  <th>Meter</th>
                  <th>Amount</th>
                  <th>Outstanding</th>
                  <th>Status</th>
                  <th>Due Date</th>
                  <th>Billing Period</th>
                </tr>
              </thead>
              <tbody>
                {filteredBills.map(bill => (
                  <tr key={bill.id}>
                    <td>{bill.id}</td>
                    <td>{bill.meter?.customer?.fullName}</td>
                    <td>{bill.meter?.meterNumber}</td>
                    <td>Rs{bill.amount?.toFixed(2)}</td>
                    <td>Rs{bill.outstandingBalance?.toFixed(2)}</td>
                    <td>
                      <span className={`status-badge ${bill.status?.toLowerCase()}`}>
                        {bill.status || 'PENDING'}
                      </span>
                    </td>
                    <td>{bill.dueDate ? new Date(bill.dueDate).toLocaleDateString() : '-'}</td>
                    <td>{bill.billingPeriod || '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {filteredBills.length === 0 && (
            <div className="no-data">No bills found</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Billing;