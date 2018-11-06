package dc.com.dcblueunpair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import dc.com.dcblueunpair.databinding.MainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DC";
    private static TitleModel title;
    private ArrayList<BluetoothDevice> deviceData;
    private ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        deviceData = new ArrayList<>();
        title = new TitleModel("点击我清空蓝牙配对设备");
        mainBinding.setMyTitle(title);//一次绑定，多次使用，避免每次都重新绑定
        mainBinding.setHitMe(v -> {
            Toast.makeText(MainActivity.this,"clicked",Toast.LENGTH_SHORT).show();
            title.setTitle("已经清空了所有配对的蓝牙设备");
//            mainBinding.setMyTitle(title);
            removePairDevice();
        });
        adapter = new ListViewAdapter(MainActivity.this, deviceData);
        mainBinding.bondList.setAdapter(adapter);
        bondDevice();
    }

    private void bondDevice(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
            title.setTitle("等待打开设备蓝牙");
//            mainBinding.setMyTitle(title);
            mBluetoothAdapter.enable();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        if (bondedDevices.isEmpty()) {
            deviceData.clear();
            title.setTitle("已经没有配对的蓝牙设备");
//            mainBinding.setMyTitle(title);
            return;
        }
        deviceData.addAll(bondedDevices);
        adapter.notifyDataSetChanged();
    }

    private void removePairDevice() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        if (bondedDevices.isEmpty()) {
            title.setTitle("已经没有配对的蓝牙设备");
//            mainBinding.setMyTitle(title);
            return;
        }
        for (BluetoothDevice device : bondedDevices) {
            unPairDevice(device);
        }
        deviceData.clear();
        adapter.notifyDataSetChanged();
    }

    private static void unPairDevice(BluetoothDevice device) {
        try {
            @SuppressWarnings({"ConstantConditions", "JavaReflectionMemberAccess"}) Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
