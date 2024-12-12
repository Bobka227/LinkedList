/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Generator;

import Lists.AbstrDoubleList;
import Lists.IAbstrDoubleList;
import Obec.Obec;
import Obec.Obyvatel;
import Obec.enumKraje;
import Obec.enumPozice;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author 38067
 */
public class Generator {

    private static final char[] FIRST_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    private static final enumKraje[] KRAJE = enumKraje.values();

    private static final Random RANDOM = new Random();


    private static String generateObecName() {
        char firstLetter = FIRST_LETTERS[RANDOM.nextInt(FIRST_LETTERS.length)];

        int nameLength = 4 + RANDOM.nextInt(6);

        StringBuilder name = new StringBuilder();
        name.append(firstLetter);

        for (int i = 1; i < nameLength; i++) {
            name.append((char) ('a' + RANDOM.nextInt(26)));
        }

        return name.toString();
    }

    public static String generateRandomObec(int pocet, Obyvatel obyvatele) {
        String seznamGenerace = "";
        for (int i = 0; i < pocet; i++) {
            String name = generateObecName();
            int psc = 10000 + RANDOM.nextInt(90000);
            int pocetMuzu = RANDOM.nextInt(1000);
            int pocetZen = RANDOM.nextInt(1000);
            enumKraje kraj = KRAJE[RANDOM.nextInt(enumKraje.values().length)];

            Obec obec = new Obec(name, kraj, psc, pocetMuzu, pocetZen);
            obyvatele.vlozObec(obec, enumPozice.POSLEDNI, kraj);
            seznamGenerace += ("Obec: " + obec.getNazev()
                    + ", PSC: " + obec.getPSC()
                    + ", Muži: " + obec.getPocetMuzu()
                    + ", Ženy: " + obec.getPocetZen()
                    + ", Celkem: " + obec.getCelkem() + "\n");

        }
        return seznamGenerace;
    }

}
