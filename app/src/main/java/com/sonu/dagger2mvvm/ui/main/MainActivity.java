package com.sonu.dagger2mvvm.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sonu.dagger2mvvm.BaseActivity;
import com.sonu.dagger2mvvm.R;
import com.sonu.dagger2mvvm.SessionManager;
import com.sonu.dagger2mvvm.ui.main.posts.PostFragment;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Inject
    SessionManager sessionManager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new PostFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManager.logOut();
                return true;

            case android.R.id.home: {
//                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                    return true;
//                }
//                else{
//                    return false;
//                }
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_profile: {

//                NavOptions navOptions = new NavOptions.Builder()
//                        .setPopUpTo(R.id.main, true)
//                        .build();
//
//                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
//                        R.id.profileScreen,
//                        null,
//                        navOptions
//                );

                break;
            }

            case R.id.nav_posts: {

//                if(isValidDestination(R.id.postsScreen)){
//                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.postsScreen);
//                }
//
//                break;
            }
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}