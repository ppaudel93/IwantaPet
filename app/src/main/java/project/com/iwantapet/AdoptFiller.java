package project.com.iwantapet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static project.com.iwantapet.LoginScreen.email;

public class AdoptFiller extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    EditText pettype,petname,petage,desc;
    Button postbutton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_filler);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.imageView = (ImageView)this.findViewById(R.id.pet_image);
        mAuth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Button takephoto=(Button)findViewById(R.id.open_camera);

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intent);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        registerviews();

    }
    private void registerviews(){
        pettype=(EditText)findViewById(R.id.pet_type_filler);
        pettype.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(pettype);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        postbutton=(Button)findViewById(R.id.post_button);
        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation())
                    post();
                else
                    Toast.makeText(AdoptFiller.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean checkValidation(){
        boolean ret=true;
        if (!Validation.hasText(pettype)) ret = false;
        return ret;
    }
    public void post(){
        String pettypes,petnames,petages,descr;
        petname=(EditText)findViewById(R.id.pet_name_filler);
        petage=(EditText)findViewById(R.id.pet_age_filler);
        desc=(EditText)findViewById(R.id.desc_filler);
        pettypes=pettype.getText().toString();
        petnames=petname.getText().toString();
        petages=petage.getText().toString();
        descr=desc.getText().toString();
        Adoptinfo post1= new Adoptinfo(email,pettypes,petnames,petages,descr);
        DatabaseReference mD = FirebaseDatabase.getInstance().getReference("posts");
        String postid=mD.push().getKey();
        mD.child(postid).setValue(post1);
        StorageReference petref = mStorageRef.child("adopt/"+email+postid);
        petref.putFile(imageUri);
        Toast.makeText(AdoptFiller.this, R.string.post_success,
                Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(AdoptFiller.this,MainScreen.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
