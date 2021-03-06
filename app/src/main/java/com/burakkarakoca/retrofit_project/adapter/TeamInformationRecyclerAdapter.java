package com.burakkarakoca.retrofit_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.model.TeamModel;
import com.burakkarakoca.retrofit_project.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class TeamInformationRecyclerAdapter extends RecyclerView.Adapter<TeamInformationRecyclerAdapter.RowHolder>{

    private ArrayList<TeamModel> teamName;


    private OnItemClickListener mListener;

    String[] imageUrls;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public TeamInformationRecyclerAdapter(ArrayList<TeamModel> teamName) {
        this.teamName = teamName;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // recyclerView içerisinde gösterilecek olan xml dosyasını çeker.
        View view = layoutInflater.inflate(R.layout.recycler_team,parent,false);
        MainActivity.mainText.setText("Team Information");
        return new RowHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TeamInformationRecyclerAdapter.RowHolder holder, int position) {
        holder.bind(teamName.get(position),position);
    }

    @Override
    public int getItemCount() {
        return teamName.size();
    }


    public class RowHolder extends RecyclerView.ViewHolder{

        TextView teamNameText, teamAlternativeNameText, teamFormedYearText, teamStadiumNameText, teamDescriptionText;
        ImageView teamImage;
        ViewPager viewPager;

        public RowHolder(@NonNull View itemView, final TeamInformationRecyclerAdapter.OnItemClickListener listener) {
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

            teamNameText = itemView.findViewById(R.id.recycler_team_teamName_text);
            teamAlternativeNameText = itemView.findViewById(R.id.recycler_team_alternateName_text);
            teamFormedYearText = itemView.findViewById(R.id.recycler_team_formedYear_text);
            teamStadiumNameText = itemView.findViewById(R.id.recycler_team_stadiumName_text);
            teamDescriptionText = itemView.findViewById(R.id.recycler_team_description_text);

            teamImage = itemView.findViewById(R.id.recycler_team_teamImage_image);

            if(teamModel.strTeam != null && !teamModel.strTeam.isEmpty()){
                teamNameText.setText(teamModel.strTeam);
            } else{
                teamNameText.setVisibility(View.GONE);
            }

            if(teamModel.strAlternate != null && !teamModel.strAlternate.isEmpty() && teamModel.strTeam != teamModel.strAlternate){
                teamAlternativeNameText.setText(teamModel.strAlternate);
            } else{
                teamAlternativeNameText.setVisibility(View.GONE);
            }


            if(teamModel.intFormedYear != null){
                teamFormedYearText.setText(""+teamModel.intFormedYear);
            } else{
                teamFormedYearText.setVisibility(View.GONE);
            }

            if(teamModel.strDescriptionEN != null && !teamModel.strDescriptionEN.isEmpty()){
                teamDescriptionText.setText(teamModel.strDescriptionEN);
            } else {
                teamDescriptionText.setVisibility(View.GONE);
            }

//            if(teamModel.strStadium != null && !teamModel.strStadium.isEmpty()){
//                teamStadiumNameText.setText("  "+teamModel.strStadium+"  ");
//            } else{
//                teamStadiumNameText.setVisibility(View.GONE);
//            }

            Picasso.get().load(teamModel.strTeamBadge).into(teamImage);

            ArrayList<String> mylist = new ArrayList<String>();

            if(teamModel.strTeamFanart1 != null){
                mylist.add(teamModel.strTeamFanart1);
            }
            if(teamModel.strTeamFanart2 != null){
                mylist.add(teamModel.strTeamFanart2);
            }
            if(teamModel.strTeamFanart3 != null){
                mylist.add(teamModel.strTeamFanart3);
            }
            if(teamModel.strTeamFanart4 != null){
                mylist.add(teamModel.strTeamFanart4);
            }

            ViewPager viewPager = itemView.findViewById(R.id.view_pager);
//            TabLayout tabLayout = itemView.findViewById(R.id.tabDots);
//            tabLayout.setupWithViewPager(viewPager, true);


            if(mylist.size() <= 0){
                viewPager.setVisibility(View.GONE);
            } else{
                imageUrls = new String[mylist.size()];

                for(int i = 0; i<mylist.size(); i++){
                    imageUrls[i] = mylist.get(i);
                }

                ViewPagerAdapter adapter = new ViewPagerAdapter(itemView.getContext(), imageUrls);
                viewPager.setAdapter(adapter);
                CircleIndicator indicator = itemView.findViewById(R.id.circle_indicator);
                indicator.setViewPager(viewPager);

            }



        }

    }


}
