import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;
/**
 * A simple program to generate files for use in your sorting algorithms assignment.
 * 
 * @author Jordan Cohen
 * @version May 2018
 */
public class RandIntFileGenerator
{
    private static Scanner scan = new Scanner (System.in);
    private static String fileName;
    public static void main (String[] args)
    {
        int size;
        int maxVal;
        System.out.println("What would you like to name the file? (include extension)");
        fileName = scan.nextLine();
        System.out.println("How large would you like the file to be?");
        size = scan.nextInt();
        scan.nextLine();
        System.out.println("What is the upper bound (highest possible value)?");
        maxVal = scan.nextInt();
        scan.nextLine();

        int updateInterval;
        if (size < 10000){
            updateInterval = 0; // file sizes under 10000 don't need updates displayed
        } else if (size < 500000){
            updateInterval = size / 20; // update every 5%
        } else {
            updateInterval = size / 100; // update every 1%
        }

        PrintWriter output = null;
        try {
            FileWriter out = new FileWriter(fileName);
            output = new PrintWriter(out);
        } catch (IOException e){
            System.out.println ("Error: " + e);
            System.exit(1);
        }

        for (int i = 0; i < size; i++)
        {
            int value = (int)(Math.random() * maxVal);

            output.println (Integer.toString(value));

            if (size > 10000 && i % updateInterval == 0) // progress updates for writing huge files so it doesn't just sit there appearing to do nothing
            {
                System.out.println((((double)i/(double)size)*100) + "% complete");
            }
            output.close();
        }
        System.out.println("Complete! Generated a file with " + size + " values between 0 and " + maxVal);
    }

    public static String getFileName(){
        return fileName;
    }
}
