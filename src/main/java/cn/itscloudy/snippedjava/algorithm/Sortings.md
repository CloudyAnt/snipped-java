# Sorting Algorithms

This document describes the sorting algorithms implemented in [Sorting.java](Sorting.java).

## Overview

| Algorithm      | Best Time  | Average Time     | Worst Time | Space    | Stable |
|----------------|------------|------------------|------------|----------|--------|
| Selection Sort | O(n²)      | O(n²)            | O(n²)      | O(1)     | No     |
| Bubble Sort    | O(n)       | O(n²)            | O(n²)      | O(1)     | Yes    |
| Insertion Sort | O(n)       | O(n²)            | O(n²)      | O(1)     | Yes    |
| Quick Sort     | O(n log n) | O(n log n)       | O(n²)      | O(log n) | No     |
| Heap Sort      | O(n log n) | O(n log n)       | O(n log n) | O(1)     | No     |
| Merge Sort     | O(n log n) | O(n log n)       | O(n log n) | O(n)     | Yes    |
| Shell Sort     | O(n log n) | O(n^1.3) - O(n²) | O(n²)      | O(1)     | No     |
| Radix Sort     | O(nk)      | O(nk)            | O(nk)      | O(n + k) | Yes    |
| Bucket Sort    | O(n + k)   | O(n + k)         | O(n²)      | O(n + k) | Yes    |
| Counting Sort  | O(n + k)   | O(n + k)         | O(n + k)   | O(k)     | Yes    |

*k = number of buckets/digits, n = array size*

---

## 1. Selection Sort

**Approach**: Find the minimum element and place it at the beginning. Repeat for the remaining unsorted portion.

```java
private static int[] selection(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }
        }
        if (minIndex != i) {
            swap(arr, i, minIndex);
        }
    }
    return arr;
}
```

**Characteristics**:
- **Time**: O(n²) for all cases
- **Space**: O(1)
- **Stable**: No (can swap distant elements)
- **Use cases**: Small datasets, memory-constrained environments

---

## 2. Bubble Sort

**Approach**: Repeatedly swap adjacent elements if they are in the wrong order. Early termination when no swaps occur.

```java
private static int[] bubble(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        boolean noSwap = true;
        for (int j = 0; j < arr.length - i; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
                noSwap = false;
            }
        }
        if (noSwap) {
            break;
        }
    }
    return arr;
}
```

**Characteristics**:
- **Time**: O(n) best, O(n²) average/worst
- **Space**: O(1)
- **Stable**: Yes
- **Use cases**: Nearly sorted data, educational purposes

---

## 3. Insertion Sort

**Approach**: Build sorted array one element at a time by inserting each element into its correct position.

```java
private static int[] insertion(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int j = i;
        for (; j > 0 && arr[j - 1] > key; j--) {
            arr[j] = arr[j - 1];
        }
        arr[j] = key;
    }
    return arr;
}
```

**Characteristics**:
- **Time**: O(n) best, O(n²) average/worst
- **Space**: O(1)
- **Stable**: Yes
- **Use cases**: Small datasets, nearly sorted data, online sorting (streaming data)

---

## 4. Quick Sort

**Approach**: Select a pivot, partition array around pivot, recursively sort sub-arrays. This implementation uses the last element as pivot.

```java
private static int[] qs(int[] arr) {
    qs0(arr, 0, arr.length - 1);
    return arr;
}

private static int partition(int[] arr, int l, int r) {
    int partitionIndex = l - 1;
    for (; l < r; l++) {
        if (arr[l] < arr[r]) {
            swap(arr, ++partitionIndex, l);
        }
    }
    swap(arr, ++partitionIndex, r);
    return partitionIndex;
}
```

**Characteristics**:
- **Time**: O(n log n) best/average, O(n²) worst (when pivot is always min/max)
- **Space**: O(log n) for recursion stack
- **Stable**: No
- **Use cases**: General-purpose sorting, large datasets

---

## 5. Heap Sort

**Approach**: Build a max-heap from the array, then repeatedly extract the maximum element.

```java
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
```

**Characteristics**:
- **Time**: O(n log n) for all cases
- **Space**: O(1)
- **Stable**: No
- **Use cases**: Memory-constrained environments, real-time systems

---

## 6. Merge Sort

**Approach**: Divide array into halves, recursively sort each half, then merge sorted halves.

