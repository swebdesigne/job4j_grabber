package ru.job4j.gc;

public class User {
    private int age;
    private String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.printf("Removed %d %s%n", age, name);
    }

    /**
     * Заголовок - 12b (64 битная система)
     * int - 4b
     * String:
     *  char[] - 4b (ссылка)
     *  coder (byte) - 1b
     *  hash (int) - 4b
     * 12 + 4 + 1 + 4 = 21 и делаем выравнивание для кратности + 3 = 24
     * итого: вес 3х объктов составляет 72 байта (24 * 3)
     *
     * 
     * параметр -Xmx16m
     * @param args
    */
    public static void main(String[] args) {
        User user1 = new User(34, "Igor");
        User user2 = new User(35, "Boris");
        User user3 = new User(36, "Alina");

        Runtime r = Runtime.getRuntime();
        System.out.printf("Total memory %s free memory before %s\n", r.totalMemory(), r.freeMemory());
        int it = 0;
        while (it < 100000) {
            new User(it, "№ " + it);
            it++;
        }
        System.out.printf("\nTotal memory %s free memory before %s\n", r.totalMemory(), r.freeMemory());
    }
}
