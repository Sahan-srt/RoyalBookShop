package shopapp.royal.royalbookshop;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar mToolbar;




    private DatabaseReference usersReference;

    private TextView mUserName;

    private FirebaseUser mCurrentUser;

    private CardView mCardview1,mainAddItem,mMakeBill, mAllItems ,mChatBtn;
    private CardView mCardView2;
    private  String Rid,Totalsale;
    String loggedUserName;
    private  TextView lastsaleview,allsales,allsaleview;
    private  String Balance,Total,Cash;
    private String date;
    private FirebaseUser loggeduser;
    int Btotal;


    @Override
    public void onStart() {
        super.onStart();

        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null) {
                sendToStart();
            } else {
                final String userID = mCurrentUser.getUid();

                usersReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                usersReference.keepSynced(true);

                usersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        loggedUserName = dataSnapshot.child("name").getValue().toString();
                        mUserName.setText(loggedUserName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }

    }
    //newtest


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
*/
       /* TextView dateView = (TextView) findViewById(R.id.dateView);
        dateView.setText(currentDate);
*/

        mAuth = FirebaseAuth.getInstance();
        Btotal=0;

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Royal BookShop");

        mainAddItem = (CardView) findViewById(R.id.mainAddItem);
        mMakeBill = (CardView) findViewById(R.id.mainMakeBill);
        mAllItems = (CardView) findViewById(R.id.mainAllItem);
        mChatBtn = (CardView) findViewById(R.id.chatButton);
        mUserName = (TextView) findViewById(R.id.loggedUser);
        mCardview1 = (CardView) findViewById(R.id.cardView);
       // mCardView2 = (CardView) findViewById(R.id.cardView2);

        mCurrentUser = mAuth.getCurrentUser();
        lastsaleview = (TextView) findViewById(R.id.lastSale);
     //   allsaleview = (TextView) findViewById(R.id.allsaleview);
     //   allsales = (TextView) findViewById(R.id.allsales);
        final FirebaseDatabase lastsale = FirebaseDatabase.getInstance();

        DatabaseReference billRef = lastsale.getReference();
        loggeduser = mAuth.getCurrentUser();

        if (loggeduser != null){

            String user=loggeduser.getUid();


            Query lastsalebill = billRef.child("Bills").orderByChild("Uid").equalTo(user).limitToLast(1);

        lastsalebill.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Total = dataSnapshot.child("Total").getValue().toString();
                lastsaleview.setText("LKR "+Total + "/=");
                Balance = dataSnapshot.child("Balance").getValue().toString();
                Cash = dataSnapshot.child("Cash").getValue().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss ");
                date = dateformat.format(dataSnapshot.child("DateTime").getValue());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
              /* for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String LS=ds.child("Total").getValue().toString();
                    lastsaleview.setText("Latest Sale:\n"+LS+" /=");
                     Balance=ds.child("Balance").getValue().toString();
                     Cash=ds.child("Cash").getValue().toString();
               //      Total=ds.child("Total").getValue().toString();
                  date= (long) ds.child("DateTime").getValue();


                } */
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
               /* for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String LS=ds.child("Total").getValue().toString();
                    lastsaleview.setText("Latest Sale:\n"+LS+" /=");
                    Balance=ds.child("Balance").getValue().toString();
                    Cash=ds.child("Cash").getValue().toString();
                //    Total=ds.child("Total").getValue().toString();
                    date= (long) ds.child("DateTime").getValue();


                } */

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
               /* for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String LS=ds.child("Total").getValue().toString();
                    lastsaleview.setText("Latest Sale:\n"+LS+" /=");
                    Balance=ds.child("Balance").getValue().toString();
                    Cash=ds.child("Cash").getValue().toString();
                  //  Total=ds.child("Total").getValue().toString();
                    date= (long) ds.child("DateTime").getValue();


                }*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            final Query allsaledisplay = billRef.child("Bills").orderByChild("Uid").equalTo(user);

            allsaledisplay.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()){

                        int sumt=0;

                        for (DataSnapshot ds:dataSnapshot.getChildren()) {

                                String t=ds.child("Total").getValue().toString();
                            sumt=sumt+Integer.parseInt(t);

                        }



                            allsaleview.setText("LKR "+String.valueOf(sumt)+"/=");



                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }












        mainAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItem = new Intent(MainActivity.this,AddItemActivity.class);
                startActivity(addItem);

            }
        });

        mMakeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent makeBill = new Intent(MainActivity.this,MakeBillActivity.class);
                startActivity(makeBill);
                MakeBillActivity billObj = new MakeBillActivity();

                billObj.setSum(0);
                BillingItems.billItemArray.clear();






            }
        });

        mChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(MainActivity.this,ChatActivity.class);
                chatIntent.putExtra("currentUserName", loggedUserName);
                startActivity(chatIntent);
            }
        });

        mAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allItemIntent = new Intent(MainActivity.this,All_ItemsActivity.class);
                startActivity(allItemIntent);
            }
        });

        mCardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIN = new Intent(MainActivity.this,LastSaleActivity.class);
                newIN.putExtra("Total",Total);
                newIN.putExtra("Cash",Cash);
                newIN.putExtra("Balance",Balance);


                newIN.putExtra("Date",date);
                startActivity(newIN);


            }
        });

       /* mCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allSales = new Intent(MainActivity.this,AllSalesActivity.class);
                startActivity(allSales);

            }
        });


        FirebaseDatabase dbnew=FirebaseDatabase.getInstance();
        DatabaseReference ref=dbnew.getReference("Items");

        Rid=getIntent().getStringExtra("Rid");
        if (Rid!=null){

            ref.child(Rid).removeValue();

            Toast.makeText(this,"Item has been Deleted",Toast.LENGTH_SHORT).show();
        }*/









    }

    private void sendToStart() {
        Intent mainIn = new Intent(MainActivity.this,StartActivity.class);
        startActivity(mainIn);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);


         getMenuInflater().inflate(R.menu.main_menu,menu);

         return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_logoutBtn){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }else if(item.getItemId()==R.id.main_about){
            Intent about = new Intent(MainActivity.this,CopyrightsActivity.class);
            startActivity(about);
        }


        return true;
    }

    public boolean isConnected(Context context){

        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cn.getActiveNetworkInfo();

        if(netinfo != null && netinfo.isConnectedOrConnecting()){

            android.net.NetworkInfo wifi = cn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(mobile != null && mobile.isConnectedOrConnecting() || (wifi != null && wifi.isConnectedOrConnecting())){
                return true;
            }else{
                return false;

            }

        }else{
            return false;
        }

    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or WiFi to go with this application. Press Close to Exit");
        builder.setCancelable(false);


        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);

                finish();
            }
        });

        return builder;
    }
}
