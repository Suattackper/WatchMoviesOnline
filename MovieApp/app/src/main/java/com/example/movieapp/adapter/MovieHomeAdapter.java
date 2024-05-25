package com.example.movieapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.fragment.MovieDetailFragment;
import com.example.movieapp.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieHomeAdapter extends RecyclerView.Adapter<MovieHomeAdapter.MovieHomeAdapterHolder> {
    List<Item> listItem;
    Context context;

    public MovieHomeAdapter(List<Item> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHomeAdapter.MovieHomeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_imagedoc_item, parent, false);
        return new MovieHomeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHomeAdapter.MovieHomeAdapterHolder holder, int position) {
        Item item = listItem.get(position);

        String imageUrl = item.getPoster_url();
        Picasso.get().load(imageUrl).into(holder.imvMovieItem);

        holder.imvMovieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieDetailFragment fragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("slug", item.getSlug());
                fragment.setArguments(bundle);

                // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
                transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
                transaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MovieHomeAdapterHolder extends RecyclerView.ViewHolder {
        ImageView imvMovieItem;
        //TextView tvName;
        LinearLayout itemLayout;
        public MovieHomeAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imvMovieItem = itemView.findViewById(R.id.imvMovieItem);
            //tvName = itemView.findViewById(R.id.tvName);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
