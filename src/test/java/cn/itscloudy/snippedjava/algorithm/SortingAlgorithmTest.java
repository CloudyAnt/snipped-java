package cn.itscloudy.snippedjava.algorithm;

import cn.itscloudy.snippedjava.tool.other.OptionalResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortingAlgorithmTest {

    @ParameterizedTest(name = "sort by algorithm ${0}")
    @EnumSource(SortingAlgorithm.class)
    void shouldSort(SortingAlgorithm algorithm) {
        if (algorithm.notReady()) {
            return;
        }

        int[] arr1 = new int[]{-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] arr2 = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2};
        int[] arr3 = new int[]{21, 39, -72, 18, 32, -10, 98, 12, 56, 28, -1, 10};

        test(algorithm, arr1, new int[]{-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        test(algorithm, arr2, new int[]{-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        test(algorithm, arr3, new int[]{-72, -10, -1, 10, 12, 18, 21, 28, 32, 39, 56, 98});
    }

    private void test(SortingAlgorithm algorithm, int[] arrayToSort, int[] expectedResultValue) {
        OptionalResult<int[]> result = OptionalResult.of(() -> algorithm.sort(arrayToSort), true);
        int[] resultValue = result.orElseThrow(RuntimeException::new);
        assertArrayEquals(expectedResultValue, resultValue);
        printResult(algorithm, result);
    }

    private void printResult(SortingAlgorithm algorithm, OptionalResult<int[]> result) {
        System.out.print(algorithm.name() + " -> ");
        boolean firstPrinted = false;
        for (int i : result.get()) {
            if (firstPrinted) {
                System.out.print(", ");
            } else {
                firstPrinted = true;
            }
            System.out.print(i);
        }
        System.out.print(" > " + result.mills() + "ms\n");
    }


}
