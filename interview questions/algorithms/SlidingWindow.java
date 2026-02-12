package algorithms;

public class SlidingWindow {
    public static int findMaxSum(int[] arr, int k) {
        if (arr.length < k) {
            return -1; // Handle edge case
        }

        int maxSum = 0;
        int windowSum = 0;

        // 1. Calculate sum of the first window of size k
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        System.out.println("Initial window sum: " + windowSum);
        maxSum = windowSum;

        // 2. Slide the window through the rest of the array
        for (int i = k; i < arr.length; i++) {
            System.out.println("I is: "+i +" and K is:" + k+ " and windowSum is: " + windowSum);
            // Add the next element and subtract the first element of the previous window
            windowSum += arr[i] - arr[i - k];
            // Update the maximum sum found so far
            maxSum = Math.max(maxSum, windowSum);
            System.out.println("Current window sum: " + windowSum + ", Max sum: " + maxSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] data = {3,5,7,2,11,8};
        int k = 3;
        System.out.println("Maximum sum of subarray of size " + k + ": " + findMaxSum(data, k));
        // Output: 9 (from the subarray [5, 1, 3])
    }
}