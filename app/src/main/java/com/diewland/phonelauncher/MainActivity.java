package com.diewland.phonelauncher;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView txt_tel;
    private Button[] btn_nums;
    private Button btn_star;
    private Button btn_sharp;
    private ImageButton btn_del;
    private ImageButton btn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        txt_tel = (TextView)findViewById(R.id.txt_tel);
        btn_nums = new Button[]{
            (Button)findViewById(R.id.btn_0),
            (Button)findViewById(R.id.btn_1),
            (Button)findViewById(R.id.btn_2),
            (Button)findViewById(R.id.btn_3),
            (Button)findViewById(R.id.btn_4),
            (Button)findViewById(R.id.btn_5),
            (Button)findViewById(R.id.btn_6),
            (Button)findViewById(R.id.btn_7),
            (Button)findViewById(R.id.btn_8),
            (Button)findViewById(R.id.btn_9),
        };
        btn_star = (Button)findViewById(R.id.btn_star);
        btn_sharp = (Button)findViewById(R.id.btn_sharp);
        btn_del = (ImageButton)findViewById(R.id.btn_del);
        btn_call = (ImageButton)findViewById(R.id.btn_call);

        for(int i=0; i<=9; i++){
            btn_nums[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ((Button) v).getText().toString();
                    append(s);

                    // backdoor to android setting
                    if(txt_tel.getText().toString().equals("4430203593")) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                }
            });
        }

        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append("*");
            }
        });

        btn_sharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                append("#");
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del();
            }
        });

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                txt_tel.setText("");
                return false;
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_tel.getText().length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + Uri.encode(txt_tel.getText().toString())));
                    startActivity(intent);
                }
            }
        });

    }

    // utility
    private void append(String v){
        String t = txt_tel.getText().toString();
        if(t.length() < 20) {
            txt_tel.append(v);
        }
    }

    private void del(){
        String t = txt_tel.getText().toString();
        if(t.length() >= 1){
            txt_tel.setText(t.substring(0, t.length() - 1));
        }
    }


    // event

    @Override
    public void onBackPressed() {
        // disable back button
    }

    // disable long press home
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
