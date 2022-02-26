package com.example.project2;

import java.util.Random;

public class RandomNumbersArray {
    Integer [] numbers;
    int limitNumber;
    int nLevel1=8;
    int nLevel2=7;
    int nLevel3=2;
    RandomNumbersArray(int limitNumber){
        this.limitNumber = limitNumber;
        numbers= new Integer[limitNumber];
        Random r =new Random();
        for(int i=0; i<10;i++){//1단계 문제 5개
            if(i<5){
                numbers[i]= r.nextInt(nLevel1);
            }else if(i<9){
                numbers[i]= r.nextInt(nLevel2)+nLevel1;
            }else{
                numbers[i]= r.nextInt(nLevel3)+nLevel2+nLevel1;
            }
            for (int j = 0; j < i; j++) {
                if(numbers[i]==numbers[j]){
                    i--;
                }
            }
        }
    }

    public Integer[] getNumbers() {
        return numbers;
    }
}
