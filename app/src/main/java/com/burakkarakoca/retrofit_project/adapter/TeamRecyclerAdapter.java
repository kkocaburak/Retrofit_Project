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
import com.burakkarakoca.retrofit_project.model.TeamModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.RowHolder> implements Filterable {

    private ArrayList<TeamModel> teamName;
    private ArrayList<TeamModel> teamNameFull;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public TeamRecyclerAdapter(ArrayList<TeamModel> teamName) {
        this.teamName = teamName;
        teamNameFull = new ArrayList<>(teamName);
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

    @Override
    public Filter getFilter() {
        return teamNameFilter;
    }

    private Filter teamNameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<TeamModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(teamNameFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(TeamModel item : teamNameFull){
                    if(item.getStrTeam().toLowerCase().contains(filterPattern)){
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

            teamName.clear();
            teamName.addAll((List) results.values);
            notifyDataSetChanged();

        }

    };



}
