package com.pluralsight.model;

public class MenuStrings {
    public static final String HOME_MENU = """
                                    ╔═══════════════════╗
                                    ║  \u001B[1;33m==== HOME ====\u001B[0m   ║
                                    ╠═══════════════════╣
                                    ║ \u001B[32m♦ D\u001B[0m  Add Deposit  ║
                                    ║ \u001B[31m♦ P\u001B[0m  Make Payment ║
                                    ║ \u001B[34m♦ L\u001B[0m  Ledger       ║
                                    ║ \u001B[35m♦ X\u001B[0m  Exit         ║
                                    ╚═══════════════════╝
                                    \u001B[36m?\u001B[0m What would you like to do: \s
            
""";
    public static final String  LEDGER_MENU = """
                                    ╔═══════════════════╗
                                    ║ \u001B[1;33m==== LEDGER ====\u001B[0m  ║
                                    ╠═══════════════════╣
                                    ║ \u001B[32m♦ A\u001B[0m  All Entries  ║
                                    ║ \u001B[31m♦ D\u001B[0m  Deposits     ║
                                    ║ \u001B[34m♦ P\u001B[0m  Payments     ║
                                    ║ \u001B[35m♦ R\u001B[0m  Reports      ║
                                    ║ \u001B[36m♦ H\u001B[0m  Home         ║
                                    ╚═══════════════════╝
                                    \u001B[36m?\u001B[0m What would you like to do: \s
            
            """;
    public static final String REPORTS_MENU = """
                                    ╔═════════════════════════╗
                                    ║     \u001B[1;33m==== REPORTS ====\u001B[0m   ║
                                    ╠═════════════════════════╣
                                    ║ \u001B[32m♦ 1\u001B[0m  Month To Date      ║
                                    ║ \u001B[31m♦ 2\u001B[0m  Previous Month     ║
                                    ║ \u001B[34m♦ 3\u001B[0m  Year To Date       ║
                                    ║ \u001B[35m♦ 4\u001B[0m  Previous Year      ║
                                    ║ \u001B[36m♦ 5\u001B[0m  Search by Vendor   ║
                                    ║ \u001B[36m♦ 6\u001B[0m  Custom Search      ║
                                    ║ \u001B[37m♦ 0\u001B[0m  Back               ║
                                    ╚═════════════════════════╝
                                    \u001B[36m?\u001B[0m What would you like to do: \s
""";
}
