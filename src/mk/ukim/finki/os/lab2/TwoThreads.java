package mk.ukim.finki.os.lab2;

class ThreadAB {
    String argument1;
    String argument2;

    public ThreadAB(String argument1, String argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    void run() {
        Thread thread = new Thread(() -> System.out.println(
                String.format("%s\n%s", argument1, argument2)
        ));
        thread.start();
    }
}

public class TwoThreads {
    public static void main(String[] args) {
        new ThreadAB("A", "B").run();
        new ThreadAB("1", "2").run();
    }
}

/*
Излезот од програмата не би можел да се предвиде со оглед на моменталната ситуација, бидејќи никогаш не се користи .join за одреден thread да почека, па доколку би имале печатење на броевите од 1 до 15 или буквите од А до F, резултатот никогаш не е предвидлив. Дури и во сегашниот случај со 1,2 и буквите А, В резултатот не е предвидлив.
*/