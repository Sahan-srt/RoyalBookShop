package shopapp.royal.royalbookshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


//sahan edit

public class items extends RecyclerView.Adapter<items.SearchViewHolder>{

    ArrayList<String> itemnamelist;
    ArrayList<String> itempricelist;
    ArrayList<String> itemid;

    Context context;
    ArrayList<All_ItemsActivity> itemDetails=new ArrayList<All_ItemsActivity>();

    public items(Context context, ArrayList<String> itemnamelist, ArrayList<String> itempricelist, ArrayList<String> itemid) {
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
                 String id=itemid.get(position);



           Intent update=new Intent(context,UpdateItemAcitivity.class);
          /*  update.putExtra("Name",name);
           update.putExtra("Price",price) ;*/
            update.putExtra("Id",id) ;

            context.startActivity(update);



         //  Toast.makeText(context,String.valueOf(position)+name,Toast.LENGTH_SHORT).show();
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
