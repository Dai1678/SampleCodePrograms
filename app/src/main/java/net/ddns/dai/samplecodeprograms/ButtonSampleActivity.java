package net.ddns.dai.samplecodeprograms;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
                String linkText = "https://qiita.com/daivr7774/items/3bed5793a124aa4a44aa";
                chromeBrowseTab(Uri.parse(linkText));

                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void chromeBrowseTab(Uri uri){

        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, uri.toString());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share);

        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStartAnimations(this, android.R.anim.slide_out_right, android.R.anim.slide_in_left)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back))
                .setActionButton(icon, "arrow_back", pendingIntent)
                .addMenuItem("共有", pendingIntent)
                .build();

        tabsIntent.launchUrl(this, uri);

    }

}
