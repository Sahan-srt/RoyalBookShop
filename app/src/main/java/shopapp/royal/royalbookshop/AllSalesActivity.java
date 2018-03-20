package shopapp.royal.royalbookshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllSalesActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;

    private RecyclerView myRview;
    private TextView billlview;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private billData billData;
    private List<billData> billList;
    private String uid;
    private ArrayAdapter setlist;
    billAdapter adapter;

    private TextView totalView, cashView, balanceView, dateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sales);

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("All Sales");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalView = (TextView) findViewById(R.id.totalView);

        cashView = (TextView) findViewById(R.id.cashView);
        balanceView = (TextView) findViewById(R.id.balanceView);
        dateView = (TextView) findViewById(R.id.dateView);

        billList = new ArrayList<billData>();
        billList.clear();


        myRview = (RecyclerView) findViewById(R.id.allsalelistview);
        myRview.setHasFixedSize(true);
        myRview.setLayoutManager(new LinearLayoutManager(this));


        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();

        mRef = FirebaseDatabase.getInstance().getReference("Bills");
        Query allsale = mRef.orderByChild("Uid").equalTo(uid).limitToLast(50);

        allsale.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String t = dataSnapshot.child("Total").getValue().toString();
                    String c = dataSnapshot.child("Cash").getValue().toString();

                    String b = dataSnapshot.child("Balance").getValue().toString();
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd  'Time' HH:mm:ss ");
                    String d = dateformat.format(dataSnapshot.child("DateTime").getValue()).toString();

                    billList.add(new billData(t, c, b, d));

                Collections.reverse(billList);
                adapter = new billAdapter(AllSalesActivity.this, (ArrayList<billData>) billList);
                myRview.setAdapter(adapter);


                // items.add("Total    :"+t+"\nCash    :"+c+"\nBalance  :"+b+"\nDate    :"+d);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String t = dataSnapshot.child("Total").getValue().toString();
                String c = dataSnapshot.child("Cash").getValue().toString();
                String b = dataSnapshot.child("Balance").getValue().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd  'Time' HH:mm:ss ");
                String d = dateformat.format(dataSnapshot.child("DateTime").getValue()).toString();

                billList.add(new billData(t, c, b, d));

                Collections.reverse(billList);
                adapter = new billAdapter(AllSalesActivity.this, (ArrayList<billData>) billList);
                myRview.setAdapter(adapter);


                //  items.add("Total    :"+t+"\nCash    :"+c+"\nBalance  :"+b+"\nDate    :"+d);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String t = dataSnapshot.child("Total").getValue().toString();
                String c = dataSnapshot.child("Cash").getValue().toString();
                String b = dataSnapshot.child("Balance").getValue().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd  'Time' HH:mm:ss ");
                String d = dateformat.format(dataSnapshot.child("DateTime").getValue()).toString();

                billList.add(new billData(t, c, b, d));

                Collections.reverse(billList);
                adapter = new billAdapter(AllSalesActivity.this, (ArrayList<billData>) billList);
                myRview.setAdapter(adapter);

                // items.add("Total    :"+t+"\nCash    :"+c+"\nBalance  :"+b+"\nDate    :"+d);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                String t = dataSnapshot.child("Total").getValue().toString();
                String c = dataSnapshot.child("Cash").getValue().toString();
                String b = dataSnapshot.child("Balance").getValue().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd  'Time' HH:mm:ss ");
                String d = dateformat.format(dataSnapshot.child("DateTime").getValue()).toString();

                billList.add(new billData(t, c, b, d));

                Collections.reverse(billList);
                adapter = new billAdapter(AllSalesActivity.this, (ArrayList<billData>) billList);
                myRview.setAdapter(adapter);

                // items.add("Total    :"+t+"\nCash    :"+c+"\nBalance  :"+b+"\nDate    :"+d);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        LocalBroadcastManager.getInstance(this).registerReceiver(getdatafrombilladapter,
                new IntentFilter("Clickbill"));


    }
    public  BroadcastReceiver getdatafrombilladapter= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            totalView.setText("LKR "+intent.getStringExtra("T"));
            cashView.setText(intent.getStringExtra("C"));
            balanceView.setText(intent.getStringExtra("B"));
            dateView.setText(intent.getStringExtra("D"));


        }};


        }
