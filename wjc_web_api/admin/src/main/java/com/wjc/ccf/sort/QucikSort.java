package com.wjc.ccf.sort;

/**
 * @author wangjunce 2018/11/28 16:50
 */
public class QucikSort {
    public static int[] quickSort(int[] arr, int left, int right){

        int param = 0;
        if(left < right){
            param = partition(arr, left, right);
            quickSort(arr, left, param-1);
            quickSort(arr, param+1, right);
        }

        return arr;
    }

    public static int partition(int[] arr, int left, int right){

        int param = arr[left];

        while(left < right){
            while(left < right && param <= arr[right])
                right--;
            arr[left] = arr[right];
            while (left < right && arr[left] <= param)
                left++;
            arr[right] = arr[left];
        }
        arr[left] = param;
        return left;
    }
}
