import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
/*Klasa wczytująca wejście*/
public class Wczytywanie {
    /**
     * @brief wczytuje procesy
     * @param plik strumień z którego są wczytywane dane dot. procesów
     * @param liczba_procesow liczba procesów, które wczytujemy
     * @return kolekcję wczytanych procesów
     */
    public static ArrayList<Proces> WczytajProcesy(Scanner plik, int liczba_procesow){
        ArrayList<Proces> Procesy = new ArrayList<>();
        for(int i=2; i<=liczba_procesow+1; i++){
            Scanner linia = new Scanner(plik.nextLine());
            int moment=0;
            int zapotrzebowanie=0;
            if(linia.hasNext()){
                try {
                    moment = linia.nextInt();
                    if(moment<0){
                        System.out.println("Błąd w wierszu "+i+": niepoprawna liczba");
                        System.exit(0);
                    }
                }
                catch(Exception e){
                    System.out.println("Błąd w wierszu "+i+": niepoprawne znaki");
                    System.exit(0);
                }
                if(linia.hasNext()){
                    try {
                        zapotrzebowanie = linia.nextInt();
                        if(zapotrzebowanie<=0){
                            System.out.println("Błąd w wierszu "+i+": niepoprawna liczba");
                            System.exit(0);
                        }
                    }
                    catch(Exception e){
                        System.out.println("Błąd w wierszu "+i+": niepoprawne znaki");
                        System.exit(0);
                    }
                    if(linia.hasNext()){
                        System.out.println("Blad w wierszu"+i+": za dużo danych");
                        System.exit(0);
                    }
                }
                else{
                    System.out.println("Błąd w wierszu"+i+": za mało danych");
                    System.exit(0);
                }
            }
            else{
                System.out.println("Błąd w wierszu"+i+": za mało danych");
                System.exit(0);
            }
            Procesy.add(new Proces(moment, zapotrzebowanie));
        }
        return Procesy;
    }

    /**
     * @brief zwraca liczbą procesów
     * @param plik strumień z którego wczytana jest liczba
     * @return liczba wczytanych procesów
     */
    public static int liczba_procesów(Scanner plik) {
        if (plik.hasNextLine()) {
            try {
                Scanner pierwsza_linia = new Scanner(plik.nextLine());
                if (pierwsza_linia.hasNext()) {
                    int liczba_procesow = Integer.parseInt(pierwsza_linia.next());
                    if (liczba_procesow <= 0) {
                        System.out.println("Błąd w wierszu 1: niepoprawna liczba procesów");
                    }
                    if (pierwsza_linia.hasNext()) {
                        System.out.println("Błąd w wierszu 1: za dużo danych");
                        System.exit(0);
                    }
                    return liczba_procesow;
                }
            } catch (Exception e) {
                System.out.println("Błąd w wierszu 1: Pusty plik");
                System.exit(0);
            }
        } else {
            System.out.println("Błąd w wierszu 1: brak danych o liczbie procesów");
            System.exit(0);
        }
        return -1;
    }

    /**
     * @brief Wczytuje ilość strategii Round Robin, które będą wykonywane
     * @param plik strumień z którego wczytywana jest liczba strategii
     * @param nr_wiersza zmienna pamiętająca aktualny numer wiersza
     * @return liczba całkowita równa ilości strategii Round Robin, które należy wywołać
     */
    public static int liczba_RoundRobin(Scanner plik, int nr_wiersza){
        int liczba_strategii = -1;
        if (!plik.hasNextLine()) {
            System.out.println("Błąd w wierszu " + nr_wiersza + ": brak danych o strategii RoundRobin");
        } else {
            Scanner linia = new Scanner(plik.nextLine());
            if (linia.hasNext()) {
                try {
                    liczba_strategii = linia.nextInt();
                    if(liczba_strategii<0){
                        System.out.println("Błąd w wierszu " + nr_wiersza+" ujemna liczba strategii");
                        System.exit(0);
                    }
                    return liczba_strategii;
                }
                catch(Exception e) {
                    System.out.println("Błąd w wierszu " + nr_wiersza+" niepoprawne znaki");
                    System.exit(0);
                }
                if (linia.hasNext()) {
                    System.out.println("Błąd w wierszu " + nr_wiersza + " za dużo danych");
                    System.exit(0);
                }
            } else {
                System.out.println("Błąd w wierszu" + nr_wiersza + ": brak danych o strategii RoundRobin");
                System.exit(0);
            }
        }
        return liczba_strategii;
    }

