package com.cancer.cancerbuddy.Community;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CommAdapter extends RecyclerView.Adapter<CommAdapter.ViewHolder> {

private Context context;
private List<CommModel> list;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;

    Dialog dialog;





public CommAdapter(Context context, List<CommModel> list) {
        this.context = context;
        this.list = list;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.social_comm, parent, false);
        return new ViewHolder(v);
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        CommModel commModel = list.get(position);


    mAuth = FirebaseAuth.getInstance();
    currentUserID = mAuth.getCurrentUser().getUid();
    CommRef = FirebaseDatabase.getInstance().getReference().child( "Community" );
    UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );



    // getData


    String Id = commModel.getPostId();
    String name = commModel.getUser_Name();
    String date = commModel.getDate();
    String desc = commModel.getDesc();
    String img = commModel.getImage();


 /*   String like = commModel.getImgLike();
    String liked = commModel.getImgLike();
    String dislike = commModel.getImgLike();
    String disliked = commModel.getImgLike();*/

    // setData

    holder.NameC.setText( name );
    holder.Date_and_Time.setText( date );
    holder.DescA.setText( desc );

    try {
        Picasso.get().load( img ).into(holder.imgComm );
    }
    catch (Exception e)
    {
        holder.imgComm.setImageResource( R.drawable.canlogo );
    }

    holder.imgcomment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //  holder.rlAddComm.setVisibility(View.VISIBLE);

            holder.imgcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("Id",Id);
                    context.startActivity(intent);
                }
            });

        }
    });




/*

    if (like=="1")
    {

        holder.imgLike.setVisibility(View.VISIBLE);
        holder.imgLiked.setVisibility(View.INVISIBLE);
        holder.imgdislike.setVisibility(View.VISIBLE);
        holder.imgDisLiked.setVisibility(View.INVISIBLE);

    }
    else if (like == "2")
    {
        holder.imgLike.setVisibility(View.INVISIBLE);
        holder.imgLiked.setVisibility(View.VISIBLE);
        holder.imgdislike.setVisibility(View.VISIBLE);
        holder.imgDisLiked.setVisibility(View.INVISIBLE);
    }
    else if (like=="3")
    {
        holder.imgLike.setVisibility(View.VISIBLE);
        holder.imgLiked.setVisibility(View.INVISIBLE);
        holder.imgdislike.setVisibility(View.VISIBLE);
        holder.imgDisLiked.setVisibility(View.INVISIBLE);
    }
    else if (like =="4")
    {
        holder.imgLike.setVisibility(View.VISIBLE);
        holder.imgLiked.setVisibility(View.INVISIBLE);
        holder.imgdislike.setVisibility(View.INVISIBLE);
        holder.imgDisLiked.setVisibility(View.VISIBLE);
    }
*/

    holder.imgLike.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait1);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            HashMap<String, Object> hashMap = new HashMap<>(  );
            hashMap.put( "Liked", "1");
            hashMap.put( "User_ID", ""+ currentUserID);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Post" );
            reference.child("like").child(commModel.getPostId()).
                    updateChildren( hashMap )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                         //   Toast.makeText( context, "Post Added" ,Toast.LENGTH_SHORT).show();
                            holder.imgLike.setVisibility(View.INVISIBLE);
                            holder.imgLiked.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( context, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                            // loadingBar.dismiss();
                            dialog.dismiss();
                        }
                    } );
        }
    });

   /* holder.imgLiked.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait1);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            HashMap<String, Object> hashMap = new HashMap<>(  );
            hashMap.put( "Liked", "2");
            hashMap.put( "User_ID", ""+ currentUserID);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Post" );
            reference.child("like").child(commModel.getPostId()).
                    updateChildren( hashMap )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           // Toast.makeText( context, "Post Added" ,Toast.LENGTH_SHORT).show();
                            holder.imgLiked.setVisibility(View.INVISIBLE);
                            holder.imgLike.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( context, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                            // loadingBar.dismiss();
                            dialog.dismiss();
                        }
                    } );
        }
    });*/

    holder.imgdislike.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait1);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();



            HashMap<String, Object> hashMap = new HashMap<>(  );
            hashMap.put( "Liked", "3");
            hashMap.put( "User_ID", ""+ currentUserID);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Post" );
            reference.child("Dislike").child(commModel.getPostId())
            .child(String.valueOf(Id)).updateChildren( hashMap )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                          //  Toast.makeText( context, "Post Added" ,Toast.LENGTH_SHORT).show();
                          //  holder.imgLike.setVisibility(View.VISIBLE);
                          //  holder.imgLiked.setVisibility(View.INVISIBLE);
                            holder.imgdislike.setVisibility(View.INVISIBLE);
                            holder.imgDisLiked.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( context, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                            // loadingBar.dismiss();
                            dialog.dismiss();
                        }
                    } );
        }
    });
