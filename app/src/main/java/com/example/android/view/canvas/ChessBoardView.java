package com.example.android.view.canvas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mmw on 2020/9/22.
 */
public class ChessBoardView extends View {

    private int chessBoardWidth;
    private int chessBoardHeight;

    private int boardMarginTop;

    private Bitmap chessBoardBitmap;
    private Bitmap boardBitmap;
    private Bitmap playerBitmap;
    private Bitmap testPlayerBitmap;
    private Bitmap startBitmap;
    private Bitmap trapBitmap;
    private Bitmap fightBitmap;
    private Bitmap reward1Bitmap;
    private Bitmap reward2Bitmap;
    private Bitmap reward3Bitmap;
    private Bitmap reward4Bitmap;

    private Bitmap cubeBitmap;
    private Rect mCubeRect;
    private List<ChessItem> cubeItems = new ArrayList<>();
    private List<ChessItem> sortedCubeItems = new ArrayList<>();
    private boolean isCubeSceneLoading;

    private Paint mPaint;
    private Rect mBoardRect;

    private List<ChessItem> testItems = new ArrayList<>();
    private ChessPlayer testPlayer = new ChessPlayer();

    private List<ChessItem> items = new ArrayList<>();
    private ChessPlayer player = new ChessPlayer();
    private boolean isSceneLoading;

    public ChessBoardView(Context context) {
        this(context, null);
    }

    public ChessBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChessBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        chessBoardWidth = context.getResources().getDisplayMetrics().widthPixels;
        chessBoardHeight = (int) (context.getResources().getDisplayMetrics().heightPixels * 1.2f);
        boardMarginTop = dp2px(context, 75);

        chessBoardBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.l1);
        boardBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.l0);
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.player);
        testPlayerBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.jr);
        startBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.start);
        trapBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.trap);
        fightBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.fight);
        reward1Bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.reward_1);
        reward2Bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.reward_2);
        reward3Bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.reward_3);
        reward4Bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.reward_4);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.cube);
        cubeBitmap = Bitmap.createScaledBitmap(bitmap, dp2px(getContext(), 80), dp2px(getContext(), 80), false);
        bitmap.recycle();

        mCubeRect = new Rect(0, 0, cubeBitmap.getWidth(), cubeBitmap.getHeight());

        mBoardRect = new Rect(0, 0, boardBitmap.getWidth(), boardBitmap.getHeight());

        // testItems
        int x = (chessBoardWidth - mBoardRect.width()) * 2 / 3;
        int y = chessBoardHeight - mBoardRect.height() - dp2px(context, 10);
        ChessItem chessItem = new ChessItem(new Rect(x, y, mBoardRect.width() + x, mBoardRect.height() + y));
        testItems.add(chessItem);

        x = (chessBoardWidth - mBoardRect.width()) / 2;
        y = chessBoardHeight - 2 * mBoardRect.height() - dp2px(context, 10);
        chessItem = new ChessItem(new Rect(x, y, mBoardRect.width() + x, mBoardRect.height() + y));
        testItems.add(chessItem);

        x = (chessBoardWidth - mBoardRect.width()) / 3;
        y = chessBoardHeight - 3 * mBoardRect.height() - dp2px(context, 10);
        chessItem = new ChessItem(new Rect(x, y, mBoardRect.width() + x, mBoardRect.height() + y));
        testItems.add(chessItem);
        testPlayer.chessItem = testItems.get(0);

        // items
        x = 0;
        y = 0;
        items.clear();
        for (int i = 0; i < 4; i++) {
            x = x + mBoardRect.width();
            items.add(new ChessItem(new Rect(x, y, x + mBoardRect.width(), y + mBoardRect.height())));
        }
        for (int j = 0; j < 3; j++) {
            y = y + mBoardRect.height();
            items.add(new ChessItem(new Rect(x, y, x + mBoardRect.width(), y + mBoardRect.height())));
        }
        for (int i = 0; i < 4; i++) {
            x = x - mBoardRect.width();
            items.add(new ChessItem(new Rect(x, y, x + mBoardRect.width(), y + mBoardRect.height())));
        }
        for (int j = 0; j < 3; j++) {
            y = y - mBoardRect.height();
            items.add(new ChessItem(new Rect(x, y, x + mBoardRect.width(), y + mBoardRect.height())));
        }

        // cubeItems
        x = 0;
        y = 0;
        int zIndex = 100;
        cubeItems.clear();
        for (int i = 0; i < 12; i++) {
            int a = i / 3;
            if (a == 0) {
                x = x - mCubeRect.width() / 2;
                y = y - mCubeRect.height() / 4;
                zIndex--;
            } else if (a == 1) {
                x = x + mCubeRect.width() / 2;
                y = y - mCubeRect.height() / 4;
                zIndex--;
            } else if (a == 2) {
                x = x + mCubeRect.width() / 2;
                y = y + mCubeRect.height() / 4;
                zIndex++;
            } else {
                x = x - mCubeRect.width() / 2;
                y = y + mCubeRect.height() / 4;
                zIndex++;
            }
            ChessItem item = new ChessItem(new Rect(x, y, x + mCubeRect.width(), y + mCubeRect.height()));
            item.zIndex = zIndex;

            cubeItems.add(item);
        }

        sortedCubeItems.addAll(cubeItems);
        Collections.sort(sortedCubeItems);

        loadScene();
        loadCubeScene();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(chessBoardWidth, chessBoardHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGame(canvas);
        drawTest(canvas);
        drawCube(canvas);
    }

    private void drawCube(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2 - mCubeRect.width() / 2, dp2px(getContext(), 600));

