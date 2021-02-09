package ma.bay.com.labase.common.mvp;

import com.common.mvp.MvpEventListener;
import com.common.mvp.contract.IMvpContract;

import ma.bay.com.labase.common.cview.indicator.FailureListener;

public interface ILAMvpContract {
    interface ILAMvpModel extends IMvpContract.IMvpModel {

    }

    interface ILAMvpView<E extends MvpEventListener> extends IMvpContract.IMvpView<E> {

        void setIndicatorFailureListener(FailureListener listener);

        void showIndicator();

        void hideIndicator();

        void showFailureIndicator();

        void showProgressDialog();

        void showProgressDialog(boolean isShowShade);

        void showProgressDialog(String string);

        void showProgressDialog(String string, boolean isShowShade);

        void dismissProgressDialog();

        void showToast(String content);

        void invokeMotionEffect();
    }

    interface ILAMvpPresenter<M extends IMvpContract.IMvpModel, V extends IMvpContract.IMvpView> extends IMvpContract.IMvpPresenter<M, V> {

    }
}
