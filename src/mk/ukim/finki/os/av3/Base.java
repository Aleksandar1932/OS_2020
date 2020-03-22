package mk.ukim.finki.os.av3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadExecutor extends Thread {

    private String name; // lokalna za samata niska
    private Incrementor incrementor;

    public ThreadExecutor(String name, Incrementor incrementor) {
        this.name = name;
        this.incrementor = incrementor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            incrementor.safeLockedIncrement();
        }
    }
}

class Incrementor {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(1);

    void unsafeIncrement() {
        count++;
    }

    void safeSynchronizedIncrement() {
        synchronized (this) {
            count++;
        }
    }

    void safeLockedIncrement() {
        lock.lock();
        count++;
        lock.unlock();
    }

    void safeIncrementWithSemaphore() throws InterruptedException {
        semaphore.acquire();
        count++;
        semaphore.release();
    }

    int getCount() {
        return this.count;
    }
}

public class Base {
    public static void threadWithRunnable() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from runnable!");
            }
        });
        t1.start();
    }

    public static void threadWithRunnableLambda() {
        Thread t1 = new Thread(() -> System.out.println("Hello from runnable with lambda!"));
        t1.start();
    }

    public static void main(String[] args) throws InterruptedException {
        threadWithRunnable();
        threadWithRunnableLambda();

        Incrementor incrementor = new Incrementor();
        Thread executor1 = new ThreadExecutor("Thread 1", incrementor); // incrementor is passed by reference
        Thread executor2 = new ThreadExecutor("Thread 2", incrementor);

        executor1.start();
        executor2.start();

        // main se blokira i vo sekoj nareden vremenski interval, blokira se dodeka executor1 i executor2 ne zavrsat so rabota
        executor1.join();
        executor2.join();

        int count = incrementor.getCount();
        System.out.println("Result is: " + count);
    }
}
