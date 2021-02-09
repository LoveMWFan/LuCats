package ma.bay.com.labase.common.android;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ma.bay.com.labase.R;
import ma.bay.com.labase.common.effect.MotionEffect;


/**
 * Created by wangl2138 on 2017/12/21.
 */

public class LAActivity extends BaseActivity {
    private Toast mToastNetworkFailure;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void networkFailure() {
        if (isFinishing()) {
            return;
        }
        if (mToastNetworkFailure == null) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_network_failure, null);
            mToastNetworkFailure = new Toast(this);
            mToastNetworkFailure.setView(view);
            mToastNetworkFailure.setGravity(Gravity.NO_GRAVITY, 0, 0);
            mToastNetworkFailure.setDuration(Toast.LENGTH_SHORT);
            mToastNetworkFailure.show();

        } else {
            mToastNetworkFailure.show();
        }
    }

    private void serverFailure(String msg) {
        showToast(TextUtils.isEmpty(msg) ? "服务器故障，请在微博上了解我们的进度 @扇贝网" : msg);
    }

    protected void invokeMotionEffect() {
        MotionEffect.invoke(this);
    }

    @Override
    public void onBackPressed() {
        invokeMotionEffect();
        super.onBackPressed();
    }
}

