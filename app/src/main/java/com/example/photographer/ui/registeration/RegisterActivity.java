package com.example.photographer.ui.registeration;

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
import com.example.photographer.ui.CategoriesRegistration.CategoriesActivity;
import com.example.photographer.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements IRegister{

    TextView fillTV;
    TextView goToLogin;
    TextInputEditText fName;
    TextInputEditText lName;
    TextInputEditText mail;
    TextInputEditText password;
    RadioButton remember;
    RadioButton terms;
    Button registerBtn;

    User user;
    Fonts fonts;
    RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fonts = new Fonts(this);
        registerPresenter = new RegisterPresenter(this);
        initViews();
    }

    private void initViews(){
        fillTV = findViewById(R.id.fillTV);
        goToLogin = findViewById(R.id.goToLogin);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        terms = findViewById(R.id.terms);
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegisterationButton();
            }
        });


        fillTV.setTypeface(fonts.getRedressedFont());
        fName.setTypeface(fonts.getComfortaaFont());
        lName.setTypeface(fonts.getComfortaaFont());
        mail.setTypeface(fonts.getComfortaaFont());
        password.setTypeface(fonts.getComfortaaFont());
        remember.setTypeface(fonts.getComfortaaFont());
        terms.setTypeface(fonts.getComfortaaFont());
        registerBtn.setTypeface(fonts.getComfortaaFont());
        goToLogin.setTypeface(fonts.getJostFont());

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    private void handleRegisterationButton(){
           user = new User("", fName.getEditableText().toString(), lName.getEditableText().toString(), mail.getEditableText().toString(), password.getEditableText().toString(), "normal","","");
            if (    fName.getEditableText().toString().isEmpty() ||
                    lName.getEditableText().toString().isEmpty() ||
                    mail.getEditableText().toString().isEmpty() ||
                    password.getEditableText().toString().isEmpty()){
                Snackbar.make(findViewById(android.R.id.content), "Please make sure all fields are filled.", Snackbar.LENGTH_LONG).show();

            }else {
                registerPresenter.createUser(getApplicationContext(), user);
            }
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
    }

    @Override
    public void onFailed(String exception) {
        Snackbar.make(findViewById(android.R.id.content), "Please make sure email and password are correct.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void addUserToDatabase(boolean status) {
        if (status){
            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            finish();
        }else {
        Snackbar.make(findViewById(android.R.id.content), "Failed, Try Later", Snackbar.LENGTH_LONG).show();
        }
    }
}