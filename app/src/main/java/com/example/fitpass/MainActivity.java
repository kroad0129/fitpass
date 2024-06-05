package com.example.fitpass;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList.add(new Item("에이블짐", "구리시 교문1동 - 1초 전", "60,000원", R.drawable.ic_launcher_foreground, "작성자1"));
        itemList.add(new Item("핏블리짐", "중랑구 면목동 - 1일 전", "300,000원", R.drawable.ic_launcher_foreground, "작성자2"));
        itemList.add(new Item("에이투피짐", "광진구 구의제3동 - 26초 전", "100,000원", R.drawable.ic_launcher_foreground, "작성자3"));
        itemList.add(new Item("맥스톤탑", "5단골목 구월동 - 59초 전", "999,999원", R.drawable.ic_launcher_foreground, "작성자4"));
        itemList.add(new Item("메이크어바디", "군자동 - 3일 전", "5,000원", R.drawable.ic_launcher_foreground, "작성자5"));

        itemAdapter = new ItemAdapter(this, itemList, username);
        recyclerView.setAdapter(itemAdapter);
    }
}
