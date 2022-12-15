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

public class Lost extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Button choose_lost,upload_lost;
    private TextView alert_choose_lost;
    private FirebaseAuth auth;
    private EditText name_lost,roll_lost,phone_lost,item_lost,place_lost;
    private Button Submit_lost;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root= db.getReference().child("Users").child("Lost_info");

    HashMap<String,String> userMap= new HashMap<>();
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private Uri ImageUri;
    private int upload_count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);


        auth = FirebaseAuth.getInstance();
        name_lost = findViewById(R.id.lost_name);
        roll_lost = findViewById(R.id.lost_roll_number);
        phone_lost = findViewById(R.id.lost_phone_number);
        item_lost = findViewById(R.id.lost_item);
        place_lost = findViewById(R.id.lost_place);
        Submit_lost = findViewById(R.id.submit_lost);
        alert_choose_lost = findViewById(R.id.alert_choose_lost);
        choose_lost = findViewById(R.id.choose_lost);
        upload_lost = findViewById(R.id.upload_lost);



        choose_lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });

        upload_lost.setOnClickListener(new View.OnClickListener() {
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
                                   /* StoreLink(url);*/
                                    userMap.put("imglink_lost",url);

                                    // root.push().setValue(userMap);

                                    alert_choose_lost.setText("Images are Uploaded");
                                    upload_lost.setVisibility(View.GONE);
                                    ImageList.clear();
                                }
                            });





                        }
                    });
                }
            }
        });
        Submit_lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam_lost = name_lost.getText().toString();
                String rol_lost = roll_lost.getText().toString();
                String phon_lost = phone_lost.getText().toString();
                String itm_lost = item_lost.getText().toString();
                String plac_lost = place_lost.getText().toString();



                userMap.put("name_lost",nam_lost);
                userMap.put("roll_number_lost",rol_lost);
                userMap.put("phone_number_lost",phon_lost);
                userMap.put("item_lost",itm_lost);
                userMap.put("place_lost",plac_lost);

                root.push().setValue(userMap);


                startActivity(new Intent(Lost.this, Submit.class));
            }
        });
    }

    /*private void StoreLink(String url) {

        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserOne");

        //HashMap<String,String> hashMap = new HashMap<>();
        userMap.put("Imglink_found",url);

        root.push().setValue(userMap);

        alert_choose_lost.setText("Image is Uploaded");
        upload_lost.setVisibility(View.GONE);
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
                    alert_choose_lost.setVisibility(View.VISIBLE);
                    alert_choose_lost.setText("You have selected " + ImageList.size() + " images");
                    choose_lost.setVisibility(View.GONE);

                }else{
                    Toast.makeText(this,"Please select at least two images",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

}


