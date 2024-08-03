import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private int accountNumber;
    private int balance;
    private int pin;
    private List<String> transactionHistory;

    public Account(int accountNumber, int initialBalance, int pin) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public boolean checkPin(int inputPin) {
        return this.pin == inputPin;
    }

    public void checkBalance(String language, String[] currentBalanceMsg) {
        System.out.println(currentBalanceMsg[getLanguageIndex(language)] + balance);
        transactionHistory.add("Checked balance: Rs " + balance);
    }

    public void deposit(int amount, String language, String[] depositMsg, String[] invalidAmountMsg) {
        if (amount > 0) {
            balance += amount;
            System.out.println(depositMsg[getLanguageIndex(language)] + amount);
            transactionHistory.add("Deposited: Rs " + amount);
            checkBalance(language, depositMsg);
        } else {
            System.out.println(invalidAmountMsg[getLanguageIndex(language)]);
        }
    }

    public void withdraw(int amount, String language, String[] withdrawMsg, String[] insufficientBalanceMsg, String[] invalidAmountMsg) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(withdrawMsg[getLanguageIndex(language)] + amount);
            transactionHistory.add("Withdrew: Rs " + amount);
            checkBalance(language, withdrawMsg);
        } else if (amount > balance) {
            System.out.println(insufficientBalanceMsg[getLanguageIndex(language)]);
        } else {
            System.out.println(invalidAmountMsg[getLanguageIndex(language)]);
        }
    }

    public void changePin(Scanner sc, String language, String[] enterCurrentPinMsg, String[] enterNewPinMsg, String[] confirmNewPinMsg, String[] pinChangedMsg, String[] pinChangeFailedMsg, String[] incorrectPinMsg) {
        System.out.print(enterCurrentPinMsg[getLanguageIndex(language)]);
        int currentPin = sc.nextInt();
        if (checkPin(currentPin)) {
            System.out.print(enterNewPinMsg[getLanguageIndex(language)]);
            int newPin = sc.nextInt();
            System.out.print(confirmNewPinMsg[getLanguageIndex(language)]);
            int confirmPin = sc.nextInt();
            if (newPin == confirmPin) {
                this.pin = newPin;
                System.out.println(pinChangedMsg[getLanguageIndex(language)]);
                transactionHistory.add("Changed PIN");
            } else {
                System.out.println(pinChangeFailedMsg[getLanguageIndex(language)]);
            }
        } else {
            System.out.println(incorrectPinMsg[getLanguageIndex(language)]);
        }
    }

    public void viewTransactionHistory(String language, String[] transactionHistoryMsg) {
        System.out.println(transactionHistoryMsg[getLanguageIndex(language)]);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    private int getLanguageIndex(String language) {
        switch (language) {
            case "मराठी":
                return 1;
            case "हिंदी":
                return 2;
            default:
                return 0;
        }
    }
}

public class ATM {
    private List<Account> accounts;
    private String language;

    private final String[] languages = {"English", "मराठी", "हिंदी"};
    private final String[] welcomeMessage = {"Welcome to the ATM", "ATM मध्ये आपले स्वागत आहे", "ATM में आपका स्वागत है"};
    private final String[] options = {
            "1. Balance Enquiry\n2. Cash Withdrawal\n3. Cash Deposit\n4. Change PIN\n5. View Transaction History\n6. Exit",
            "1. बॅलन्स चौकशी\n2. रोख पैसे काढणे\n3. रोख ठेव\n4. पिन बदला\n5. व्यवहाराचा इतिहास पहा\n6. बाहेर पडा",
            "1. शेष राशि पूछताछ\n2. नकद निकासी\n3. नकद जमा\n4. पिन बदलें\n5. लेनदेन इतिहास देखें\n6. बाहर निकलें"
    };
    private final String[] chooseOption = {"Choose an option: ", "एक पर्याय निवडा: ", "एक विकल्प चुनें: "};
    private final String[] enterAmountToWithdraw = {"Enter amount to withdraw: ", "काढायची रक्कम प्रविष्ट करा: ", "निकालने की राशि दर्ज करें: "};
    private final String[] enterAmountToDeposit = {"Enter amount to deposit: ", "ठेवायची रक्कम प्रविष्ट करा: ", "जमा करने की राशि दर्ज करें: "};
    private final String[] enterYourPin = {"Enter your PIN: ", "आपला पिन प्रविष्ट करा: ", "अपना पिन दर्ज करें: "};
    private final String[] incorrectPin = {"Incorrect PIN. Access denied.", "चुकीचा पिन. प्रवेश नाकारला.", "ग़लत पिन। प्रवेश अस्वीकृत।"};
    private final String[] currentBalance = {"Your balance is Rs ", "आपला शिल्लक Rs आहे ", "आपका बैलेंस Rs है "};
    private final String[] deposited = {"Successfully deposited Rs ", "यशस्वीरित्या जमा Rs ", "Rs सफलतापूर्वक जमा किया गया "};
    private final String[] withdrew = {"Successfully withdrew Rs ", "यशस्वीरित्या काढले Rs ", "Rs सफलतापूर्वक निकाला गया "};
    private final String[] insufficientBalance = {"Insufficient balance. Unable to withdraw.", "अपुरा शिल्लक. काढता येणार नाही.", "अपर्याप्त शेष राशि। निकालने में असमर्थ।"};
    private final String[] invalidAmount = {"Invalid amount.", "अवैध रक्कम.", "अमान्य राशि।"};
    private final String[] enterCurrentPin = {"Enter your current PIN: ", "आपला वर्तमान पिन प्रविष्ट करा: ", "अपना वर्तमान पिन दर्ज करें: "};
    private final String[] enterNewPin = {"Enter your new PIN: ", "आपला नवीन पिन प्रविष्ट करा: ", "अपना नया पिन दर्ज करें: "};
    private final String[] confirmNewPin = {"Confirm your new PIN: ", "आपला नवीन पिन पुष्टी करा: ", "अपना नया पिन कन्फर्म करें: "};
    private final String[] pinChanged = {"PIN successfully changed.", "पिन यशस्वीरित्या बदलले.", "पिन सफलतापूर्वक बदल गया।"};
    private final String[] pinChangeFailed = {"PIN change failed.", "पिन बदलणे अयशस्वी झाले.", "पिन बदलने में विफल।"};
    private final String[] transactionHistoryMsg = {"Transaction History:", "व्यवहार इतिहास:", "लेन-देन इतिहास:"};
    private final String[] thankYou = {"Thank you for using the ATM. Goodbye!", "ATM वापरल्याबद्दल धन्यवाद. अलविदा!", "ATM का उपयोग करने के लिए धन्यवाद। अलविदा!"};
    private final String[] invalidOption = {"Invalid option. Please try again.", "अवैध पर्याय. कृपया पुन्हा प्रयत्न करा.", "अमान्य विकल्प। कृपया पुन: प्रयास करें।"};

