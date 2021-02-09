package com.common.mvp.contract;

import androidx.annotation.Keep;

import com.common.mvp.BridgeNode;
import com.common.mvp.MvpEventListener;

/**
 * @author feibiao.ma
 * @project LuCats
 * @package_name com.common.mvp.contract
 * @date 2021/2/9
 */
public interface IMvpContract {

    @Keep
    interface IMvpModel {
        void detachPresenter();
    }

    interface IMvpPresenter<M extends IMvpModel, V extends IMvpView> {
        void setParentBridgeNode(BridgeNode bridge);

        void setModel(M model);

        void addView(V view);

        void attach();

        void detach();

    }


    @Keep
    interface IMvpView<E extends MvpEventListener> {

        void setEventListener(E e);

        void addInnerView(IMvpView view);

        <T extends IMvpView> T getInnerView(Class<T> tClass);
    }

}
