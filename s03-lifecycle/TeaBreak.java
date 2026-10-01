void main() throws InterruptedException {
    long start = System.currentTimeMillis();

    Thread meena = new Thread(() -> {
        try {
            Thread.sleep(5000);                                   // a five-second chai
            IO.println("Meena: back at the window");
        } catch (InterruptedException e) {
            IO.println("Meena caught: " + e);
            IO.println("message     : " + e.getMessage());
            IO.println("flag now    : " + Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            IO.println("flag put back: " + Thread.currentThread().isInterrupted());
        }
    }, "Meena");

    meena.start();
    Thread.sleep(500);
    meena.interrupt();                                            // a tap on the shoulder
    meena.join();

    IO.println("shift ended after " + (System.currentTimeMillis() - start) + " ms, Meena is " + meena.getState());
}
