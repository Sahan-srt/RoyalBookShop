package shopapp.royal.royalbookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button regBtn;
    private  Button lgBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        lgBtn = (Button)findViewById(R.id.newAccount);

        lgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newLg = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(newLg);
            }
        });
    }
}
