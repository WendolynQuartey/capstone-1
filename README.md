# Ledger App

## Description
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

## Code I'm Most Proud of
```
switch (reportChoice) {
                case 1:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int thisMonth = today.getMonthValue();
                        int thisYear = today.getYear();
                        if (t.getDate().getMonthValue() == thisMonth && t.getDate().getYear() == thisYear) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 2:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int lastMonth = today.getMonthValue() - 1;
                        int thisYear = today.getYear();
                        if (t.getDate().getMonthValue() == lastMonth && t.getDate().getYear() == thisYear ) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 3:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int thisYear = today.getYear();
                        if (t.getDate().getYear() == thisYear && t.getDate().isBefore(today)) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 4:
                    for (Transaction t : transactions) {
                        LocalDate today = LocalDate.now();
                        int lastYear = today.getYear() - 1;
                        if (t.getDate().getYear() == lastYear) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nWhich vendor would you like to search? ");
                    String searchVendor = scanner.nextLine();

                    for (Transaction t : transactions) {
                        if (t.getVendor().equalsIgnoreCase(searchVendor)) {
                            System.out.println(t.displayTransaction());
                        }
                    }
                    break;
                case 6:
                    customSearch();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("This is not an option");
            }
        } while (running);
    }
 ```


## Running the Code
After cloning the repository, simply open the project in IntelliJ IDEA and run the `BankApp` class to start the application.

## Personal Challenges
One of the biggest challenges I faced was implementing the logic for the bonus custom search feature and the pre-defined search reports. Initially, I approached the problem in a way that made the solution more complex than necessary. After a code review with my instructor, Dave, I gained a better understanding of how to simplify the logic and break the problem into smaller, more manageable pieces. This experience helped me improve my problem-solving skills and reinforced the importance of keeping solutions as simple as possible.

## Next Time...
For future projects, I would like to spend more time enhancing the user interface to better showcase my creativity. While the application is functional and straightforward, I feel it could benefit from a more engaging and visually appealing design. I would also dedicate additional time to the bonus search feature, as I believe I was very close to completing it successfully. With a bit more time and experimentation, I am confident I could have fully implemented the functionality.
