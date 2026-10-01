static final int ADDS = 50_000;

void main() {
    for (int round = 1; round <= 3; round++) {                    // three rounds, so the JIT has warmed up by the last
        IO.println("round " + round + ": ArrayList " + time(new ArrayList<>()) + " ms, CopyOnWriteArrayList "
                + time(new CopyOnWriteArrayList<>()) + " ms, for " + ADDS + " adds each");
    }
}

static long time(List<Integer> list) {
    long start = System.nanoTime();
    for (int i = 0; i < ADDS; i++) {
        list.add(i);                                              // CopyOnWriteArrayList copies its whole array here
    }
    if (list.size() != ADDS) throw new AssertionError();
    return (System.nanoTime() - start) / 1_000_000;
}
