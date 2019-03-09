package net.ddns.dai.samplecodeprograms.list

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_list_sample.*
import kotlinx.coroutines.*
import net.ddns.dai.samplecodeprograms.R

class ListSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sample)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val listItem = ArrayList<String>()
        listItem.add("画面を下に引っ張るとlistにデータが追加されます")

        //ListView
        val listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem)
        list_view.adapter = listAdapter

        //SwipeRefreshLayout
        swipe_refresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        swipe_refresh.setOnRefreshListener {
            //onRefresh()
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val addItemJob = async { listItem.add("Data") }
                    val syncAdapterJob = async {
                        listAdapter.notifyDataSetChanged()
                        addItemJob.await()
                    }
                    async {
                        swipe_refresh.isRefreshing = false
                        syncAdapterJob.await()
                    }

                } catch (e: CancellationException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
