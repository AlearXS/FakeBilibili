package com.example.jiemian.UI;

import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.jiemian.R;
import com.example.jiemian.adapter.Myadapter;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.fragment.ShouyeFragment;
import com.example.jiemian.fragment.UserFragment;
import com.example.jiemian.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.main_pager)
    NoScrollViewPager mainpager;
    Myadapter myadapter;
    private long clickTime;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        List<Fragment>fragments=new ArrayList<>();
        fragments.add(new ShouyeFragment());
        fragments.add(new UserFragment());
        myadapter=new Myadapter(getSupportFragmentManager(),fragments);
        // 只缓存当前页，并且不缓存其他页面
        // 每次切换到新页面时，ViewPager 都会销毁上一个页面并创建新的页面
        mainpager.setOffscreenPageLimit(1);
        mainpager.setAdapter(myadapter);
        radioGroup.setOnCheckedChangeListener(this);
    }


    /**
     *  通过导航栏切换页面
     * @param group the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.tab_home:
                // 0 对应 adapter 中的 ShouyeFragment
                mainpager.setCurrentItem(0,false);
                break;


            case R.id.tab_me:
                // 1 对应 adapter 中的 UserFragment
                mainpager.setCurrentItem(1,false);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            try {
                //正常退出
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}