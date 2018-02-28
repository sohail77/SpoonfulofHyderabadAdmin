package com.sohail.spoonfulofhyderabadadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG =DetailActivity.class.getSimpleName() ;
    FirebaseFirestore mFirestore;
    Button confirmBtn,finishBtn;
    String code,email,id;
    public TextView titleText,hotleName,userMail,codeTxt,emptyText;
    CardView card,confirmCard;
    Orders_Model order;
    List<Orders_Model> orders_list;
    Order_Adapter order_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        confirmBtn=(Button)findViewById(R.id.confirmBtn);
        titleText=(TextView)findViewById(R.id.couponTitleTxt);
        hotleName=(TextView)findViewById(R.id.hotelName);
        codeTxt=(TextView)findViewById(R.id.code);
        userMail=(TextView)findViewById(R.id.userMail);
        card=(CardView)findViewById(R.id.card);
        confirmCard=(CardView)findViewById(R.id.confirmationCard);
        finishBtn=(Button)findViewById(R.id.finishBtn);
        emptyText=(TextView)findViewById(R.id.emptyText);
        code=getIntent().getStringExtra("code");
        email=getIntent().getStringExtra("email");
        orders_list=new ArrayList<>();
        order_adapter=new Order_Adapter(orders_list,DetailActivity.this);

        mFirestore=FirebaseFirestore.getInstance();
        CollectionReference ref=mFirestore.collection("orders");
        Query query=ref.whereEqualTo("code",code).whereEqualTo("user",email).whereEqualTo("used",false);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e !=null){
                    Log.d(TAG,"Error : " + e.getMessage());
                }
                for (DocumentChange doc:documentSnapshots.getDocumentChanges()){

                    if(doc.getType()==DocumentChange.Type.ADDED) {
                        order=doc.getDocument().toObject(Orders_Model.class);
                        titleText.setText(order.getTitle());
                        hotleName.setText(order.getHotel());
                        codeTxt.setText(order.getCode());
                        userMail.setText("Code: " + order.getUser());
                        id=doc.getDocument().getId();
                        order_adapter.notifyDataSetChanged();

                    }
                }
                if(titleText.getText().toString().equals("")&&hotleName.getText().toString().equals("")&&codeTxt.getText().toString().equals("")&&userMail.getText().toString().equals("")){
                    confirmBtn.setVisibility(View.GONE);
                    card.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                }

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = mFirestore.collection("orders").document(id);

            // Set the "isCapital" field of the city 'DC'
                documentReference
                        .update("used", true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                card.setVisibility(View.GONE);
                                confirmCard.setVisibility(View.VISIBLE);
                                finishBtn.setVisibility(View.VISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailActivity.this,"Error Confirming order try again",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DetailActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
