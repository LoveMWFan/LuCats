package com.common.mvp.model;

import android.content.Context;

public abstract class BaseMvpModel implements IMvpModel {
    protected Context mContext;

    public BaseMvpModel(Context context) {
        mContext = context;
    }

    @Override
    public void detachPresenter() {
        mContext = null;
    }
}
