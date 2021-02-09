package com.common.mvp.presenter;

import com.common.mvp.BridgeNode;
import com.common.mvp.model.IMvpModel;
import com.common.mvp.view.IMvpView;


public interface IMvpPresenter<M extends IMvpModel, V extends IMvpView> {
    void setParentBridgeNode(BridgeNode bridge);

    void setModel(M model);

    void addView(V view);

    void attach();

    void detach();

}
