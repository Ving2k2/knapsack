package org.example;

public class DynamicProgramming implements Algorithms {
  private int[][] hist;
  private Knapsacks knapsacks;

  public int solve(Knapsacks knapsacks) {
    this.knapsacks = knapsacks;
    int capacity = knapsacks.getCapacity();
    int[] capacities = new int[capacity + 1];
    int n = knapsacks.getItems().length;
    this.hist = new int[n + 1][capacity + 1];

    for (int i = 1; i < n + 1; i++) {
      for (int w = capacity; w >= 0; w--) {
        Item item = knapsacks.getItem(i - 1);
        if (item.weight <= w) {
          capacities[w] = Math.max(capacities[w], capacities[w - item.weight] + item.profit);
        } else {
          break;
        }
      }
      hist[i] = capacities.clone();
    }

    return capacities[capacity];
  }

  @Override
  public int[] getSelectedItems() {
    int n = this.knapsacks.getItems().length;
    int[] selectedItems = new int[n];
    int maxWeight = this.knapsacks.getCapacity();
    int maxProfits = hist[n][maxWeight];

    for (int i = n; i > 0; i--) {
      if (maxProfits != hist[i - 1][maxWeight]) {
        selectedItems[i - 1] = 1;
        maxWeight -= this.knapsacks.getItem(i - 1).weight;
        maxProfits -= this.knapsacks.getItem(i - 1).profit;
      }
    }
    return selectedItems;
  }
}
