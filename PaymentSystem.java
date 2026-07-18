import java.util.concurrent.atomic.AtomicLong;

// ---------- Abstract class: shared state + shared implementation + partial abstraction ----------
abstract class PaymentMethod {

    // Shared counter used by every payment type for transaction IDs.
    private static final AtomicLong counter = new AtomicLong(0);

    protected String transactionId;

    // Identical for every payment method -> written once, here.
    protected String generateTransactionId() {
        long seq = counter.incrementAndGet();
        return "TXN-" + System.currentTimeMillis() + "-" + seq;
    }
    protected boolean validateAccountDetails(String accountOrRoutingNumber) {
        return accountOrRoutingNumber != null
                && accountOrRoutingNumber.matches("\\d{8,17}");
    }

    // Each payment type differs -> no shared implementation possible.
    abstract boolean authorize(double amount);
    abstract void processPayment(double amount);
    abstract String generateReceipt();
}

// ---------- Interfaces: optional, mixable capabilities ----------
interface Refundable {
    void refund(double amount);
}

interface RecurringBillable {
    void setUpRecurringBilling(double amount, int intervalDays);
}

interface LoyaltyPointsEarner {
    int earnPoints(double amount);
}

// ---------- Concrete classes ----------
class CreditCardPayment extends PaymentMethod
        implements Refundable, RecurringBillable, LoyaltyPointsEarner {

    private String cardNumber;

    CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    boolean authorize(double amount) {
        return validateAccountDetails(cardNumber) && amount > 0;
    }

    @Override
    void processPayment(double amount) {
        transactionId = generateTransactionId();
        System.out.println("Charged $" + amount + " to credit card. ID: " + transactionId);
    }

    @Override
    String generateReceipt() {
        return "Receipt[CreditCard, txn=" + transactionId + "]";
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunded $" + amount + " to credit card.");
    }

    @Override
    public void setUpRecurringBilling(double amount, int intervalDays) {
        System.out.println("Recurring $" + amount + " every " + intervalDays + " days on card.");
    }

    @Override
    public int earnPoints(double amount) {
        return (int) (amount); // 1 point per dollar
    }
}

class PayPalPayment extends PaymentMethod
        implements Refundable, RecurringBillable, LoyaltyPointsEarner {

    private String email;

    PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    boolean authorize(double amount) {
        return email != null && email.contains("@") && amount > 0;
    }

    @Override
    void processPayment(double amount) {
        transactionId = generateTransactionId();
        System.out.println("Charged $" + amount + " via PayPal. ID: " + transactionId);
    }

    @Override
    String generateReceipt() {
        return "Receipt[PayPal, txn=" + transactionId + "]";
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunded $" + amount + " via PayPal.");
    }

    @Override
    public void setUpRecurringBilling(double amount, int intervalDays) {
        System.out.println("Recurring $" + amount + " every " + intervalDays + " days via PayPal.");
    }

    @Override
    public int earnPoints(double amount) {
        return (int) (amount * 0.5); // 0.5 point per dollar
    }
}

class BankTransferPayment extends PaymentMethod {

    private String routingNumber;

    BankTransferPayment(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    @Override
    boolean authorize(double amount) {
        return validateAccountDetails(routingNumber) && amount > 0;
    }

    @Override
    void processPayment(double amount) {
        transactionId = generateTransactionId();
        System.out.println("Transferred $" + amount + " via bank transfer. ID: " + transactionId);
    }

    @Override
    String generateReceipt() {
        return "Receipt[BankTransfer, txn=" + transactionId + "]";
    }
    // No Refundable, no RecurringBillable, no LoyaltyPointsEarner.
}

class CryptoPayment extends PaymentMethod implements LoyaltyPointsEarner {

    private String walletAddress;

    CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    boolean authorize(double amount) {
        return walletAddress != null && !walletAddress.isEmpty() && amount > 0;
    }

    @Override
    void processPayment(double amount) {
        transactionId = generateTransactionId();
        System.out.println("Sent $" + amount + " in crypto. ID: " + transactionId);
    }

    @Override
    String generateReceipt() {
        return "Receipt[Crypto, txn=" + transactionId + "]";
    }

    @Override
    public int earnPoints(double amount) {
        return (int) (amount * 0.25);
    }
    // Deliberately NOT Refundable (irreversible by design) and NOT RecurringBillable.
}

// ---------- Demo ----------
public class PaymentSystem {
    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("41111111111111");
        PayPalPayment pp = new PayPalPayment("user@example.com");
        BankTransferPayment bt = new BankTransferPayment("12345678901");
        CryptoPayment cp = new CryptoPayment("0xABC123");

        PaymentMethod[] all = { cc, pp, bt, cp };
        for (PaymentMethod pm : all) {
            if (pm.authorize(100.0)) {
                pm.processPayment(100.0);
                System.out.println(pm.generateReceipt());
            }
        }

        cc.refund(20.0);
        pp.setUpRecurringBilling(9.99, 30);
        System.out.println("Crypto loyalty points: " + cp.earnPoints(100.0));
    }
}
