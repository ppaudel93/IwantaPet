package project.com.iwantapet;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static project.com.iwantapet.LoginScreen.email;

public class LnfActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    EditText type,name,description;
    Button post;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lnf);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.imageView = (ImageView)this.findViewById(R.id.pet_image);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Button takephoto=(Button)findViewById(R.id.open_camera_lnf);
        Spinner lnf=(Spinner)findViewById(R.id.lnf_spinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.lnf_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lnf.setAdapter(adapter);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        checkeverything();

    }
    private void checkeverything(){
        description=(EditText)findViewById(R.id.desc_filler_lnf);
        description.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(description);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        post=(Button)findViewById(R.id.post_button_lnf);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation())
                    postlnf();
                else
                    Toast.makeText(LnfActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean checkValidation(){
        boolean ret=true;
        if (!Validation.hasText(description)) ret = false;
        return ret;
    }
    public void postlnf(){
        String status,type,name,poster,description,key;

    }
}
