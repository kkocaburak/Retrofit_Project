package com.burakkarakoca.retrofit_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.model.SportModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.RowHolder> implements Filterable {

    private ArrayList<SportModel> sportName;
    private ArrayList<SportModel> sportNameFull;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MainRecyclerAdapter(ArrayList<SportModel> sportName) {
        this.sportName = sportName;
        sportNameFull = new ArrayList<>(sportName);
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
        ImageView imageView;

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

            textView = itemView.findViewById(R.id.recycler_sport_textView);
            imageView = itemView.findViewById(R.id.recycler_sport_imageview);

            textView.setText(sportModel.strSport);
            Picasso.get().load(sportModel.strSportThumb).into(imageView);


        }

    }

    @Override
    public Filter getFilter() {
        return sportNameFilter;
    }

    private Filter sportNameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<SportModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(sportNameFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(SportModel item : sportNameFull){
                    if(item.getStrSport().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            sportName.clear();
            sportName.addAll((List) results.values);
            notifyDataSetChanged();

        }

    };



}
