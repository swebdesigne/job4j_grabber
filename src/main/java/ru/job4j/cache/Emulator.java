package ru.job4j.cache;

public class Emulator {
    /**
     *
     * @param args - Names.txt Address.txt
     */
    public static void main(String[] args) {
        String dir = args[1];
        DirFileCache fileCache = new DirFileCache(dir);
        if (fileCache.get(dir) == null) {
            System.out.println(fileCache.load(dir));
        }
        System.out.println(fileCache.get(dir));
    }
}
