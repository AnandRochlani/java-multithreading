# Section 6: Deadlock

Lectures in this section: *Two Clerks, Two Keys*, *Found One Java-Level Deadlock*, *Two Orders Are the Bug*, *Give Up Instead of Hanging*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java AfterYou.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `AfterYou.java` | Give Up Instead of Hanging | livelock: two polite clerks and one rubber stamp, each hands it over while the other has a passenger. After one second both are RUNNABLE, each has used about a second of CPU (952–1015 ms), the stamp has changed hands about 20 million times (14.7–22.2 million), and 0 tickets are s … | `java AfterYou.java` |  |
| `FindDeadlock.java` | Two Orders Are the Bug | the same deadlock, found by the program itself: `ThreadMXBean.findDeadlockedThreads()` → `Meena is BLOCKED, waiting for a lock held by Tom` and the mirror line for Tom. The clerks are daemons, so it reports and exits 0 (20/20). … | `java FindDeadlock.java` |  |
| `ForgotFinally.java` | Give Up Instead of Hanging | the deliberate wrong turn: an exception between `lock()` and `unlock()` with no `finally`. Meena's thread dies still owning the key (`cash key locked: true`), Tom waits forever (`WAITING`), and `jstack` reports no deadlock | `java ForgotFinally.java` |  |
| `LockDeadlock.java` | Every Tool Has a Catch | the `TwoKeys` deadlock with `ReentrantLock.lock()`: the threads show `WAITING` (parking), not BLOCKED; `jstack` still prints `Found one Java-level deadlock:` with `waiting for ownable synchronizer … which is held by`; … | `java LockDeadlock.java` |  |
| `NoNap.java` | Two Orders Are the Bug | The opposite-order clerks with no nap between the two keys: they can still deadlock, but the window is tiny. | `java NoNap.java` | hangs on purpose: press Ctrl+C |
| `OneLine.java` | Two Orders Are the Bug | One log line added inside the first lock: the opposite-order clerks now deadlock in most runs. | `java OneLine.java` | hangs on purpose: press Ctrl+C |
| `ThreeClerks.java` | Two Orders Are the Bug | a circle of three: Meena cash→ledger, Tom ledger→stamp, Kavya stamp→cash. `Meena: BLOCKED, Tom: BLOCKED, Kavya: BLOCKED`, hangs every run; `jstack` names all three, each "held by" the next | `java ThreeClerks.java` | hangs on purpose: press Ctrl+C |
| `TwoKeys.java` | Two Clerks, Two Keys | Two plain `Object` keys, `CASH` and `LEDGER`; `sell(first, second)` takes one, naps 100 ms holding it, then takes the other. Meena runs `sell(CASH, LEDGER)`, Tom `sell(LEDGER, CASH)`. … | `java TwoKeys.java` | hangs on purpose: press Ctrl+C |
| `TwoKeysFixed.java` | Two Orders Are the Bug | Fix 1, lock ordering: a one-line diff from `TwoKeys` (Tom runs `sell(CASH, LEDGER)`), with the 100 ms nap still inside. **Completed 40/40**, exit 0 | `java TwoKeysFixed.java` |  |
| `TwoKeysTry.java` | Two Orders Are the Bug; Give Up Instead of Hanging | Fix 2: `ReentrantLock`, `lock()` right before `try`, `tryLock(1, TimeUnit.SECONDS)` for the second key, every `unlock` in a `finally`, the opposite order kept. Finishes **40/40**, exit 0, about 1.4 s (1.35–1.38 s); … | `java TwoKeysTry.java` |  |
| `WaitKeepsCash.java` | Found One Java-Level Deadlock | `wait` hands back only the key of the object you wait on. Meena holds CASH and LEDGER and calls `LEDGER.wait()`; Tom gets the ledger key but is BLOCKED on the cash key: `Tom: got the ledger key`, `Meena: WAITING, Tom: BLOCKED`, then hangs (exit 142, 20/20). … | `java WaitKeepsCash.java` | hangs on purpose: press Ctrl+C |

## Extra experiments (`diagnostics/`)

Smaller programs used for a single question in a lecture, or to check a claim. Run them from this folder.

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `diagnostics/NapOutside.java` | extra experiment | the one key to the cash drawer | `java diagnostics/NapOutside.java` |  |
| `diagnostics/NapOutsideQuiet.java` | extra experiment | the one key to the cash drawer | `java diagnostics/NapOutsideQuiet.java` |  |
| `diagnostics/OneLineSysout.java` | extra experiment | OneLine with System.out.println instead of IO.println. | `java diagnostics/OneLineSysout.java` |  |
| `diagnostics/OneLineWarm.java` | extra experiment | OneLine with printing warmed up before the clerks start. | `java diagnostics/OneLineWarm.java` | hangs on purpose: press Ctrl+C |
| `diagnostics/PrintCost.java` | extra experiment | How long the first and the second IO.println take. | `java diagnostics/PrintCost.java` | hangs on purpose: press Ctrl+C |
| `diagnostics/Retry.java` | extra experiment | Back off with tryLock and try again, with the same wait between attempts. | `java diagnostics/Retry.java` | hangs on purpose: press Ctrl+C |
| `diagnostics/RetryRandom.java` | extra experiment | The same retry with a random wait between attempts. | `java diagnostics/RetryRandom.java` | hangs on purpose: press Ctrl+C |
| `diagnostics/ThreadStartGap.java` | extra experiment | How far apart two threads started back to back actually begin running. | `java diagnostics/ThreadStartGap.java` |  |
| `diagnostics/UnlockNotHeld.java` | extra experiment | unlock() on a ReentrantLock this thread never locked: IllegalMonitorStateException. | `java diagnostics/UnlockNotHeld.java` |  |