    public ATM() {
        accounts = new ArrayList<>();
        language = "English"; // Default language
        initializeAccounts();
    }

    private void initializeAccounts() {
        accounts.add(new Account(1, 1000, 1234)); // Account 1 with initial balance and PIN
        accounts.add(new Account(2, 2000, 2345)); // Account 2 with initial balance and PIN
        accounts.add(new Account(3, 3000, 3456)); // Account 3 with initial balance and PIN
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        selectLanguage(sc);

        Account selectedAccount = selectAccount(sc);

        if (selectedAccount == null) {
            System.out.println("Invalid account number. Exiting...");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println(welcomeMessage[getLanguageIndex()]);
            System.out.println(options[getLanguageIndex()]);
            System.out.print(chooseOption[getLanguageIndex()]);

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    if (requestPin(sc, selectedAccount)) {
                        selectedAccount.checkBalance(language, currentBalance);
                    }
                    break;
                case 2:
                    System.out.print(enterAmountToWithdraw[getLanguageIndex()]);
                    int withdrawAmount = sc.nextInt();
                    if (requestPin(sc, selectedAccount)) {
                        selectedAccount.withdraw(withdrawAmount, language, withdrew, insufficientBalance, invalidAmount);
                    }
                    break;
                case 3:
                    System.out.print(enterAmountToDeposit[getLanguageIndex()]);
                    int depositAmount = sc.nextInt();
                    selectedAccount.deposit(depositAmount, language, deposited, invalidAmount);
                    break;
                case 4:
                    selectedAccount.changePin(sc, language, enterCurrentPin, enterNewPin, confirmNewPin, pinChanged, pinChangeFailed, incorrectPin);
                    break;
                case 5:
                    selectedAccount.viewTransactionHistory(language, transactionHistoryMsg);
                    break;
                case 6:
                    System.out.println(thankYou[getLanguageIndex()]);
                    running = false;
                    break;
                default:
                    System.out.println(invalidOption[getLanguageIndex()]);
            }
            System.out.println(); // Add a new line for better readability
        }

        sc.close();
    }

    private boolean requestPin(Scanner sc, Account account) {
        System.out.print(enterYourPin[getLanguageIndex()]);
        int inputPin = sc.nextInt();
        if (account.checkPin(inputPin)) {
            return true;
        } else {
            System.out.println(incorrectPin[getLanguageIndex()]);
            return false;
        }
    }

    private void selectLanguage(Scanner sc) {
        System.out.println("Select Language / भाषा निवडा / भाषा चुनें:");
        for (int i = 0; i < languages.length; i++) {
            System.out.println((i + 1) + ". " + languages[i]);
        }
        int langChoice = sc.nextInt();
        if (langChoice >= 1 && langChoice <= languages.length) {
            language = languages[langChoice - 1];
        } else {
            System.out.println("Invalid selection. Defaulting to English.");
        }
    }

    private Account selectAccount(Scanner sc) {
        System.out.println("Select Account Number:");
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
        }
        int accountNumber = sc.nextInt();
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private int getLanguageIndex() {
        switch (language) {
            case "मराठी":
                return 1;
            case "हिंदी":
                return 2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
