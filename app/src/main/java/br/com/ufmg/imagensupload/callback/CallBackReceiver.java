package br.com.ufmg.imagensupload.callback;

import retrofit2.Response;

public interface CallBackReceiver<T> {
    void onResult(Response<T> data);
    void onErrorResult(Throwable t);
}
