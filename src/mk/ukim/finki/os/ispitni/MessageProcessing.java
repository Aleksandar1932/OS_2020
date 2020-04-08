package mk.ukim.finki.os.ispitni;

import java.util.HashSet;
import java.util.concurrent.Semaphore;


public class MessageProcessing {

    static Semaphore messages;
    static int messageCount;
    static Semaphore lock;

    static Semaphore wake;
    static Semaphore request;
    static Semaphore waitMsg;
    static Semaphore done;

    public static void init() {
        messages = new Semaphore(5);
        messageCount = 0;
        lock = new Semaphore(1);
        wake = new Semaphore(0);
        request = new Semaphore(0);
        waitMsg = new Semaphore(0);
        done = new Semaphore(0);
    }

    static class Processor extends Thread {
        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            int processedMessages = 0;

            while (processedMessages < 50) {
                wake.acquire();// wait fotr 5 ready messages in order to activate the processing
                System.out.println("Activate processing");

                for (int i = 1; i <= 5; i++) {
                    request.release();
                    System.out.println("Request message");
                    waitMsg.acquire(); // when the messasge is provided, process it
                    System.out.println("Process message");
                    processedMessages++;
                    done.release();
                }
                Thread.sleep(200);
                if (waitMsg.availablePermits() == 0) { // if there are no more ready messages, pause the processing
                    System.out.println("Processing pause");
                }
            }
        }

    }

    static class MessageSource extends Thread {
        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            messages.acquire();
            lock.acquire();
            messageCount++;
            boolean isCoordinator = (messageCount == 5);

            Thread.sleep(50);
            System.out.println("Message ready");
            if (isCoordinator) {
                wake.release();
            }
            lock.release();
            request.acquire();// wait until the processor requests the message
            System.out.println("Provide message");
            waitMsg.release();
            done.acquire();// wait until the processor is done with the processing of the message
            System.out.println("Message delivered. Leaving.");
            if (isCoordinator) {
                messageCount = 0;
                messages.release(5);
            }
        }

    }


    public static void main(String[] args) throws InterruptedException {
        init();
        HashSet<Thread> threads = new HashSet<Thread>();
        for (int i = 0; i < 50; i++) {
            MessageSource ms = new MessageSource();
            threads.add(ms);
        }
        Thread processor = new Processor();
        processor.start();
        threads.forEach(Thread::start); // start all threads in background

        processor.join();
        for (Thread thread : threads) {
            thread.join(1000);
        }

        boolean notDeadlock = true;

        for (Thread thread : threads) {
            if (thread.isAlive()) {
                notDeadlock = !notDeadlock;
                thread.interrupt();
                System.err.println("Possible deadlock");
            }
        }

        if(notDeadlock){
            System.out.println("Successful synchronization!!!");
        }
    }

}