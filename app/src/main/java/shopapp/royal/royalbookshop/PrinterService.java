package shopapp.royal.royalbookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class PrinterService extends AppCompatActivity {

    // will show the statuses like bluetooth open, close or data sent
    TextView statusLabel;

    // will enable user to enter model number of device
    EditText modelNumbEdit;

    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    TextView totalTxt;
    TextView cashTxt;
    TextView balanceTxt;

    Button connectButton;
    Button sendButton;
    Button disconnectButton;
    Button makeNewBill;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;



    private android.support.v7.widget.Toolbar nToolBar;
    private Button backToHome;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_service);




        // we are going to have three buttons for specific functions
        connectButton = (Button) findViewById(R.id.connectBtn);
        sendButton = (Button) findViewById(R.id.sendBtn);
        disconnectButton = (Button) findViewById(R.id.disconnectBtn);
        makeNewBill = (Button)findViewById(R.id.makeNewBillbtn);
        backToHome =(Button)findViewById(R.id.goHomeBtn);

        // text label and input box
        modelNumbEdit = (EditText) findViewById(R.id.modelNumbEdit);
        statusLabel = (TextView) findViewById(R.id.statusTxt);

        totalTxt = (TextView) findViewById(R.id.totalValue);
        cashTxt = (TextView) findViewById(R.id.cashValue);
        balanceTxt = (TextView) findViewById(R.id.balanceValue);

        //go home
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(PrinterService.this,MainActivity.class);
                startActivity(goHome);
            }
        });

        //go to new bill
        makeNewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeNewBill = new Intent(PrinterService.this,MakeBillActivity.class);
                startActivity(makeNewBill);
                MakeBillActivity billObj = new MakeBillActivity();
                billObj.setSum(0);
                BillingItems.billItemArray.clear();
                finish();
            }
        });

        // open bluetooth connection
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    findBT();
                    openBT();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // send data typed by the user to be printed
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // close bluetooth connection
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    closeBT();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // this will find a bluetooth printer device
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                statusLabel.setText("No bluetooth adapter available");
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals(modelNumbEdit.getText().toString())) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            statusLabel.setText("Bluetooth device found.");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // tries to open a connection to the bluetooth printer device
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();
            statusLabel.setText("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 * after opening a connection to bluetooth printer device,
 * we have to listen and check if a data were sent to be printed.
 */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                statusLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this will send text data to be printed by the bluetooth printer
    void sendData() throws IOException {
        try {
            Calendar calendar = Calendar.getInstance();

            String heading = "COMPANY NAME";
            String address = "COMPANY ADDRESS";
            String telNo =  "TELEPHONE NO:";
            // getting date and time for the receipt
            String dateTime = DateFormat.getDateTimeInstance().format(calendar.getTime());

            // the text details for receipt
            String total = "TOTAL: " + totalTxt.getText().toString() + "\n";
            String cash = "CASH: " + cashTxt.getText().toString() + "\n\n";
            String balance = "BALANCE: " + balanceTxt.getText().toString() + "\n";



            String msg = heading + "\n" + address + "\n" + telNo + "THANK YOU! COME AGAIN" + "\n\n" + dateTime + "\n\n" + total + "" + cash + "" + balance;

            mmOutputStream.write(msg.getBytes());

            // tell the user data were sent
            statusLabel.setText("Data sent.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // close the connection to bluetooth printer.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            statusLabel.setText("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
