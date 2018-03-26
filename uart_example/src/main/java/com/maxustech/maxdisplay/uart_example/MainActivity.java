package com.maxustech.maxdisplay.uart_example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maxustech.maxdisplay.uartcommlib.OperationCallbackInterface;
import com.maxustech.maxdisplay.uartcommlib.UartSerialService;
import com.maxustech.maxdisplay.uartcommlib.UartSerialClient;
import com.maxustech.maxdisplay.uartcommlib.UartSerialInterface;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements UartSerialInterface{
    private TextView mTextOutput;

    private UartSerialClient mUartSerialClient = new UartSerialClient(this, "/dev/ttyS1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent serialService = new Intent(this, UartSerialService.class);
        mUartSerialClient.bindService(this, serialService);

        setContentView(R.layout.activity_main);

        mTextOutput = (TextView) findViewById(R.id.dataReceived);
        mTextOutput.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUartSerialClient.unbindService();
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

    public void dataOn(View v){
        mTextOutput.append("dataOn\r\n");
        mUartSerialClient.sendDataOnMessage(new OperationCallbackInterface() {
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
        mUartSerialClient.sendDataOffMessage(new OperationCallbackInterface() {
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
        mUartSerialClient.sendGestureOnMessage(new OperationCallbackInterface() {
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
        mUartSerialClient.sendGestureOffMessage(new OperationCallbackInterface() {
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
        mUartSerialClient.sendSystemResetMessage(new OperationCallbackInterface() {
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
