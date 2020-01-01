import Mutil.BankServiceWindows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProduceConsumer {
    //定义一个队列缓冲区，数据为Integer
    private static Queue<Integer> queue = new LinkedList<Integer>();

    //设置缓冲区最大容量
    private static final int MAX_SIZE = 100;


    // 独占锁

    //        final BankServiceWindow bankServiceWindow=new BankServiceWindow();
    // 共享锁
    final BankServiceWindows bankServiceWindow = new BankServiceWindows(MAX_SIZE);

    class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    if (queue.size() < MAX_SIZE) {
                        int num = new Random().nextInt(100);
                        queue.offer(num);
                        queue.notifyAll();
                        System.out.println("生产者" + Thread.currentThread().getName() + "生产了产品：" + num + "，此时缓冲区数据量为：" + queue.size() + System.currentTimeMillis());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                // 要保证queue的线程安全
                synchronized (queue) {
                    if (queue.size() > 0) {
                        int num = queue.poll();
                        System.out.println("消费者" + Thread.currentThread().getName() + "消费了产品：" + num + "，此时缓冲区数据量为：" + queue.size() + System.currentTimeMillis());
                        queue.notifyAll();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumer pc = new ProduceConsumer();

        //Thread构造函数需要一个Runnable对象即可构造一个新的线程，Runnable对象可以重复利用，不必new多个
        //一个消费者，一个生产者
        Consumer c = pc.new Consumer();
        Producer p = pc.new Producer();

        //生产者和消费者谁先start都一样
        new Thread(c).start();
//        new Thread(c).start();
//        new Thread(c).start();
//        new Thread(c).start();
//        new Thread(c).start();
//
//        new Thread(p).start();
//        new Thread(p).start();
//        new Thread(p).start();
//        new Thread(p).start();
        new Thread(p).start();

    }


}