void main() {
    long t0 = System.nanoTime();
    IO.println("first line");
    long t1 = System.nanoTime();
    IO.println("second line");
    long t2 = System.nanoTime();
    IO.println("first IO.println took " + (t1 - t0) / 1000 + " us, second took " + (t2 - t1) / 1000 + " us");
}
