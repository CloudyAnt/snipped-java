package cn.itscloudy.snippedjava.algorithm;

import java.util.function.Function;

public enum SortingAlgorithm {
    SELECTION(SortingAlgorithm::selection),
    BUBBLE(SortingAlgorithm::bubble),
    INSERTION(SortingAlgorithm::insertion),
    QS(SortingAlgorithm::qs), // quick sort
    HEAP(SortingAlgorithm::heap),
    SHELL(SortingAlgorithm::shell),
    ;

    private final Function<int[], int[]> algorithm;

    SortingAlgorithm(Function<int[], int[]> algorithm) {
        this.algorithm = algorithm;
    }

    public int[] sort(int[] arr) {
        return algorithm.apply(arr);
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static int[] selection(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }
        }
        return arr;
    }

    private static int[] bubble(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }

    private static int[] insertion(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] > arr[j - 1]) {
                    break;
                }
                swap(arr, j, j - 1);
            }
        }
        return arr;
    }

    // quick sort
    private static int[] qs(int[] arr) {
        return arr;
    }

    private static int partition(int[] arr) {
        return 0;
    }

    private static int[] heap(int[] arr) {
        return arr;
    }

    private static int[] shell(int[] arr) {
        return arr;
    }
}
