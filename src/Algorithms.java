import random.GenerateRandom;


import java.io.*;
import java.util.ArrayList;

public class Algorithms {

    public static final int DestinationTask1 = 76;
    public static final int DestinationTask2 = 51;
    private static final int OFFSPRING_PER_GENERATION = 50 ;
    public static ArrayList<Destination> point1 = new ArrayList<>();
    public static ArrayList<Destination> point2 = new ArrayList<>();
    public static ArrayList<Chromosome> population = new ArrayList<>();
    public static ArrayList<Chromosome>OffspringPopulation = new ArrayList<>();
    public static ArrayList<Chromosome> Selection = new ArrayList<>();
    public static final int InitialNumber = 100;
    public static final int MinShuffles = 20;
    public static final int MaxShuffles = 40;
    public static final int MinSelection = 10;
    public static final int MaxSelection = 30;
    public static final double rmp = 0.8;
    Chromosome winner1 = new Chromosome();
    Chromosome winner2 = new Chromosome();

    public static int mutations;
    private static  final int MaxEpochs = 1000;


    public void algorithms()throws IOException{
        int Epochs = 0;
        boolean done = false;
        InitializeData();
        InitializePopulation();
        Decoding();
        EvaluateMultitaskingOptimization();

        while (!done){
            AssortativeMating();
            addAllValueToList(population, OffspringPopulation);
            OffspringPopulation.clear();
            Decoding();
            UpdateFactorialCost();

            EvaluateMultitaskingOptimization();
            EvaluateTotalRank();


            NaturalSelection();
            prepareNextEpochs();



            Epochs ++;
            System.out.println("Epochs :" + Epochs);

            if(Epochs == MaxEpochs){
                done = true;
            }
        }

        printBestSolution();

        System.out.println("Algorithms done after " + MaxEpochs + " Epochs ");
    }

    private void InitializeData() throws IOException{
        try{
            FileInputStream fis = new FileInputStream("/Users/Elephant/Học tập/MSO lab/TSPbyMFO/src/eil51.tsp.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine(

            );
            while (line != null){
                String [] arr = line.split(" ");
                if(arr.length ==3){
                    Destination intx;
                    intx = new Destination(Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[0]));
                    point2.add(intx);
                }
                line = br.readLine();
            }
            fis.close();
            isr.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            FileInputStream fis = new FileInputStream("/Users/Elephant/Học tập/MSO lab/TSPbyMFO/src/eil76.tsp.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine(

            );
            while (line != null){
                String [] arr = line.split(" ");
                if(arr.length ==3){
                    Destination intx;
                    intx = new Destination(Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[0]));
                    point1.add(intx);
                }
                line = br.readLine();
            }
            fis.close();
            isr.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void InitializePopulation() {
        int shuffles = 0;
        Chromosome newChromo = null;
        int chromoIndex = 0;

        for(int i = 0; i < InitialNumber; i++)
        {
            newChromo = new Chromosome();
            addValueToList(population,newChromo);
            chromoIndex = population.indexOf(newChromo);

            shuffles = GenerateRandom.getRandomNumber(MinShuffles, MaxShuffles);

            exchangeMutation(chromoIndex, shuffles);

            population.get(chromoIndex).Cost();

        }
        return;
    }
    public static void exchangeMutation(final int index, final int exchanges){
        int i =0;
        int tempData = 0;
        Chromosome thisChromo = null;
        int gene1 = 0;
        int gene2 = 0;
        boolean done = false;

        thisChromo = population.get(index);

        while(!done)
        {
            gene1 = GenerateRandom.getRandomNumber(0, DestinationTask1 - 1);
            gene2 = GenerateRandom.getExclusiveRandomNumber(DestinationTask1 - 1, gene1);

            tempData = thisChromo.getDataTask1(gene1);
            thisChromo.setDataTask1(gene1,thisChromo.getDataTask1(gene2));
            thisChromo.setDataTask1(gene2,tempData);

            if(i == exchanges){
                done = true;
            }
            i++;
        }
        mutations++;
        return;
    }



