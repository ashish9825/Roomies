package com.example.ashishpanjwani.rommies.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashishpanjwani.rommies.Interfaces.CustomHostelClickListener;
import com.example.ashishpanjwani.rommies.Models.HostelList;
import com.example.ashishpanjwani.rommies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder> implements Filterable {

    private Context context;
    private List<HostelList> hostels;
    private List<HostelList> filteredHostels;
    private static int hostelID;
    CustomHostelClickListener listener;

    public HostelAdapter(Context context, List<HostelList> hostels, CustomHostelClickListener listener) {
        this.context = context;
        this.hostels = hostels;
        this.filteredHostels=hostels;
        this.listener=listener;
    }

    @Override
    public HostelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.hostel_names,parent,false);
        final HostelViewHolder mViewHolder=new HostelViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHostelClick(v,mViewHolder.getLayoutPosition());
            }
        });
        return mViewHolder;
        //return new HostelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HostelViewHolder holder, final int position) {

        HostelList hostel=hostels.get(position);
        holder.hostelName.setText(hostel.getHname());
        holder.hostelAddress.setText(hostel.getHaddress());
        if (!"".equals(hostel.getImgurl())) {
            Picasso.with(context).load(""+hostel.getImgurl())
                    .into(holder.imageView);
        } else
            holder.imageView.setImageResource(R.drawable.no_image2);
        hostelID=hostel.getHid();
        Log.d("TAG :",""+hostelID);
    }

    public static int getHostelId() {
        return hostelID;
    }


    @Override
    public int getItemCount() {
        return hostels.size();
    }

    //Filter method
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString=constraint.toString();
                if (charString.isEmpty()) {
                    filteredHostels=hostels;
                }
                else {
                    List filteredList=new ArrayList<>();
                    for (HostelList locations : hostels) {

                        if (locations.getHaddress().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(locations);
                        }
                    }

                    filteredHostels=filteredList;
                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=filteredHostels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredHostels= (List<HostelList>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class HostelViewHolder extends RecyclerView.ViewHolder {

        Typeface typeface1,typeface2;

        TextView hostelName;
        TextView hostelAddress;
        ImageView imageView;
        public HostelViewHolder(View itemView) {
            super(itemView);
            hostelName=itemView.findViewById(R.id.hostel_naam);
            hostelAddress=itemView.findViewById(R.id.hostel_pata);
            imageView=itemView.findViewById(R.id.hostel_photo);

            hostelName.setTextColor(Color.WHITE);
            hostelAddress.setTextColor(Color.WHITE);

            typeface1=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/Coco-Gothic-Regular-trial.ttf");
            typeface2=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/CenturyGothic.ttf");

            this.hostelName.setTypeface(typeface1);
            this.hostelAddress.setTypeface(typeface2);
        }
    }

}
