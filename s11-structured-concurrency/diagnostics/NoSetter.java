// A scoped value has no set method (no flag): this file does not compile.
static final ScopedValue<String> PASSENGER = ScopedValue.newInstance();

void main() {
    ScopedValue.where(PASSENGER, "Sophie").run(() -> PASSENGER.set("Tom"));
}