    private void EvaluateMultitaskingOptimization() {
        UpdateFactorialRank();
        UpdateScalarFitness();
    }
    private void UpdateFactorialRank() {
        int i=0;
        for(Chromosome chromosome : population){
            i++;
            chromosome.setFactorialRank(1,i);
            chromosome.setFactorialRank(2,i);
        }
        for(Chromosome thischromo : population){
            for(Chromosome thatchromo : population){
                if((thischromo.getFactorialCost(1)-thatchromo.getFactorialCost(1))
                        *(thischromo.getFactorialRank(1)-thatchromo.getFactorialRank(1))<0){
                    int temp = thischromo.getFactorialRank(1);
                    thischromo.setFactorialRank(1,thatchromo.getFactorialRank(1));
                    thatchromo.setFactorialRank(1,temp);

                }
                if((thischromo.getFactorialCost(2)-thatchromo.getFactorialCost(2))
                        *(thischromo.getFactorialRank(2)-thatchromo.getFactorialRank(2))<0){
                    int temp = thischromo.getFactorialRank(2);
                    thischromo.setFactorialRank(2,thatchromo.getFactorialRank(2));
                    thatchromo.setFactorialRank(2,temp);
                }
            }
        }

    }
    private void UpdateScalarFitness() {
        for(Chromosome chromosome : population){
            if(chromosome.getFactorialRank(1)<chromosome.getFactorialRank(2)){
                chromosome.setSkillFactor(1);
                chromosome.setScalarFitness((double)1f/chromosome.getFactorialRank(1));
            }
            else {
                chromosome.setSkillFactor(2);
                chromosome.setScalarFitness((double)1f/chromosome.getFactorialRank(2));

            }
        }
    }



    private void UpdateFactorialCost() {
        for(Chromosome chromosome : population){
            chromosome.Cost();

        }
    }


    private void AssortativeMating() {
        rouletteSelection();
        mating();
    }
    private static void rouletteSelection()
    {
        int j = 0;
        int popSize = population.size();
        double genTotal = 0.0;
        double selTotal = 0.0;
        int maximumToSelect = GenerateRandom.getRandomNumber(MinSelection, MaxSelection);
        double rouletteSpin = 0.0;
        Chromosome thisChromo = null;
        Chromosome thatChromo = null;
        boolean done = false;

        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            genTotal += thisChromo.getScalarFitness();
        }

        genTotal *= 0.01;

        for(int i = 0; i < popSize; i++)
        {
            thisChromo = population.get(i);
            thisChromo.setSelectionProbability(thisChromo.getScalarFitness()/genTotal);
        }

