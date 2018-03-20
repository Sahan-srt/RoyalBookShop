package shopapp.royal.royalbookshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;
    private TextView email;
    private TextView pass;
    private Button logIn;

    private ProgressDialog loginProg;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();


        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("Log In ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginProg = new ProgressDialog(this );


        email   =(TextView)findViewById(R.id.regEmail);
        pass=(TextView)findViewById(R.id.regPassword);
        logIn = (Button)findViewById(R.id.loginButton);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = email.getText().toString();
                String newPass= pass.getText().toString();

                if(!TextUtils.isEmpty(newEmail)||!TextUtils.isEmpty(newPass)){
                    loginProg.setTitle("Logging In");
                    loginProg.setMessage("Please wait while we check your information");
                    loginProg.setCanceledOnTouchOutside(false);
                    loginProg.show();

                    loginUser(newEmail,newPass);
                }
            }
        });

    }
    
    private void loginUser(String newEmail, String newPass) {
        mAuth.signInWithEmailAndPassword(newEmail,newPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginProg.dismiss();
                    Intent mainIn = new Intent(LoginActivity.this,MainActivity.class);
                    mainIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIn);
                    finish();
                }else{
                    loginProg.hide();
                    Toast.makeText(LoginActivity.this, "Try Again Later !!! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
