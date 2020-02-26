package com.burakkarakoca.retrofit_project.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.burakkarakoca.retrofit_project.R;

public class MainActivity extends AppCompatActivity {

    public static int fragmentAdded = -1;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public FragmentSports sportFragment;
    public static TextView mainText;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firstFragment();

        mainText = findViewById(R.id.app_main_text);
        mainText.setText("Sport App");

    }

    public void firstFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        sportFragment = new FragmentSports();
        // frame_layout id'sine sahip Frame Layout'una firstFragment konuldu.
        fragmentTransaction.replace(R.id.frame_layout, sportFragment,"Fragment Sport").commit();
        fragmentAdded++;
    }

    @Override
    public void onBackPressed() {

        if(fragmentAdded == 0){
            super.onBackPressed();
        } else if(fragmentAdded == 1){
            fragmentAdded--;
            fragmentAdded--;
            mainText.setText("Sport App");
            firstFragment();
            // Fragment Sport'u sil
        } else if(fragmentAdded == 2){
            fragmentAdded--;
            FragmentLeagues fragmentLeagues = new FragmentLeagues();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentLeagues.setItemName(FragmentSports.sportString);

            fragmentTransaction.replace(R.id.frame_layout, fragmentLeagues,"Fragment League").commit();

        } else if(fragmentAdded == 3){
            fragmentAdded--;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FragmentTeams fragmentTeams = new FragmentTeams();
            fragmentTeams.setItemId(FragmentLeagues.leagueString);

            fragmentTransaction.replace(R.id.frame_layout, fragmentTeams,"Fragment Teams").commit();

        } else if(fragmentAdded == 4){
            fragmentAdded--;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FragmentTeamInformations fragmentTeamInformations = new FragmentTeamInformations();

            fragmentTeamInformations.setItemId(FragmentTeams.teamItemId);
            fragmentTeamInformations.setItemName(FragmentTeams.teamItemName);

            fragmentTransaction.replace(R.id.frame_layout, fragmentTeamInformations,"Fragment Team Info");
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.searchitem);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(fragmentAdded == 0){
                    FragmentSports.mainRecyclerAdapter.getFilter().filter(newText);
                } else if(fragmentAdded == 1){
                    FragmentLeagues.leagueRecyclerAdapter.getFilter().filter(newText);
                }else if(fragmentAdded == 2){
                    FragmentTeams.teamRecyclerAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.searchitem:
                Toast.makeText(getApplicationContext(),"search button",Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                onBackPressed();
            default:return super.onOptionsItemSelected(item);

        }

    }

}
