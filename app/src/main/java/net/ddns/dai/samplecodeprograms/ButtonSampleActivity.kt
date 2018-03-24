package net.ddns.dai.samplecodeprograms

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_button_sample.*
import kotlinx.android.synthetic.main.toolbar.*

class ButtonSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_sample)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "fabが押されました！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
