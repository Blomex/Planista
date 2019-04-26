import java.util.ArrayList;
import java.util.PriorityQueue;

public class RoundRobin extends Strategia {
    private int parametrRR; //parametr z którym wywołujemy RoundRobin

    public RoundRobin(ArrayList<Proces> Procesy, int q){
        super(Procesy);
        parametrRR = q;
        FCFS komparator = new FCFS(null);
        PriorityQueue<Proces> Kolejka= new PriorityQueue<>(10, komparator);
        if(Procesy!=null)
            Kolejka.addAll(Procesy);
        this.Kolejka = Kolejka;
    }

    @Override
    public void Wykonaj(){
        int czas = 0;
        int suma_czasu_wykonywania = 0;
        int suma_czasu_oczekiwania = 0;
        System.out.println("Strategia: RR-"+parametrRR);
        while(!Kolejka.isEmpty()){
            Proces p = Kolejka.poll();
            if(p.getMoment()>czas){
                czas = p.getMoment();
                Kolejka.add(p);
                continue;
            }
            if(p.getZapotrzebowanie()>parametrRR) {//Jeżeli proces potrzebuje więcej czasu do zakończenia niż q, to wykonje się przez q czasu po czym idzie na koniec
                czas += parametrRR;
                for (Proces p2 : Kolejka) {//Dodajemy czas za wszystkie procesy aktualnie oczekujące
                    if (p2.getMoment() < czas) {
                        if (p2.getMoment() < czas - parametrRR) {
                            suma_czasu_oczekiwania += parametrRR;
                            suma_czasu_wykonywania += parametrRR;
                        }
                        else{
                            suma_czasu_oczekiwania += czas - p2.getMoment();
                            suma_czasu_wykonywania += czas - p2.getMoment();
                        }
                    }
            }
                suma_czasu_wykonywania+=parametrRR;
                p = new Proces(czas, p.getZapotrzebowanie()-parametrRR, p.getID());
                Kolejka.add(p);//Ponieważ procesy na wejściu są posortowane względem chwili pojawienia się, więc inne procesy
                               // pojawiające się w chwili równej czas będą miały większe ID, przez co nas proces wejdzie przed nie
            }
            else if(p.getZapotrzebowanie()<= parametrRR){
                czas+=p.getZapotrzebowanie();
                for(Proces p2: Kolejka){
                    if(p2.getMoment()<czas) {
                        if (p2.getMoment() < czas - p.getZapotrzebowanie()){
                            suma_czasu_oczekiwania += p.getZapotrzebowanie();
                            suma_czasu_wykonywania +=p.getZapotrzebowanie();
                        }
                        else{
                            suma_czasu_oczekiwania+=czas - p2.getMoment();
                            suma_czasu_wykonywania+= czas - p2.getMoment();
                        }
                    }
                }
                suma_czasu_wykonywania += (int) p.getZapotrzebowanie();
                p = GetProces(p.getID());
                p.setMoment_zakończenia(czas);

                System.out.print("["+p.getID()+" "+p.getMoment()+" "+String.format("%.2f", (double)p.getMoment_zakończenia()).replace(',','.')+"]");
            }
        }
        System.out.println();
        System.out.println("Średni czas obrotu: "+String.format("%.2f", (double)suma_czasu_wykonywania/Proces.liczba_procesów).replace(',','.'));
        System.out.println("Średni czas oczekiwania: "+String.format("%.2f", (double)suma_czasu_oczekiwania/Proces.liczba_procesów).replace(',','.'));
    }

    /**
     * @brief funkcja szuka w kolekcji procesu o konkretnym id i następnie go zwraca.
     * @param ID ID szukanego procesu
     * @return proces o podanym ID, lub null jeżeli procesu nie było w kolekcji.
     */
    private Proces GetProces(int ID){
        for(Proces p: Procesy){
            if(p.getID()==ID){
                return p;
            }
        }
        return null;
    }

}
