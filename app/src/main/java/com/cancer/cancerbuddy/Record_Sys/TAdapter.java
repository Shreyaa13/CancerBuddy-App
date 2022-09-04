package com.cancer.cancerbuddy.Record_Sys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.cancer.cancerbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TAdapter extends RecyclerView.Adapter<TAdapter.ViewHolder> {

    private Context context;
    private List<TModel> list;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;







    public TAdapter(Context context, List<TModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.show_event_recy, parent, false);
        return new TAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TModel TModel = list.get(position);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        CommRef = FirebaseDatabase.getInstance().getReference().child( "Community" );
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );



        // getData


        String title = TModel.getTitle();
        String date = TModel.getDate();
        String desc = TModel.getDesc();
        String infection = TModel.getInfection();


        // setData


        holder.event_1.setText(title);
        holder.event_2.setText(date);
        holder.event_3.setText(desc);
        holder.event_4.setText("Status : "+ infection);

     /*   if (infection=="Moderate")
        {
            holder.event_showR. setBackgroundResource(R.color.red);
        }*/

      //  holder.event_showR.remove
        holder.event_showR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[]=new CharSequence[]{
                        // select any from the value
                        "Delete",
                        "Cancel",
                };
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Delete Content");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // if delete option is choosed
                        // then call delete function
                        if(which==0) {
                            delete(position,title);
                        }
                    }
                });
                builder.show();
            }
        });

    }

    private void delete(int position, String time) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("DataValue");
        // we are use add listerner
        // for event listener method
        // which is called with query.
        Query query=dbref.child(time);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // remove the value at reference
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView event_1,event_2,event_3,event_4;
        public RelativeLayout event_showR;

        public ViewHolder(View itemView) {
            super(itemView);

            event_1 = itemView.findViewById(R.id.event_1);
            event_2 = itemView.findViewById(R.id.event_2);
            event_3 = itemView.findViewById(R.id.event_3);
            event_4 = itemView.findViewById(R.id.event_4);
            event_showR = itemView.findViewById(R.id.event_showR);




        }
    }
}
