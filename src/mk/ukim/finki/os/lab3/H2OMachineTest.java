package mk.ukim.finki.os.lab3;


import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class H2OMachine {
    static AtomicInteger printRowCounter = new AtomicInteger(1);
    String[] molecule;
    int count;

    public static Semaphore o;
    public static Semaphore h;
    public static Semaphore oHere;
    public static Semaphore hHere;
    public static Semaphore ready;

    public static void init() {
        o = new Semaphore(1);
        h = new Semaphore(2);
        oHere = new Semaphore(0);
        hHere = new Semaphore(0);
        ready = new Semaphore(0);
    }

    public H2OMachine() {
        molecule = new String[3];
        count = 0;
        init();
    }

    public void hydrogen() throws InterruptedException {
        // DONE: 4/01/20 synchronized logic here
        h.acquire(1);
        oHere.acquire(1);
        hHere.release(1);
        ready.acquire(1);

    }

    public void oxygen() throws InterruptedException {
        // DONE: 4/01/20 synchronized logic here
        o.acquire(1);
        oHere.release(2);
        hHere.acquire(2);
        ready.release(2);
        System.out.println(String.format("[%d]The molecule is formed", printRowCounter.getAndIncrement()));
        o.release(1);
        h.release(2);
    }
}

class H2OThread extends Thread {

    H2OMachine molecule;
    String atom;

    public H2OThread(H2OMachine molecule, String atom) {
        this.molecule = molecule;
        this.atom = atom;
    }

    public void run() {
        if ("H".equals(atom)) {
            try {
                molecule.hydrogen();
            } catch (Exception e) {
            }
        } else if ("O".equals(atom)) {
            try {
                molecule.oxygen();
            } catch (Exception e) {
            }
        }
    }
}

public class H2OMachineTest {
    public static void main(String[] args) throws InterruptedException {
        final int NUM_TRIALS = 100;
        H2OMachine machine = new H2OMachine();
        String[] moleculesPool = {"H", "O"};

        HashSet<Thread> threads = new HashSet<>();

        AtomicInteger hCounter = new AtomicInteger(0);

        for (int i = 0; i < NUM_TRIALS; i++) {
            String molecule = moleculesPool[new Random().nextInt(2)];
            if (molecule == "H") {
                hCounter.incrementAndGet();
            }
            threads.add(new H2OThread(machine, molecule));
        }

        System.out.println(String.format("H : %d\nO : %d\nExpected H20: %d ",
                hCounter.get(),
                NUM_TRIALS - hCounter.get(),
                Math.min((hCounter.get() / 2), (1000 - hCounter.get()))
        ));

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
}
