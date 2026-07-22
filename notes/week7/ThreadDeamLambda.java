import java.time.LocalDateTime;

public class ThreadDemoLambda {
    public static void main(String[] args) throws InterruptedException {
        // Lambda 表达式实现 Runnable
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.printf("线程：%s，当前时间：%s%n", threadName, LocalDateTime.now());
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("所有子线程执行完成，主线程结束");
    }
}