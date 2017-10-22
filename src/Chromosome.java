import java.util.ArrayList;

public class Chromosome {

    public static final int DestinationTask1 = 76;
    public static final int DestinationTask2 = 51;
    public static ArrayList<Destination> point1 = Algorithms.point1;
    public static ArrayList<Destination> point2 = Algorithms.point2;
    private int DataTask1[] = new int[DestinationTask1];
    private int DataTask2[] = new int[DestinationTask2];

    private boolean Selected = false;
    private double SelectionProbability = 0.0;
    private double FactorialCost[] = new double[3];
    private int FactorialRank[] = new int[3];
    private double ScalarFitness;
    private int SkillFactor = 0;
    private int TotalRank = 0;


    public Chromosome() {

        for(int i = 0; i < DestinationTask1; i++)
        {
            this.DataTask1[i] = i;
        }
        for(int j = 0; j< DestinationTask2; j++){
            this.DataTask2[j] = j;
        }
        return;
    }
    public void Cost(){

        switch (SkillFactor){
            case 0:
                this.setFactorialCost(1,point1.get(0).distanceWith(point1.get(DestinationTask1-1)));
                for(int i = 0; i < DestinationTask1-1 ; i++){
                    this.addUpFactorialCost(1, point1.get(this.getDataTask1(i)).distanceWith(point1.get(this.getDataTask1(i+1))));
                }

                this.setFactorialCost(2,point2.get(0).distanceWith(point2.get(DestinationTask2-1)));
                for(int i = 0; i < DestinationTask2-1 ; i++){
                    this.addUpFactorialCost(2, point2.get(this.getDataTask2(i)).distanceWith(point2.get(this.getDataTask2(i+1))));
                }
                break;
            case 1:
                this.setFactorialCost(1,point1.get(0).distanceWith(point1.get(DestinationTask1-1)));
                for(int i = 0; i < DestinationTask1-1 ; i++){
                    this.addUpFactorialCost(1, point1.get(this.getDataTask1(i)).distanceWith(point1.get(this.getDataTask1(i+1))));
                }
                this.setFactorialCost(2,99999999);
                break;
            case 2:
                this.setFactorialCost(2,point2.get(0).distanceWith(point2.get(DestinationTask2-1)));
                for(int i = 0; i < DestinationTask2-1 ; i++){
                    this.addUpFactorialCost(2, point2.get(this.getDataTask2(i)).distanceWith(point2.get(this.getDataTask2(i+1))));
                }
                this.setFactorialCost(1,99999999);
                break;
            default:
                System.out.println("Fail in Cost()");
        }

        return;
    }

    public int getDataTask1(int i) {
        return DataTask1[i];
    }

    public void setDataTask1(int i, int value) {
        DataTask1[i] = value;
    }

    public int getDataTask2(int i) {
        return DataTask2[i];
    }

    public void setDataTask2(int i, int value) {
        DataTask2[i] = value;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public double getSelectionProbability() {
        return SelectionProbability;
    }

    public void setSelectionProbability(double selectionProbability) {
        SelectionProbability = selectionProbability;
    }

    public double getFactorialCost(int i) {
        return FactorialCost[i];
    }

    public void setFactorialCost(int i, double value) {
        FactorialCost[i] = value;
    }

    public void addUpFactorialCost(int i, double value){
        FactorialCost[i] += value;
    }

    public int getFactorialRank(int i) {
        return FactorialRank[i];
    }

    public void setFactorialRank(int i,int value) {
        FactorialRank[i] = value;
    }

    public double getScalarFitness() {
        return ScalarFitness;
    }

    public void setScalarFitness(double scalarFitness) {
        ScalarFitness = scalarFitness;
    }

    public int getSkillFactor() {
        return SkillFactor;
    }

    public void setSkillFactor(int skillFactor) {
        SkillFactor = skillFactor;
    }

    public int getTotalRank() {
        return TotalRank;
    }

    public void setTotalRank(int totalRank) {
        TotalRank = totalRank;
    }
}
