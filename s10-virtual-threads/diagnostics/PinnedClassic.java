// Pinned.java in classic syntax (a named class, System.out), so the SAME program runs on JDK 21 and JDK 27
import java.util.concurrent.*;

public class PinnedClassic {
    public static void main(String[] args) {
        long start = System.nanoTime();
        try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                Object key = new Object();
                desk.execute(() -> {
                    synchronized (key) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
            }
        }
        System.out.println("Java " + Runtime.version().feature() + ": 1000 naps of 100 ms inside synchronized took "
                + (System.nanoTime() - start) / 1_000_000 + " ms");
    }
}
