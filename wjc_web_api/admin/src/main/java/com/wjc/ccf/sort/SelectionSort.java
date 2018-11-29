package com.wjc.ccf.sort;

/**
 * 选择排序
 * 选择排序的思想其实和冒泡排序有点类似，都是在一次排序后把最小的元素放到最前面。但是过程不同，冒泡排序是通过相邻的比较和交换。
 * 而选择排序是通过对整体的选择。举个栗子，对5,3,8,6,4这个无序序列进行简单选择排序，首先要选择5以外的最小数来和5交换，
 * 也就是选择3和5交换，一次排序后就变成了3,5,8,6,4.对剩下的序列一次进行选择和交换，最终就会得到一个有序序列。
 * 其实选择排序可以看成冒泡排序的优化，因为其目的相同，只是选择排序只有在确定了最小数的前提下才进行交换，大大减少了交换的次数。
 * 选择排序的时间复杂度为O(n^2)。
 * @author wangjunce 2018/11/28 15:53
 */
public class SelectionSort {

    public static int[] selectionSort(int[] arr){

        for (int i = 0; i < arr.length-1; i++) {
            int min = i;
            for (int i1 = i+1; i1 < arr.length; i1++) {
                if(arr[min] > arr[i1]){
                    min = i1;
                }
            }
            if(min != i){
                int param = arr[min];
                arr[min] = arr[i];
                arr[i] = param;
            }
        }
        return arr;
    }
}
