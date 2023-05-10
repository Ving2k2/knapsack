package org.example;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;

public class Greedy implements Algorithms {
  private Knapsacks knapsacks;
  private int[] selectedItems;

  public int solve(Knapsacks knapsacks) {
    this.knapsacks = knapsacks;
    Arrays.sort(knapsacks.getItems(), (x, y) -> Double.compare(y.ratio, x.ratio));
    int n = knapsacks.getItems().length;
    selectedItems = new int[n];
    int maxProfits = 0;
    for (int i = 0; i < 3; i++) {
      int currentProfits = solve(knapsacks, i);
      if (currentProfits > maxProfits) {
        maxProfits = currentProfits;
      }
    }

    return maxProfits;
  }

  @Override
  public int[] getSelectedItems() {
    return this.selectedItems;
  }

  public int solve(Knapsacks knapsacks, int k) {
    int n = knapsacks.getItems().length;
    int maxProfits = 0;

    Iterator<int[]> combinations = CombinatoricsUtils.combinationsIterator(n, k);

    while (combinations.hasNext()){
      int[] combination = combinations.next();
      int currentCapacity = knapsacks.getCapacity();

      for (int item : combination) {
        currentCapacity -= knapsacks.getItem(item).weight;
      }

      if (currentCapacity <= 0) {
        continue;
      }

      int[] selectedItems = greedy(knapsacks, combination);
      int currentProfits = 0;

      for (int i = 0; i < n; i++) {
        if (selectedItems[i] == 1) {
          currentProfits += knapsacks.getItem(i).profit;
        }
      }

      for (int item : combination) {
        currentProfits += knapsacks.getItem(item).profit;
      }

      if (maxProfits < currentProfits) {
        maxProfits = currentProfits;
        this.selectedItems = selectedItems;
        for (int item : combination) {
          this.selectedItems[item] = 1;
        }
      }
    }

    return maxProfits;
  }

  public static int[] greedy(Knapsacks knapsacks, int[] M) {
    int n = knapsacks.getItems().length;
    int capacity = knapsacks.getCapacity();

    for (int i : M) {
      capacity -= knapsacks.getItem(i).weight;
    }

    int[] selectedItems = new int[n];
    for (int j = 0; j < n; j++) {
      int temp = j;
      if (IntStream.of(M).noneMatch(x -> x == temp) && knapsacks.getItem(j).weight <= capacity) {
        capacity -= knapsacks.getItem(j).weight;
        selectedItems[j] = 1;
      }
    }

    return selectedItems;
  }
}
