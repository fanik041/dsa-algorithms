import java.util.Map;
import java.util.HashMap;

class TwoSum {

    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexByVal = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (indexByVal.containsKey(complement)) {
                return new int[] {
                    indexByVal.get(complement), i
                };
            }
            indexByVal.put(nums[i], i);
        }
        return new int[] {};
    }
    
}


/*
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Hashmap<Integer, Integer> map = new Hashmap<>(); b
        int left = 0;
        int right = nums.length -1;

        while (left <= right) {
            int twoSum = nums[left] + nums[right];
            if (twoSum == target) {
                return new int[]{left, right};
            }
            else if (twoSum <= target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }
}
*/