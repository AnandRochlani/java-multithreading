// What does a sleeping thread cost? Usage: java ThreadCost.java virtual 1000000   or   java ThreadCost.java platform 4000
import java.lang.management.ManagementFactory;
void main(String[] args) throws Exception {
    boolean virtual = args[0].equals("virtual");
    int count = Integer.parseInt(args[1]);
    Thread.Builder hire = virtual ? Thread.ofVirtual() : Thread.ofPlatform();
    long rssBefore = rssMb();
    List<Thread> clerks = new ArrayList<>(count);
    long start = System.nanoTime();
    for (int i = 0; i < count; i++) {
        clerks.add(hire.start(() -> nap(5_000)));             // every clerk falls asleep for five seconds
    }
    long started = (System.nanoTime() - start) / 1_000_000;
    Thread.sleep(1_000);                                      // give the last ones time to fall asleep
    System.gc();
    long heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() >> 20;
    long rss = rssMb();
    IO.println(count + " " + args[0] + " threads started in " + started + " ms, all asleep");
    IO.println("resident memory: " + rssBefore + " MB before, " + rss + " MB now");
    IO.println("heap in use after a GC: " + heap + " MB");
    IO.println("OS threads in this process: " + osThreads());
    for (Thread clerk : clerks) clerk.join();
}

static long rssMb() throws Exception {                       // ask ps, the way you would from a terminal
    return Long.parseLong(ps("-o", "rss=").strip()) / 1024;
}

static long osThreads() throws Exception {                   // ps -M prints a header, then one line per OS thread
    return ps("-M").lines().count() - 1;
}

static String ps(String... opts) throws Exception {
    List<String> cmd = new ArrayList<>(List.of("ps"));
    cmd.addAll(List.of(opts));
    cmd.addAll(List.of("-p", String.valueOf(ProcessHandle.current().pid())));
    Process p = new ProcessBuilder(cmd).start();
    String out = new String(p.getInputStream().readAllBytes());
    p.waitFor();
    return out;
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
