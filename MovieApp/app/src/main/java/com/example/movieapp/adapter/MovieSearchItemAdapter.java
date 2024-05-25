package com.example.movieapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.fragment.MovieDetailFragment;
import com.example.movieapp.model.Item;

import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieSearchItemAdapter extends RecyclerView.Adapter<MovieSearchItemAdapter.MovieSearchItemAdapterHolder> {
    List<Item> listItem;
    Context context;
    String domainimage;
    public MovieSearchItemAdapter(List<Item> listItem, Context context, String domainimage) {
        this.listItem = listItem;
        this.context = context;
        this.domainimage = domainimage;
    }
    @NonNull
    @Override
    public MovieSearchItemAdapter.MovieSearchItemAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_image_item, parent, false);
        return new MovieSearchItemAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchItemAdapter.MovieSearchItemAdapterHolder holder, int position) {
        Item item = listItem.get(position);

        holder.tvName.setText(item.getName());

        //String imageUrl = domainimage + "/" + item.getPoster_url();
        //Picasso.get().load(imageUrl).into(holder.imvMovieItem);

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Item " + item.getName() + " clicked", Toast.LENGTH_SHORT).show();

                // Khởi tạo Fragment và truyền dữ liệu nếu cần
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

    public static class MovieSearchItemAdapterHolder extends RecyclerView.ViewHolder {
        //ImageView imvMovieItem;
        TextView tvName;
        LinearLayout itemLayout;

        public MovieSearchItemAdapterHolder(@NonNull View itemView) {
            super(itemView);
            //imvMovieItem = itemView.findViewById(R.id.imvMovieItem);
            tvName = itemView.findViewById(R.id.tvName);
            itemLayout = itemView.findViewById(R.id.itemLayoutSearch);
        }
    }
}
