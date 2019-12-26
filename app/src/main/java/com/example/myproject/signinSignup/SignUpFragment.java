package com.example.myproject.signinSignup;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Fragments.MainFragment;
import com.example.myproject.Fragments.NoWifiFragment;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    TextInputLayout edMobileSignup, edEmailSignup, edpasswordSignup;
    Button btnSignUpNow;
    TextView tvSignInInsignUpF;
    ProgressBar pb2 ;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        edMobileSignup = view.findViewById(R.id.edMobileSignup);
        edEmailSignup = view.findViewById(R.id.edEmailSignup);
        edpasswordSignup = view.findViewById(R.id.edpasswordSignup);
        btnSignUpNow = view.findViewById(R.id.btnSignUpNow);
        tvSignInInsignUpF = view.findViewById(R.id.tvSignInInsignUpF);
        pb2=view.findViewById(R.id.pb2);

        btnSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileSignup.getEditText().getText().toString().isEmpty() || edEmailSignup.getEditText().getText().toString().isEmpty() || edpasswordSignup.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.completeFileds), Toast.LENGTH_SHORT).show();
                } else {
                    btnSignUpNow.setClickable(false);
                    pb2.setVisibility(View.VISIBLE);
                    String name = edMobileSignup.getEditText().getText().toString();
                    String email = edEmailSignup.getEditText().getText().toString();
                    String password = edpasswordSignup.getEditText().getText().toString();

                    View view1 = getActivity().getCurrentFocus();
                    if(view1!=null){
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
                    }

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    pb2.setVisibility(View.GONE);
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHREDPREFERENCENAME, Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putBoolean(MainActivity.SHREDPREFERENCE_IS_FIRST, false).commit();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MainFragment()).commit();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pb2.setVisibility(View.GONE);
                            btnSignUpNow.setClickable(true);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        tvSignInInsignUpF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasAccessToInternet()){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new NoWifiFragment()).addToBackStack(null).commit();
               // MainFragment.hideBottomNav();
            }else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new SigninFragment()).commit();
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
