package com.example.lostandfound;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Found extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Button choose_found,upload_found;
    private TextView alert_choose_found;
    private FirebaseAuth auth;
    private EditText name_found,roll_found,phone_found,item_found,place_found;
    private Button Submit_found;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root= db.getReference().child("Users").child("Found_info");
    HashMap<String,String> userMap= new HashMap<>();
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private Uri ImageUri;
    private int upload_count = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);


        auth = FirebaseAuth.getInstance();
        name_found = findViewById(R.id.found_name);
        roll_found = findViewById(R.id.found_roll_number);
        phone_found = findViewById(R.id.found_phone_number);
        item_found = findViewById(R.id.found_item);
        place_found = findViewById(R.id.found_place);
        Submit_found = findViewById(R.id.submit_found);
        alert_choose_found = findViewById(R.id.alert_choose_found);
        choose_found = findViewById(R.id.choose_found);
        upload_found = findViewById(R.id.upload_found);


        choose_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });

        upload_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

                for(upload_count = 0; upload_count < ImageList.size(); upload_count++){

                    Uri IndividualImage = ImageList.get(upload_count);
                    StorageReference ImageName = ImageFolder.child("Image"+ IndividualImage.getLastPathSegment());

                    ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    /*StoreLink(url);*/
                                    userMap.put("imglink_found",url);

                                    // root.push().setValue(userMap);

                                    alert_choose_found.setText("Images are Uploaded");
                                    upload_found.setVisibility(View.GONE);
                                    ImageList.clear();

                                }
                            });





                        }
                    });
                }
            }
        });
        Submit_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam_found = name_found.getText().toString();
                String rol_found = roll_found.getText().toString();
                String phon_found = phone_found.getText().toString();
                String itm_found = item_found.getText().toString();
                String plac_found = place_found.getText().toString();



                userMap.put("name_found",nam_found);
                userMap.put("roll_number_found",rol_found);
                userMap.put("phone_number_found",phon_found);
                userMap.put("item_found",itm_found);
                userMap.put("place_found",plac_found);

                root.push().setValue(userMap);


                startActivity(new Intent(Found.this, Submit.class));
            }
        });
    }

    /*private void StoreLink(String url) {

        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserOne");

        //HashMap<String,String> hashMap = new HashMap<>();
        userMap.put("Imglink_found",url);

        root.push().setValue(userMap);

        alert_choose_found.setText("Image is Uploaded");
        upload_found.setVisibility(View.GONE);
        ImageList.clear();
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){

            if(resultCode == RESULT_OK){

                if(data.getClipData() != null) {

                    int countClipData = data.getClipData().getItemCount();

                    int currentImageSelect = 0;

                    while(currentImageSelect < countClipData){

                        ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                        ImageList.add(ImageUri);
                        currentImageSelect = currentImageSelect + 1;

                    }
                    alert_choose_found.setVisibility(View.VISIBLE);
                    alert_choose_found.setText("You have selected " + ImageList.size() + " images");
                    choose_found.setVisibility(View.GONE);

                }else{
                    Toast.makeText(this,"Please select at least two images",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

}


