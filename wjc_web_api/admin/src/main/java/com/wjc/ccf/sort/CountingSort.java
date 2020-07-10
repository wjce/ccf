package com.wjc.ccf.sort;

import java.util.Arrays;

/**
 * 计数排序
 * @author wangjunce 2018/11/30 15:17
 */
public class CountingSort {
    public static void countingSort(int[] arr){
        int param = arr[0];
        int min = arr[0];
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {  //获取最大最小值
            if(arr[i] > max)
                max = arr[i];
            if(arr[i] < min)
                min = arr[i];
        }
        param = 0 - min;
        int[] newArr = new int[max-min+1];  //要排序的数组中，元素大小的极值差+1
        Arrays.fill(newArr, 0);
        for(int i = 0; i < arr.length; i++){
            newArr[arr[i]+param]++;     //优化过的地方，减小了数组newArray的大小
        }

        int index = 0;
        int i = 0;
        while(index < arr.length){
            if(newArr[i] != 0){
                arr[index] = i - param;
                newArr[i]--;
                index++;
            }else{
                i++;
            }
        }
    }
}
