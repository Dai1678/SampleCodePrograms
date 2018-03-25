package net.ddns.dai.samplecodeprograms;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DebugListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_list);

        ListView listView = findViewById(R.id.debug_list);
        List<String> list = new ArrayList<>();

        PackageInfo packageInfo = null;
        try {
            //activity一覧を取得
            getPackageManager();
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        assert packageInfo != null;
        for (ActivityInfo activityInfo : packageInfo.activities){
            //ライブラリなどのactivityは表示しないように条件指定
            if (activityInfo.name.contains(getPackageName())){
                //activity名のみ取得できるようにsplit
                String activityName = activityInfo.name.split(String.format("%s.", getPackageName()), 0)[1];
                list.add(activityName);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String activityName = (String) parent.getItemAtPosition(position);
                try {
                    //パッケージ名を追加してclassを指定
                    Class myClass = Class.forName(getPackageName() + "." + activityName);
                    Intent intent = new Intent(getApplicationContext(), myClass);
                    startActivity(intent);

                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
