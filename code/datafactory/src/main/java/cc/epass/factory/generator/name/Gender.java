package cc.epass.factory.generator.name;

public enum Gender {
    male(0), female(1);
    private final int value;

    Gender(int v) {
        value = v;
    }

    public int value() {
        return value;
    }

    public static Gender fromValue(int v) {
        for (Gender c : Gender.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}
