package practical;


import java.util.*;

public class Knapsack {
    public static int knapSack(int[] values, int[] weights, int capacity) {
        int[][] K = new int[values.length + 1][capacity + 1];
        for (int i = 0; i <= values.length; i++) {
            K[i][0] = 0;
        }
        for (int w = 0; w <= capacity; w++) {
            K[0][w] = 0;
        }
        for (int i = 1; i <= values.length; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    K[i][w] = Math.max(values[i - 1] + K[i - 1][w - weights[i - 1]], K[i - 1][w]);
                } else {
                    K[i][w] = K[i - 1][w];
                }
            }
        }
        List<Integer> selectedObjects = new ArrayList<>();
        int i = values.length;
        int w = capacity;
        while (i > 0 && w > 0) {
            if (K[i][w] == K[i - 1][w]) {
                i--;
            } else {
                selectedObjects.add(i);
                i--;
                w -= weights[i];
            }
        }
        Collections.sort(selectedObjects);
        System.out.println(selectedObjects);
        return K[values.length][capacity];
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter total number of Objects: ");
        int num =sc.nextInt();
        System.out.print("Capacity : ");
        int capacity =sc.nextInt();
        int[] values = new int[num];
        int[] weights = new int[num];
        for(int i=0;i<num;i++){
            System.out.print("Profit for Object "+(i+1)+" : ");
            values[i]=sc.nextInt();
            System.out.print("Weight for Object "+(i+1)+" : ");
            weights[i]=sc.nextInt();
        }
        long start2 = System.nanoTime();
        int maxValue = knapSack(values, weights, capacity);
        long end2 = System.nanoTime();
        System.out.println("The maximum Profit : " + maxValue);
        System.out.println("\nTime :"+ (end2-start2)+"ns");
        sc.close();
    }
}
