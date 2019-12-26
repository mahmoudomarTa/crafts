package com.example.myproject.signinSignup;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    Button btnSignInNow ;
    TextInputEditText edEmailSignin , edpasswordSignin;
    TextView tvForgetPassword,tvSignUpInsigninF;
    ProgressBar  pb3;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        edEmailSignin = view.findViewById(R.id.edEmailSignin);
        edpasswordSignin = view.findViewById(R.id.edpasswordSignin);
        btnSignInNow = view.findViewById(R.id.btnSignInNow);
        tvForgetPassword = view.findViewById(R.id.tvForgetPassword);
        tvSignUpInsigninF = view.findViewById(R.id.tvSignUpInsigninF);
        pb3=view.findViewById(R.id.pb3);

        btnSignInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edEmailSignin.getText().toString().isEmpty()||edpasswordSignin.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), getString(R.string.completeFileds), Toast.LENGTH_SHORT).show();
                }else{
                    pb3.setVisibility(View.VISIBLE);
                    btnSignInNow.setClickable(false);
                    String eamil =edEmailSignin.getText().toString();
                    String password = edpasswordSignin.getText().toString();
                    View view1 = getActivity().getCurrentFocus();
                    if(view1!=null){
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
                    }
                    auth.signInWithEmailAndPassword(eamil,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    btnSignInNow.setClickable(true);
                                    pb3.setVisibility(View.GONE);
                                    getActivity().getSharedPreferences(MainActivity.SHREDPREFERENCENAME,MODE_PRIVATE).edit().putBoolean(MainActivity.SHREDPREFERENCE_IS_FIRST, false).commit();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MainFragment()).commit();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pb3.setVisibility(View.GONE);
                            btnSignInNow.setClickable(true);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        tvSignUpInsigninF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new SignUpFragment()).commit();
            }
        });


        return view;
    }

}
