package algorithms;


// Kadane’s algorithm
public class MaxSubArray {
    
    public static int findMaxSubArray(int[] arr) {

        int result = arr[0];
        int maxSum = arr[0];

        for (int i=1; i<arr.length; i++) {
            // System.out.println("I is: "+i +" and maxSum is: " + maxSum);
            maxSum = Math.max(maxSum+arr[i], arr[i]);
            result = Math.max(result, maxSum);

        }
        System.out.println("Max sum is: " + maxSum + ", Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        int[] data = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println("Maximum sum of subarray: " + findMaxSubArray(data));
        // Output: 6 (from the subarray [4,-1,2,1])
    }
}
