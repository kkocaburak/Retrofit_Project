package com.burakkarakoca.retrofit_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.model.LeagueModel;

import java.util.ArrayList;
import java.util.List;

public class LeagueRecyclerAdapter extends RecyclerView.Adapter<LeagueRecyclerAdapter.RowHolder> implements Filterable {

    private ArrayList<LeagueModel> leagueName;
    private ArrayList<LeagueModel> leagueNameFull;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public LeagueRecyclerAdapter(ArrayList<LeagueModel> leagueName) {
        this.leagueName = leagueName;
        leagueNameFull = new ArrayList<>(leagueName);
    }

    // Oluşturulunca yapılacaklar
    // Bizim recyclerView'imiz ile oluşturduğumuz recycler_sport_itemrt_item.xml dosyasını bağlamak için kullanılır.
    @NonNull
    @Override
    public LeagueRecyclerAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_league_item,parent,false);

        return new LeagueRecyclerAdapter.RowHolder(view, mListener);
    }


    // Bağlanınca yapılacaklar
    // recyclerView'e oluşturduğumu recycler_sport_item.xmltem.xml dosyası bağlanınca yapılacaklar.
    @Override
    public void onBindViewHolder(@NonNull LeagueRecyclerAdapter.RowHolder holder, int position) {
        holder.bind(leagueName.get(position),position);
    }

    @Override
    public int getItemCount() {
        return leagueName.size();
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

        public void bind(LeagueModel leagueModel, Integer position){

            textView = itemView.findViewById(R.id.recycler_sport_textView);
            textView.setText(leagueModel.strLeague);

        }

    }

    @Override
    public Filter getFilter() {
        return leagueNameFilter;
    }

    private Filter leagueNameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<LeagueModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(leagueNameFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(LeagueModel item : leagueNameFull){
                    if(item.getStrLeague().toLowerCase().contains(filterPattern)){
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

            leagueName.clear();
            leagueName.addAll((List) results.values);
            notifyDataSetChanged();

        }

    };

}
