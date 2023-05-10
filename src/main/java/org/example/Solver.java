package org.example;

public class Solver {
  private final Knapsacks KNAPSACKS;
  private int[] selected;

  public Solver(Knapsacks knapsacks) {
    this.KNAPSACKS = knapsacks;
  }

  public int solve(AlgorithmsType algorithmsType) {
    Algorithms algorithms;
    switch (algorithmsType) {
      case GREEDY -> algorithms = new Greedy();
      case BRANCH_AND_BOUND -> algorithms = new BranchAndBound();
      default -> algorithms = new DynamicProgramming();
    }
    int maxProfits = algorithms.solve(KNAPSACKS);
    this.selected = algorithms.getSelectedItems();
    return maxProfits;
  }

  public int[] getSelected() {
    if (this.selected == null) {
      solve(AlgorithmsType.DYNAMIC_PROGRAMMING);
    }
    return selected;
  }
}
