/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obec;

import Lists.AbstrDoubleList;
import Lists.IAbstrDoubleList;
import static Obec.enumPozice.NASLEDNIK;
import static Obec.enumPozice.POSLEDNI;
import static Obec.enumPozice.PREDCHUDCE;
import static Obec.enumPozice.PRVNI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 * @author 38067
 */
public class Obyvatel implements IObyvatel {

//    private AbstrDoubleList<Obec> seznamObce = new AbstrDoubleList<>();
    private AbstrDoubleList<Obec>[] krajeObceArray;

    public Obyvatel() {
        krajeObceArray = new AbstrDoubleList[enumKraje.values().length];
        inicializujKraje();
    }

    private void inicializujKraje() {
        for (enumKraje kraj : enumKraje.values()) {
            krajeObceArray[kraj.ordinal()] = new AbstrDoubleList<>();
        }
    }

    public AbstrDoubleList<Obec> krajeObceArray(enumKraje kraj) {
        return krajeObceArray[kraj.ordinal()];
    }

    private Obec[][] kraje = new Obec[14][];

    @Override
    public int importData(String soubor) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 7) {
                    System.out.println("nekorektni line: " + line);
                    continue;
                }

                try {
                    int krajIndex = Integer.parseInt(parts[0]) - 1;
                    enumKraje kraj = enumKraje.values()[krajIndex];

                    String obecName = parts[3].trim();
                    int PSC = Integer.parseInt(parts[2].trim());
                    int pocetMuzu = Integer.parseInt(parts[4].trim());
                    int pocetZen = Integer.parseInt(parts[5].trim());

                    Obec novaObec = new Obec(obecName, kraj, PSC, pocetMuzu, pocetZen);

                    krajeObceArray[kraj.ordinal()].vlozPrvni(novaObec);
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("Chyba zmeneni cisla: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("chybny kraj: " + parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private Obec aktualniObec = null;

    @Override
    public Obec zpristupniObec(enumPozice pozice, enumKraje kraj) {

        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        if (seznamObce.jePrazdny()) {
            System.out.println(kraj + "prazdny");
            return null;
        }

        switch (pozice) {
            case PRVNI -> {
                aktualniObec = seznamObce.zpristupniPrvni();
                return aktualniObec;
            }
            case POSLEDNI -> {
                aktualniObec = seznamObce.zpristuniPosledni();
                return aktualniObec;
            }
            case ACTUALNI -> {
                if (aktualniObec == null) {
                    aktualniObec = seznamObce.zpristupniPrvni();
                }
                return aktualniObec;
            }
            case NASLEDNIK -> {
                if (aktualniObec != null) {
                    aktualniObec = seznamObce.zpristupniNaslednika();
                }
                return aktualniObec;
            }
            case PREDCHUDCE -> {
                if (aktualniObec != null) {
                    aktualniObec = seznamObce.zpristuniPredchudce();
                }
                return aktualniObec;
            }
            default ->
                throw new IllegalArgumentException("Invalid position: " + pozice);
        }
    }

    @Override
    public Obec odeberObec(enumPozice pozice, enumKraje kraj) {
        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        if (seznamObce.jePrazdny()) {
            System.out.println(kraj + "prazdny");
            return null;
        }

        switch (pozice) {
            case PRVNI:
                return seznamObce.odeberPrvni();

            case POSLEDNI:
                return seznamObce.odeberPosledni();

            case NASLEDNIK:
                return seznamObce.odeberNaslednika();
            case PREDCHUDCE:
                return seznamObce.odeberPredchudce();

            case ACTUALNI:
                return seznamObce.odeberAktualni();
            default: return null;

        }

    }

//    public List<Obec> getObceProKraj(enumKraje kraj) {
//        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];
//
//        List<Obec> filteredObce = new ArrayList<>();
//        for (Iterator<Obec> it = seznamObce.iterator(); it.hasNext();) {
//            Obec obec = it.next();
//            if (obec.getKraj().equals(kraj)) {
//                filteredObce.add(obec);
//            }
//        }
//        return filteredObce;
//    }

    @Override
    public float zjistiPrumer(enumKraje kraj) {
        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        int celkovyPocetObyvatel = 0;
        int pocetObci = 0;

        Iterator<Obec> iterator = seznamObce.iterator();
        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            if (obec.getKraj() == kraj || kraj == null) {
                celkovyPocetObyvatel += obec.getCelkem();
                pocetObci++;
            }
        }

        if (pocetObci == 0) {
            return 0;
        }

        return (float) celkovyPocetObyvatel / pocetObci;
    }

    @Override
    public void zobrazObce(enumKraje kraj, StringBuilder output) {
        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        if (output == null || seznamObce == null) {
            System.out.println("Output nebo seznamObce se rovna null.");
            return;
        }

        System.out.println("Rozmer seznamObce: " + seznamObce.rozmer());

        Iterator<Obec> iterator = seznamObce.iterator();
        int count = 0;

        while (iterator.hasNext()) {
            Obec obec = iterator.next();

            // Debug
            System.out.println("Aktualni: " + obec.getNazev() + ", Kraj: " + obec.getKraj());

            if (kraj == null || obec.getKraj() == kraj) {
                output.append(obec).append("\n");
                count++;
            }
        }

        // Debug
        System.out.println("Pocet pridanych elementu: " + count);

        if (count == 0) {
            System.out.println("Neni data.");
            output.append("neni data pro zobrazeni.\n");
        }

    }

    @Override
    public void zobrazObceNadPrumer(enumKraje kraj) {
        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        float prumer = zjistiPrumer(kraj);

        Iterator<Obec> iterator = seznamObce.iterator();

        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            if (obec.getKraj() == kraj || kraj == null) {
                if (obec.getCelkem() > prumer) {
                    System.out.println(obec);
                }
            }
        }
    }

    @Override
    public void zrus(enumKraje kraj) {
        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        Iterator<Obec> iterator = seznamObce.iterator();
        if (kraj == null) {
            for (int i = 0; i < enumKraje.values().length; i++) {
                krajeObceArray[i].zrus();
            }
        }
        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            iterator.remove();  
        }

    }

    @Override
    public void vlozObec(Obec obec, enumPozice pozice, enumKraje kraj) {
        AbstrDoubleList<Obec> seznamObce = krajeObceArray[kraj.ordinal()];

        switch (pozice) {
            case PRVNI:
                seznamObce.vlozPrvni(obec);
                break;
            case POSLEDNI:
                seznamObce.vlozPosledni(obec);
                break;
            case NASLEDNIK:
                seznamObce.vlozNaslednika(obec);
                break;
            case PREDCHUDCE:
                seznamObce.vlozPredchudce(obec);
                break;
            default:
                throw new IllegalArgumentException("Neplatná pozice: " + pozice);
        }
        System.out.println(obec);
        //Debug
        System.out.println("rozmer seznamu obce: " + seznamObce.rozmer());
    }

}
