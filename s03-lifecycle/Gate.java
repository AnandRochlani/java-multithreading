long start;

void main() throws InterruptedException {
    start = System.currentTimeMillis();

    Thread meena = clerk("Meena", 900);
    Thread tom   = clerk("Tom",   300);
    Thread kavya = clerk("Kavya", 600);

    meena.join();
    IO.println("  gate: Meena signed out at " + since() + " ms");
    tom.join();
    IO.println("  gate: Tom signed out at " + since() + " ms");
    kavya.join();
    IO.println("  gate: Kavya signed out at " + since() + " ms");
}

Thread clerk(String name, long ms) {
    Thread t = new Thread(() -> {
        nap(ms);
        IO.println(name + " finished the work at " + since() + " ms");
    }, name);
    t.start();
    return t;
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
