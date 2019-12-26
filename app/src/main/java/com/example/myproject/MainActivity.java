package com.example.myproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myproject.Fragments.HomeFragment;
import com.example.myproject.Fragments.MainFragment;
import com.example.myproject.signinSignup.SignUpFragment;
public class MainActivity extends AppCompatActivity {

    public static HomeFragment homeFragment;
    public static SignUpFragment signUpFragment;
    public static String SHREDPREFERENCENAME = "mySharedPreferences";
    public static String SHREDPREFERENCEUID = "uid";
    public static String SHREDPREFERENCE_IS_FIRST = "isFirst";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.mToolbar));
        homeFragment = new HomeFragment();
        signUpFragment=new SignUpFragment();
        SharedPreferences sharedPreferences = getSharedPreferences(SHREDPREFERENCENAME, MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean(SHREDPREFERENCE_IS_FIRST, true);

        if (isFirstTime) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, signUpFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MainFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {

        if (!getSharedPreferences(SHREDPREFERENCENAME,MODE_PRIVATE).getBoolean(SHREDPREFERENCE_IS_FIRST,true)){
            if (homeFragment.isAdded() && homeFragment.isVisible()){
                finish();
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new MainFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.subContainer,homeFragment).commit();
            }

        }else if(!signUpFragment.isAdded()&&!signUpFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,signUpFragment).commit();
        }else {
            finish();
        }
    }

}
