package me.rowkey.libs.util;

/**
 * Author: Bryant Hang
 * Date: 16/7/7
 * Time: 18:07
 */
public class SortUtil {
    /**
     * 冒泡排序
     *
     * @param data
     */
    public static void bubbleSort(int data[]) {
        for (int i = 0; i < data.length - 1; i++) {
            boolean change = false;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    change = true;
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
            if (!change) break;
        }
    }

    /**
     * 快速排序
     * <p/>
     * int[] data = {8,2,5,6,7};
     * quickSort(data,0,data.length-1);
     *
     * @param data
     * @param left
     * @param right
     */
    public static void quickSort(int[] data, int left, int right) {
        if (left < right) {
            int d = division(data, left, right);
            quickSort(data, left, d - 1);
            quickSort(data, d + 1, right);
        }
    }

    private static int division(int[] data, int left, int right) {
        int baseNum = data[left];
        while (left < right) {
            while (baseNum <= data[right] && left < right) {
                right--;
            }
            data[left] = data[right];
            while (baseNum >= data[left] && left < right) {
                left++;
            }
            data[right] = data[left];
        }
        data[left] = baseNum;
        return left;
    }

    /**
     * 选择排序
     *
     * @param data
     */
    public static void selectSort(int data[]) {
        for (int i = 0; i < data.length - 1; i++) {
            int tempIndex = i;
            for (int j = i; j < data.length; j++) {
                if (data[j] < data[tempIndex]) {
                    tempIndex = j;
                }
            }
            int tmp = data[i];
            data[i] = data[tempIndex];
            data[tempIndex] = tmp;
        }
    }

    /**
     * 堆排序
     *
     * @param data
     */
    public static void heapSort(int[] data) {
        for (int i = data.length / 2 + 1; i >= 0; i--) {
            heapAdjust(data, i, data.length);
        }
        for (int i = data.length - 1; i > 0; i--) {
            int temp = data[i];
            data[i] = data[0];
            data[0] = temp;
            heapAdjust(data, 0, i);
        }
    }

    private static void heapAdjust(int[] data, int parent, int length) {
        int child = parent * 2 + 1;
        int temp = data[parent];
        while (child < length) {
            if (child + 1 < length && data[child] < data[child + 1]) {
                child++;
            }
            if (temp > data[child]) break;
            data[parent] = data[child];
            parent = child;
            child = 2 * parent + 1;
        }
        data[parent] = temp;
    }

    /**
     * 插入排序
     *
     * @param data
     */
    public static void insertSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int j = i - 1;
            int temp = data[i];
            for (; j >= 0 && temp < data[j]; j--) {
                data[j + 1] = data[j];
            }
            data[j + 1] = temp;
        }
    }

    /**
     * 希尔排序
     *
     * @param data
     */
    public static void shellSort(int[] data) {
        int step = data.length / 2;
        while (step > 0) {
            for (int i = step; i < data.length; i++) {
                int temp = data[i];
                int j = i - step;
                for (; j >= 0 && temp < data[j]; j = j - step) {
                    data[j + step] = data[j];
                }
                data[j + step] = temp;
            }
            step /= 2;
        }
    }

    /**
     * 归并排序
     *
     * @param data
     * @param to
     * @param left
     * @param right
     */
    public static void mergeSort(int[] data, int to[], int left, int right) {
        if (left == right) {
            to[left] = data[left];
        } else if (left < right) {
            int mid = (left + right) / 2;
            int[] temp = new int[data.length];
            mergeSort(data, temp, left, mid);
            mergeSort(data, temp, mid + 1, right);
            merge(temp, to, left, mid, right);
        }
    }

    private static void merge(int data[], int to[], int s, int m, int e) {
        int j = m + 1;
        int k = s;
        while (s <= m && j <= e) {
            if (data[s] < data[j]) {
                to[k++] = data[s++];
            } else {
                to[k++] = data[j++];
            }
        }

        while (s <= m)

        {
            to[k++] = data[s++];
        }

        while (j <= e)

        {
            to[k++] = data[j++];
        }
    }
}
