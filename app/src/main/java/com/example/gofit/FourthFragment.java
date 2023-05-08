package com.example.gofit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;


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
import com.example.gofit.data.model.requests.Challenges.ChallengeRequestDto;
import com.example.gofit.data.model.requests.Challenges.CreateChallengeDto;
import com.example.gofit.data.model.requests.Challenges.ChallengeDto;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.recyclerViews.FriendsRecViewAdapter;
import com.example.gofit.recyclerViews.custum_base_adapter;
import com.example.gofit.recyclerViews.display_new_challenge_adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FourthFragment extends Fragment implements custum_base_adapter.OnNoteListener {

    private ArrayList<ChallengeDto> work_out_names;


    //////Challenge Attributes////

    private String chalCreatorEmail;
    private String chalTitle;
    private String chalDescription;
    private ArrayList<Integer> chalExercisesIds = new ArrayList<>();
    private int chalDuration;
    private int chalReps;
    private int chalSets;
///////////////////////////////////////////////////////////////////


    private final String CHANNEL_ID = "MYCHANNEL_ID_CHALLENEGE";

    private ArrayList<ChallengeRecordDto> custom_challenges ;

    private ArrayList<Friend> friendsList = new ArrayList<>();

    private boolean isComplete = false;         // this is to check if weather or not the boolean is complete or not  ///


    private RecyclerView continer1;
    private FloatingActionButton add;
    private custum_base_adapter adapter;

    // 5/1/2023 adapter2 will be removed(including this comment), not need for this feature//


    // UI elements attributes   //

    private display_new_challenge_adapter adapter2;

    private RecyclerView challenge_r;   // challenge_r will be short for challenge recylerview container    //

    private RelativeLayout visble_container;
    private SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";

    private static final String text = "text";


    private Button all;

    private Button custom;
    private Button requests;
    private SharedPreferences sp ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fourth_view = inflater.inflate(R.layout.fragment_fourth, container, false);
        sp = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
       work_out_names = new ArrayList<>();

        // api calls        //
        userFriendsCall();
        // if the array has been saved, than load else it will it create an empty array //

        challenge_call();


        //used in this fragment too to turn requests btn red when having any requests
        challengeRequestsCall();

        custom_challenges = new ArrayList<>( );


        //define the UI ELEMENTS        ///

        visble_container = (RelativeLayout) fourth_view.findViewById(R.id.visible_container);


        all = (Button) fourth_view.findViewById(R.id.all_button);
        custom = (Button) fourth_view.findViewById(R.id.custom_button);
        requests = (Button) fourth_view.findViewById(R.id.requests_button);

        challenge_r = (RecyclerView) fourth_view.findViewById(R.id.recyler_view_2);

        //add_challenges(); this sholuld be comment, removed or a combination   //

        continer1 = (RecyclerView) fourth_view.findViewById(R.id.recyler_month_challenge);

        add = (FloatingActionButton) fourth_view.findViewById(R.id.floating_button1);
        adapter2 = new display_new_challenge_adapter(fourth_view.getContext(), custom_challenges);
        adapter = new custum_base_adapter(fourth_view.getContext(), work_out_names, this);
        continer1.setLayoutManager(new LinearLayoutManager(fourth_view.getContext()));
        continer1.setAdapter(adapter);
    /*
        *
        * add custom function will allow user the create their own challenge and get added
        * in their challenge recycler
        * after will updated the recycler view and displayed it to  the user
        * Return a void
        *
     */

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChallengeRequestsPage.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add_custum_work_outs();
                startActivity(new Intent(getContext(), ChallengeCreationPage.class));
            }
        });

        challenge_r.setLayoutManager(new LinearLayoutManager(fourth_view.getContext()));
        challenge_r.setAdapter(adapter2);

        /*
        * the remove listener will wait for the user the click on the remove button
        * and remove Challenge object from the Challenge array list and notify the
        * recyler that item has been removed and modified the recycler
        * than update recycler view
        *
        *
         */

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
                /////////////API ON WORK HERE /////////////////////
                        //customs_description.remove(postion);
                       // custum_work_outs.remove(postion);
                        adapter2.notifyItemRemoved(postion);

                       // isComplete = true;

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


   //private void add_challenges() {    //S temporary way to populate the recylerview addapter //
       // for (int i = 0; i < 10; i++) {
          //  work_out_names.add("Run if you can");
           // personal_challenge.add("description...");
     //   }
    //}


    private void challenge_call () {
        String token = sp.getString("token", "");

        MainApplication.apiManager.getChallenges(token, new Callback<defaultResponseList<ChallengeDto>>() {
            @Override
            public void onResponse(Call<defaultResponseList<ChallengeDto>> call, Response<defaultResponseList<ChallengeDto>> response) {
                defaultResponseList<ChallengeDto> challengeResponse = response.body();

                Toast.makeText(getContext(), "Challenge was sucessful: " + response.code(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful() && challengeResponse != null) {
                    if (challengeResponse.getData() != null) {
                        work_out_names.addAll(challengeResponse.getData()) ;

                        // updates the front end again after the api call   //

                        adapter.setChallenges_list(work_out_names);
                        continer1.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<defaultResponseList<ChallengeDto>> call, Throwable t) {

                Toast.makeText(getContext(), "ERROR: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void add_custum_work_outs() {

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

        builder.show() ;

    }


    private void createChallengeCall () {

        String token = sp.getString("token", "");

        //CreateChallengeDto testModel = new CreateChallengeDto(userEmail, "Test Challenge", "Test Description", chalExercisesIds,10, 8,3);
        CreateChallengeDto testModel = new CreateChallengeDto(chalCreatorEmail, chalTitle, chalDescription, chalExercisesIds,chalDuration, chalReps,chalSets);
        MainApplication.apiManager.createChallenge(token, testModel, new Callback<defaultResponse<ChallengeRecordDto>>() {
            @Override
            public void onResponse(Call<defaultResponse<ChallengeRecordDto>> call, Response<defaultResponse<ChallengeRecordDto>> response) {
                defaultResponse<ChallengeRecordDto> challengeRecords = response.body();


                custom_challenges.add(challengeRecords.getData()) ;

                /////reset the UI data  /////////

                adapter2.setChallengesList(custom_challenges);
                challenge_r.setAdapter(adapter2);

                ////////////////////////////////

                Toast.makeText(getActivity(),
                        "Challenge Creation Successful",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<defaultResponse<ChallengeRecordDto>> call, Throwable t) {

            }
        });
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




    private void challengeRequestsCall() {

        String token = sp.getString("token", "");

        MainApplication.apiManager.getUserChallengeRequests(token, new Callback<defaultResponseList<ChallengeRequestDto>>() {
            @Override
            public void onResponse(Call<defaultResponseList<ChallengeRequestDto>> call, Response<defaultResponseList<ChallengeRequestDto>> response) {
                defaultResponseList<ChallengeRequestDto> requestersList = response.body();

                if(response.isSuccessful() && requestersList.getData() != null)
                {
                    ((Button)requests).setTextColor(Color.parseColor("#db0d0d"));
                }
                else
                {
                    requests.setTextColor(Color.parseColor("#FFFFFF"));

                }
            }

            @Override
            public void onFailure(Call<defaultResponseList<ChallengeRequestDto>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "Error: " + t.getMessage()
                        , Toast.LENGTH_LONG).show();

            }
        });

    }



    protected void userFriendsCall() {
        String token = sp.getString("token", "");
        MainApplication.apiManager.getFriends(token, new Callback<defaultResponseList<Friend>>() {
            @Override
            public void onResponse(Call<defaultResponseList<Friend>> call, Response<defaultResponseList<Friend>> response) {
                defaultResponseList<Friend> responseFriends = response.body();

                if (response.isSuccessful() && responseFriends != null) {
                    ArrayList<Friend> tempList = new ArrayList<>();
                    tempList.addAll(responseFriends.getData());

                    friendsList = new ArrayList<>(tempList);

//                    Toast.makeText(getActivity(),
//                            "Get Friends was Successful",
//                            Toast.LENGTH_SHORT).show();


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

    @Override
    public void onNoteClick(int position) {

        Intent intent = new Intent(getContext(), ChallengeDetails.class);
        intent.putExtra("title", work_out_names.get(position).getTitle());
        intent.putExtra("duration", work_out_names.get(position).getDurationDays());
        intent.putExtra("reps",work_out_names.get(position).getReps());
        intent.putExtra("sets", work_out_names.get(position).getSets());
        intent.putExtra("id", work_out_names.get(position).getChallengeId());
        intent.putExtra("email", work_out_names.get(position).getCreatorEmail());
        intent.putExtra("description", work_out_names.get(position).getDesc());

        Bundle bundle = new Bundle();
        ArrayList<Exercise_Item> exercise_list = work_out_names.get(position).getExerciseList() ;
        bundle.putSerializable("exercise_list", exercise_list);
        intent.putExtras(bundle);

        //SEND FRIENDS LIST TO A CHALLENGE ITEM PAGE
        Bundle friendArgs = new Bundle();
        friendArgs.putSerializable("friends_list", friendsList);
        intent.putExtras(friendArgs);


        startActivity(intent);
    }
}


