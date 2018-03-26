### MaxDisplay Client Apis Documentation

---

The project offer libraries and examples to build application based on MaxDisplay hardware module



#### Project Structure

You can run either **uart_example** or **usb_example** to have your MaxDisplay demo tested. The MaxDisplay Client Library is under the corresponding **libs** directory, detailed usage please refers to the Usage section.



#### MaxDisplay Client Apis

The following Apis are public functions within **UartSerialClient**/**UsbSerialClient** class.

| APIs                                                         | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `UartSerialClient(UartSerialInterface Clb, String PortName)` | UartSerialClient constructor, **Clb** refers to the class implements **UartSerialInterface**, and PortName is the full name of the connected Uart port (example shown below). Make sure you have the correct access permission. For example You can run `chmod 666 /dev/tty*` to modify access permission |
| `UsbSerialClient(UsbSerialInterface Clb)`                    | UsbSerialClient construtor, **Clb** refers to the class implements **UsbSerialInterface**, please refer to the code example. |
| `bindService(Context context, Intent intent)`                | Binds `UsbSerialService` or `UartSerialService` based on the library used to user app |
| `unbindService()`                                            | Unbinds `UsbSerialService`                                   |
| `sendGetStatusMessage(OperationCallbackInterface OpCb)`      | Gets **MaxDisplay** Hardware status, passing **OperationCallbackInterface OpCb** as the status callback function |
| `sendDataOnMessage(OperationCallbackInterface OpCb)`         | Turns on **MaxDisplay** Data flow, passing **OperationCallbackInterface OpCb** as the status callback function |
| `sendDataOffMessage(OperationCallbackInterface OpCb)`        | Turns off **MaxDisplay** Data flow, passing **OperationCallbackInterface OpCb** as the status callback function |
| `sendGestureOnMessage(OperationCallbackInterface OpCb)`      | Turns on **MaxDisplay** gesture output, passing **OperationCallbackInterface OpCb** as the status callback function |
| `sendGestureOffMessage(OperationCallbackInterface OpCb)`     | Turns off **MaxDisplay** gesture output, passing **OperationCallbackInterface OpCb** as the status callback function |
| `sendSystemResetMessage(OperationCallbackInterface OpCb)`    | Reset **MaxDisplay** Module, please implement **onDeviceConnected** and **onDeviceDisconnected** function to monitor device reset status |
| `onData(float[] data)`                                       | Data callback method, four channels data will be passed      |
| `onGesture(String gesture)`                                  | Gesture output callback method, a recognized gesture will be passed |
| `onCommandResponse(String cmdResp)`                          | Command response callback method.                            |
| `onDeviceConnected(String msg)`                              | MaxDisplay device connected callback (Only works on Usb connectivity) |
| `onDeviceDisconnected(String msg)`                           | MaxDisplay device disconnected callback (Only works on Usb connectivity) |

**UartSerialInterface** **UsbSerialInterface**

```java
//UartSerialInterface and UsbSerialInterface are the same
public interface UsbSerialInterface {
    public void onData(float[] data);
    public void onGesture(String gesture);
    public void onCommandResponse(String command);
}

public interface UartSerialInterface {
    public void onData(float[] data);
    public void onGesture(String gesture);
    public void onCommandResponse(String command);
}
```



**OperationCallbackInterface**

```java
public interface OperationCallbackInterface {
    public void successCallback();
    public void failCallback();
    public void timeoutCallback();
}
```



#### Usage for USB Connectivity

1. Include `usbcommlib.jar` in your project, and add it as the project dependencies.

2. In your project **manifest** file, add the following code: 

   ```java
   <service android:name="com.maxustech.maxdisplay.usbcommlib.UsbSerialService"
               android:exported="false"/>
   ```

3. In your Activity class, implement the following code:

   ```java
   import com.maxustech.maxdisplay.usbcommlib.UsbSerialService;
   import com.maxustech.maxdisplay.usbcommlib.UsbSerialClient;
   import com.maxustech.maxdisplay.usbcommlib.UsbSerialInterface;

   public class MainActivity extends Activity implements UsbSerialInterface{
       private UsbSerialClient mUsbSerialClient = new UsbSerialClient(this);
       
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);

           Intent serialService = new Intent(this, UsbSerialService.class);
           mUsbSerialClient.bindService(this, serialService);
           
           setContentView(R.layout.activity_main);
       }
       
       @Override
       protected void onDestroy() {
           super.onDestroy();
           mUsbSerialClient.unbindService();
       }
       
       @Override
       public void onData(float[] data) {
           //Log.d("Data: ", Arrays.toString(data));
       }

       @Override
       public void onGesture(String gesture){
           Log.d("onGesture", gesture);
       }

       @Override
       public void onCommandResponse(String command) {
           Log.d("onCommandResponse", command);
       }
       
       @Override
       public void onDeviceConnected(String msg){
           Log.d("onDeviceConnected", msg);
       }

       @Override
       public void onDeviceDisconnected(String msg){
           Log.d("onDeviceDisconnected", msg);
       }
   }
   ```

   ​

   #### Usage for UART Connectivity

   **Uart Settings**

   - Baudrate: 115200
   - Data bits: 8 bits
   - Parity: None
   - Stop bits: 1 bit
   - Flow control: None

   ​

   **Uart Library Usage**

   1. Include `uartcommlib.aar` in your project, and add it as the project dependencies.

   2. In your project **manifest** file, add the following code: 

      ```java
      <service android:name="com.maxustech.maxdisplay.uartcommlib.UartSerialService"
                  android:exported="false"/>
      ```

   3. In your Activity class, implement the following code:

      ```java
      import com.maxustech.maxdisplay.uartcommlib.UartSerialService;
      import com.maxustech.maxdisplay.uartcommlib.UartSerialClient;
      import com.maxustech.maxdisplay.uartcommlib.UartSerialInterface;

      public class MainActivity extends Activity implements UartSerialInterface{
          private UartSerialClient mUartSerialClient = new UartSerialClient(this, "/dev/ttyS1");
          
          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);

              Intent serialService = new Intent(this, UartSerialService.class);
              mUartSerialClient.bindService(this, serialService);
              
              setContentView(R.layout.activity_main);
          }
          
          @Override
          protected void onDestroy() {
              super.onDestroy();
              mUartSerialClient.unbindService();
          }
          
          @Override
          public void onData(float[] data) {
              //Log.d("Data: ", Arrays.toString(data));
          }

          @Override
          public void onGesture(String gesture){
              Log.d("onGesture", gesture);
          }

          @Override
          public void onCommandResponse(String command) {
              Log.d("onCommandResponse", command);
          }
      }
      ```

      ​