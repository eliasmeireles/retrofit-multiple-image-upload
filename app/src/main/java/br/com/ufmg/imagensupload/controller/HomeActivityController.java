package br.com.ufmg.imagensupload.controller;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufmg.imagensupload.HomeActivity;
import br.com.ufmg.imagensupload.R;
import br.com.ufmg.imagensupload.adapter.ImagemAdapter;
import br.com.ufmg.imagensupload.callback.CallBackReceiver;
import br.com.ufmg.imagensupload.callback.ImageCallBack;
import br.com.ufmg.imagensupload.file.FileManager;
import br.com.ufmg.imagensupload.payload.UploadFileResponse;
import br.com.ufmg.imagensupload.retrofit.RetrofitInitializer;
import br.com.ufmg.imagensupload.retrofit.service.ImageService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class HomeActivityController implements CallBackReceiver<List<UploadFileResponse>> {

    private HomeActivity homeActivity;
    private RecyclerView recyclerView;
    private ImagemAdapter imagemAdapter;
    private List<Uri> imagensUri;

    public HomeActivityController(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        recyclerViewInite();
        homeActivity.findViewById(R.id.buttom_salvar_imagens).setOnClickListener(view -> {
            if (this.imagensUri.size() > 0) saveImages();
        });
    }

    public void setImagensUri(List<Uri> imagensUri) {
        this.imagensUri = imagensUri;
        imagemAdapter.replaceItems(imagensUri);
    }

    private void saveImages() {
        List<MultipartBody.Part> partList = new ArrayList<>();
        FileManager fileManager = new FileManager(homeActivity);
        for (Uri uri : this.imagensUri) {
            try {
                MultipartBody.Part formData = builtMultpartBody(fileManager, uri);
                partList.add(formData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        postImages(partList);
    }

    private MultipartBody.Part builtMultpartBody(FileManager fileManager, Uri uri) throws IOException {
        File imageOnCashDir = fileManager.createImageOnCashDir(String.valueOf(System.currentTimeMillis()), uri);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageOnCashDir);
        return MultipartBody.Part.createFormData("files", imageOnCashDir.getName(), requestBody);
    }

    private void postImages(List<MultipartBody.Part> partList) {
        RetrofitInitializer retrofitInitializer = new RetrofitInitializer();
        ImageService imageService = retrofitInitializer.retrofit.create(ImageService.class);
        ImageCallBack callback = new ImageCallBack(this);
        imageService.postMulplie(partList).enqueue(callback);
    }


    private void recyclerViewInite() {
        this.imagensUri = new ArrayList<>();
        this.recyclerView = homeActivity.findViewById(R.id.recycler_view_imagens_selecionadas);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(homeActivity, RecyclerView.HORIZONTAL, false));
        this.imagemAdapter = new ImagemAdapter(imagensUri);
        this.recyclerView.setAdapter(imagemAdapter);
    }


    @Override
    public void onResult(Response<List<UploadFileResponse>> data) {
        Toast.makeText(homeActivity, data.body().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResult(Throwable t) {
        t.printStackTrace();
    }
}