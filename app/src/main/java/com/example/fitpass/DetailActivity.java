package com.example.fitpass;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private String username;
    private int postId;
    private String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        postId = intent.getIntExtra("postId", 0);
        author = intent.getStringExtra("AUTHOR");

        ImageView detailImageView = findViewById(R.id.detailImageView);
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        Button chatButton = findViewById(R.id.chatButton);

        if (getIntent() != null) {
            int imageResId = getIntent().getIntExtra("IMAGE_RES_ID", R.drawable.ic_launcher_foreground);
            String title = getIntent().getStringExtra("TITLE");
            String location = getIntent().getStringExtra("LOCATION");
            String price = getIntent().getStringExtra("PRICE");
            String description = getIntent().getStringExtra("DESCRIPTION");

            detailImageView.setImageResource(imageResId);
            titleTextView.setText(title);
            locationTextView.setText(location);
            priceTextView.setText(price);
            descriptionTextView.setText(description);
        }

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(DetailActivity.this, ChatActivity.class);
                chatIntent.putExtra("ROOM_ID", author + "_room");
                chatIntent.putExtra("USERNAME", username);
                chatIntent.putExtra("AUTHOR", author);
                startActivity(chatIntent);
            }
        });
    }
}
