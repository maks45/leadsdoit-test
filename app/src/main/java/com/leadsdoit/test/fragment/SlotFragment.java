package com.leadsdoit.test.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import com.leadsdoit.test.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotFragment extends Fragment {
    private final int[] result = {2, 1, 1};
    private final int[] images;
    private final List<View> wheels;
    private final boolean[] isRotating;
    private final List<Integer> imagesForWheels;
    private PopupWindow popupWindow;
    private TextView balanceTextView;
    private long balance = 250;
    private WeakReference<Context> context;

    public SlotFragment() {
        wheels = new ArrayList<>();
        imagesForWheels = new ArrayList<>();
        images = new int[]{
                R.drawable.sevent_done,
                R.drawable.orange_done,
                R.drawable.triple_done,
                R.drawable.waternelon_done,
                R.drawable.cherry_done,
                R.drawable.bar_done,
                R.drawable.lemon_done
        };
        isRotating = new boolean[]{false, false, false};
        imagesForWheels.add(images[0]);
        imagesForWheels.add(images[2]);
        imagesForWheels.add(images[1]);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        popupWindow = new PopupWindow(
                LayoutInflater.from(context.get()).inflate(R.layout.popup_win, null),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        View view = inflater.inflate(R.layout.fragment_slot, container, false);
        initButton(view);
        initWheels(view);
        setImagesToWheels(0);
        setImagesToWheels(1);
        setImagesToWheels(2);
        balanceTextView = view.findViewById(R.id.slot_balance);
        setBalance();
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
            topImage = imagesForWheels.size() - 1;
        }
        int bottomImage = centerImage + 1;
        if (bottomImage == imagesForWheels.size()) {
            bottomImage = 0;
        }
        ((ImageView) wheels.get(wheelNumber).findViewById(R.id.image_1))
                .setImageDrawable(ContextCompat.getDrawable(context.get(), imagesForWheels.get(bottomImage)));
        ((ImageView) wheels.get(wheelNumber).findViewById(R.id.image_2))
                .setImageDrawable(ContextCompat.getDrawable(context.get(), imagesForWheels.get(centerImage)));
        ((ImageView) wheels.get(wheelNumber).findViewById(R.id.image_3))
                .setImageDrawable(ContextCompat.getDrawable(context.get(), imagesForWheels.get(topImage)));
    }

    private void initButton(View rootView) {
        ImageButton spinButton = rootView.findViewById(R.id.spin_button);
        spinButton.setOnClickListener(v -> {
            balance = balance - 10;
            setBalance();
            moveWheelDown(0, new Random().nextInt(50));
            moveWheelDown(1, new Random().nextInt(40));
            moveWheelDown(2, new Random().nextInt(30));
        });
    }

    private void moveWheelDown(int wheelNumber, int spinCount) {
        float stepHeight = wheels.get(wheelNumber).findViewById(R.id.image_1).getHeight();
        isRotating[wheelNumber] = true;
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
                        , - stepHeight / 2f
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
                if (result[wheelNumber] == imagesForWheels.size() - 1) {
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
                if (spinCount > 0) {
                    moveWheelDown(wheelNumber, spinCount - 1);
                } else {
                    isRotating[wheelNumber] = false;
                    checkResult();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheels.get(wheelNumber).startAnimation(startAnimation);
    }

    private void setBalance() {
        balanceTextView.setText(String.valueOf(balance));
    }

    private void checkResult() {
        if (!isRotating[0] && !isRotating[1] && !isRotating[2]
                && imagesForWheels.get(result[0]).equals(imagesForWheels.get(result[1]))
                && imagesForWheels.get(result[1]).equals(imagesForWheels.get(result[2]))) {
            showWinDialog();
            balance = balance + 50;
            setBalance();
            imagesForWheels.add(images[new Random().nextInt(images.length - 1)]);
        }
    }

    private void showWinDialog() {
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
    }
}
