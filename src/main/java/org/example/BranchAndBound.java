package org.example;

import java.util.Arrays;

public class BranchAndBound implements Algorithms {
  private int z;
  private int zHat;
  private int j;
  private int[] x;
  private int[] xHat;
  private int cHat;
  private int n;

  public int solve(Knapsacks knapsacks) {
    Arrays.sort(knapsacks.getItems(), (x, y) -> Double.compare(y.ratio, x.ratio));

    z = 0;
    zHat = 0;
    j = 0;
    cHat = knapsacks.getCapacity();
    n = knapsacks.getItems().length + 1;

    Item[] temp = new Item[n];
    temp[n - 1] = new Item(Integer.MAX_VALUE, 0);
    Item[] temp2 = new Item[n - 1];
    System.arraycopy(knapsacks.getItems(), 0, temp2, 0, n - 1);
    System.arraycopy(knapsacks.getItems(), 0, temp, 0, n - 1);
    knapsacks.setItems(temp);

    x = new int[n];
    xHat = new int[n];

    while (true) {
      computeUpperBound(knapsacks);

      while (true) {
        Item[] items = knapsacks.getItems();
        while (items[j].weight <= cHat) {
          cHat -= items[j].weight;
          zHat += items[j].profit;
          xHat[j] = 1;
          j++;
        }

        if (j < n - 1) {
          xHat[j] = 0;
          j++;
        }

        if (j < n - 2) {
          computeUpperBound(knapsacks);
        } else if (j > n - 2) {
          break;
        }
      }


      if (zHat > z) {
        z = zHat;
        System.arraycopy(xHat, 0, x, 0, n);
      }

      j = n - 1;

      if (xHat[n - 1] == 1) {
        cHat += knapsacks.getItem(n - 1).weight;
        zHat -= knapsacks.getItem(n - 1).profit;
        xHat[n - 1] = 0;
      }

      boolean temp1 = backtrack(knapsacks);
      if (!temp1) {
        break;
      }
    }

    knapsacks.setItems(temp2);

    return z;
  }

  public void computeUpperBound(Knapsacks knapsacks) {
    int r = 0;

    Item[] subW = knapsacks.getItems();

    long sum = 0;
    for (int k = j; k < n; k++) {
      sum += subW[k].weight;
      if (sum > cHat) {
        r = k;
        break;
      }
    }

    int u = 0;
    long totalWeights = 0;
    for (int k = j; k < r; k++) {
      u += knapsacks.getItem(k).profit;
      totalWeights += knapsacks.getItem(k).weight;
    }

    Item itemR = knapsacks.getItem(r);
    u += (cHat - totalWeights) * itemR.profit / itemR.weight;


    if (z >= zHat + u) {
      backtrack(knapsacks);
    }
  }

  public boolean backtrack(Knapsacks knapsacks) {
    int i = -1;

    for (int k = 0; k < j; k++) {
      if (xHat[k] == 1) {
        i = k;
      }
    }

    if (i == -1) {
      return false;
    }

    cHat += knapsacks.getItem(i).weight;
    zHat -= knapsacks.getItem(i).profit;
    xHat[i] = 0;
    j = i + 1;

    computeUpperBound(knapsacks);

    return true;
  }

  public int[] getSelectedItems() {
    int n = this.x.length;
    int[] temp = new int[n - 1];
    System.arraycopy(this.x, 0, temp, 0, n - 1);
    return temp;
  }
}
