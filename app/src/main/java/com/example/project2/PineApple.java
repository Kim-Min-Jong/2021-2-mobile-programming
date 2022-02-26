package com.example.project2;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.TimerTask;

public class PineApple extends androidx.appcompat.widget.AppCompatImageView {

    int heightInDp;
    int widthInDp;

    public PineApple(@NonNull Context context) {
        super(context);
        this.setImageResource(R.drawable.pineapple_1);
    }

    public void setSizeDp(int height, int width) {
        int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        int widthInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        this.getLayoutParams().height = heightInDp;
        this.getLayoutParams().width = widthInDp;
    }


    public boolean collision(ImageView view) {
        float leftSide = view.getX() - view.getLayoutParams().width / 2;
        float rightSide = view.getX() + view.getLayoutParams().width / 2;
        float downSide = view.getY() + view.getLayoutParams().height / 2;
        float upSide = view.getY() - view.getLayoutParams().height / 2;

//        System.out.println(this.getX());
//        System.out.println(this.getY());

        float myLeftSide = this.getX() - this.getLayoutParams().width / 2;
        float myRightSide = this.getX() + this.getLayoutParams().width / 2;
        float myDownSide = this.getY() + this.getLayoutParams().height / 2;
        float myUpSide = this.getY() - this.getLayoutParams().height / 2;


//        if(myLeftSide >leftSide) {
//
//
//            System.out.println("left = " + leftSide);
//            System.out.println("right = " + rightSide);
//            System.out.println("down = " + downSide);
//            System.out.println("up = " + upSide);
////
//            System.out.println("myLeft = " + myLeftSide);
//            System.out.println("myRight = " + myRightSide);
//            System.out.println("myDown = " + myDownSide);
//            System.out.println("myUp = " + myUpSide);
//        }

        if (((rightSide < myLeftSide) || (leftSide > myRightSide))
                || ((downSide < myUpSide) || (upSide > myDownSide))) {
//            System.out.println("false");
            return false;
        } else{
//            System.out.println("true");
            return true;
        }

    }


}
