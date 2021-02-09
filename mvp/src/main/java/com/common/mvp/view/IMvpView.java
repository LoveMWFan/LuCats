package com.common.mvp.view;

import androidx.annotation.Keep;

import com.common.mvp.MvpEventListener;


@Keep
public interface IMvpView<E extends MvpEventListener> {

    void setEventListener(E e);

    void addInnerView(IMvpView view);

    <T extends IMvpView> T getInnerView(Class<T> tClass);
}
