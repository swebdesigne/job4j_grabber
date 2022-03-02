package ru.job4j.gc;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SoftRef {
    private void softRef() throws InterruptedException {
        String name = "Igor";
        SoftReference<String> nameRef = new SoftReference<>(name);
        name = null;
        System.gc();
        if (nameRef.get() != null) {
            System.out.println(nameRef.get());
        }
    }

    private void weakRef() throws InterruptedException {
        String name = "Igor";
        WeakReference nameRef = new WeakReference(name);
        name = null;
        System.gc();
        if (nameRef.get() != null) {
            System.out.println(nameRef.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SoftRef ref = new SoftRef();
        ref.softRef();
        ref.weakRef();
    }
}
