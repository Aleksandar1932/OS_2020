package mk.ukim.finki.os.lab2;

import com.sun.jdi.ThreadReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class BlockingQueue<T> {
    List<T> contents;
    int capacity;
    Lock enqueueLock = new ReentrantLock();
    Lock dequeueLock = new ReentrantLock();

    public BlockingQueue(int capacity) {
//        contents = (T[]) new Object[capacity];
        contents = new ArrayList<>(capacity);
        this.capacity = capacity;
    }

    public List<T> getContents() {
        return contents;
    }

    public void enqueue(T item) {
        try {
            enqueueLock.lock();
            contents.add(item);
        } finally {
            enqueueLock.unlock();
        }
    }

    public T dequeue() {
        T lastElement = null;
        try {
            dequeueLock.lock();
            lastElement = contents.get(contents.size() - 1);
            contents.remove(lastElement);
        } finally {
            dequeueLock.unlock();
        }
        return lastElement;
    }
}

class ThreadExecutor<T> extends Thread {
    private String threadName;
    private int numberToPopulate;
    private BlockingQueue<T> queue;
    private static Random random = new Random();

    public ThreadExecutor(String threadName, int numberToPopulate, BlockingQueue<T> queue) {
        this.threadName = threadName;
        this.numberToPopulate = numberToPopulate;
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberToPopulate; i++) {
            queue.enqueue((T) generateRandomInteger());
        }

        for (int i = 0; i < random.nextInt(numberToPopulate / 2); i++) {
            queue.dequeue();
        }
    }

    public static Integer generateRandomInteger() {
        return random.nextInt(101);
    }
}

public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int capacity = Integer.parseInt(br.readLine());
        int numberOfThreads = Integer.parseInt(br.readLine());

        List<Thread> threads = new ArrayList<>();

        BlockingQueue<Integer> integerBlockingQueue = new BlockingQueue<>(capacity);

        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new ThreadExecutor<>(
                    String.format("%s_%d", "Thread", i),
                    capacity / numberOfThreads,
                    integerBlockingQueue
            );

            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println(String.format("Number: %d Elements: %s",
                integerBlockingQueue.getContents().size(),
                integerBlockingQueue.getContents())
        );
    }
}
