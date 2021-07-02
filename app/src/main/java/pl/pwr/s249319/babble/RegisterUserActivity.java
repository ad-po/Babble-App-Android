package pl.pwr.s249319.babble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterUserActivity extends AppCompatActivity {

    //user details
    EditText usernameTxt, emailTxt, passwordTxt;
    Button registerButton;

    //database
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //initialization
        usernameTxt = findViewById(R.id.usernameText);
        emailTxt = findViewById(R.id.emailText);
        passwordTxt = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.button);

        firebaseAuth = FirebaseAuth.getInstance();

        //register button
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterUserActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    Registration(username, email, password);
                    Toast.makeText(RegisterUserActivity.this, "User added correctly!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Registration(final String name, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    //Realtime Database appearance
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", name);
                    hashMap.put("imageURL", "default");

                    //Going to Main activity
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(RegisterUserActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterUserActivity.this, "Incorrect values", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}