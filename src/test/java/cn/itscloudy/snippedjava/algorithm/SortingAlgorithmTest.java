package cn.itscloudy.snippedjava.algorithm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SortingAlgorithmTest {

    @ParameterizedTest(name = "sort by algorithm ${0}")
    @EnumSource(SortingAlgorithm.class)
    public void shouldSort(SortingAlgorithm algorithm) {
        if (algorithm.notReady()) {
            return;
        }

        int[] arr1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] arr2 = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] arr3 = new int[]{21, 39, 18, 32, 98, 12, 56, 28};

        assertArrayEquals(arr1, algorithm.sort(arr1));
        assertArrayEquals(arr1, algorithm.sort(arr2));
        assertArrayEquals(new int[]{12, 18, 21, 28, 32, 39, 56, 98}, algorithm.sort(arr3));
    }
}
