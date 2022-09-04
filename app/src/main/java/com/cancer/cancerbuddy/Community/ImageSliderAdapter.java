// Future development for community forum
/*


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {

    Context context;
    int setTotalCount;
    String Image_links;
    // List<ImageSliderModel> imageSliderModelList;

    public ImageSliderAdapter(Context context, int setTotalCount) {
        this.setTotalCount = setTotalCount;
        this.context = context;
        //  this.imageSliderModelList = imageSliderModelList;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.image_slide , parent, false);
        return new SliderViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final SliderViewHolder viewHolder, final int position) {

        FirebaseDatabase.getInstance().getReference("Feed_Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (position)
                {
                    case 0:
                        Image_links = dataSnapshot.child( "01" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 1:
                        Image_links = dataSnapshot.child( "02" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 2:
                        Image_links = dataSnapshot.child( "03" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 3:
                        Image_links = dataSnapshot.child( "04" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 4:
                        Image_links = dataSnapshot.child( "05" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 5:
                        Image_links = dataSnapshot.child( "06" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 6:
                        Image_links = dataSnapshot.child( "07" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 7:
                        Image_links = dataSnapshot.child( "08" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 8:
                        Image_links = dataSnapshot.child( "09" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;
                    case 9:
                        Image_links = dataSnapshot.child( "10" ).getValue().toString();
                        Glide.with(viewHolder.itemView).load(Image_links).into(viewHolder.sliderImageView);
                        break;




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }

    @Override
    public int getCount() {

        return setTotalCount;
    }
}

class  SliderViewHolder extends SliderViewAdapter.ViewHolder {
    ImageView sliderImageView;
    View itemView;
    public SliderViewHolder(View itemView)
    {
        super(itemView);
        this.itemView = itemView;
        sliderImageView= itemView.findViewById( R.id.imageSlide );

    }
}
*/
