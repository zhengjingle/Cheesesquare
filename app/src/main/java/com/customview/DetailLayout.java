package com.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * @author zhengjingle
 */
public class DetailLayout extends FrameLayout{
    public DetailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    View view0;
    ScrollView scrollView;
    float startX,startY;
    float height0,height1;//控件高度
    float maxMoveUpY0,maxMoveUpY1;//所允许最大上滑位置y
    float maxMoveDownY0,maxMoveDownY1;//所允许最大下滑位置y
    float y0,y1;

    float length;
    float precent;

    @Override
    protected void onFinishInflate() {
        
        super.onFinishInflate();
        view0=this.getChildAt(0);
        scrollView=(ScrollView)this.getChildAt(1);

        ViewTreeObserver ob = getViewTreeObserver();
        ob.addOnPreDrawListener(new OnPreDrawListener() {
            boolean hasMeasure;
            @Override
            public boolean onPreDraw() {
                if(!hasMeasure) {
                    height0=view0.getHeight();
                    height1=scrollView.getHeight();

                    maxMoveUpY0=-3*height0/4;
                    maxMoveDownY0=0;

                    maxMoveUpY1=height0/4;
                    maxMoveDownY1=height0;

                    length=maxMoveDownY0-maxMoveUpY0;

                    hasMeasure=true;
                }
                return true;
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

                startX=event.getRawX();
                startY=event.getRawY();

                y0=view0.getY();
                y1=scrollView.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                float deltaX=event.getRawX()-startX;
                float deltaY=event.getRawY()-startY;

                if(scrollView.getY()==height0/4 && scrollView.getScrollY()==0 && deltaY>0){
                    //scrollView在顶部，scrollView内部也滑到顶，并且向下滑。这种情况不允许内部滑动。
                }else if(scrollView.getY()==height0/4){
                    //除上面那种情况外，其他scrollView在顶部的情况都可以内部滑动
                    return super.dispatchTouchEvent(event);
                }

                move(deltaY);
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private void move(float deltaY){
        float move0 = y0 + deltaY;
        if (move0 < maxMoveUpY0)
            move0 = maxMoveUpY0;
        if (move0 > maxMoveDownY0)
            move0 = maxMoveDownY0;
        view0.setY(move0);

        float move1 = y1 + deltaY;
        if (move1 < maxMoveUpY1)
            move1 = maxMoveUpY1;
        if (move1 > maxMoveDownY1)
            move1 = maxMoveDownY1;
        scrollView.setY(move1);

        // scrollView高度
        FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
        param.height = (int) (getHeight() - scrollView.getY());
        scrollView.setLayoutParams(param);

        //字体大小
        precent=(move0-maxMoveUpY0)/length;
        if(tv_name!=null){
            LayoutParams lp_tv_name=(LayoutParams) tv_name.getLayoutParams();
            lp_tv_name.leftMargin=(int) (maxLeftMargin/2+maxLeftMargin*precent/2);
            tv_name.setLayoutParams(lp_tv_name);
            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,maxTextSize/2+maxTextSize*precent/2);
        }
    }

    TextView tv_name;
    float maxTextSize;
    float maxLeftMargin;
    public void setTextView(TextView tv_name){
        this.tv_name=tv_name;
        maxTextSize=tv_name.getTextSize();
        LayoutParams lp_tv_name=(LayoutParams) tv_name.getLayoutParams();
        maxLeftMargin=lp_tv_name.leftMargin;
    }

}
