package net.ddns.dai.samplecodeprograms;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import net.ddns.dai.samplecodeprograms.databinding.ActivityTextSampleBinding;

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
        final ActivityTextSampleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_text_sample);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final String name = generateUserName();

        binding.userName.setText("あなたのユーザー名は " + name + " です\n長押しでコピーできます");

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yourPassWord = binding.editPassword.getText().toString();
                String inputUserName = binding.userNameInput.getText().toString();
                String inputUserPassWord = binding.userPassWordInput.getText().toString();

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

        binding.userNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > binding.nameTextInputLayout.getCounterMaxLength()){
                    binding.nameTextInputLayout.setError("ユーザー名は10文字以内で入力してください");
                }else{
                    binding.nameTextInputLayout.setError(null);
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
                //TODO 記事作ってリンク更新
                String linkText = "https://www.google.co.jp/";
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
