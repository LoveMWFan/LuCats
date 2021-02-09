package ma.bay.com.labase.common.mvp;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.common.mvp.MvpEventListener;
import com.common.mvp.view.BaseMvpView;

import ma.bay.com.labase.common.cview.dialog.LAProgressDialog;
import ma.bay.com.labase.common.cview.indicator.FailureListener;
import ma.bay.com.labase.common.cview.indicator.Indicator;
import ma.bay.com.labase.common.effect.MotionEffect;


public abstract class LAMvpView<E extends MvpEventListener> extends BaseMvpView<E> implements ILAMvpContract.ILAMvpView<E> {

    protected Indicator mIndicator;
    private LAProgressDialog mProgressDialog;

    public LAMvpView(Activity activity) {
        super(activity);
        mIndicator = Indicator.with(activity);
    }

    @Override
    public void setIndicatorFailureListener(final FailureListener listener) {
        if (mIndicator == null) {
            return;
        }
        mIndicator.setListener(new FailureListener() {
            @Override
            public void onTryAgain() {
                listener.onTryAgain();
            }
        });
    }

    @Override
    public void showIndicator() {
        if (mIndicator == null) {
            return;
        }
        mIndicator.show();
    }

    @Override
    public void hideIndicator() {
        if (mIndicator == null) {
            return;
        }
        mIndicator.hide();
    }

    @Override
    public void showFailureIndicator() {
        if (mIndicator == null) {
            return;
        }
        mIndicator.showFailure();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    @Override
    public void showProgressDialog(String text) {
        if (mProgressDialog == null) {
            mProgressDialog = new LAProgressDialog(getActivity());
        }
        mProgressDialog.showProgressDialog(text);
    }

    @Override
    public void showProgressDialog(boolean isShowShade) {
        showProgressDialog(null, isShowShade);
    }

    @Override
    public void showProgressDialog(String text, boolean isShowShade) {
        if (mProgressDialog == null) {
            mProgressDialog = new LAProgressDialog(getActivity());
        }
        mProgressDialog.showProgressDialog(text, isShowShade);
    }

    @Override
    public void showToast(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void invokeMotionEffect() {
        MotionEffect.invoke(getActivity());
    }
}
