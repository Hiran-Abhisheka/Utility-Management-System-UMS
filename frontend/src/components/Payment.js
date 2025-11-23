import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './Payment.css';

const Payment = () => {
  const [payments, setPayments] = useState([]);
  const [bills, setBills] = useState([]);
  const [form, setForm] = useState({
    billId: '',
    amount: '',
    method: '',
    paymentDate: new Date().toISOString().split('T')[0],
    notes: ''
  });
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchPayments();
    fetchBills();
  }, []);

  const fetchPayments = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/payments');
      setPayments(response.data);
    } catch (error) {
      console.error('Error fetching payments:', error);
    }
  };

  const fetchBills = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/bills');
      setBills(response.data);
    } catch (error) {
      console.error('Error fetching bills:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const bill = bills.find(b => b.id == form.billId);
      await axios.post('http://localhost:8081/api/payments', {
        ...form,
        bill,
        paymentDate: form.paymentDate || new Date().toISOString().split('T')[0]
      });
      fetchPayments();
      fetchBills(); // Refresh bills to update outstanding balances
      setForm({
        billId: '',
        amount: '',
        method: '',
        paymentDate: new Date().toISOString().split('T')[0],
        notes: ''
      });
    } catch (error) {
      console.error('Error recording payment:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredPayments = payments.filter(payment =>
    payment.bill?.customer?.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    payment.method?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    payment.bill?.id.toString().includes(searchTerm)
  );

  const selectedBill = bills.find(bill => bill.id == form.billId);

  return (
    <div className="payment">
      <div className="header">
        <h2>Payment Processing</h2>
        <Link to="/dashboard" className="back-btn">← Back to Dashboard</Link>
      </div>

      <div className="content">
        <div className="form-section">
          <h3>Record New Payment</h3>
          <form onSubmit={handleSubmit} className="payment-form">
            <div className="form-group">
              <label htmlFor="billId">Select Bill *</label>
              <select
                id="billId"
                value={form.billId}
                onChange={(e) => setForm({ ...form, billId: e.target.value })}
                required
              >
                <option value="">Select Bill</option>
                {bills
                  .filter(bill => bill.outstandingBalance > 0)
                  .map(bill => (
                    <option key={bill.id} value={bill.id}>
                      Bill #{bill.id} - {bill.customer?.name} - Outstanding: Rs{bill.outstandingBalance?.toFixed(2)}
                    </option>
                  ))}
              </select>
            </div>

            {selectedBill && (
              <div className="bill-info">
                <p><strong>Customer:</strong> {selectedBill.customer?.name}</p>
                <p><strong>Total Amount:</strong> Rs{selectedBill.amount?.toFixed(2)}</p>
                <p><strong>Outstanding:</strong> Rs{selectedBill.outstandingBalance?.toFixed(2)}</p>
              </div>
            )}

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="amount">Payment Amount *</label>
                <input
                  type="number"
                  id="amount"
                  placeholder="Enter amount"
                  value={form.amount}
                  onChange={(e) => setForm({ ...form, amount: e.target.value })}
                  min="0.01"
                  step="0.01"
                  max={selectedBill?.outstandingBalance || undefined}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="method">Payment Method *</label>
                <select
                  id="method"
                  value={form.method}
                  onChange={(e) => setForm({ ...form, method: e.target.value })}
                  required
                >
                  <option value="">Select Method</option>
                  <option value="Cash">Cash</option>
                  <option value="Card">Card</option>
                  <option value="Online">Online Banking</option>
                  <option value="Cheque">Cheque</option>
                </select>
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="paymentDate">Payment Date *</label>
                <input
                  type="date"
                  id="paymentDate"
                  value={form.paymentDate}
                  onChange={(e) => setForm({ ...form, paymentDate: e.target.value })}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="notes">Notes</label>
                <input
                  type="text"
                  id="notes"
                  placeholder="Optional notes"
                  value={form.notes}
                  onChange={(e) => setForm({ ...form, notes: e.target.value })}
                />
              </div>
            </div>

            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? 'Processing...' : 'Record Payment'}
            </button>
          </form>
        </div>

        <div className="table-section">
          <div className="table-header">
            <h3>Payment History</h3>
            <input
              type="text"
              placeholder="Search payments..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>

          <div className="table-container">
            <table className="payment-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Bill #</th>
                  <th>Customer</th>
                  <th>Amount</th>
                  <th>Method</th>
                  <th>Payment Date</th>
                  <th>Notes</th>
                </tr>
              </thead>
              <tbody>
                {filteredPayments.map(payment => (
                  <tr key={payment.id}>
                    <td>{payment.id}</td>
                    <td>{payment.bill?.id}</td>
                    <td>{payment.bill?.customer?.name}</td>
                    <td>Rs{payment.amount?.toFixed(2)}</td>
                    <td>{payment.method}</td>
                    <td>{payment.paymentDate ? new Date(payment.paymentDate).toLocaleDateString() : '-'}</td>
                    <td>{payment.notes || '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {filteredPayments.length === 0 && (
            <div className="no-data">No payments found</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Payment;