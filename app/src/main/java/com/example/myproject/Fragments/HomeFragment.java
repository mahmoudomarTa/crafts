package com.example.myproject.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myproject.Adapters.HomeRvAdapter;
import com.example.myproject.R;
import com.example.myproject.models.Job;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rvMain;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainFragment.showBottomNav();
        MainFragment.selectHome();

        final ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job(R.drawable.building,getString(R.string.bulding)));
        jobs.add(new Job(R.drawable.car_maintenance,getString(R.string.car_maintenance)));
        jobs.add(new Job(R.drawable.condition,getString(R.string.condition)));
        jobs.add(new Job(R.drawable.elctric,getString(R.string.elctric)));
        jobs.add(new Job(R.drawable.improvement,getString(R.string.improvement)));
        jobs.add(new Job(R.drawable.insects,getString(R.string.insects)));
        jobs.add(new Job(R.drawable.modelling,getString(R.string.modelling)));
        jobs.add(new Job(R.drawable.paint,getString(R.string.paint)));
        jobs.add(new Job(R.drawable.party,getString(R.string.party)));
        jobs.add(new Job(R.drawable.pipes,getString(R.string.pipes)));
        jobs.add(new Job(R.drawable.planting,getString(R.string.planting)));
        jobs.add(new Job(R.drawable.solar,getString(R.string.solar)));
        jobs.add(new Job(R.drawable.tiles,getString(R.string.tiles)));
        jobs.add(new Job(R.drawable.transfer,getString(R.string.transfer)));
        jobs.add(new Job(R.drawable.windows,getString(R.string.windows)));
        jobs.add(new Job(R.drawable.solar_w,getString(R.string.solarW)));


        rvMain = view.findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new GridLayoutManager(getContext(),3));
        HomeRvAdapter adapter = new HomeRvAdapter(jobs);
        adapter.setOnJobClickListener(new HomeRvAdapter.OnJobClickListener() {
            @Override
            public void onJobClick(int positon) {
                // Log.d("hzm","onJobClick !!");
                // Toast.makeText(getContext(), "onJobClick"+positon, Toast.LENGTH_SHORT).show();
                if (hasAccessToInternet()) {

                    Bundle bundle = new Bundle();
                    bundle.putString("jobName", jobs.get(positon).getJobName());
                    ProvidingServiceFragment providingServiceFragment = new ProvidingServiceFragment();
                    providingServiceFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, providingServiceFragment).addToBackStack(null).commit();
                    MainFragment.hideBottomNav();
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, new NoWifiFragment()).addToBackStack(null).commit();
                    MainFragment.hideBottomNav();
                }
            }
            @Override
            public void onJobLongClick(int position) {

            }
        });
        rvMain.setAdapter(adapter);
        return view;
    }
    private boolean hasAccessToInternet(){
        ConnectivityManager manager =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return manager.getActiveNetwork()!=null;
        }
        return true;
    }
}
