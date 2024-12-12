/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Lists;

import java.util.Iterator;

/**
 *
 * @author 38067
 */
public interface IAbstrDoubleList <T> {
    
    void zrus();
    
    boolean jePrazdny();
    
    
    void vlozPrvni(T data);
    void vlozPosledni(T data);
    void vlozNaslednika(T data);
    void vlozPredchudce(T data);
    
    
    T zpristuniAktualni();
    T zpristupniPrvni();
    T zpristuniPosledni();
    T zpristupniNaslednika();
    T zpristuniPredchudce();
    
    
    T odeberAktualni();
    T odeberPrvni();
    T odeberPosledni();
    T odeberNaslednika();
    T odeberPredchudce();
    
    Iterator<T> iterator();
    
    
    
}
