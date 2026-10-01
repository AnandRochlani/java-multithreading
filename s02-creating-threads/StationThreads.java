long start;

void main() {
    start = System.currentTimeMillis();

    new Thread(() -> sellTicket(3)).start();
    new Thread(() -> brewChai()).start();
    new Thread(() -> printReport()).start();
}

void sellTicket(int n) {
    nap(2000);
    IO.println("sold " + n + " tickets     at " + since() + " ms");
}

void brewChai() {
    nap(2000);
    IO.println("chai is ready      at " + since() + " ms");
}

void printReport() {
    nap(2000);
    IO.println("day report printed at " + since() + " ms");
}

long since() {
    return System.currentTimeMillis() - start;
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
