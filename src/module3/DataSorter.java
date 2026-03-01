import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class DataSorter {
    
    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);
    
    // Constants for formatting
    private static final String SEPARATOR = "=".repeat(80);
    private static final int MAX_DISPLAY_SIZE = 100;
    
  
    public static void main(String[] args) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("          DATA SORTER - SORTING ALGORITHM COMPARISON TOOL");
        System.out.println(SEPARATOR);
        
        try {
            // Get the array from the user
            int[] originalArray = getUserInput();
            
            if (originalArray == null || originalArray.length == 0) {
                System.out.println("Invalid input. Exiting...");
                return;
            }
            
            // Display the original array if it's reasonably small
            displayOriginalArray(originalArray);
            
            // Create copies of the array for fair comparison
            int[] arrayForBubbleSort = Arrays.copyOf(originalArray, originalArray.length);
            int[] arrayForMergeSort = Arrays.copyOf(originalArray, originalArray.length);
            int[] arrayForQuickSort = Arrays.copyOf(originalArray, originalArray.length);
            
            // Measure performance of each algorithm
            long bubbleSortTime = measureBubbleSort(arrayForBubbleSort);
            long mergeSortTime = measureMergeSort(arrayForMergeSort);
            long quickSortTime = measureQuickSort(arrayForQuickSort);
            
            // Display sorted arrays
            displaySortedArray(arrayForBubbleSort, "Bubble Sort");
            displaySortedArray(arrayForMergeSort, "Merge Sort");
            displaySortedArray(arrayForQuickSort, "Quick Sort");
            
            // Display comparison table
            displayComparisonTable(bubbleSortTime, mergeSortTime, quickSortTime);
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    

    private static int[] getUserInput() {
        System.out.println("\nPlease choose input method:");
        System.out.println("1. Enter array manually");
        System.out.println("2. Generate random array");
        System.out.print("Enter your choice (1 or 2): ");
        
        int choice = getIntInput();
        
        if (choice == 1) {
            return getManualInput();
        } else if (choice == 2) {
            return getRandomArray();
        } else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
            return getUserInput();
        }
    }
    

    private static int[] getManualInput() {
        System.out.print("\nEnter the number of elements: ");
        int size = getIntInput();
        
        if (size <= 0) {
            System.out.println("Array size must be greater than 0.");
            return getManualInput();
        }
        
        int[] array = new int[size];
        System.out.println("Enter " + size + " integers (space-separated or one per line):");
        
        for (int i = 0; i < size; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            array[i] = getIntInput();
        }
        
        return array;
    }
    

    private static int[] getRandomArray() {
        System.out.print("\nEnter the size of the random array: ");
        int size = getIntInput();
        
        if (size <= 0) {
            System.out.println("Array size must be greater than 0.");
            return getRandomArray();
        }
        
        Random random = new Random();
        int[] array = new int[size];
        System.out.print("Enter the maximum value for random numbers (default is 1000): ");
        int maxValue = getIntInput();
        
        if (maxValue <= 0) {
            maxValue = 1000;
        }
        
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue) + 1; // Generate numbers between 1 and maxValue
        }
        
        System.out.println("Random array of size " + size + " generated successfully.");
        return array;
    }
    

    private static int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear the invalid input
            System.out.print("Invalid input. Please enter a valid integer: ");
            return getIntInput();
        }
    }
    

    private static void displayOriginalArray(int[] array) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("ORIGINAL ARRAY");
        System.out.println(SEPARATOR);
        System.out.println("Array size: " + array.length);
        
        if (array.length < MAX_DISPLAY_SIZE) {
            System.out.println("Array contents: " + Arrays.toString(array));
        } else {
            System.out.println("Array is too large to display (" + array.length + " elements).");
            System.out.println("Showing first 50 elements: " + Arrays.toString(Arrays.copyOf(array, 50)) + "...");
        }
    }
    

    private static void displaySortedArray(int[] array, String algorithmName) {
    }
    

    private static long measureBubbleSort(int[] array) {
        long startTime = System.nanoTime();
        bubbleSort(array);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    

    private static long measureMergeSort(int[] array) {
        long startTime = System.nanoTime();
        mergeSort(array, 0, array.length - 1);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    

    private static long measureQuickSort(int[] array) {
        long startTime = System.nanoTime();
        quickSort(array, 0, array.length - 1);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    

    private static void bubbleSort(int[] array) {
        int n = array.length;
        
        // Outer loop for each pass
        for (int i = 0; i < n - 1; i++) {
            // Inner loop for comparing adjacent elements
            for (int j = 0; j < n - i - 1; j++) {
                // Swap if the current element is greater than the next element
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    

    private static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Recursively sort the left half
            mergeSort(array, left, mid);
            
            // Recursively sort the right half
            mergeSort(array, mid + 1, right);
            
            // Merge the sorted halves
            merge(array, left, mid, right);
        }
    }
    

    private static void merge(int[] array, int left, int mid, int right) {
        // Calculate sizes of the two subarrays
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        
        // Create temporary arrays
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];
        
        // Copy data to temporary arrays
        System.arraycopy(array, left, leftArray, 0, leftSize);
        System.arraycopy(array, mid + 1, rightArray, 0, rightSize);
        
        // Merge the temporary arrays back
        int i = 0;      // Initial index for left subarray
        int j = 0;      // Initial index for right subarray
        int k = left;   // Initial index for main array
        
        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }
        
        // Copy remaining elements from left subarray
        while (i < leftSize) {
            array[k++] = leftArray[i++];
        }
        
        // Copy remaining elements from right subarray
        while (j < rightSize) {
            array[k++] = rightArray[j++];
        }
    }
    

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pivotIndex = partition(array, low, high);
            
            // Recursively sort elements before and after partition
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }
    

    private static int partition(int[] array, int low, int high) {
        // Choose the last element as pivot
        int pivot = array[high];
        int i = low - 1;
        
        // Iterate through all elements and compare with pivot
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                // Swap array[i] and array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        // Swap array[i+1] and array[high] (pivot)
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        
        return i + 1;
    }
    

    private static void displayComparisonTable(long bubbleSortTime, long mergeSortTime, long quickSortTime) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("SORTING ALGORITHM COMPARISON TABLE");
        System.out.println(SEPARATOR);
        
        // Header row
        System.out.printf("%-20s | %-20s | %-20s | %-15s%n", 
            "Algorithm", "Time (Nanoseconds)", "Time (Milliseconds)", "Time (Seconds)");
        System.out.println("-".repeat(80));
        
        // Data rows
        displayAlgorithmRow("Bubble Sort", bubbleSortTime);
        displayAlgorithmRow("Merge Sort", mergeSortTime);
        displayAlgorithmRow("Quick Sort", quickSortTime);
        
        System.out.println(SEPARATOR);
        
        // Summary
        System.out.println("\nSUMMARY:");
        long fastestTime = Math.min(bubbleSortTime, Math.min(mergeSortTime, quickSortTime));
        
        if (fastestTime == bubbleSortTime) {
            System.out.println("Fastest Algorithm: Bubble Sort");
        } else if (fastestTime == mergeSortTime) {
            System.out.println("Fastest Algorithm: Merge Sort");
        } else {
            System.out.println("Fastest Algorithm: Quick Sort");
        }
        
        System.out.printf("Fastest Time: %.4f milliseconds (%.6f seconds)%n", 
            fastestTime / 1_000_000.0, fastestTime / 1_000_000_000.0);
        System.out.println();
    }
    

    private static void displayAlgorithmRow(String algorithmName, long timeNanoseconds) {
        double timeMilliseconds = timeNanoseconds / 1_000_000.0;
        double timeSeconds = timeNanoseconds / 1_000_000_000.0;
        
        System.out.printf("%-20s | %19d | %19.4f | %14.6f%n", 
            algorithmName, timeNanoseconds, timeMilliseconds, timeSeconds);
    }
}
