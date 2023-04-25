package com.example.gofit;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.Challenges.CreateChallengeDto;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.FriendsRecViewAdapter;
import com.example.gofit.recyclerViews.custum_base_adapter;
import com.example.gofit.recyclerViews.second_custom_adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FourthFragment extends Fragment {

    private ArrayList<String> work_out_names;


    private final String CHANNEL_ID = "MYCHANNEL_ID_CHALLENEGE";
    private ArrayList<String> personal_challenge;
    private ArrayList<String> custum_work_outs;
    private ArrayList<String> customs_description;

    ArrayList<Friend> friendsList = new ArrayList<>();

    private boolean isComplete = false;         // this is to check if weather or not the boolean is complete or not  ///


    private RecyclerView continer1;
    private FloatingActionButton add;
    private custum_base_adapter adapter;
    private second_custom_adapter adapter2;
    private RecyclerView container_2;
    private RelativeLayout visble_container;
    private SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";

    private static final String text = "text";


    private Button all;

    private Button custom;
    private SharedPreferences sp;


    //////Challenge Attributes////

    private String chalCreatorEmail;
    private String chalTitle;
    private String chalDescription;
    private ArrayList<Integer> chalExercisesIds = new ArrayList<>();
    private int chalDuration;
    private int chalReps;
    private int chalSets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fourth_view = inflater.inflate(R.layout.fragment_fourth, container, false);
        sp = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        work_out_names = new ArrayList<>();
        personal_challenge = new ArrayList<>();

        // This call creates a challenge. Currently Lacks interface
        // If you uncomment, when user navigates to the fourth fragment, a test Challenge will be created and pushed to the Database
        //createChallengeCall();

        //userFriendsCall();
        // if the array has been saved, than load else it will it create an empty array //
        load_array();


        visble_container = (RelativeLayout) fourth_view.findViewById(R.id.visible_container);


        all = (Button) fourth_view.findViewById(R.id.all_button);
        custom = (Button) fourth_view.findViewById(R.id.custom_button);

        container_2 = (RecyclerView) fourth_view.findViewById(R.id.recyler_view_2);

        add_challenges();
        continer1 = (RecyclerView) fourth_view.findViewById(R.id.recyler_month_challenge);

        add = (FloatingActionButton) fourth_view.findViewById(R.id.floating_button1);

        adapter2 = new second_custom_adapter(fourth_view.getContext(), custum_work_outs, customs_description);
        adapter = new custum_base_adapter(fourth_view.getContext(), work_out_names, personal_challenge);
        continer1.setLayoutManager(new LinearLayoutManager(fourth_view.getContext()));
        continer1.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setCancelable(true);
                builder.setTitle("Create a Challenge");
                builder.setMessage("Enter Attributes");

                EditText edtTitle = new EditText(getActivity());
                EditText edtDesciption = new EditText(getActivity());
                EditText edtEx1 = new EditText(getActivity());
                EditText edtEx2 = new EditText(getActivity());
                EditText edtEx3 = new EditText(getActivity());
                EditText edtDuration = new EditText(getActivity());
                EditText edtReps = new EditText(getActivity());
                EditText edtSets = new EditText(getActivity());

                LinearLayout lp = new LinearLayout(getActivity().getBaseContext());
                lp.setOrientation(LinearLayout.VERTICAL);
                lp.addView(edtTitle);
                lp.addView(edtDesciption);
                lp.addView(edtEx1);
                lp.addView(edtEx2);
                lp.addView(edtEx3);
                lp.addView(edtDuration);
                lp.addView(edtReps);
                lp.addView(edtSets);

                edtTitle.setHint("Title");
                edtDesciption.setHint("Description");
                edtEx1.setHint("First Exercise Id");
                edtEx2.setHint("Second Exercise Id");
                edtEx3.setHint("Third Exercise Id");
                edtDuration.setHint("Challenge Duration");
                edtReps.setHint("Repetitions amount");
                edtSets.setHint("Sets amount");

                builder.setView(lp);

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chalCreatorEmail = sp.getString("email", "");
                        chalTitle = edtTitle.getText().toString().trim();
                        chalDescription = edtDesciption.getText().toString().trim();

                        chalExercisesIds.add(Integer.parseInt(edtEx1.getText().toString().trim()));
                        chalExercisesIds.add(Integer.parseInt(edtEx2.getText().toString().trim()));
                        chalExercisesIds.add(Integer.parseInt(edtEx3.getText().toString().trim()));

                        chalDuration = Integer.parseInt(edtDuration.getText().toString().trim());
                        chalReps = Integer.parseInt(edtReps.getText().toString().trim());
                        chalSets = Integer.parseInt(edtSets.getText().toString().trim());

                        createChallengeCall();

                    }
                });

                builder.show();

