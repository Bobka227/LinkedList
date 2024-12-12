
import Lists.AbstrDoubleList;
import java.util.Iterator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 38067
 */
public class Tester {
    public static void main(String[] args) {
        AbstrDoubleList<Integer> list = new AbstrDoubleList<>();

        // Тестирование вставки элементов в начало списка
        list.vlozPrvni(10);
        list.vlozPrvni(20);
        list.vlozPrvni(30);
        list.vlozPredchudce(40);
        System.out.println("После вставки в начало: ");
        printList(list);  // Ожидается: 30, 20, 40, 10

        // Тестирование вставки элементов в конец списка
        list.vlozPosledni(40);
        list.vlozPosledni(50);
        System.out.println("После вставки в конец: ");
        printList(list);  // Ожидается: 30, 20, 10, 40, 50

        // Тестирование доступа к первому и последнему элементам
        System.out.println("Первый элемент: " + list.zpristupniPrvni());  // Ожидается: 30
        System.out.println("Последний элемент: " + list.zpristuniPosledni());  // Ожидается: 50

        // Тестирование доступа к текущему элементу
        System.out.println("Текущий элемент (последний): " + list.zpristuniAktualni());  // Ожидается: 50

        // Тестирование вставки после текущего элемента
        list.vlozNaslednika(60);
        System.out.println("После вставки 60 после последнего: ");
        printList(list);  // Ожидается: 30, 20, 10, 40, 50, 60

        // Тестирование вставки перед текущим элементом
        list.zpristupniPrvni();
        list.vlozPredchudce(5);
        System.out.println("После вставки 5 перед первым: ");
        printList(list);  // Ожидается: 5, 30, 20, 10, 40, 50, 60

        // Тестирование удаления текущего элемента
        list.zpristuniPosledni();
        list.odeberAktualni();
        System.out.println("После удаления последнего элемента: ");
        printList(list);  // Ожидается: 5, 30, 20, 10, 40, 50

        // Тестирование удаления первого элемента
        list.odeberPrvni();
        System.out.println("После удаления первого элемента: ");
        printList(list);  // Ожидается: 30, 20, 10, 40, 50

        // Тестирование удаления последнего элемента
        list.odeberPosledni();
        System.out.println("После удаления последнего элемента: ");
        printList(list);  // Ожидается: 30, 20, 10, 40
    }

    public static void printList(AbstrDoubleList<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
}

