package com.common.mvp;

import com.common.mvp.view.BaseMvpView;

public interface INewViewRoute<T extends BaseMvpView> extends IRoute<Class<T>, T> {

}
