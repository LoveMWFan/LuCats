package com.common.mvp;

import android.app.Activity;
import android.content.Context;

import com.common.mvp.annotation.CallOnce;
import com.common.mvp.model.BaseMvpModel;
import com.common.mvp.view.BaseMvpView;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.List;


public abstract class BaseMvpActivity extends RxAppCompatActivity {

    private BridgeNode mBridgeNode;

    @CallOnce
    public BridgeNode createBridgeNode() {
        mBridgeNode = new BridgeNode(null);

        newRoute(new INewViewRoute<BaseMvpView>() {

            @Override
            public BaseMvpView call(Class<BaseMvpView> bClass) {
                try {
                    return bClass.getConstructor(Activity.class).newInstance(BaseMvpActivity.this);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        newModel(new INewModelRoute<BaseMvpModel>() {
            @Override
            public BaseMvpModel call(Class<BaseMvpModel> clazz) {
                try {
                    return clazz.getConstructor(Context.class).newInstance(BaseMvpActivity.this);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return mBridgeNode;
    }

    protected void newRoute(IRoute<?, ?> route) {
        if (mBridgeNode != null) {
            mBridgeNode.newRoute(route);
        }
    }

    protected void newModel(IRoute<?, ?> route) {
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

    @Override
    protected void onDestroy() {
        if (mBridgeNode != null) {
            mBridgeNode.release();
        }
        super.onDestroy();
    }
}

