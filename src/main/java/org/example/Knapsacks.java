package org.example;

import java.util.Arrays;

public class Knapsacks {
  private Item[] items;
  private int capacity;

  public Knapsacks(Item[] items, int capacity) {
    this.items = items;
    this.capacity = capacity;
  }

  public Knapsacks(int[] weights, int[] profits, int capacity) {
    this.capacity = capacity;
    int n = weights.length;

    this.items = new Item[n];
    for (int i = 0; i < n; i++) {
      this.items[i] = new Item(weights[i], profits[i]);
    }
  }

  public Item[] getItems() {
    return items;
  }

  public void setItems(Item[] items) {
    this.items = items;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public Item getItem(int i) {
    return this.items[i];
  }

  @Override
  public String toString() {
    return "Knapsacks" + Arrays.toString(items);
  }
}
