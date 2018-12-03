package com.wjc.ccf.sort;

/**
 * 插入排序
 *          1、将指针指向某个元素，假设该元素左侧的元素全部有序，将该元素抽取出来，然后按照从右往左的顺序分别与其左边的元素比较，遇到比其大的元素便将元素右移，直到找到比该元素小的元素或者找到最左面发现其左侧的元素都比它大，停止；
 *
 *          2、此时会出现一个空位，将该元素放入到空位中，此时该元素左侧的元素都比它小，右侧的元素都比它大；
 *
 * 　　     3、指针向后移动一位，重复上述过程。每操作一轮，左侧有序元素都增加一个，右侧无序元素都减少一个。
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
