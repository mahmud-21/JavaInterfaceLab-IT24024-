# Sample Input & Output

Both programs take no console/keyboard input — the "input" is the hardcoded object creation in each `main()` method. Below is that input alongside the exact console output it produces.

---

## Problem 1: Animal Shelter System (`AnimalShelter.java`)

### Input (hardcoded in `main`)

```java
Dog dog = new Dog("Buddy");
Cat cat = new Cat("Whiskers");
Bird bird = new Bird("Tweety");
```

### Output

```
--- Dog ---
Buddy is eating
Buddy says Woof!
Buddy is sleeping
Buddy is swimming

--- Cat ---
Whiskers is eating
Whiskers says Meow!
Whiskers is sleeping

--- Bird ---
Tweety is eating
Tweety says Tweet!
Tweety is sleeping
Tweety is flying
```

---

## Problem 2: Payment Processing System (`PaymentSystem.java`)

### Input (hardcoded in `main`)

```java
CreditCardPayment cc = new CreditCardPayment("41111111111111");
PayPalPayment pp = new PayPalPayment("user@example.com");
BankTransferPayment bt = new BankTransferPayment("12345678901");
CryptoPayment cp = new CryptoPayment("0xABC123");

// each is authorized and processed for $100.00
// then:
cc.refund(20.0);
pp.setUpRecurringBilling(9.99, 30);
cp.earnPoints(100.0);
```

### Output

```
Charged $100.0 to credit card. ID: TXN-1737200000001-1
Receipt[CreditCard, txn=TXN-1737200000001-1]
Charged $100.0 via PayPal. ID: TXN-1737200000002-2
Receipt[PayPal, txn=TXN-1737200000002-2]
Transferred $100.0 via bank transfer. ID: TXN-1737200000003-3
Receipt[BankTransfer, txn=TXN-1737200000003-3]
Sent $100.0 in crypto. ID: TXN-1737200000004-4
Receipt[Crypto, txn=TXN-1737200000004-4]
Refunded $20.0 to credit card.
Recurring $9.99 every 30 days via PayPal.
Crypto loyalty points: 25
```

> **Note:** the exact numbers inside each `TXN-...` ID will differ every time you run the program, since they're built from `System.currentTimeMillis()` plus an incrementing counter. The transaction *count* (1, 2, 3, 4) and everything else will always match.
