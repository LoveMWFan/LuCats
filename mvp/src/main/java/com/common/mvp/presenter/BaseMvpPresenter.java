package com.common.mvp.presenter;

import com.common.mvp.BridgeNode;
import com.common.mvp.INewModelRoute;
import com.common.mvp.INewViewRoute;
import com.common.mvp.IRoute;
import com.common.mvp.contract.IMvpContract;
import com.common.mvp.model.BaseMvpModel;
import com.common.mvp.view.BaseMvpView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseMvpPresenter<M extends IMvpContract.IMvpModel, V extends IMvpContract.IMvpView> implements IMvpContract.IMvpPresenter<M, V> {

    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    protected abstract void onAttach();

    protected abstract void onDetach();

    private M mModel;
    private Map<Class, V> mViews = new HashMap<>();

    private BridgeNode mBridgeNode;
    private INewViewRoute mNewViewRoute;
    private INewModelRoute mNewModelRoute;

    @Override
    public void setParentBridgeNode(BridgeNode parentBridge) {
        mBridgeNode = new BridgeNode(parentBridge);
        parentBridge.addChild(mBridgeNode);
        mNewViewRoute = mBridgeNode.getRoute(INewViewRoute.class);
        mNewModelRoute = mBridgeNode.getRoute(INewModelRoute.class);
    }

    @Override
    public final void setModel(M model) {
        mModel = model;
    }

    @Override
    public final void addView(V view) {
        if (view == null) {
            return;
        }
        Class iClass = getAssignableClassFrom(view.getClass());
        if (iClass != null && mViews != null) {
            mViews.put(iClass, view);
        }
    }

    @Override
    public final void attach() {
        onAttach();
    }

    @Override
    public final void detach() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }

        if (mModel != null) {
            mModel.detachPresenter();
            mModel = null;
        }

        if (mViews != null) {
            mViews.clear();
            mViews = null;
        }

        if (mBridgeNode != null) {
            mBridgeNode.release();
            mBridgeNode = null;
        }

        mNewViewRoute = null;
        mNewModelRoute = null;

        onDetach();
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

    protected final M getModel() {
        return mModel;
    }

    protected final <T extends V> T getView(Class<T> tClass) {
        if (mViews == null) {
            return null;
        }
        return (T) mViews.get(tClass);
    }

    protected final boolean isViewAttached() {
        if (mViews == null) {
            return false;
        }

        for (V view : mViews.values()) {
            if (view == null) {
                return false;
            }
        }

        return true;
    }

    protected final void add(Disposable subscription) {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.add(subscription);
        }
    }

    protected final void remove(Disposable subscription) {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.remove(subscription);
        }
    }

    //*****************

    protected BridgeNode getBridgeNode() {
        return mBridgeNode;
    }

    protected void newRoute(IRoute<?, ?> route) {
        if (mBridgeNode != null) {
            mBridgeNode.newRoute(route);
        }
    }

    protected <T extends IRoute> T getRoute(Class<T> tClass) {
        if (mBridgeNode != null) {
            return mBridgeNode.getRoute(tClass);
        }
        return null;
    }

    protected <T extends IRoute> List<T> getAllSuchRoutes(Class<T> tClass) {
        if (mBridgeNode != null) {
            return mBridgeNode.getAllSuchRoutes(tClass);
        }
        return null;
    }

    protected <T extends BaseMvpView> T newView(Class<T> tClass) {
        if (mNewViewRoute != null) {
            return (T) mNewViewRoute.call(tClass);
        }
        return null;
    }

    protected <T extends BaseMvpModel> T newModel(Class<T> tClass) {
        if (mNewModelRoute != null) {
            return (T) mNewModelRoute.call(tClass);
        }
        return null;
    }
}
