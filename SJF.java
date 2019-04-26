import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF extends Strategia implements Comparator<Proces> {

    public SJF (ArrayList<Proces> Procesy) {
        super(Procesy);
        PriorityQueue<Proces> Kolejka= new PriorityQueue<>(10, this);
        if(Procesy!=null)
            Kolejka.addAll(Procesy);
        this.Kolejka = Kolejka;
    }

    @Override
    public int compare(Proces X, Proces P){
        if(X.getZapotrzebowanie()>P.getZapotrzebowanie()){
            return 1;
        }
        else if (X.getZapotrzebowanie()<P.getZapotrzebowanie()){
            return -1;
        }
        else return Integer.compare(X.getID(), P.getID());
    }

    @Override
    public void Wykonaj(){
        System.out.println("Strategia: SJF");
        int czas = 0;
        int suma_czasu_oczekiwania=0;
        int suma_czasu_wykonania=0;
        int min_moment = Integer.MAX_VALUE;
        while (!Kolejka.isEmpty()){
            Proces p = Kolejka.poll();
            ArrayList<Proces> pom = new ArrayList<>();
            while(p!=null&&p.getMoment()>czas){//Szukamy pierwszego elementu który może się wykonać w aktualnej chwili
                min_moment = Integer.min(min_moment, p.getMoment()-czas);
                pom.add(p);
                p = Kolejka.poll();
            }
            Kolejka.addAll(pom);
            if(p==null){//Nie ma takiego elementu
                czas += min_moment;
                continue;
            }
                suma_czasu_oczekiwania = suma_czasu_oczekiwania + czas - p.getMoment();
                czas += p.getZapotrzebowanie();
                suma_czasu_wykonania = suma_czasu_wykonania + czas - p.getMoment();
            p.setMoment_zakończenia(czas);
            System.out.print("["+p.getID()+" "+p.getMoment()+" "+String.format("%.2f", (double)p.getMoment_zakończenia()).replace(',','.')+"]");
        }
        System.out.println();
        System.out.println("Średni czas obrotu: "+String.format("%.2f", (double)suma_czasu_wykonania/Proces.liczba_procesów).replace(',','.'));
        System.out.println("Średni czas oczekiwania: "+String.format("%.2f", (double)suma_czasu_oczekiwania/Proces.liczba_procesów).replace(',','.'));
    }

}

