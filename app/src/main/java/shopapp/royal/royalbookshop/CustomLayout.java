package shopapp.royal.royalbookshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomLayout extends AppCompatActivity {

    TextView totalValue;
    TextView cashValue;
    TextView balanceValue;
    Button saveContBtn;
    Button cancelBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private android.support.v7.widget.Toolbar nToolBar;


    private DatabaseReference mDatabase ;

    private ProgressDialog mRegProg;
    private FirebaseAuth mAuth;
    private String BillId,uid;
    private Date billdate;
    private ListView billitem;
    private ArrayList<ExmapleItem> getexamplelis;
     private Double key;
    private ExampleAdapter mAdapter;
    private ArrayList<ExmapleItem> exampleItem;
    private  String ftotal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout);

        mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Bills").child(mAuth.getCurrentUser().getUid()).push();

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        final TextView dateView = (TextView) findViewById(R.id.dateView);
        dateView.setText(currentDate);

        getSupportActionBar().setTitle("Bill Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        totalValue = (TextView) findViewById(R.id.totalValue);
        cashValue = (TextView) findViewById(R.id.cashValue);
        balanceValue = (TextView) findViewById(R.id.balanceValue);
        saveContBtn = (Button) findViewById(R.id.saveAndContBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        billitem=(ListView)findViewById(R.id.billItemlist);
        uid=mAuth.getCurrentUser().getUid();







        BillingItems tobill=new BillingItems();

         ArrayList mylist= tobill.billItemArray;


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mylist);
          billitem.setAdapter(adapter);


        //end ->edit sahan
        Bundle extras = getIntent().getExtras();
        String totalString = extras.getString("EXTRA_TOTAL");
        final String cashString = extras.getString("EXTRA_CASH");
        String balanceString = extras.getString("EXTRA_BALANCE");

        ftotal=totalString;

        totalValue.setText("LKR " + totalString );
        cashValue.setText("LKR " + cashString );
        balanceValue.setText("LKR " + balanceString );

       // final String time = ServerValue.TIMESTAMP.toString();

        saveContBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String billTotal = ftotal.toString();
                String billCash = cashValue.getText().toString();
                String billBalance = balanceValue.getText().toString();
                String time = ServerValue.TIMESTAMP.toString();

               BillId= UUID.randomUUID().toString();



                billdate=Calendar.getInstance().getTime();





                mDatabase = FirebaseDatabase.getInstance().getReference().child("Bills");
              /*  mRegProg.setTitle("Save Bill");
                mRegProg.setMessage("Please wait while save bill details");
                mRegProg.setCanceledOnTouchOutside(false);
                mRegProg.show();*/
                addIetmToDatabase(billTotal, billCash, billBalance);



                /*Intent intent = new Intent(CustomLayout.this, PrinterService.class);
                startActivity(intent);
                finish(); */
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomLayout.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

        //add item to database

    private void addIetmToDatabase(String newTotal , String newCash , String newBalance ){


        final DatabaseReference newRef = mDatabase.push();
        //final Map noteMap = new HashMap();



        final Map noteMap = new HashMap();
        noteMap.put("Uid",uid );
        noteMap.put("Total", newTotal);
        noteMap.put("Cash", newCash);
        noteMap.put("Balance",newBalance );
        noteMap.put("DateTime",ServerValue.TIMESTAMP);
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd");
        String dateset=df.format(date);
        noteMap.put("Date",dateset );






        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.push().setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //mRegProg.dismiss();

                        if (task.isSuccessful()) {

                            Toast.makeText(CustomLayout.this, "Bill Saved !", Toast.LENGTH_SHORT).show();
                            Intent showHomePanel = new Intent(CustomLayout.this,PrinterService.class);
                            startActivity(showHomePanel);

                        } else {
                            Toast.makeText(CustomLayout.this, "Error ! Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        mainThread.start();



    }

}
