package com.geaosu.puzzle.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geaosu.puzzle.R;
import com.geaosu.puzzle.base.BaseActivity;
import com.geaosu.puzzle.utils.MusicUtils;


/**
 * 游戏首页
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvStart;
    private TextView tvSet;
    private TextView tvReadme;
    private TextView tvAbout;
    private TextView tvExit;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvStart = (TextView) findViewById(R.id.tvStart);
        tvSet = (TextView) findViewById(R.id.tvSet);
        tvReadme = (TextView) findViewById(R.id.tvReadme);
        tvAbout = (TextView) findViewById(R.id.tvAbout);
        tvExit = (TextView) findViewById(R.id.tvExit);

        tvStart.setOnClickListener(this);
        tvSet.setOnClickListener(this);
        tvReadme.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        playSound();
        switch (v.getId()) {
            case R.id.tvStart:
                startActivity(new Intent(HomeActivity.this, ChosePhotoActivity.class));
                break;
            case R.id.tvSet:
                startActivity(new Intent(HomeActivity.this, SetActivity.class));
                break;
            case R.id.tvReadme:
                startActivity(new Intent(HomeActivity.this, ReadmeActivity.class));
                break;
            case R.id.tvAbout:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                break;
            case R.id.tvExit:
                showExitDialog();
                break;
        }
    }

    private void playSound() {
        MusicUtils.getDefault(HomeActivity.this).plarMusic();
    }

    /**
     * 退出确认（如果正在游戏中，图示退出，如果游戏完成了，不提示退出）
     * 如果游戏完成了，提示是否继续游戏，继续游戏，难度等级加1，以此类推
     * 1. 换图玩
     * 2. 手动指定游戏难度玩
     */
    public void showExitDialog() {
        //注册成功dialog
        View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.dialog_home_activity_exit, null, false);
        TextView tvDlgCancel = (TextView) dialogView.findViewById(R.id.tvDlgCancel);        // 难度：变态(5)
        TextView tvDlgExit = (TextView) dialogView.findViewById(R.id.tvDlgExit);                    // 用时：5分12秒

        tvDlgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

        tvDlgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                System.exit(0);
            }
        });

        //创建dialog
        if (mDialog == null) {
            mDialog = new Dialog(HomeActivity.this, R.style.play_success_dialog_style);
        }
        mDialog.setCancelable(false);
        mDialog.setContentView(dialogView);
        mDialog.show();
    }

    @Override
    public void onBackPressed() {

    }

}
