package com.acdprd.modules.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import su.bnet.utils.helper.Logg;

public class HardWork {
   private int id;

    public HardWork(int id){
        this.id = id;
        doWork();
    }

    private void doWork() {
        int size = 30000;
        log("start work ",""+id);
        int[] array = generateShuffledArray(size, 0, 1);
        simpleInsertSort(array);
        log("end work ",""+id);
    }

    private int[] generateShuffledArray(int size, int minValue, int multiplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
//            log("first for",String.valueOf(i));
            list.add(minValue + i*multiplier);
        }
        Collections.shuffle(list);
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
//            log("second for",String.valueOf(i));
            array[i] = list.get(i);
        }
        return array;
    }

    private void simpleInsertSort(int[] array) {
        int sortedSize = 1;
        while (sortedSize < array.length) {
            if(array[sortedSize] < array[sortedSize - 1]) {
                shiftElementToLeft(array, sortedSize);
            }
            sortedSize++;
        }
    }

    private void shiftElementToLeft(int[] array, int position) {
        while (position > 0 && array[position - 1] > array[position]) {
            swapItems(array, position, --position);
        }
    }

    private void swapItems(int[] array, int firstPosition, int secondPosition) {
        int firstValue = array[firstPosition];
        array[firstPosition] = array[secondPosition];
        array[secondPosition] = firstValue;
    }

    private static void log(String where,String what){
        Logg.Companion.v("HARDWORK :"+where,what);
    }
}
