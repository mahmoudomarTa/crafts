package com.example.myproject.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myproject.R;
import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUSFragment extends Fragment {

    EditText edNameC,edMobileC,edMessageC;
    Button btnSendC;
    ProgressBar progressBarC;

    public ContactUSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        edNameC = view.findViewById(R.id.edNameC);
        edMobileC = view.findViewById(R.id.edMobileC);
        edMessageC = view.findViewById(R.id.edMessageC);
        btnSendC = view.findViewById(R.id.btnSendC);
        progressBarC = view.findViewById(R.id.progressBarC);

        btnSendC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasAccessToInternet()){
                if (edNameC.getText().toString().isEmpty()||
                edMessageC.getText().toString().isEmpty()||
                edMobileC.getText().toString().isEmpty()){
                    Snackbar.make(getActivity().findViewById(android.R.id.content),getString(R.string.completeFileds),Snackbar.LENGTH_SHORT).show();
                }else{

                    View view1 = getActivity().getCurrentFocus();
                    if(view1!=null){
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
                    }
                    progressBarC.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBarC.setVisibility(View.GONE);
                            edNameC.setText("");
                            edMessageC.setText("");
                            edMobileC.setText("");
                            Toast.makeText(getContext(), getString(R.string.sendSuccessfully), Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);
                }
            }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer,new NoWifiFragment()).commit();
                    MainFragment.hideBottomNav();
                }
            }
        });

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
