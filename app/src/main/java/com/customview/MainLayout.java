package com.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author zhengjingle
 */
public class MainLayout extends FrameLayout {

    public MainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public View view0,view1,view2;
    float startX,startY;
    float height0,height1,height2;//控件高度
    float maxMoveUpY0,maxMoveUpY1,maxMoveUpY2;//所允许最大上滑位置y
    float maxMoveDownY0,maxMoveDownY1,maxMoveDownY2;//所允许最大下滑位置y
    float y0,y1,y2;

    @Override
    protected void onFinishInflate() {
        
        super.onFinishInflate();
        view0=this.getChildAt(0);
        view1=this.getChildAt(1);
        view2=this.getChildAt(2);


        ViewTreeObserver ob = getViewTreeObserver();
        ob.addOnPreDrawListener(new OnPreDrawListener() {
            boolean hasMeasure;
            @Override
            public boolean onPreDraw() {
                if(!hasMeasure) {
                    height0=view0.getHeight();
                    height1=view1.getHeight();
                    height2=view2.getHeight();

                    maxMoveUpY0=-height0;
                    maxMoveDownY0=0;

                    maxMoveUpY1=0;
                    maxMoveDownY1=height0;

                    maxMoveUpY2=height1;
                    maxMoveDownY2=height0+height1;

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
                y1=view1.getY();
                y2=view2.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                float deltaX=event.getRawX()-startX;
                float deltaY=event.getRawY()-startY;

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
                view1.setY(move1);

                float move2 = y2 + deltaY;
                if (move2 < maxMoveUpY2)
                    move2 = maxMoveUpY2;
                if (move2 > maxMoveDownY2)
                    move2 = maxMoveDownY2;
                view2.setY(move2);

                // view2高度
                FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) view2.getLayoutParams();
                param.height = (int) (getHeight() - view2.getY());
                view2.setLayoutParams(param);

            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(event);
    }
} 