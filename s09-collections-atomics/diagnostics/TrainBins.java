// Two Safe Calls Make a Race, extra experiment: which pigeonhole (bin) each train lands in, read from the map itself by reflection.
// Run: java --add-opens java.base/java.util.concurrent=ALL-UNNAMED TrainBins.java   (expect table length 16, bins 1, 6, 8, 13)
static final String[] TRAINS = {"northbound", "southbound", "eastbound", "westbound"};
void main() throws Exception {
    Map<String, Integer> perTrain = new ConcurrentHashMap<>();
    for (int b = 0; b < 100_000; b++) perTrain.merge(TRAINS[b % 4], 1, Integer::sum);
    var fld = ConcurrentHashMap.class.getDeclaredField("table");
    fld.setAccessible(true);
    Object[] tab = (Object[]) fld.get(perTrain);
    IO.println("table length " + tab.length);
    for (int i = 0; i < tab.length; i++) if (tab[i] != null) IO.println("bin " + i + ": " + tab[i]);
}
