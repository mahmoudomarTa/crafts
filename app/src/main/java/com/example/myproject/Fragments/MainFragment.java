package com.example.myproject.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static View view;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, MainActivity.homeFragment).commit();
        BottomNavigationView navigationView = view.findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home_fragment:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, MainActivity.homeFragment).commit();
                        break;
                    case R.id.requests_fragment:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, new RequsetsFragment()).commit();
                        break;
                    case R.id.more_fragment:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, new MoreFragment()).commit();
                        break;
                }

                return true;
            }
        });

        return view;
    }

    public static void selectHome() {
        BottomNavigationView navigationView = view.findViewById(R.id.bottomNavigationView);
        if (navigationView.getVisibility() != View.GONE) {
            navigationView.setSelectedItemId(R.id.home_fragment);
        } else {
            showBottomNav();
            selectHome();
        }
    }

    public static void selectRequest() {
        BottomNavigationView navigationView = view.findViewById(R.id.bottomNavigationView);
        if (navigationView.getVisibility() != View.GONE) {
            navigationView.setSelectedItemId(R.id.requests_fragment);
        } else {
            showBottomNav();
            selectRequest();
        }
    }

    public static void selectMore() {
        BottomNavigationView navigationView = view.findViewById(R.id.bottomNavigationView);
        if (navigationView.getVisibility() != View.GONE) {
            navigationView.setSelectedItemId(R.id.more_fragment);
        } else {
            showBottomNav();
            selectMore();
        }
    }

    public static void hideBottomNav() {
        BottomNavigationView navigationView = view.findViewById(R.id.bottomNavigationView);
        if (navigationView!=null){
        navigationView.setVisibility(View.GONE);
    }
    }

    public static void showBottomNav() {
        BottomNavigationView navigationView = view.findViewById(R.id.bottomNavigationView);
        navigationView.setVisibility(View.VISIBLE);
    }
}