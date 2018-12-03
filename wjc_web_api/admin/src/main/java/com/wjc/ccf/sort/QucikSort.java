package com.wjc.ccf.sort;

/**
 * 快速排序
 *     1、从第一个元素开始，分别与后面的元素向比较，找到最小的元素与第一个元素交换位置；
 *
 * 　　2、从第二个元素开始，分别与后面的元素相比较，找到剩余元素中最小的元素，与第二个元素交换；
 *
 * 　　3、重复上述步骤，直到所有的元素都排成由小到大为止。
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
