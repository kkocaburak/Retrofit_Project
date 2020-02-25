package com.burakkarakoca.retrofit_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.model.TeamModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.RowHolder> {

    private ArrayList<TeamModel> teamName;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public TeamRecyclerAdapter(ArrayList<TeamModel> teamName) {
        this.teamName = teamName;
    }

    // Oluşturulunca yapılacaklar
    // Bizim recyclerView'imiz ile oluşturduğumuz recycler_sport_item.xmltem.xml dosyasını bağlamak için kullanılır.
    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // recyclerView içerisinde gösterilecek olan xml dosyasını çeker.
        View view = layoutInflater.inflate(R.layout.recycler_text_image,parent,false);

        return new RowHolder(view, mListener);

    }

    // Bağlanınca yapılacaklar
    // recyclerView'e oluşturduğumu recycler_sport_itemrt_item.xml dosyası bağlanınca yapılacaklar.
    @Override
    public void onBindViewHolder(@NonNull TeamRecyclerAdapter.RowHolder holder, int position) {
        holder.bind(teamName.get(position),position);
    }

    @Override
    public int getItemCount() {
        return teamName.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public RowHolder(@NonNull View itemView, final TeamRecyclerAdapter.OnItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }
                }
            });

        }

        public void bind(TeamModel teamModel, Integer position){

            textView = itemView.findViewById(R.id.recycler_text_image_textView);
            textView.setText(teamModel.strTeam);

            imageView = itemView.findViewById(R.id.recycler_text_image_imageView);
            Picasso.get().load(teamModel.strTeamBadge).into(imageView);

        }

    }



}
