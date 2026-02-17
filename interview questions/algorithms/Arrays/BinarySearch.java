package algorithms;

public class BinarySearch {
    
    public static int binarySearchArr(int[] arr, int target) {
        // take index of array
        int left = 0;
        int right = arr.length - 1;
        // navigate via index value
        while(left<=right) {
            // calculate the median index
            int median = left + (right - left) / 2;
            if (arr[median] == target) {
                return median;
                // only difference between this and two-pointers is that it moves from
                // the middle instead of the ends
            } else if (arr[median] < target) {
                left = median + 1;
            } else {
                right = median - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 52, 6, 7, 8, 9};
        int target = 52;
        int result = binarySearchArr(arr, target);
        
        if (result != -1) {
            System.out.println("Target found at index: " + result);
        } else {
            System.out.println("Target not found in the array.");
        }
    }
}
