//
// SquareRoot Java Program
// This program spawns children, who will help to determine square roots.
// To compile:  javac SquareRoot.java
// To run:  java SquareRoot
// To monitor system resources: (Linux): Applications->System Tools->System Monitor->Resources
// Use Spreadsheet Applications->Office->LibreOffice Calc to show efficiency of multiple threads.
//

import java.lang.Math;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;



class SquareRoot {
    public static void main(String[] args) {
        final int numChild = 6;
        final int total = 20000;
        double range = total/numChild;
        double begin=0.0;
        List<Thread> threads = new ArrayList<Thread>();


        System.out.println("Run SquareRoot " + total + ":" + numChild);

        // Spawn children processes
        for (int i=0; i<numChild; i++) {
            Thread t = new Thread(new Child(begin, begin+range));
            t.start();
            threads.add(t);
            begin += range + 1;
        }

        // Wait for children to finish
        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("All Children Done: " + numChild);
    }
}

class Child implements Runnable {
    private double begin;
    private double end;
    double memAttr;
    double global;

    public Child(double begin, double end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        // Print the current CPU number
        System.out.println("CPU=" + Thread.currentThread().getName());

        // Get the current time
        long start_s = System.currentTimeMillis();
        double totalSum = 0.0;
        for (int local = (int)begin; local < end; local++) {
            double root = Math.sqrt(local);
            // revised lines to do prints:
            System.out.println(local + ":" + root + " ");
            if (local%5==0) System.out.println();
            totalSum += root;
        }

        // Are class attributes and globals shared?
        System.out.println("  totalSum=" + totalSum + " global=" + ++global + " memAttr=" + ++memAttr);
        // Calculate execution time in ms and print
        long stop_s = System.currentTimeMillis();
        System.out.println("time: " + (stop_s - start_s));
    }
}
