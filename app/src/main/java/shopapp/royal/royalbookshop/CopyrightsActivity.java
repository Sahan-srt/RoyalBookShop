package shopapp.royal.royalbookshop;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CopyrightsActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;

    private Button mCallUs;
    private Button mEmailUs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyrights);

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("About iInventory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCallUs = (Button) findViewById(R.id.callUs);
        mEmailUs=(Button)findViewById(R.id.emailUs);


        mCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0711091499"));
                startActivity(intent);

            }
        });

        mEmailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subject = "About iInventory";
                String message = "Message here";
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","email@email.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }
}
