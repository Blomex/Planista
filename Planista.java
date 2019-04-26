import java.util.ArrayList;
import java.util.Scanner;

public class Planista {
    public static void main(String []args) {
        Strategia [] pomocnicza;
        int liczba_strategii=0; //liczba strategii RR
        Scanner plik = Wczytywanie.WczytajSkaner(args);
        int liczba_procesow = Wczytywanie.liczba_procesów(plik);
        ArrayList<Proces> Procesy = Wczytywanie.WczytajProcesy(plik, liczba_procesow);
        liczba_procesow += 2;
        liczba_strategii = Wczytywanie.liczba_RoundRobin(plik, liczba_procesow);
        pomocnicza = Wczytywanie.WczytajStrategie(plik, liczba_strategii, liczba_procesow, Procesy);
        for(Strategia s: pomocnicza){
            s.Wykonaj();
            System.out.println();
        }
    }
}
