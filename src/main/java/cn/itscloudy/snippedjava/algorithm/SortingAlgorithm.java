package cn.itscloudy.snippedjava.algorithm;

import java.util.function.Function;

public enum SortingAlgorithm {
    SELECTION(SortingAlgorithm::selection),
    BUBBLE(SortingAlgorithm::bubble),
    INSERTION(SortingAlgorithm::insertion),
    QS(SortingAlgorithm::qs), // quick sort
    HEAP(SortingAlgorithm::heap, false),
    SHELL(SortingAlgorithm::shell, false),
    ;

    private final Function<int[], int[]> algorithm;
    private final boolean ready; // weather it's read for test

    SortingAlgorithm(Function<int[], int[]> algorithm) {
        this.algorithm = algorithm;
        this.ready = true;
    }

    SortingAlgorithm(Function<int[], int[]> algorithm, boolean ready) {
        this.algorithm = algorithm;
        this.ready = ready;
    }

    public boolean notReady() {
        return !ready;
    }

    public int[] sort(int[] arr) {
        int[] sortedArr = algorithm.apply(arr);
        printArr(sortedArr);
        return sortedArr;
    }

    private void printArr(int[] arr) {
        System.out.print(name() + " -> ");
        boolean firstPrinted = false;
        for (int i : arr) {
            if (firstPrinted) {
                System.out.print(", ");
            } else {
                firstPrinted = true;
            }
            System.out.print(i);
        }
        System.out.println();
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
        qs0(arr, 0, arr.length - 1);
        return arr;
    }

    public static void qs0(int[] arr, int l, int r) {
        if (l < r) {
            int partitionIndex = partition(arr, l, r);
            qs0(arr, l, partitionIndex - 1);
            qs0(arr, partitionIndex + 1, r);
        }
    }

    private static int partition(int[] arr, int l, int r) {
        int i = l - 1;
        for (; l < r; l++) {
            if (arr[l] < arr[r]) {
                swap(arr, ++i, l);
            }
        }
        swap(arr, ++i, r);
        return i;
    }

    private static int[] heap(int[] arr) {
        return arr;
    }

    private static int[] shell(int[] arr) {
        return arr;
    }
}
