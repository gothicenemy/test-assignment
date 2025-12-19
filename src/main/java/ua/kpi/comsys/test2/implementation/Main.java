package ua.kpi.comsys.test2.implementation;

public class Main {
    public static void main(String[] args) {
        NumberListImpl n1 = new NumberListImpl();
        n1.add(2);

        NumberListImpl n2 = new NumberListImpl();
        n2.add(3);

        System.out.println("Число A: " + n1);
        System.out.println("Число B: " + n2);

        NumberListImpl res1 = NumberListImpl.multiply(n1, n2);
        System.out.println("Результат (2 * 3): " + res1); // Має бути [6]

        System.out.println("-----------------");

        NumberListImpl n3 = new NumberListImpl();
        n3.add(4);

        NumberListImpl n4 = new NumberListImpl();
        n4.add(2);

        NumberListImpl res2 = NumberListImpl.multiply(n3, n4);
        System.out.println("Результат (4 * 2): " + res2); // Має бути [1, 0] або [0, 1] залежно від порядку виводу
    }
}
