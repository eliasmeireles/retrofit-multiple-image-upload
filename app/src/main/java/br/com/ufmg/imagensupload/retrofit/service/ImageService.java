package br.com.ufmg.imagensupload.retrofit.service;

import java.util.List;

import br.com.ufmg.imagensupload.payload.UploadFileResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageService {

    @Multipart
    @POST("coltec/public/media/upload/files")
    Call<List<UploadFileResponse>> postMulplie(@Part List<MultipartBody.Part> imagens);

}
