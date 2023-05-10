package org.example;

public class Item {
  protected int weight;
  protected int profit;
  protected double ratio;

  public Item(int weight, int profit) {
    this.weight = weight;
    this.profit = profit;
    this.ratio = (double) profit / weight;
  }

  @Override
  public String toString() {
    return "(" +
            "w=" + weight +
            ", p=" + profit +
            ')';
  }
}
