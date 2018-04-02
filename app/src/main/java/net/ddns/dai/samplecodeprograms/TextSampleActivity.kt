package net.ddns.dai.samplecodeprograms

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_text_sample.*
import net.ddns.dai.samplecodeprograms.databinding.ActivityTextSampleBinding
import java.text.SimpleDateFormat
import java.util.*

class TextSampleActivity : AppCompatActivity() {

    private val noInput = -2
    private val login = 1
    private val error = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_sample)
        val binding = DataBindingUtil.setContentView<ActivityTextSampleBinding>(this, R.layout.activity_text_sample)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        //yourNameを作成
        val name = generateUserName(10)
        binding.userName.text = "あなたのユーザー名は $name です\n長押しでコピーできます"

        binding.loginButton.setOnClickListener { view ->
            val yourPassWord = editPassword.text.toString()
            val inputUserName = userNameInput.text.toString()
            val inputUserPassWord = userPassWordInput.text.toString()

            if (inputUserPassWord == ""){
                //yourPassWordの入力を促す
                showResult(view, noInput)
            }else{
                val loginResult = checkLogin(name, yourPassWord, inputUserName, inputUserPassWord)

                if (loginResult){
                    //login
                    showResult(view, login)
                }else{
                    //error
                    showResult(view, error)
                }
            }

        }

        binding.userNameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > binding.nameTextInputLayout.counterMaxLength) {
                    binding.nameTextInputLayout.error = "ユーザー名は10文字以内で入力してください"
                } else {
                    binding.nameTextInputLayout.error = null
                }
            }
        })

    }

    //引数の値の桁数で乱数作成
    private fun generateUserName(length: Int): String {

        val letters = "abcdefghijklmnopqrstuvwxyz0123456789"

        var str = ""
        while (str.length < length) {
            str += letters[Random().nextInt(letters.length)]
        }

        return str

    }

    //loginチェック
    private fun checkLogin(currentName: String, currentPassWord: String, inputName: String, inputPassWord: String): Boolean{

        return if (currentName == inputName){
            currentPassWord == inputPassWord
        }else{
            false
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun showResult(view: View, result: Int){

        var showText = ""
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date(System.currentTimeMillis())

        when (result) {
            noInput -> {
                showText = "パスワードを設定してください"
            }
            login -> {
                showText = "${dateFormat.format(date)}にログインしました！"
            }
            error -> {
                showText = "入力されたユーザー名やパスワードが正しくありません"
            }
        }

        Snackbar.make(view, showText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.menuItem -> {
                //TODO 記事作ってリンク更新
                val linkText = "https://www.google.co.jp/"
                chromeBrowseTab(Uri.parse(linkText))

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("PrivateResource")
    private fun chromeBrowseTab(uri: Uri){

        val  intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Intent.EXTRA_TEXT, uri.toString())

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val icon = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_share)

        val tabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                .setStartAnimations(this, R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top)
                .setExitAnimations(this, R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                .setCloseButtonIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_arrow_back))
                .setActionButton(icon, "arrow_back", pendingIntent)
                .addMenuItem("共有", pendingIntent)
                .build()

        //Chrome起動
        tabsIntent.launchUrl(this, uri)
    }
}
