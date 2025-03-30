package thread.bounded;

import java.util.ArrayList;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BoundedMain {
    public static void main(String[] args) {
        //1. BoundedQueue 선택.
//        BoundedQueue queue = new BoundedQueueV1(2); // 버퍼 크기
//        BoundedQueue queue = new BoundedQueueV2(2); // 버퍼 크기
//        BoundedQueue queue = new BoundedQueueV3(2); // 버퍼 크기
//        BoundedQueue queue = new BoundedQueueV4(2); // 버퍼 크기
        BoundedQueue queue = new BoundedQueueV5(2); // 버퍼 크기

        //2. 생산자, 소비자 실행 순서 선택, 반드시 하나만 선택!
//        producerFirst(queue); // 생산자 먼저 실행
        consumerFirst(queue); // 소비자 먼저 실행
    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        // 스레드의 결과 상태를 한꺼번에 출력학히 위해 보관
        ArrayList<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        // 스레드의 결과 상태를 한꺼번에 출력학히 위해 보관
        ArrayList<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    // 모든 스레드의 상태 출력
    private static void printAllState(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            log(thread.getName() + ": " + thread.getState());
        }
    }

    // 생산자 스레드를 만들어 실행
    private static void startProducer(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("생산자 시작");
        for (int i=1; i<=3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data" + i), "producer" + i);
            threads.add(producer);
            producer.start();
            // 순서대로 스레드를 실행시키기 위함
            sleep(100);
        }
    }

    // 생산자 스레드를 만들어 실행
    private static void startConsumer(BoundedQueue queue, ArrayList<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for (int i=1; i<=3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            // 순서대로 스레드를 실행시키기 위함
            sleep(100);
        }
    }
}
