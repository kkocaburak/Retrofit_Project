package com.burakkarakoca.retrofit_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.model.SportModel;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.RowHolder> {

    private ArrayList<SportModel> sportName;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MainRecyclerAdapter(ArrayList<SportModel> sportName) {
        this.sportName = sportName;
    }

    // Oluşturulunca yapılacaklar
    // Bizim recyclerView'imiz ile oluşturduğumuz recycler_sport_itemrt_item.xml dosyasını bağlamak için kullanılır.
    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // recyclerView içerisinde gösterilecek olan xml dosyasını çeker.
        View view = layoutInflater.inflate(R.layout.recycler_sport_item,parent,false);

        return new RowHolder(view, mListener);

    }

    // Bağlanınca yapılacaklar
    // recyclerView'e oluşturduğumu recycler_sport_item.xmltem.xml dosyası bağlanınca yapılacaklar.
    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(sportName.get(position),position);
    }

    @Override
    public int getItemCount() {
        return sportName.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public RowHolder(@NonNull View itemView, final OnItemClickListener listener) {
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

        public void bind(SportModel sportModel, Integer position){

            textView = itemView.findViewById(R.id.recycler_view_textView);

            textView.setText(sportModel.strSport);


        }

    }



}
