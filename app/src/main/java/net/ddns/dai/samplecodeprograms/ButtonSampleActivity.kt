package net.ddns.dai.samplecodeprograms

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_button_sample.*

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

        when (view.id){     //switch文はないのでwhen文を使う
            R.id.textChangeButton -> {
                this.flag = if (flag){
                    changeText.text = "押してください"
                    textChangeButton.text = "BUTTON"
                    false

                }else{
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
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.menuItem -> {
                //TODO WebViewで解説ページへ
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
