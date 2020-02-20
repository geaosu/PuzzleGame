package com.geaosu.puzzle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.geaosu.puzzle.R;
import com.geaosu.puzzle.base.BaseActivity;
import com.geaosu.puzzle.utils.MusicUtils;


/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    private TextView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ivBack = (TextView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void playSound() {
        MusicUtils.getDefault(this).plarMusic();
    }
}
