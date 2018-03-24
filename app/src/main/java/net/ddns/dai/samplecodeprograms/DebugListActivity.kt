package net.ddns.dai.samplecodeprograms

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class DebugListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug_list)

        val listView = findViewById<ListView>(R.id.debug_list)
        val list = arrayListOf<String>()

        var packageInfo: PackageInfo? = null    //for文で回すためにnullは代入しておいた(null代入のときは型名?を使用)
        try {
            //activity一覧取得
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }

        //変数の中身がnullでないことがわかっているなら!!でnull safetyを解除
        packageInfo!!.activities
                .filter { //ライブラリなどのActivityは表示しないように条件指定
                    it.name.contains(packageName)
                }
                .mapTo(list) { //activity名のみ取得できるようsplint
                    it.name.split(Regex(packageName + "."), 0)[1]    //kotlinでの正規表現splitは引数にRegexをとる
                }

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        listView.adapter = arrayAdapter //代入する形

        listView.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val activityName = parent.getItemAtPosition(position)
            try {
                //パッケージ名を追加してclassを指定
                val myClass = Class.forName(packageName + "." + activityName)
                val intent = Intent(this, myClass)
                startActivity(intent)
            }catch (e: ClassNotFoundException){
                e.printStackTrace()
            }
        }
    }
}
