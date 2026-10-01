# Section 4: Race Conditions and synchronized

Lectures in this section: *The Vanishing Tickets*, *Slow Motion*, *What synchronized Actually Locks*, *Four Ways to Get synchronized Wrong*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java Blocked.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `Blocked.java` | Four Ways to Get synchronized Wrong | one TIMED_WAITING holder, nine BLOCKED | `java Blocked.java` |  |
| `LockInt.java` | Four Ways to Get synchronized Wrong | **does not compile on purpose**: `required: reference, found: int` | `java LockInt.java` | does not compile on purpose |
| `LockInteger.java` | What synchronized Actually Locks | javac 27 `[identity]` warning: synchronizing on a value-based class | `java LockInteger.java` |  |
| `NapFirst.java` | Slow Motion | nap moved before the read: 17/20 runs say 2, 3/20 say 1 | `java NapFirst.java` |  |
| `NullDesk.java` | Four Ways to Get synchronized Wrong | NPE: `Cannot enter synchronized block because "NullDesk.DESK" is null` | `java NullDesk.java` |  |
| `SellRefund.java` | What synchronized Actually Locks | two methods on two different locks → not 0; same lock → 0 | `java SellRefund.java` |  |
| `SellRefundSameKey.java` | What synchronized Actually Locks | two methods on two different locks → not 0; same lock → 0 | `java SellRefundSameKey.java` |  |
| `SlowMotion.java` | Slow Motion | read, nap 50 ms, write: two clerks collide every run (register says 1) | `java SlowMotion.java` |  |
| `SlowMotionSync.java` | Slow Motion | the same with `synchronized`: read 0, write 1, read 1, write 2 | `java SlowMotionSync.java` |  |
| `TicketCounter.java` | The Vanishing Tickets | 10 clerks × 100,000 `ticketsSold++` → 148k–223k, never 1,000,000 | `java TicketCounter.java` |  |
| `TicketDesk.java` | What synchronized Actually Locks | `synchronized (DESK)` on a `static final Object` → 1,000,000 | `java TicketDesk.java` |  |
| `TicketNewObject.java` | Four Ways to Get synchronized Wrong | `synchronized (new Object())` → higher, still wrong | `java TicketNewObject.java` |  |
| `TicketSync.java` | Slow Motion; What synchronized Actually Locks | `static synchronized sell()` → 1,000,000 every run (28–34 ms) | `java TicketSync.java` |  |
| `TicketSyncRun.java` | What synchronized Actually Locks | `synchronized run()` on 10 clerk objects = 10 keys → wrong | `java TicketSyncRun.java` |  |
| `TicketVolatile.java` | Four Ways to Get synchronized Wrong | `volatile` → visibility, not atomicity; slower and wrong | `java TicketVolatile.java` |  |
