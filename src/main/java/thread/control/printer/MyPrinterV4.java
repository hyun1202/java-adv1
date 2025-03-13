package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

import static util.MyLogger.log;

public class MyPrinterV4 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread thread = new Thread(printer, "printer");
        thread.start();

        Scanner userInput = new Scanner(System.in);
        while(true) {
            log("프린터할 문서를 입력하세요. 종료(q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                thread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        // 여러 스레드가 동시에 접근하는 경우 동시성을 지원하는 동시성 컬렉션을 사용해야 함
        Queue<String> jobQueue = new ConcurrentLinkedDeque<>();

        @Override
        public void run() {
            // cpu입장에서 계속 반복문이 돌면서 체크하는데 큐에 작업이 없다면 다른 스레드에게 양보를 해서 성능개선
            while(!Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
                    Thread.yield(); //추가
                    continue;
                }

                try {
                    String job = jobQueue.poll();
                    log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                    Thread.sleep(3000);
                    log("출력 완료");
                } catch (InterruptedException e) {
                    log("인터럽트 발생!");
                    break;
                }
            }
            log("프린터 종료");
        }

        public void addJob(String input) {
            jobQueue.offer(input);
        }
    }
}
