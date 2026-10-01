void main() {
    int passengers = 5_000;                                   // rush hour at Riverside Junction
    int hired = 0;
    try {
        for (int p = 1; p <= passengers; p++) {
            new Thread(() -> nap(1_000), "clerk-" + p).start();   // one clerk per passenger, a second each
            hired++;
        }
        IO.println("hired " + hired + " clerks");
    } catch (OutOfMemoryError e) {
        IO.println("hired " + hired + " clerks, then passenger " + (hired + 1) + " got:");
        IO.println(e);
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
