package com.geaosu.puzzle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geaosu.puzzle.R;
import com.geaosu.puzzle.base.BaseActivity;
import com.geaosu.puzzle.bean.PhotoBean;
import com.geaosu.puzzle.utils.BigUtilsSP;
import com.geaosu.puzzle.utils.MusicUtils;

import java.util.ArrayList;


/**
 * 选择图片
 */
public class ChosePhotoActivity extends BaseActivity implements View.OnClickListener {


    private TextView ivBack;
    private TextView ivNext;
    private GridView gvPhotos;

    private ImageAdapter mImageAdapter;
    private ArrayList<PhotoBean> mPicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_photo);

        // 游戏页面也需要更新图片数据
        mPicList.add(new PhotoBean(R.mipmap.jsx_001, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_002, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_003, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_004, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_006, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_007, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_008, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_009, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_010, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_011, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_012, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_013, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_014, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_015, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_016, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_017, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_018, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_019, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_020, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_021, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_022, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_023, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_024, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_025, false));
        mPicList.add(new PhotoBean(R.mipmap.jsx_026, false));

        ivBack = (TextView) findViewById(R.id.ivBack);
        ivNext = (TextView) findViewById(R.id.ivNext);
        gvPhotos = (GridView) findViewById(R.id.gvPhotos);

        ivBack.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        gvPhotos.setAdapter(mImageAdapter = new ImageAdapter(ChosePhotoActivity.this));
        gvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSound();
                boolean checked = mPicList.get(position).isChecked();
                if (checked) {
                    mPicList.get(position).setChecked(false);
                } else {
                    for (int i = 0; i < mPicList.size(); i++) {
                        mPicList.get(i).setChecked(false);
                    }
                    mPicList.get(position).setChecked(true);
                }
                mImageAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        playSound();
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivNext:
                int resId = -1;
                for (int i = 0; i < mPicList.size(); i++) {
                    if (mPicList.get(i).isChecked()) {
                        resId = mPicList.get(i).getResId();
                    }
                }

                if (resId == -1) {
                    Toast.makeText(ChosePhotoActivity.this, "请选择一张图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                BigUtilsSP.putInt("resId", resId);
                // startActivity(new Intent(ChosePhotoActivity.this, MainActivity.class));
                finish();
                startActivity(new Intent(ChosePhotoActivity.this, PlayActivity.class));
                break;
        }
    }

    /**
     * 数据适配器
     */
    private class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context context) {
            this.mContext = context;
        }


        @Override
        public int getCount() {
            return mPicList.size();
        }

        @Override
        public Object getItem(int position) {
            return mPicList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gride_view_item_layout, parent, false);
                holder = new ViewHolder();
                holder.ivPic = convertView.findViewById(R.id.ivPic);
                holder.rlSelected = convertView.findViewById(R.id.rlSelected);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            boolean checked = mPicList.get(position).isChecked();
            if (checked) {
                holder.rlSelected.setVisibility(View.VISIBLE);
            } else {
                holder.rlSelected.setVisibility(View.GONE);
            }
            holder.ivPic.setImageDrawable(getResources().getDrawable(mPicList.get(position).getResId()));
            return convertView;
        }

    }

    static class ViewHolder {
        private ImageView ivPic;
        private RelativeLayout rlSelected;
    }

    private void playSound() {
        MusicUtils.getDefault(ChosePhotoActivity.this).plarMusic();
    }

    @Override
    public void onBackPressed() {

    }
}
