import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PS extends Strategia implements Comparator<Proces>{
    public PS(ArrayList<Proces> Procesy){
        super(Procesy);
        FCFS komparator = new FCFS(null);
        PriorityQueue<Proces> Kolejka= new PriorityQueue<>(10, komparator);
        if(Procesy!=null)
            Kolejka.addAll(Procesy);
        this.Kolejka = Kolejka;
    }

    @Override
    public int compare(Proces p1, Proces p2) {
        return Integer.compare(p1.getID(), p2.getID());
    }

    @Override
    public void Wykonaj() {
        SJF komparator = new SJF(null);
        PriorityQueue<Proces> Aktualnie_wykonywane= new PriorityQueue<>(10, komparator);
        System.out.println("Strategia: PS");
        double czas =0;
        double suma_czasu_wykonania=0;
        while(!Kolejka.isEmpty()||!Aktualnie_wykonywane.isEmpty()) {
            Proces p;
            if(!Kolejka.isEmpty()) {
                p = Kolejka.poll();
                if(Aktualnie_wykonywane.isEmpty()&&p.getMoment()>czas){
                    Kolejka.add(p);
                    czas+=p.getMoment()-czas;
                    continue;
                }
                while (p.getMoment() <= czas) {
                    Aktualnie_wykonywane.add(p);
                    p = Kolejka.poll();
                    if(p==null)
                        break;
                }
                if (p!=null&&p.getMoment() > czas) {
                    Kolejka.add(p);
                } else if(p!=null){
                    Aktualnie_wykonywane.add(p);
                }
            }
            p = Aktualnie_wykonywane.poll();
            double moc_na_jednostke = (double)1/(1 + Aktualnie_wykonywane.size());
            double akt_moc;
            boolean CzyZakonczony;
            if (p.getZapotrzebowanie() <= moc_na_jednostke) {
                akt_moc = p.getZapotrzebowanie();
                CzyZakonczony = true;
            } else {
                akt_moc = moc_na_jednostke;
                CzyZakonczony = false;
            }

            PriorityQueue<Proces> poObrocie = new PriorityQueue<>(10, komparator);
            for (Proces p2 : Aktualnie_wykonywane) {
                p2 = new Proces(p2.getMoment(), p2.getZapotrzebowanie()-akt_moc, p2.getID());
                poObrocie.add(p2);
            }
            Aktualnie_wykonywane = poObrocie;
            czas += akt_moc * (1+ Aktualnie_wykonywane.size());

            if (CzyZakonczony) {
                ArrayList<Proces> zakonczone = new ArrayList<>();
                p.setMoment_zakończenia(czas);
                zakonczone.add(p);
                for (Proces p2 : Aktualnie_wykonywane) {
                    if (p2.getZapotrzebowanie() == 0) {
                        p2.setMoment_zakończenia(czas);
                        zakonczone.add(p2);
                    }
                }
                Aktualnie_wykonywane.removeAll(zakonczone);
                zakonczone.sort(this);
                for(Proces p2: zakonczone) {
                    p2.setMoment_zakończenia(czas);
                    suma_czasu_wykonania +=p2.getMoment_zakończenia()-p2.getMoment();
                    System.out.print("[" + p.getID() + " " + p.getMoment() + " " + String.format("%.2f", (double) p.getMoment_zakończenia()).replace(',','.') + "]");
                }
            }
            else{
                Aktualnie_wykonywane.add(new Proces(p.getMoment(), p.getZapotrzebowanie() - akt_moc, p.getID()));

            }
        }
        System.out.println();
        System.out.println("Średni czas obrotu: "+String.format("%.2f", suma_czasu_wykonania/Proces.liczba_procesów).replace(',','.'));
        System.out.println("Średni czas oczekiwania: 0.00");

    }

}
