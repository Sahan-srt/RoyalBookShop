package shopapp.royal.royalbookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateItemAcitivity extends AppCompatActivity implements View.OnClickListener {

    private android.support.v7.widget.Toolbar nToolBar;
    EditText ItemName,ItemPrice,ItemQ;
    TextView ItemId;
    private Button Remove,Update;
    String id;


    FirebaseDatabase mDB;
    DatabaseReference mRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item_acitivity);

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("Update Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         id=getIntent().getStringExtra("Id");
        ItemId=(TextView) findViewById(R.id.itemID);
        ItemId.setText(id);

        mDB=FirebaseDatabase.getInstance();
        mRef=mDB.getReference("Items");

        ItemName=(EditText)findViewById(R.id.newItemName);
        ItemPrice=(EditText)findViewById(R.id.newItemPrice);
        ItemQ=(EditText)findViewById(R.id.newItemQty);
        Remove=(Button)findViewById(R.id.uremoveIetmBtn);
        Remove.setOnClickListener(this);
        Update=(Button)findViewById(R.id.updateItemBtn);
        Update.setOnClickListener(this);
        mRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    String name = dataSnapshot.child("iName").getValue().toString();
                    String quentity = dataSnapshot.child("iQ").getValue().toString();
                    String price = dataSnapshot.child("iPrice").getValue().toString();

                    ItemName.setText(name);
                    ItemPrice.setText(price);
                    ItemQ.setText(quentity);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.uremoveIetmBtn){


            Intent call = new Intent(UpdateItemAcitivity.this,MainActivity.class);
            call.putExtra("Rid",String.valueOf(ItemId.getText()));

             startActivity(call);
           // Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();







        }

        if (v.getId()==R.id.updateItemBtn){

            mRef.child(String.valueOf(ItemId.getText())).child("iName").setValue(String.valueOf(ItemName.getText()));

            mRef.child(String.valueOf(ItemId.getText())).child("iPrice").setValue(String.valueOf(ItemPrice.getText()));

            mRef.child(String.valueOf(ItemId.getText())).child("iQ").setValue(String.valueOf(ItemQ.getText()));
            Toast.makeText(this,String.valueOf(ItemQ.getText()),Toast.LENGTH_SHORT).show();


        }



    }
}
