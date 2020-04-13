package mk.ukim.finki.os.ispitni.musicBand;

import mk.ukim.finki.os.ispitni.syncHelperMethods.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class MusicBand {

    static Semaphore singers;
    static Semaphore guitarPlayers;
    static Semaphore lock;
    static int memberCount;

    static Semaphore ready;
    static Semaphore done;

    public static void init() {
        singers = new Semaphore(2);
        guitarPlayers = new Semaphore(3);
        lock = new Semaphore(1);
        ready = new Semaphore(0);
        done = new Semaphore(0);
        memberCount = 0;
    }

    static MusicBandState state = new MusicBandState();

    public static class GuitarPlayer extends TemplateThread {

        public GuitarPlayer(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {

            guitarPlayers.acquire();
            lock.acquire();
            memberCount++;
            boolean commander = (memberCount == 5);
            lock.release();
            if (commander) {
                ready.release(5);
            }
            ready.acquire();
            state.play();
            done.release();
            if (commander) {
                done.acquire(5);
                state.evaluate();
                lock.acquire();
                memberCount = 0;
                lock.release();
                guitarPlayers.release(3);
                singers.release(2);
            }
        }

    }

    public static class Singer extends TemplateThread {

        public Singer(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            singers.acquire();
            lock.acquire();
            memberCount++;
            boolean commander = (memberCount == 5);
            lock.release();
            if (commander) {
                ready.release(5);
            }
            ready.acquire();
            state.play();
            done.release();
            if (commander) {
                done.acquire(5);
                state.evaluate();
                lock.acquire();
                memberCount = 0;
                lock.release();
                guitarPlayers.release(3);
                singers.release(2);
            }
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            Scanner s = new Scanner(System.in);
            int numRuns = 1;
            int numIterations = 100;
            s.close();

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numIterations; i++) {
                Singer singer = new Singer(numRuns);
                threads.add(singer);
                GuitarPlayer gp = new GuitarPlayer(numRuns);
                threads.add(gp);
                gp = new GuitarPlayer(numRuns);
                threads.add(gp);
                singer = new Singer(numRuns);
                threads.add(singer);
                gp = new GuitarPlayer(numRuns);
                threads.add(gp);
            }

            init();

            ProblemExecution.start(threads, state);
            System.out.println(new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}