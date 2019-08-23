package com.example.demoeditimage.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.demoeditimage.R;
import com.example.demoeditimage.fragment.MoreOptionsFragment;
import com.example.demoeditimage.fragment.OverviewStoreFragment;
import com.example.demoeditimage.fragment.ProductManagementFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewStoreActivity extends AppCompatActivity {

    @BindView(R.id.navBottom)
    BottomNavigationView navBottom;

    @BindView(R.id.frame_main)
    FrameLayout frame_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_store);
        ButterKnife.bind(this);

        addOverviewStoreFragment();


        navBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.overview_menu:
                        setFragment(new OverviewStoreFragment());
                        return true;

                    case R.id.products_menu:
                        setFragment(new ProductManagementFragment());
                        return true;

                    case R.id.images_library:
//                        setFragment(new ProfileFragment());
                        return true;

                    case R.id.moreAction:
                        setFragment(new MoreOptionsFragment());
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }

    private void addOverviewStoreFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new OverviewStoreFragment()).commit();
    }

}


