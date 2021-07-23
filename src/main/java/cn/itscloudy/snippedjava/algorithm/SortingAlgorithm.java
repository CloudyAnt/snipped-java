package cn.itscloudy.snippedjava.algorithm;

import java.util.function.UnaryOperator;

public enum SortingAlgorithm {
    SELECTION(SortingAlgorithm::selection),
    BUBBLE(SortingAlgorithm::bubble),
    INSERTION(SortingAlgorithm::insertion),
    QS(SortingAlgorithm::qs), // quick sort
    HEAP(SortingAlgorithm::heap),
    SHELL(null),
    RADIX(SortingAlgorithm::radix),
    COUNT(SortingAlgorithm::count),
    ;

    private final UnaryOperator<int[]> algorithm;

    SortingAlgorithm(UnaryOperator<int[]> algorithm) {
        this.algorithm = algorithm;
    }

    public boolean notReady() {
        return algorithm == null;
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
        int mid = arr.length / 2 - 1;
        for (int i = mid; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }
        return arr;
    }

    private static void heapify(int[] arr, int i, int limit) {
        int maxIndex = i;
        int l = 2 * i + 1;
        if (l < limit && arr[l] > arr[maxIndex]) {
            maxIndex = l;
        }

        int r = 2 * i + 2;
        if (r < limit && arr[r] > arr[maxIndex]) {
            maxIndex = r;
        }

        if (maxIndex != i) {
            swap(arr, i, maxIndex);
            heapify(arr, maxIndex, limit);
        }
    }

    private static int[] radix(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = Math.abs(arr[i]);
            }
        }

        int maxDigit = 1;
        while ((max /= 10) > 0) {
            maxDigit++;
        }

        for (int i = 1; i <= maxDigit; i++) {
            arr = sortDigit(arr, i);
        }
        return arr;
    }

    private static int[] sortDigit(int[] arr, int digit) {
        int[] counts = new int[20];

        int dividend = powerOf10(digit - 1);
        for (int i : arr) {
            int i1 = getDigitIndex(dividend, i);
            counts[i1]++;
        }

        int[] pointers = new int[20];
        for (int i = 1; i < 20; i++) {
            pointers[i] = pointers[i - 1] + counts[i - 1];
        }

        int[] newArr = new int[arr.length];
        for (int i : arr) {
            int index = pointers[getDigitIndex(dividend, i)]++;
            newArr[index] = i;
        }

        return newArr;
    }

    private static int getDigitIndex(int dividend, int i) {
        int index = (i / dividend) % 10;
        if (i < 0) {
            index += 9;
        } else {
            index += 10;
        }
        return index;
    }

    private static int powerOf10(int exponent) {
        int result = 1;
        while (exponent > 0) {
            result *= 10;
            exponent--;
        }
        return result;
    }

    public static int[] count(int[] arr) {
        int min = 0;
        int max = 0;
        for (int i : arr) {
            if (i > max) {
                max = i;
            } else if (i < min) {
                min = i;
            }
        }

        int len = max - min + 1;
        int[] counts = new int[len];
        for (int i : arr) {
            counts[i - min]++;
        }

        int[] result = new int[arr.length];
        int resultIndex = 0;
        for (int count : counts) {
            while (count > 0) {
                result[resultIndex++] = min;
                count--;
            }
            min++;
        }
        return result;
    }
}
