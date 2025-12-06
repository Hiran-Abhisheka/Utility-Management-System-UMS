import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './MeterManagement.css';

const MeterManagement = () => {
  const [meters, setMeters] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [utilityTypes, setUtilityTypes] = useState([]);
  const [form, setForm] = useState({
    meterNumber: '',
    customerId: '',
    utilityTypeId: '',
    installationDate: '',
    status: 'ACTIVE'
  });
  const [editing, setEditing] = useState(null);
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchMeters();
    fetchCustomers();
    fetchUtilityTypes();
  }, []);

  const fetchMeters = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/meters');
      setMeters(response.data);
    } catch (error) {
      console.error('Error fetching meters:', error);
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

  const fetchUtilityTypes = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/utility-types');
      setUtilityTypes(response.data);
    } catch (error) {
      console.error('Error fetching utility types:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const customer = customers.find(c => c.id == form.customerId);
      const utilityType = utilityTypes.find(u => u.id == form.utilityTypeId);

      const meterData = {
        ...form,
        customer,
        utilityType,
        installationDate: form.installationDate || new Date().toISOString().split('T')[0]
      };

      if (editing) {
        await axios.put(`http://localhost:8081/api/meters/${editing.id}`, meterData);
        setEditing(null);
      } else {
        await axios.post('http://localhost:8081/api/meters', meterData);
      }

      fetchMeters();
      setForm({
        meterNumber: '',
        customerId: '',
        utilityTypeId: '',
        installationDate: '',
        status: 'ACTIVE'
      });
    } catch (error) {
      console.error('Error saving meter:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (meter) => {
    setForm({
      meterNumber: meter.meterNumber,
      customerId: meter.customer?.id || '',
      utilityTypeId: meter.utilityType?.id || '',
      installationDate: meter.installationDate ? meter.installationDate.split('T')[0] : '',
      status: meter.status || 'ACTIVE'
    });
    setEditing(meter);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this meter?')) {
      try {
        await axios.delete(`http://localhost:8081/api/meters/${id}`);
        fetchMeters();
      } catch (error) {
        console.error('Error deleting meter:', error);
      }
    }
  };

  const handleCancel = () => {
    setForm({
      meterNumber: '',
      customerId: '',
      utilityTypeId: '',
      installationDate: '',
      status: 'ACTIVE'
    });
    setEditing(null);
  };

  const filteredMeters = meters.filter(meter =>
    meter.meterNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
    meter.customer?.fullName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    meter.utilityType?.utilityName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="meter-management">
      <div className="header">
        <h2>Meter Management</h2>
        <Link to="/dashboard" className="back-btn">← Back to Dashboard</Link>
      </div>

      <div className="content">
        <div className="form-section">
          <h3>{editing ? 'Edit Meter' : 'Add New Meter'}</h3>
          <form onSubmit={handleSubmit} className="meter-form">
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="meterNumber">Meter Number *</label>
                <input
                  type="text"
                  id="meterNumber"
                  value={form.meterNumber}
                  onChange={(e) => setForm({ ...form, meterNumber: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="status">Status</label>
                <select
                  id="status"
                  value={form.status}
                  onChange={(e) => setForm({ ...form, status: e.target.value })}
                >
                  <option value="ACTIVE">Active</option>
                  <option value="INACTIVE">Inactive</option>
                  <option value="MAINTENANCE">Maintenance</option>
                </select>
              </div>
            </div>

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
              <label htmlFor="utilityTypeId">Utility Type *</label>
              <select
                id="utilityTypeId"
                value={form.utilityTypeId}
                onChange={(e) => setForm({ ...form, utilityTypeId: e.target.value })}
                required
              >
                <option value="">Select Utility Type</option>
                {utilityTypes.map(type => (
                  <option key={type.id} value={type.id}>{type.utilityName}</option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="installationDate">Installation Date</label>
              <input
                type="date"
                id="installationDate"
                value={form.installationDate}
                onChange={(e) => setForm({ ...form, installationDate: e.target.value })}
              />
            </div>

            <div className="form-actions">
              <button type="submit" className="submit-btn" disabled={loading}>
                {loading ? 'Saving...' : (editing ? 'Update Meter' : 'Add Meter')}
              </button>
              {editing && (
                <button type="button" onClick={handleCancel} className="cancel-btn">
                  Cancel
                </button>
              )}
            </div>
          </form>
        </div>

        <div className="table-section">
          <div className="table-header">
            <h3>Meter List</h3>
            <input
              type="text"
              placeholder="Search meters..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>

          <div className="table-container">
            <table className="meter-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Meter Number</th>
                  <th>Customer</th>
                  <th>Utility Type</th>
                  <th>Status</th>
                  <th>Installation Date</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredMeters.map(meter => (
                  <tr key={meter.id}>
                    <td>{meter.id}</td>
                    <td>{meter.meterNumber}</td>
                    <td>{meter.customer?.fullName}</td>
                    <td>{meter.utilityType?.utilityName}</td>
                    <td>
                      <span className={`status-badge ${meter.status?.toLowerCase()}`}>
                        {meter.status || 'ACTIVE'}
                      </span>
                    </td>
                    <td>{meter.installationDate ? new Date(meter.installationDate).toLocaleDateString() : '-'}</td>
                    <td>
                      <button onClick={() => handleEdit(meter)} className="edit-btn">
                        Edit
                      </button>
                      <button onClick={() => handleDelete(meter.id)} className="delete-btn">
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {filteredMeters.length === 0 && (
            <div className="no-data">No meters found</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MeterManagement;