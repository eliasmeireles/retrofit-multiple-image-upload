package br.com.ufmg.imagensupload.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.ufmg.imagensupload.R;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.ViewHolder> {

    private List<Uri> imagensUri;

    public ImagemAdapter(List<Uri> imagensUri) {
        this.imagensUri = imagensUri;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.imagem_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(this.imagensUri.get(position));
    }

    @Override
    public int getItemCount() {
        return this.imagensUri.size();
    }

    public void replaceItems(List<Uri> imagensUri) {
        this.imagensUri = imagensUri;
        super.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        void bind(Uri uri) {
            ImageView imageView = itemView.findViewById(R.id.imagem);

            Glide.with(itemView.getContext())
                    .load(uri)
                    .into(imageView);
        }
    }
}
