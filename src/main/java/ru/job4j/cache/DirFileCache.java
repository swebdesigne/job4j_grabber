package ru.job4j.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {
    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String data = null;
        try {
            data = Files.readString(Path.of(cachingDir + "/" + key));
            put(key, data);
        } catch (IOException e) {
            System.err.println("File was not found. Put another directory or file name");
        }
        return data;
    }
}