//        int x = 0;
//        int y = -firstCube.height / 2;
        for (ChessItem item : sortedCubeItems) {
            int alpha = mPaint.getAlpha();
            mPaint.setAlpha((int) (item.alpha * 255));
            canvas.drawBitmap(cubeBitmap, mCubeRect, item.getPosition(), mPaint);
            mPaint.setAlpha(alpha);
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("TAG", "w:" + w + " h " + h);

    }

    private void drawGame(Canvas canvas) {
        canvas.save();
        canvas.translate((chessBoardWidth - 5 * mBoardRect.width()) / 2, dp2px(getContext(), 200));

        // 绘制棋盘
        for (ChessItem item : items) {
            canvas.save();
            canvas.translate(0, item.translateY);
            // 绘制棋盘
            switch (item.type) {
                case 0:
                    canvas.drawBitmap(startBitmap, mBoardRect, item.getPosition(), mPaint);
                    break;
                case 2:
                    canvas.drawBitmap(fightBitmap, mBoardRect, item.getPosition(), mPaint);
                    break;
                case 3:
                    canvas.drawBitmap(trapBitmap, mBoardRect, item.getPosition(), mPaint);
                    break;
                default:
                    canvas.drawBitmap(boardBitmap, mBoardRect, item.getPosition(), mPaint);
                    break;
            }
            canvas.restore();
        }

        // 场景加载
        if (isSceneLoading) {
            canvas.restore();
            return;
        }

        // 绘制道具
        for (ChessItem item : items) {
            switch (item.type) {
                case 1:
                    canvas.save();
                    canvas.translate((mBoardRect.width() - reward1Bitmap.getWidth()) / 2, -(reward1Bitmap.getHeight()) / 2.5f);
                    canvas.drawBitmap(reward1Bitmap, item.getPosition().left, item.position.top, mPaint);
                    canvas.restore();
                    break;
                case 2:
                    canvas.save();
                    canvas.translate((mBoardRect.width() - reward2Bitmap.getWidth()) / 2, -(reward2Bitmap.getHeight()) / 2.5f);
                    canvas.drawBitmap(reward2Bitmap, item.getPosition().left, item.position.top, mPaint);
                    canvas.restore();
                    break;
                case 3:
                    canvas.save();
                    canvas.translate((mBoardRect.width() - reward3Bitmap.getWidth()) / 2, -(reward3Bitmap.getHeight()) / 2f);
                    canvas.drawBitmap(reward3Bitmap, item.getPosition().left, item.position.top, mPaint);
                    canvas.restore();
                    break;
                case 4:
                    canvas.save();
                    canvas.translate((mBoardRect.width() - reward4Bitmap.getWidth()) / 2, -(reward4Bitmap.getHeight()) / 2.5f);
                    canvas.drawBitmap(reward4Bitmap, item.getPosition().left, item.position.top, mPaint);
                    canvas.restore();
                    break;
            }
        }

        // 绘制玩家
        Rect position = player.chessItem.getPosition();
        canvas.translate(player.translateX, player.translateY);
        canvas.translate(position.left + (position.width() - playerBitmap.getWidth()) / 2, position.top + (position.height() - playerBitmap.getHeight()) / 6);
        canvas.scale(player.scaleX, player.scaleY, playerBitmap.getWidth() / 2, playerBitmap.getHeight());
        canvas.drawBitmap(playerBitmap, 0, 0, mPaint);

        canvas.restore();
    }

    private void drawTest(Canvas canvas) {
        drawTestBoards(canvas);
        drawTestChessItems(canvas);
    }

    private void drawTestBoards(Canvas canvas) {
        for (ChessItem item : testItems) {
            canvas.drawBitmap(boardBitmap, mBoardRect, item.getPosition(), mPaint);
        }
    }

    private void drawTestChessItems(Canvas canvas) {
        final ChessItem currentPlayerChess = testPlayer.chessItem;
        if (currentPlayerChess == null) {
            return;
        }

        Rect position = currentPlayerChess.getPosition();

        canvas.save();
        canvas.translate(testPlayer.translateX, testPlayer.translateY);
        canvas.translate(position.left + (position.width() - testPlayerBitmap.getWidth()) / 2, position.top + testPlayerBitmap.getHeight() / 2);
        canvas.scale(testPlayer.scaleX, testPlayer.scaleY, testPlayerBitmap.getWidth() / 2, testPlayerBitmap.getHeight());
        canvas.drawBitmap(testPlayerBitmap, 0, 0, mPaint);
        canvas.restore();
    }

    private int dp2px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public void goTest() {
        go(testPlayer, testItems);
    }

    public void gameGo() {
        go(player, items);
    }

    public void loadScene() {
        if (isSceneLoading) {
            return;
        }
        isSceneLoading = true;
        // 模拟位置
        for (final ChessItem item : items) {
            item.maxTranslateY = (float) (-dp2px(getContext(), 200) - Math.random() * dp2px(getContext(), 200));
        }

        // 模拟类型
        for (ChessItem item : items) {
            item.type = (int) (Math.random() * 10) + 1;
        }
        items.get(0).type = 0;

        // 重置玩家位置
        player.chessItem = items.get(0);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0f).setDuration(800);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                for (final ChessItem item : items) {
                    item.translateY = item.maxTranslateY * animatedValue;
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isSceneLoading = false;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isSceneLoading = false;
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private void go(final ChessPlayer player, final List<ChessItem> items) {
        if (player.isRunning) {
            return;
        }
        final ChessItem chessItem = player.chessItem;
        final ChessItem nextChessItem = items.get((items.indexOf(chessItem) + 1) % items.size());

        ValueAnimator jumpUp = ValueAnimator.ofFloat(0, 0.5f);
        jumpUp.setDuration(250);
        jumpUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                float y0 = chessItem.position.top;
                float y1 = nextChessItem.position.top;
                float deltaY = y1 - y0 >= 0 ? dp2px(getContext(), 20) : Math.abs((y1 - y0)) + dp2px(getContext(), 20);
                float y;
                if (animatedValue < 0.5f) {
                    y = (float) (y0 - deltaY * Math.sin(Math.PI * animatedValue));
                } else {
                    y = (float) (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue));
                }
                player.translateX = (nextChessItem.position.left - chessItem.position.left) * animatedValue;
                player.translateY = y - y0;
                invalidate();
            }
        });

        ValueAnimator jumpDown = ValueAnimator.ofFloat(0.5f, 1f);
        jumpDown.setDuration(250);
        jumpDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                float y0 = chessItem.position.top;
                float y1 = nextChessItem.position.top;
                float deltaY = y1 - y0 >= 0 ? dp2px(getContext(), 20) : Math.abs((y1 - y0)) + dp2px(getContext(), 20);
                float y;
                if (animatedValue < 0.5f) {
                    y = (float) (y0 - deltaY * Math.sin(Math.PI * animatedValue));
                } else {
                    y = (float) (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue));
                }
                player.translateX = (nextChessItem.position.left - chessItem.position.left) * animatedValue;
                player.translateY = y - y0;
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
                player.isRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 切换到下一位
                player.chessItem = nextChessItem;
                player.translateX = 0;
                player.translateY = 0;
                player.scaleX = 1;
                player.scaleY = 1;
                invalidate();

                player.isRunning = false;

                // 触发事件
                switch (nextChessItem.type) {
                    case 1:
                        Log.d("TAG", "触发钻石事件");
                        break;
                    case 2:
                        Log.d("TAG", "触发PK事件");
                        break;
                    case 3:
                        Log.d("TAG", "触发陷阱事件，回到起点");
                        player.chessItem = items.get(0);
                        Path path = new Path();

                        break;
                    case 4:
                        Log.d("TAG", "触发碎片事件");
                        break;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                // 恢复原位
                player.translateX = 0;
                player.translateY = 0;
                player.scaleX = 1;
                player.scaleY = 1;
                invalidate();

                player.isRunning = false;
            }
        });
        animatorSet.playSequentially(squash, jumpUpSet, jumpDownSet);
        animatorSet.start();
    }

    final Property<ChessPlayer, Float> scaleYProperty = new Property<ChessPlayer, Float>(Float.class, "scaleY") {
        @Override
        public Float get(ChessPlayer object) {
            return object.scaleY;
        }

        @Override
        public void set(ChessPlayer object, Float value) {
            object.scaleY = value;
            invalidate();
        }
    };

    public void loadCubeScene() {
        if (isCubeSceneLoading) {
            return;
        }
        isCubeSceneLoading = true;

        for (ChessItem chessItem : cubeItems) {
            chessItem.alpha = 0;
        }
        fadeIn(cubeItems, 0);
    }

    private void fadeIn(final List<ChessItem> items, final int position) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                items.get(position).alpha = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (position + 1 >= items.size()) {
                    isCubeSceneLoading = false;
                    return;
                }
                fadeIn(items, position + 1);
            }
        });
        valueAnimator.start();
    }
}
