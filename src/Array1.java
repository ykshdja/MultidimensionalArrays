/**
 * Statement of AuthorShip - I Yash Khanduja, 000826385 hereby declare that this Assessment is my own work and that I have not copied it from anyone.
 * @author - Yash Khanduja
 * @date - 25th-01-2021
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class Array - Main method with Implementation of Four Questions - Mountain Valleys and Peaks
 * @author Yash Khanduja, 000826385
 */
public class Array1 {
    static int [] fileSizeRow = new int[3];
   static int elevationVal = 98480;
   static double [][] minDist = new double [20][5];
   static int[][] arrayforNumOfPeaks = new int[600*1250][3];
    public static void main(String[] args) {
         /*Reading a File in an Array*/
        final int SIZE = 1000000;
        int[] numbers = new int[SIZE];
        int count = 0;
        int correctCount = 0;
        File file = new File("ELEVATIONS.txt");
        try {
            Scanner inputFile = new Scanner(file);
            while (inputFile.hasNext() && count < numbers.length) {
                if(count<3){
                    fileSizeRow[count] = inputFile.nextInt();

                }
                else{
                    numbers[correctCount] = inputFile.nextInt();
                    correctCount++;
                }
                count++;
            }
            numbers = Arrays.copyOf(numbers, correctCount); // Create a new reference the correct size
            inputFile.close();


        } catch (FileNotFoundException ex) {
            System.out.println("Error Reading File " + ex.getMessage());
        }
        /**
         * Main Method Implementation
         */
        /* Q1- Implementation */
        long startTimeForTheProgram = System.nanoTime();
        long startTime = System.nanoTime();
        int [] lowestElevation = lowestElevation(numbers);
        long stopTime = System.nanoTime();
        long time1 = (stopTime - startTime) / 1000;
        System.out.println("The Lowest Peak:"+lowestElevation[0] + " found "+lowestElevation[1]+" times!");
        System.out.printf(" [Time = %d us]\n", time1);
        /*Q1 - Implementation*/

         int fileRow = fileSizeRow[0];
         int fileCol = fileSizeRow[1];
         int indexRadius = fileSizeRow[2];
         int i = 0;
        int[][] numbersInOrder = new int[fileRow+1][fileCol+1];
        //Convert 1d Array to 2D Array.
       for(int y=0;y<fileRow;y++)
       {
           for(int x=0;x<fileCol;x++)
           {
               numbersInOrder[y][x] = numbers[i];
               i++;
           }
       }
        //Testing Some Array Out of Bound Exception
        //System.out.println(Arrays.deepToString(numbersInOrder));

        /*Q2 - Implementation Calling method*/
        startTime = System.nanoTime();
        int totalPeaks = LocalPeak(numbersInOrder,indexRadius,fileCol,fileRow);
        stopTime = System.nanoTime();
        time1 = (stopTime - startTime) / 1000;
        System.out.println("Total number of peaks are  "+totalPeaks+ "");
        System.out.printf(" [Time = %d us]\n", time1);
        /*Q2 - Implementation */

        /*Q3 - Implementation */
        //int leastDistance = (int) closestLocalPeaks(numbersInOrder);
        /*Q3 - Implmentation */



        /*Q4 - Implementation*/
        startTime = System.nanoTime();
        int [] mostCommonElevation = mostCommonElev(numbers);
        time1 = (stopTime - startTime) / 1000;
        stopTime = System.nanoTime();
        System.out.printf(" [Time = %d us]\n", time1);

            System.out.println("Most Common Elevation in the Terrain is " + mostCommonElevation[0] + " Occurs " +mostCommonElevation[1] + " times.");
        /*Q4 - Implementation*/
        long stopTimeFortheProgram  = System.nanoTime();
        long timeForTheProgram = (stopTime - startTime) / 1000;
        System.out.printf(" [Time for the Entire Program = %d us]\n", timeForTheProgram);
        /*List of All the Local Peaks*/
        System.out.println("List of All the Local Peaks - Elevation Value, Row and Col" );

        for(int k=0;k<totalPeaks;k++)
        {
            for(int l=0;l<3;l++)
            {
                if(l==0)
                {
                    System.out.println("Value - "+arrayforNumOfPeaks[k][l]);
                }
                if(l==1)
                {
                    System.out.println("Row - "+arrayforNumOfPeaks[k][l]);
                }
                if(l==2 )
                {
                    System.out.println("Col - "+arrayforNumOfPeaks[k][l]);
                }

            }
            System.out.println("-------");
        }

    }

