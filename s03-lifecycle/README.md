# Section 3: The Life of a Thread: sleep, join and interrupt

Lectures in this section: *The Life of a Thread*, *The Supervisor at the Gate*, *The Wrong-Place Join, and a Tap on the Shoulder*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java CounterJoined.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `CounterJoined.java` | The Supervisor at the Gate | start all, then join all: summary last, ≈2.14 s, total wrong (4–9 over 20 runs) | `java CounterJoined.java` |  |
| `CounterWrongJoin.java` | The Wrong-Place Join, and a Tap on the Shoulder | join under each start: tidy, 9 tickets, ≈6.39 s | `java CounterWrongJoin.java` |  |
| `Gate.java` | The Supervisor at the Gate | join orders results, not work | `java Gate.java` |  |
| `Gotcha.java` | The Supervisor at the Gate | joining a never-started thread returns after 0 ms; state NEW | `java Gotcha.java` |  |
| `IgnoreTap.java` | The Wrong-Place Join, and a Tap on the Shoulder | a swallowed interrupt: Tom keeps selling | `java IgnoreTap.java` |  |
| `JoinDuration.java` | The Supervisor at the Gate | `join(Duration)` (Java 19+) returns `false` on timeout | `java JoinDuration.java` |  |
| `JoinState.java` | The Supervisor at the Gate | plain `join` → WAITING, `join(1500)` → TIMED_WAITING | `java JoinState.java` |  |
| `NegativeSleep.java` | The Wrong-Place Join, and a Tap on the Shoulder | `IllegalArgumentException: timeout value is negative` | `java NegativeSleep.java` |  |
| `Shift.java` | The Life of a Thread | NEW → RUNNABLE → TIMED_WAITING → TERMINATED, `isAlive`, restart → `IllegalThreadStateException` | `java Shift.java` |  |
| `SleepOther.java` | The Wrong-Place Join, and a Tap on the Shoulder | `tom.sleep(1000)` sleeps **main**; Tom stays NEW | `java SleepOther.java` |  |
| `States.java` | The Life of a Thread | the six `Thread.State` values | `java States.java` |  |
| `StopGone.java` | The Wrong-Place Join, and a Tap on the Shoulder | **does not compile on JDK 27**: `Thread.stop()` no longer exists | `java StopGone.java` | does not compile on purpose |
| `TeaBreak.java` | The Wrong-Place Join, and a Tap on the Shoulder | interrupt a sleep: `sleep interrupted`, ~513 ms, flag cleared then put back | `java TeaBreak.java` |  |
