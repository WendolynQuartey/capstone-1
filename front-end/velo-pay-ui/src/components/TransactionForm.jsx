import React, { useState } from 'react';
import './TransactionForm.css';

const TransactionForm = ({ type, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    vendor: '',
    description: '',
    date: new Date().toISOString().split('T')[0],
    time: new Date().toTimeString().slice(0, 5),
    amount: '',
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const amount = parseFloat(formData.amount);
    if (isNaN(amount) || amount <= 0) {
      alert('Please enter a valid positive amount');
      return;
    }
    onSubmit({
      ...formData,
      amount: amount,
      type: type,
    });
  };

  return (
    <div className="form-overlay" onClick={onCancel}>
      <form className="transaction-form" onSubmit={handleSubmit} onClick={(e) => e.stopPropagation()}>
        <div className="form-header">
          <h2>{type === 'deposit' ? '💰 Add Deposit' : '💸 Add Payment'}</h2>
          <button type="button" className="close-btn" onClick={onCancel}>×</button>
        </div>

        <div className="form-group">
          <label>Vendor *</label>
          <input
            type="text"
            name="vendor"
            value={formData.vendor}
            onChange={handleChange}
            placeholder="Enter vendor name"
            required
          />
        </div>

        <div className="form-group">
          <label>Description *</label>
          <input
            type="text"
            name="description"
            value={formData.description}
            onChange={handleChange}
            placeholder="Enter description"
            required
          />
        </div>

        <div className="form-row">
          <div className="form-group">
            <label>Date *</label>
            <input
              type="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Time *</label>
            <input
              type="time"
              name="time"
              value={formData.time}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="form-group">
          <label>Amount ($) *</label>
          <input
            type="number"
            name="amount"
            value={formData.amount}
            onChange={handleChange}
            placeholder="0.00"
            step="0.01"
            min="0.01"
            required
          />
        </div>

        <div className="form-actions">
          <button type="submit" className={`btn-submit ${type}`}>
            {type === 'deposit' ? 'Add Deposit' : 'Add Payment'}
          </button>
          <button type="button" className="btn-cancel" onClick={onCancel}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default TransactionForm;