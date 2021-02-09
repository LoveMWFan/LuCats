package com.common.mvp.model;

import android.content.Context;

import com.common.mvp.contract.IMvpContract;

public abstract class BaseMvpModel implements IMvpContract.IMvpModel {
    protected Context mContext;

    public BaseMvpModel(Context context) {
        mContext = context;
    }

    @Override
    public void detachPresenter() {
        mContext = null;
    }
}
