package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MoviesFragment.OnFragmentInteractionListener, TvFragment.OnFragmentInteractionListener {


    private final int MOVIE_TYPE = 1;
    private final int TV_TYPE = 2;
    MoviesFragment moviesFragment;
    TvFragment tvFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //TODO on boarding screen

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //finding device resolution size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("dimension","h:"+height+" w:"+width);
        //sp
        openFragment(MOVIE_TYPE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,ViewAllActivity.class);
            intent.putExtra("url","popular");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_movies) {
            openFragment(MOVIE_TYPE);
        } else if (id == R.id.nav_tv) {
            openFragment(TV_TYPE);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this,DetailsScrollingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void openFragment(int type){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(type==MOVIE_TYPE){
            moviesFragment =MoviesFragment.newInstance(null,null);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,moviesFragment);
            fragmentTransaction.commit();
        }else if(type==TV_TYPE){
            tvFragment =TvFragment.newInstance(null,null);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,tvFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void seeAll(View view){
        int id = view.getId();
        Intent intent = new Intent(this,ViewAllActivity.class);
        if(id==R.id.popularTextView){
            intent.putExtra("url","popular");
        }else if(id==R.id.topRated2TextView){
            intent.putExtra("url","topRated");
        }else if(id==R.id.upcoming2TextView){
            intent.putExtra("url","upcoming");
        }else if(id==R.id.nowShowingTextView2){
            intent.putExtra("url","nowShowing");
        }else if(id==R.id.topRated22TextView){
            intent.putExtra("url","tvTopRated");
        }else if(id==R.id.airingToday2TextView){
            intent.putExtra("url","airingToday");
        }else if(id==R.id.onTheAir2TextView){
            intent.putExtra("url","onTheAir");
        }else if(id==R.id.popular22TextView){
            intent.putExtra("url","tvPopular");
        }
        startActivity(intent);
    }
}
