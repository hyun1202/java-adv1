package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class MyPrinterV2 {
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
        // 여러 스레드가 동시에 접근하는 변수에는 volatile 키워드를 붙여주어야 함
        volatile boolean work = true;
        // 여러 스레드가 동시에 접근하는 경우 동시성을 지원하는 동시성 컬렉션을 사용해야 함
        Queue<String> jobQueue = new ConcurrentLinkedDeque<>();

        @Override
        public void run() {
            while(true) {
                if (jobQueue.isEmpty()) {
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
