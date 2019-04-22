package br.com.ufmg.imagensupload.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageCallBack<T> implements Callback<T> {

    private CallBackReceiver<T> callBackReceiver;

    public ImageCallBack(CallBackReceiver<T> callBackReceiver) {
        this.callBackReceiver = callBackReceiver;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        callBackReceiver.onResult(response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callBackReceiver.onErrorResult(t);
    }
}
