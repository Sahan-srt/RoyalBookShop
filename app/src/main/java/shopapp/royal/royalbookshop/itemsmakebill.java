package shopapp.royal.royalbookshop;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


//sahan edit



public class itemsmakebill extends RecyclerView.Adapter<itemsmakebill.SearchViewHolder>{

    ArrayList<String> itemnamelist;
    ArrayList<String> itempricelist;
    ArrayList<String> itemid;



    Context context;
    ArrayList<All_ItemsActivity> itemDetails=new ArrayList<All_ItemsActivity>();
    final ArrayList<ExmapleItem> exampleItem  = new ArrayList<>();

    public itemsmakebill(Context context, ArrayList<String> itemnamelist, ArrayList<String> itempricelist, ArrayList<String> itemid) {
        this.context = context;
        this.itemnamelist = itemnamelist;
        this.itempricelist = itempricelist;
        this.itemid = itemid;




    }


    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView item_name,item_price;

        Context ctx;
        ArrayList<String> itemnamelist;
        ArrayList<String> itempricelist;
        ArrayList<String> itemid;


        public SearchViewHolder(View itemView, Context ctx,ArrayList<String> itemnamelist, ArrayList<String> itempricelist,ArrayList<String> itemid) {
            super(itemView);
            this.ctx=ctx;
            this.itemnamelist=itemnamelist;
            this.itempricelist=itempricelist;
            this.itemid = itemid;


            itemView.setOnClickListener(this);
            item_name =(TextView)itemView.findViewById(R.id.itemName);
            item_price=(TextView)itemView.findViewById(R.id.itemPrice);
        }



        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();

                String name=itemnamelist.get(position);
                String price=itempricelist.get(position);
                 final String id=itemid.get(position);
                 MakeBillActivity newsum= new MakeBillActivity();
                 int total1=newsum.getSum();
                 String total=Integer.toString(total1);
            DatabaseReference myref=FirebaseDatabase.getInstance().getReference("Items");


            // Toast.makeText(MakeBillActivity.this,String.valueOf(name),Toast.LENGTH_LONG).show();
            Query itemquantity=myref.orderByChild("iID").equalTo(id);
            itemquantity.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.exists()) {
                        int qty = Integer.parseInt(dataSnapshot.child("iQ").getValue().toString());
                       if (qty>0) {
                           int newqty = qty - 1;

                           DatabaseReference changeqty = FirebaseDatabase.getInstance().getReference("Items/" + id);

                           changeqty.child("iQ").setValue(newqty);

                           Toast.makeText(context, String.valueOf(newqty), Toast.LENGTH_LONG).show();
                       }

                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });










            // Toast.makeText(context,String.valueOf(id),Toast.LENGTH_SHORT).show();
           Intent update=new Intent("itemDetails");
            update.putExtra("Name",name);
           update.putExtra("Price",price) ;
            update.putExtra("Total",total) ;
            update.putExtra("Id",id) ;


           LocalBroadcastManager.getInstance(context).sendBroadcast(update);


        }
    }







    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_items2, parent,false);
        return new SearchViewHolder(view,context,itemnamelist,itempricelist,itemid);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.item_name.setText(itemnamelist.get(position));
        holder.item_price.setText(itempricelist.get(position));



    }



    @Override
    public int getItemCount() {
        return itemnamelist.size();
    }
}
