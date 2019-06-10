package com.multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etInputNumber;
    Button btnThreadOne, btnThreadTwo;
    TextView tvThreadOutput;
    CountHandler ch = new CountHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread m = Thread.currentThread();
        Log.e("Hello ", m.getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("Hello ", "I am ON");

        etInputNumber = (EditText) findViewById(R.id.etInputNumber);
        btnThreadOne = (Button) findViewById(R.id.btnThreadOne);
        btnThreadTwo = (Button) findViewById(R.id.btnThreadTwo);
        tvThreadOutput = (TextView) findViewById(R.id.tvThreadOutput);

        btnThreadOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(etInputNumber.getText().toString());
                CountThread t = new CountThread(n);
                t.start();
            }
        });

        btnThreadTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(etInputNumber.getText().toString());
                TabelThread t = new TabelThread(n);
                t.start();
            }
        });
    }

    class CountThread extends Thread {
        int n;

        public CountThread(int n) {
            this.n = n;
        }

        public void run() {
            for (int i = 1; i <= n; i++) {
                Log.e("Count:", i + "");
                //tvOutput.setText("count:"+i);
                Bundle bn = new Bundle();
                bn.putInt("Count", i);
                Message msg = new Message();
                msg.setData(bn);
                ch.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class TabelThread extends Thread {
        int n;

        public TabelThread(int n) {
            this.n = n;
        }

        public void run() {
            for (int i = 1; i <= 10; i++) {
                Log.e("Table:", (i * n) + "");
                tvThreadOutput.setText("Table:" + i * n);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class CountHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Bundle bn = msg.getData();
            int c = bn.getInt("Count");
            tvThreadOutput.setText("Count:" + c);
            int n = Integer.parseInt(etInputNumber.getText().toString());
        }
    }
}