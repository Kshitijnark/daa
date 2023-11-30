package practical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TSPGeneticAlgorithm {

    static final int NUM_CITIES = 4;
    static final int MAX_GENERATIONS = 100;
    static final int POPULATION_SIZE = 10;
    static final double MUTATION_RATE = 0.1;

    static class City {
        int x;
        int y;

        City(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Route {
        List<Integer> path;
        double fitness;

        Route(List<Integer> path) {
            this.path = path;
        }
    }

    private static double calculateDistance(City city1, City city2) {
        int dx = city1.x - city2.x;
        int dy = city1.y - city2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static Route generateRandomRoute(int numCities) {
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < numCities; ++i) {
            path.add(i);
        }
        Collections.shuffle(path);
        return new Route(path);
    }

    private static void calculateFitness(Route route, List<City> cities, int[][] adjacencyMatrix) {
        double totalDistance = 0.0;
        int numCities = route.path.size();

        for (int i = 0; i < numCities - 1; ++i) {
            int cityIndex1 = route.path.get(i);
            int cityIndex2 = route.path.get(i + 1);
            totalDistance += adjacencyMatrix[cityIndex1][cityIndex2];
        }

        int lastCityIndex = route.path.get(numCities - 1);
        totalDistance += adjacencyMatrix[lastCityIndex][route.path.get(0)];
        route.fitness = totalDistance;
    }

    private static Route crossover(Route parent1, Route parent2) {
        List<Integer> childPath = new ArrayList<>(Collections.nCopies(parent1.path.size(), -1));
        int startPos = new Random().nextInt(parent1.path.size());
        int endPos = new Random().nextInt(parent1.path.size());

        for (int i = 0; i < parent1.path.size(); ++i) {
            if (startPos < endPos && i > startPos && i < endPos) {
                childPath.set(i, parent1.path.get(i));
            } else if (startPos > endPos && !(i < startPos && i > endPos)) {
                childPath.set(i, parent1.path.get(i));
            }
        }

        for (int i = 0; i < parent2.path.size(); ++i) {
            if (!childPath.contains(parent2.path.get(i))) {
                for (int j = 0; j < childPath.size(); ++j) {
                    if (childPath.get(j) == -1) {
                        childPath.set(j, parent2.path.get(i));
                        break;
                    }
                }
            }
        }

        return new Route(childPath);
    }

    private static void mutate(Route route, double mutationRate) {
        Random rand = new Random();

        for (int i = 0; i < route.path.size(); ++i) {
            if (rand.nextDouble() < mutationRate) {
                int swapIndex = rand.nextInt(route.path.size());
                Collections.swap(route.path, i, swapIndex);
            }
        }
    }

    private static Route findBestRoute(List<Route> population) {
        double bestFitness = Double.MAX_VALUE;
        int bestIndex = -1;
        for (int i = 0; i < population.size(); ++i) {
            if (population.get(i).fitness < bestFitness) {
                bestFitness = population.get(i).fitness;
                bestIndex = i;
            }
        }
        return population.get(bestIndex);
    }

    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        cities.add(new City(0, 0));
        cities.add(new City(10, 15));
        cities.add(new City(35, 25));
        cities.add(new City(15, 35));

        int[][] adjacencyMatrix = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        List<Route> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; ++i) {
            Route randomRoute = generateRandomRoute(NUM_CITIES);
            calculateFitness(randomRoute, cities, adjacencyMatrix);
            population.add(randomRoute);
        }

        for (int generation = 0; generation < MAX_GENERATIONS; ++generation) {
            List<Route> newPopulation = new ArrayList<>();

            for (int i = 0; i < POPULATION_SIZE; ++i) {
                Route parent1 = findBestRoute(population);
                Route parent2 = findBestRoute(population);
                Route child = crossover(parent1, parent2);
                mutate(child, MUTATION_RATE);
                calculateFitness(child, cities, adjacencyMatrix);
                newPopulation.add(child);
            }

            population = newPopulation;
        }

        Route bestRoute = findBestRoute(population);

        System.out.print("Best Route: ");
        for (int cityIndex : bestRoute.path) {
            System.out.print(cityIndex + " ");
        }
        System.out.println();

        System.out.println("Total Distance: " + bestRoute.fitness);
    }
}
