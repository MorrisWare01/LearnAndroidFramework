package com.example.learnandroidframework.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.learnandroidframework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmw on 2020/9/22.
 */
public class ChessBoardView extends View {

    private int chessBoardWidth;
    private int chessBoardHeight;
    private float chessBoardRatio = 1.3f;

    private int boardMarginTop;

    private Bitmap chessBoardBitmap;
    private Bitmap boardBitmap;
    private Bitmap playerBitmap;

    private Paint mPaint;
    private Rect mBoardRect;

    private Rect tmpRect = new Rect();


    private List<ChessItem> items;
    private ChessPlayer player = new ChessPlayer();

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
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.jr);

        mBoardRect = new Rect(0, 0, boardBitmap.getWidth(), boardBitmap.getHeight());

        items = new ArrayList<>();
        int x = (chessBoardWidth - mBoardRect.width()) * 2 / 3;
        int y = chessBoardHeight - mBoardRect.height() - dp2px(context, 10);
        ChessItem chessItem = new ChessItem(new Rect(x, y, mBoardRect.width() + x, mBoardRect.height() + y));
        items.add(chessItem);

        x = (chessBoardWidth - mBoardRect.width()) / 2;
        y = chessBoardHeight - 2 * mBoardRect.height() - dp2px(context, 10);
        chessItem = new ChessItem(new Rect(x, y, mBoardRect.width() + x, mBoardRect.height() + y));
        items.get(items.size() - 1).next = chessItem;
        items.add(chessItem);

        x = (chessBoardWidth - mBoardRect.width()) / 3;
        y = chessBoardHeight - 3 * mBoardRect.height() - dp2px(context, 10);
        chessItem = new ChessItem(new Rect(x, y, mBoardRect.width() + x, mBoardRect.height() + y));
        items.get(items.size() - 1).next = chessItem;
        items.add(chessItem);
        chessItem.next = items.get(0);

        player.chessItem = items.get(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
//        int size = View.MeasureSpec.getSize(widthMeasureSpec);
//        heightMeasureSpec = View.MeasureSpec.getMode(heightMeasureSpec);
//        widthMeasureSpec = View.MeasureSpec.getSize(widthMeasureSpec);
//        if (mode == MeasureSpec.AT_MOST && heightMeasureSpec == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(this.chessBoardWidth, this.chessBoardHeight);
//            return;
//        }
//        if (mode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(this.chessBoardWidth, widthMeasureSpec);
//            return;
//        }
//        if (heightMeasureSpec == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(size, this.chessBoardHeight);
//            return;
//        }
        setMeasuredDimension(chessBoardWidth, (chessBoardHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawBackground(canvas);
        drawBoards(canvas);
        drawChessItems(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("TAG", "w:" + w + " h " + h);

    }

    private void drawBackground(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0F, this.boardMarginTop);
        Bitmap bitmap = this.chessBoardBitmap;
        int i = bitmap.getWidth();
        Rect rect = new Rect(0, 0, i, bitmap.getHeight());
        Context context = getContext();
        int left = -dp2px(context, 35);
        canvas.drawBitmap(bitmap, rect, new Rect(left, 0, chessBoardWidth + dp2px(context, 35), this.chessBoardHeight), mPaint);
        canvas.restore();
    }

    private void drawBoards(Canvas canvas) {
        for (ChessItem item : items) {
            canvas.drawBitmap(boardBitmap, mBoardRect, item.getPosition(), mPaint);
        }
    }

    private void drawChessItems(Canvas canvas) {
        final ChessItem currentPlayerChess = player.chessItem;
        if (currentPlayerChess == null) {
            return;
        }

        Rect position = currentPlayerChess.getPosition();

        canvas.save();
        canvas.translate(player.translateX, player.translateY);
        canvas.translate(position.left + (position.width() - playerBitmap.getWidth()) / 2, position.top + playerBitmap.getHeight() / 2);
        canvas.scale(player.scaleX, player.scaleY, playerBitmap.getWidth() / 2, playerBitmap.getHeight());
        canvas.drawBitmap(playerBitmap, 0, 0, mPaint);
        canvas.restore();
    }

    private int dp2px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public void go() {
        if (player.isRunning) {
            return;
        }
        final ChessItem chessItem = player.chessItem;
        if (chessItem.next == null) {
            player.chessItem = items.get(0);
            invalidate();
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();

                float y0 = chessItem.position.top;
                float y1 = chessItem.next.position.top;
                float y;
                if (y1 - y0 > 0) {
                    float deltaY = Math.abs((y1 - y0));
                    if (animatedValue < 0.5f) {
                        y = (float) (y0 + deltaY * Math.sin(Math.PI + Math.PI * animatedValue));
                    } else {
                        y = (float) (y1 + (y1 + deltaY - y0) * Math.sin(Math.PI + Math.PI * animatedValue));
                    }
                } else {
                    float deltaY = Math.abs(2 * (y1 - y0));
                    if (animatedValue < 0.5f) {
                        y = (float) (y0 - deltaY * Math.sin(Math.PI * animatedValue));
                    } else {
                        y = (float) (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue));
                    }
                }

                player.translateX = (chessItem.next.position.left - chessItem.position.left) * animatedValue;
                player.translateY = y - y0;
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                player.isRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                player.chessItem = player.chessItem.next;
                player.translateX = 0;
                player.translateY = 0;
                player.scaleX = 1;
                player.scaleY = 1;
                invalidate();

                player.isRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                player.isRunning = false;
            }
        });
        valueAnimator.start();
    }
}
