//Samantha Barnum
//CS 1181L-07
//Project 4
//4/7/22

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyThread extends Thread {

    private ArrayList<Chromosome> currentPopulation;
    private Chromosome bestChromosome;
    private int bestFitness;

    public MyThread(ArrayList<Chromosome> currentPopulation) {
        this.currentPopulation = new ArrayList<>();
        for (Chromosome c : currentPopulation) {
            this.currentPopulation.add(new Chromosome(c));
        }
    }

    @Override
    public void run() {
        Random rng = new Random();
        ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();

        for (int o = 0; o < (GeneticAlgorithm.NUM_EPOCHS / GeneticAlgorithm.NUM_THREADS); o++) {

            for (int i = 0; i < currentPopulation.size(); i++) {
                nextGeneration.add(currentPopulation.get(i));
            }

            Collections.shuffle(currentPopulation);
            for (int i = 0; i < currentPopulation.size(); i += 2) {
                Chromosome currentParent = currentPopulation.get(i);
                Chromosome otherParent = currentPopulation.get(i + 1);
                Chromosome child = currentParent.crossover(otherParent);
                nextGeneration.add(child);
            }

            for (int i = 0; i < nextGeneration.size(); i++) {
                if (rng.nextInt(10) == 7) {
                    nextGeneration.get(i).mutate();
                }
            }
            Collections.sort(nextGeneration);
            currentPopulation.clear();

            for (int i = 0; i < GeneticAlgorithm.POP_SIZE; i++) {
                currentPopulation.add(nextGeneration.get(i));
            }

            nextGeneration.clear();
            o++;
        }
        Collections.sort(currentPopulation);
        bestChromosome = currentPopulation.get(0);
    }

    public Chromosome getBest() {
        return bestChromosome;
    }

    public int getFitness() {
        bestFitness = bestChromosome.getFitness();
        return bestFitness;
    }
}