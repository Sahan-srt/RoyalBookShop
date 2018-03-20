package shopapp.royal.royalbookshop;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by hansaanuradhawickramanayake on 2/12/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> mMessageList;
    FirebaseAuth mAuth;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView displayTextMessageTextView;
        public TextView displayUserNameTextView;
        ImageView userImageView;

        public MessageViewHolder(View view) {
            super(view);

            displayTextMessageTextView =  view.findViewById(R.id.displayTextMessageTextView);

            displayUserNameTextView =  view.findViewById(R.id.displayUserNameTextView);

            userImageView = view.findViewById(R.id.userImegeView);

        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int position) {

        mAuth = FirebaseAuth.getInstance();

        String currentUserId = mAuth.getCurrentUser().getUid();



        Messages c = mMessageList.get(position);

        String fromUser = c.getCurrentUser();

        if (fromUser.equals(currentUserId)) {

            viewHolder.displayTextMessageTextView.setBackgroundResource(R.drawable.message_text_background);
            viewHolder.displayTextMessageTextView.setTextColor(Color.WHITE);


            viewHolder.displayUserNameTextView.setBackgroundColor(Color.WHITE);
            viewHolder.displayUserNameTextView.setTextColor(Color.BLACK);



            viewHolder.userImageView.setImageResource(R.drawable.current_user);

        } else {



            viewHolder.displayTextMessageTextView.setBackgroundResource(R.drawable.message_text_background2);
            viewHolder.displayTextMessageTextView.setTextColor(Color.WHITE);

        }

        viewHolder.displayTextMessageTextView.setText(c.getMessage());
        viewHolder.displayUserNameTextView.setText(c.getUserName());


    }

    @Override
    public int getItemCount() {

        return mMessageList.size();
    }
}
