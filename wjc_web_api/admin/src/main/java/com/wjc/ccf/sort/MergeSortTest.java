package com.wjc.ccf.sort;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSortTest {

    public static void mergeSort(int[] arr) {
        int[] newArr = new int[arr.length];
        sort(arr, newArr, 0 , arr.length-1);
    }

    public static void sort(int[] arr, int[] newArr, int left, int right){
//        System.out.println(mid+"left:"+left+"right:"+right);

        if(left < right) {
            int mid = (left+right)/2;
            //通过mid去拆分左侧节点
            sort(arr, newArr, left, mid);
            //mid右侧为mid+1
            sort(arr, newArr, mid+1, right);
            merge(left, right, mid, arr, newArr);
        }
    }

    public static void merge(int left, int right, int mid, int[] arr, int[] newArr){
        int m = mid+1;
        int l = left;
        int r = right;
        int i = 0;

        //索引left小于mid，索引m小于最大索引right，因为不确定是左边还是右边进来排序，所以都要判断。
        while (l <= mid && m <= r){
            //通过判断大小将数据存入新数组
            if(arr[l] < arr[m]){
                newArr[i++] = arr[l++];
            }else{
                newArr[i++] = arr[m++];
            }
        }

        //如left=0,mid=1，那么排序后新数组后面的值都是0，所以需要将原数组的值赋到新数组中
        while (l <= mid){
            newArr[i++] = arr[l++];
        }
        //同理，只是上面判断的是mid左边，现在判断mid右边
        while(m <= r){
            newArr[i++] = arr[m++];
        }

        //因为新数组的数据是排序后的，所以要将新数组的值赋给原数组。将新数组索引归0，进行循环
        i=0;
        while (left<=right){
            arr[left++] = newArr[i++];
        }

        for(int n : arr){
            System.out.println("----> "+n);
        }
        System.out.println("------------------");
    }
}
