//package edu.ser222.m02_01;
import java.text.DecimalFormat;
import java.util.Random;

/*** testing insertion sort and shellsort with binary, half, and half-random arrays
*
* Completion time: 3 hours ish 
*
* @author Bella Sheridan, Acuna, Sedgewick
* @version 1.0
*/

public class CompletedBenchmarkTool implements BenchmarkTool {
/***************************************************************************
* START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK) *
**************************************************************************/
public static void insertionSort(Comparable[] a) {
int N = a.length;
for (int i = 1; i < N; i++)
{
// Insert a[i] among a[i-1], a[i-2], a[i-3]... ..
for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
exch(a, j, j-1);
}
}
public static void shellsort(Comparable[] a) {
int N = a.length;
int h = 1;
while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
while (h >= 1) {
// h-sort the array.
for (int i = h; i < N; i++) {
// Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
exch(a, j, j-h);
}
h = h/3;
}
}
private static boolean less(Comparable v, Comparable w) {
return v.compareTo(w) < 0;
}
private static void exch(Comparable[] a, int i, int j) {
Comparable t = a[i]; a[i] = a[j]; a[j] = t;
}
/***************************************************************************
* END - SORTING UTILITIES, DO NOT MODIFY *
**************************************************************************/
//TODO: implement interface methods.

@Override
public Integer[] generateTestDataBinary(int size){ //half 0s hald 1s
    Integer[] data = new Integer[size];
    for(int i = 0; i < size / 2; i++){ //first half of the array will be all 0's
        data[i] = 0;
    }

    for(int i = size / 2; i < size; i++){ //second half of the array will be all 1's
        data[i] = 1;
    }
    return data; //return completed array
}

@Override
public Integer[] generateTestDataHalves(int size){ //half the data is 0s, half the remainder is 1s, half the reminder is 2s, half the reminder is 3s
    Integer[] data = new Integer[size]; //array to hold data
    int index = 0; //current position
    int remaining = size; //how many elements are still needed to fill
    int value = 0; //valiu currently being inserted

    while(remaining > 0){
        int count = remaining / 2; //fill half of whats left

        if(count == 0){ //if remaining is 1 it would return as 0 so force it to be 1
            count = 1;
        }

        for(int i = 0; i < count && index < size; i++){ //fill count amount of positions with the current value 
            data[index] = value; //plave current value into array 
            index++; //move index to next element
        }

        remaining -= count; //update remaining 
        value++; //increase value 
    }
    return data; //return array 
}

@Override
public Integer[] generateTestDataHalfRandom(int size){
    Integer[] data = new Integer[size]; //create array
    Random random = new Random(); //create random object

    for(int i = 0; i < size / 2; i++){ //first half of the array will be all 0's
        data[i] = 0;
    }
    //second half is random positive numbers 
    for(int i = size / 2; i < size; i++){
        data[i] = random.nextInt(Integer.MAX_VALUE); //generate random positive numbers
    }
    return data; //return array
}

@Override
public double computeDoublingFormula(double t1, double t2){ //b = log2(t2/t1) //log base 2 is log(x)/log(2)
    return Math.log(t2 / t1) / Math.log(2);
}

@Override
public double benchmarkInsertionSort(Integer[] small, Integer[] large){
    //clone arrays 
    Integer[] smallc = small.clone();
    Integer[] largec = large.clone();

    //time insertion sort on small copy
    Stopwatch timer1 = new Stopwatch();
    insertionSort(smallc);
    double t1 = timer1.elapsedTime();

    //time insertion sort on large copy
    Stopwatch timer2 = new Stopwatch();
    insertionSort(largec);
    double t2 = timer2.elapsedTime();

    return computeDoublingFormula(t1, t2);
}

@Override
public double benchmarkShellsort(Integer[] small, Integer[] large){
    //clone arrays 
    Integer[] smallc = small.clone();
    Integer[] largec = large.clone();

    //time shellsort sort on small copy
    Stopwatch timer1 = new Stopwatch();
    shellsort(smallc);
    double t1 = timer1.elapsedTime();

    //time shellsort sort on large copy
    Stopwatch timer2 = new Stopwatch();
    shellsort(largec);
    double t2 = timer2.elapsedTime();

    return computeDoublingFormula(t1, t2);
}

@Override
public void runBenchmarks(int size){
    DecimalFormat df = new DecimalFormat("0.000"); //so that the decimals are formatted correctly 

    //binary
    Integer[] smallBinary = generateTestDataBinary(size);   //small and large for all sets
    Integer[] largeBinary = generateTestDataBinary(size * 2);
    //halves
    Integer[] smallHalf = generateTestDataHalves(size);
    Integer[] largeHalf = generateTestDataHalves(size * 2);
    //half-random
    Integer[] smallRandom = generateTestDataHalfRandom(size);
    Integer[] largeRandom = generateTestDataHalfRandom(size * 2);
    //run all the benchmarks
    double insertionBinary = benchmarkInsertionSort(smallBinary, largeBinary);
    double shellBinary = benchmarkShellsort(smallBinary, largeBinary);
///////////
    double insertionHalf = benchmarkInsertionSort(smallHalf, largeHalf);
    double shellHalf = benchmarkShellsort(smallHalf, largeHalf);
//////////
    double insertionRandom = benchmarkInsertionSort(smallRandom, largeRandom);
    double shellRandom = benchmarkShellsort(smallRandom, largeRandom);
    //print everything
    System.out.println("Insertion Shellsort"); //use decimal format to make it easy 
    System.out.println("Bin " + df.format(insertionBinary) + " " + df.format(shellBinary));
    System.out.println("Half " + df.format(insertionHalf) + " " + df.format(shellHalf));
    System.out.println("RanInt " + df.format(insertionRandom) + " " + df.format(shellRandom));

}


public static void main(String args[]) {
BenchmarkTool me = new CompletedBenchmarkTool();
int size = 100000;
//NOTE: feel free to change size here. all other code must go in the
// methods.
me.runBenchmarks(size);
}
}

