# Section 5: wait and notify

Lectures in this section: *Waiting for Something to Happen*, *wait Lets Go of the Key*, *The Hatch*, *while Not if, notifyAll Not notify*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java Hatch.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `Hatch.java` | The Hatch | capacity 3, 6 slips, printer 50 ms, Meena 150 ms; the five steps (synchronized, while, wait, act, notifyAll); identical transcript 10/10 (15 lines), "tray full, waiting" on line 9, never above 3, ends empty | `java Hatch.java` |  |
| `HatchIf.java` | while Not if, notifyAll Not notify | `while` → `if` with two clerks: Tom dies with `NoSuchElementException`, slips #4–#6 stranded, and the process **still exits 0** (10/10) | `java HatchIf.java` |  |
| `LockHold.java` | wait Lets Go of the Key | holder keeps the lock 1 s: `sleep` lets main in after about 900 ms (895–904 ms over 20 runs), `wait` after 0 ms (20/20) | `java LockHold.java` |  |
| `Monitor1.java` | Waiting for Something to Happen | `tray.wait()` on a plain `new Object()` with no lock held → `IllegalMonitorStateException: current thread is not owner` (trace at line 3, exit 1, 10/10) | `java Monitor1.java` |  |
| `Monitor2.java` | Waiting for Something to Happen; wait Lets Go of the Key | the same `wait()` inside `synchronized (tray)`: legal, silent, and nobody ever knocks → no output, exit 142 under the watchdog (10/10) | `java Monitor2.java` | hangs on purpose: press Ctrl+C |
| `NotifyAllOk.java` | while Not if, notifyAll Not notify | the control: the same file with `notifyAll` in both places (a two-line diff). **Completed 40/40** over two passes: "6 of 6 slips taken", all TERMINATED; about 0.26 s per run | `java NotifyAllOk.java` | hangs on purpose: press Ctrl+C |
| `NotifyStall.java` | while Not if, notifyAll Not notify | capacity 1, two clerks, no naps, `notify` instead of `notifyAll`: a lost wakeup. Main gives each thread 500 ms, then reports. **Stuck 40/40** over two passes: "shutters down: 2 of 6 slips taken, tray = []" (34/40; "1 of 6" in 6/40), and printer, Meena and Tom all WAITING; … | `java NotifyStall.java` | hangs on purpose: press Ctrl+C |
| `Spin.java` | The Hatch | a busy-wait for one slip: about 260 million checks in about 1 s (254.4M–263.3M over 15 runs) | `java Spin.java` |  |
| `Waiting.java` | wait Lets Go of the Key | three threads on one lock: WAITING (called wait), TIMED_WAITING (sleeping with the key), BLOCKED (wants the key); identical 10/10 | `java Waiting.java` |  |
| `WrongObject.java` | wait Lets Go of the Key | `synchronized (cashDrawer)` then `ledger.wait()` → the same exception (trace at line 5, exit 1, 10/10): holding *a* key is not holding *that* key | `java WrongObject.java` |  |
