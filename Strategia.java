import java.util.ArrayList;
import java.util.PriorityQueue;

public abstract class Strategia {
    ArrayList<Proces> Procesy;
    PriorityQueue<Proces> Kolejka;

    Strategia(ArrayList<Proces> P) {
        this.Procesy = P;
    }


    public abstract void Wykonaj();

}
