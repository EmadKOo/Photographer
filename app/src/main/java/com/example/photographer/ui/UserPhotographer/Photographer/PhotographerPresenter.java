package com.example.photographer.ui.UserPhotographer.Photographer;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.photographer.Tools.Fonts;
import com.example.photographer.pojo.Category;
import com.example.photographer.pojo.Image;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PhotographerPresenter {

    FirebaseAuth mAuth;
    DatabaseReference categoriesReference, usersReference, currentCatRef;
    ArrayList<Category> allCategoriesList = new ArrayList<>();
    ArrayList<Category> userCategoriesList = new ArrayList<>();

    DatabaseReference allImagesReference;
    ArrayList<Image> allImageList = new ArrayList<>();


    //upload img to firebase
    StorageReference storageRef;
    DatabaseReference imgRef;
    String imageName;
    String fName;
    Fonts fonts;
    IPhotographer iPhotographer;
     Image currentImage;
     Category category;
    private static final String TAG = "PhotographerPresenter";

    public PhotographerPresenter(IPhotographer iPhotographer) {
        this.iPhotographer = iPhotographer;
        mAuth = FirebaseAuth.getInstance();
        categoriesReference = FirebaseDatabase.getInstance().getReference().child("categories");
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        allImagesReference = FirebaseDatabase.getInstance().getReference();
        imgRef = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void getAllCategories(final ArrayList userCategories) {
        categoriesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCategoriesList.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    allCategoriesList.add(datasnapshot.getValue(Category.class));
                }
                for (int x = 0; x < userCategories.size(); x++) {
                    for (int y = 0; y < allCategoriesList.size(); y++) {
                        if (userCategories.get(x).equals(allCategoriesList.get(y).getCategory())) {
                            userCategoriesList.add(allCategoriesList.get(y));
                        }
                    }
                }
                getAllImagesOfCurrentUser();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllCategoriesOfCurrentUser() {
        final ArrayList userList = new ArrayList();
        allImagesReference.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        if (snapshot2.getKey().equals(mAuth.getCurrentUser().getUid())) {
                            Log.d(TAG, "onDataChange: ss " + snapshot1.getKey());
                            userList.add(snapshot1.getKey());
                        }
                    }
                }
                getAllCategories(userList);
                userList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllImagesOfCurrentUser() {
        allImageList = new ArrayList<>();
        allImageList.clear();
        allImagesReference.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot = dataSnapshot.child(mAuth.getCurrentUser().getUid());
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        allImageList.add(snapshot1.getValue(Image.class));
                    }
                }
                iPhotographer.configureMainAdapter(userCategoriesList, allImageList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addImageToFirebase(Bitmap bitmapImageUpload, final Image image) {
        Log.d(TAG, "addImageToFirebase: ");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImageUpload.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        imageName = timeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference().child("images");
        final UploadTask uploadTask = storageRef.child(imageName).putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageRef = FirebaseStorage.getInstance().getReference().child("images/").child(imageName);
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            iPhotographer.imgUploadToStorage(true, "");
                            // here add image to firebaseDB
                            addImgToFirebaseDatabase(image, uri.toString());
                            Log.d(TAG, "onSuccess: " + uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            iPhotographer.imgUploadToStorage(true, e.getMessage());
                            Log.d(TAG, "onComplete: " + e.getMessage());
                        }
                    });
                } else {
                    Log.d(TAG, "onComplete: " + task.getException().toString());
                }
            }
        });
    }

    private void addImgToFirebaseDatabase(Image imageInfo, String imgPath) {
        imageInfo.setImgPath(imgPath);
        imgRef = FirebaseDatabase.getInstance().getReference().child("images")
                .child(imageInfo.getCategory().getCategory()).child(imageInfo.getPhotographerUID()).push();
        imgRef.setValue(imageInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: done");
                    iPhotographer.addImageToFirebaseDatabase(true, "");
                } else {
                    iPhotographer.addImageToFirebaseDatabase(false, task.getException().getMessage());
                    Log.d(TAG, "onComplete: failed");
                }
            }
        });
    }

    void getUserName(final String selectedCategory){
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        usersReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("fName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fName = snapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: " + fName);
                iPhotographer.getFirstName(fName);
               // getCurrentCategory(selectedCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fName = "Friend";
            }
        });
    }

    void getAllCategories(){
        final ArrayList<Category> categories = new ArrayList<>();
        currentCatRef = FirebaseDatabase.getInstance().getReference().child("categories");
        currentCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    categories.add(dataSnapshot.getValue(Category.class));
                }

                iPhotographer.getAllCategories(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    Category getSelectedCategory(ArrayList<Category> allCategories, String selected){
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getCategory().equals(selected))
                return allCategories.get(i);
        }
        return null;
    }


//    void getCurrentCategory(final String cat){
//        currentCatRef = FirebaseDatabase.getInstance().getReference().child("categories");
//        currentCatRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
//                    Category cCategory = dataSnapshot.getValue(Category.class);
//                    //   Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
//                    if (cCategory.getCategory().equals(cat)){
//                        iPhotographer.getSelectedCategoryFromSpinner(cCategory);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