    /**
     * @brief wczytuje strategie do tablicy strategii
     * @param plik strumień z którego wczytywane sa parametry do strategii RR
     * @param liczba_strategii liczba strategii RR
     * @param liczba_procesow zmienna mówiąca ile wcześniej było wierszy
     * @param Procesy kolekcja procesów, potrzebna jako konstruktor przy tworzeniu obiektów strategia
     * @return tablica strategii
     */
    public static Strategia[] WczytajStrategie(Scanner plik, int liczba_strategii, int liczba_procesow, ArrayList<Proces> Procesy){
        Strategia[] Strategie;
        if (liczba_strategii > 0) {//liczba strategii RR
            Scanner parametryRR = new Scanner(plik.nextLine());
            int[] parametrRR = new int[liczba_strategii];
            for (int i = 0; i < liczba_strategii; i++) {
                int nr_wiersza = liczba_procesow + i+1;
                if (!parametryRR.hasNext()) {
                    System.out.println("Błąd w wierszu " + nr_wiersza + ": za mało danych");
                    System.exit(0);
                }
                try {
                    parametrRR[i] = parametryRR.nextInt();
                    if(parametrRR[i]<0){
                        System.out.println("Błąd w wierszu "+nr_wiersza+": niepoprawne znaki");
                        System.exit(0);
                    }
                }
                catch(Exception e){
                    System.out.println("Błąd w wierszu "+nr_wiersza+": niepoprawne znaki");
                    System.exit(0);
                }
                if (parametrRR[i] == 0) {
                    System.out.println("Błąd w wierszu " + nr_wiersza + ": 0 nie może być parametrem dla RoundRobin");
                    System.exit(0);
                }
            }
            Strategie = new Strategia[liczba_strategii + 4];
            for (int i = 4; i < liczba_strategii + 4; i++) {
                Strategie[i] = new RoundRobin(Procesy, parametrRR[i - 4]);
            }
        }
        else{
            Strategie = new Strategia[4];
        }
        Strategie[0] = new FCFS(Procesy);
        Strategie[1] = new SJF(Procesy);
        Strategie[2] = new SRT(Procesy);
        Strategie[3] = new PS(Procesy);
        return Strategie;
    }

    /**
     * @brief Wczytuje Skaner, z którego będzie korzystać program
     * @param args argumenty z którymi został wywołany program
     * @return Skaner korzystający z pliku, jeżeli została podana ścieżka do pliku, lub skaner na standardowe wejście
     */
    public static Scanner WczytajSkaner(String[] args){
        Scanner plik;
        String sciezka;
        if(args.length > 1){
            System.out.println("Błąd w ścieżce pliku: za dużo argumentów");
            System.exit(0);
        }
        if (args.length == 1) {
            sciezka = args[0];
            plik = OtworzPlik(sciezka);
        }
        else {
            plik = new Scanner(System.in);
        }
        return plik;
    }

    /**
     * @brief Otwiera plik
     * @param sciezka napis będący ścieżką do pliku
     * @return Skaner na otworzony plik
     */
    private static Scanner OtworzPlik(String sciezka){
        try{
            return new Scanner(new File(sciezka));
        }
        catch(Exception e) {
            System.out.println("Plik z danymi nie jest dostępny.");
            System.exit(0);
        }
        return null;
    }
}