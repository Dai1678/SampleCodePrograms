package net.ddns.dai.samplecodeprograms.button

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_button_sample.*
import net.ddns.dai.samplecodeprograms.R

class ButtonSampleActivity : AppCompatActivity(), View.OnClickListener {

    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_sample)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        textChangeButton.setOnClickListener(this)   //kotlin-android-extensionsのおかげでfindViewByIdの一文を書かなくてもxmlのidで指定できる！！
        fab.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {

        when (view.id) {     //switch文はないのでwhen文を使う
            R.id.textChangeButton -> {
                this.flag = if (flag) {
                    changeText.text = "押してください"
                    textChangeButton.text = "BUTTON"
                    false

                } else {
                    changeText.text = "Buttonが押されました"
                    textChangeButton.text = "戻す"
                    true
                }
            }

            R.id.fab -> {
                Snackbar.make(view, "fabが押されました！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        }
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
                val linkText = "https://qiita.com/daivr7774/items/3bed5793a124aa4a44aa"
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
