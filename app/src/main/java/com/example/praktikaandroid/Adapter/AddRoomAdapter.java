package com.example.praktikaandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
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

public class AddRoomAdapter extends RecyclerView.Adapter <AddRoomAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<AddRoom> addRooms;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick (AddRoom addRoom);
    }

    public AddRoomAdapter(Context context, List<AddRoom> addRooms, OnItemClickListener onItemClickListener){
        this.inflater = LayoutInflater.from(context);
        this.addRooms = addRooms;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AddRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.addroom_list_item,parent,false);
        return new ViewHolder(view);
    }

    TextView LastTextView =  null;
    CardView LastCardView = null;
    boolean flag=false;
    @Override
    public void onBindViewHolder(@NonNull AddRoomAdapter.ViewHolder holder, int position) {
        AddRoom addRoom = addRooms.get(position);
        holder.textViewRoom.setText(addRoom.getTitle());
        holder.imageViewRoom.setImageResource(addRoom.getImage());
        holder.cardViewRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(addRoom);

                holder.cardViewRoom.setSelected(!holder.cardViewRoom.isSelected());
                if(holder.cardViewRoom.isSelected()) {
                    if(flag) {
                        LastCardView.setSelected(false);
                        LastCardView.setCardBackgroundColor(Color.parseColor("#F0F0F0"));
                        LastTextView.setSelected(false);
                    }
                    holder.cardViewRoom.setCardBackgroundColor(Color.parseColor("#984E4F"));
                    holder.textViewRoom.setSelected(true);
                }
                else
                {
                    holder.cardViewRoom.setCardBackgroundColor(Color.parseColor("#F0F0F0"));
                    holder.textViewRoom.setSelected(false);
                }
                LastCardView = holder.cardViewRoom;
                LastTextView = holder.textViewRoom;
                flag=true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return addRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewRoom;
        ImageView imageViewRoom;
        TextView textViewRoom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewRoom = itemView.findViewById(R.id.cardViewRoom);
            imageViewRoom = itemView.findViewById(R.id.imageViewRoom);
            textViewRoom = itemView.findViewById(R.id.textViewRoomTitle);
        }
    }
}
