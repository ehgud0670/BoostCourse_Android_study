package org.techtown.list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SingerAdapter singerAdapter;

    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

        ListView listView = findViewById(R.id.listView);

        singerAdapter = new SingerAdapter();
        singerAdapter.addItem(new SingerItem("소녀시대", "010-1000-1000", R.drawable.idol));
        singerAdapter.addItem(new SingerItem("에이핑크", "010-1234-1234", R.drawable.idol));
        singerAdapter.addItem(new SingerItem("트와이스", "010-2297-5762", R.drawable.idol));
        singerAdapter.addItem(new SingerItem("레드벨벳", "010-9330-1994", R.drawable.idol));
        singerAdapter.addItem(new SingerItem("여자친구", "010-2531-6735", R.drawable.idol));


        listView.setAdapter(singerAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingerItem item = (SingerItem) singerAdapter.getItem(i);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                String mobile = editText2.getText().toString();

                singerAdapter.addItem(new SingerItem(name,mobile,R.drawable.idol));
                singerAdapter.notifyDataSetChanged();
            }
        });
    }

    class SingerAdapter extends BaseAdapter {


        // 데이터 관리 ArrayList
        ArrayList<SingerItem> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        void addItem(SingerItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        // 각 아이템의 뷰 생성
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            SingerItemView view2;
            if(view == null){
                view2 = new SingerItemView(getApplicationContext());
            } else {
                view2 = (SingerItemView)view;
            }

            SingerItem item = items.get(i);
            view2.setName(item.getName());
            view2.setMobile(item.getMobile());
            view2.setImageView(item.getResId());

            return view2;
        }
    }
}
