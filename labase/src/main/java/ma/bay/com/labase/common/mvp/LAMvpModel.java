package ma.bay.com.labase.common.mvp;

import android.content.Context;
import android.content.res.Resources;

import com.common.mvp.contract.IMvpContract;
import com.common.mvp.model.BaseMvpModel;


public abstract class LAMvpModel extends BaseMvpModel implements IMvpContract.IMvpModel {

    public LAMvpModel(Context context) {
        super(context);
    }

    public Resources getResources() {
        return mContext.getResources();
    }
}
