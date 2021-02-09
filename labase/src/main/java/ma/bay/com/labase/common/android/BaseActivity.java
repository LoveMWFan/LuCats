package ma.bay.com.labase.common.android;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.common.mvp.BaseMvpActivity;

import ma.bay.com.labase.R;
import ma.bay.com.labase.common.cview.dialog.LAProgressDialog;
import ma.bay.com.labase.common.utlis.ThemeUtils;


/**
 * Create wangle12138 on 18/2/20.
 */
public abstract class BaseActivity extends BaseMvpActivity {

    private final String TAG = getClass().getName();

    private Toolbar mToolbar;
    private AudioManager mAudioManger; // 声音管理类
    private LAProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 默认都有返回键
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        fitSystemUi();
        mAudioManger = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        log("onCreate");
    }

    protected void fitSystemUi() {
        ThemeUtils.fullscreen(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:    // 增大音量
                mAudioManger.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:   // 减小音量
                mAudioManger.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = findToolbarById();
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                ActionBar ab = getSupportActionBar();
                if (ab != null) {
                    ab.setDisplayHomeAsUpEnabled(true);
                }
            }
        }
        return mToolbar;
    }

    protected Toolbar findToolbarById() {
        return (Toolbar) findViewById(R.id.toolbar_base);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    public int getActionBarHeight() {
        int actionBarHeight = 0;

        if (getToolbar() != null) {
            actionBarHeight = getToolbar().getHeight();
        } else {
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        }

        return actionBarHeight;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        log("onNewIntent:" + intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 防止连续的activity共有的toolbar会被设置透明度
        if (mToolbar != null && mToolbar.getBackground() != null) {
            mToolbar.getBackground().setAlpha(255);
        }
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        log("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(String msg) {
        if (isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new LAProgressDialog(this);
        }
        mProgressDialog.showProgressDialog(msg);
    }

    public void showToast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected void log(String msg) {
        Log.i(TAG, msg);
    }
}
