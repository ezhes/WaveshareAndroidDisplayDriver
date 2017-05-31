package com.hotspotoffice.usbtest3;

// David Schofield, Hotspot Office, LLC., Pittsburgh, PA.
// Donations via PayPal always welcome! schofield (dot) david (at) verizon.net

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Instrumentation;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;


public class MainActivity extends Activity {

    protected static final int STD_USB_REQUEST_GET_DESCRIPTOR = 0x06;
    // http://libusb.sourceforge.net/api-1.0/group__desc.html
    protected static final int LIBUSB_DT_STRING = 0x03;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private Button btnDiscover;
    private TextView txtInfo;
    private PendingIntent mPermissionIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDiscover=(Button)findViewById(R.id.btnDiscover);
        txtInfo=(TextView)findViewById(R.id.txtInfo);


        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        //START SERVICE
        Intent i= new Intent(getBaseContext(), TouchService.class);
        getBaseContext().startService(i);

        //Go home
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        btnDiscover.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i= new Intent(getBaseContext(), TouchService.class);
                getBaseContext().startService(i);


            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            //call method to set up device communication
                        }
                    }
                    else {
                        txtInfo.append("permission denied for device " + device);
                    }
                }
            }
        }
    };

}