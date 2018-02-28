package com.sohail.spoonfulofhyderabadadmin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SOHAIL on 27/02/18.
 */

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.ViewHolder> {


    List<Orders_Model> orders;
    Context context;

    public Order_Adapter(List<Orders_Model> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public Order_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new Order_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Order_Adapter.ViewHolder holder, int position) {
        holder.titleText.setText(orders.get(position).getTitle());
        holder.hotleName.setText(orders.get(position).getHotel());
        holder.code.setText("Code: " + orders.get(position).getCode());
        holder.userMail.setText(orders.get(position).getUser());
//        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView titleText,hotleName,userMail,code;
//        Button confirmBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            titleText=(TextView)mView.findViewById(R.id.couponTitleTxt);
            hotleName=(TextView)mView.findViewById(R.id.hotelName);
            code=(TextView)mView.findViewById(R.id.code);
            userMail=(TextView)mView.findViewById(R.id.userMail);
//            confirmBtn=(Button) mView.findViewById(R.id.confirmBtn);
        }
    }
}
