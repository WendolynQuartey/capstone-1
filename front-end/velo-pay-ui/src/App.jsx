import React, { useState, useEffect } from 'react';
import './App.css';
import { getTransactions, addTransaction, searchTransactions } from './services/api';
import TransactionForm from './components/TransactionForm';
import TransactionList from './components/TransactionList';
import ReportFilters from './components/ReportFilters';

function App() {
  const [transactions, setTransactions] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [formType, setFormType] = useState('deposit');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    loadTransactions();
  }, []);

// comment
  const loadTransactions = async () => {
    setLoading(true);
    try {
      const data = await getTransactions();
      setTransactions(data);
      setError('');
    } catch (err) {
      setError('Failed to load transactions. Please make sure the backend is running.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddTransaction = async (transactionData) => {
    setLoading(true);
    try {
      const newTransaction = await addTransaction(transactionData);
      setTransactions([newTransaction, ...transactions]);
      setShowForm(false);
      setError('');
    } catch (err) {
      setError('Failed to add transaction.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (searchParams) => {
    setLoading(true);
    try {
      const data = await searchTransactions(searchParams);
      setTransactions(data);
      setError('');
    } catch (err) {
      setError('Search failed.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    loadTransactions();
  };

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-left">
          <span className="logo">💰</span>
          <h1>Ledger App</h1>
        </div>
        <div className="header-actions">
          <button
            className="btn btn-primary"
            onClick={() => { setFormType('deposit'); setShowForm(true); }}
          >
            + Add Deposit
          </button>
          <button
            className="btn btn-secondary"
            onClick={() => { setFormType('payment'); setShowForm(true); }}
          >
            - Add Payment
          </button>
        </div>
      </header>

      <main className="app-main">
        {error && <div className="error-banner">{error}</div>}

        <ReportFilters onSearch={handleSearch} onReset={handleReset} />

        {showForm && (
          <TransactionForm
            type={formType}
            onSubmit={handleAddTransaction}
            onCancel={() => setShowForm(false)}
          />
        )}

        {loading ? (
          <div className="loading-state">
            <div className="spinner"></div>
            <p>Loading transactions...</p>
          </div>
        ) : (
          <TransactionList transactions={transactions} />
        )}
      </main>
    </div>
  );
}

export default App;
