package com.example.photographer.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.User;
import com.example.photographer.ui.UserAdmin.Admin.AdminActivity;
import com.example.photographer.ui.UserPhotographer.Photographer.PhotographerActivity;
import com.example.photographer.ui.UserNormal.main.MainActivity;
import com.example.photographer.ui.registeration.RegisterActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements ILogin{

    TextView welcomeTV;
    TextView continueTV;
    TextView goToSignUp;
    TextInputEditText mailLogin;
    TextInputEditText passwordLogin;
    Button loginBtn;
    RadioButton radioRemember;
    TextView forgetPassword;
    TextView blabla;

    Fonts fonts;
    LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);
        fonts = new Fonts(this);
        initViews();
        handleViews();
    }

    private void initViews(){
        mailLogin = findViewById(R.id.mailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.loginBtn);
        goToSignUp = findViewById(R.id.goToSignUp);
        welcomeTV = findViewById(R.id.continueTV);
        continueTV = findViewById(R.id.welcomeTV);
        forgetPassword = findViewById(R.id.forgetPassword);
        radioRemember = findViewById(R.id.radioRemember);
        blabla = findViewById(R.id.blabla);

        welcomeTV.setTypeface(fonts.getRedressedFont());
        continueTV.setTypeface(fonts.getJostFont());
        mailLogin.setTypeface(fonts.getComfortaaFont());
        passwordLogin.setTypeface(fonts.getComfortaaFont());
        radioRemember.setTypeface(fonts.getComfortaaFont());
        forgetPassword.setTypeface(fonts.getComfortaaFont());
        loginBtn.setTypeface(fonts.getComfortaaFont());
        blabla.setTypeface(fonts.getJostFont());
        goToSignUp.setTypeface(fonts.getJostFont());
    }

    private void handleViews(){
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mailLogin.getEditableText().toString().trim().isEmpty() ||passwordLogin.getEditableText().toString().trim().isEmpty()){
                    Snackbar.make(findViewById(android.R.id.content), "Please Fill Email and Password", Snackbar.LENGTH_LONG).show();
                }else
                    loginPresenter.login(mailLogin.getEditableText().toString(), passwordLogin.getEditableText().toString());
            }
        });
    }

    @Override
    public void login(FirebaseUser firebaseUser, User user) {
        if (firebaseUser!=null){
            Toast.makeText(this, "Hello "+ user.getfName(), Toast.LENGTH_SHORT).show();
            startActivity(detectUserType(user.getUserType()));
            finish();
        }else {
            Snackbar.make(findViewById(android.R.id.content), "Please make sure email and password are correct.", Snackbar.LENGTH_LONG).show();
        }
    }

    public Intent detectUserType(String userType){
        Intent intent;
        if (userType.equals("admin")){
            return new Intent(LoginActivity.this, AdminActivity.class);
        }else if (userType.equals("photographer")){
            return new Intent(LoginActivity.this, PhotographerActivity.class);
        }else if (userType.equals("normal")){
            return new Intent(LoginActivity.this, MainActivity.class);
        }else {
            return new Intent(LoginActivity.this, MainActivity.class);
        }
    }

}
