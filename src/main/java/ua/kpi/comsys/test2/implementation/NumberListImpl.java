package ua.kpi.comsys.test2.implementation;

import java.util.*;
import java.io.File;

/**
 * Student: Maksymovskyi Nazar
 * Group: IM-31
 * Student ID: KB14340342
 * Variant: Octal Multiplication (C3=0, C5=2, C7=2)
 */
public class NumberListImpl implements List<Integer> {

    private static class Node {
        Integer value;
        Node next;
        Node prev;

        Node(Integer value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public NumberListImpl() {
        this.size = 0;
    }

    // Конструктор для тестів (String)
    public NumberListImpl(String s) {
        this();
        if (s != null) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    this.add(Character.getNumericValue(c));
                }
            }
        }
    }

    // Конструктор для тестів (File)
    public NumberListImpl(File f) {
        this();
    }

    // Статичний метод для тестів
    public static int getRecordBookNumber() {
        return 14340342;
    }

    // Метод для тестів операцій
    public NumberListImpl additionalOperation(NumberListImpl other) {
        return NumberListImpl.multiply(this, other);
    }

    // Метод для тестів переводу систем
    public String toDecimalString() {
        if (this.isEmpty()) return "";
        NumberListImpl decimal = this.changeScale();
        StringBuilder sb = new StringBuilder();
        for (Integer val : decimal) {
            sb.append(val);
        }
        return sb.toString();
    }

    public void saveList(File f) {}

    // --- Основна логіка: МНОЖЕННЯ (Octal) ---
    public static NumberListImpl multiply(NumberListImpl a, NumberListImpl b) {
        if (isZero(a) || isZero(b)) {
            NumberListImpl zero = new NumberListImpl();
            zero.add(0);
            return zero;
        }

        NumberListImpl result = new NumberListImpl();
        // Важливо: не додаємо сюди початковий 0, щоб не збити додавання

        int shift = 0;
        Node currentB = b.tail;

        while (currentB != null) {
            int digitB = currentB.value;
            NumberListImpl partialProduct = multiplyByDigit(a, digitB);

            // Зсув розрядів (множення на 8^shift)
            for (int i = 0; i < shift; i++) {
                partialProduct.add(0);
            }

            result = addNumbers(result, partialProduct, 8);
            currentB = currentB.prev;
            shift++;
        }

        // Чистка нулів на початку
        while (result.size() > 1 && result.get(0) == 0) {
            result.remove(0);
        }
        if (result.size() == 0) result.add(0);

        return result;
    }

    // --- Переведення в десяткову ---
    public NumberListImpl changeScale() {
        NumberListImpl decimalResult = new NumberListImpl();
        decimalResult.add(0);

        Node current = this.head;
        while (current != null) {
            decimalResult = multiplyByDigit(decimalResult, 8); // scale factor 8 -> base 10 calculation

            NumberListImpl digitList = new NumberListImpl();
            digitList.add(current.value);

            decimalResult = addNumbers(decimalResult, digitList, 10); // add in base 10
            current = current.next;
        }

        while (decimalResult.size() > 1 && decimalResult.get(0) == 0) {
            decimalResult.remove(0);
        }
        return decimalResult;
    }

    // --- Допоміжні методи ---

    private static boolean isZero(NumberListImpl list) {
        return list == null || list.size == 0 || (list.size == 1 && list.get(0) == 0);
    }

    private static NumberListImpl multiplyByDigit(NumberListImpl num, int factor) {
        NumberListImpl result = new NumberListImpl();
        if (num.size() == 0) return result;

        if (factor == 0) {
            result.add(0);
            return result;
        }

        int carry = 0;
        Node curr = num.tail;
        int base = (factor == 8) ? 10 : 8;

        while (curr != null || carry > 0) {
            int val = (curr != null ? curr.value : 0);
            long prod = (long) val * factor + carry;

            int digit = (int) (prod % base);
            carry = (int) (prod / base);

            result.add(0, digit);
            if (curr != null) curr = curr.prev;
        }
        return result;
    }

    private static NumberListImpl addNumbers(NumberListImpl n1, NumberListImpl n2, int base) {
        NumberListImpl sumList = new NumberListImpl();
        Node p1 = n1.tail;
        Node p2 = n2.tail;
        int carry = 0;

        while (p1 != null || p2 != null || carry != 0) {
            int v1 = (p1 != null) ? p1.value : 0;
            int v2 = (p2 != null) ? p2.value : 0;
            int sum = v1 + v2 + carry;

            sumList.add(0, sum % base);
            carry = sum / base;

            if (p1 != null) p1 = p1.prev;
            if (p2 != null) p2 = p2.prev;
        }
        return sumList;
    }

    // --- List implementation ---
    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public boolean contains(Object o) { for (Integer val : this) if (Objects.equals(val, o)) return true; return false; }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private Node current = head;
            @Override public boolean hasNext() { return current != null; }
            @Override public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                Integer val = current.value;
                current = current.next;
                return val;
            }
        };
    }

    @Override public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0; for (Integer val : this) arr[i++] = val; return arr;
    }
    @Override public <T> T[] toArray(T[] a) { return a; }

    @Override
    public boolean add(Integer integer) {
        Node newNode = new Node(integer);
        if (head == null) { head = newNode; tail = newNode; }
        else { tail.next = newNode; newNode.prev = tail; tail = newNode; }
        size++; return true;
    }

    @Override
    public void add(int index, Integer element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) { add(element); return; }
        Node newNode = new Node(element);
        if (index == 0) {
            newNode.next = head; if (head != null) head.prev = newNode; head = newNode; if (tail == null) tail = newNode;
        } else {
            Node current = getNode(index); Node prev = current.prev;
            prev.next = newNode; newNode.prev = prev; newNode.next = current; current.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(Object o) {
        Node current = head;
        while (current != null) {
            if (Objects.equals(current.value, o)) { unlink(current); return true; }
            current = current.next;
        }
        return false;
    }
    @Override
    public Integer remove(int index) {
        Node node = getNode(index); Integer val = node.value; unlink(node); return val;
    }
    private void unlink(Node node) {
        Node prev = node.prev; Node next = node.next;
        if (prev == null) head = next; else { prev.next = next; node.prev = null; }
        if (next == null) tail = prev; else { next.prev = prev; node.next = null; }
        node.value = null; size--;
    }
    @Override public boolean containsAll(Collection<?> c) { for (Object e : c) if (!contains(e)) return false; return true; }
    @Override public boolean addAll(Collection<? extends Integer> c) { boolean m = false; for (Integer e : c) if (add(e)) m = true; return m; }
    @Override public boolean addAll(int index, Collection<? extends Integer> c) { boolean m = false; for (Integer e : c) { add(index++, e); m = true; } return m; }
    @Override public boolean removeAll(Collection<?> c) { boolean m = false; for (Object e : c) while (contains(e)) { remove(e); m = true; } return m; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public void clear() { head = null; tail = null; size = 0; }
    @Override public Integer get(int index) { return getNode(index).value; }
    @Override public Integer set(int index, Integer element) { Node n = getNode(index); Integer old = n.value; n.value = element; return old; }
    private Node getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current;
        if (index < size / 2) { current = head; for (int i = 0; i < index; i++) current = current.next; }
        else { current = tail; for (int i = size - 1; i > index; i--) current = current.prev; }
        return current;
    }
    @Override public int indexOf(Object o) { int i = 0; for (Node x = head; x != null; x = x.next) { if (Objects.equals(o, x.value)) return i; i++; } return -1; }
    @Override public int lastIndexOf(Object o) { int i = size - 1; for (Node x = tail; x != null; x = x.prev) { if (Objects.equals(o, x.value)) return i; i--; } return -1; }
    @Override public ListIterator<Integer> listIterator() { throw new UnsupportedOperationException(); }
    @Override public ListIterator<Integer> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public List<Integer> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
    @Override public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node curr = head;
        while (curr != null) { sb.append(curr.value); if (curr.next != null) sb.append(", "); curr = curr.next; }
        sb.append("]"); return sb.toString();
    }
}
