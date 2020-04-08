package mk.ukim.finki.os.lab3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class BarberShop {

    static BarberShopEnvironment barberShopEnv = new BarberShopEnvironment();
    static final int DAILY_QUOTA = 100;
    static Semaphore customers;

    static Semaphore customerHere;
    static Semaphore customerStart;
    static Semaphore customerFinish;

    public static void init() {
        customers = new Semaphore(5);

        customerHere = new Semaphore(0);
        customerStart = new Semaphore(0);
        customerFinish = new Semaphore(0);
    }

    static class Customer extends Thread {
        Integer customerID;

        public Customer(Integer customerID) {
            this.customerID = customerID;
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            customers.acquire();
            customerHere.release();
            customerStart.acquire();
            customerFinish.acquire();
            customers.release();
        }
    }

    static class Barber extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < DAILY_QUOTA; i++) {
                    execute();
                }
                System.out.println("Daily quota reached, closing down!");
                this.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            customerHere.acquire();
            customerStart.release();
            barberShopEnv.doHaircut();
            customerFinish.release();
        }
    }

    static class BarberShopEnvironment {
        public void doHaircut() {
            System.out.println("Doing haircut!!!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        init();
        final int NUM_CLIENTS = 85;
        Barber barber = new Barber();
        barber.start();

        HashSet<Thread> customers = new HashSet<>();
        for (int i = 0; i < NUM_CLIENTS; i++) {
            customers.add(new Customer(i));
        }
        for (Thread t : customers) {
            t.start();
        }

        for (Thread t : customers) {
            t.join(2000);
        }

        if (barber.isAlive()) {
            System.out.println("No more clients, closing down!");
            barber.interrupt();
        }
    }
}