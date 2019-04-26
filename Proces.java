public class Proces {
    private int moment;
    private double zapotrzebowanie;
    private int ID;
    static int liczba_procesów;
    private double moment_zakończenia;

    public Proces(int moment, double zapotrzebowanie){
        this.moment = moment;
        this.zapotrzebowanie = zapotrzebowanie;
        ID = ++liczba_procesów;
        this.moment_zakończenia = -1;
    }
    public Proces(int moment, double zapotrzebowanie, int ID){
        this.moment = moment;
        this.zapotrzebowanie = zapotrzebowanie;
        this.ID = ID;
    }


    public int getMoment() {
        return moment;
    }

    public int getID(){
        return ID;
    }

    public double getZapotrzebowanie() {
        return zapotrzebowanie;
    }

    public void setMoment_zakończenia(double moment_zakonczenia) {
        this.moment_zakończenia = moment_zakonczenia;
    }

    public double getMoment_zakończenia() {
        return moment_zakończenia;
    }

    @Override
    public String toString() {
        return "moment:"+moment+" zapotrzebowanie:"+zapotrzebowanie;
    }
}