        for(int i = 0; i < maximumToSelect; i++)
        {
            rouletteSpin = GenerateRandom.getRandomNumber(0, 99);
            j = 0;
            selTotal = 0;
            done = false;
            while(!done)
            {
                if(j<population.size()){
                    thisChromo = population.get(j);
                }
                else {
                    break;
                }
                selTotal += thisChromo.getSelectionProbability();
                if(selTotal >= rouletteSpin){
                    if(j == 0){
                        thatChromo = population.get(j);
                    }else if(j >= popSize - 1){
                        thatChromo = population.get(popSize - 1);
                    }else{
                        thatChromo = population.get(j - 1);
                    }
                    thatChromo.setSelected(true);
                    done = true;
                }else{
                    j++;
                }
            }
        }
    }
    private static void mating()
    {
        int getRand = 0;
        int parentA = 0;
        int parentB = 0;
        int newIndex1 = 0;
        int newIndex2 = 0;
        Chromosome newChromo1 = null;
        Chromosome newChromo2 = null;
        int rank =101;
        for(int i = 0; i < OFFSPRING_PER_GENERATION; i++)
        {
            parentA = chooseParent();
            getRand = GenerateRandom.getRandomNumber(0, 100);
            if(getRand <= rmp * 100){
                parentB = chooseParent(parentA);
                newChromo1 = new Chromosome();
                newChromo2 = new Chromosome();
                addValueToList(OffspringPopulation, newChromo1);
                newIndex1 = OffspringPopulation.indexOf(newChromo1);
                addValueToList(OffspringPopulation, newChromo2);
                newIndex2 = OffspringPopulation.indexOf(newChromo2);

                partiallyMappedCrossover(parentA, parentB, newIndex1, newIndex2);
                int Rand = 0;
                Rand = GenerateRandom.getRandomNumber(0,99);
                if(Rand < 50){
                    newChromo1.setSkillFactor(population.get(parentA).getSkillFactor());
                }
                else {
                    newChromo1.setSkillFactor(population.get(parentB).getSkillFactor()) ;
                }
                Rand = GenerateRandom.getRandomNumber(0,99);
                if(Rand < 50){
                    newChromo2.setSkillFactor(population.get(parentA).getSkillFactor());
                }
                else {
                    newChromo2.setSkillFactor(population.get(parentB).getSkillFactor());
                }

            }else {
                newChromo1 = population.get(parentA);
                newChromo2 = population.get(parentB);
                addValueToList(OffspringPopulation, newChromo1);
                newIndex1 = OffspringPopulation.indexOf(newChromo1);
                addValueToList(OffspringPopulation, newChromo2);
                newIndex2 = OffspringPopulation.indexOf(newChromo2);
                Mutation(newIndex1,5);
                Mutation(newIndex2,5);
            }
        } // i
        return;
    }
    private static void partiallyMappedCrossover(int chromA, int chromB, int child1, int child2)
    {
        int j = 0;
        int item1 = 0;
        int item2 = 0;
        int pos1 = 0;
        int pos2 = 0;
        Chromosome thisChromo = population.get(chromA);
        Chromosome thatChromo = population.get(chromB);
        Chromosome newChromo1 = OffspringPopulation.get(child1);
        Chromosome newChromo2 = OffspringPopulation.get(child2);
        int crossPoint1 = GenerateRandom.getRandomNumber(0, DestinationTask1 - 1);
        int crossPoint2 = GenerateRandom.getExclusiveRandomNumber(DestinationTask1 - 1, crossPoint1);

        if(crossPoint2 < crossPoint1){
            j = crossPoint1;
            crossPoint1 = crossPoint2;
            crossPoint2 = j;
        }

        for(int i = 0; i < DestinationTask1; i++)
        {
            newChromo1.setDataTask1(i,thisChromo.getDataTask1(i));
            newChromo2.setDataTask1(i,thatChromo.getDataTask1(i));

        }

        for(int i = crossPoint1; i <= crossPoint2; i++)
        {
            item1 = thisChromo.getDataTask1(i);
            item2 = thatChromo.getDataTask1(i);

            for(j = 0; j < DestinationTask1; j++)
            {
                if(newChromo1.getDataTask1(j) == item1){
                    pos1 = j;
                }else if(newChromo1.getDataTask1(j) == item2){
                    pos2 = j;
                }
            } // j

            if(item1 != item2){
                newChromo1.setDataTask1(pos1,item2);
                newChromo1.setDataTask1(pos2,item1);
            }

            for(j = 0; j < DestinationTask1; j++)
            {
                if(newChromo2.getDataTask1(j) == item2){
                    pos1 = j;
                }else if(newChromo2.getDataTask1(j) == item1){
                    pos2 = j;
                }
            } // j

            if(item1 != item2){
                newChromo2.setDataTask1(pos1,item1);
                newChromo2.setDataTask1(pos2,item2);
            }

        }
    }
    private static int chooseParent()
    {
        int parent = 0;
        Chromosome thisChromo;
        boolean done = false;

        while(!done)
        {
            parent = GenerateRandom.getRandomNumber(0, population.size() - 1);
            thisChromo = population.get(parent);
            if(thisChromo.isSelected()){
                done = true;
            }
        }

        return parent;
    }
    private static int chooseParent(final int parentA)
    {
        int parent = 0;
        Chromosome thisChromo;
        boolean done = false;

        while(!done)
        {
            parent = GenerateRandom.getRandomNumber(0, population.size() - 1);
            if(parent != parentA){
                thisChromo = population.get(parent);
                if(thisChromo.isSelected()){
                    done = true;
                }
            }
        }

        return parent;
    }
    private static void Mutation(final int index, final int exchanges){
        int i =0;
        int tempData;
        Chromosome thisChromo;
        int gene1 = 0;
        int gene2 = 0;
        boolean done = false;

        thisChromo = OffspringPopulation.get(index);

        while(!done)
        {
            gene1 = GenerateRandom.getRandomNumber(0, DestinationTask1 - 1);
            gene2 = GenerateRandom.getExclusiveRandomNumber(DestinationTask1 - 1, gene1);


            tempData = thisChromo.getDataTask1(gene1);
            thisChromo.setDataTask1(gene1, thisChromo.getDataTask1(gene2));
            thisChromo.setDataTask1(gene2, tempData);

            if(i == exchanges){
                done = true;
            }
            i++;
        }
        mutations++;
    }


    private void EvaluateTotalRank() {
        int i=0;
        for(Chromosome chromosome : population){
            i++;
            chromosome.setTotalRank(i);
        }
        for(Chromosome thischromo1 : population){
            for(Chromosome thatchromo1 : population){
                if((thischromo1.getTotalRank()-thatchromo1.getTotalRank())
                        *(thischromo1.getScalarFitness()-thatchromo1.getScalarFitness())>0){
                    int temp = thischromo1.getTotalRank();
                    thischromo1.setTotalRank(thatchromo1.getTotalRank());
                    thatchromo1.setTotalRank(temp);
                }

            }
        }

    }


    private void NaturalSelection() {
        for(Chromosome chromosome: population){
            if(chromosome.getTotalRank() < InitialNumber+1){
                addValueToList(Selection, chromosome);
            }
        }
        population.clear();
        addAllValueToList(population, Selection);
    }


    private void prepareNextEpochs(){
        Selection.clear();
        OffspringPopulation.clear();
        for(Chromosome chromosome : population){
            chromosome.setSelected(false);
            if(chromosome.getFactorialRank(1)== 1){
                winner1 = chromosome;
            }
            if(chromosome.getFactorialRank(2) ==1){
                winner2 = chromosome;
            }
        }
    }


    private void printBestSolution(){

        System.out.println("Task 1 result :");
        for(int i =0; i<DestinationTask1; i++){
            System.out.println(" Destination " + i + " : " + winner1.getDataTask1(i) );
        }
        System.out.println("Task 1 Cost : " + winner1.getFactorialCost(1));
        System.out.println("Task 2 result :");
        for(int i =0; i<DestinationTask2; i++){
            System.out.println(" Destination " + i + " : " + winner2.getDataTask2(i) );
        }
        System.out.println("Task 2 Cost : " + winner2.getFactorialCost(2));

    }



    public void Decoding(Chromosome chromosome){
        int j = 0;
            for(int i=0; i<DestinationTask1; i++) {
            if (chromosome.getDataTask1(i) < DestinationTask2) {
                chromosome.setDataTask2(j, chromosome.getDataTask1(i));
                j++;

            }
        }
    }
    public void Decoding(){
        for(Chromosome chromosome : population){
            Decoding(chromosome);
        }
    }

    static void addValueToList(ArrayList<Chromosome> list, Chromosome chromo) {
        if(!list.contains(chromo)) {
            list.add(chromo);
        }
    }
    static void addAllValueToList(ArrayList<Chromosome> list, ArrayList<Chromosome> other){
        for(Chromosome chromosome: other){
            addValueToList(list, chromosome);
        }
    }

}