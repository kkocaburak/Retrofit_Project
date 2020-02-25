package com.burakkarakoca.retrofit_project.service;

import com.burakkarakoca.retrofit_project.model.League;
import com.burakkarakoca.retrofit_project.model.Sport;
import com.burakkarakoca.retrofit_project.model.Team;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MainAPI {

    String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/";

    @GET("all_sports.php")
    Call<Sport> getSports();

    @GET("all_leagues.php")
    Call<League> getLeagues();

    @GET
    Call<Team> getTeams(@Url String url);

//    @GET("lookup_all_teams.php?id="+LEAGUE_ID)
//    Call<Team> getTeams();


}
