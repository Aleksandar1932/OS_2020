package mk.ukim.finki.os.lab3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Singleton {
    static int initCounter = 0;

    private static volatile Singleton singleton;
    private static Semaphore initalPresence = new Semaphore(1);

    private Singleton() {
        // initialize empty
    }

    public static Singleton getInstance() {
        if (initalPresence.tryAcquire()) {
            singleton = new Singleton();
            synchronized (Singleton.class) {
                initCounter += 1;
            }
        }
        System.out.println(String.format("Instance (%s)", System.identityHashCode(singleton)));
        return singleton;
    }


    public static void main(String[] args) throws InterruptedException {
        final int NUM_TESTS = 1000; // number of test cases
        final int NUM_THREADS = 100; // threads per test case
        for (int testCase = 0; testCase < NUM_TESTS; testCase++) {
            HashSet<Thread> threadHashSet = new HashSet<>();

            for (int i = 0; i < NUM_THREADS; i++) {
                Thread thread = new Thread(Singleton::getInstance);
                threadHashSet.add(thread);
            }

            for (Thread t : threadHashSet) {
                t.start();
            }
            for (Thread t : threadHashSet) {
                t.join();
            }

            if (initCounter == 1) {
                System.out.println(String.format("Test case: %d passed!", testCase));
            } else {
                System.err.println(String.format("Test case: %d failed to synchronize!", testCase));
            }
        }
    }
}