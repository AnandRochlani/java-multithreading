static volatile long meenaIn, tomIn;
void main() throws InterruptedException {
    long t0 = System.nanoTime();
    Thread meena = new Thread(() -> meenaIn = System.nanoTime(), "Meena");
    Thread tom   = new Thread(() -> tomIn = System.nanoTime(), "Tom");
    meena.start();
    tom.start();
    meena.join(); tom.join();
    IO.println("Meena running after " + (meenaIn - t0) / 1000 + " us, Tom after " + (tomIn - t0) / 1000 + " us");
}
