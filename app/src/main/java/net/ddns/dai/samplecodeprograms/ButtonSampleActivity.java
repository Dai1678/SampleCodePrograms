package net.ddns.dai.samplecodeprograms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonSampleActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_sample);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        button = findViewById(R.id.textChangeButton);
        button.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.textChangeButton:
                TextView textView = findViewById(R.id.changeText);

                if (flag){
                    textView.setText("押してください");
                    button.setText("BUTTON");
                    flag = false;

                }else{
                    textView.setText("Buttonが押されました!");
                    button.setText("戻す");
                    flag = true;
                }

                break;

            case R.id.fab:
                Snackbar.make(view, "fabが押されました！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menuItem:
                //TODO WebViewで解説ページへ
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
