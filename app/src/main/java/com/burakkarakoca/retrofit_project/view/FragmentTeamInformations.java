package com.burakkarakoca.retrofit_project.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.adapter.TeamInformationRecyclerAdapter;
import com.burakkarakoca.retrofit_project.model.SportModel;
import com.burakkarakoca.retrofit_project.model.Team;
import com.burakkarakoca.retrofit_project.model.TeamModel;
import com.burakkarakoca.retrofit_project.service.MainAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentTeamInformations extends Fragment {

    ArrayList<TeamModel> teamNameFromRetrofit;

    RecyclerView recyclerView;
    public static TeamInformationRecyclerAdapter teamInformationRecyclerAdapter;
    String itemName, itemId;

    ArrayList<TeamModel> Takım = new ArrayList<>();

    public void setItemName(String string){
        itemName = string;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public FragmentTeamInformations() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_team_information, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_teams_recyclerView);

        getTeamsFromRetrofit(itemName,itemId);
    }

    public void getTeamsFromRetrofit(final String item, final String itemId){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainAPI mainAPI = retrofit.create(MainAPI.class);
        final Call<Team> callTeams = mainAPI.getTeams("lookup_all_teams.php?id="+itemId);

        callTeams.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(final Call<Team> call, Response<Team> response) {
                List<TeamModel> responseList = response.body().teams;
                teamNameFromRetrofit = new ArrayList<>(responseList);

                for(TeamModel team : teamNameFromRetrofit){

                    if(team.getStrTeam().equals(item) ){
                        Takım.add(team);
                    }

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                teamInformationRecyclerAdapter = new TeamInformationRecyclerAdapter(Takım);
                // buradaki recyclerView fragmentten çekilecek
                recyclerView.setAdapter(teamInformationRecyclerAdapter);

            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }

        });

    }





}
