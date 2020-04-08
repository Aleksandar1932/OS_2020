package mk.ukim.finki.os.ispitni;

import java.util.HashSet;
import java.util.concurrent.Semaphore;


public class MacauCardTournament {
    static Semaphore greenPlayers;
    static Semaphore redPlayers;

    static Semaphore greenHere;
    static Semaphore ready;
    static Semaphore done;

    static Semaphore lock;
    static int redCount;


    public static void init() {
        greenPlayers = new Semaphore(2);
        redPlayers = new Semaphore(2);

        greenHere = new Semaphore(0);
        ready = new Semaphore(0);
        done = new Semaphore(0);

        lock = new Semaphore(1);
        redCount = 0;

    }

    static class GreenPlayer extends Thread {

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            greenPlayers.acquire();
            System.out.println("Green player ready");
            Thread.sleep(50);
            System.out.println("Green player here");

            for (int num = 0; num < 3; num++) {
                greenHere.release();
                ready.acquire();
                System.out.println("Game " + num + " started");
                Thread.sleep(200);
                System.out.println("Green player finished game " + num);
                done.release();
            }
        }

    }

    static class RedPlayer extends Thread {

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            redPlayers.acquire();
            System.out.println("Red player ready");

            Thread.sleep(50);
            System.out.println("Red player here");

            lock.acquire();
            redCount++;
            boolean isCoordinator = (redCount == 2);
            lock.release();

            for (int num = 1; num <= 3; num++) {
                if (isCoordinator) {
                    greenHere.acquire(2);
                    ready.release(4);
                }
                ready.acquire();
                System.out.println("Game " + num + " started");
                Thread.sleep(200);
                System.out.println("Red player finished game " + num);
                done.release();
                if (isCoordinator) {
                    done.acquire(4);
                }
            }

            if (isCoordinator) {
                redCount = 0;
                redPlayers.release(2);
                greenPlayers.release(2);
                System.out.println("Match finished");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        init();
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            RedPlayer red = new RedPlayer();
            threads.add(red);
            GreenPlayer green = new GreenPlayer();
            threads.add(green);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join(1000);
        }

        boolean deadLockOccured = false;
        for (Thread t : threads) {
            if (t.isAlive()) {
                deadLockOccured = true;
                t.interrupt();
                System.err.println("Possible deadlock");
            }
        }
        if(!deadLockOccured){
            System.out.println("Synchronization is done correctly!");
        }
    }
}
