package id.co.kmbmicro.samplepermission;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button turnOn, visible, showList, turnOff, scanDevices;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevice;
    ListView listView;
    private ArrayAdapter<String> deviceListAdapter;
    private ArrayList<String> deviceList;
    private ArrayList<BluetoothDevice> discoveredDevices;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 2;

    // Broadcast receiver for discovering devices
    private final BroadcastReceiver deviceDiscoveryReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) 
                            == PackageManager.PERMISSION_GRANTED) {
                        String deviceName = device.getName();
                        String deviceAddress = device.getAddress();
                        String deviceInfo = deviceName != null ? deviceName + " (" + deviceAddress + ")" : deviceAddress;
                        
                        if (!deviceList.contains(deviceInfo)) {
                            deviceList.add(deviceInfo);
                            discoveredDevices.add(device);
                            deviceListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context, "Device discovery finished", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        turnOn = findViewById(R.id.turnOn);
        visible = findViewById(R.id.visible);
        showList = findViewById(R.id.showList);
        turnOff = findViewById(R.id.turnOff);
        scanDevices = findViewById(R.id.scanDevices);
        listView = findViewById(R.id.listView);

        // Initialize device list and adapter
        deviceList = new ArrayList<>();
        discoveredDevices = new ArrayList<>();
        deviceListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        listView.setAdapter(deviceListAdapter);

        // Register broadcast receiver for device discovery
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(deviceDiscoveryReceiver, filter);

        // Check if device supports Bluetooth
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
            return;
        }

        // Request permissions on app start
        requestBluetoothPermissions();

        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    if (hasBluetoothPermissions()) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    } else {
                        requestBluetoothPermissions();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth is already turned ON", Toast.LENGTH_LONG).show();
                }
            }
        });

        turnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasBluetoothPermissions()) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth is turned OFF", Toast.LENGTH_LONG).show();
                } else {
                    requestBluetoothPermissions();
                }
            }
        });

        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasBluetoothPermissions()) {
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(discoverableIntent);
                } else {
                    requestBluetoothPermissions();
                }
            }
        });

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPairedDevices();
            }
        });

        scanDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanForDevices();
            }
        });
    }

    private void showPairedDevices() {
        if (!hasBluetoothPermissions()) {
            requestBluetoothPermissions();
            return;
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pairedDevice = bluetoothAdapter.getBondedDevices();
        deviceList.clear();
        discoveredDevices.clear();

        for (BluetoothDevice bt : pairedDevice) {
            String deviceName = bt.getName();
            String deviceAddress = bt.getAddress();
            String deviceInfo = deviceName != null ? deviceName + " (" + deviceAddress + ") [Paired]" : deviceAddress + " [Paired]";
            deviceList.add(deviceInfo);
            discoveredDevices.add(bt);
        }

        deviceListAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Showing paired devices", Toast.LENGTH_SHORT).show();
    }

    private void scanForDevices() {
        if (!hasBluetoothPermissions()) {
            requestBluetoothPermissions();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Please turn on Bluetooth first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear previous results
        deviceList.clear();
        discoveredDevices.clear();
        deviceListAdapter.notifyDataSetChanged();

        // Cancel any ongoing discovery
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        // Start discovery
        boolean discoveryStarted = bluetoothAdapter.startDiscovery();
        if (discoveryStarted) {
            Toast.makeText(this, "Scanning for devices...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to start device discovery", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                   ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                   ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
                   ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            String[] permissions = {
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE
            };
            ActivityCompat.requestPermissions(this, permissions, REQUEST_BLUETOOTH_PERMISSIONS);
        } else {
            String[] permissions = {
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            };
            ActivityCompat.requestPermissions(this, permissions, REQUEST_BLUETOOTH_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            
            if (allPermissionsGranted) {
                Toast.makeText(this, "Bluetooth permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bluetooth permissions are required for this app to work", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth is turned ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bluetooth enable request was cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel discovery if it's running
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
            if (hasBluetoothPermissions()) {
                bluetoothAdapter.cancelDiscovery();
            }
        }
        // Unregister broadcast receiver
        try {
            unregisterReceiver(deviceDiscoveryReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver was not registered
        }
    }
}