package net.ddns.dai.samplecodeprograms

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class DebugListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug_list)

        val listView = findViewById<ListView>(R.id.debug_list)
        val list = arrayListOf<String>()

        try {
            //activity一覧取得
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)

            packageInfo.activities
                    .asSequence()
                    .filter { it.name.contains(packageName) }   //ライブラリなどのactivityは表示しないように比較
                    .mapTo(list) { it.name.split(Regex("$packageName."), 0)[1] }  // activity名のみ取得できるようにsplit

        }catch (e: NameNotFoundException){
            e.printStackTrace()
        }

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = arrayAdapter //代入する形

        listView.onItemClickListener = AdapterView.OnItemClickListener{ parent, _, position, _ ->   //使ってない引数は_で省略できる
            val activityName = parent.getItemAtPosition(position)
            try {
                //パッケージ名を追加してclassを指定
                val myClass = Class.forName("$packageName.$activityName")   // + "~" + で繋げなくても$変数名でできる
                val intent = Intent(this, myClass)
                startActivity(intent)
            }catch (e: ClassNotFoundException){
                e.printStackTrace()
            }
        }
    }
}