```java
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
    } else if (length == 2 && arr[l] > arr[r - 1]) {
        swap(arr, l, r - 1);
    }
}
```

**Characteristics**:
- **Time**: O(n log n) for all cases
- **Space**: O(n) for temporary merge array
- **Stable**: Yes
- **Use cases**: External sorting, linked lists, guaranteed performance

---

## 7. Shell Sort

**Approach**: Generalizes insertion sort by comparing elements separated by a gap, reducing the gap over time.

```java
private static int[] shell(int[] arr) {
    int gap = arr.length / 2;
    while (gap > 0) {
        shellSort(arr, gap);
        gap /= 2;
    }
    return arr;
}
```

**Characteristics**:
- **Time**: O(n log n) best, O(n^1.3) - O(n²) average/worst (depends on gap sequence)
- **Space**: O(1)
- **Stable**: No
- **Use cases**: Medium-sized datasets, when simpler than quicksort is needed

---

## 8. Radix Sort

**Approach**: Sort by individual digits (LSD to MSD), using counting sort for each digit position.

```java
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
```

**Characteristics**:
- **Time**: O(nk) where k is number of digits
- **Space**: O(n + k) where k = 20 (for -9 to 9 digit indices)
- **Stable**: Yes
- **Use cases**: Integer sorting, fixed-length string sorting

---

## 9. Bucket Sort

**Approach**: Distribute elements into buckets based on value ranges, sort each bucket with insertion sort.

```java
private static int[] bucket(int[] arr) {
    int len = arr.length;
    int max = min = arr[0];
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] > max) max = arr[i];
        else if (arr[i] < min) min = arr[i];
    }
    int length = max - min + 1;

    int[] bucketSizes = new int[len];
    for (int i : arr) {
        int bkIdx = ((i - min) * len) / length;
        bucketSizes[bkIdx]++;
    }

    int[] bucketStarts = new int[len + 1];
    bucketStarts[len] = len;
    for (int i = 1; i < len; i++) {
        bucketStarts[i] = bucketStarts[i - 1] + bucketSizes[i - 1];
    }

    int[] bucketIndices = Arrays.copyOf(bucketStarts, len);
    int[] newArr = new int[len];
    for (int i : arr) {
        int bkIdx = ((i - min) * len) / length;
        int index = bucketIndices[bkIdx]++;
        newArr[index] = i;
    }

    for (int i = 0; i < len; i++) {
        insertion(newArr, bucketStarts[i], bucketStarts[i + 1]);
    }
    return newArr;
}
```

**Characteristics**:
- **Time**: O(n + k) best/average, O(n²) worst (all elements in one bucket)
- **Space**: O(n + k) where k = number of buckets
- **Stable**: Yes
- **Use cases**: Uniformly distributed data, floating-point numbers

---

## 10. Counting Sort

**Approach**: Count occurrences of each value, then reconstruct sorted array using counts.

```java
private static int[] counting(int[] arr) {
    int min = 0;
    int max = 0;
    for (int i : arr) {
        if (i > max) max = i;
        else if (i < min) min = i;
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
```

**Characteristics**:
- **Time**: O(n + k) where k = max - min + 1
- **Space**: O(k)
- **Stable**: Yes
- **Use cases**: Integer sorting with limited range, frequency counting

---

## Stability Comparison

A sorting algorithm is **stable** if elements with equal keys maintain their relative order.

| Stable Algorithms | Unstable Algorithms |
|-------------------|---------------------|
| Bubble Sort       | Selection Sort      |
| Insertion Sort    | Quick Sort          |
| Merge Sort        | Heap Sort           |
| Radix Sort        | Shell Sort          |
| Bucket Sort       |                     |
| Counting Sort     |                     |

---

## Choosing a Sorting Algorithm

| Scenario                         | Recommended Algorithm       |
|----------------------------------|-----------------------------|
| Small arrays (< 50 elements)     | Insertion Sort              |
| Nearly sorted data               | Insertion Sort, Bubble Sort |
| General purpose, average case    | Quick Sort                  |
| Guaranteed O(n log n) worst case | Merge Sort, Heap Sort       |
| Memory constrained               | Heap Sort, Selection Sort   |
| Integer sorting, limited range   | Counting Sort, Radix Sort   |
| Uniformly distributed values     | Bucket Sort                 |
