/*
package ithub.iinventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_ItemsActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersReference;

    private ImageButton mSearchButton;
    private EditText mSearchItemID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__items);

        mAuth = FirebaseAuth.getInstance();

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("All Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchButton = (ImageButton)findViewById(R.id.searchButton);
        mSearchItemID = (EditText)findViewById(R.id.searchItemID);

        final ArrayList<ExmapleItem> exampleItem = new ArrayList<>();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemID = mSearchItemID.getText().toString();
                usersReference = FirebaseDatabase.getInstance().getReference().child("Items").child(itemID);

                usersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String itemName = dataSnapshot.child("iName").getValue().toString();
                        String itemPrice = dataSnapshot.child("iPrice").getValue().toString();

                        exampleItem.add(new ExmapleItem(itemName, itemPrice));
                        mRecyclerView = findViewById(R.id.itemListView);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(All_ItemsActivity.this);
                        mAdapter = new ExampleAdapter(exampleItem);

                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });






     */
/*  ArrayList<ExmapleItem> exampleItem  = new ArrayList<>();
        exampleItem.add(new ExmapleItem("Marvo Keyboard","LKR 2500/="));
        exampleItem.add(new ExmapleItem("Marvo Mouse","LKR 1500/="));
        exampleItem.add(new ExmapleItem("Samsung SSD EVO 250GB","LKR 25000/="));
        exampleItem.add(new ExmapleItem("Samsung Monitor 24","LKR 35000/="));
        exampleItem.add(new ExmapleItem("Gaming Pad","LKR 250/="));
        exampleItem.add(new ExmapleItem("Gaming Headset Marvo","LKR 35000/="));
        exampleItem.add(new ExmapleItem("Huawei Power Bank 10000mAH","LKR 5000/="));
        exampleItem.add(new ExmapleItem("AC Power Adapter","LKR 25000/="));
        exampleItem.add(new ExmapleItem("Apple Magic Mouse","LKR 11000/="));
        exampleItem.add(new ExmapleItem("HyperX 8GB Memory","LKR 10000/="));
        exampleItem.add(new ExmapleItem("Toshiba 1TB Portable","LKR 11000/="));
        exampleItem.add(new ExmapleItem("Gaming Bag Pack","LKR 5500/="));
        exampleItem.add(new ExmapleItem("Gaming Bag Pack XL","LKR 6000/="));

        mRecyclerView = findViewById(R.id.itemListView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleItem);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);*//*





    }
}
*/
package shopapp.royal.royalbookshop;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_ItemsActivity extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView result_list;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> itemnamelist;
    ArrayList<String> itempricelist;
    ArrayList<String> itemid;





    items items;

    private android.support.v7.widget.Toolbar nToolBar;


    private DatabaseReference mitemsDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__items);

        mAuth = FirebaseAuth.getInstance();

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("All Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        search_edit_text = (EditText) findViewById(R.id.searchItemID);
        result_list = (RecyclerView) findViewById(R.id.itemListView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        result_list.setHasFixedSize(true);
        result_list.setLayoutManager(new LinearLayoutManager(this));
        result_list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        itemnamelist = new ArrayList<>();
        itempricelist = new ArrayList<>();
        itemid = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                }else {setAdapter(null);}

            }
        });








    }


    private void setAdapter(final String searchedstring) {
        if (searchedstring!=null) {

            itemnamelist.clear();
            itempricelist.clear();
            itemid.clear();

            result_list.removeAllViews();
            databaseReference.child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int counter = 0;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String uid = snapshot.getKey();
                        String iName = snapshot.child("iName").getValue(String.class);

                        String iPrice = snapshot.child("iPrice").getValue(String.class);
                        String iID = snapshot.child("iID").getValue(String.class);


                        if (iName.toLowerCase().contains(searchedstring)) {
                            itemnamelist.add(iName);
                            itempricelist.add(iPrice);
                            itemid.add(iID);


                            counter++;
                        } else if (iPrice.contains(searchedstring)) {
                            itemnamelist.add(iName);
                            itempricelist.add(iPrice);


                            counter++;
                        }

                        if (counter == 15) {
                            break;
                        }


                    }
                    items = new items(All_ItemsActivity.this, itemnamelist, itempricelist, itemid);
                    result_list.setAdapter(items);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {
            result_list.setAdapter(null);
            itemnamelist.clear();
            itempricelist.clear();
            itemid.clear();

        }


    }

   /* private void showUpdateDialog(String itemid, String itemname, String itemqty, String itemprice) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

      //  final View dialogView = inflater.inflate(R.layout.activity_update, null);

        dialogBuilder.setView(dialogView);


        final EditText editTextId = (EditText) dialogView.findViewById(R.id.editTextId);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextqty = (EditText) dialogView.findViewById(R.id.editTextQty);
        final EditText editTextprice = (EditText) dialogView.findViewById(R.id.editTextprice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);

        dialogBuilder.setTitle("Updating Item" + itemid);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }*/





}





