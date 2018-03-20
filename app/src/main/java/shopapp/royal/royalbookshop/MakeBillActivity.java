package shopapp.royal.royalbookshop;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MakeBillActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;

    private TextView mItemID , mQty, mItemName, mItemPrice,QTY;

    //took this textView as a static value because nedded from recyclerView
    private static TextView mTotalPrice;

    private FirebaseAuth mAuth;


    private DatabaseReference mRef;

    private Button mAddToBill;
    private Button mContinue;

    private  EditText mCash;

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private CardView mCardView;

    //    public LayoutInflater inflater = getLayoutInflater();
    DatabaseReference rootRef, itemRef;

    public LayoutInflater inflater;
    public View v;

    private Button mRemoveItem;
    private ImageButton add,minus;

    //searh bar
    EditText search_edit_text;
    RecyclerView result_list;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> itemnamelist;
    ArrayList<String> itempricelist;
    ArrayList<String> itemid;
    itemsmakebill itemsmakebill;



    // ViewGroup parent;
    static int sum=0;


    static int qty=1;

   
    private   String ItemName ,ItemPrice;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_bill);


        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();

        //database reference pointing to child node
        itemRef = rootRef.child("Items");

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("Make Bill");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        search_edit_text = (EditText) findViewById(R.id.searchItemIDinBill);
        result_list = (RecyclerView) findViewById(R.id.itemListViewForSearch);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        result_list.setHasFixedSize(true);
        result_list.setLayoutManager(new LinearLayoutManager(this));
        result_list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        itemnamelist = new ArrayList<>();
        itempricelist = new ArrayList<>();
        itemid = new ArrayList<>();
        //add=(ImageButton)findViewById(R.id.Add);
       // minus=(ImageButton)findViewById(R.id.Minus);
      //  QTY=(TextView)findViewById(R.id.QTY);




        LocalBroadcastManager.getInstance(this).registerReceiver(getdatafromadapter,
                new IntentFilter("itemDetails"));




       
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


        final List list = new ArrayList();

        mItemID = (TextView)findViewById(R.id.itemID);
       // mQty = (TextView)findViewById(R.id.qty);

       // mAddToBill = (Button)findViewById(R.id.addToBillBtn);
        mTotalPrice = (TextView)findViewById(R.id.totalPriceView);
       // mRemoveItem = (Button) findViewById(R.id.removeItem);
      //  mItemName = (TextView) findViewById(R.id.makebillitemName);
       // mItemPrice = (TextView) findViewById(R.id.itemPrice);
        mContinue = (Button) findViewById(R.id.contBtn);
        mCash = (EditText) findViewById(R.id.cashEdit);
      //  mCardView = findViewById(R.id.myCardView);
     //   mRemoveItem = mCardView.findViewById(R.id.removeItem);
  //      mItemPrice = mCardView.findViewById(R.id.itemPrice);
        //////////////////****************/////////
        // here child node of items taken and called according to their model number
        // modelRef = itemRef.child(mItemID.getText().toString());

        ///////////********************////////////

        final ArrayList<ExmapleItem> exampleItem  = new ArrayList<>();


        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCash.getText().toString().isEmpty()) {
                    Double Total = Double.parseDouble(mTotalPrice.getText().toString());
                    Double Cash = Double.parseDouble(mCash.getText().toString());

                    if (Total <= Cash) {
                        if (!mCash.getText().toString().isEmpty()) {
                            int cash = (Integer.parseInt(mCash.getText().toString().trim()) - Integer.parseInt(mTotalPrice.getText().toString().trim()));

                            Intent intent = new Intent(MakeBillActivity.this, CustomLayout.class);
                            Bundle extras = new Bundle();
                            extras.putString("EXTRA_TOTAL", mTotalPrice.getText().toString());
                            extras.putString("EXTRA_CASH", mCash.getText().toString());
                            extras.putString("EXTRA_BALANCE", Integer.toString(cash));


                            intent.putExtras(extras);
                            startActivity(intent);
                            finish();







                        }
                    } else {
                        Toast.makeText(MakeBillActivity.this, "Entered Value is Not sufficient ", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(MakeBillActivity.this, "You must enter the Cash value before continue", Toast.LENGTH_SHORT).show();
                }





            }
        });








    }//end of  on create method

    //item click ->list to cart

    final ArrayList<ExmapleItem> exampleItem  = new ArrayList<>();

    public  BroadcastReceiver getdatafromadapter= new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
         String name=intent.getStringExtra("Name");
      String  price=intent.getStringExtra("Price");
        String  id=intent.getStringExtra("Id");
        String  previoussum=intent.getStringExtra("Total");
        sum =Integer.parseInt(previoussum);
        String nquantity=intent.getStringExtra("Quantity");




        if (name!=null && price!=null){

            ItemName=name;
            ItemPrice=price;

            // Toast.makeText(MakeBillActivity.this,String.valueOf(ItemName),Toast.LENGTH_LONG).show();



            exampleItem.add(new ExmapleItem(ItemName, ItemPrice,id));



            mRecyclerView = findViewById(R.id.itemListView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(MakeBillActivity.this);
            mAdapter = new ExampleAdapter(MakeBillActivity.this,exampleItem);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            sum += Integer.parseInt(ItemPrice);
            mTotalPrice.setText(sum+"");
            BillingItems.billItemArray.clear();
           // Toast.makeText(MakeBillActivity.this,id,Toast.LENGTH_LONG).show();






        }


    }
};



    ///here this is used to update textView from RecyclerView class
    public static  void updateCounter(String value){
        try{
            mTotalPrice.setText(value);
        }

        catch (Exception e){

        }
    }

    ///used to set and get the sum value...
    public int getSum(){
        return sum;
    }


    public void setSum(int sum){
        this.sum = sum;
    }
    public static int getQty() {
        return qty;
    }

    public static void setQty(int qty) {
        MakeBillActivity.qty = qty;
    }





    //search bar


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

                            if (counter == 15)
                                break;




                        }
                        itemsmakebill = new itemsmakebill(MakeBillActivity.this, itemnamelist, itempricelist, itemid);
                        result_list.setAdapter(itemsmakebill);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }else {
                itemnamelist.clear();
                itempricelist.clear();

                result_list.removeAllViews();
                result_list.setAdapter(null);}

    }


}
