package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV1 {
    public static void main(String[] args) {
        // 최대 2개까지 작업을 큐에 보관할 수 있다.
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        // 요청이 많거나 급한 경우 스레드 풀은 최대 4개까지 스레드를 증가시켜서 사용할 수 있다.
        // 요청이 많거나 급한 경우: 큐가 가득찬 경우 -> 대기하는 작업이 꽉 찰 정도로 요청이 많다는 뜻
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3"));
        printState(es, "task3");

        es.execute(new RunnableTask("task4"));
        printState(es, "task4");

        es.execute(new RunnableTask("task5"));
        printState(es, "task5");

        es.execute(new RunnableTask("task6"));
        printState(es, "task6");

        try {
            es.execute(new RunnableTask("task7"));
            printState(es, "task7");
        } catch (RejectedExecutionException e) {
            log("task7 실행 거절 예외 발생: " + e);
        }
        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 ==");
        printState(es);

        es.close();
        log("== shutdown 완료 ==");
        printState(es);

        int nThreads = 2;
    }
}
