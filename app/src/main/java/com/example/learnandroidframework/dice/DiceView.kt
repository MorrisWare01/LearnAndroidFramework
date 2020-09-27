package com.example.learnandroidframework.dice

import android.animation.*
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ScrollView
import com.example.learnandroidframework.R
import java.util.*

/**
 * Created by mmw on 2020/9/25.
 */
class DiceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    
    // 建筑物
    private val mBuildingBitmap: Bitmap
    private val mBuildingRect: Rect

    // 玩家
    private val mPlayerBitmap: Bitmap
    private val mPlayerAvatarBitmap: Bitmap

    // 棋盘
    private val mBoardBitmap: Bitmap
    private val mBoardCheckBitmap: Bitmap
    private val mBoardRect: Rect
    private val mBoardRatio: Float = 82.0f / (82 + 95)

    // 道具
    private val mRedBagBitmap: Bitmap =
        BitmapFactory.decodeResource(resources, R.mipmap.dice_red_bag)
    private val mCoinBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_coin)
    private val mTrapBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_trap)
    private val mEventBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_event)

    //
    private val mPaint = Paint()
    private val mTextPaint = TextPaint().apply {
        textSize = sp2px(context, 16).toFloat()
        color = Color.WHITE
    }

    private val diceBuildingList: MutableList<DiceBuilding> = ArrayList()
    private val diceChessList: MutableList<DiceChess> = ArrayList()
    private val dicePlayer = DicePlayer()

    init {
        val widthPixels = context.resources.displayMetrics.widthPixels
        val tmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(tmp)
        canvas.drawColor(Color.parseColor("#b38d74"))
        mBuildingBitmap =
            Bitmap.createScaledBitmap(tmp, dp2px(context, 156), dp2px(context, 200), false)
        mBuildingRect = Rect(0, 0, mBuildingBitmap.width, mBuildingBitmap.height)
        tmp.recycle()

        mBoardBitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_board)
        mBoardCheckBitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_board_checked)
        mBoardRect = Rect(0, 0, mBoardBitmap.width, mBoardBitmap.height)

        mPlayerBitmap = BitmapFactory.decodeResource(resources, R.mipmap.dice_player)

        val avatar =
            Bitmap.createBitmap(dp2px(context, 30), dp2px(context, 30), Bitmap.Config.ARGB_8888)
        Canvas(avatar).drawOval(
            RectF(0f, 0f, dp2px(context, 30).toFloat(), dp2px(context, 30).toFloat()),
            mPaint
        )
        mPlayerAvatarBitmap = avatar
    }

    private fun dp2px(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.density * dp + 0.5f).toInt()
    }

    private fun sp2px(context: Context, sp: Int): Int {
        return (context.resources.displayMetrics.scaledDensity * sp + 0.5f).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthPixels = context.resources.displayMetrics.widthPixels
        val heightPixels = context.resources.displayMetrics.heightPixels
        setMeasuredDimension(widthPixels, (heightPixels * 2f).toInt())
        onSizeChanged(measuredWidth, measuredHeight, 0, 0)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        var x = 0
        var y = 0
        val magrin = mBoardBitmap.height
        val leftBottom = mBuildingBitmap.height / 2 + mBoardBitmap.height / 2
        diceBuildingList.clear()
        for (i in 0..4) {
            x = w - mBuildingBitmap.width
            y = h - (i + 1) * mBuildingBitmap.height - i * magrin
            diceBuildingList.add(
                DiceBuilding(
                    Rect(
                        x,
                        y,
                        x + mBuildingBitmap.width,
                        y + mBuildingBitmap.height
                    )
                )
            )
            diceBuildingList.add(
                DiceBuilding(
                    Rect(
                        0,
                        y - leftBottom,
                        mBuildingBitmap.width,
                        y - leftBottom + mBuildingBitmap.height
                    )
                )
            )
        }
        var i = 0
        diceChessList.clear()
        while (i < diceBuildingList.size - 1) {
            // cur
            val buildingRect = diceBuildingList[i].rect
            val freeSpace = w - buildingRect.width() - mBoardBitmap.width
            x = if (buildingRect.left == 0) {
                (buildingRect.width() + freeSpace * (1 - mBoardRatio)).toInt()
            } else {
                (freeSpace * mBoardRatio).toInt();
            }
            y = buildingRect.top + buildingRect.height() / 2 - mBoardBitmap.height / 2
            val curChess = DiceChess(Rect(x, y, x + mBoardBitmap.width, y + mBoardBitmap.height))
            diceChessList.add(curChess)

            //next
            val nextBuildingRect = diceBuildingList[i + 1].rect
            x = if (nextBuildingRect.left == 0) {
                (nextBuildingRect.width() + freeSpace * (1 - mBoardRatio)).toInt()
            } else {
                (freeSpace * mBoardRatio).toInt();
            }
            y = nextBuildingRect.top + nextBuildingRect.height() / 2 - mBoardBitmap.height / 2
            val nextChess = DiceChess(Rect(x, y, x + mBoardBitmap.width, y + mBoardBitmap.height))

            // 中间
            val count = 2
            val spaceW = (nextChess.rect.left - curChess.rect.left) / (count + 1)
            val spaceH = (nextChess.rect.top - curChess.rect.top) / (count + 1)
            for (j in 0 until count) {
                x = curChess.rect.left + (j + 1) * spaceW
                y = curChess.rect.top + (j + 1) * spaceH
                diceChessList.add(
                    DiceChess(
                        Rect(
                            x,
                            y,
                            x + mBoardBitmap.width,
                            y + mBoardBitmap.height
                        )
                    )
                )
            }
            i++
            if (i == diceBuildingList.size - 1) {
                diceChessList.add(nextChess)
            }
        }

        for ((index, item) in diceChessList.withIndex()) {
            // 模拟类型
            item.type = when ((Math.random() * 10).toInt() + 1) {
                1 -> DiceChess.TYPE_RED_BAG
                2 -> DiceChess.TYPE_COIN
                3 -> DiceChess.TYPE_TRAP
                4 -> DiceChess.TYPE_EVENT
                else -> null
            }
        }

        dicePlayer.chess = diceChessList[0]
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制背景
        canvas.drawColor(Color.parseColor("#c2bace"))

        // 绘制建筑
        for ((rect) in diceBuildingList) {
            canvas.drawBitmap(mBuildingBitmap, mBuildingRect, rect, mPaint)
        }

        for ((index, item) in diceChessList.withIndex()) {
            // 绘制棋盘
            val chess = dicePlayer.chess
            if (chess != null && index <= diceChessList.indexOf(chess)) {
                canvas.drawBitmap(mBoardCheckBitmap, mBoardRect, item.rect, mPaint)
            } else {
                canvas.drawBitmap(mBoardBitmap, mBoardRect, item.rect, mPaint)
            }

            // 绘制序号
            val number = (index + 1).toString()
            val numberWidth = mTextPaint.measureText(number)
            canvas.save()
            canvas.translate(
                item.rect.left.toFloat() + (item.rect.width() - numberWidth) / 2.toFloat(),
                item.rect.top.toFloat() + (mBoardBitmap.height / 2).toFloat()
            )
            canvas.drawText(number, 0f, 0f, mTextPaint)
            canvas.restore()

            // 绘制道具
            when (item.type) {
                DiceChess.TYPE_RED_BAG -> {
                    canvas.save()
                    canvas.translate(
                        item.rect.left.toFloat() + (mBoardRect.width() - mRedBagBitmap.width) / 2.toFloat(),
                        item.rect.top.toFloat() - mPlayerBitmap.height * 0.65f
                    )
                    canvas.drawBitmap(mRedBagBitmap, 0f, 0f, mPaint)
                    canvas.restore()
                }
                DiceChess.TYPE_COIN -> {
                    canvas.save()
                    canvas.translate(
                        item.rect.left.toFloat() + (mBoardRect.width() - mCoinBitmap.width) / 2.toFloat(),
                        item.rect.top.toFloat() - mPlayerBitmap.height * 0.68f
                    )
                    canvas.drawBitmap(mCoinBitmap, 0f, 0f, mPaint)
                    canvas.restore()
                }
                DiceChess.TYPE_TRAP -> {
                    canvas.save()
                    canvas.translate(
                        item.rect.left.toFloat() + (mBoardRect.width() - mTrapBitmap.width) / 2.toFloat(),
                        item.rect.top.toFloat() - mPlayerBitmap.height * 0.58f
                    )
                    canvas.drawBitmap(mTrapBitmap, 0f, 0f, mPaint)
                    canvas.restore()
                }
                DiceChess.TYPE_EVENT -> {
                    canvas.save()
                    canvas.translate(
                        item.rect.left.toFloat() + (mBoardRect.width() - mEventBitmap.width) / 2.toFloat(),
                        item.rect.top.toFloat() - mPlayerBitmap.height * 0.65f
                    )
                    canvas.drawBitmap(mEventBitmap, 0f, 0f, mPaint)
                    canvas.restore()
                }
            }
        }

        // 绘制角色
        val chess = dicePlayer.chess
        if (chess != null) {
            canvas.save()
            canvas.translate(
                chess.rect.left.toFloat() + (chess.rect.width() - mPlayerBitmap.width) / 2.toFloat(),
                chess.rect.top.toFloat() - mPlayerBitmap.height * 0.8f
            )
            canvas.translate(dicePlayer.translateX, dicePlayer.translateY)
            canvas.scale(
                dicePlayer.scaleX,
                dicePlayer.scaleY,
                mPlayerBitmap.width / 2.toFloat(),
                mPlayerBitmap.height.toFloat()
            )

            updatePlayerPosition((chess.rect.top.toFloat() - mPlayerBitmap.height * 0.8f).toInt())

            // 绘制角色背景
            canvas.drawBitmap(mPlayerBitmap, 0f, 0f, mPaint)
            // 绘制角色头像
            canvas.drawBitmap(
                mPlayerAvatarBitmap,
                (mPlayerBitmap.width - mPlayerAvatarBitmap.width) / 2.toFloat(),
                dp2px(context, 8).toFloat(),
                mPaint
            )

            canvas.restore()
        }
    }

    private fun go(player: DicePlayer, items: List<DiceChess>) {
        val chessItem = player.chess ?: return

        if (player.deferredStepCount == 0) {
            player.isRunning = false
            player.reset()
            return
        }
        // 步进单位
        val stepIn = if (dicePlayer.deferredStepCount > 0) 1 else -1

        // 路径
        val nextChessItem = items[(items.indexOf(chessItem) + stepIn) % items.size]
        val jumpUp = ValueAnimator.ofFloat(0f, 0.5f)
        jumpUp.duration = 250
        jumpUp.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            val y0 = chessItem.rect.top.toFloat()
            val y1 = nextChessItem.rect.top.toFloat()
            val deltaY =
                if (y1 - y0 >= 0) dp2px(context, 20).toFloat() else Math.abs(y1 - y0) + dp2px(
                    context, 20
                )
            val y: Float
            y = if (animatedValue < 0.5f) {
                (y0 - deltaY * Math.sin(Math.PI * animatedValue)).toFloat()
            } else {
                (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue)).toFloat()
            }
            player.translateX = (nextChessItem.rect.left - chessItem.rect.left) * animatedValue
            player.translateY = y - y0
            invalidate()
        }
        val jumpDown = ValueAnimator.ofFloat(0.5f, 1f)
        jumpDown.duration = 250
        jumpDown.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            val y0 = chessItem.rect.top.toFloat()
            val y1 = nextChessItem.rect.top.toFloat()
            val deltaY =
                if (y1 - y0 >= 0) dp2px(context, 20).toFloat() else Math.abs(y1 - y0) + dp2px(
                    context, 20
                )
            val y: Float
            y = if (animatedValue < 0.5f) {
                (y0 - deltaY * Math.sin(Math.PI * animatedValue)).toFloat()
            } else {
                (y1 - (y1 + deltaY - y0) * Math.sin(Math.PI * animatedValue)).toFloat()
            }
            player.translateX = (nextChessItem.rect.left - chessItem.rect.left) * animatedValue
            player.translateY = y - y0
            invalidate()
        }
        val squash = ObjectAnimator.ofFloat(player, scaleYProperty, 0.6f).setDuration(100)
        val stretch = ObjectAnimator.ofFloat(player, scaleYProperty, 1.2f).setDuration(100)
        val scaleBack = ObjectAnimator.ofFloat(player, scaleYProperty, 1f).setDuration(100)
        val jumpUpSet = AnimatorSet()
        jumpUpSet.playTogether(jumpUp, stretch)
        val jumpDownSet = AnimatorSet()
        jumpDownSet.playTogether(jumpDown, scaleBack)
        val animatorSet = AnimatorSet()
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                player.isRunning = true
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                // 切换到下一位
                player.chess = nextChessItem
                player.reset()
                invalidate()

                // 下一步
                player.deferredStepCount -= stepIn
                if (player.deferredStepCount != 0) {
                    go(player, diceChessList)
                } else {
                    player.isRunning = false

                    // 触发道具
                    nextChessItem.type = null
                    invalidate()
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
            }

            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
                player.isRunning = false
                player.reset()
                invalidate()
            }
        })
        animatorSet.playSequentially(squash, jumpUpSet, jumpDownSet)
        animatorSet.start()
    }

    private val scaleYProperty: Property<DicePlayer, Float> = object : Property<DicePlayer, Float>(
        Float::class.java, "scaleY"
    ) {
        override fun get(`object`: DicePlayer): Float {
            return `object`.scaleY
        }

        override fun set(`object`: DicePlayer, value: Float) {
            `object`.scaleY = value
            invalidate()
        }
    }

    private fun updatePlayerPosition(top: Int) {
        val scrollView = parent
        if (scrollView is ScrollView) {
            scrollView.scrollTo(0, top - scrollView.height / 2)
        }
    }

    fun go(steps: Int) {
        if (dicePlayer.isRunning) {
            return
        }
        dicePlayer.deferredStepCount += steps
        go(dicePlayer, diceChessList)
    }

    fun isPlayerRunning(): Boolean {
        return dicePlayer.isRunning
    }

}