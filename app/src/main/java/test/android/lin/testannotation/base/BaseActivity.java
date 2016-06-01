package test.android.lin.testannotation.base;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.module.news.ui.MainActivity;
import test.android.lin.testannotation.module.photo.ui.PictureActivity;
import test.android.lin.testannotation.module.setting.ui.SettingActivity;
import test.android.lin.testannotation.module.video.ui.VideoActivity;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;
    /**
     * 标示该activity是否可滑动退出,默认false
     */
    protected boolean mEnableSlider;

    /**
     * 布局的id
     */
    protected int mContentViewId;


    protected boolean mHasNavigationView;

    protected DrawerLayout drawerLayout;
    protected NavigationView mNavigationView;

    protected int mMenuId;

    protected int mToolBarTitle;

    /**
     * 默认选中的菜单项
     */
    protected int mMenuDefaultCheckedItem;

    /**
     * Toolbar左侧按钮的样式
     */
    private int mToolbarIndicator;

    /**
     * 控制滑动与否的接口
     */
//    protected SlidrInterface mSlidrInterface;
//
    /**
     * 结束Activity的可观测对象
     */
    private Observable<Boolean> mFinishObservable;

    /**
     * 跳转的类
     */
    private Class mClass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            ActivityFragmentInject annomation = getClass().getAnnotation(ActivityFragmentInject.class);

            mContentViewId = annomation.contentViewId();
            mMenuId = annomation.menuId();
            mEnableSlider = annomation.enableSlider();
            mHasNavigationView = annomation.hasNavigationView();
            mToolBarTitle = annomation.toolBarTitle();
            mToolbarIndicator = annomation.toolBatIndicate();
            mMenuDefaultCheckedItem = annomation.menuDefaultCheckItem();
        } else
            throw new RuntimeException("Class must add annotations of ActivityFragmentInitParams.class");

        setContentView(mContentViewId);
        if (mHasNavigationView) {
            initNavigationView();
            initFinishRxBus();
        }

        initToolbar();

        initView();

    }

    protected void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setCheckedItem(mMenuDefaultCheckedItem);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_news:
                        mClass = MainActivity.class;
                        break;
                    case R.id.action_video:
                        mClass = VideoActivity.class;
                        break;
                    case R.id.action_photo:
                        mClass = PictureActivity.class;
                        break;
                    case R.id.action_settings:
                        mClass = SettingActivity.class;
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (mClass != null) {
                    Intent intent = new Intent();
                    intent.setClass(BaseActivity.this, mClass);
                    // 此标志用于启动一个Activity的时候，若栈中存在此Activity实例，则把它调到栈顶。不创建多一个
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    mClass = null;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(mMenuId, menu);
        return true;
    }

    protected void initFinishRxBus() {

    }

    protected void initToolbar() {

    }

    protected void initView() {

    }

    @Override
    public void toast(String text) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
    }
}
