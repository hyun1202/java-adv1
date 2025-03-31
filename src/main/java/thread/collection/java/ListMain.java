package thread.collection.java;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ListMain {
    public static void main(String[] args) throws InterruptedException {
        List<Object> list = new CopyOnWriteArrayList<>();
        test(list);
        System.out.println("list = " + list);
    }

    public static void test(Collection<Object> list) throws InterruptedException {
        Runnable task1 = () -> {
            for (int i=0; i<3; i++) {
                list.add("task1: " + i);
            }
        };

        Runnable task2 = () -> {
            for (int i=0; i<3; i++) {
                list.add("task2: " + i);
            }
        };

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
