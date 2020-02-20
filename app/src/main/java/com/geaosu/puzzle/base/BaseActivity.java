package com.geaosu.puzzle.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.geaosu.puzzle.R;


public class BaseActivity extends AppCompatActivity {

//    finish();
//    overridePendingTransition(R.anim.left, R.anim.slide_left_exit);

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.rite, R.anim.left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left, R.anim.left2);
    }
}
