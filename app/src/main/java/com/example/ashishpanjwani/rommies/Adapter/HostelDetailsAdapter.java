package com.example.ashishpanjwani.rommies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashishpanjwani.rommies.Interfaces.ClickListener;
import com.example.ashishpanjwani.rommies.Models.HostelList;
import com.example.ashishpanjwani.rommies.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class HostelDetailsAdapter extends RecyclerView.Adapter<HostelDetailsAdapter.DetailsViewHolder> {

    private static int ownerId;
    private Context context;
    private List<HostelList> hostelList;
    private Typeface typeface1,typeface2,typeface3,typeface4,typeface5,typeface6;

    public HostelDetailsAdapter(Context context, List<HostelList> hostelList) {
        this.context=context;
        this.hostelList=hostelList;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.hostel_details,parent,false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, final int position) {
        final HostelList hostel=hostelList.get(position);
        holder.hostelName.setText(hostel.getHname());
        holder.hostelAddress.setText(hostel.getHaddress());
        if (!"".equals(hostel.getImgurl())) {
            Picasso.with(context).load(""+hostel.getImgurl())
                    .into(holder.hostelImage);
        } else
            holder.hostelImage.setImageResource(R.drawable.no_image2);

        holder.hostelRent.setText("Rs."+hostel.getRent());
        holder.feature1.setText("* "+hostel.getFeature1());
        holder.feature2.setText("* "+hostel.getFeature2());
        holder.feature3.setText("* "+hostel.getFeature3());
        holder.ownerName.setText(hostel.getName());
        holder.ownerAddress.setText(hostel.getAddress());
        holder.ownerNumber.setText("+91"+hostel.getPhone());

        holder.ownerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo="+91"+hostel.getPhone();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNo));
                context.startActivity(intent);
            }
        });

        holder.ownerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=hostel.getAddress();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+address));
                context.startActivity(intent);
            }
        });

        holder.hostelAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=hostel.getAddress();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+address));
                context.startActivity(intent);
            }
        });

        ownerId=hostel.getOwnerId();
    }

    public static int returnOwnerId() {
        return ownerId;
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {

        TextView hostelName;
        TextView hostelAddress;
        TextView feature1;
        TextView feature2;
        TextView feature3;
        TextView hostelRent;
        TextView ownerName;
        TextView ownerAddress;
        TextView ownerNumber;
        TextView ownerStatic;
        ImageView hostelImage;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            hostelName=itemView.findViewById(R.id.hostel_name);
            hostelAddress=itemView.findViewById(R.id.hostel_address);
            feature1=itemView.findViewById(R.id.feature1);
            feature2=itemView.findViewById(R.id.feature2);
            feature3=itemView.findViewById(R.id.feature3);
            hostelRent=itemView.findViewById(R.id.hostel_rent);
            hostelImage=itemView.findViewById(R.id.hostel_image);
            ownerName=itemView.findViewById(R.id.owner_name);
            ownerAddress=itemView.findViewById(R.id.owner_address);
            ownerNumber=itemView.findViewById(R.id.owner_number);
            ownerStatic=itemView.findViewById(R.id.owner_static);

            typeface1=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/GOTHICB.TTF");
            typeface2=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/GOTHIC.TTF");
            typeface3=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/GOTHICB.TTF");
            typeface4=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/CenturyGothic.ttf");

            this.hostelName.setTypeface(typeface1);
            this.hostelAddress.setTypeface(typeface2);
            this.hostelRent.setTypeface(typeface3);
            this.feature1.setTypeface(typeface4);
            this.feature2.setTypeface(typeface4);
            this.feature3.setTypeface(typeface4);
            this.ownerStatic.setTypeface(typeface1);
            this.ownerName.setTypeface(typeface2);
            this.ownerAddress.setTypeface(typeface2);
            this.ownerNumber.setTypeface(typeface4);
        }
    }
}
