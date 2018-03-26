package com.maxustech.maxdisplay.usb_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import com.maxustech.maxdisplay.usbcommlib.OperationCallbackInterface;
import com.maxustech.maxdisplay.usbcommlib.UsbSerialService;
import com.maxustech.maxdisplay.usbcommlib.UsbSerialClient;
import com.maxustech.maxdisplay.usbcommlib.UsbSerialInterface;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements UsbSerialInterface{

    private TextView mTextOutput;
    private UsbSerialClient mUsbSerialClient = new UsbSerialClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serialService = new Intent(this, UsbSerialService.class);
        mUsbSerialClient.bindService(this, serialService);

        setContentView(R.layout.activity_main);

        mTextOutput = (TextView) findViewById(R.id.dataReceived);
        mTextOutput.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUsbSerialClient.unbindService();
    }

    @Override
    public void onData(float[] data) {
        mTextOutput.append(Arrays.toString(data));
        mTextOutput.append("\r\n");
        Log.d("Data: ", Arrays.toString(data));
    }

    @Override
    public void onGesture(String gesture){
        mTextOutput.append(gesture);
        Log.d("onGesture", gesture);
    }

    @Override
    public void onCommandResponse(String command) {
        mTextOutput.append(command);
        Log.d("onCommandResponse", command);
    }

    @Override
    public void onDeviceConnected(String msg){
        mTextOutput.append(msg);
        Log.d("onDeviceConnected", msg);
    }

    @Override
    public void onDeviceDisconnected(String msg){
        mTextOutput.append(msg);
        Log.d("onDeviceDisconnected", msg);
    }

    public void dataOn(View v){
        mTextOutput.append("dataOn\r\n");
        mUsbSerialClient.sendDataOnMessage(new OperationCallbackInterface() {
            @Override
            public void successCallback() {

            }

            @Override
            public void failCallback() {

            }

            @Override
            public void timeoutCallback() {

            }
        });
    }

    public void dataOff(View v){
        mTextOutput.append("dataOff\r\n");
        mUsbSerialClient.sendDataOffMessage(new OperationCallbackInterface() {
            @Override
            public void successCallback() {

            }

            @Override
            public void failCallback() {

            }

            @Override
            public void timeoutCallback() {

            }
        });
    }

    public void gestureOn(View v){
        mTextOutput.append("gestureOn\r\n");
        mUsbSerialClient.sendGestureOnMessage(new OperationCallbackInterface() {
            @Override
            public void successCallback() {

            }

            @Override
            public void failCallback() {

            }

            @Override
            public void timeoutCallback() {

            }
        });
    }

    public void gestureOff(View v){
        mTextOutput.append("gestureOff\r\n");
        mUsbSerialClient.sendGestureOffMessage(new OperationCallbackInterface() {
            @Override
            public void successCallback() {

            }

            @Override
            public void failCallback() {

            }

            @Override
            public void timeoutCallback() {

            }
        });
    }

    public void reset(View v){
        mTextOutput.setText("");
        mTextOutput.append("reset\r\n");
        mUsbSerialClient.sendSystemResetMessage(new OperationCallbackInterface() {
            @Override
            public void successCallback() {

            }

            @Override
            public void failCallback() {

            }

            @Override
            public void timeoutCallback() {

            }
        });
    }
}
