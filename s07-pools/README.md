# Section 7: Thread Pools (ExecutorService)

Lectures in this section: *A Pool of Clerks*, *Nobody Told the Clerks*, *Closing Time*, *Which Pool, and What It Costs*, *How Many Clerks?*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java AwaitGate.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `AwaitGate.java` | Closing Time | `shutdown()` then `awaitTermination`: `false` after 100 ms, then `true` (20/20) | `java AwaitGate.java` |  |
| `CachedFlood.java` | Which Pool, and What It Costs | the cold open's 5,000 one-second cards on a cached pool (no ceiling): the same wall, now named `pool-1-thread-4065` (11/11) | `java CachedFlood.java` |  |
| `CachedIdle.java` | Which Pool, and What It Costs | a cached pool with no shutdown exits by itself after 60,003–60,014 ms (8/8): its idle clerk's sixty-second keep-alive ran out | `java CachedIdle.java` |  |
| `CloseWaits.java` | Closing Time | `close()` = shutdown and wait: six 200 ms cards handed in after 0–1 ms, the try block over after 409–425 ms (20/20) | `java CloseWaits.java` |  |
| `Closed.java` | Nobody Told the Clerks | a card handed in after `shutdown()`: `RejectedExecutionException ... rejected from java.util.concurrent.ThreadPoolExecutor@...[Shutting down, pool size = 1, active threads = 1, queued tasks = 0, completed tasks = 0]`, `AbortPolicy` in the trace, exit 1; … | `java Closed.java` |  |
| `CpuBound.java` | How Many Clerks? | 200 computing cards on pools of 1, 4, 10, 200: about 5.5–5.7 s, 1.54 s, 0.78 s, 0.78 s in the quieter runs (16 runs in all, 773–798 ms for 200 against 777–895 for 10); flat from the core count on, never "200 is slower" | `java CpuBound.java` |  |
| `CrashingCard.java` | How Many Clerks? | a card that throws, via `execute`, on a pool of one: `Exception in thread "pool-1-thread-1" ... card 2 is torn`, and cards 3 and 4 run on a replacement, `pool-1-thread-2`; exit 0 (20/20) | `java CrashingCard.java` |  |
| `FirstPool.java` | A Pool of Clerks | the first pool: `newFixedThreadPool(3)`, six cards handed in with `execute`, `shutdown()` at the end. `main: all six cards handed in` prints first, and three names, `pool-1-thread-1..3`, serve two cards each (20/20) | `java FirstPool.java` |  |
| `NoShutdown.java` | Nobody Told the Clerks | A fixed pool of four runs one card and main finishes, but nobody shuts the pool down, so the program never exits. | `java NoShutdown.java` | hangs on purpose: press Ctrl+C |
| `OneClerkEach.java` | A Pool of Clerks | **the problem.** 5,000 passengers, a new `Thread` each, every card a one-second nap. It dies at clerk 4,065 or 4,066 with `OutOfMemoryError: unable to create native thread`, and the JVM's own warning line contains `stacksize: 2048k` (16/16 over two sessions; … | `java OneClerkEach.java` |  |
| `PoolSizes.java` | How Many Clerks? | the same 200 waiting cards on pools of 4, 10, 25, 50, 200: 5,193–5,400, 2,085–2,183, 833–873, 417–439, 111–127 ms (10 runs over two sessions). The core-count pool (10) is 16.5–19× slower than 200 in the same run | `java PoolSizes.java` |  |
| `RawClerks.java` | A Million Threads on This Laptop | the same 200 cards, a thread each: **108–119 ms** (26 runs), about 45–48 times faster within each session | `java RawClerks.java` |  |
| `Roster200.java` | A Million Threads on This Laptop | Two hundred cards that each wait a tenth of a second, on a fixed pool of four clerks: about five seconds. | `java Roster200.java` |  |
| `ShutdownNow.java` | Closing Time | `shutdownNow()` after 100 ms: 4 unstarted cards handed back, the 2 running ones interrupted after 101–111 ms (20/20) | `java ShutdownNow.java` |  |
| `SilentCard.java` | How Many Clerks? | the same card via `submit`: no trace at all, the clerk survives (cards 1, 3, 4 on `pool-1-thread-1`), exit 0 (25/25) | `java SilentCard.java` |  |
| `SleepOvershoot.java` | How Many Clerks? | why more than 5.0 s: `Thread.sleep(100)` took 100.2–116.3 ms, run averages 103.6–108.4 (15 × 20). The average moved between the two sessions (105.9–108.4, then 103.6–104.7) and Roster200 moved with it: 50 rounds × the average matched both times | `java SleepOvershoot.java` |  |
| `Stuck.java` | Nobody Told the Clerks; Closing Time | try-with-resources and a one-minute card: prints `card handed in` and stops. `jstack`: main `TIMED_WAITING (parking)` in `awaitTermination` under `ExecutorService.close`, at `Stuck.java:5`, the closing brace (5/5 dumps; hung 6/6) | `java Stuck.java` | hangs on purpose: press Ctrl+C |
| `ThreadAsCard.java` | A Pool of Clerks | a `Thread` subclass, `window-9`, handed to the pool as a card: `getName() says window-9, but I run on pool-1-thread-1`, and `window-9 is NEW, alive: false` (20/20). The pool accepts it, because Thread implements Runnable, but the clerk you built never works | `java ThreadAsCard.java` |  |
| `ThreeFactories.java` | Which Pool, and What It Costs | Six cards on each of three kinds of pool, printing which clerk served each card. | `java ThreeFactories.java` |  |
| `TrayOfCards.java` | A Pool of Clerks | the same 5,000 one-second cards on a pool of ten, cast to `ThreadPoolExecutor` to look inside. After 2.5 s: 20 served, 10 clerks, **4,970 cards waiting in a `LinkedBlockingQueue`**, 16 Java threads (40-41 OS threads, about 110 MB); … | `java TrayOfCards.java` |  |
| `WithShutdown.java` | Nobody Told the Clerks | the same plus one line, `desk.shutdown()`: exit 0, 40/40 | `java WithShutdown.java` |  |

