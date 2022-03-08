package com.mukeshkpdeveloper.crudfirebase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukeshkpdeveloper.crudfirebase.R;
import com.mukeshkpdeveloper.crudfirebase.utils.AppPreference;
import com.mukeshkpdeveloper.crudfirebase.utils.constants.Constant;

public class LoginActivity extends AppCompatActivity {
    // creating variable for edit text, textview,
    // button, progress bar and firebase auth.
    private TextInputEditText userNameEdt, passwordEdt;
    private Button loginBtn;
    private TextView newUserTV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initializing all our variables.
        // FirebaseApp.initializeApp(this);
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        newUserTV = findViewById(R.id.idTVNewUser);
        mAuth = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);

        if (AppPreference.getBooleanPreference(LoginActivity.this,Constant.LOGGED_IN)) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        // adding click listener for our new user tv.
        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line opening a login activity.
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        // adding on click listener for our login button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String email = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter your email..", Toast.LENGTH_SHORT).show();
                    loadingPB.setVisibility(View.GONE);
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter your password..", Toast.LENGTH_SHORT).show();
                    loadingPB.setVisibility(View.GONE);
                } else {
                    // on below line we are calling a sign in method and passing email and password to it.
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // on below line we are checking if the task is success or not.
                            if (task.isSuccessful()) {
                                loadingPB.setVisibility(View.GONE);
                                AppPreference.setBooleanPreference(LoginActivity.this, Constant.LOGGED_IN,true);
                                Toast.makeText(LoginActivity.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // in on start method checking if
        // the user is already sign in.
        if (user != null) {
            Log.d("TAG", "onRestart: "+user.toString());
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        } else {
            Log.d("TAG", "onRestart: ");
        }
    }
}