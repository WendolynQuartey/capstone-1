import React, { useState } from 'react';
import './ReportFilters.css';

const ReportFilters = ({ onSearch, onReset }) => {
  const [filters, setFilters] = useState({
    vendor: '',
    startDate: '',
    endDate: '',
    type: 'all',
  });

  const handleChange = (e) => {
    setFilters({
      ...filters,
      [e.target.name]: e.target.value,
    });
  };

  const handleSearch = () => {
    onSearch(filters);
  };

  const handleReset = () => {
    setFilters({
      vendor: '',
      startDate: '',
      endDate: '',
      type: 'all',
    });
    onReset();
  };

  const handleQuickReport = (reportType) => {
    onSearch({ reportType });
  };

  return (
    <div className="report-filters">
      <div className="quick-reports">
        <span className="quick-label">Quick Reports:</span>
        <button className="quick-btn" onClick={() => handleQuickReport('month-to-date')}>
          Month to Date
        </button>
        <button className="quick-btn" onClick={() => handleQuickReport('previous-month')}>
          Previous Month
        </button>
        <button className="quick-btn" onClick={() => handleQuickReport('year-to-date')}>
          Year to Date
        </button>
        <button className="quick-btn" onClick={() => handleQuickReport('previous-year')}>
          Previous Year
        </button>
      </div>

      <div className="filter-controls">
        <div className="filter-group">
          <input
            type="text"
            name="vendor"
            placeholder="🔍 Search by vendor..."
            value={filters.vendor}
            onChange={handleChange}
            className="filter-input"
          />
        </div>

        <div className="filter-group date-group">
          <input
            type="date"
            name="startDate"
            value={filters.startDate}
            onChange={handleChange}
            className="filter-input"
          />
          <span className="filter-label">to</span>
          <input
            type="date"
            name="endDate"
            value={filters.endDate}
            onChange={handleChange}
            className="filter-input"
          />
        </div>

        <div className="filter-group">
          <select name="type" value={filters.type} onChange={handleChange} className="filter-select">
            <option value="all">All Transactions</option>
            <option value="deposits">Deposits Only</option>
            <option value="payments">Payments Only</option>
          </select>
        </div>

        <div className="filter-actions">
          <button className="btn-filter" onClick={handleSearch}>
            Apply Filters
          </button>
          <button className="btn-reset" onClick={handleReset}>
            Reset
          </button>
        </div>
      </div>
    </div>
  );
};

export default ReportFilters;