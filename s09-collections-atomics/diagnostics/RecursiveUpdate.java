void main() {
    Map<String, Integer> fares = new ConcurrentHashMap<>();
    try {
        fares.computeIfAbsent("S7-42", seat -> fares.computeIfAbsent("S7-42", again -> 1250));   // the function touches the map
    } catch (IllegalStateException e) {
        IO.println("computeIfAbsent inside computeIfAbsent: " + e);
    }
}
