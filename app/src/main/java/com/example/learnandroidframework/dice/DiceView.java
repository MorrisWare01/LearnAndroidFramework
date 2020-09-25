package com.example.learnandroidframework.dice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.learnandroidframework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmw on 2020/9/25.
 */
public class DiceView extends View {

    // 建筑物
    private Bitmap mBuildingBitmap;
    private Rect mBuildingRect;
    // 玩家
    private Bitmap mPlayerBitmap;
    // 棋盘
    private Bitmap mBoardBitmap;
    private Rect mBoardRect;

    private Paint mPaint = new Paint();

    private List<DiceBuilding> diceBuildingList = new ArrayList<>();
    private List<DiceChess> diceChessList = new ArrayList<>();
    private DicePlayer dicePlayer = new DicePlayer();

    public DiceView(Context context) {
        this(context, null);
    }

    public DiceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;

        Bitmap tmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tmp);
        canvas.drawColor(Color.parseColor("#b38d74"));

        mBuildingBitmap = Bitmap.createScaledBitmap(tmp, widthPixels / 3, (int) (widthPixels / 3 * 1.5f), false);
        mBuildingRect = new Rect(0, 0, mBuildingBitmap.getWidth(), mBuildingBitmap.getHeight());

        mPlayerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.player);

        canvas.drawColor(Color.parseColor("#ff0000"));

        mBoardBitmap = Bitmap.createScaledBitmap(tmp, dp2px(getContext(), 30), dp2px(getContext(), 30), false);
        mBoardRect = new Rect(0, 0, mBoardBitmap.getWidth(), mBoardBitmap.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;

        setMeasuredDimension(widthPixels, (int) (heightPixels * 2f));
        onSizeChanged(getMeasuredWidth(), getMeasuredHeight(), 0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int x = 0;
        int y = 0;
        int magrin = dp2px(getContext(), 20);
        int leftBottom = dp2px(getContext(), 100);
        diceBuildingList.clear();
        for (int i = 0; i < 5; i++) {
            x = w - mBuildingBitmap.getWidth();
            y = h - (i + 1) * mBuildingBitmap.getHeight() - i * magrin;
            diceBuildingList.add(new DiceBuilding(new Rect(x, y, x + mBuildingBitmap.getWidth(), y + mBuildingBitmap.getHeight())));

            diceBuildingList.add(new DiceBuilding(new Rect(0, y - leftBottom, mBuildingBitmap.getWidth(), y - leftBottom + mBuildingBitmap.getHeight())));
        }

        int i = 0;
        while (i < diceBuildingList.size() - 1) {
            // cur
            DiceBuilding cur = diceBuildingList.get(i);
            x = (w - cur.getRect().width() - mBoardBitmap.getWidth()) / 2;
            y = cur.getRect().top + (cur.getRect().height() - mBoardBitmap.getHeight()) / 2;
            if (cur.getRect().left == 0) {
                x += cur.getRect().width();
            }
            DiceChess curChess = new DiceChess(new Rect(x, y, x + mBoardBitmap.getWidth(), y + mBoardBitmap.getHeight()));
            diceChessList.add(curChess);

            //next
            DiceBuilding next = diceBuildingList.get(i + 1);
            x = (w - next.getRect().width() - mBoardBitmap.getWidth()) / 2;
            y = next.getRect().top + (next.getRect().height() - mBoardBitmap.getHeight()) / 2;
            if (next.getRect().left == 0) {
                x += next.getRect().width();
            }
            DiceChess nextChess = new DiceChess(new Rect(x, y, x + mBoardBitmap.getWidth(), y + mBoardBitmap.getHeight()));

            // 中间
            int count = 2;
            int spaceW = (nextChess.getRect().left - curChess.getRect().left) / (count + 1);
            int spaceH = (nextChess.getRect().top - curChess.getRect().top) / (count + 1);
            for (int j = 0; j < count; j++) {
                x = curChess.getRect().left + (j + 1) * spaceW;
                y = curChess.getRect().top + (j + 1) * spaceH;
                diceChessList.add(new DiceChess(new Rect(x, y, x + mBoardBitmap.getWidth(), y + mBoardBitmap.getHeight())));
            }

            i++;
            if (i == diceBuildingList.size() - 1) {
                diceChessList.add(nextChess);
            }
        }

        dicePlayer.setChess(diceChessList.get(0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#c2bace"));

        int saveCount = canvas.save();

        for (DiceBuilding item : diceBuildingList) {
            canvas.drawBitmap(mBuildingBitmap, mBuildingRect, item.getRect(), mPaint);
        }

        for (DiceChess item : diceChessList) {
            canvas.drawBitmap(mBoardBitmap, mBoardRect, item.getRect(), mPaint);
        }

        DiceChess chess = dicePlayer.getChess();
        if (chess != null) {
            canvas.translate(dicePlayer.getTranslateX(), dicePlayer.getTranslateY());
            canvas.translate(chess.getRect().left + (chess.getRect().width() - mPlayerBitmap.getWidth()) / 2,
                    chess.getRect().top + (chess.getRect().height() - mPlayerBitmap.getHeight()) / 6);
            canvas.scale(dicePlayer.getScaleX(), dicePlayer.getScaleY(), mPlayerBitmap.getWidth() / 2, mPlayerBitmap.getHeight());
            canvas.drawBitmap(mPlayerBitmap, 0, 0, mPaint);
        }

        canvas.restoreToCount(saveCount);
    }


    private int dp2px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    private void go(final DicePlayer player, final List<DiceChess> items) {
        if (player.isRunning()) {
            return;
        }
        final DiceChess chessItem = player.getChess();
        final DiceChess nextChessItem = items.get((items.indexOf(chessItem) + 1) % items.size());
        if (chessItem == null || nextChessItem == null) {
            return;
        }

        ValueAnimator jumpUp = ValueAnimator.ofFloat(0, 0.5f);
        jumpUp.setDuration(250);
        jumpUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                float y0 = chessItem.getRect().top;
                float y1 = nextChessItem.getRect().top;
                float deltaY = y1 - y0 >= 0 ? dp2px(getContext(), 20) : Math.abs((y1 - y0)) + dp2px(getContext(), 20);
                float y;
                if (animatedValue < 0.5f) {
                    y = (float) (y0 - deltaY * Math.sin(Math.PI * animatedValue));
                } else {
                    y = (float) (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue));
                }
                player.setTranslateX((nextChessItem.getRect().left - chessItem.getRect().left) * animatedValue);
                player.setTranslateY(y - y0);
                invalidate();
            }
        });

        ValueAnimator jumpDown = ValueAnimator.ofFloat(0.5f, 1f);
        jumpDown.setDuration(250);
        jumpDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                float y0 = chessItem.getRect().top;
                float y1 = nextChessItem.getRect().top;
                float deltaY = y1 - y0 >= 0 ? dp2px(getContext(), 20) : Math.abs((y1 - y0)) + dp2px(getContext(), 20);
                float y;
                if (animatedValue < 0.5f) {
                    y = (float) (y0 - deltaY * Math.sin(Math.PI * animatedValue));
                } else {
                    y = (float) (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue));
                }
                player.setTranslateX((nextChessItem.getRect().left - chessItem.getRect().left) * animatedValue);
                player.setTranslateY(y - y0);
                invalidate();
            }
        });

        ObjectAnimator squash = ObjectAnimator.ofFloat(player, scaleYProperty, 0.6f).setDuration(100);
        ObjectAnimator stretch = ObjectAnimator.ofFloat(player, scaleYProperty, 1.2f).setDuration(100);
        ObjectAnimator scaleBack = ObjectAnimator.ofFloat(player, scaleYProperty, 1f).setDuration(100);

        AnimatorSet jumpUpSet = new AnimatorSet();
        jumpUpSet.playTogether(jumpUp, stretch);

        AnimatorSet jumpDownSet = new AnimatorSet();
        jumpDownSet.playTogether(jumpDown, scaleBack);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                player.setRunning(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 切换到下一位
                player.setChess(nextChessItem);
                player.setTranslateX(0);
                player.setTranslateY(0);
                player.setScaleX(1);
                player.setScaleY(1);
                invalidate();

                player.setRunning(false);

                // 触发事件
//                switch (nextChessItem.get) {
//                    case 1:
//                        Log.d("TAG", "触发钻石事件");
//                        break;
//                    case 2:
//                        Log.d("TAG", "触发PK事件");
//                        break;
//                    case 3:
//                        Log.d("TAG", "触发陷阱事件，回到起点");
//                        player.chessItem = items.get(0);
//                        Path path = new Path();
//
//                        break;
//                    case 4:
//                        Log.d("TAG", "触发碎片事件");
//                        break;
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                // 恢复原位
                player.setTranslateX(0);
                player.setTranslateY(0);
                player.setScaleX(1);
                player.setScaleY(1);
                invalidate();

                player.setRunning(false);
            }
        });
        animatorSet.playSequentially(squash, jumpUpSet, jumpDownSet);
        animatorSet.start();
    }

    final Property<DicePlayer, Float> scaleYProperty = new Property<DicePlayer, Float>(Float.class, "scaleY") {
        @Override
        public Float get(DicePlayer object) {
            return object.getScaleY();
        }

        @Override
        public void set(DicePlayer object, Float value) {
            object.setScaleY(value);
            invalidate();
        }
    };

    public void go() {
        go(dicePlayer, diceChessList);
    }

}
