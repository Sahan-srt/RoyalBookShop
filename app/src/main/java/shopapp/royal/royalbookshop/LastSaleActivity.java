package shopapp.royal.royalbookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LastSaleActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar nToolBar;

    private TextView total2,balance2,cash2,date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_sale);

        nToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.reg_toolbar);
        setSupportActionBar(nToolBar);

        getSupportActionBar().setTitle("Last Sale");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        total2=(TextView)findViewById(R.id.total2);
        balance2=(TextView)findViewById(R.id.balance2);
        cash2=(TextView)findViewById(R.id.cash2);
        date=(TextView)findViewById(R.id.date2);

        total2.setText("LKR "+getIntent().getStringExtra("Total"));

        balance2.setText(getIntent().getStringExtra("Balance"));

        cash2.setText(getIntent().getStringExtra("Cash"));
        date.setText(getIntent().getStringExtra("Date"));

    }
}
