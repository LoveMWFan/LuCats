package com.poney.lucats.biz.cat.home.contract;


import ma.bay.com.labase.common.mvp.ILAMvpContract;

/**
 * @author feibiao.ma
 * @project LuCats
 * @package_name com.poney.lucats.biz.cat.home.contract
 * @date 2021/2/9
 */
public interface IHomeContract {
    interface Model extends ILAMvpContract.ILAMvpModel {
    }

    interface View extends ILAMvpContract.ILAMvpView {
    }

    interface Presenter extends ILAMvpContract.ILAMvpPresenter {
        void init();
    }
}