/*    holder.imgDisLiked.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait1);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            HashMap<String, Object> hashMap = new HashMap<>(  );
            hashMap.put( "Liked", "4");
            hashMap.put( "User_ID", ""+ currentUserID);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Post" );
            reference.child("Dislike").child(commModel.getPostId())
                    .updateChildren( hashMap )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                         //   Toast.makeText( context, "Post Added" ,Toast.LENGTH_SHORT).show();
                            holder.imgLike.setVisibility(View.INVISIBLE);
                            holder.imgDisLiked.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( context, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                            // loadingBar.dismiss();
                            dialog.dismiss();
                        }
                    } );
        }
    });*/


   /* holder.PostComm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String Post_Comm = holder.AddComm.getText().toString();

            if (Post_Comm.isEmpty()) {
                Toast.makeText( context, "Please Write Something" ,Toast.LENGTH_SHORT).show();
            }
            else {


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Comment", Post_Comm);
                hashMap.put("User_ID", "" + currentUserID);

                CommRef.child("Comment").child(String.valueOf(Id)).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                   Toast.makeText( context, "Comment Added" ,Toast.LENGTH_SHORT).show();
                                holder.rlAddComm.setVisibility(View.INVISIBLE);
                               // dialog.dismiss();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                // loadingBar.dismiss();
                                dialog.dismiss();
                            }
                        });

            }

        }
    });*/


    DatabaseReference referenceLike = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("like");

    referenceLike.child(commModel.getPostId()).orderByChild("User_ID").equalTo(currentUserID)
            .addValueEventListener( new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

             //  String s =snapshot.child( "User_ID" ).getValue().toString();

             /*   int sum = 0;
                sum = (int) snapshot.getChildrenCount();
                holder.commentA.setText( Integer.toString( sum )  );*/
                holder.imgLike.setVisibility(View.GONE);
                holder.imgLiked.setVisibility(View.VISIBLE);



            }
            else
            {
                holder.imgLike.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    } );



    DatabaseReference referenceDisLike = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("Dislike");

    referenceDisLike.child(commModel.getPostId()).orderByChild("User_ID").equalTo(currentUserID)
            .addValueEventListener( new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        //  String s =snapshot.child( "User_ID" ).getValue().toString();

             /*   int sum = 0;
                sum = (int) snapshot.getChildrenCount();
                holder.commentA.setText( Integer.toString( sum )  );*/
                        holder.imgdislike.setVisibility(View.GONE);
                        holder.imgDisLiked.setVisibility(View.VISIBLE);



                    }
                    else
                    {
                        holder.imgdislike.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );





    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("Comment");

    reference1.child(commModel.getPostId()).addValueEventListener( new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                int sum = 0;
                sum = (int) snapshot.getChildrenCount();
                holder.commentA.setText( Integer.toString( sum )  );


            }
            else
            {
                holder.commentA.setText( "0" );
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    } );


    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("like");

    reference2.child(commModel.getPostId()).addValueEventListener( new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                int sum = 0;
                sum = (int) snapshot.getChildrenCount();
                holder.likecountA.setText( Integer.toString( sum )  );


            }
            else
            {
                holder.likecountA.setText( "0" );
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    } );


    DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("Dislike");

    reference3.child(commModel.getPostId()).addValueEventListener( new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                int sum = 0;
                sum = (int) snapshot.getChildrenCount();
                holder.dislikecountA.setText( Integer.toString( sum )  );


            }
            else
            {
                holder.dislikecountA.setText( "0" );
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    } );






}

        @Override
        public int getItemCount() {
        return list.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView NameC, Date_and_Time, DescA, likecountA,dislikecountA,commentA;
    public RelativeLayout CardGet;
    public ImageView imgComm,imgLike,imgLiked,imgdislike,imgDisLiked,imgcomment,PostComm;
    public EditText AddComm;
    public LinearLayout rlAddComm;

    public ViewHolder(View itemView) {
        super(itemView);

        NameC = itemView.findViewById(R.id.NameC);
        Date_and_Time = itemView.findViewById(R.id.Date_and_Time);
        DescA = itemView.findViewById(R.id.DescA);
        likecountA = itemView.findViewById(R.id.likecountA);
        dislikecountA = itemView.findViewById(R.id.dislikecountA);
        commentA = itemView.findViewById(R.id.commentA);
        imgComm = itemView.findViewById(R.id.imgComm);
        imgLike = itemView.findViewById(R.id.imgLike);
        imgLiked = itemView.findViewById(R.id.imgLiked);
        imgdislike = itemView.findViewById(R.id.imgdislike);
        imgDisLiked = itemView.findViewById(R.id.imgDisLiked);
        imgcomment = itemView.findViewById(R.id.imgcomment);
        rlAddComm = itemView.findViewById(R.id.rlAddComm);
        AddComm = itemView.findViewById(R.id.AddComm);
        PostComm = itemView.findViewById(R.id.PostComm);





    }
}
}
