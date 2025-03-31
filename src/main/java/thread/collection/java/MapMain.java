package thread.collection.java;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapMain {
    public static void main(String[] args) throws InterruptedException {
        Map<Object, Object> map1 = new ConcurrentHashMap<>();
        test(map1);
        System.out.println("map1 = " + map1);

        Map<Object, Object> map2 = new ConcurrentSkipListMap<>();
        test(map2);
        System.out.println("map2 = " + map2);
    }

    public static void test(Map<Object, Object> list) throws InterruptedException {
        Runnable task1 = () -> {
            for (int i=0; i<3; i++) {
                list.put("task1-" + i, i);
            }
        };

        Runnable task2 = () -> {
            for (int i=0; i<3; i++) {
                list.put("task2-" + i, i);
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
