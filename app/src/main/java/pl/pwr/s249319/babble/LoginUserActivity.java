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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUserActivity extends AppCompatActivity {

    //login details
    EditText emailLoginTxt, passwordLoginTxt;
    Button loginButton, firstTimeButton;

    //database
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //checking user existance
        if(firebaseUser != null){
            Intent i = new Intent (LoginUserActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        //user login info
        emailLoginTxt = findViewById(R.id.usernameLoginText);
        passwordLoginTxt = findViewById(R.id.passwordLoginText);

        loginButton = findViewById(R.id.buttonLogin);
        firstTimeButton = findViewById(R.id.buttonFirstTime);

        //database:
        firebaseAuth = FirebaseAuth.getInstance();

        //first time?
        firstTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginUserActivity.this, RegisterUserActivity.class);
                startActivity(i);
            }
        });


        //login button
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = emailLoginTxt.getText().toString();
                String password = passwordLoginTxt.getText().toString();

                //check in firebase:
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginUserActivity.this, "Incorrect values ", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(LoginUserActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(LoginUserActivity.this, "Can't login, try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}