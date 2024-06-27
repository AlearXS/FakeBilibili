package com.example.jiemian.UI;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.Utils.FileUtils;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.bean.Shiping;
import com.example.jiemian.sqlite.ShipingDBUtils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import butterknife.BindView;
import butterknife.OnClick;

public class AddVideoActivity extends BaseActivity {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.sp_level)
    Spinner spLevel;
    @BindView(R.id.tv_path)
    TextView tvPath;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    String PATH = "";
    String level ="生活";
    @BindView(R.id.image_head)
    ImageView imageHead;
    String path1= "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_video;
    }

    @Override
    protected void init() {
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String[] languages = getResources().getStringArray(R.array.languages);
                level = languages[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @OnClick({ R.id.image_head, R.id.tv_path, R.id.tv_login})
    public void onViewClicked(View view) {
        long sTime = System.currentTimeMillis();
        switch (view.getId()) {

            case R.id.tv_path:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.image_head:
                PictureSelector
                        .create(AddVideoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture();
                break;
            case R.id.tv_login:
                String title = etTitle.getText().toString();
                if (TextUtils.isEmpty(title)){
                    showToast("请输入详细信息");
                    return;
                }else {
                    Shiping shiping = new Shiping();
                    shiping.setLevel(level);
                    shiping.setTitle(title);
                    shiping.setPath(PATH);
                    shiping.setType("1");
                    shiping.setTupian(path1);
                    ShipingDBUtils.getInstance(getApplicationContext()).insert(shiping);
                    long eTime = System.currentTimeMillis();
                    Log.i("添加视频使用的时间为: ", String.valueOf(eTime-sTime) + "ms");
                    showToast("保存成功");
                    finish();
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = FileUtils.getInstance().getPath(this, uri);
                    if (path != null) {
                        PATH = path;
                        tvPath.setText(FileUtils.getInstance().getFileNameWithSuffix(PATH));
                    }
                }
            }
            return;
        }
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                Glide.with(this).load(pictureBean.getPath()).into(imageHead);
                path1 = pictureBean.getPath();
            }
        }
    }
}