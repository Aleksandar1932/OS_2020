/*

    Realizirano so pomosh na ReentrantLock():

 */

package mk.ukim.finki.os.lab2;

import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountSeven {

    public static int NUM_RUNS = 100;

    int count = 0;
    Semaphore s = new Semaphore(1);

    public void incrementCount(int toIncrement) throws InterruptedException {
        s.acquire();
        count += toIncrement;
        s.release();
    }

    public void init() {

    }

    class Counter extends Thread {
        private int threadCounter = 0;

        public void count(int[] data) throws InterruptedException {

            for (int number : data) {
                if (number == 7) {
                    threadCounter++;
                }
            }
            incrementCount(threadCounter);
        }

        private int[] data;

        public Counter(int[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                count(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            CountSeven environment = new CountSeven();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {

        init();

        HashSet<Thread> threads = new HashSet<>();
        Scanner s = new Scanner(System.in);
        int total = s.nextInt();

        for (int i = 0; i < NUM_RUNS; i++) {
            int[] data = new int[total];
            for (int j = 0; j < total; j++) {
                data[j] = s.nextInt();
            }
            Counter c = new Counter(data);
            threads.add(c);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println(count);

    }
}
