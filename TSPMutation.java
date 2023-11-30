package practical;

import java.util.Arrays;
import java.util.Random;

public class TSPMutation {

    public static void main(String[] args) {
        // Example usage
        int[][] distanceMatrix = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        int[] initialChromosome = {0, 1, 2, 3};  // Initial order of cities
        double mutationRate = 0.2;  // Probability of mutation for each gene

        int[] mutatedChromosome = tspMutation(initialChromosome, mutationRate);

        System.out.println("Initial Chromosome: " + Arrays.toString(initialChromosome));
        System.out.println("Mutated Chromosome: " + Arrays.toString(mutatedChromosome));
    }

    public static int[] tspMutation(int[] chromosome, double mutationRate) {
        int[] mutatedChromosome = Arrays.copyOf(chromosome, chromosome.length);
        Random random = new Random();

        for (int i = 0; i < mutatedChromosome.length; i++) {
            
                // Swap two random cities to perform mutation
                int j = random.nextInt(mutatedChromosome.length);
                int temp = mutatedChromosome[i];
                mutatedChromosome[i] = mutatedChromosome[j];
                mutatedChromosome[j] = temp;
            
        }

        return mutatedChromosome;
    }
}
