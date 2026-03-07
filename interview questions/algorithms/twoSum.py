from typing import List, Tuple, Dict

def two_sum(nums: List[int], target: int) -> Tuple[int, int]:
    index_by_value: Dict[int, int] = {}

    for current_index, current_value in enumerate(nums):
        complement = target - current_value
        if complement in index_by_value:
            return index_by_value[complement], current_index
        index_by_value[current_value] = current_index

    raise ValueError("No two sum solution")


def bubble_sort(values: List[int]) -> List[int]:
    sorted_values = values[:]
    n = len(sorted_values)

    for end in range(n - 1, 0, -1):
        swapped = False
        for i in range(end):
            if sorted_values[i] > sorted_values[i + 1]:
                sorted_values[i], sorted_values[i + 1] = sorted_values[i + 1], sorted_values[i]
                swapped = True
        if not swapped:
            break
    return sorted_values