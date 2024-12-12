/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obec;

/**
 *
 * @author 38067
 */
public class Obec {
    private String obec;
    private int PSC;
    private int pocetMuzu;
    private int pocetZen;
    private int celkem;
    private enumKraje kraj;

    public Obec(String obec, enumKraje kraj, int PSC, int pocetMuzu, int pocetZen) {
        this.kraj = kraj;
        this.obec = obec;
        this.PSC = PSC;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = pocetMuzu + pocetZen;
    }

    public int getPSC() {
        return PSC;
    }

    public int getPocetMuzu() {
        return pocetMuzu;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public int getCelkem() {
        return celkem;
    }

    public String getNazev() {
        return obec;
    }

    public enumKraje getKraj() {
        return kraj;
    }

    @Override
    public String toString() {
        return "Obec: " + obec + "; PSC- " + PSC + "; pocetMuzu- " + pocetMuzu + "; pocetZen- " + pocetZen + "; celkem- " + celkem + '.';
    }
}
