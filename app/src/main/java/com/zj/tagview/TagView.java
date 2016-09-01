package com.zj.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zj on 2016/8/31.
 */
public class TagView extends View {
    private Paint mTagPaint;
    private Paint mTextPaint;
    private Paint mLinePaint;

    private String text;
    private int tagWidth;
    private float cornerDistance;
    private float tagTextSize;
    private int tagTextColor;
    private int tagColor;
    private int tagPosition;

    private PointF pointStart = new PointF();
    private PointF pointEnd = new PointF();
    private Path path = new Path();


    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, 0);
        text = typedArray.getString(R.styleable.TagView_tagText);
        tagWidth = typedArray.getDimensionPixelSize(R.styleable.TagView_tagWidth, dip2px(30));
        cornerDistance = typedArray.getDimensionPixelSize(R.styleable.TagView_cornerDistance, dip2px(50));
        tagTextSize = typedArray.getDimensionPixelSize(R.styleable.TagView_tagTextSize, dip2px(14));
        tagTextColor = typedArray.getColor(R.styleable.TagView_tagTextColor, Color.WHITE);
        tagColor = typedArray.getColor(R.styleable.TagView_tagColor, 0x9F27CDC0);
        tagPosition = typedArray.getInt(R.styleable.TagView_tagPosition, 0);
        typedArray.recycle();

        mTagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTagPaint.setColor(tagColor);
        mTagPaint.setDither(true);
        mTagPaint.setStyle(Paint.Style.STROKE);

        /**
         * 该方法用来设置我们画笔的 笔触风格 ，比如：ROUND，表示是圆角的笔触。那么什么叫笔触呢，
         * 其实很简单，就像我们现实世界中的笔，如果你用圆珠笔在纸上戳一点，那么这个点一定是个圆，
         * 即便很小，它代表了笔的笔触形状，如果我们把一支铅笔笔尖削成方形的，
         * 那么画出来的线条会是一条弯曲的“矩形”，这就是笔触的意思。除了ROUND，
         * Paint.Cap还提供了另外两种类型：SQUARE和BUTT
         */
        mTagPaint.setStrokeCap(Paint.Cap.ROUND);

        /**
         * ROUND:让画的线圆滑
         * 设置结合处的样子，Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
         *
         * 这个方法用于设置接合处的形态，就像你用代码画了一条线，
         * 但是这条线其实是由无数条小线拼接成的，拼接处的形状就由该方法指定。可选参数是：BEVEL，MITER，ROUND。
         */
        mTagPaint.setStrokeJoin(Paint.Join.MITER);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(tagTextSize);
        mTextPaint.setColor(tagTextColor);
        mTextPaint.setAntiAlias(true);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setStyle(Paint.Style.STROKE);

        /**
         * 当画笔样式（style）为STROKE或FILL_OR_STROKE时(空心样式时)，设置笔刷的粗细度。
         */
        mTagPaint.setStrokeWidth(tagWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int parentWidth = ((View) getParent()).getMeasuredWidth();
        int parentHeight = ((View) getParent()).getMeasuredHeight();

        System.out.println("parentWidth=" + parentWidth);
        System.out.println("parentHeight=" + parentHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        float tagDistance = (float) (cornerDistance + tagWidth / 2 * Math.sqrt(2));
        switch (tagPosition) {
            case 0:
                pointStart.x = 0;
                pointStart.y = tagDistance;
                pointEnd.x = tagDistance;
                pointEnd.y = 0;

                canvas.drawLine(tagDistance, 0, tagDistance, getMeasuredHeight(), mLinePaint);
                canvas.drawLine(0, tagDistance, getMeasuredWidth(), tagDistance, mLinePaint);

                canvas.drawLine(0, tagDistance / 2, getMeasuredWidth(), tagDistance / 2, mLinePaint);
                canvas.drawLine(tagDistance / 2, 0, tagDistance / 2, getMeasuredHeight(), mLinePaint);
                break;
            case 1:
                pointStart.x = getMeasuredWidth() - tagDistance;
                pointStart.y = 0;
                pointEnd.x = getMeasuredWidth();
                pointEnd.y = tagDistance;
                break;
            case 2:
                pointStart.x = getMeasuredWidth() - tagDistance;
                pointStart.y = getMeasuredHeight();
                pointEnd.x = getMeasuredWidth();
                pointEnd.y = getMeasuredHeight() - tagDistance;
                break;
            case 3:
                pointStart.x = 0;
                pointStart.y = getMeasuredHeight() - tagDistance;
                pointEnd.x = tagDistance;
                pointEnd.y = getMeasuredHeight();
                break;
        }
        path.reset();
        path.moveTo(pointStart.x, pointStart.y);
        path.lineTo(pointEnd.x, pointEnd.y);
        //path.close();
        canvas.drawPath(path, mTagPaint);

        Rect textBounds = new Rect();

        mTextPaint.getTextBounds(text, 0, text.length() - 1, textBounds);

        float pathWidth = (float) (tagDistance * Math.sqrt(2));

        canvas.drawTextOnPath(text, path,
                pathWidth / 2 - (float) Math.sqrt(2) * textBounds.width() / 2,
                (float) (Math.sqrt((textBounds.height() * textBounds.height() / 2))) / 2,
                mTextPaint);

        canvas.drawPath(path, mLinePaint);
    }

    private int dip2px(int dip) {
        return (int)(getContext().getResources().getDisplayMetrics().density * dip + 0.5f);
    }

    public void setTagWidth(int tagWidth){
        this.tagWidth = dip2px(tagWidth);
        mTagPaint.setStrokeWidth(this.tagWidth);
        invalidate();
    }

    public void setCornerDistance(int cornerDistance){
        this.cornerDistance = dip2px(cornerDistance);
        invalidate();
    }

    public void setTagTextSize(int textSize) {
        this.tagTextSize = dip2px(textSize);
        mTextPaint.setTextSize(tagTextSize);
        invalidate();
    }

    public void setTagPosition(int position){
        this.tagPosition = position;
        invalidate();
    }

    public void setText(int resId){
        String text = getContext().getString(resId);
        this.text = text;
        invalidate();
    }

    public void setTagColor(int color){
        tagColor = color;
        mTagPaint.setColor(tagColor);
        invalidate();
    }
}
