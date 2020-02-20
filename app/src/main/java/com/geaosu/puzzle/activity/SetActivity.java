package com.geaosu.puzzle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.geaosu.puzzle.R;
import com.geaosu.puzzle.base.BaseActivity;
import com.geaosu.puzzle.utils.BigUtilsSP;
import com.geaosu.puzzle.utils.MusicUtils;
import com.suke.widget.SwitchButton;

/**
 * 设置
 */
public class SetActivity extends BaseActivity {

    private TextView ivBack;

    private RadioGroup rgSelectMode;
    private RadioButton rbGeneral;
    private RadioButton rbExchange;


    private RadioGroup rgSelectDifficulty;
    private RadioButton rbEasyDifficulty;
    private RadioButton rbLowDifficulty;
    private RadioButton rbMediumDifficulty;
    private RadioButton rbHighDifficulty;
    private RadioButton rbMetamorphosis;


    private SwitchButton sbMusic;
    private SwitchButton sbSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        ivBack = (TextView) findViewById(R.id.ivBack);
        rgSelectMode = (RadioGroup) findViewById(R.id.rgSelectMode);
        rbGeneral = (RadioButton) findViewById(R.id.rbGeneral);
        rbExchange = (RadioButton) findViewById(R.id.rbExchange);
        rgSelectDifficulty = (RadioGroup) findViewById(R.id.rgSelectDifficulty);
        rbEasyDifficulty = (RadioButton) findViewById(R.id.rbEasyDifficulty);
        rbLowDifficulty = (RadioButton) findViewById(R.id.rbLowDifficulty);
        rbMediumDifficulty = (RadioButton) findViewById(R.id.rbMediumDifficulty);
        rbHighDifficulty = (RadioButton) findViewById(R.id.rbHighDifficulty);
        rbMetamorphosis = (RadioButton) findViewById(R.id.rbMetamorphosis);
        sbMusic = (SwitchButton) findViewById(R.id.sbMusic);
        sbSound = (SwitchButton) findViewById(R.id.sbSound);

        loadSet();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                finish();
            }
        });

        rgSelectMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                playSound();
                switch (checkedId) {
                    case R.id.rbGeneral:
                        BigUtilsSP.putInt("mode", 1);           // 一般模式（1）
                        break;
                    case R.id.rbExchange:
                        BigUtilsSP.putInt("mode", 2);           // 交换模式（2）
                        break;
                }
            }
        });

        rgSelectDifficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                playSound();
                switch (checkedId) {
                    case R.id.rbEasyDifficulty:
                        BigUtilsSP.putInt("difficulty", 1);     // 简单（1）
                        break;
                    case R.id.rbLowDifficulty:
                        BigUtilsSP.putInt("difficulty", 2);     // 低难（2）
                        break;
                    case R.id.rbMediumDifficulty:
                        BigUtilsSP.putInt("difficulty", 3);     // 中难（3）
                        break;
                    case R.id.rbHighDifficulty:
                        BigUtilsSP.putInt("difficulty", 4);     // 高难（4）
                        break;
                    case R.id.rbMetamorphosis:
                        BigUtilsSP.putInt("difficulty", 5);     // 变态（5）
                        break;
                }
            }
        });

        sbMusic.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                playSound();
                if (isChecked) {
                    BigUtilsSP.putBoolean("music", true);
                } else {
                    BigUtilsSP.putBoolean("music", false);
                }
            }
        });

        sbSound.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                playSound();
                if (isChecked) {
                    BigUtilsSP.putBoolean("sound", true);
                } else {
                    BigUtilsSP.putBoolean("sound", false);
                }
            }
        });
    }

    /**
     * loading set
     */
    private void loadSet() {
        // mode
        int mode = BigUtilsSP.getInt("mode", 1);
        switch (mode) {
            case 1:
                rbGeneral.setChecked(true);
                break;
            case 2:
                rbExchange.setChecked(true);
                break;
        }

        // difficulty
        int difficulty = BigUtilsSP.getInt("difficulty", 1);
        switch (difficulty) {
            case 1:
                rbEasyDifficulty.setChecked(true);
                break;
            case 2:
                rbLowDifficulty.setChecked(true);
                break;
            case 3:
                rbMediumDifficulty.setChecked(true);
                break;
            case 4:
                rbHighDifficulty.setChecked(true);
                break;
            case 5:
                rbMetamorphosis.setChecked(true);
                break;
        }

        // music
        if (BigUtilsSP.getBoolean("music", true)) {
            sbMusic.setChecked(true);
        } else {
            sbMusic.setChecked(false);
        }

        // sound
        if (BigUtilsSP.getBoolean("sound", true)) {
            sbSound.setChecked(true);
        } else {
            sbSound.setChecked(false);
        }

    }

    private void playSound() {
        MusicUtils.getDefault(SetActivity.this).plarMusic();
    }

    @Override
    public void onBackPressed() {

    }

}
