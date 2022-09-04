package com.cancer.cancerbuddy.medicine;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cancer.cancerbuddy.AlarmActivity;
import com.cancer.cancerbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder>   {


    private Context context;
    private List<MedicineModel> list;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;

    Dialog SetEditDialog,SetPostDose;


    TextView txt_1,txt_2,txt_3,txtid,CountDoseAlarm;
    Button btn_edt1,setAlarmDose;
    EditText CountDoseAlarmInput;

    String txtId;






    public MedicineAdapter(Context context, List<MedicineModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mmrecy, parent, false);
        return new MedicineAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, int position) {
        MedicineModel MedicineModel = list.get(position);

        SetEditDialog = new Dialog(context);
        SetPostDose = new Dialog(context);



        SetEditDialog.setContentView(R.layout.dialog_post_edit);
        SetEditDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        SetEditDialog.setCancelable(true);
        SetEditDialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        txt_1 = SetEditDialog.findViewById(R.id.txt_1);
        txt_2 = SetEditDialog.findViewById(R.id.txt_2);
        txt_3 = SetEditDialog.findViewById(R.id.txt_3);
        txtid = SetEditDialog.findViewById(R.id.txtid);
        btn_edt1 = SetEditDialog.findViewById(R.id.btn_edt11);






        SetPostDose.setContentView(R.layout.dialog_input_alarm);
        SetPostDose.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        SetPostDose.setCancelable(true);
        SetPostDose.getWindow().getAttributes().windowAnimations = R.style.animation;


        CountDoseAlarm = SetPostDose.findViewById(R.id.doseidr);
        CountDoseAlarmInput = SetPostDose.findViewById(R.id.doseInput);
        setAlarmDose = SetPostDose.findViewById(R.id.doseSave1);







        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        CommRef = FirebaseDatabase.getInstance().getReference().child( "Alarm" );
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );



        CommRef.child(currentUserID).child(MedicineModel.getTxtId()).child("Alarm").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String alarmM =snapshot.child( "Alarm" ).getValue().toString();

                    holder.dose1.setText(alarmM);

                    holder.setAlarmSet.setVisibility(View.VISIBLE);
                    holder.dose1.setVisibility(View.VISIBLE);
                    holder.setAlarm.setVisibility(View.GONE);
                }
                else
                {

                    holder.setAlarmSet.setVisibility(View.GONE);
                    holder.dose1.setVisibility(View.GONE);
                    holder.setAlarm.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // getData


         txtId = MedicineModel.getTxtId();
        String txt1 = MedicineModel.getTxtA();
        String txt2 = MedicineModel.getTxtB();

     //   String alarm = MedicineModel.getAlarm();


        // setData
        txt_1.setText(txt1);
        txt_2.setText(txt2);

        txtid.setText(txtId);


        holder.MMp1.setText( txt1 );
        holder.MMp2.setText( txt2 );
       // holder.dose1.setText( alarm );




        holder.setedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SetEditDialog.show();

            }
        });

        holder.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(context, AlarmActivity.class);
               // context.startActivity(intent);

               // SetPostDose.show();

                Intent intent = new Intent(context, AlarmActivity.class);
                intent.putExtra("Id",MedicineModel.getTxtId());

                context.startActivity(intent);


            }
        });


        holder.setAlarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent(context, AlarmActivity.class);
                // context.startActivity(intent);

                // SetPostDose.show();

            /*    Intent intent = new Intent(context, AlarmActivity.class);
                intent.putExtra("Id",MedicineModel.getTxtId());

                context.startActivity(intent);*/



                AlertDialog.Builder dialog=new AlertDialog.Builder(context);
               // dialog.setMessage("Please Select any option");
                dialog.setTitle("Cancel Alarm");
                dialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {


                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Alarm" );
                                reference.child(currentUserID).child(MedicineModel.getTxtId()).child("Alarm").removeValue();
                                Toast.makeText(context,"Alarm off",Toast.LENGTH_LONG).show();
                            }
                        });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(context,"cancel is clicked",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();



            }
        });




    }


    @Override
    public int getItemCount() {
        return list.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView MMp1, MMp2,dose1,dose2,dose3;
        public Button btn_set_alarm;
        public ImageView setAlarm,setedit,setAlarmSet;

        public ViewHolder(View itemView) {
            super(itemView);

            setedit = itemView.findViewById(R.id.setedit);
            setAlarm = itemView.findViewById(R.id.setAlarm);
            setAlarmSet = itemView.findViewById(R.id.setAlarmSet);
            MMp1 = itemView.findViewById(R.id.MMp1);
            MMp2 = itemView.findViewById(R.id.MMp2);
            dose1 = itemView.findViewById(R.id.dose1);
            dose2 = itemView.findViewById(R.id.dose2);
            dose3 = itemView.findViewById(R.id.dose3);



        }
    }
}
