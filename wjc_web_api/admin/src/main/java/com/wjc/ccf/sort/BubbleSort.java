package com.wjc.ccf.sort;

/**
 * 冒泡排序
 * 通过与相邻元素的比较和交换来把小的数交换到最前面。这个过程类似于水泡向上升一样，因此而得名。举个栗子，对5,3,8,6,4这个无序序列进行冒泡排序。
 * 首先从后向前冒泡，4和6比较，把4交换到前面，序列变成5,3,8,4,6。同理4和8交换，变成5,3,4,8,6,3和4无需交换。5和3交换，变成3,5,4,8,6,3.这样一次冒泡就完了，
 * 把最小的数3排到最前面了。对剩下的序列依次冒泡就会得到一个有序序列。冒泡排序的时间复杂度为O(n^2)。
 * @author wangjunce 2018/11/28 15:16
 */
public class BubbleSort {
    public static void main(String[] args) {

        int[] arr = {12,3,123,5,13,765,34};
//        arr = bubbleSort(arr);
//        arr = SelectionSort.selectionSort(arr);
//        arr = InsertionSort.insertionSort(arr);
//        arr = QucikSort.quickSort(arr, 0, arr.length-1);
        SeapSort.seapSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }

    }

    public static int[] bubbleSort(int[] arrs){

        if(arrs == null)
            return null;
        if(arrs.getClass().getComponentType()==null) {
            throw new RuntimeException("aaaa");
        }
        for (int i = 0; i < arrs.length; i++) {
            for(int i1 = i+1; i1 < arrs.length; i1++){
                if(arrs[i1] < arrs[i]){
                    int param = arrs[i];
                    arrs[i] = arrs[i1];
                    arrs[i1] = param;
                }
            }
        }

        return arrs;
    }
}
