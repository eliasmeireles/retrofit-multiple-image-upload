package br.com.ufmg.imagensupload.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    public File createImageOnCashDir(String imageName, Uri imageUri) throws IOException {
        File cashImage = new File(context.getCacheDir().getPath(), imageName.concat(".jpg"));
        FileOutputStream outputStream = new FileOutputStream(cashImage);
        ParcelFileDescriptor descriptor = context.getContentResolver().openFileDescriptor(imageUri, "r");

        if (descriptor != null) {
            FileDescriptor fileDescriptor = descriptor.getFileDescriptor();
            Bitmap imageBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            descriptor.close();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);
        }
        outputStream.flush();
        outputStream.close();
        return cashImage;
    }
}
