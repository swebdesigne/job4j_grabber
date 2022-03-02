package ru.job4j.cache;

public class Emulator {
    private final DirFileCache dir;

    public Emulator(String dirFileCache) {
        dir = new DirFileCache(dirFileCache);
    }

    public void load(String key) {
        dir.load(key);
    }

    public String getData(String key) {
        return dir.get(key);
    }

    public static void main(String[] args) {
        Emulator emulator = new Emulator("D:\\");
        emulator.load("test.txt");
        String data = emulator.getData("test.txt");
        System.out.println(data);

    }
}
