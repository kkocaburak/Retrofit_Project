package com.burakkarakoca.retrofit_project.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burakkarakoca.retrofit_project.R;
import com.burakkarakoca.retrofit_project.adapter.LeagueRecyclerAdapter;
import com.burakkarakoca.retrofit_project.model.League;
import com.burakkarakoca.retrofit_project.model.LeagueModel;
import com.burakkarakoca.retrofit_project.service.MainAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentLeagues extends Fragment {

    RecyclerView recyclerView;
    LeagueRecyclerAdapter leagueRecyclerAdapter;

    String itemName;

    ArrayList<LeagueModel> Ligler = new ArrayList<>();
    ArrayList<LeagueModel> leagueNameFromRetrofit;

    public static String leagueString;


    public FragmentLeagues() {
        // Required empty public constructor
    }

    public void setItemName(String string){
        itemName = string;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_leagues, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_leagues_recyclerView);

        getLeaguesFromRetrofit(itemName);
    }

    public void getLeaguesFromRetrofit(final String sportName){
        Retrofit retrofit_2 = new Retrofit.Builder()
                .baseUrl(MainAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainAPI mainAPI = retrofit_2.create(MainAPI.class);
        Call<League> callLeague = mainAPI.getLeagues();

        // Çekilen xml dosyasındaki textView çekilip işlendi.


        callLeague.enqueue(new Callback<League>() {
            @Override
            public void onResponse(Call<League> call, Response<League> response) {
                List<LeagueModel> responseList = response.body().leagues;
                leagueNameFromRetrofit = new ArrayList<>(responseList);

                for(LeagueModel league : leagueNameFromRetrofit){

                    if(league.getStrSport().equals(sportName) ){
                        Ligler.add(league);
                    }

                }


                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                leagueRecyclerAdapter = new LeagueRecyclerAdapter(Ligler);

                recyclerView.setAdapter(leagueRecyclerAdapter);
                MainActivity.mainText.setText("Leagues");

                // On Item Click
                leagueRecyclerAdapter.setOnClickListener(new LeagueRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getContext(),"Pozisyon: "+position,Toast.LENGTH_SHORT).show();
                        System.out.println(Ligler.get(position).strSport);
                        // #### sportNameFromRetrofit.get(position).strSport = İstenen spor ismi ####

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        FragmentTeams fragmentTeams = new FragmentTeams();
                        System.out.println(Ligler.get(position).idLeague);
                        fragmentTeams.setItemId(Ligler.get(position).idLeague);
                        leagueString = Ligler.get(position).idLeague;

                        fragmentTransaction.replace(R.id.frame_layout, fragmentTeams,"Fragment Teams");
                        MainActivity.fragmentAdded++;
                        fragmentTransaction.commit();

                    }
                });

            }

            @Override
            public void onFailure(Call<League> call, Throwable t) {
                // err
            }
        });


    }


}
