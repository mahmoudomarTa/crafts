package com.example.myproject.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myproject.Database.MyDatabasehelper;
import com.example.myproject.R;
import com.example.myproject.models.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.UUID;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvidingServiceFragment extends Fragment {

    private FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
//    private DatabaseReference dbrUser = fdb.getReference("/request/").child("uid");
//    private DatabaseReference dbrName = fdb.getReference("/request/").child("name");
//    private DatabaseReference dbrDate = fdb.getReference("/request/").child("date");
//    private DatabaseReference dbrFinished = fdb.getReference("/request/").child("finished");
//    private DatabaseReference dbrAddreess = fdb.getReference("/request/").child("address");
//    private DatabaseReference dbrMore = fdb.getReference("/request/").child("mdore");



    private String jobName , addressDetails, moreDetails;
    private Long date;
    private int y , m , d, h,mi;
    private TextView edWorkDate ,edWorkTime;
    private EditText edAddressDetails ,edMoreDetails;
    private RadioGroup rowRadioGroup;
    private Button submit;
    private RadioButton radioWith,radioWithout;

    public ProvidingServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_providing, container, false);

        final MyDatabasehelper myDatabasehelper = new MyDatabasehelper(getContext());
        if (getArguments()!=null){
            jobName =getArguments().getString("jobName");
        }else {
            jobName=getString(R.string.special);
        }

        edAddressDetails = view.findViewById(R.id.edAddressDetails);
        edWorkDate = view.findViewById(R.id.edWorkDate);
        edWorkTime = view.findViewById(R.id.edWorkTime);
        edMoreDetails = view.findViewById(R.id.edMoreDetails);
        rowRadioGroup = view.findViewById(R.id.rowRadioGroup);
        submit=view.findViewById(R.id.btnSubmit);
        radioWith=view.findViewById(R.id.radioWith);
        radioWithout=view.findViewById(R.id.radioWithout);



        edWorkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y=year;
                        m=month+1;
                        d=dayOfMonth;
                        edWorkDate.setText(d+" : "+m+" : "+y);

                        //TODO : create an instance from the date

                    }
                },year,month,day);
                dialog.show();
            }
        });
        edWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                final TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        h=hourOfDay;
                        mi=minute;
                        edWorkTime.setText(h + " : "+mi);
                        //TODO : create an instance from the time
                    }
                }, hour, minute, false);
                dialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edAddressDetails.getText().toString().isEmpty()||
                        edWorkDate.getText().toString().equals(getString(R.string.date))||
                        edWorkTime.getText().toString().equals(getString(R.string.enterTime))||
                        edMoreDetails.getText().toString().isEmpty()||
                        (!radioWith.isChecked()&&!radioWithout.isChecked())){
                        Toast.makeText(getContext(),getString(R.string.completeFileds),Toast.LENGTH_SHORT).show();

                }else if(hasAccessToInternet()){

                    Calendar cc = Calendar.getInstance();
                    Calendar calendar = Calendar.getInstance();
                    cc.set(Calendar.YEAR,y);
                    cc.set(Calendar.MONTH,m-1);
                    cc.set(Calendar.DAY_OF_MONTH,d);
                    cc.set(Calendar.MINUTE,mi);
                    cc.set(Calendar.HOUR_OF_DAY,h);
                    date = cc.getTimeInMillis();

                    if (date<calendar.getTimeInMillis()){
                        Toast.makeText(getContext(), getString(R.string.realDate), Toast.LENGTH_SHORT).show();
                    }else {

                        view.findViewById(R.id.mProgress).setVisibility(View.VISIBLE);
                        submit.setClickable(false);
                        addressDetails = edAddressDetails.getText().toString();
                        moreDetails = edMoreDetails.getText().toString();
                        String uid = UUID.randomUUID().toString();
                        Request request = new Request(addressDetails, date, false, moreDetails, jobName, radioWith.isChecked(), auth.getCurrentUser().getUid(), uid);
                        fdb.getReference("/request").child(uid).setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                view.findViewById(R.id.mProgress).setVisibility(View.GONE);
                                submit.setClickable(true);
                                edWorkDate.setText("");
                                edWorkTime.setText(getResources().getString(R.string.enterTime));
                                edWorkDate.setText(getResources().getString(R.string.date));
                                edAddressDetails.setText("");
                                edMoreDetails.setText("");
                                radioWith.setChecked(true);
                                radioWithout.setChecked(false);
                                Toast.makeText(getContext(), getString(R.string.providingSuccessfully), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                        });


                        View view1 = getActivity().getCurrentFocus();
                        if (view1 != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                        }

                    }
                }else if (!hasAccessToInternet()){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subContainer,new NoWifiFragment()).addToBackStack(null).commit();
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
