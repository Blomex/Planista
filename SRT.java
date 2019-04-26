import java.util.ArrayList;
import java.util.PriorityQueue;

public class SRT extends Strategia {

    public SRT(ArrayList<Proces> Procesy){
        super(Procesy);
        SJF nowy = new SJF(null);
        PriorityQueue<Proces> Kolejka= new PriorityQueue<>(10, nowy);
        if(Procesy!=null)
            Kolejka.addAll(Procesy);
        this.Kolejka = Kolejka;
    }

    @Override
    public void Wykonaj(){
        System.out.println("Strategia: SRT");
        double czas = 0;
        double suma_czasu_oczekiwania=0;
        double suma_czasu_wykonania=0;
        double min_moment = Double.MAX_VALUE;
        while (!Kolejka.isEmpty()){
            Proces p = Kolejka.poll();
            ArrayList<Proces> pom = new ArrayList<>();
            while(p!=null&&p.getMoment()>czas){//Szukamy pierwszego elementu który może się wykonać w aktualnej chwili
                min_moment = Double.min(min_moment, p.getMoment()-czas);
                pom.add(p);
                p = Kolejka.poll();
            }
            Kolejka.addAll(pom);
            if(p==null){//Nie ma procesu który aktualnie może się wykonywać, procesor jest bezczynny
                czas +=min_moment;
                continue;
            }
            while(p.getZapotrzebowanie()>0){
                p = new Proces(p.getMoment(), p.getZapotrzebowanie()-1.0, p.getID());
                czas+=1;
                int i=0;
                for(Proces p2: Kolejka){
                    if(p2.getMoment()<czas){
                        i++;
                    }
                }
                suma_czasu_oczekiwania+=i;
                suma_czasu_wykonania += i+1;
                if(!Kolejka.isEmpty()){//Sprawdzamy czy w kolejce nie ma procesu, który miałby pierwszeństwo
                    ArrayList<Proces> pom2 = new ArrayList<>();
                    Proces p2 = Kolejka.poll();
                    while(p2.getZapotrzebowanie()<p.getZapotrzebowanie()&&!Kolejka.isEmpty()){
                        if(p2.getMoment()<=czas){
                            Kolejka.add(p);
                            p = p2;
                            break;
                        }
                        else{
                            pom2.add(p2);
                            p2 = Kolejka.poll();
                        }
                    }
                    if ((p2.getMoment()>p.getMoment()||p2.getZapotrzebowanie() >= p.getZapotrzebowanie())&&p.getID()!=p2.getID()){
                        pom2.add(p2);
                    }
                    Kolejka.addAll(pom2);
                }
            }
            p.setMoment_zakończenia(czas);
            System.out.print("["+p.getID()+" "+p.getMoment()+" "+String.format("%.2f", (double)p.getMoment_zakończenia()).replace(',','.')+"]");
        }


        System.out.println();
        System.out.println("Średni czas obrotu: "+String.format("%.2f", suma_czasu_wykonania/Proces.liczba_procesów).replace(',','.'));
        System.out.println("Średni czas oczekiwania: "+String.format("%.2f", suma_czasu_oczekiwania/Proces.liczba_procesów).replace(',','.'));
    }

}
