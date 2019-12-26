package com.example.myproject.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.signinSignup.SignUpFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    LinearLayout llAboutUs, llContactUS, llLogout, llFacebook, llRateApp;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        llAboutUs = view.findViewById(R.id.llAboutUs);
        llContactUS = view.findViewById(R.id.llContactUS);
        llLogout = view.findViewById(R.id.llLogout);
        llFacebook = view.findViewById(R.id.llFacebook);
        llRateApp = view.findViewById(R.id.llRateApp);

        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, new AboutUsFragment()).addToBackStack(null).commit();
                MainFragment.hideBottomNav();
            }
        });

        llContactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer, new ContactUSFragment()).addToBackStack(null).commit();
                MainFragment.hideBottomNav();
            }
        });

        llFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100009227183630")));
            }
        });

        llRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/webhp?hl=ar&sa=X&ved=0ahUKEwjo_Inkhr3mAhXR8qYKHSz5BwcQPAgH")));
            }
        });


        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.logout))
                        .setMessage(getString(R.string.confirmLogOut))
                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHREDPREFERENCENAME, Context.MODE_PRIVATE);
                                sharedPreferences.edit().putBoolean(MainActivity.SHREDPREFERENCE_IS_FIRST, true).commit();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new SignUpFragment()).commit();
                            }
                        }).setNeutralButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
        return view;
    }

}
