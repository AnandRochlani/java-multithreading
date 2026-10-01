void main() {
    long start = System.currentTimeMillis();

    sellTicket(3);
    brewChai();
    printReport();

    long elapsed = System.currentTimeMillis() - start;
    IO.println("station closed in " + elapsed + " ms");
}

void sellTicket(int n) {
    IO.println("[" + Thread.currentThread().getName() + "] selling " + n + " tickets");
    nap(2000);
    IO.println("sold " + n + " tickets");
}

void brewChai() {
    IO.println("[" + Thread.currentThread().getName() + "] brewing chai");
    nap(2000);
    IO.println("chai is ready");
}

void printReport() {
    IO.println("[" + Thread.currentThread().getName() + "] printing the report");
    nap(2000);
    IO.println("day report printed");
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
