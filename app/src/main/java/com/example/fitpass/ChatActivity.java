package com.example.fitpass;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private ListView messageListView;
    private EditText messageEditText;
    private Button sendButton;
    private ArrayAdapter<String> messageAdapter;
    private List<String> messageList;
    private Socket socket;
    private String roomId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageListView = findViewById(R.id.messageListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        messageListView.setAdapter(messageAdapter);

        roomId = getIntent().getStringExtra("ROOM_ID");
        username = getIntent().getStringExtra("USERNAME");

        Log.d(TAG, "roomId: " + roomId);
        Log.d(TAG, "username: " + username);

        try {
            socket = IO.socket("http://218.38.190.81:10001");
            socket.connect();
            socket.emit("joinRoom", new JSONObject().put("roomId", roomId).put("username", username));
        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

        socket.off("message");
        socket.off("chatHistory");

        socket.on("chatHistory", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray data = (JSONArray) args[0];
                        try {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject messageData = data.getJSONObject(i);
                                String message = messageData.getString("message");
                                String sender = messageData.getString("username");
                                messageList.add(sender + ": " + message);
                            }
                            messageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            String message = data.getString("message");
                            String sender = data.getString("username");
                            messageList.add(sender + ": " + message);
                            messageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (!message.isEmpty()) {
                    try {
                        JSONObject messageObject = new JSONObject();
                        messageObject.put("message", message);
                        messageObject.put("username", username);
                        socket.emit("message", messageObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    messageEditText.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
