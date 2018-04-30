package net.ddns.dai.samplecodeprograms.nearby

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import net.ddns.dai.samplecodeprograms.R
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class NearByConSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_by_con_sample)
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
                val linkText = "https://qiita.com/niusounds/items/ecc759c51da8e1f1e8a0"
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
