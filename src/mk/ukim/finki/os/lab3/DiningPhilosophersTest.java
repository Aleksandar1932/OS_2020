package mk.ukim.finki.os.lab3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DiningPhilosophersTest {
    public static void main(String args[]) throws InterruptedException {
        DiningPhilosophers.runTest();
    }
}


class DiningPhilosophers {

    private static Random random = new Random(System.currentTimeMillis());
    private Semaphore[] forks = new Semaphore[5];

    public DiningPhilosophers() {
        forks[0] = new Semaphore(1);
        forks[1] = new Semaphore(1);
        forks[2] = new Semaphore(1);
        forks[3] = new Semaphore(1);
        forks[4] = new Semaphore(1);
    }

    public void lifecycleOfPhilosopher(int id) throws InterruptedException {

        while (true) {
            think(id);
            eat(id);
        }
    }

    void think(int id) throws InterruptedException {
        System.out.println(String.format("Philosopher %d is thinking", id));
        Thread.sleep(random.nextInt(50));
    }

    void eat(int id) throws InterruptedException {
        if (forks[id].tryAcquire()) {
            int rightForkID = (id + 1 < forks.length) ? id + 1 : 0;
            if (forks[rightForkID].tryAcquire()) {
                System.out.println(String.format("Philosopher %d is eating", id));
		forks[rightForkID].release();
	        forks[id].release();
            }
            System.err.println(String.format("Philosopher %d cannot eat", id));
            forks[id].release();
        }
    }

    static void runPhilosopher(DiningPhilosophers dp, int id) {
        try {
            dp.lifecycleOfPhilosopher(id);
        } catch (InterruptedException ignored) {

        }
    }

    public static void runTest() throws InterruptedException {
        final DiningPhilosophers dp = new DiningPhilosophers();

        Thread p1 = new Thread(() -> runPhilosopher(dp, 0));

        Thread p2 = new Thread(() -> runPhilosopher(dp, 1));

        Thread p3 = new Thread(() -> runPhilosopher(dp, 2));

        Thread p4 = new Thread(() -> runPhilosopher(dp, 3));

        Thread p5 = new Thread(() -> runPhilosopher(dp, 4));

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

        p1.join();
        p2.join();
        p3.join();
        p4.join();
        p5.join();
    }
}
