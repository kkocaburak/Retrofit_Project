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
import com.burakkarakoca.retrofit_project.adapter.MainRecyclerAdapter;
import com.burakkarakoca.retrofit_project.model.Sport;
import com.burakkarakoca.retrofit_project.model.SportModel;
import com.burakkarakoca.retrofit_project.service.MainAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSports extends Fragment {

    ArrayList<SportModel> sportNameFromRetrofit;
    RecyclerView recyclerView;
    MainRecyclerAdapter mainRecyclerAdapter;
    public static FragmentLeagues fragmentLeagues;

    public static String sportString;

    // #####################  Bu sayfalarda neden string ile get(), set() yapamıyorum?


    public FragmentSports() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // ViewGroup oluşturulup tasarlanan xml dosyası çekildi.
        // Bu işlemin ardından xml dosyası içerisindeki tüm objelere ulaşım sağlanabilir.
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sports, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_sports_recyclerView);

        getSportsFromRetrofit();
    }

    public void getSportsFromRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainAPI mainAPI = retrofit.create(MainAPI.class);
        final Call<Sport> callsport = mainAPI.getSports();

        callsport.enqueue(new Callback<Sport>() {
            @Override
            public void onResponse(final Call<Sport> call, Response<Sport> response) {
                List<SportModel> responseList = response.body().sports;
                sportNameFromRetrofit = new ArrayList<>(responseList);


                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                mainRecyclerAdapter = new MainRecyclerAdapter(sportNameFromRetrofit);

                recyclerView.setAdapter(mainRecyclerAdapter);


                // On Item Click
                mainRecyclerAdapter.setOnClickListener(new MainRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getContext(),"Pozisyon: "+position,Toast.LENGTH_SHORT).show();
                        System.out.println(sportNameFromRetrofit.get(position).strSport);
                        // #### sportNameFromRetrofit.get(position).strSport = İstenen spor ismi ####
                        fragmentLeagues = new FragmentLeagues();

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentLeagues.setItemName(sportNameFromRetrofit.get(position).strSport);
                        sportString = sportNameFromRetrofit.get(position).strSport;

                        fragmentTransaction.replace(R.id.frame_layout, fragmentLeagues,"Fragment League").commit();
                        MainActivity.fragmentAdded++;

                    }
                });

            }

            @Override
            public void onFailure(Call<Sport> call, Throwable t) {

            }

        });

    }

    public static void sportBackPressed(){

    }





}
