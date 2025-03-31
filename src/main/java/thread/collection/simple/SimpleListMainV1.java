package thread.collection.simple;

import thread.collection.simple.list.BasicList;

import java.util.ArrayList;
import java.util.List;

public class SimpleListMainV1 {
    public static void main(String[] args) {
        BasicList list = new BasicList();
        // 스레드1, 2가 동시에 실행한다고 가정
        list.add("A");
        list.add("B");
        System.out.println("list= " + list);
    }
}
