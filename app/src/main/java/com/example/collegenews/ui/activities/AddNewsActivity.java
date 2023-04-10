package com.example.collegenews.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegenews.R;
import com.example.collegenews.databinding.ActivityAddNewsBinding;
import com.example.collegenews.model.NewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class AddNewsActivity extends AppCompatActivity {

    private ActivityAddNewsBinding binding;
    int PICK_IMAGE_REQUEST = 2000;
    String category;
    String imageUrl = "";
    FirebaseStorage storage;
    StorageReference storageRef;
    private Uri filePath;
    FirebaseDatabase database;
    List<NewsModel> newsList;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        binding = ActivityAddNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("news");

        newsList = new ArrayList<>();

        initData();


        category = "Academics";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        binding.categorySpinner.setAdapter(adapter);

        // listen to the spinner selected options...
        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                category = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddNewsActivity.this, "" + category, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opengallery and select image....

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checking all news Parameters

                if (binding.edtTitle.getText().toString().trim() != "" && binding.edtDescription.getText().toString().trim() != "" && binding.edtAuthor.getText().toString().trim() != "") {

                    NewsModel news = new NewsModel();
                    news.setTitle(binding.edtTitle.getText().toString());
                    news.setDescription(binding.edtDescription.getText().toString());
                    news.setAuthor(binding.edtAuthor.getText().toString());
                    news.setCategory(category);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());
                    news.setUploadedat(currentDateandTime);
                    news.setUrlimage(imageUrl);


                    // call update ..
                    newsList.add(news);

                    //set ---> update....
                    myRef.setValue(newsList);
                    Toast.makeText(AddNewsActivity.this, "" + " News created successfully ", Toast.LENGTH_SHORT).show();

                    // imageUrl
                    //uploaded at


                    //

                    // here we will create the news Model and then we will add that news Model to List
                    // then finally we will add this list to fireBase ...
                }


            }
        });
        binding.btnuploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });

    }

    private void initData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null && !snapshot.getValue().equals("")) {

                    newsList.clear();
                    List newsRemote = (List) snapshot.getValue();
//                Toast.makeText(getActivity().getBaseContext(), "" + newsRemote.toString(), Toast.LENGTH_SHORT).show();
                    if (newsRemote != null) {
                        for (int index = 0; index < newsRemote.size(); index++) {
                            HashMap<Integer, Object> tempData = new HashMap<>();
                            tempData.putAll((Map<? extends Integer, ? extends Object>) newsRemote.get(index));
                            if (tempData != null) {
                                NewsModel tempNews = new NewsModel();
                                tempNews.setTitle((String) tempData.get("title"));
                                tempNews.setAuthor((String) tempData.get("author"));
                                tempNews.setCategory((String) tempData.get("category"));
                                tempNews.setUploadedat((String) tempData.get("uploadedat"));
                                tempNews.setDescription((String) tempData.get("description"));
                                tempNews.setUrlimage((String) tempData.get("urlimage"));


                                newsList.add(tempNews);


                            }
                        }

                    }


//                       newsList.addAll(mlist);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
// Create a reference to the image file you want to upload
            filePath = data.getData();
            try {
                // on below line getting bitmap for image from file uri.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // on below line setting bitmap for our image view.
                binding.ivPreview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


//            ProgressDialog progressDialog = new ProgressDialog(this);
//            // on below line setting title and message for our progress dialog and displaying our progress dialog.
//            progressDialog.setTitle("Uploading...");
//            progressDialog.setMessage("Uploading your image..");
//            progressDialog.show();
            // Upload the image to Firebase Storage

            // Use the image URI as needed
        }


    }

    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageRef
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog

                                    // url...
                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                            new OnCompleteListener<Uri>() {

                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    imageUrl = task.getResult().toString();
                                                    //next work with URL

                                                }
                                            });
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddNewsActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddNewsActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }


}