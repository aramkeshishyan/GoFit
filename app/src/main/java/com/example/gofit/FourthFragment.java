package com.example.gofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FourthFragment extends Fragment {

    private ArrayList<String> work_out_names ;
    private ArrayList<String> personal_challenge;
    private ArrayList<String> custum_work_outs;
    private ArrayList<String> customs_description;
    private RecyclerView continer1 ;
    private RecyclerView container2 ;
    private FloatingActionButton add ;
    private custum_base_adapter adapter ;
    private second_custom_adapter adapter2 ;
    private RecyclerView container_2 ;
    private RelativeLayout visble_container ;

    private Button all;

    private Button custom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fourth_view = inflater.inflate(R.layout.fragment_fourth, container, false) ;
        work_out_names = new ArrayList<>() ;
        personal_challenge = new ArrayList<>( );
        custum_work_outs = new ArrayList<>() ;
        customs_description = new ArrayList<>() ;
        visble_container = (RelativeLayout)fourth_view.findViewById(R.id.visible_container)  ;

        all = (Button)fourth_view.findViewById(R.id.all_button) ;
        custom = (Button)fourth_view.findViewById(R.id.custom_button) ;

        container_2 = (RecyclerView)fourth_view.findViewById(R.id.recyler_view_2);

        add_challenges();
        continer1 = (RecyclerView)fourth_view.findViewById(R.id.recyler_month_challenge) ;

        add = (FloatingActionButton)fourth_view.findViewById(R.id.floating_button1) ;
        adapter2 = new second_custom_adapter(fourth_view.getContext(), custum_work_outs, customs_description) ;
        adapter = new custum_base_adapter(fourth_view.getContext(), work_out_names, personal_challenge) ;
        continer1.setLayoutManager(new LinearLayoutManager(fourth_view.getContext()));
        continer1.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_custum_work_outs();

            }
        });

        container_2.setLayoutManager(new LinearLayoutManager(fourth_view.getContext()));
        container_2.setAdapter(adapter2);

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visble_container.setVisibility(View.GONE);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visble_container.setVisibility(View.VISIBLE);
            }
        });
        return fourth_view;
    }


    public void add_challenges () {    // temporary way to populate the recylerview addapter //
        for (int i = 0; i < 10; i++) {
            work_out_names.add("Run if you can") ;
            personal_challenge.add("description...") ;
        }
    }

    public void add_custum_work_outs () {

        custum_work_outs.add("Add Challenges");
        customs_description.add("Add descriptions");
        adapter2.notifyItemInserted(customs_description.size() -1);
        Toast.makeText(getContext(), "this new size is " + custum_work_outs.size(), Toast.LENGTH_SHORT).show();

    }
}