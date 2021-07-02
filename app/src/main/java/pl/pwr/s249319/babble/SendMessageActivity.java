package pl.pwr.s249319.babble;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import pl.pwr.s249319.babble.Prototypes.User;

public class SendMessageActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;

    EditText sendMessageTxt;
    ImageButton sendMessageButton;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        imageView = findViewById(R.id.user_image);
        username = findViewById(R.id.username_value);

        sendMessageButton = findViewById(R.id.button_send_message);
        sendMessageTxt = findViewById(R.id.text_send);

        //------------------------------------------------------------------------------------------

        intent = getIntent();
        String userid = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = sendMessageTxt.getText().toString();
                if(!text.equals("")){
                    sendMessage(firebaseUser.getUid(),userid,text);
                }else{
                    Toast.makeText(SendMessageActivity.this,"Your are trying to send empty message",Toast.LENGTH_SHORT);

                }
                sendMessageTxt.setText("");
            }
        });

    }

    private void sendMessage(String sender, String receiver, String text){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Sender", sender);
        hashMap.put("Receiver", receiver);
        hashMap.put("Message content", text);

        databaseRef.child("Chats").push().setValue(hashMap);
    }
}