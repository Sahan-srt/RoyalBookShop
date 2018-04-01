package shopapp.royal.royalbookshop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;



public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    Context context;
    //public TextView mTotal;



    private ArrayList<ExmapleItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView mItemName;
        public TextView mItemPrice,QTY;
        public Button mRemoveButton;
        public ImageButton additem,minusitem;


         //public TextView mTotal;
      //  public Activity mMakeBillActivity;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mItemName = itemView.findViewById(R.id.makebillitemName);
            mItemPrice = itemView.findViewById(R.id.makebillitemPrice);
            mRemoveButton = itemView.findViewById(R.id.removeItem);
            //additem = itemView.findViewById(R.id.Add);
            //minusitem = itemView.findViewById(R.id.Minus);
            QTY = itemView.findViewById(R.id.QTY);





        }

    }


    public ExampleAdapter(Context context,ArrayList<ExmapleItem> exampleList){
        this.context=context;
        mExampleList = exampleList;



    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.makebill_items,parent,false);

        ExampleViewHolder evh = new ExampleViewHolder(v);

        return evh;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        final ExmapleItem currentItem = mExampleList.get(position);
//billing check item


        holder.mItemName.setText(currentItem.getItemName());
        holder.mItemPrice.setText(currentItem.getItemPrice());

        String name=currentItem.getItemName();
        String price=currentItem.getItemPrice();

        String A=name+"\n"+price+"  LKR";

        BillingItems.billItemArray.add(A);


    //    LayoutInflater inflater = (LayoutInflater) MakeBillActivity.class.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    //    View nv  = LayoutInflater.from().inflate(R.layout.activity_make_bill,null);
     //   ConstraintLayout conLay = nv.findViewById(R.id.conLayout);
     //   final TextView mTotalTxt = conLay.findViewById(R.id.totalPriceView);



        holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=currentItem.getItemName();
                String price=currentItem.getItemPrice();
                String id=currentItem.getItemID();
                String A=name+"\n"+price+"  LKR";

                MakeBillActivity billObj = new MakeBillActivity();
                int sumValue = billObj.getSum();

                // String equipPrice = holder.mItemPrice.getText().toString();
                String equipPrice = currentItem.getItemPrice();
                sumValue -= Integer.parseInt(equipPrice);

                ///update the sum value after reduce the value on remove button click
                billObj.updateCounter(Integer.toString(sumValue));
                //  billObj.setSum(sumValue);
                //  innerView.setText("LKR"+ sumValue +"/=");
               //  holder.mTotal.setText("LKR"+ sumValue +"/=");
              //     mTotal.setText("LKR"+ sumValue +"/=");;
            //        mTotalTxt.setText(sumValue);
                // Toast.makeText(billObj, Integer.toString(sum), Toast.LENGTH_SHORT).show();

                // Remove the item on remove/button click


                if ( BillingItems.billItemArray.size()>0){
                BillingItems.billItemArray.remove(position);
                BillingItems.billItemArray.remove(A);
                mExampleList.remove(position);}
                else {

                    mExampleList.remove(position);
                    BillingItems.billItemArray.clear();

                }


                DatabaseReference myref= FirebaseDatabase.getInstance().getReference("Items");


               //  Toast.makeText(context,String.valueOf(id),Toast.LENGTH_LONG).show();
                Query itemquantity=myref.orderByChild("iID").equalTo(id);
                itemquantity.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        if (dataSnapshot.exists()) {
                            int qty = Integer.parseInt(dataSnapshot.child("iQ").getValue().toString());

                                int newqty = qty +1;

                                DatabaseReference changeqty = FirebaseDatabase.getInstance().getReference("Items/" + currentItem.getItemID());

                                changeqty.child("iQ").setValue(newqty);

                               // Toast.makeText(context, String.valueOf(newqty), Toast.LENGTH_LONG).show();


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









                ///on each removal of recycler view element the sum has to be updated to the latest value...
                billObj.setSum(sumValue);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mExampleList.size());
                notifyItemRangeChanged(position,BillingItems.billItemArray.size());

            }
        });

  /*      holder.additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MakeBillActivity billObj = new MakeBillActivity();
                int  amount=billObj.getQty();
                int sumValue = billObj.getSum();

                int qty = amount+1;

                // String equipPrice = holder.mItemPrice.getText().toString();
                String equipPrice = currentItem.getItemPrice();
                sumValue = Integer.parseInt(equipPrice)*qty;
            billObj.updateCounter(Integer.toString(qty));

            Intent quantity = new Intent("Quantity");
            quantity.putExtra("Quantity",qty);
            quantity.putExtra("Sum",sumValue);

             //   Toast.makeText(context,String.valueOf(qty), Toast.LENGTH_SHORT).show();


            }
        });
        holder.minusitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,"Minus", Toast.LENGTH_SHORT).show();


            }
        });*/



    }



    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
