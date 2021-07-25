package cn.itscloudy.snippedjava.algorithm;

import cn.itscloudy.snippedjava.tool.other.OptionalResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortingAlgorithmTest {

    private final Random random = new Random();
    private final int[] longArray;
    private final int[] shuffledLongArray;
    {
        longArray = generateOrderedArrayWithLength1000();
        shuffledLongArray = Arrays.copyOf(longArray, longArray.length);
        randomSwapArr(shuffledLongArray);
    }

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
        test(algorithm, Arrays.copyOf(shuffledLongArray, shuffledLongArray.length), longArray);
    }

    private void test(SortingAlgorithm algorithm, int[] arrayToSort, int[] expectedResultValue) {
        OptionalResult<int[]> result = OptionalResult.of(() -> algorithm.sort(arrayToSort), true);
        int[] resultValue = result.orElseThrow(RuntimeException::new);
        assertArrayEquals(expectedResultValue, resultValue);
        printResult(algorithm, result);
    }

    private void printResult(SortingAlgorithm algorithm, OptionalResult<int[]> result) {
        System.out.println(algorithm.name() + " > " + result.mills() + "ms");
    }

    private int[] generateOrderedArrayWithLength1000() {
        int len = 10000;
        int[] arr = new int[len];
        int index = 0;
        arr[index] = -random.nextInt(100);
        while (index < len - 1) {
            int addition = random.nextInt(5);
            int next = arr[index] + addition;
            int i = ++index;
            arr[i] = next;
        }
        return arr;
    }

    private void randomSwapArr(int[] arr) {
        int remandTimes = 500;
        while (remandTimes > 0) {
            int a = random.nextInt(arr.length);
            int b = random.nextInt(arr.length);
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
            remandTimes--;
        }
    }
}
