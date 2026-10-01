void main() {
    int cores = Runtime.getRuntime().availableProcessors();
    int used = 1;                        // one clerk: every program so far

    IO.println("cores : " + cores);
    IO.println("used  : " + used);
    IO.println("idle  : " + (100 * (cores - used) / cores) + "%");
}
