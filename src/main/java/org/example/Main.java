package org.example;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Main {
  public static void main(String[] args) {
    int n = 100;
    Random random = new Random();
    int[] profits = new int[n];
    int[] weights = new int[n];
    for (int i = 0; i < n; i++) {
      profits[i] = random.nextInt(1000);
      weights[i] = random.nextInt(1000);
    }
    int capacity = random.nextInt(400, 800);

    System.out.println(capacity);
    System.out.println(Arrays.toString(profits));
    System.out.println(Arrays.toString(weights));

    Knapsacks knapsacks = new Knapsacks(weights, profits, capacity);
//    Arrays.sort(knapsacks.getItems(), (x, y) -> Double.compare(y.ratio, x.ratio));
    Solver solver = new Solver(knapsacks);

    System.out.println("Dynamic programming: " + solver.solve(AlgorithmsType.DYNAMIC_PROGRAMMING));
    System.out.println(Arrays.toString(solver.getSelected()));
    System.out.println("Branch and bound: " + solver.solve(AlgorithmsType.BRANCH_AND_BOUND));
    System.out.println(Arrays.toString(solver.getSelected()));
    System.out.println("Greedy: " + solver.solve(AlgorithmsType.GREEDY));
    System.out.println(Arrays.toString(solver.getSelected()));

    int numRuns = 20;
    long[] dynamicProgrammingTimes = new long[numRuns];
    long[] branchAndBoundTimes = new long[numRuns];
    long[] greedyTimes = new long[numRuns];

    for (int i = 0; i < numRuns; i++) {
      knapsacks = new Knapsacks(weights, profits, capacity);

      solver = new Solver(knapsacks);

      long startTime = System.nanoTime();
      solver.solve(AlgorithmsType.DYNAMIC_PROGRAMMING);
      long endTime = System.nanoTime();
      dynamicProgrammingTimes[i] = endTime - startTime;


      startTime = System.nanoTime();
      solver.solve(AlgorithmsType.BRANCH_AND_BOUND);
      endTime = System.nanoTime();
      branchAndBoundTimes[i] = endTime - startTime;


      startTime = System.nanoTime();
      solver.solve(AlgorithmsType.GREEDY);
      endTime = System.nanoTime();
      greedyTimes[i] = endTime - startTime;

    }

    // Plot the results using a charting library of your choice

    // Ví dụ: sử dụng thư viện JFreeChart để vẽ biểu đồ
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < numRuns; i++) {
      dataset.addValue(dynamicProgrammingTimes[i], "Dynamic Programming", String.valueOf(i+1));
      dataset.addValue(branchAndBoundTimes[i], "Branch and Bound", String.valueOf(i+1));
      dataset.addValue(greedyTimes[i], "Greedy", String.valueOf(i+1));
    }

    JFreeChart chart = ChartFactory.createBarChart("Algorithm Comparison", "Run", "Time (ns)", dataset, PlotOrientation.VERTICAL, true, true, false);
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setBackgroundPaint(new Color(255, 255, 255));
    plot.setRangeGridlinePaint(Color.BLACK);
    ChartFrame frame = new ChartFrame("Algorithm Comparison", chart);
    frame.pack();
    frame.setVisible(true);
  }
}