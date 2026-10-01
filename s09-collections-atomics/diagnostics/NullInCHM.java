void main() {
    Map<String, String> plain = new HashMap<>();
    plain.put("S7-42", null);                                     // a booking with no passenger yet
    IO.println("HashMap takes a null value: " + plain);
    Map<String, String> concurrent = new ConcurrentHashMap<>();
    try {
        concurrent.put("S7-42", null);
    } catch (NullPointerException e) {
        IO.println("ConcurrentHashMap refuses it: " + e);
    }
}
