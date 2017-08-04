package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;

/**
 * @author TheJoker丶豪
 *         一个自定义的支持自定义添加背景图片的控件
 *         支持在背景图上 显示一个圆形的imageview
 * @date 创建时间:2017/5/31
 */

public class Hexagon extends ImageView {
    String text = "未付款";
    private Bitmap mAreaBitmap, logobitmap;
    private Paint paint;
    private Paint painttext;
    int textColor;
    float textSize;
    private Paint paintlogo;
    private Paint paintcircle;
    private int number = 0;
    private Paint txtPaint;

    public Hexagon(Context context) {
        this(context, null);
    }

    public Hexagon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hexagon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mArray = context.obtainStyledAttributes(attrs, R.styleable.Hexagonattrs);
        Drawable areaBackground = mArray.getDrawable(R.styleable.Hexagonattrs_HexagonBackground);
        if (null != areaBackground) {
            // 设置了背景
            if (areaBackground instanceof BitmapDrawable) {
                // 设置了一张图片
                mAreaBitmap = ((BitmapDrawable) areaBackground).getBitmap();
            }
        }
        Drawable logoBackground = mArray.getDrawable(R.styleable.Hexagonattrs_logoBackground);
        if (null != logoBackground) {
            // 设置了背景
            if (logoBackground instanceof BitmapDrawable) {
                // 设置了一张图片
                logobitmap = ((BitmapDrawable) logoBackground).getBitmap();
            }
        }
        text = mArray.getString(R.styleable.Hexagonattrs_titleHG);
        textColor = mArray.getColor(R.styleable.Hexagonattrs_titleColorHG, Color.parseColor("#FFFFFF"));
        textSize = mArray.getDimension(R.styleable.Hexagonattrs_titleSizeHG, 90f);
        init();
    }

    private void init() {
        painttext = new Paint();
        painttext.setColor(textColor);
        painttext.setTextSize(textSize);

        paintcircle = new Paint();
        paintcircle.setAntiAlias(true);
        paintcircle.setColor(Color.RED);

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(Color.WHITE);
        txtPaint.setTextSize(18);
        //  txtPaint.setStyle(Paint.Style.STROKE); //设置画笔为空心
        // txtPaint.setStrokeWidth(1); //设置线宽

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //绘制背景
        if (mAreaBitmap != null) {
            Rect rect1 = new Rect(0, 0, mAreaBitmap.getWidth(), mAreaBitmap.getHeight());
            Rect rect2 = new Rect(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(mAreaBitmap, rect1, rect2, null);
        }

        //绘制logo
        if (logobitmap != null) {
            Rect rect1 = new Rect(0, 0, logobitmap.getWidth(), logobitmap.getHeight());
            Rect rect2 = new Rect(getWidth() / 3, getHeight() / 10 * 3, getWidth() / 3 * 2, getHeight() / 10 * 6);
            canvas.drawBitmap(logobitmap, rect1, rect2, null);
        }


        //拿到字符串的宽度  X轴居中
        float stringWidth = painttext.measureText(text);
        float x = (getWidth() - stringWidth) / 2;
        canvas.drawText(text, x, (float) (getHeight() / 10 * 7.5), painttext);


        //绘制小圆点
        if (number != 0) {
            canvas.drawCircle((float) ((float) getWidth() / 10 * 7.3), (getHeight() / 10 * 3), 14, paintcircle);
        }


        //绘制小圆点中的文字
        if (number != 0) {
            canvas.drawText(number + "",
                    (float) (getWidth() / 10 * 7.3 - txtPaint.measureText(number + "") / 2),
                    getHeight() / 10 * 3 + (txtPaint.descent() - txtPaint.ascent()) / 2 - txtPaint.descent(),
                    txtPaint);
        }


    }

    public void setNumber(int number) {
        if (number > 99) {
            this.number = 99;
        } else {
            this.number = number;
        }
        invalidate();
    }
}
