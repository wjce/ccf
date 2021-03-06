package com.wjc.ccf.sort;

/**
 *      大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
 *
 *      小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
 *
 *      堆排序的基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。将其与末尾元素进行交换，此时末尾就为最大值。
 *      然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了
 *      思路：
 *      a.将无需序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
 *
 * 　　 b.将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
 *
 * 　　 c.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
 * @author wangjunce 2018/11/29 15:10
 */
public class SeapSort {

    public static int[] seapSort(int[] arr){

        // 按照完全二叉树的特点，从最后一个非叶子节点开始，对于整棵树进行大根堆的调整
        // 也就是说，是按照自下而上，每一层都是自右向左来进行调整的
        // 注意，这里元素的索引是从0开始的
        // 另一件需要注意的事情，这里的建堆，是用堆调整的方式来做的
        // 堆调整的逻辑在建堆和后续排序过程中复用的
        for(int i = arr.length/2-1; i >= 0; i--){
            sort(arr, i, arr.length);
        }

        for(int i1 = arr.length-1; i1 > 0; i1--){
            //每一次的堆调整之后，都会有一个元素到达自己的最终位置
            int param = arr[i1];
            arr[i1] = arr[0];
            arr[0] = param;
            //自上而下，自左向右进行调整
            sort(arr, 0, i1);
        }
        return arr;
    }

    public static void sort(int[] arr, int i, int length){

        int param = arr[i];
        // 可以参照sort中的调用逻辑，在堆建成，且完成第一次交换之后，实质上i=0；也就是说，是从根所在的最小子树开始调整的
        // 接下来的讲解，都是按照i的初始值为0来讲述的
        // 这一段很好理解，如果i=0；则k=1；k+1=2
        // 实质上，就是根节点和其左右子节点记性比较，让k指向这个不超过三个节点的子树中最大的值
        // 这里，必须要说下为什么k值是跳跃性的。
        // 首先，举个例子，如果a[0] > a[1]&&a[0]>a[2],说明0,1,2这棵树不需要调整，那么，下一步该到哪个节点了呢？肯定是a[1]所在的子树了，
        // 也就是说，是以本节点的左子节点为根的那棵小的子树
        // 而如果a[0}<a[2]呢，那就调整a[0]和a[2]的位置，然后继续调整以a[2]为根节点的那棵子树，而且肯定是从左子树开始调整的
        // 所以，这里面的用意就在于，自上而下，自左向右一点点调整整棵树的部分，直到每一颗小子树都满足大根堆的规律为止
        for(int j = i*2+1; j < length; j = j*2+1){
            //如果左节点小于右节点，选择右节点
            if(j + 1 < length && arr[j] < arr[j+1])
                j++;
            //如果子节点大于当前节点数据，将替换当前节点数据
            if(arr[j] > param){
                arr[i] = arr[j];
                i = j;
            }else{
                break;
            }
        }
        arr[i] = param;
    }
}
