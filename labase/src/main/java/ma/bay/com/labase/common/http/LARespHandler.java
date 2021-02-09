package ma.bay.com.labase.common.http;


import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import ma.bay.com.labase.common.http.exception.HttpRespException;
import ma.bay.com.labase.common.http.exception.LARespException;
import ma.bay.com.labase.common.http.exception.NetworkRespException;
import ma.bay.com.labase.common.http.exception.RespException;
import ma.bay.com.labase.common.http.exception.UnknownRespException;
import ma.bay.com.labase.common.module.ErrorMsg;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class LARespHandler<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
    }

    @Override
    final public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    final public void onError(Throwable throwable) {
        if (throwable instanceof ConnectException || throwable instanceof UnknownHostException || throwable instanceof IOException) {
            // 网络连接错误
            onFailure(new NetworkRespException(throwable.getMessage()));
        } else if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            int httpCode = exception.code();
            String msg = exception.message();
            if (httpCode == 401) {
                onAuthenticationFailure();
            } else {
                HttpRespException httpRespException = new HttpRespException(httpCode, msg);
                try {
                    ErrorMsg errorMsg = Model.fromJson(new String(exception.response().errorBody().bytes()), ErrorMsg.class);
                    httpRespException.setMsg(errorMsg.msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onFailure(httpRespException);
            }
        } else if (throwable instanceof LARespException) {
            // 自定义错误
            onFailure((LARespException) throwable);

        } else {
            onFailure(new UnknownRespException(throwable.getMessage()));
        }
    }

    public boolean isDataError(RespException e) {
        return e != null && e instanceof LARespException;
    }

    public boolean isDataError1(RespException e) {
        return isDataError(e) && ((LARespException) e).getStatusCode() == 1;
    }

    public boolean isDataError404(RespException e) {
        return isDataError(e) && ((LARespException) e).getStatusCode() == 404;
    }

    public boolean isDataError409(RespException e) {
        return isDataError(e) && ((LARespException) e).getStatusCode() == 409;
    }

    public boolean isDataError401(RespException e) {
        return isDataError(e) && ((LARespException) e).getStatusCode() == 401;
    }

    public boolean isDataError403(RespException e) {
        return isDataError(e) && ((LARespException) e).getStatusCode() == 403;
    }

    public void onSuccess(T data) {

    }

    public void onFailure(RespException e) {

    }

    public void onAuthenticationFailure() {

    }
}
