package mk.ukim.finki.os.ispitni.concert;

import mk.ukim.finki.os.ispitni.syncHelperMethods.*;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Concert {

    // Regulatorni semafori
    public static Semaphore tenor;
    public static Semaphore baritone;
    public static Semaphore performer;

    // Notifikaciski semafori
    public static Semaphore tenorHere;
    public static Semaphore baritoneHere;
    public static Semaphore backingHere;
    public static Semaphore ready;
    public static Semaphore finish;

    public static void init() {
        tenor = new Semaphore(3);
        baritone = new Semaphore(3);
        performer = new Semaphore(1);

        tenorHere = new Semaphore(0);
        backingHere = new Semaphore(0);
        baritoneHere = new Semaphore(0);
        ready = new Semaphore(0);
        finish = new Semaphore(0);
    }

    public static class Performer extends TemplateThread {

        public Performer(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            performer.acquire();
            backingHere.acquire(6);
            ready.release(6);

            state.perform();

            //Ceka gi da zavrsat, pusta glasanje i gi osloboduva site pa i sebe si na krajot.
            finish.acquire(6);
            state.vote();
            tenor.release(3);
            baritone.release(3);
            performer.release();
        }

    }

    public static class Baritone extends TemplateThread {

        public Baritone(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            baritone.acquire();
            baritoneHere.release();
            tenorHere.acquire();

            state.formBackingVocals();
            backingHere.release();
            ready.acquire();
            state.perform();
            finish.release();
        }

    }

    public static class Tenor extends TemplateThread {

        public Tenor(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            tenor.acquire();
            tenorHere.release();
            baritoneHere.acquire();

            state.formBackingVocals();
            backingHere.release();
            ready.acquire();
            state.perform();
            finish.release();
        }

    }

    static ConcertState state = new ConcertState();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            int numRuns = 1;
            int numScenarios = 300;

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numScenarios; i++) {
                Tenor t = new Tenor(numRuns);
                Baritone b = new Baritone(numRuns);
                threads.add(t);
                if (i % 3 == 0) {
                    Performer p = new Performer(numRuns);
                    threads.add(p);
                }
                threads.add(b);
            }

            init();

            ProblemExecution.start(threads, state);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}