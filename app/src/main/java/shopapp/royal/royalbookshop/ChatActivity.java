package shopapp.royal.royalbookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    // Toolbar
    private android.support.v7.widget.Toolbar nToolBar;

    // Firebase Auth
    FirebaseAuth mAuth;

    // Database Reference
    DatabaseReference rootReference;


    // Layout Fields
    EditText messageEditText;
    ImageButton sendMessageImageButton;
    RecyclerView messagesListRecyclerView;

    final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    MessageAdapter mAdapter;

    // Member Variables
    String currentUser;
    String message;
    String currentUserName;

    // when sendMessageImageButton is clicked,
    public void sendMessage(View view) {

        message = messageEditText.getText().toString();


        if (TextUtils.isEmpty(message)) {

            Toast.makeText(this, "message is empty", Toast.LENGTH_SHORT).show();
        } else {

            DatabaseReference userMessagePush = rootReference.child("Messages").push();

            /*
            Map chatAddMap = new HashMap();
            chatAddMap.put("seen", false);
            chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

            Map chatUserMap = new HashMap();
            chatUserMap.put("Chat/" + currentUser, chatAddMap);

            */


            String currentUserRef = "Messages/" + userMessagePush.getKey();

            Map messageMap = new HashMap();
            messageMap.put("currentUser", currentUser);
            messageMap.put("message", message);
            messageMap.put("time", ServerValue.TIMESTAMP );
            messageMap.put("userName",currentUserName);

            Map messageUserMap = new HashMap();
            messageUserMap.put(currentUserRef , messageMap);


            messageEditText.setText("");

            rootReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (databaseError != null) {
                        Log.d("CHAT_LOG", databaseError.getMessage().toString());

                    }

                }
            });


            //mRef.child(String.valueOf(ItemId.getText())).child("iName").setValue(String.valueOf(ItemName.getText()));

            /*rootReference.child("Messages").child(userMessagePush.getKey()).child("message").setValue(message);
            rootReference.child("Messages").child(userMessagePush.getKey()).child("time").setValue(ServerValue.TIMESTAMP);
            rootReference.child("Messages").child(userMessagePush.getKey()).child("currentUser").setValue(currentUser);
            */



        }



    }


    private void sendToStart() {
        Intent mainIn = new Intent(ChatActivity.this,StartActivity.class);
        startActivity(mainIn);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Toolbar
        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Get Intent Data

        currentUserName = getIntent().getStringExtra("currentUserName");


        // Layout Fields
        messageEditText = findViewById(R.id.messageEditText);
        sendMessageImageButton = findViewById(R.id.sendMessageImageButton);

        mAdapter = new MessageAdapter(messagesList);

        messagesListRecyclerView =findViewById(R.id.messagesListRecyclerView);

        mLinearLayout = new LinearLayoutManager(this);

        messagesListRecyclerView.setHasFixedSize(true);
        messagesListRecyclerView.setLayoutManager(mLinearLayout);

        messagesListRecyclerView.setAdapter(mAdapter);


        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        // Database Reference
        rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.keepSynced(true);



        // Display Messages on ChatActivity
        loadMessages();


        if (currentUser == null) {
            sendToStart();
        } else {

            // Chat Conversation
            rootReference.child("Chat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild(currentUser)) {

                        Map chatAddMap = new HashMap();
                        chatAddMap.put("seen", false);
                        chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                        Map chatUserMap = new HashMap();
                        chatUserMap.put("Chat/" + currentUser, chatAddMap);

                        rootReference.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                if (databaseError != null) {

                                    Log.d("CHAT_LOG", databaseError.getMessage().toString());
                                }

                            }
                        });


                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }



    }



    private void loadMessages() {

        rootReference.child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Messages message = dataSnapshot.getValue(Messages.class);

                messagesList.add(message);
                mAdapter.notifyDataSetChanged();

                messagesListRecyclerView.scrollToPosition(messagesList.size() - 1);

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


    }
}
