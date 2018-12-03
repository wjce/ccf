package com.wjc.ccf.sort;

/**
 * @author wangjunce 2018/11/30 10:33
 */
public class ShellSort {

    public static void shellSort(int[] arr){
        int increment = arr.length;

        int param;
        int param2;
        int j;
        while (increment > 1){
            increment = increment/3 + 1;
            for(int i = increment; i < arr.length; i++){
                param = arr[i];
                j = i - increment;
                while(j > 0){
                    if(param < arr[j]){
                        param2 = arr[j];
                        arr[j] = param;
                        arr[j + increment] = param2;
                    }
                    j -= increment;
                }
            }
        }
    }
}
