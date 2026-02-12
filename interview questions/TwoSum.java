import java.util*

class TwoSum {

    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexByVal = new HashMap<>();
        for int(i=0; i<nums.length; i++) {
            int complement = target - nums[i];
            if indexByVal.containsKey(complement) {
                return new int[] {
                    indexByVal.get(complement), i;
                }
            }
            indexByVal.put(nums[i], i);
        }
        return new int[] {};
    }
    
}