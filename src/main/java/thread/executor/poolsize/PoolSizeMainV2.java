package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class PoolSizeMainV2 {
    public static void main(String[] args) {
        // 큐 사이즈에 제한이 없다.
        ExecutorService es = Executors.newFixedThreadPool(2);

        log("poll 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }

        es.close();
        log("== shutdown 완료 ==");
    }
}
