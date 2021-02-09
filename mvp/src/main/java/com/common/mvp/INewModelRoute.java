package com.common.mvp;

import com.common.mvp.model.BaseMvpModel;

public interface INewModelRoute<T extends BaseMvpModel> extends IRoute<Class<T>, T> {
}
