package com.wjc.ccf.sort;

/**
 * 冒泡排序
 *     1、从第一个数据开始，与第二个数据相比较，如果第二个数据小于第一个数据，则交换两个数据的位置。
 *
 * 　　2、指针由第一个数据移向第二个数据，第二个数据与第三个数据相比较，如果第三个数据小于第二个数据，则交换两个数据的位置。
 *
 * 　　3、依此类推，完成第一轮排序。第一轮排序结束后，最大的元素被移到了最右面。
 *
 * 　　4、依照上面的过程进行第二轮排序，将第二大的排在倒数第二的位置。
 *
 * 　　5、重复上述过程，没排完一轮，比较次数就减少一次。
 * @author wangjunce 2018/11/28 15:16
 */
public class BubbleSort {
    public static void main(String[] args) {

        int[] arr = {12,3,123,5,13,765,34};
//        arr = bubbleSort(arr);
//        arr = SelectionSort.selectionSort(arr);
//        arr = InsertionSort.insertionSort(arr);
//        arr = QucikSort.quickSort(arr, 0, arr.length-1);
//        SeapSort.seapSort(arr);
//        ShellSort.shellSort(arr);
        CountingSort.countingSort(arr);
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