//                add_custum_work_outs();
//                saveArray_state();
//
//
//                try {
//                    Notifcations();
//                } catch (PendingIntent.CanceledException e) {
//                    throw new RuntimeException(e);
//                }

            }
        });

        container_2.setLayoutManager(new LinearLayoutManager(fourth_view.getContext()));
        container_2.setAdapter(adapter2);

        adapter2.setOnRemoveItemLister(new removeItemListner() {
            @Override
            public void setRemoveItem(int postion) {

                final Dialog dialog = new Dialog(getActivity());

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


                dialog.setCancelable(true);

                dialog.setContentView(R.layout.custom_dialog_page);


                //setting up the views in the dialog        //

                AppCompatButton send_friend = dialog.findViewById(R.id.send_friend_button);
                EditText message = dialog.findViewById(R.id.messagess);


                RecyclerView friends_list = dialog.findViewById(R.id.friends_list_container);
                FriendsRecViewAdapter adapter4 = new FriendsRecViewAdapter(getContext(), null);
                adapter4.setFriends(friendsList);
                friends_list.setAdapter(adapter4);

                friends_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                send_friend.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {
                        // api get request friends controller goes here ///


                        Toast.makeText(getContext(), "john: " + message.getText().toString(), Toast.LENGTH_SHORT).show();

                        customs_description.remove(postion);
                        custum_work_outs.remove(postion);
                        adapter2.notifyItemRemoved(postion);

                        isComplete = true;

                        dialog.dismiss();

                    }

                });

                dialog.show();

            }
        });


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


    // this will  be modify later, tempority type of challeneges       // /


    private void add_challenges() {    //S temporary way to populate the recylerview addapter //
        for (int i = 0; i < 10; i++) {
            work_out_names.add("Run if you can");
            personal_challenge.add("description...");
        }
    }


    // same situatio over here //
    private void add_custum_work_outs() {

        custum_work_outs.add("Add Challenges");
        customs_description.add("Add descriptions");
        adapter2.notifyItemInserted(customs_description.size() - 1);
        Toast.makeText(getContext(), "this new size is " + custum_work_outs.size(), Toast.LENGTH_SHORT).show();

    }


    // feature that may or may not be used ///

    protected void Notifcations() throws PendingIntent.CanceledException {

        // NotificationChannel needs to be API CALL 32 OR higher must check with an if statement //

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Challenge Page Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nManger = (NotificationManager) getActivity().getSystemService(NotificationManager.class);
            nManger.createNotificationChannel(channel);

        }
        // must use the same channel ID
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID);

        builder.setContentTitle("Challenge Page");
        builder.setContentText("a new Challenge awaits");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        // after clicking the notification clear it out by setting it true  //


        NotificationManagerCompat newManger = NotificationManagerCompat.from(getContext());

        Intent notification_intent = new Intent(getActivity(), getActivity().getClass());
        notification_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        notification_intent.putExtra("MenuFragment", "FourthFragment");
        PendingIntent pending_intent = PendingIntent.getActivity(getActivity(),
                0, notification_intent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(pending_intent);

        newManger.notify(0, builder.build());

    }


    //save the state of the  arrayList  //

    public void saveArray_state() {
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String Json = gson.toJson(custum_work_outs);
        String JSon2 = gson.toJson(customs_description);

        editor.putString(text, Json);
        editor.putString(text, JSon2);
        editor.apply();
    }

    private void load_array() {

        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String Json = sharedPreferences.getString(text, "");
        String Json2 = sharedPreferences.getString(text, "");


        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();

        custum_work_outs = gson.fromJson(Json, type);
        customs_description = gson.fromJson(Json2, type);


        if (customs_description == null && custum_work_outs == null) {

            customs_description = new ArrayList<String>();
            custum_work_outs = new ArrayList<String>();
        }


    }


    // later will be removed ///


    private void populate_fake_friends_list() {


        for (int i = 0; i < 10; i++) {
            Friend friends = new Friend("john", "fakeemail.com",
                    "https://upload.wikimedia.org/wikipedia/commons/b/bf/LeBron_James_-_51959723161_%28cropped%29.jpg");
            friendsList.add(friends);
        }
    }

    protected void userFriendsCall() {
        String token = sp.getString("token", "");
        MainApplication.apiManager.getFriends(token, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();

                if (response.isSuccessful() && responseFriends != null) {
                    friendsList.addAll(responseFriends.getData());

                    Toast.makeText(getActivity(),
                            "Get Friends was Successful",
                            Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getContext(),
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponseList<Friend>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.d("myTag", t.getMessage());

            }
        });
    }

    private void createChallengeCall() {

        //String userEmail = sp.getString("email","");

//        ArrayList<Integer> testExerciseList = new ArrayList<>();
//        testExerciseList.add(4);
//        testExerciseList.add(5);
//        testExerciseList.add(6);

        String token = sp.getString("token", "");

        //CreateChallengeDto testModel = new CreateChallengeDto(userEmail, "Test Challenge", "Test Description", chalExercisesIds,10, 8,3);
        CreateChallengeDto testModel = new CreateChallengeDto(chalCreatorEmail, chalTitle, chalDescription, chalExercisesIds,chalDuration, chalReps,chalSets);
        MainApplication.apiManager.createChallenge(token, testModel, new Callback<defaultResponse<ChallengeRecordDto>>() {
            @Override
            public void onResponse(Call<defaultResponse<ChallengeRecordDto>> call, Response<defaultResponse<ChallengeRecordDto>> response) {
                defaultResponse<ChallengeRecordDto> challengeRecords = response.body();

                Toast.makeText(getActivity(),
                        "Challenge Creation Successful",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<defaultResponse<ChallengeRecordDto>> call, Throwable t) {

            }
        });
    }
}


