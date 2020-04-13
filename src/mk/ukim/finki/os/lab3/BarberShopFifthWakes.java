package mk.ukim.finki.os.lab3;

//public class BarberShopFifthWakes {
//}

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopFifthWakes {

    //    static Lock countLock = new ReentrantLock();
//    static Semaphore clients = new Semaphore(5);
//    static Lock chair = new ReentrantLock();
//    static Semaphore barberAwake = new Semaphore(0);
    static Semaphore clients;
    static Semaphore countLock;
    static Semaphore ready;
    static Semaphore wakeUp;
    static Semaphore leave;
    static int customersNumber;
    static int istrizeniKorisnici;

    public static void init() {
        clients = new Semaphore(5);
        countLock = new Semaphore(1);
        wakeUp = new Semaphore(0);
        ready = new Semaphore(0);
        leave = new Semaphore(0);
        customersNumber = 0;
        istrizeniKorisnici = 0;
    }


    public static class Barber extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    barber();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Client extends Thread {
        int index = 0;

        public Client(int id) {
            index = id;
        }

        @Override
        public void run() {
            try {
                customerComesIn(index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static int waitingCustomers = 0;

    static void customerComesIn(int index) throws InterruptedException {
        clients.acquire();
        countLock.acquire();
        customersNumber++;
        boolean commander = customersNumber % 5 == 0;
        countLock.release();
        if (commander && wakeUp.availablePermits() == 0) {

            wakeUp.release(); // go budi berberceto
        }
        ready.acquire();
        System.out.println(String.format("Klient %d se sisha", index));
        leave.acquire();
        countLock.acquire();
        istrizeniKorisnici++;
        countLock.release();
        clients.release();
//        //tvojo kod
//        clients.acquire();
//        countLock.lock();
//        waitingCustomers++;
//        if (waitingCustomers == 5 && barberAwake.availablePermits() == 0) {
//            barberAwake.release(5);
//            chair.lock();
//            System.out.println(index + " se shisha");
//            waitingCustomers--;
//            chair.unlock();
//            clients.release();
//            countLock.unlock();
//        } else if (barberAwake.availablePermits() > 0) {
//            chair.lock();
//            System.out.println(index + " se shisha");
//            waitingCustomers--;
//            chair.unlock();
//            clients.release();
//            countLock.unlock();
//        } else countLock.unlock();


        // TODO: 3/29/20 Synchronize this method, invoked by a Customer thread
    }

    static void barber() throws InterruptedException {

//            Thread.sleep(1000);
//            if (barberAwake.tryAcquire()) {
//                System.out.println("Шишање...");
//            } else {
//                System.out.println("Спиење...");
//            }
        System.out.println("Berberot spie...");
        wakeUp.acquire(); //Ceka da go razbudat
        System.out.println("Berberot e razbuden...");

        for (int i = 0; i < 5; i++) {
            ready.release();
//            System.out.println("Berberot sisha");
            leave.release();
        }

    }


    // TODO: 3/29/20 Synchronize this method, invoked by Barber thread


    public static void main(String[] args) throws InterruptedException {
        init();
        HashSet<Thread> threads = new HashSet<>();
        int NUM_CUSTOMERS = 1200;
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            threads.add(new Client(i));

            // TODO: 3/29/20 Synchronize the scenario
        }
        threads.add(new Barber());

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join(1000);
        }

        for (Thread t : threads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
        System.out.println(String.format("Vkupno istrizeni glavi: %d", istrizeniKorisnici));
    }
}