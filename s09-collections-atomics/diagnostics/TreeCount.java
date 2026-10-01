// Looks inside HashMap with reflection, so run it with:
//   java --add-opens java.base/java.util=ALL-UNNAMED TreeCount.java
static final int CLERKS = 10;
static final int PER_CLERK = 10_000;

void main() throws Exception {
    Map<String, String> shared = new HashMap<>();                 // SharedRegister's race first, while the JVM is cold
    List<Callable<Integer>> clerks = new ArrayList<>();
    for (int i = 1; i <= CLERKS; i++) {
        int clerk = i;
        clerks.add(() -> {
            for (int b = 1; b <= PER_CLERK; b++) {
                shared.put("clerk-" + clerk + "/booking-" + b, "window-" + clerk);
            }
            return PER_CLERK;
        });
    }
    int threw = 0;
    try (ExecutorService desk = Executors.newFixedThreadPool(CLERKS)) {
        for (Future<Integer> slip : desk.invokeAll(clerks)) {
            try {
                slip.get();
            } catch (ExecutionException e) {
                threw++;
            }
        }
    }
    IO.println("ten clerks : " + inside(shared) + (threw > 0 ? ", slips that threw " + threw : ""));

    Map<String, String> alone = new HashMap<>();                  // the same 100,000 keys, written by ONE thread
    for (int clerk = 1; clerk <= CLERKS; clerk++) {
        for (int b = 1; b <= PER_CLERK; b++) {
            alone.put("clerk-" + clerk + "/booking-" + b, "window-" + clerk);
        }
    }
    IO.println("one thread : " + inside(alone));
}

static String inside(Map<String, String> map) throws Exception {
    Field tableField = HashMap.class.getDeclaredField("table");
    tableField.setAccessible(true);
    Field nextField = Class.forName("java.util.HashMap$Node").getDeclaredField("next");
    nextField.setAccessible(true);
    Field keyField = Class.forName("java.util.HashMap$Node").getDeclaredField("key");
    keyField.setAccessible(true);
    Set<Object> distinct = Collections.newSetFromMap(new IdentityHashMap<>());
    Object[] table = (Object[]) tableField.get(map);
    int treeBins = 0, longest = 0;
    long reachable = 0, treeNodes = 0;
    boolean loop = false;
    for (Object bin : table) {
        if (bin == null) continue;
        if (bin.getClass().getSimpleName().equals("TreeNode")) treeBins++;
        int length = 0;
        for (Object node = bin; node != null; node = nextField.get(node)) {
            reachable++;
            distinct.add(keyField.get(node));
            if (node.getClass().getSimpleName().equals("TreeNode")) treeNodes++;
            if (++length > 1_000_000) { loop = true; break; }     // a chain that never ends
        }
        longest = Math.max(longest, length);
    }
    return "size() says " + map.size() + ", nodes reachable " + reachable + ", distinct keys " + distinct.size() + ", buckets " + table.length
            + ", tree buckets " + treeBins + " (tree nodes " + treeNodes + "), longest chain " + longest
            + (loop ? ", A CHAIN THAT LOOPS" : "");
}
