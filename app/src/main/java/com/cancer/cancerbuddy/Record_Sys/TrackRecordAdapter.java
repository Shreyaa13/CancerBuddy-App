package com.cancer.cancerbuddy.Record_Sys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TrackRecordAdapter extends RecyclerView.Adapter<TrackRecordAdapter.ViewHolder> {

    private Context context;
    private List<TrackRecordModel> list;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID,Str_Infection;







    public TrackRecordAdapter(Context context, List<TrackRecordModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TrackRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.track_record, parent, false);
        return new TrackRecordAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TrackRecordAdapter.ViewHolder holder, int position) {
        TrackRecordModel TrackRecordModel = list.get(position);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        CommRef = FirebaseDatabase.getInstance().getReference().child( "TrackRecord" );
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );



        // getData


        String Id = TrackRecordModel.getId();
        String title = TrackRecordModel.getTitle();



        // setData


        holder.title.setText(title);


        //  holder.event_showR.remove

        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.up.setVisibility(View.GONE);
                holder.desc.setVisibility(View.VISIBLE);
                holder.RadioG.setVisibility(View.VISIBLE);
                holder.L1.setVisibility(View.VISIBLE);
                holder.down.setVisibility(View.VISIBLE);
            }
        });

        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.up.setVisibility(View.VISIBLE);
                holder.desc.setVisibility(View.GONE);
                holder.RadioG.setVisibility(View.GONE);
                holder.L1.setVisibility(View.GONE);
                holder.down.setVisibility(View.GONE);
            }
        });

        holder.btn_regOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Desc = holder.desc.getText().toString();
                if (Desc.isEmpty())
                {
                    Toast.makeText( context, "Please Fill Your Data ", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    final String PostId= ""+System.nanoTime();
                    String saveCurrentDate;
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat( " dd/MM/yyyy" );
                    saveCurrentDate = currentDate.format( calendar.getTime() );

                    HashMap userMap = new HashMap();
                    userMap.put( "Id", PostId );
                    userMap.put( "Title", title );
                    userMap.put( "Desc", Desc );
                    userMap.put( "Infection", Str_Infection );
                    userMap.put( "Date", saveCurrentDate );

                    CommRef.child(currentUserID).child(PostId).updateChildren( userMap ).addOnCompleteListener( new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText( context, "your Record Added..", Toast.LENGTH_LONG ).show();
                                        holder.up.setVisibility(View.VISIBLE);
                                        holder.desc.setVisibility(View.GONE);
                                        holder.RadioG.setVisibility(View.GONE);
                                        holder.L1.setVisibility(View.GONE);
                                        holder.down.setVisibility(View.GONE);
                                        holder.desc.setText("");
                                    }
                                }, 3000);
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText( context, "Error Occured: " + message, Toast.LENGTH_SHORT ).show();

                            }
                        }
                    } );
                }
            }
        });


        holder.btn_regCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.up.setVisibility(View.VISIBLE);
                holder.desc.setVisibility(View.GONE);
                holder.RadioG.setVisibility(View.GONE);
                holder.L1.setVisibility(View.GONE);
                holder.down.setVisibility(View.GONE);

                holder.desc.setText("");

            }
        });

        holder.RadioG.setOnCheckedChangeListener( (group, i) -> {
            holder.setup_for_radiobtn = holder.RadioG.findViewById( i );
            switch (i) {
                case R.id.setup_None:
                    Str_Infection = holder.setup_for_radiobtn.getText().toString();
                    break;
                case R.id.setup_Mild:
                    Str_Infection = holder.setup_for_radiobtn.getText().toString();
                    break;
                case R.id.setup_moderate:
                    Str_Infection = holder.setup_for_radiobtn.getText().toString();
                    break;
                case R.id.setup_severe:
                    Str_Infection = holder.setup_for_radiobtn.getText().toString();
                    break;
                default:
            }

        } );


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView up,down;
        public EditText desc;
        public RadioGroup RadioG;
        public Button btn_regCancle,btn_regOk;
        public RadioButton setup_None,setup_Mild,setup_moderate,setup_severe,setup_for_radiobtn;
        public LinearLayout L1;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            up = itemView.findViewById(R.id.up);
            down = itemView.findViewById(R.id.down);
            desc = itemView.findViewById(R.id.desc);
            RadioG = itemView.findViewById(R.id.RadioG);
            setup_None = itemView.findViewById(R.id.setup_None);
            setup_Mild = itemView.findViewById(R.id.setup_Mild);
            setup_moderate = itemView.findViewById(R.id.setup_moderate);
            setup_severe = itemView.findViewById(R.id.setup_severe);
            btn_regCancle = itemView.findViewById(R.id.btn_regCancle);
            btn_regOk = itemView.findViewById(R.id.btn_regOk);
            L1 = itemView.findViewById(R.id.L1);




        }
    }
}
