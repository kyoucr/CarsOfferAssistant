package com.qinshou.administrator.carsofferassistant.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;

/**
 * Created by 禽兽先生 on 2016.03.06
 */
public class IndexOnRight extends View {
    private Paint paint = new Paint();//相应的画笔
    private boolean isDown = false;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;//点击事件
    private String[] index = {"热", "A", "B", "C", "D", "F", "G", "H",
            "J", "K", "L", "M", "N", "O", "Q", "R", "S", "T", "W", "X", "Y", "Z"};//侧边栏显示字母
    private int choose = -1;//是否选中
    private TextView textViewDialog;//提示显示文本框

    public interface OnTouchingLetterChangedListener {
        void OnTouchingLetterChanged(String string);
    }

    private void init() {
        //画笔初始化
        paint = new Paint();
    }

    /**
     * 构造函数 数据初始化
     * <p/>
     * context
     * 上下文对象
     * attrs
     * 属性列表
     * defStyleAttr
     * 默认样式
     */
    public IndexOnRight(Context context) {
        this(context, null);
        init();
    }

    public IndexOnRight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexOnRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //绘制列表控件的方法
        //将要绘制的字母以从上到下的顺序绘制在一个指定区域
        //如果是进行选中的字母就进行高亮显示
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / index.length;// 获取每一个字母的高度

        for (int i = 0; i < index.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
//            paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20f);
            // 选中的状态
            if (isDown) {
                paint.setColor(Color.parseColor("#ffffff"));
                //paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(index[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(index[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
    }

    /**
     * 处理触摸事件的方法
     * 用户按下时候，整个控件背景变化
     * 根据按下y坐标 判断究竟用户按下那个字母
     * 当前字母高亮显示 将其字母显示listview中央
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //处理触摸事件的方法
        //用户按下时候，整个控件背景变化
        //根据按下y坐标 判断究竟用户按下那个字母
        //当前字母高亮显示 将其字母显示listview中央
        int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * index.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        switch (action) {
            case MotionEvent.ACTION_UP:
                isDown = false;
                setBackgroundResource(android.R.color.transparent);
                choose = -1;//
                invalidate();
                if (textViewDialog != null) {
                    textViewDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                isDown = true;
                setBackgroundResource(R.color.index_on_right_bg_color);
                if (oldChoose != c) {
                    if (c >= 0 && c < index.length) {
                        if (listener != null) {
                            listener.OnTouchingLetterChanged(index[c]);
                        }
                        if (textViewDialog != null) {
                            textViewDialog.setText(index[c]);
                            textViewDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public OnTouchingLetterChangedListener getOnTouchingLetterChangedListener() {
        //获取字母改变的监听器
        return onTouchingLetterChangedListener;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        //设置字母改变的监听器
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public TextView getTextViewDialog() {
        //获取弹出对话框
        return textViewDialog;
    }

    public void setTextViewDialog(TextView textViewDialog) {
        //设置对话框
        this.textViewDialog = textViewDialog;
    }


}
