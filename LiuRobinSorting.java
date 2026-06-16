import java.util.*;
import java.io.*;
public class LiuRobinSorting
{
    public static ArrayList<Integer> nums = new ArrayList<>();

    public static Scanner scan = new Scanner(System.in);
    public static String filename;
    public static String choice;

    public static long operations = 0;
    public static int i, j, temp;
    public static boolean swapped;

    public static int[] list;
    public static int[] clonedList;

    public static Checker check = new Checker();  
    public static Timer time = new Timer();
    public static StaticTimer staticTime = new StaticTimer();

    public static void main(String[] args){
        askSort();

        System.out.println(check.checkResults(clonedList, false));
    }

    public static void askSort(){
        /*
        System.out.print("Enter file name: ");
        filename = scan.nextLine();
        loadList();
         */
        System.out.println("""
        What would you like to do?
        
        1. Generate a list
        2. Sort a file
        """);

        choice = scan.nextLine().trim();
        if(choice.equals("1")){
            genList(nums);
        }else if(choice.equals("2")){
            loadList(nums);
        }
        System.out.print("What sort to perform? ");
        choice = scan.nextLine().trim().toLowerCase();

        if(choice.equals("my sort")){
            choice = askOperation(list);
            if(choice.equals("1")){
                time.startTimer();

                cloneList(list);
                mySort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));

                time.endTimer();
                System.out.println(time);
            }else if(choice.equals("2")){
                cloneList(list);
                mySort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));

                cloneList(list);
                long ops = mySortOpCount(clonedList, clonedList.length);
                System.out.println("Operations: " + ops);
            }else if(choice.equals("3")){
                time.startTimer();

                cloneList(list);
                mySort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));

                cloneList(list);
                long ops = mySortOpCount(clonedList, clonedList.length);
                System.out.println("Operations: " + ops);

                time.endTimer();
                System.out.println(time);
            }
        }else if(choice.equals("bubble sort")){
            choice = askOperation(list);
            if(choice.equals("1")){
                time.startTimer();

                cloneList(list);
                bubbleSort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));

                time.endTimer();
                System.out.println(time);
            }else if(choice.equals("2")){
                cloneList(list);
                bubbleSort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));

                cloneList(list);
                long ops = bubbleSortOpCount(clonedList, clonedList.length);
                System.out.println("Operations: " + ops);
            }else if(choice.equals("3")){
                time.startTimer();

                cloneList(list);
                bubbleSort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));

                cloneList(list);
                long ops = bubbleSortOpCount(clonedList, clonedList.length);
                System.out.println("Operations: " + ops);

                time.endTimer();
                System.out.println(time);
            }
        }else if(choice.equals("selection sort")){
            choice = askOperation(list);
            if(choice.equals("1")){
                time.startTimer();
                
                cloneList(list);
                selectionSort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));
                
                time.endTimer();
                System.out.println(time);
            }else if(choice.equals("2")){
                cloneList(list);
                selectionSort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));
                
                cloneList(list);
                long ops = selectionSortOpCount(clonedList, clonedList.length);
                System.out.println("Operations: " + ops);
            }else if(choice.equals("3")){
                time.startTimer();
                
                cloneList(list);
                selectionSort(clonedList, clonedList.length);
                //System.out.println("Sorted list: " + Arrays.toString(clonedList));
                
                cloneList(list);
                long ops = selectionSortOpCount(clonedList, clonedList.length);
                System.out.println("Operations: " + ops);
                
                time.endTimer();
                System.out.println(time);
            }
        }
        scan.close();
    }

    public static void genList(ArrayList<Integer> arr){
        arr.clear();
        System.out.print("How many numbers do you want to sort? ");
        int size = scan.nextInt();
        scan.nextLine();
        System.out.println("What is the upper bound (highest possible #)");
        int maxVal = scan.nextInt();
        scan.nextLine();

        for(i = 0; i < size; i++){
            int num = (int)(Math.random() * maxVal);
            arr.add(num);
        }
        list = new int[arr.size()];
        for(i = 0; i < arr.size(); i++){
            list[i] = arr.get(i);
        }
    }

    public static void loadList(ArrayList<Integer> arr){
        arr.clear();

        System.out.print("Enter the file name: ");
        filename = scan.nextLine();
        File inputFile = new File(filename);

        try(Scanner fileScan = new Scanner(inputFile)){
            while(fileScan.hasNextLine()){
                arr.add(Integer.parseInt(scan.nextLine()));
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            return;
        }

        list = new int[arr.size()];
        for(i = 0; i < arr.size(); i++){
            list[i] = arr.get(i);
        }
        System.out.println("Loaded list of " + list.length + " numbers from " + filename);
    }

    public static String askOperation(int[] list){
        System.out.println("Enter the operation #");
        System.out.println(
        """
        1. Check time
        2. Check operations
        3. Both
        """);

        String choice = scan.nextLine().trim().toLowerCase();
        return choice;

    }

    public static void cloneList(int[] arr){
        clonedList = new int[arr.length];
        for(i = 0; i < arr.length; i++){
            clonedList[i] = arr[i];
        }
    }

    public static int[] bubbleSort(int[] arr, int n) {
        for (i = 0; i < n - 1; i++) {
            swapped = false;

            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no swaps happened, array is sorted
            if (!swapped) {
                break;
            }
        }
        return arr;
    }

    public static long bubbleSortOpCount(int arr[], int n){
        operations = 0;
        int i, j, temp;

        operations++; // i = 0
        for (i = 0; i < n - 1; i++) {
            operations++; // i < n - 1
            swapped = false;
            operations++; // swapped = false;

            operations++; // j = 0
            for (j = 0; j < n - i - 1; j++) {
                operations++; // j < n - 1
                operations++; // if statement below
                if (arr[j] > arr[j + 1]) {

                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                    operations += 4;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            operations++;
            if (swapped == false){
                break;
            }
            operations++; // i++
        }
        return operations;
    }

    public static int[] mySort(int[] arr, int n){
        for(i = 0; i < n; i++){
            for(j = i + 1; j < n; j++){
                if(arr[i] > arr[j]){
                    //Swap
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    public static long mySortOpCount(int[] arr, int n){
        operations = 0;

        operations++; // i = 0
        for(i = 0; i < n - 1; i++){
            operations++; // i < n
            operations++; // j = i + 1
            for(j = i + 1; j < n; j++){
                operations++; // j < n
                operations++; // if statement below
                if(arr[i] > arr[j]){
                    // Swap
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    operations += 3;
                }
                operations++; // j++
            }
            operations++; // i++
        }

        return operations;
    }

    // selectionSort from online
    public static void selectionSort(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i; // Assume current index has the smallest value
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) { // Find smaller element
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp; // Swap the smallest value into its correct position
        }
    }
    
    public static long selectionSortOpCount(int[] arr, int n){
        operations = 0;
        
        operations++; // i = 0
        for (i = 0; i < n - 1; i++) {
            operations++; // i < n - 1
            int minIndex = i; // Assume current index has the smallest value
            operations++; // j = i + 1;
            for (j = i + 1; j < n; j++) {
                operations++; // j < n
                operations++; // if statement
                if (arr[j] < arr[minIndex]) { // Find smaller element
                    minIndex = j;
                    operations++;
                }
                operations++; // j++
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp; // Swap the smallest value into its correct position
            operations += 3;
            
            operations++; // i++
        }
        return operations;
    }

}
