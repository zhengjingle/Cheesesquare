package com.cheesesquare;

import com.customview.DetailLayout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author zhengjingle
 */
public class DetailActivity extends Activity{

    DetailLayout detailLayout;
    TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        detailLayout=(DetailLayout)findViewById(R.id.detailLayout);

        tv_name=(TextView)findViewById(R.id.tv_name);
        detailLayout.setTextView(tv_name);

    }

}
