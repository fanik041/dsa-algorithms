package Arrays;

class InnerLinearSearch {

    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Return the index of the target
            }
        }
        return -1; // Return statement if the target is not found
    }
    
    public static void main(String[] args) {
        int[] arr = {5, 3, 2, 8, 1};
        int target = 2;
        int result = linearSearch(arr, target);
        
        if (result != -1) {
            System.out.println("Target found at index: " + result);
        } else {
            System.out.println("Target not found in the array.");
        }
    }
}

// for( int i=0; i< arr.length; i++)