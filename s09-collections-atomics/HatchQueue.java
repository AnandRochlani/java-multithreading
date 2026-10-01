static final BlockingQueue<String> tray = new ArrayBlockingQueue<>(3);   // the hatch's tray: room for three slips
static final int SLIPS = 6;

void main() throws InterruptedException {
    Thread ravi = new Thread(() -> {
        try {
            for (int n = 1; n <= SLIPS; n++) {
                nap(50);                                          // printing takes 50 ms
                IO.println("printer: printed slip #" + n);
                long start = System.nanoTime();
                tray.put("slip #" + n);                           // waits here while the tray is full
                long waited = (System.nanoTime() - start) / 1_000_000;
                if (waited >= 20) IO.println("printer: the tray was full, so put waited " + waited + " ms");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "printer");
    Thread meena = new Thread(() -> {
        try {
            for (int n = 1; n <= SLIPS; n++) {
                long start = System.nanoTime();
                String slip = tray.take();                        // waits here while the tray is empty
                long waited = (System.nanoTime() - start) / 1_000_000;
                IO.println("Meena: took " + slip + (waited >= 20 ? ", after take waited " + waited + " ms at an empty tray" : ""));
                nap(150);                                         // serving takes 150 ms
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "Meena");

    ravi.start();
    meena.start();
    ravi.join();
    meena.join();
    IO.println("shutters down, tray = " + tray);
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
