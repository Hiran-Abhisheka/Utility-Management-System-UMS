import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './MeterReading.css';

const MeterReading = () => {
  const [readings, setReadings] = useState([]);
  const [meters, setMeters] = useState([]);
  const [form, setForm] = useState({
    meterId: '',
    currentReading: '',
    readingDate: new Date().toISOString().split('T')[0]
  });
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchReadings();
    fetchMeters();
  }, []);

  const fetchReadings = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/readings');
      setReadings(response.data);
    } catch (error) {
      console.error('Error fetching readings:', error);
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

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const meter = meters.find(m => m.id == form.meterId);
      await axios.post('http://localhost:8081/api/readings', {
        ...form,
        meter,
        readingDate: form.readingDate || new Date().toISOString().split('T')[0]
      });
      fetchReadings();
      setForm({
        meterId: '',
        currentReading: '',
        readingDate: new Date().toISOString().split('T')[0]
      });
    } catch (error) {
      console.error('Error submitting reading:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredReadings = readings.filter(reading =>
    reading.meter?.meterNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
    reading.meter?.customer?.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="meter-reading">
      <div className="header">
        <h2>Meter Reading</h2>
        <Link to="/dashboard" className="back-btn">← Back to Dashboard</Link>
      </div>

      <div className="content">
        <div className="form-section">
          <h3>Record New Reading</h3>
          <form onSubmit={handleSubmit} className="reading-form">
            <div className="form-group">
              <label htmlFor="meterId">Select Meter *</label>
              <select
                id="meterId"
                value={form.meterId}
                onChange={(e) => setForm({ ...form, meterId: e.target.value })}
                required
              >
                <option value="">Select Meter</option>
                {meters.map(meter => (
                  <option key={meter.id} value={meter.id}>
                    {meter.meterNumber} - {meter.customer?.name} ({meter.utilityType?.name})
                  </option>
                ))}
              </select>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="currentReading">Current Reading *</label>
                <input
                  type="number"
                  id="currentReading"
                  placeholder="Enter reading value"
                  value={form.currentReading}
                  onChange={(e) => setForm({ ...form, currentReading: e.target.value })}
                  min="0"
                  step="0.01"
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="readingDate">Reading Date *</label>
                <input
                  type="date"
                  id="readingDate"
                  value={form.readingDate}
                  onChange={(e) => setForm({ ...form, readingDate: e.target.value })}
                  required
                />
              </div>
            </div>

            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? 'Submitting...' : 'Submit Reading'}
            </button>
          </form>
        </div>

        <div className="table-section">
          <div className="table-header">
            <h3>Reading History</h3>
            <input
              type="text"
              placeholder="Search readings..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>

          <div className="table-container">
            <table className="reading-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Meter Number</th>
                  <th>Customer</th>
                  <th>Utility Type</th>
                  <th>Current Reading</th>
                  <th>Reading Date</th>
                  <th>Previous Reading</th>
                  <th>Consumption</th>
                </tr>
              </thead>
              <tbody>
                {filteredReadings.map(reading => (
                  <tr key={reading.id}>
                    <td>{reading.id}</td>
                    <td>{reading.meter?.meterNumber}</td>
                    <td>{reading.meter?.customer?.name}</td>
                    <td>{reading.meter?.utilityType?.name}</td>
                    <td>{reading.currentReading}</td>
                    <td>{reading.readingDate ? new Date(reading.readingDate).toLocaleDateString() : '-'}</td>
                    <td>{reading.previousReading || '-'}</td>
                    <td>{reading.unitsConsumed || '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {filteredReadings.length === 0 && (
            <div className="no-data">No readings found</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MeterReading;