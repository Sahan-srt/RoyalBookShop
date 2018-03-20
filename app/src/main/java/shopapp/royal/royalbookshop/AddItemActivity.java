package shopapp.royal.royalbookshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;

    private Button mAddItemToDb;

    private TextView mItemID , mItemName , mItemQty , mItemPrice;

    private DatabaseReference mDatabase ;

    private FirebaseAuth mAuth;

    private ProgressDialog mRegProg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mRegProg = new ProgressDialog(this  );


        mAuth = FirebaseAuth.getInstance();
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Items").child(mAuth.getCurrentUser().getUid()).push();


        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("Add Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddItemToDb = (Button)findViewById(R.id.addItemBtn);
        mItemID = (TextView) findViewById(R.id.itemID);
        mItemName = (TextView)findViewById(R.id.newItemName);
        mItemQty = (TextView)findViewById(R.id.newItemQty);
        mItemPrice = (TextView)findViewById(R.id.newItemPrice);


        mAddItemToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemID = mItemID.getText().toString();
                String itemName = mItemName.getText().toString();
                String itemQty = mItemQty.getText().toString();
                String itemPrice = mItemPrice.getText().toString();

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Items").child(itemID);


                if(!TextUtils.isEmpty(itemID) || TextUtils.isEmpty(itemName) ||
                        TextUtils.isEmpty(itemQty) || TextUtils.isEmpty(itemPrice)){
                    mRegProg.setTitle("Adding Item");
                    mRegProg.setMessage("Please wait while add item details");
                    mRegProg.setCanceledOnTouchOutside(false);
                    mRegProg.show();

                    addIetmToDatabase(itemID,itemName,itemQty,itemPrice);




                }else{
                    Snackbar.make(view,"Fill empty fields",Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }

    //add item to database

    private void addIetmToDatabase(String newitemid , String newitemname , String newitemqty , String newitemprice){


        final DatabaseReference newRef = mDatabase.push();
        //final Map noteMap = new HashMap();



        final Map noteMap = new HashMap();
        noteMap.put("iID", newitemid);
        noteMap.put("iName", newitemname);
        noteMap.put("iPrice",newitemprice );
        noteMap.put("iQ",newitemqty );

        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        mRegProg.dismiss();

                        if (task.isSuccessful()) {

                            Toast.makeText(AddItemActivity.this, "Item Added !", Toast.LENGTH_SHORT).show();
                            Intent showHomePanel = new Intent(AddItemActivity.this,MainActivity.class);
                            startActivity(showHomePanel);

                        } else {
                            Toast.makeText(AddItemActivity.this, "Error ! Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        mainThread.start();



    }


}
