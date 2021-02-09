package com.common.mvp.view;

import android.app.Activity;

import com.common.mvp.MvpEventListener;
import com.common.mvp.contract.IMvpContract;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseMvpView<E extends MvpEventListener> implements IMvpContract.IMvpView<E> {
    private Activity mActivity;

    public BaseMvpView(Activity activity) {
        mActivity = activity;
    }

    protected Activity getActivity() {
        return mActivity;
    }

    private E mEventListener;

    @Override
    public void setEventListener(E e) {
        mEventListener = e;
    }

    protected E getEventListener() {
        return mEventListener;
    }

    private Map<Class, IMvpContract.IMvpView> mInnerViews = new HashMap<>();

    @Override
    public void addInnerView(IMvpContract.IMvpView v) {
        if (mInnerViews == null || v == null) {
            return;
        }
        Class resC = getAssignableClassFrom(v.getClass());
        if (resC != null) {
            mInnerViews.put(resC, v);
        }
    }

    private Class getAssignableClassFrom(Class aClass) {
        if (aClass.isInterface()) {
            if (IMvpContract.IMvpView.class.isAssignableFrom(aClass)) {
                return aClass;
            }
        }

        for (Class<?> iClass : aClass.getInterfaces()) {
            Class iiClass = getAssignableClassFrom(iClass);
            if (iiClass != null) {
                return iiClass;
            }
        }

        if (aClass.getSuperclass() != null) {
            return getAssignableClassFrom(aClass.getSuperclass());
        }

        return null;
    }

    @Override
    public <T extends IMvpContract.IMvpView> T getInnerView(Class<T> tClass) {
        if (mInnerViews == null) {
            return null;
        }
        return (T) mInnerViews.get(tClass);
    }

}
