package Arrays;

public class TwoPointers {
    
    public static int[] twoSumOfNumbers(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length -1;
        //moves via index value
        while (left<right) {
            // calculates the sum and checks with target
            int sumOfTwo = numbers[left] + numbers [right];
            if (sumOfTwo == target) {
                return new int[] {left+1, right +1};
                //pointer moves based on sum values rather then index, the only
                // difference between this and binary search is that 
                // it moves from the ends instead of the middle. 
            } else if (sumOfTwo < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] {-1, -1}; // Return statement if no solution is found
    }

    public static void main(String[] args) {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSumOfNumbers(numbers, target);
        
        if (result[0] != -1) {
            System.out.println("Indices of the two numbers: " + result[0] + ", " + result[1]);
        } else {
            System.out.println("No two numbers found that add up to the target.");
        }
    }
}
