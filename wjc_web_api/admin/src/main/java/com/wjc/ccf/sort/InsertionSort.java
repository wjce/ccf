package com.wjc.ccf.sort;

/**
 * 插入排序
 * 插入排序不是通过交换位置而是通过比较找到合适的位置插入元素来达到排序的目的的。相信大家都有过打扑克牌的经历，特别是牌数较大的。在分牌时可能要整理自己的牌，牌多的时候怎么整理呢？就是拿到一张牌，找到一个合适的位置插入。这个原理其实和插入排序是一样的。举个栗子，对5,3,8,6,4这个无序序列进行简单插入排序，首先假设第一个数的位置时正确的，想一下在拿到第一张牌的时候，没必要整理。然后3要插到5前面，把5后移一位，变成3,5,8,6,4.想一下整理牌的时候应该也是这样吧。然后8不用动，6插在8前面，8后移一位，4插在5前面，从5开始都向后移一位。注意在插入一个数的时候要保证这个数前面的数已经有序。简单插入排序的时间复杂度也是O(n^2)。
 * @author wangjunce 2018/11/28 16:05
 */
public class InsertionSort {

    public static int[] insertionSort(int[] arr){

        for (int i = 0; i < arr.length; i++) {
            if(i == arr.length-1)
                break;
            if(arr[i+1] < arr[i]){
                int param = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = param;
            }
        }
        return arr;
    }
}
