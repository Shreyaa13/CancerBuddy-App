package com.cancer.cancerbuddy.Community;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cancer.cancerbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<CommentModel> list;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;

    Dialog dialog;

    public CommentAdapter(Context context, List<CommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.add_comment, parent, false);
        return new CommentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        CommentModel CommentModel = list.get(position);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        CommRef = FirebaseDatabase.getInstance().getReference().child( "Community" );
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" );
               // .child( currentUserID );

        UsersRef.child(CommentModel.getUserId()).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String Full_Name =dataSnapshot.child( "Full_Name" ).getValue().toString();
                    holder.CUser.setText(Full_Name );

                }
                else
                {
                    Toast.makeText(context, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        // getData
        String Id = CommentModel.getPostId();
        String UserId = CommentModel.getUserId();
        String date = CommentModel.getDate();
        String desc = CommentModel.getComm();


        // setData
        holder.CDate.setText(date);
        holder.CommentC.setText(desc);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView CUser, CDate, CommentC;

        public ViewHolder(View itemView) {
            super(itemView);

            CUser = itemView.findViewById(R.id.CUser);
            CDate = itemView.findViewById(R.id.CDate);
            CommentC = itemView.findViewById(R.id.CommentC);
        }
    }
}
