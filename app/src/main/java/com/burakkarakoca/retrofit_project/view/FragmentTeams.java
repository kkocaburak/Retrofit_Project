package com.burakkarakoca.retrofit_project.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.adapter.TeamRecyclerAdapter;
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

public class FragmentTeams extends Fragment {

    ArrayList<TeamModel> teamNameFromRetrofit;
    RecyclerView recyclerView;
    public static TeamRecyclerAdapter teamRecyclerAdapter;
    String itemID;
    public static String teamItemId;
    public static String teamItemName;


    // #####################  Bu sayfalarda neden string ile get(), set() yapamıyorum?

    public void setItemId(String string){
        itemID = string;
    }

    public FragmentTeams() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // ViewGroup oluşturulup tasarlanan xml dosyası çekildi.
        // Bu işlemin ardından xml dosyası içerisindeki tüm objelere ulaşım sağlanabilir.
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_teams, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_teams_recyclerView);

        getTeamsFromRetrofit();
    }

    public void getTeamsFromRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainAPI mainAPI = retrofit.create(MainAPI.class);
        final Call<Team> callTeams = mainAPI.getTeams("lookup_all_teams.php?id="+itemID);

        callTeams.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(final Call<Team> call, Response<Team> response) {
                List<TeamModel> responseList = response.body().teams;
                teamNameFromRetrofit = new ArrayList<>(responseList);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                teamRecyclerAdapter = new TeamRecyclerAdapter(teamNameFromRetrofit);
                // buradaki recyclerView fragmentten çekilecek
                recyclerView.setAdapter(teamRecyclerAdapter);
                MainActivity.mainText.setText("Teams");

                // On Item Click
                teamRecyclerAdapter.setOnClickListener(new TeamRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        Toast.makeText(getContext(),"Pozisyon: "+position,Toast.LENGTH_SHORT).show();
                        System.out.println(teamNameFromRetrofit.get(position).strTeam);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        FragmentTeamInformations fragmentTeamInformations = new FragmentTeamInformations();

                        fragmentTeamInformations.setItemId(teamNameFromRetrofit.get(position).idLeague);
                        fragmentTeamInformations.setItemName(teamNameFromRetrofit.get(position).strTeam);
                        teamItemId = teamNameFromRetrofit.get(position).idLeague;
                        teamItemName = teamNameFromRetrofit.get(position).strTeam;

                        fragmentTransaction.replace(R.id.frame_layout, fragmentTeamInformations,"Fragment Team Info");
                        MainActivity.fragmentAdded++;
                        fragmentTransaction.commit();

                    }
                });

            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {

            }

        });

    }


}
