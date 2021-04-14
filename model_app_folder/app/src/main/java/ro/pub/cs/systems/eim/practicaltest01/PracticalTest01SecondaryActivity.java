package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    Button ok, cancel;
    TextView textViewSum;
    private PracticalTest01SecondaryActivity.ButtonClickListener buttonClickListener = new PracticalTest01SecondaryActivity.ButtonClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        textViewSum = findViewById(R.id.textView);
        ok = findViewById(R.id.okButton);
        cancel = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("sum")) {
            int numberOfClicks = intent.getIntExtra("sum", 0);
            textViewSum.setText(String.valueOf(numberOfClicks));
        }

        ok.setOnClickListener(buttonClickListener);
        cancel.setOnClickListener(buttonClickListener);
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.okButton:
                    setResult(RESULT_OK);
                    finish();
                    break;

                case R.id.cancelButton:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
            }
        }
    }
}