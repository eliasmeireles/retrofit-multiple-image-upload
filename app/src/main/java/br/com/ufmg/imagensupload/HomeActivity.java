package br.com.ufmg.imagensupload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import br.com.ufmg.imagensupload.controller.HomeActivityController;

public class HomeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_MULTIPLE = 2;
    private HomeActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new HomeActivityController(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> getReadExternalStoragePemission());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int itemCount = data.getClipData().getItemCount();
                List<Uri> imagensUri = new ArrayList<>();
                for (int i = 0; i < itemCount; i++) {
                    imagensUri.add(data.getClipData().getItemAt(i).getUri());
                }
                controller.setImagensUri(imagensUri);
            }
        }
    }

    private void getReadExternalStoragePemission() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permitions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permitions, PICK_IMAGE_MULTIPLE);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                startActivityForResult(galleryIntent, PICK_IMAGE_MULTIPLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
