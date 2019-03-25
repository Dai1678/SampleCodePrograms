package net.ddns.dai.samplecodeprograms.text

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_text_sample.*
import net.ddns.dai.samplecodeprograms.R
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

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        //yourNameを作成
        val name = generateUserName(10)
        userName.text = "あなたのユーザー名は${name}です\n長押しでコピーできます"

        loginButton.setOnClickListener { view ->
            val yourPassWord = editPassword.text.toString()
            val inputUserName = userNameInput.text.toString()
            val inputUserPassWord = userPassWordInput.text.toString()

            if (inputUserPassWord == "") {
                //yourPassWordの入力を促す
                showResult(view, noInput)
            } else {
                val loginResult = name == inputUserName && yourPassWord == inputUserPassWord

                if (loginResult) {
                    //login
                    showResult(view, login)
                } else {
                    //error
                    showResult(view, error)
                }
            }

        }

        userNameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length > nameTextInputLayout.counterMaxLength) {
                    nameTextInputLayout.error = "ユーザー名は10文字以内で入力してください"
                } else {
                    nameTextInputLayout.error = null
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

    @SuppressLint("SimpleDateFormat")
    private fun showResult(view: View, result: Int) {

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
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.menuItem -> {
                val linkText = "https://qiita.com/daivr7774/items/c5807902c16624c57559"
                chromeBrowseTab(Uri.parse(linkText))

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("PrivateResource")
    private fun chromeBrowseTab(uri: Uri) {

        val intent = Intent(Intent.ACTION_SEND)
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
