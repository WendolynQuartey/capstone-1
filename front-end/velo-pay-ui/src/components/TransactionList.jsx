import './TransactionList.css';

const TransactionList = ({ transactions, allTransactions }) => {
  const totalDeposits = allTransactions
    .filter(t => t.amount > 0)
    .reduce((sum, t) => sum + t.amount, 0);

  const totalPayments = allTransactions
    .filter(t => t.amount < 0)
    .reduce((sum, t) => sum + Math.abs(t.amount), 0);

  const balance = totalDeposits - totalPayments;

  return (
    <div className="transaction-list">
      <div className="summary-cards">
        <div className="summary-card deposits">
          <span className="label">Total Deposits</span>
          <span className="value">${totalDeposits.toFixed(2)}</span>
        </div>
        <div className="summary-card payments">
          <span className="label">Total Payments</span>
          <span className="value">${totalPayments.toFixed(2)}</span>
        </div>
        <div className="summary-card balance">
          <span className="label">Balance</span>
          <span className={`value ${balance >= 0 ? 'positive' : 'negative'}`}>
            ${balance.toFixed(2)}
          </span>
        </div>
        <div className="summary-card count">
          <span className="label">Transactions</span>
          <span className="value">{transactions.length}</span>
        </div>
      </div>

      {transactions.length === 0 ? (
        <div className="empty-state">
          <div className="empty-icon">📭</div>
          <p>No transactions found</p>
          <p className="empty-subtext">Add a deposit or payment to get started</p>
        </div>
      ) : (
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Vendor</th>
                <th>Description</th>
                <th className="amount-column">Amount</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map((transaction) => (
                <tr key={transaction.id} className={transaction.amount > 0 ? 'deposit-row' : 'payment-row'}>
                  <td>{transaction.date}</td>
                  <td>{transaction.time}</td>
                  <td>{transaction.vendor}</td>
                  <td>{transaction.description}</td>
                  <td className={`amount-column ${transaction.amount > 0 ? 'positive-amount' : 'negative-amount'}`}>
                    {transaction.amount > 0 ? '+' : ''}{transaction.amount.toFixed(2)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default TransactionList;