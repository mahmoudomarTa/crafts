package com.example.myproject.Fragments;


import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myproject.Adapters.RequestsAdapter;
import com.example.myproject.Database.MyDatabasehelper;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequsetsFragment extends Fragment {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/request/");
    RecyclerView recyclerView;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid;
    RequestsAdapter adapter;
    ArrayList<Request> data;
    Button btnFinish;
    public RequsetsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_requsets, container, false);
        final String key = getActivity().getSharedPreferences(MainActivity.SHREDPREFERENCENAME, Context.MODE_PRIVATE).getString(MainActivity.SHREDPREFERENCEUID,"unknown");
        recyclerView= view.findViewById(R.id.rvRequests);
        btnFinish=view.findViewById(R.id.btnFinish);

        if (hasAccessToInternet()) {

            uid = auth.getCurrentUser().getUid();
            //final ArrayList<Request> requests = new ArrayList<>();

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    data = new ArrayList<>();
                    data.clear();
                    final Iterator<DataSnapshot> requests = dataSnapshot.getChildren().iterator();
                    while ((requests.hasNext())) {
                        DataSnapshot item = requests.next();
                        Request r = item.getValue(Request.class);
                        if (auth.getCurrentUser().getUid().equals(r.getUid())) {
                            data.add(r);
                        }
                    }

                    view.findViewById(R.id.pb4).setVisibility(View.GONE);
                    adapter = new RequestsAdapter(data);
                    adapter.notifyDataSetChanged();
                    adapter.setOnBtnClick(new RequestsAdapter.OnBtnClick() {
                        @Override
                        public void onClick(final int i) {
                            if (data.get(i).getKey().toString() != null) {
                                if (data.get(i).getKey().toString().equals(dataSnapshot.child(data.get(i).getKey().toString()).getKey().toString())) {
                                    new AlertDialog.Builder(getContext())
                                            .setTitle(getString(R.string.delete))
                                            .setMessage(getString(R.string.areYouSureToDelete))
                                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dataSnapshot.child(data.get(i).getKey()).getRef().removeValue();
                                                }
                                            }).setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();


                                }
                            }
                        }
                    });

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                     }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer,new NoWifiFragment()).commit();
            MainFragment.hideBottomNav();
        }


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
