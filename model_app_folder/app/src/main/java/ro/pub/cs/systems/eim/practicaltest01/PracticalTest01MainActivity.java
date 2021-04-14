package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01.service.PracticalTest01Service;


public class PracticalTest01MainActivity extends AppCompatActivity {

    Button button1, button2, otherActivity;
    EditText editText1, editText2;
    int Req_code = 2;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private IntentFilter intentFilter = new IntentFilter();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    final public static String ACTION_STRING = "ro.pub.cs.systems.eim.practicaltest01.service.startedservice.string";
    final public static String DATA = "ro.pub.cs.systems.eim.practicaltest01.service.startedservice.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        intentFilter.addAction(ACTION_STRING);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        otherActivity = findViewById(R.id.nextActivityButton);

        otherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PracticalTest01SecondaryActivity.class);

                Integer int1 = Integer.valueOf(editText1.getText().toString());
                Integer int2 = Integer.valueOf(editText2.getText().toString());
                intent.putExtra("sum", int1 + int2);

                startActivityForResult(intent, Req_code);
            }
        });


        button1.setOnClickListener(buttonClickListener);
        button2.setOnClickListener(buttonClickListener);

        if (savedInstanceState == null) {
            Log.d("COLOCVIU TAG", "onCreate() method was invoked without a previous state");
        } else {
            Log.d("COLOCVIU TAG", "onCreate() method was invoked with a previous state");
            if (savedInstanceState.containsKey("editText1")) {
                String edit1 = savedInstanceState.getString("editText1");
                editText1.setText(edit1);
                Toast.makeText(getApplicationContext(), "Updated number of clicks" , Toast.LENGTH_SHORT).show();
            }

            if (savedInstanceState.containsKey("editText2")) {
                String edit2 = savedInstanceState.getString("editText2");
                editText2.setText(edit2);
                Toast.makeText(getApplicationContext(), "Updated number of clicks" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    String value1 = editText1.getText().toString();
                    Integer integer = Integer.valueOf(value1);
                    integer ++;

                    editText1.setText(integer + "");

                    Integer integerb = Integer.valueOf(editText2.getText().toString());
                    if(integerb + integer >= 3) {

                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                        intent.putExtra("no1", editText1.getText().toString());
                        intent.putExtra("no2", editText1.getText().toString());
                        getApplicationContext().startService(intent);
                        Log.d("TAG", "Sent service request!!!!");
                    }
                    break;

                case R.id.button2:
                    String value2 = editText2.getText().toString();
                    Integer integer2 = Integer.valueOf(value2);
                    integer2 ++;

                    editText2.setText(integer2 + "");
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("editText1", editText1.getText().toString());
        savedInstanceState.putString("editText2", editText2.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("editText1")) {
            editText1.setText(savedInstanceState.getString("editText1"));
        }
        if (savedInstanceState.containsKey("editText2")) {
            editText2.setText(savedInstanceState.getString("editText2"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Req_code) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", intent.getStringExtra(DATA));
        }
    }
}