import { useState, useEffect } from 'react';
import './App.css';
import logo from './assets/logo.png';
import { getTransactions, addTransaction, searchTransactions } from './services/api';
import TransactionForm from './components/TransactionForm';
import TransactionList from './components/TransactionList';
import ReportFilters from './components/ReportFilters';
import Login from './components/Login';

function App() {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem('user');
    return stored ? JSON.parse(stored) : null;
  });
  const [transactions, setTransactions] = useState([]);
  const [displayedTransactions, setDisplayedTransactions] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [formType, setFormType] = useState('deposit');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const loadTransactions = async () => {
    setLoading(true);
    try {
      const data = await getTransactions(user.userId);
      setTransactions(data);
      setDisplayedTransactions(data);
      setError('');
    } catch (err) {
      setError('Failed to load transactions. Please make sure the backend is running.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user) {
      loadTransactions();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user]);

  const handleLoginSuccess = (loggedInUser) => {
    localStorage.setItem('user', JSON.stringify(loggedInUser));
    setUser(loggedInUser);
  };

  const handleLogout = () => {
    localStorage.removeItem('user');
    setUser(null);
    setTransactions([]);
    setDisplayedTransactions([]);
  };

  const handleAddTransaction = async ({ type, ...transactionData }) => {
    setLoading(true);
    try {
      const signedAmount = type === 'payment' ? -Math.abs(transactionData.amount) : transactionData.amount;
      const newTransaction = await addTransaction({
        ...transactionData,
        amount: signedAmount,
        userId: user.userId,
      });
      setTransactions([newTransaction, ...transactions]);
      setDisplayedTransactions([newTransaction, ...displayedTransactions]);
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
      const data = await searchTransactions(user.userId, searchParams);
      setDisplayedTransactions(data);
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

  if (!user) {
    return <Login onLoginSuccess={handleLoginSuccess} />;
  }

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-left">
          <img src={logo} alt="Velo Pay" className="logo" />
          <h1>Velo Pay</h1>
        </div>
        <div className="header-actions">
          <span className="welcome-text">Hi, {user.userName}</span>
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
          <button className="btn btn-secondary" onClick={handleLogout}>
            Log out
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
          <TransactionList transactions={displayedTransactions} allTransactions={transactions} />
        )}
      </main>
    </div>
  );
}

export default App;
