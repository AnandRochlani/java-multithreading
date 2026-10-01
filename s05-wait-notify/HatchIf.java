static final Queue<String> tray = new ArrayDeque<>();
static final int CAPACITY = 3;
static final int SLIPS = 6;

static void print(int n) throws InterruptedException {       // Ravi's side of the hatch
    synchronized (tray) {
        while (tray.size() == CAPACITY) {
            IO.println("printer: tray full, waiting");
            tray.wait();
        }
        tray.add("slip #" + n);
        IO.println("printer: printed slip #" + n + "   (tray " + tray.size() + ")");
        tray.notifyAll();
    }
}

static String collect() throws InterruptedException {        // the clerk's side of the hatch
    synchronized (tray) {
        if (tray.isEmpty()) {                                // if, not while
            IO.println(Thread.currentThread().getName() + ": tray empty, waiting");
            tray.wait();
        }
        String slip = tray.remove();
        IO.println(Thread.currentThread().getName() + ": took " + slip + "   (tray " + tray.size() + ")");
        tray.notifyAll();
        return slip;
    }
}

void main() throws InterruptedException {
    Thread ravi = new Thread(() -> {
        try {
            for (int n = 1; n <= SLIPS; n++) {
                nap(50);                                      // printing takes 50 ms
                print(n);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "printer");
    Runnable clerk = () -> {
        try {
            for (int n = 1; n <= SLIPS / 2; n++) {           // two clerks, three slips each
                collect();
                nap(150);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };
    Thread meena = new Thread(clerk, "Meena");
    Thread tom   = new Thread(clerk, "Tom");

    meena.start();
    tom.start();
    ravi.start();
    ravi.join();
    meena.join();
    tom.join();
    IO.println("shutters down, tray = " + tray);
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
