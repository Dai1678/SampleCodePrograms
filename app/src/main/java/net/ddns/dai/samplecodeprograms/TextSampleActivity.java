package net.ddns.dai.samplecodeprograms;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TextSampleActivity extends AppCompatActivity {

    private final int NoInput = -2;
    private final int LOGIN = 1;
    private final int ERROR = -1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sample);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final String name = generateUserName();

        TextView userName = findViewById(R.id.userName);
        userName.setText("あなたのユーザー名は " + name + " です\n長押しでコピーできます");

        Button loginButton = findViewById(R.id.loginButton);
        final EditText editPassWord = findViewById(R.id.editPassword);
        final TextInputEditText userNameInput = findViewById(R.id.userNameInput);
        final TextInputEditText userPassWordInput = findViewById(R.id.userPassWordInput);

        final TextInputLayout nameTextInputLayout = findViewById(R.id.nameTextInputLayout);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yourPassWord = editPassWord.getText().toString();
                String inputUserName = userNameInput.getText().toString();
                String inputUserPassWord = userPassWordInput.getText().toString();

                if (inputUserName.equals("")){
                    //yourPassWordの入力を促す
                    showResult(view, NoInput);
                }else{
                    boolean loginResult = name.equals(inputUserName) && yourPassWord.equals(inputUserPassWord);

                    if (loginResult){
                        //login
                        showResult(view, LOGIN);
                    }else{
                        //error
                        showResult(view, ERROR);
                    }
                }

            }
        });

        userNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > nameTextInputLayout.getCounterMaxLength()){
                    nameTextInputLayout.setError("ユーザー名は10文字以内で入力してください");
                }else{
                    nameTextInputLayout.setError(null);
                }
            }


        });

    }

    private String generateUserName(){

        final String letters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < 10){
            int val = random.nextInt(letters.length());
            stringBuilder.append(letters.charAt(val));
        }

        return stringBuilder.toString();

    }

    @SuppressLint("SimpleDateFormat")
    private void showResult(View view, int result){

        String showText = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        switch (result){
            case NoInput:
                showText = "パスワードを設定してください";
                break;

            case LOGIN:
                showText = dateFormat.format(date) + "にログインしました!";
                break;

            case ERROR:
                showText = "入力されたユーザー名やパスワードが正しくありません";
        }

        Snackbar.make(view, showText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

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
                String linkText = "https://qiita.com/daivr7774/items/c5807902c16624c57559";
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
