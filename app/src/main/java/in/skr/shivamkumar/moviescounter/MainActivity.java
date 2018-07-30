package in.skr.shivamkumar.moviescounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MoviesFragment.OnFragmentInteractionListener, TvFragment.OnFragmentInteractionListener,
        MoviesFragmentNowShowing.OnFragmentInteractionListener, MoviesFragmentPopular.OnFragmentInteractionListener,MoviesFragmentTopRated.OnFragmentInteractionListener,
        MoviesFragmentUpcoming.OnFragmentInteractionListener{


    private final int MOVIE_TYPE = 1;
    private final int TV_TYPE = 2;
    MoviesFragment moviesFragment;
    TvFragment tvFragment;
    public static HashMap<Long,String> map;
    HashMap<Long,Boolean> favorite;
    SharedPreferences sharedPreferences;
    public static boolean isBlackTheme;
    public static boolean isGridLayout;
    public boolean isMovieFragment;
    FragmentStatePagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //TODO on boarding screen
        //TODO  overflow of rectanglar text name (coming over rating's text))
        //TODO youtube view
        //TODO change favourite and star colour
        //TODO extend theme in cast and adapters
        //TODO complete room databast features with storing image of the favourite list
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //tab layout
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.containerViewPager);
        openTabLayout();


        //tab layout

        //stackover flow
//        {
//            ViewPager vp_pages= (ViewPager) findViewById(R.id.viewPageContainer);
//            PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
//            vp_pages.setAdapter(pagerAdapter);
//
//            TabLayout tbl_pages= (TabLayout) findViewById(R.id.tabs);
//            tbl_pages.setupWithViewPager(vp_pages);
//        }

        //finding device resolution size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("dimension","h:"+height+" w:"+width);
        //sp
        addGenre();
        openP();
//        openLinearLayout();
    }

    private void openLinearLayout() {
        FrameLayout frameLayout = findViewById(R.id.container);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);

        if(isMovieFragment)
            openFragment(MOVIE_TYPE);
        else
            openFragment(TV_TYPE);
    }

    private void openP() {

        sharedPreferences=getSharedPreferences("my_shared_pref",MODE_PRIVATE);
        isBlackTheme = sharedPreferences.getBoolean("ISBLACKTHEME",true);
        isGridLayout = sharedPreferences.getBoolean("ISGRIDLAYOUT",false);
        isMovieFragment = sharedPreferences.getBoolean("ISMOVIEFRAGMENT",true);

//        if(isGridLayout)
//            openLinearLayout();
//        else
//            openTabLayout();
    }

    private void openTabLayout() {
        FrameLayout frameLayout = findViewById(R.id.container);
        frameLayout.setVisibility(View.GONE);

        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position ==0){
                    //openFragment(MOVIE_TYPE);
                    Toast.makeText(MainActivity.this,"pos 0" ,Toast.LENGTH_SHORT).show();
                    Log.d("position", position+" pos");
                    return new MoviesFragment();
                }
                else if(position == 1){
                    //openFragment(TV_TYPE);
//                    Toast.makeText(MainActivity.this,"pos 12" ,Toast.LENGTH_SHORT).show();
//                    Log.d("position", position+" pos");
//                    return new TvFragment() ;
                    return new MoviesFragmentPopular();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void addGenre() {
        map = new HashMap<>();
        map.put(10759L, "Action & Adventure");
        map.put(16L," Animation");
        map.put(35L, "Comedy");
        map.put(80L, "Crime");
        map.put(99L, "Documentary");
        map.put(18L, "Drama");
        map.put(10751L, "Family");
        map.put(10762L, "Kids");
        map.put(9648L, "Mystery");
        map.put(10763L, "News");
        map.put(10764L, "Reality");
        map.put(10765L, "Sci-Fi & Fantasy");
        map.put(10766L, "Soap");
        map.put(10767L, "Talk");
        map.put(10768L, "War & Politics");
        map.put(37L, "Western");
        map.put(28L, "Action");
        map.put(12L," Adventure");
        map.put(14L, "Fantasy");
        map.put(36L, "History");
        map.put(27L, "Horror");
        map.put(10402L, "Music");
        map.put(10749L, "Romance");
        map.put(878L, "Science Fiction");
        map.put(10770L, "TV Movie");
        map.put(53L, "Thriller");
        map.put(10752L,"War");

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
        if (id == R.id.black_theme) {
            if(!isBlackTheme){
                isBlackTheme = true;
                if(isMovieFragment)
                    openFragment(MOVIE_TYPE);
                else
                    openFragment(TV_TYPE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISBLACKTHEME",true);
                editor.apply();
            }
            return true;
        }else if (id == R.id.white_theme) {
            if(isBlackTheme){
                isBlackTheme = false;
                if(isMovieFragment)
                    openFragment(MOVIE_TYPE);
                else
                    openFragment(TV_TYPE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISBLACKTHEME",false);
                editor.apply();
            }
            return true;
        }else if (id == R.id.grid_layout) {
            if(!isGridLayout){
                isGridLayout = true;
                if(isMovieFragment)
                    openFragment(MOVIE_TYPE);
                else
                    openFragment(TV_TYPE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISGRIDLAYOUT",true);
                editor.apply();
                startActivity(getIntent());
                finish();
            }

            return true;
        }else if (id == R.id.linear_layout) {
            if(!isGridLayout){
                isGridLayout = false;
                if(isMovieFragment)
                    openFragment(MOVIE_TYPE);
                else
                    openFragment(TV_TYPE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISGRIDLAYOUT",false);
                editor.apply();
                startActivity(getIntent());
                finish();
            }

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
            if(!isMovieFragment){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISMOVIEFRAGMENT",true);
                editor.apply();
                isMovieFragment = true;
                startActivity(getIntent());
                finish();
            }
        } else if (id == R.id.nav_tv) {
            if(isMovieFragment){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ISMOVIEFRAGMENT",true);
                editor.apply();
                isMovieFragment = false;
                startActivity(getIntent());
                finish();
            }
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
//
//        BlankFragment blankFragment = BlankFragment.newInstance(null,null);
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container,blankFragment);
//        fragmentTransaction.commit();

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
