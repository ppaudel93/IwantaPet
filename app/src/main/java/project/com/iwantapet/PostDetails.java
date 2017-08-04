package project.com.iwantapet;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static project.com.iwantapet.MainScreen.statpost;

public class PostDetails extends AppCompatActivity {
    private StorageReference mStorageRef;
    Uri imageUri;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        TextView type,name,age,description,uploader;
        ImageView image = (ImageView)findViewById(R.id.pet_image_view);
        mAuth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploader=(TextView) findViewById(R.id.uploader_name);
        type = (TextView) findViewById(R.id.pet_type);
        name = (TextView) findViewById(R.id.pet_name);
        age = (TextView) findViewById(R.id.pet_age);
        description=(TextView) findViewById(R.id.desc);
        uploader.setText(statpost.pemail);
        type.setText(statpost.type);
        name.setText(statpost.name);
        age.setText(statpost.age);
        description.setText(statpost.description);
        Log.d("here is the ID", statpost.key);
        StorageReference petref=mStorageRef.child("adopt/"+statpost.pemail+statpost.key);
        //petref.getFile(imageUri);
        Button back=(Button)findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetails.this,MainScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
