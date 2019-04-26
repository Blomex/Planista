import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FCFS extends Strategia implements Comparator<Proces> {
    public FCFS(ArrayList<Proces> Procesy) {
        super(Procesy);
        PriorityQueue<Proces> Kolejka= new PriorityQueue<>(10, this);
        if(Procesy!=null)
            Kolejka.addAll(Procesy);
        this.Kolejka = Kolejka;
    }

    @Override
    public int compare(Proces X, Proces P){
        if(X.getMoment()>P.getMoment()){
            return 1;
        }
        else if (X.getMoment()<P.getMoment()){
            return -1;
        }
        else return Integer.compare(X.getID(), P.getID());
    }

    @Override
    public void Wykonaj(){
        System.out.println("Strategia: FCFS");
        int czas = 0;
        int suma_czasu_oczekiwania=0;
        int suma_czasu_wykonania=0;
        while (!Kolejka.isEmpty()){
            Proces p = Kolejka.poll();
            assert (p!=null);
            if(p.getMoment()>czas){
                czas = p.getMoment();
            }
            suma_czasu_oczekiwania = suma_czasu_oczekiwania + czas - p.getMoment();
            czas +=p.getZapotrzebowanie();
            suma_czasu_wykonania = suma_czasu_wykonania + czas - p.getMoment();

            p.setMoment_zakończenia(czas);
            System.out.print("["+p.getID()+" "+p.getMoment()+" "+String.format("%.2f", (double)p.getMoment_zakończenia()).replace(',','.')+"]");
        }
        System.out.println();
        System.out.println("Średni czas obrotu: "+String.format("%.2f", (double)suma_czasu_wykonania/Proces.liczba_procesów).replace(',','.'));
        System.out.println("Średni czas oczekiwania: "+String.format("%.2f", (double)suma_czasu_oczekiwania/Proces.liczba_procesów).replace(',','.'));
    }

}
