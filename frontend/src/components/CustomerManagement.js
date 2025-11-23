import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './CustomerManagement.css';

const CustomerManagement = () => {
  const [customers, setCustomers] = useState([]);
  const [form, setForm] = useState({
    fullName: '',
    customerType: '',
    street: '',
    city: '',
    district: '',
    contactNo: '',
    email: ''
  });
  const [editing, setEditing] = useState(null);
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/customers');
      setCustomers(response.data);
    } catch (error) {
      console.error('Error fetching customers:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (editing) {
        await axios.put(`http://localhost:8081/api/customers/${editing.id}`, form);
        setEditing(null);
      } else {
        await axios.post('http://localhost:8081/api/customers', form);
      }
      fetchCustomers();
      setForm({
        fullName: '',
        customerType: '',
        street: '',
        city: '',
        district: '',
        contactNo: '',
        email: ''
      });
    } catch (error) {
      console.error('Error saving customer:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (customer) => {
    setForm(customer);
    setEditing(customer);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this customer?')) {
      try {
        await axios.delete(`http://localhost:8081/api/customers/${id}`);
        fetchCustomers();
      } catch (error) {
        console.error('Error deleting customer:', error);
      }
    }
  };

  const handleCancel = () => {
    setForm({
      fullName: '',
      customerType: '',
      street: '',
      city: '',
      district: '',
      contactNo: '',
      email: ''
    });
    setEditing(null);
  };

  const filteredCustomers = customers.filter(customer =>
    customer.fullName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    customer.customerType.toLowerCase().includes(searchTerm.toLowerCase()) ||
    customer.city.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="customer-management">
      <div className="header">
        <h2>Customer Management</h2>
        <Link to="/dashboard" className="back-btn">← Back to Dashboard</Link>
      </div>

      <div className="content">
        <div className="form-section">
          <h3>{editing ? 'Edit Customer' : 'Add New Customer'}</h3>
          <form onSubmit={handleSubmit} className="customer-form">
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="fullName">Name *</label>
                <input
                  type="text"
                  id="fullName"
                  value={form.fullName}
                  onChange={(e) => setForm({ ...form, fullName: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="customerType">Type *</label>
                <select
                  id="customerType"
                  value={form.customerType}
                  onChange={(e) => setForm({ ...form, customerType: e.target.value })}
                  required
                >
                  <option value="">Select Type</option>
                  <option value="HOUSEHOLD">Household</option>
                  <option value="BUSINESS">Business</option>
                  <option value="GOVERNMENT">Government</option>
                </select>
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="contactNo">Phone</label>
                <input
                  type="tel"
                  id="contactNo"
                  value={form.contactNo}
                  onChange={(e) => setForm({ ...form, contactNo: e.target.value })}
                />
              </div>
              <div className="form-group">
                <label htmlFor="email">Email</label>
                <input
                  type="email"
                  id="email"
                  value={form.email}
                  onChange={(e) => setForm({ ...form, email: e.target.value })}
                />
              </div>
            </div>

            <div className="form-group">
              <label htmlFor="street">Street Address *</label>
              <input
                type="text"
                id="street"
                value={form.street}
                onChange={(e) => setForm({ ...form, street: e.target.value })}
                required
              />
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="city">City *</label>
                <input
                  type="text"
                  id="city"
                  value={form.city}
                  onChange={(e) => setForm({ ...form, city: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="district">District *</label>
                <input
                  type="text"
                  id="district"
                  value={form.district}
                  onChange={(e) => setForm({ ...form, district: e.target.value })}
                  required
                />
              </div>
            </div>

            <div className="form-actions">
              <button type="submit" className="submit-btn" disabled={loading}>
                {loading ? 'Saving...' : (editing ? 'Update Customer' : 'Add Customer')}
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
            <h3>Customer List</h3>
            <input
              type="text"
              placeholder="Search customers..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>

          <div className="table-container">
            <table className="customer-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Phone</th>
                  <th>Email</th>
                  <th>Address</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredCustomers.map(customer => (
                  <tr key={customer.id}>
                    <td>{customer.id}</td>
                    <td>{customer.fullName}</td>
                    <td>
                      <span className={`type-badge ${customer.customerType.toLowerCase()}`}>
                        {customer.customerType}
                      </span>
                    </td>
                    <td>{customer.contactNo || '-'}</td>
                    <td>{customer.email || '-'}</td>
                    <td>{`${customer.street}, ${customer.city}, ${customer.district}`}</td>
                    <td>
                      <button onClick={() => handleEdit(customer)} className="edit-btn">
                        Edit
                      </button>
                      <button onClick={() => handleDelete(customer.id)} className="delete-btn">
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {filteredCustomers.length === 0 && (
            <div className="no-data">No customers found</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CustomerManagement;