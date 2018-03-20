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

import java.util.ArrayList;

/**
 * Created by Sahan Ridma on 2/20/2018.
 */

public class billAdapter extends  RecyclerView.Adapter<billAdapter.billViewViewHolder> {

            Context context;

    public billAdapter(Context context, ArrayList<shopapp.royal.royalbookshop.billData> billData) {
        this.context = context;
        this.billData = billData;
    }

    ArrayList<billData> billData;
    @Override
    public billViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.userdetails,parent,false);
        billViewViewHolder holder=new billViewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(billViewViewHolder holder, int position) {

        billData users=billData.get(position);

        holder.usersname.setText("LKR "+users.getTotal());
        holder.phone.setText(users.getDate());



    }

    @Override
    public int getItemCount() {
        return billData.size();

    }

    class billViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView usersname,phone;

        public billViewViewHolder(View itemView) {
            super(itemView);
            usersname=itemView.findViewById(R.id.usersname);
            phone=itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            int possition=getAdapterPosition();
            billData bill=billData.get(possition);


            String t=bill.getTotal();
            String c=bill.getCash();
            String b=bill.getBalance();
            String d=bill.getDate();
          //  Toast.makeText(context,t,Toast.LENGTH_LONG).show();

            Intent billdataintent=new Intent("Clickbill");

            billdataintent.putExtra("T",t);
            billdataintent.putExtra("C",c);
            billdataintent.putExtra("B",b);
            billdataintent.putExtra("D",d);

           LocalBroadcastManager.getInstance(context).sendBroadcast(billdataintent);









        }
    }


}
