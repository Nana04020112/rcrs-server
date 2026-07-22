import java.time.LocalDateTime;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        // 定义线程任务
        class PrintTimeTask implements Runnable {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.printf("线程：%s，当前时间：%s%n", threadName, LocalDateTime.now());
            }
        }

        // 创建3个线程
        Thread t1 = new Thread(new PrintTimeTask(), "Thread-1");
        Thread t2 = new Thread(new PrintTimeTask(), "Thread-2");
        Thread t3 = new Thread(new PrintTimeTask(), "Thread-3");

        // 启动线程
        t1.start();
        t2.start();
        t3.start();

        // 主线程等待所有子线程结束
        t1.join();
        t2.join();
        t3.join();
        System.out.println("所有子线程执行完成，主线程结束");
    }
}