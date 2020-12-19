package com.example.photographer.ui.UserAdmin.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photographer.R;
import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements IAdmin {

    AdminPresenter adminPresenter;
    AlertDialog alertDialog;
    TextView grantTV;
    EditText mailPermission;
    RadioGroup radioGroup;
    RadioButton radioAdmin;
    RadioButton radioPhotographer;
    Button btnSubmit;

    String userType = "";
    Fonts fonts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        fonts = new Fonts(this);
        adminPresenter = new AdminPresenter(AdminActivity.this, this);

    }


    public void addCategoryDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.add_category_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        final EditText idET = view.findViewById(R.id.idET);
        final EditText categoryET = view.findViewById(R.id.categoryET);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                adminPresenter.addCategoryToDatabase(new Category(Integer.parseInt(idET.getEditableText().toString()), FirebaseAuth.getInstance().getCurrentUser().getUid(),categoryET.getEditableText().toString(),""));
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void grantPermissionDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.grant_permission_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        grantTV = view.findViewById(R.id.grantTV);
        mailPermission = view.findViewById(R.id.mailPermission);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioAdmin = view.findViewById(R.id.radioAdmin);
        radioPhotographer = view.findViewById(R.id.radioPhotographer);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        grantTV.setTypeface(fonts.getJostFont());
        btnSubmit.setTypeface(fonts.getJostFont());
        mailPermission.setTypeface(fonts.getComfortaaFont());
        radioAdmin.setTypeface(fonts.getComfortaaFont());
        radioPhotographer.setTypeface(fonts.getComfortaaFont());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioAdmin.isChecked())
                    userType = "admin";
                else if (radioPhotographer.isChecked())
                    userType = "photographer";

                adminPresenter.grantPermission(userType, mailPermission.getEditableText().toString());
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void addCategory(boolean status, String exception) {
        if (status){
            Toast.makeText(this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        }else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void addCategoryDialog(View view) {
        addCategoryDialog();
    }

    public void grantPermission(View view) {
        grantPermissionDialog();
    }
}