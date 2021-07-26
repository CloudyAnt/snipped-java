package cn.itscloudy.snippedjava.algorithm;

import java.util.Arrays;
import java.util.function.UnaryOperator;

public enum SortingAlgorithm {
    SELECTION(SortingAlgorithm::selection),
    BUBBLE(SortingAlgorithm::bubble),
    INSERTION(SortingAlgorithm::insertion),
    QS(SortingAlgorithm::qs), // quick sort
    HEAP(SortingAlgorithm::heap),
    MERGE(SortingAlgorithm::merge),
    SHELL(SortingAlgorithm::shell),
    RADIX(SortingAlgorithm::radix),
    BUCKET(SortingAlgorithm::bucket),
    COUNTING(SortingAlgorithm::counting),
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

    private static void qs0(int[] arr, int l, int r) {
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

    private static int[] counting(int[] arr) {
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

    private static int[] shell(int[] arr) {
        int gap = arr.length / 2;
        while (gap > 0) {
            shellSort(arr, gap);
            gap--;
        }
        return arr;
    }

    private static void shellSort(int[] arr, int gap) {
        int cur = 0;
        int next = cur + gap;
        while (next < arr.length) {
            if (arr[cur] > arr[next]) {
                swap(arr, cur, next);
                shellSort1(arr, gap, cur);
            }
            cur += gap;
            next += gap;
        }
    }

    private static void shellSort1(int[] arr, int gap, int cur) {
        int previous = cur - gap;
        while (previous >= 0) {
            if (arr[cur] >= arr[previous]) {
                break;
            }
            swap(arr, cur, previous);
            cur -= gap;
            previous -= gap;
        }
    }

    private static int[] bucket(int[] arr) {
        int len = arr.length;

        int max;
        int min;
        max = min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            } else if (arr[i] < min) {
                min = arr[i];
            }
        }
        int length = max - min + 1;

        // calculate each bucket sizes
        int[] bucketSizes = new int[len];
        for (int i : arr) {
            int bkIdx = ((i - min) * len) / length;
            bucketSizes[bkIdx]++;
        }

        // calculate each bucket start index
        int[] bucketStarts = new int[len + 1];
        bucketStarts[len] = len;
        for (int i = 1; i < len; i++) {
            bucketStarts[i] = bucketStarts[i - 1] + bucketSizes[i - 1];
        }

        // put elements to buckets
        int[] bucketIndices = Arrays.copyOf(bucketStarts, len);
        int[] newArr = new int[len];
        for (int i : arr) {
            int bkIdx = ((i - min) * len) / length;
            int index = bucketIndices[bkIdx]++;
            newArr[index] = i;
        }

        // sort each bucket by insertion sort
        for (int i = 0; i < len; i++) {
            insertion(newArr, bucketStarts[i], bucketStarts[i + 1]);
        }
        return newArr;
    }

    private static void insertion(int[] newArr, int start, int end) {
        for (int i = start; i < end - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (newArr[j] > newArr[j - 1]) {
                    break;
                }
                swap(newArr, j, j - 1);
            }
        }
    }

    private static int[] merge(int[] arr) {
        mergeSort(arr, 0, arr.length);
        return arr;
    }

    private static void mergeSort(int[] arr, int l, int r) {
        int length = r - l;
        if (length > 2) {
            int middle = length / 2 + l;
            mergeSort(arr, l, middle);
            mergeSort(arr, middle, r);

            int[] merge = new int[length];
            int li = l;
            int ri = middle;
            for (int i = 0; i < length; i++) {
                if (li < middle && (ri == r || arr[li] <= arr[ri])) {
                    merge[i] = arr[li++];
                } else {
                    merge[i] = arr[ri++];
                }
            }
            System.arraycopy(merge, 0, arr, l, length);
        } else if (length == 2) {
            if (arr[l] > arr[r - 1]) {
                swap(arr, l, r - 1);
            }
        }

    }
}
