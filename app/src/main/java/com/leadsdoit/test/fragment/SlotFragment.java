package com.leadsdoit.test.fragment;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.leadsdoit.test.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotFragment extends Fragment {
    private final int[] result = {2, 5, 4};
    private final int[] images;
    private final List<View> wheels;
    private Button spinButton;

    public SlotFragment() {
        wheels = new ArrayList<>();
        images = new int[]{R.drawable.sevent_done, R.drawable.orange_done, R.drawable.triple_done,
                R.drawable.waternelon_done, R.drawable.cherry_done, R.drawable.bar_done,
                R.drawable.lemon_done};
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slot, container, false);
        initButton(view);
        initWheels(view);
        setImagesToWheels(0);
        setImagesToWheels(1);
        setImagesToWheels(2);
        return view;
    }

    private void initWheels(View rootView) {
        wheels.add(rootView.findViewById(R.id.wheel_1));
        wheels.add(rootView.findViewById(R.id.wheel_2));
        wheels.add(rootView.findViewById(R.id.wheel_3));
    }

    private void setImagesToWheels(int wheelNumber) {
        int centerImage = result[wheelNumber];
        int topImage = centerImage - 1;
        if (topImage == -1) {
            topImage = images.length - 1;
        }
        int bottomImage = centerImage + 1;
        if (bottomImage == images.length) {
            bottomImage = 0;
        }
        ((ImageView) wheels.get(wheelNumber).findViewById(R.id.image_1))
                .setImageDrawable(ContextCompat.getDrawable(getContext(), images[bottomImage]));
        ((ImageView) wheels.get(wheelNumber).findViewById(R.id.image_2))
                .setImageDrawable(ContextCompat.getDrawable(getContext(), images[centerImage]));
        ((ImageView) wheels.get(wheelNumber).findViewById(R.id.image_3))
                .setImageDrawable(ContextCompat.getDrawable(getContext(), images[topImage]));
    }

    private void initButton(View rootView) {
        spinButton = rootView.findViewById(R.id.spin_button);
        spinButton.setOnClickListener(v -> {
            spinWheels();
        });
    }

    private void spinWheels() {
        moveWheelDown(0, new Random().nextInt(50));
        moveWheelDown(1, new Random().nextInt(40));
        moveWheelDown(2, new Random().nextInt(30));
    }

    private void moveWheelDown(int wheelNumber, int spinCount) {
        float stepHeight = wheels.get(wheelNumber).findViewById(R.id.image_1).getHeight();
        setImagesToWheels(wheelNumber);
        TranslateAnimation startAnimation =
                new TranslateAnimation(0
                        , 0
                        , 0
                        , stepHeight / 2f

                );
        startAnimation.setDuration(50 - spinCount);
        startAnimation.setFillAfter(true);
        TranslateAnimation endAnimation =
                new TranslateAnimation(0
                        , 0
                        , -stepHeight / 2f
                        , 0
                );
        endAnimation.setDuration(50 - spinCount);
        endAnimation.setFillAfter(true);
        startAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (result[wheelNumber] == images.length - 1) {
                    result[wheelNumber] = 0;
                } else {
                    result[wheelNumber] = result[wheelNumber] + 1;
                }
                setImagesToWheels(wheelNumber);
                wheels.get(wheelNumber).setY(-stepHeight / 2f);
                wheels.get(wheelNumber).startAnimation(endAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        endAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(spinCount > 0) {
                    moveWheelDown(wheelNumber, spinCount - 1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheels.get(wheelNumber).startAnimation(startAnimation);
    }
}
