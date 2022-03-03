package ru.job4j.gc;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SoftRef {
    private void softRef(String name) {
        SoftReference<String> nameRef = new SoftReference<>(name);
        Object getName = nameRef.get();
        if (getName != null) {
            System.out.println(getName);
        }
    }

    private void weakRef(String name) {
        WeakReference nameRef = new WeakReference(name);
        Object getName = nameRef.get();
        if (getName != null) {
            System.out.println(getName);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SoftRef ref = new SoftRef();
        ref.softRef("Igor");
        ref.weakRef("Igor");
    }
}
