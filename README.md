# Ledger App
> A command-line ledger application that tracks user deposits and payments,
generates financial reports, and persists data to CSV files.

## Methods
| Method Name              | Return Type   | Parameters                       | Description                                                                                                                                         |
|--------------------------|---------------|----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `main`                   | `void`        | `String[] args`                  | Application entry point that calls `homeScreen()`                                                                                                   |
| `homeScreen`             | `void`        | None                             | Displays main menu and routes user to deposits, payments, ledger, or exit                                                                           |
| `addDeposit`             | `void`        | None                             | Prompts user for deposit details (vendor, description, date, time, amount) and appends positive transaction to CSV file                             |
| `makePayment`            | `void`        | None                             | Prompts user for payment details (vendor, description, date, time, amount) and appends negative transaction to CSV file                             |
| `displayLedger`          | `void`        | None                             | Reads all transactions from CSV, displays ledger menu, and shows filtered entries (all, deposits only, payments only, reports, or back to home)     |
| `displayReports`         | `void`        | None                             | Shows pre-defined report menu with date-range search filters: month-to-date, previous month, year-to-date, previous year, search by vendor, or back |

## Transaction Class References
This class uses a `Transaction` object with the following methods:
- `displayTransaction()` - Returns formatted transaction string for CSV/file output
- `getAmount()`, `getDate()`, `getTime()`, `getVendor()` - Getter methods used for filtering and sorting

## Key Constants
| Constant               | Type                     | Value                                 |
|------------------------|--------------------------|---------------------------------------|
| `scanner`              | `Scanner`                | System.in scanner for user input      |
| `transactions`         | `ArrayList<Transaction>` | List of transactions                  |
| `FILE_NAME`            | `String`                 | "src/main/resources/transactions.csv" |
| `INPUT_TIME_FORMATTER` | `DateTimeFormatter`      | Pattern "H:mm" for time parsing       |