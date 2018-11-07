package com.drying.ppe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/11/7 15:10
 * <p/>
 * Description:仿微信  6位密码输入框
 */
public class PaymentPwdEditText extends AppCompatEditText {
    /** 默认边距 */
    private int pad         = 5;
    /** 画笔宽度 */
    private int paintWidth  = 2;
    /** 最多输入字数 */
    private int maxItemSize = 6;
    /** 记录已经输入的字数 */
    private int textSize    = 0;
    /** 输入的圆圈半径 */
    private int checkdR     = 10;
    /** 画笔 */
    private Paint         paint;
    /** 输入完成监听事件 */
    private InputEndClick inputEndClick;

    public PaymentPwdEditText(Context context) {
        this(context, null);
    }

    public PaymentPwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentPwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setSingleLine();
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxItemSize)});
        setFocusable(true);
        setFocusableInTouchMode(true);
        setLongClickable(false);
        setCursorVisible(false);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(paintWidth);
    }

    public void setInputEndClick(InputEndClick inputEndClick) {
        this.inputEndClick = inputEndClick;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //        super.onDraw(canvas);
        int width  = getMeasuredWidth();
        int height = getMeasuredHeight();

        paint.setStyle(Paint.Style.STROKE);
        //画边框
        RectF rect = new RectF(0 + pad, 0 + pad, width - pad, height - pad);
        canvas.drawRoundRect(rect, 10, 10, paint);
        int itemWidth = (width - 2 * pad - 2 * paintWidth - (maxItemSize - 1) * paintWidth) / maxItemSize;
        //画分割线
        for (int i = 1; i < maxItemSize; i++) {
            float startX = pad + i * (paintWidth + itemWidth);
            float startY = pad;
            float endX   = startX;
            float endY   = height - pad;
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        //画输入圆点
        for (int i = 1; i <= textSize; i++) {
            float x = pad + i * paintWidth + (i - 1) * itemWidth + itemWidth / 2;
            float y = height / 2;
            canvas.drawCircle(x, y, checkdR, paint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textSize = text.length();
        invalidate();
        if (textSize == maxItemSize && maxItemSize != 0) {
            if (inputEndClick != null) {
                inputEndClick.onInputEndClick(text.toString());
            }
        }
    }

    /** 密码输入完成回调 */
    public interface InputEndClick {
        void onInputEndClick(String text);
    }
}
