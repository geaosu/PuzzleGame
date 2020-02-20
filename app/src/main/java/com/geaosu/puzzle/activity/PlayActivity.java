package com.geaosu.puzzle.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geaosu.puzzle.R;
import com.geaosu.puzzle.base.BaseActivity;
import com.geaosu.puzzle.utils.BigUtilsSP;
import com.geaosu.puzzle.utils.MusicUtils;
import com.geaosu.puzzle.utils.Utils;
import com.geaosu.puzzle.widget.PuzzleLayout;

import java.util.Random;


/**
 * 游戏页面
 */
public class PlayActivity extends BaseActivity {

    private TextView ivBack;
    private ImageView ivPic;
    private TextView tvMode;
    private TextView tvDifficulty;
    private TextView tvTime;
    private PuzzleLayout plPlay;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ivBack = (TextView) findViewById(R.id.ivBack);
        ivPic = (ImageView) findViewById(R.id.ivPic);
        tvMode = (TextView) findViewById(R.id.tvMode);
        tvDifficulty = (TextView) findViewById(R.id.tvDifficulty);
        tvTime = (TextView) findViewById(R.id.tvTime);
        plPlay = (PuzzleLayout) findViewById(R.id.plPlay);

        loadSet();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                showExitGameDialog();
            }
        });

        plPlay.setOnSuccessListener(new PuzzleLayout.OnSuccessListener() {
            @Override
            public void onSuccess() {
                showSuccessDialog();
            }
        });
    }

    /**
     * 加载游戏设置
     */
    private void loadSet() {
        // mode
        int mode = BigUtilsSP.getInt("mode", 1);// 一般模式（1）
        if (mode == 1) {
            // 修改游戏模式
            tvMode.setText("模式：一般模式（1）");
            plPlay.changeMode(PuzzleLayout.GAME_MODE_NORMAL);
        } else {
            // 修改游戏模式
            tvMode.setText("模式：交换模式（2）");
            plPlay.changeMode(PuzzleLayout.GAME_MODE_EXCHANGE);
        }

        // difficulty
        int difficulty = BigUtilsSP.getInt("difficulty", 1);
        switch (difficulty) {
            case 1:
                tvDifficulty.setText("难度：简单（1）");
                plPlay.setLevel(1);
                break;
            case 2:
                tvDifficulty.setText("难度：低难（2）");
                plPlay.setLevel(2);
                break;
            case 3:
                tvDifficulty.setText("难度：中难（3）");
                plPlay.setLevel(3);
                break;
            case 4:
                tvDifficulty.setText("难度：高难（4）");
                plPlay.setLevel(4);
                break;
            case 5:
                tvDifficulty.setText("难度：变态（5）");
                plPlay.setLevel(5);
                break;
        }

        int resId = BigUtilsSP.getInt("resId", -1);
        if (resId == -1) {
            Toast.makeText(PlayActivity.this, "图片数据错误，请重新选择", Toast.LENGTH_SHORT).show();
            finish();
        }

        plPlay.changeRes(resId);
        // 完整的游戏图，提示图
        ivPic.setImageBitmap(Utils.readBitmap(getApplicationContext(), plPlay.getRes(), 4));
    }

    private void playSound() {
        MusicUtils.getDefault(PlayActivity.this).plarMusic();
    }

    /**
     * 退出确认（如果正在游戏中，图示退出，如果游戏完成了，不提示退出）
     * 如果游戏完成了，提示是否继续游戏，继续游戏，难度等级加1，以此类推
     * 1. 换图玩
     * 2. 手动指定游戏难度玩
     */
    public void showSuccessDialog() {
        //注册成功dialog
        View dialogView = LayoutInflater.from(PlayActivity.this).inflate(R.layout.dialog_play_success, null, false);
        final TextView tvDialogDifficulty = (TextView) dialogView.findViewById(R.id.tvDifficulty);        // 难度：变态(5)
        TextView tvDialogTime = (TextView) dialogView.findViewById(R.id.tvTime);                    // 用时：5分12秒
        TextView tvDialogRanking = (TextView) dialogView.findViewById(R.id.tvRanking);              // 排名：第1名
        TextView tvDialogOldPicOldDif = (TextView) dialogView.findViewById(R.id.tvOldPicOldDif);    // 继续-原图（难度不变）
        TextView tvDialogNewPicOldDif = (TextView) dialogView.findViewById(R.id.tvNewPicOldDif);    // 继续-更换图片（难度不变）
        TextView tvDialogOldPicNewDif = (TextView) dialogView.findViewById(R.id.tvOldPicNewDif);    // 继续-原图（增加难度）
        TextView tvDialogNewPicNewDif = (TextView) dialogView.findViewById(R.id.tvNewPicNewDif);    // 继续-更换图片（增加难度）
        TextView tvDialogExit = (TextView) dialogView.findViewById(R.id.tvExit);                    // 退出

        // difficulty
        final int difficulty = plPlay.getLevel();
        switch (difficulty) {
            case 1:
                tvDialogDifficulty.setText("难度：简单（1）");
                break;
            case 2:
                tvDialogDifficulty.setText("难度：低难（2）");
                break;
            case 3:
                tvDialogDifficulty.setText("难度：中难（3）");
                break;
            case 4:
                tvDialogDifficulty.setText("难度：高难（4）");
                break;
            case 5:
                tvDialogDifficulty.setText("难度：变态(5)");
                break;
        }

        tvTime.setText("用时：00分00秒");

        tvDialogRanking.setText("排名：第1名");

        // 继续-原图（难度不变）
        tvDialogOldPicOldDif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                plPlay.reset();
                Toast.makeText(PlayActivity.this, "已打乱图片，游戏开始", Toast.LENGTH_SHORT).show();
            }
        });

        // 继续-更换图片（难度不变）
        tvDialogNewPicOldDif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                Random random = new Random();
                int index = random.nextInt(26);
                Log.d("geaosu", "随机数 = " + index);
                switch (index) {
                    case 1:
                        plPlay.changeRes(R.mipmap.jsx_001);
                        break;
                    case 2:
                        plPlay.changeRes(R.mipmap.jsx_002);
                        break;
                    case 3:
                        plPlay.changeRes(R.mipmap.jsx_003);
                        break;
                    case 4:
                        plPlay.changeRes(R.mipmap.jsx_004);
                        break;
                    case 5:
                        plPlay.changeRes(R.mipmap.jsx_005);
                        break;
                    case 6:
                        plPlay.changeRes(R.mipmap.jsx_006);
                        break;
                    case 7:
                        plPlay.changeRes(R.mipmap.jsx_007);
                        break;
                    case 8:
                        plPlay.changeRes(R.mipmap.jsx_008);
                        break;
                    case 9:
                        plPlay.changeRes(R.mipmap.jsx_009);
                        break;
                    case 10:
                        plPlay.changeRes(R.mipmap.jsx_010);
                        break;
                    case 11:
                        plPlay.changeRes(R.mipmap.jsx_011);
                        break;
                    case 12:
                        plPlay.changeRes(R.mipmap.jsx_012);
                        break;
                    case 13:
                        plPlay.changeRes(R.mipmap.jsx_013);
                        break;
                    case 14:
                        plPlay.changeRes(R.mipmap.jsx_014);
                        break;
                    case 15:
                        plPlay.changeRes(R.mipmap.jsx_015);
                        break;
                    case 16:
                        plPlay.changeRes(R.mipmap.jsx_016);
                        break;
                    case 17:
                        plPlay.changeRes(R.mipmap.jsx_017);
                        break;
                    case 18:
                        plPlay.changeRes(R.mipmap.jsx_018);
                        break;
                    case 19:
                        plPlay.changeRes(R.mipmap.jsx_019);
                        break;
                    case 20:
                        plPlay.changeRes(R.mipmap.jsx_020);
                        break;
                    case 21:
                        plPlay.changeRes(R.mipmap.jsx_021);
                        break;
                    case 22:
                        plPlay.changeRes(R.mipmap.jsx_022);
                        break;
                    case 23:
                        plPlay.changeRes(R.mipmap.jsx_023);
                        break;
                    case 24:
                        plPlay.changeRes(R.mipmap.jsx_024);
                        break;
                    case 25:
                        plPlay.changeRes(R.mipmap.jsx_025);
                        break;
                    case 26:
                        plPlay.changeRes(R.mipmap.jsx_026);
                        break;
                }
                // 完整的游戏图，提示图
                ivPic.setImageBitmap(Utils.readBitmap(getApplicationContext(), plPlay.getRes(), 4));
                plPlay.reset();
                Toast.makeText(PlayActivity.this, "已更换图片，游戏开始", Toast.LENGTH_SHORT).show();
            }
        });

        // 继续-原图（增加难度）
        tvDialogOldPicNewDif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                int difficulty = plPlay.getLevel();
                switch (difficulty) {
                    case 1:
                        tvDifficulty.setText("难度：低难（2）");
                        plPlay.setLevel(2);
                        break;
                    case 2:
                        tvDifficulty.setText("难度：中难（3）");
                        plPlay.setLevel(3);
                        break;
                    case 3:
                        tvDifficulty.setText("难度：高难（4）");
                        plPlay.setLevel(4);
                        break;
                    case 4:
                        tvDifficulty.setText("难度：变态（5）");
                        plPlay.setLevel(5);
                        break;
                    case 5:
                        Toast.makeText(PlayActivity.this, "已经是最大难度了", Toast.LENGTH_SHORT).show();
                        tvDifficulty.setText("难度：变态(5)");
                        plPlay.setLevel(5);
                        return;
                }
                Toast.makeText(PlayActivity.this, "难度增加，游戏开始", Toast.LENGTH_SHORT).show();
            }
        });

        // 继续-更换图片（增加难度）
        tvDialogNewPicNewDif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                // 难度增加
                int difficulty = plPlay.getLevel();
                switch (difficulty) {
                    case 1:
                        tvDifficulty.setText("难度：低难（2）");
                        plPlay.setLevel(2);
                        break;
                    case 2:
                        tvDifficulty.setText("难度：中难（3）");
                        plPlay.setLevel(3);
                        break;
                    case 3:
                        tvDifficulty.setText("难度：高难（4）");
                        plPlay.setLevel(4);
                        break;
                    case 4:
                        tvDifficulty.setText("难度：变态（5）");
                        plPlay.setLevel(5);
                        break;
                    case 5:
                        Toast.makeText(PlayActivity.this, "已经是最大难度了", Toast.LENGTH_SHORT).show();
                        tvDifficulty.setText("难度：变态(5)");
                        plPlay.setLevel(5);
                        break;
                }

                // 更换图片
                Random random = new Random();
                int index = random.nextInt(26) + 1;
                switch (index) {
                    case 1:
                        plPlay.changeRes(R.mipmap.jsx_001);
                        break;
                    case 2:
                        plPlay.changeRes(R.mipmap.jsx_002);
                        break;
                    case 3:
                        plPlay.changeRes(R.mipmap.jsx_003);
                        break;
                    case 4:
                        plPlay.changeRes(R.mipmap.jsx_004);
                        break;
                    case 5:
                        plPlay.changeRes(R.mipmap.jsx_005);
                        break;
                    case 6:
                        plPlay.changeRes(R.mipmap.jsx_006);
                        break;
                    case 7:
                        plPlay.changeRes(R.mipmap.jsx_007);
                        break;
                    case 8:
                        plPlay.changeRes(R.mipmap.jsx_008);
                        break;
                    case 9:
                        plPlay.changeRes(R.mipmap.jsx_009);
                        break;
                    case 10:
                        plPlay.changeRes(R.mipmap.jsx_010);
                        break;
                    case 11:
                        plPlay.changeRes(R.mipmap.jsx_011);
                        break;
                    case 12:
                        plPlay.changeRes(R.mipmap.jsx_012);
                        break;
                    case 13:
                        plPlay.changeRes(R.mipmap.jsx_013);
                        break;
                    case 14:
                        plPlay.changeRes(R.mipmap.jsx_014);
                        break;
                    case 15:
                        plPlay.changeRes(R.mipmap.jsx_015);
                        break;
                    case 16:
                        plPlay.changeRes(R.mipmap.jsx_016);
                        break;
                    case 17:
                        plPlay.changeRes(R.mipmap.jsx_017);
                        break;
                    case 18:
                        plPlay.changeRes(R.mipmap.jsx_018);
                        break;
                    case 19:
                        plPlay.changeRes(R.mipmap.jsx_019);
                        break;
                    case 20:
                        plPlay.changeRes(R.mipmap.jsx_020);
                        break;
                    case 21:
                        plPlay.changeRes(R.mipmap.jsx_021);
                        break;
                    case 22:
                        plPlay.changeRes(R.mipmap.jsx_022);
                        break;
                    case 23:
                        plPlay.changeRes(R.mipmap.jsx_023);
                        break;
                    case 24:
                        plPlay.changeRes(R.mipmap.jsx_024);
                        break;
                    case 25:
                        plPlay.changeRes(R.mipmap.jsx_025);
                        break;
                    case 26:
                        plPlay.changeRes(R.mipmap.jsx_026);
                        break;
                }
                ivPic.setImageBitmap(Utils.readBitmap(getApplicationContext(), plPlay.getRes(), 4));
                plPlay.reset();
            }
        });

        tvDialogExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                finish();
            }
        });

        //创建dialog
        if (mDialog == null) {
            mDialog = new Dialog(PlayActivity.this, R.style.play_success_dialog_style);
        }
        mDialog.setCancelable(false);
        mDialog.setContentView(dialogView);
        mDialog.show();
    }


    /**
     * 退出确认（如果正在游戏中，图示退出，如果游戏完成了，不提示退出）
     * 如果游戏完成了，提示是否继续游戏，继续游戏，难度等级加1，以此类推
     * 1. 换图玩
     * 2. 手动指定游戏难度玩
     */
    public void showExitGameDialog() {
        //注册成功dialog
        View dialogView = LayoutInflater.from(PlayActivity.this).inflate(R.layout.dialog_exit_game, null, false);
        TextView tvDlgContinue = (TextView) dialogView.findViewById(R.id.tvDlgContinue);
        TextView tvDlgExit = (TextView) dialogView.findViewById(R.id.tvDlgExit);

        tvDlgContinue.setOnClickListener(new View.OnClickListener() {
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
                playSound();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                startActivity(new Intent(PlayActivity.this, HomeActivity.class));
                finish();
            }
        });

        //创建dialog
        if (mDialog == null) {
            mDialog = new Dialog(PlayActivity.this, R.style.play_success_dialog_style);
        }
        mDialog.setCancelable(false);
        mDialog.setContentView(dialogView);
        mDialog.show();
    }

    @Override
    public void onBackPressed() {

    }
}