    /*Question 1 - Find the Lowest Elevation Value and How many times it is found*/
    public static int[] lowestElevation(int [] x)
    {
        int minimumValue = x[0];
        int countOfLowest = 0;
        // find minimum value in array java
        for(int a = 1; a < x.length; a++)
        {
            if(x[a] < minimumValue)
            {
                minimumValue = x[a];

            }
        }
        for(int i=0;i<x.length;i++)
        {
            if(x[i] == minimumValue)
            {
                countOfLowest++;
            }
        }
        int [] arr = new int[]{minimumValue,countOfLowest};
        return arr;

    }
    /**
     *Q2 -  Find all the local peaks where the peak elevation is greater than or equal to 98480.
     * @param x The Array to be searched
     * @return Total Number of peaks and Number of closest sets.
     */
    public static int LocalPeak(int [][]x, int indexRadius, int fileCol, int fileRow)
    {

        int countofPeak = 0;
        boolean found = false;
        for(int j=indexRadius;j<fileRow-indexRadius;j++)
        {
            for(int i=indexRadius;i<fileCol-indexRadius;i++)
            {
                if(x[j][i]>=elevationVal)
                {

                    found = true;
                    //Compare (Bruteforce) Left and Right Side
                    for(int col=0;col<indexRadius+1;col++)
                    {
                        for(int row=1;row<indexRadius+1;row++)
                        {
                            if(found == true)
                            {
                                if(x[j][i] <= x[j-row][i-col])
                                {
                                    found = false;
                                }
                                else if(x[j][i] <= x[j+row][i+col])
                                {
                                    found = false;
                                }

                            }
                        }
                    }
                    // Compare Peaks with Col while Rows are  Fixated
                    for(int row=0;row<indexRadius+1;row++)
                    {
                        for(int col=1;col<indexRadius+1;col++)
                        {
                            if(found == true)
                            {
                                if(x[j][i]<=x[j+row][i-col])
                                {
                                    found = false;
                                }
                                else if(x[j][i]<=x[j-row][i+col])
                                {
                                    found = false;
                                }
                            }
                        }
                    }
                    if(found)
                    {
                        arrayforNumOfPeaks[countofPeak][0] = x[j][i];
                        arrayforNumOfPeaks[countofPeak][1] = j;
                        arrayforNumOfPeaks[countofPeak][2] = i;
                        countofPeak++;
                    }

                }
            }
        }
        return countofPeak;
    }

    /**
     *   Q3 -  Find the Smallest Distance between the Closest Peaks.
     * @param x An arranged 2D Array
     * @return value (double) two decimal places - smallest distance between local peaks.
     */
    public static double closestLocalPeaks(int [][] x)
    {
        //Calculate the Distance between the Closest local Peaks
        int count = 0;
        double row,col, distance = 0;
        for(int i=0;i<x.length;i++)
        {
            int r1 = x[i][0], c1 = x[i][1];
            for(int j=i+1;j<x.length;j++)
            {
                int r2 = x[j][0], col2 = x[j][1];
                double d=(((r1-r2)*(r1-r2))+((c1-col2)*(c1-col2)));
                d=Math.sqrt(d);
                d = Math.sqrt(d);
                d=Math.round(d * 100.0) / 100.0;;

                if(distance==0 || d<distance){
                    distance=d;
                }
               else if(d==distance){
                            minDist[count][0]=r1;
                    minDist[count][1]=r2;
                    minDist[count][2]=c1;
                    minDist[count][3]=col2;
                    minDist[count][4]=distance;
                    count++;
                }
            }

            }
            return distance;
        }

    /**
     *
     * Q4 - Print the most common elevation in the data set.
     * @param x - A One Dimensional Array.
     * @return returnArr - A One Array populated most common elevation and Frequency of the most common elevation value.
     * */
    public static int[]  mostCommonElev(int [] x)
    {
        // Approach - Sort First
        //Array -  Return...
        Arrays.sort(x); // Sort the Array Passed
        int max_frq = 0;
        int answer = -1;
        int i=0;
        while( i < x.length)
        {
            int curr_freq = 1;
            while( i+1 < x.length && x[i] == x[i+1])
            {
                curr_freq = curr_freq + 1;
                i = i +1;

            }
            if(max_frq < curr_freq)
            {
                max_frq = curr_freq;
                answer = x[i];
            }else if(max_frq == curr_freq)
            {
                answer = Math.min(answer, x[i]);
            }
            i = i+1;
        }
        int [] returnArr = new int[]{answer,max_frq};
        return returnArr;

    }
}
