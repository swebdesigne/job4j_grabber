package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DirFileCache extends AbstractCache<String, String> {
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        try (FileReader fr = new FileReader(key);
             BufferedReader bfr = new BufferedReader(fr)
        ) {
            StringBuilder sb = new StringBuilder();
            while (bfr.ready()) {
                sb.append(bfr.readLine());
            }
            put(key, sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return get(key);
    }
}
