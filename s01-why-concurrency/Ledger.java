sealed interface Payment permits Phone, Card, Cash { long dollars(); }
record Phone(String handle, long dollars) implements Payment {}
record Card(String bank, String last4, long dollars) implements Payment {}
record Cash(long dollars) implements Payment {}

void main() {
    long start = System.currentTimeMillis();

    List<Payment> day = List.of(
            new Phone("priya", 250),
            new Card("BANK A", "4321", 1299),
            new Cash(150),
            new Phone("daniel", 49),
            new Card("BANK B", "1111", 2500));

    long total = 0;
    double fees = 0;
    for (Payment p : day) {
        double fee = fee(p);
        total += p.dollars();
        fees += fee;
        IO.println("%-30s fee $%6.2f".formatted(label(p), fee));
    }
    IO.println("%-30s    $%7.2f".formatted("TOTAL", (double) total));
    IO.println("%-30s    $%7.2f".formatted("FEES", fees));

    long elapsed = System.currentTimeMillis() - start;
    IO.println();
    IO.println("elapsed " + elapsed + " ms");
}

double fee(Payment p) {
    return switch (p) {
        case Phone f -> 0;
        case Card c  -> askTheBank(c);
        case Cash c  -> 0;
    };
}

// A real payment system asks the card network. We stand in for that round trip.
double askTheBank(Card c) {
    try {
        Thread.sleep(800);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    return c.dollars() * 0.02;
}

String label(Payment p) {
    return switch (p) {
        case Phone f -> "PHONE " + f.handle() + "  $" + f.dollars();
        case Card c  -> "CARD " + c.bank() + " ****" + c.last4() + "  $" + c.dollars();
        case Cash c  -> "CASH  $" + c.dollars();
    };
}
