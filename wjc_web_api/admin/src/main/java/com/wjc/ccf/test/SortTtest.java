package com.wjc.ccf.test;

import java.util.Arrays;
import java.util.List;

/**
 * @author wjc
 * @description
 * @date 2019/10/16
 */
public class SortTtest {

    public static void main(String[] args) {
//        System.outz.println(reverse(123));
//        System.out.println(reverse(1534236469));
//        System.out.println(myAtoi("0-1"));
//        System.out.println(isPalindrome(-121));
//        System.out.println(romanToInt("MCMXCIV"));
//        System.out.println(climbStairs(5));
//        System.out.println(fib(15));
//        int[] arr = {0,1,1,2,2,3,5,5,6};
//        System.out.println(removeElement(arr, 2));
//        int[] arr = {1,1,2};
//        System.out.println(removeDuplicates(arr));
//        int[] arr = {0,0,1,1,1,1,2,2,3};
//        System.out.println(removeDuplicates2(arr));
//        int[] arr = {1,3,5,6};
//        System.out.println(searchInsert(arr, 0));

        int[] arr = {9,9,9,9};
        Arrays.stream(plusOne(arr)).forEach(i -> System.out.println(i));
    }

    public static int[] plusOne(int[] digits) {
        for (int i = digits.length-1; i >=0; i--) {
            int last = digits[i];
            if(last+1 > 9){
                digits[i] = 0;
            }else {
                digits[i] = last+1;
                return digits;
            }
        }
        digits = new int[digits.length+1];
        digits[0] = 1;
        return digits;
    }

    public static int searchInsert(int[] nums, int target) {
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if(nums[i] == target || nums[i] > target){
                return i;
            }
        }
        return length;
    }

    public static int removeDuplicates2(int[] nums) {
        int a = 0;
        int b = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if(i == 0){
                b = nums[i];
                a++;
                count++;
                continue;
            }

            if(nums[i] == b){
                if(count == 2){
                    continue;
                }
                count++;
            }else{
                b = nums[i];
                count = 1;
            }

            nums[a] = nums[i];
            a++;
        }
        return a;
    }
    //删除排序数组中的重复项
    public static int removeDuplicates(int[] nums) {
        int a = 0;
        int b = -1;

        for (int i = 0; i < nums.length; i++) {
            int c = nums[i];
            if(i==0){
                b=c;
                a++;
                continue;
            }
            if(b != c){
                b = c;
                nums[a] = b;
                a++;
            }
        }
        return a;
    }

    //移除元素
    public static int removeElement(int[] nums, int val) {
        int a = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val){
                nums[a] = nums[i];
                a++;
            }
        }
        return a;
    }

    public static int fib(int N) {
        if(N == 0){
            return 0;
        }
        if(N == 1){
            return 1;
        }

        return fib(N-1) + fib(N -2);
    }

    //爬楼梯
    public static int climbStairs(int n) {
        if(n==0){
            return 0;
        }

        if(n==1 || n==2){
            return n;
        }

        int a = 1;
        int b = 2;

        for(int i = 3; i <= n; i++){
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static int romanToInt(String s) {

        int a = 0;
        char[] arr = s.toCharArray();
        int length = arr.length;

        for (int i = 0; i < length; i++) {
            switch (arr[i]){
                case 'I':
                    if(i < length-1){
                        char next = arr[i+1];
                        if(next=='V' || next == 'X'){
                            a -= 1;
                            break;
                        }
                    }
                    a += 1;
                    break;
                case 'V': a += 5;
                break;
                case 'X':
                    if(i < length-1){
                        char next = arr[i+1];
                        if(next=='L' || next == 'C'){
                            a -= 10;
                            break;
                        }
                    }
                    a += 10;
                    break;
                case 'L': a += 50;
                break;
                case 'C':
                    if(i < length-1){
                        char next = arr[i+1];
                        if(next=='D' || next == 'M'){
                            a -= 100;
                            break;
                        }
                    }
                    a += 100;
                    break;
                case 'D': a += 500;break;
                case 'M': a += 1000;break;
            }
        }

        return a;
    }

    public static boolean isPalindrome(int x) {
        if(0<=x && x<10){
            return true;
        }
        if(x<0 || x%10 == 0){
            return false;
        }

        int a = 0;
        int b = x;
        while(true){
            a = (x%10) + a*10;
            x = x/10;
            if(x==0)
                break;
        }

        return a == b;
    }

    public static int reverse(int x) {
        int a = 0;
        while(true){
            int b = x % 10;
            if(a > Integer.MAX_VALUE / 10 ) {
                return 0;
            }
            if(a < Integer.MIN_VALUE / 10 ) {
                return 0;
            }
            a = a*10 + b;

            x = x/10;
            if(x==0){
                return a;
            }
        }
    }

    public static int myAtoi(String str) {
        if(str.equals("")){
            return 0;
        }

        char[] arr = str.toCharArray();

        long result = 0;
        char first = 0;
        boolean b = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ((byte) (' '))) {
                if(b)
                    break;
                continue;
            } else {
                b = true;
                if (arr[i] == ((byte) ('+')) || arr[i] == ((byte) ('-'))) {
                    if(first == ((byte) (0))){
                        first = arr[i];
                        continue;
                    }else {
                        break;
                    }
                } else if (Character.isDigit(arr[i])) {
                    result = result * 10 + (arr[i] - '0');
                    if (first != 45 && result > Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                    if (first == 45 && result * -1 < Integer.MIN_VALUE) {
                        return Integer.MIN_VALUE;
                    }
                } else {
                    break;
                }
            }
        }

        if(first == ((byte) ('-'))){
            result *= -1;
        }
        return (int)result;
    }

}
