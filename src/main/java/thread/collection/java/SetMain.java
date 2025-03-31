package thread.collection.java;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetMain {
    public static void main(String[] args) throws InterruptedException {
        Set<Object> set = new CopyOnWriteArraySet<>();
        test(set);
        System.out.println("set = " + set);

        Set<Object> skipSet = new ConcurrentSkipListSet<>();
        test(skipSet);
        System.out.println("skipSet = " + skipSet);
    }

    public static void test(Collection<Object> list) throws InterruptedException {
        Runnable task1 = () -> {
            for (int i=0; i<3; i++) {
                list.add("task1-" + i);
            }
        };

        Runnable task2 = () -> {
            for (int i=0; i<3; i++) {
                list.add("task2-" + i);
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
