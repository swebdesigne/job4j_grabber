package ru.job4j.cache;

import java.util.Scanner;

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
        System.out.println("Please to point the direction:");
        Emulator emulator = new Emulator(new Scanner(System.in).nextLine());
        System.out.println("Please to point the file:");
        String data = emulator.getData(new Scanner(System.in).nextLine());
        System.out.println(data);
    }
}
