package com.example.praktikaandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikaandroid.R;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter <HomePageAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<HomePage> homepages;
    onItemClickListener onItemClickListener;


    public interface onItemClickListener {
        void onItemClick(HomePage homepage, String title);
    }

    public HomePageAdapter(Context context, List<HomePage> homepages, onItemClickListener onItemClickListener){
        this.inflater = LayoutInflater.from(context);
        this.homepages = homepages;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomePageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homepage_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageAdapter.ViewHolder holder, int position) {
        HomePage homePage = homepages.get(position);
        holder.imageViewHomePageItem.setImageResource(homePage.getImage());
        holder.textViewTitle.setText(homePage.getTitle());
        holder.textViewInfo.setText(homePage.getInfo());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(homePage, homePage.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return homepages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewHomePageItem;
        TextView textViewTitle, textViewInfo;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewHomePageItem = itemView.findViewById(R.id.imageViewHomePageItem);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewInfo = itemView.findViewById(R.id.textViewInfo);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
