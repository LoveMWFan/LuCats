package ma.bay.com.labase.common.mvp;


import com.common.mvp.contract.IMvpContract;
import com.common.mvp.presenter.BaseMvpPresenter;

public abstract class LAMvpPresenter<M extends IMvpContract.IMvpModel, V extends IMvpContract.IMvpView> extends BaseMvpPresenter<M, V> implements IMvpContract.IMvpPresenter<M, V> {
}
