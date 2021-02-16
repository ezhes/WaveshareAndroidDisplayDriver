package com.hotspotoffice.usbtest3;

import android.app.Instrumentation;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Allison on 9/30/15.
 */

/*
new Thread(new Runnable() {
                @Override
                public void run() {
                UsbManager manager = (UsbManager)getSystemService(Context.USB_SERVICE);

                HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                while(deviceIterator.hasNext()){
                    UsbDevice device = deviceIterator.next();

                    UsbInterface intf = device.getInterface(0);
                    int epc = 0;
                    epc = intf.getEndpointCount();
                    //Check for touchscreen USB devices
                    if (device.getVendorId() == 3823) {
                        manager.requestPermission(device, mPermissionIntent);



                        UsbDeviceConnection connection = manager.openDevice(device);
                        if (null == connection) {
                        } else {

                            // Claims exclusive access to a UsbInterface.
                            // This must be done before sending or receiving data on
                            // any UsbEndpoints belonging to the interface.
                            connection.claimInterface(intf, true);

                            // getRawDescriptors can be used to access descriptors
                            // not supported directly via the higher level APIs,
                            // like getting the manufacturer and product names.
                            // because it returns bytes, you can get a variety of
                            // different data types.
                            byte[] rawDescs = connection.getRawDescriptors();
                            String manufacturer = "", product = "";

                            UsbEndpoint ep = intf.getEndpoint(0);
                            boolean touchdown = false;
                            long startTime = 0;
                            Instrumentation m_Instrumentation = new Instrumentation();
                            float lastX = 0;
                            float lastY = 0;
                            while (true){
                                byte[] buffer = new byte[255];
                                byte[] buffer2 = new byte[255];
                                int intd = connection.bulkTransfer(ep, buffer2, 0xFF, 0);
                                String xTouch = String.format("%02X", buffer2[2]) + String.format("%02X", buffer2[3]);
                                String yTouch = String.format("%02X%02X", buffer2[4], buffer2[5]);
                                int rawX = Integer.parseInt(xTouch, 16);
                                int rawY = Integer.parseInt(yTouch, 16);
                                float calibratedX = rawX * 3/2;
                                float calibratedY = rawY * 3/2;

                                Log.d("Touch","X: " + calibratedX + " Y: " + calibratedY);
                                if (rawX == 0 && rawY == 0) {
                                    if (connection == null) {
                                        Log.d("USB","Connection dropped");
                                    }
                                    touchdown = false;
                                    Log.d("Touch", "Touches ended");
                                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                                            SystemClock.uptimeMillis(),
                                            SystemClock.uptimeMillis(),
                                            MotionEvent.ACTION_UP, lastX, lastY, 0));
                                }else {
                                    if (!touchdown) {
                                        //Touch down for the first time
                                        touchdown = true;
                                        startTime = SystemClock.uptimeMillis();
                                        Log.d("Touch", "Touches began");
                                        m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                                                startTime,
                                                SystemClock.uptimeMillis(),
                                                MotionEvent.ACTION_DOWN, calibratedX, calibratedY, 0));
                                    }
                                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                                            startTime,
                                            SystemClock.uptimeMillis(),
                                            MotionEvent.ACTION_MOVE, calibratedX, calibratedY, 0));
                                }
                                lastX = calibratedX;
                                lastY = calibratedY;
                            }
                        }

                    }
                }


                }
                }).start();

 */
public class TouchService extends Service {
    PendingIntent mPermissionIntent;
    static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Log.d("Thread","Recieved start command");
        runAsForeground();
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UsbManager manager = (UsbManager)getSystemService(Context.USB_SERVICE);

                HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                while(deviceIterator.hasNext()){
                    UsbDevice device = deviceIterator.next();

                    UsbInterface intf = device.getInterface(0);
                    int epc = 0;
                    epc = intf.getEndpointCount();
                    //Check for touchscreen USB devices
                    if (device.getVendorId() == 3823) {
                        manager.requestPermission(device, mPermissionIntent);



                        UsbDeviceConnection connection = manager.openDevice(device);
                        if (null == connection) {
                        } else {

                            // Claims exclusive access to a UsbInterface.
                            // This must be done before sending or receiving data on
                            // any UsbEndpoints belonging to the interface.
                            connection.claimInterface(intf, true);

                            // getRawDescriptors can be used to access descriptors
                            // not supported directly via the higher level APIs,
                            // like getting the manufacturer and product names.
                            // because it returns bytes, you can get a variety of
                            // different data types.
                            byte[] rawDescs = connection.getRawDescriptors();
                            String manufacturer = "", product = "";

                            UsbEndpoint ep = intf.getEndpoint(0);
                            boolean touchdown = false;
                            long startTime = 0;
                            Instrumentation m_Instrumentation = new Instrumentation();
                            float lastX = 0;
                            float lastY = 0;
                            while (true){
                                byte[] buffer = new byte[255];
                                byte[] buffer2 = new byte[255];
                                int intd = connection.bulkTransfer(ep, buffer2, 0xFF, 1000);
                                String xTouch = String.format("%02X", buffer2[2]) + String.format("%02X", buffer2[3]);
                                String yTouch = String.format("%02X%02X", buffer2[4], buffer2[5]);
                                int rawX = Integer.parseInt(xTouch, 16);
                                int rawY = Integer.parseInt(yTouch, 16);
                                float calibratedX = rawX * 3/2;
                                float calibratedY = rawY * 3/2;

                                Log.d("Touch", "X: " + calibratedX + " Y: " + calibratedY);
                                if (rawX == 0 && rawY == 0) {
                                    if (connection.getSerial() == null) {
                                        Log.d("USB","Connection dropped");
                                        connection.close();
                                        connection = manager.openDevice(device);
                                    }else {
                                        touchdown = false;
                                        Log.d("Touch", "Touches ended");
                                        m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                                                SystemClock.uptimeMillis(),
                                                SystemClock.uptimeMillis(),
                                                MotionEvent.ACTION_UP, lastX, lastY, 0));
                                    }
                                }else {
                                    if (!touchdown) {
                                        //Touch down for the first time
                                        touchdown = true;
                                        startTime = SystemClock.uptimeMillis();
                                        Log.d("Touch", "Touches began");
                                        m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                                                startTime,
                                                SystemClock.uptimeMillis(),
                                                MotionEvent.ACTION_DOWN, calibratedX, calibratedY, 0));
                                    }
                                    m_Instrumentation.sendPointerSync(MotionEvent.obtain(
                                            startTime,
                                            SystemClock.uptimeMillis(),
                                            MotionEvent.ACTION_MOVE, calibratedX, calibratedY, 0));
                                }
                                lastX = calibratedX;
                                lastY = calibratedY;
                            }
                        }

                    }
                }


            }
        }).start();
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
    private void runAsForeground(){
        final  int myID = 1242;

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setTicker("TICKER").setContentTitle("TITLE").setContentText("CONTENT")
                    .setWhen(System.currentTimeMillis()).setAutoCancel(false)
                    .setOngoing(true).setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendIntent);
            Notification notification = builder.build();


        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(myID, notification);

    }
}