## Extra experiments (`diagnostics/`)

Smaller programs used for a single question in a lecture, or to check a claim. Run them from this folder.

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `diagnostics/AwaitNoShutdown.java` | Closing Time | awaitTermination without calling shutdown first: it waits the whole two seconds and returns false. | `java diagnostics/AwaitNoShutdown.java` |  |
| `diagnostics/BoundedTray.java` | extra experiment | Two clerks and a tray that holds three: cards one to five are accepted and card six is rejected. | `java diagnostics/BoundedTray.java` |  |
| `diagnostics/CardTwice.java` | extra experiment | A Thread subclass handed to a pool as a plain Runnable: the pool's own clerk runs its run(). | `java diagnostics/CardTwice.java` |  |
| `diagnostics/CloseOnThrow.java` | Closing Time | main throws inside try-with-resources: close() still waits for all three cards first. | `java diagnostics/CloseOnThrow.java` |  |
| `diagnostics/ClosedNoNap.java` | extra experiment | the original shape: card 1 does not nap | `java diagnostics/ClosedNoNap.java` |  |
| `diagnostics/ClosedSubmit.java` | extra experiment | Handing a card to a pool after shutdown(): RejectedExecutionException. | `java diagnostics/ClosedSubmit.java` |  |
| `diagnostics/CpuCurve.java` | extra experiment | guarded by the lock on the class | `java diagnostics/CpuCurve.java` |  |
| `diagnostics/DaemonPool.java` | extra experiment | A pool whose clerks are daemon threads and is never shut down: the program exits when main does. | `java diagnostics/DaemonPool.java` |  |
| `diagnostics/DeafCard.java` | Closing Time | A card that never checks for interruption: shutdownNow taps it, but it still runs its full second. | `java diagnostics/DeafCard.java` |  |
| `diagnostics/LazyHiring.java` | Nobody Told the Clerks | A fixed pool hires its clerks only as cards arrive: pool size before and after each card. | `java diagnostics/LazyHiring.java` |  |
| `diagnostics/NamedPool.java` | extra experiment | A ThreadFactory that names the pool's clerks window-1, window-2 and window-3. | `java diagnostics/NamedPool.java` |  |
| `diagnostics/PoolDaemon.java` | Nobody Told the Clerks | What a pool's clerk is: its name, daemon flag, priority and thread group, next to main's. | `java diagnostics/PoolDaemon.java` |  |
| `diagnostics/ShutdownNowSubmit.java` | extra experiment | shutdownNow hands back the cards that never started: wrapped in a FutureTask after submit, the same object after execute. | `java diagnostics/ShutdownNowSubmit.java` |  |
| `diagnostics/SingleOrder.java` | Which Pool, and What It Costs | A single-thread executor serves ten cards in exactly the order they were handed in. | `java diagnostics/SingleOrder.java` |  |
| `diagnostics/TrayDepth.java` | Which Pool, and What It Costs | How much room a fixed pool's tray has: Integer.MAX_VALUE. | `java diagnostics/TrayDepth.java` |  |
| `diagnostics/VirtualNoClose.java` | Nobody Waits for a Virtual Thread | A virtual-thread executor that nobody closes: main can finish before the task runs, and the task's thread has no name. | `java diagnostics/VirtualNoClose.java` |  |
