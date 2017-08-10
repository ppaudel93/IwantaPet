package project.com.iwantapet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static project.com.iwantapet.LandFmain.lnfpoststat;
import static project.com.iwantapet.LoginScreen.email;
import static project.com.iwantapet.MainScreen.statpost;
import static project.com.iwantapet.AdoptFiller.imagedl;

public class PostDetails extends AppCompatActivity {
    private StorageReference mStorageRef;
    Uri imageUri=null;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    EditText comment;
    Button okbutton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        myRef.keepSynced(true);
        TextView type,name,age,description,uploader;
        final ImageView image = (ImageView)findViewById(R.id.pet_image_view);
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
        Log.d("here is key", statpost.pemail+statpost.key);
        StorageReference petref=mStorageRef.child("adopt/"+statpost.pemail+statpost.key+".jpg");
        Log.d("THE LINK", petref.getDownloadUrl().toString());
        String asd =petref.getDownloadUrl().toString();
       Log.d("THE DOWNLOAD URL:", asd);
       petref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(PostDetails.this).load(uri).into(image);
                //imageUri=uri;
                //String uristring = uri.toString();
                //Bitmap bitmapimage = loadBitmap(uristring);
                //image.setImageBitmap(bitmapimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FAILED", "I REPEAT. FAILED");
            }
        });
        myRef.child("posts").child(statpost.key).child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final CommentInfo comment1 = dataSnapshot.getValue(CommentInfo.class);
                LinearLayout main = (LinearLayout)findViewById(R.id.mainpost_vertical);
                LinearLayout secondary=new LinearLayout(PostDetails.this);
                secondary.setOrientation(LinearLayout.VERTICAL);
                TextView comment = new TextView(PostDetails.this);
                TextView commenter = new TextView(PostDetails.this);
                comment.setTextSize(20);
                comment.setPadding(10,10,20,10);
                comment.setText(comment1.comment);
                commenter.setTextSize(10);
                commenter.setPadding(10,10,20,10);
                commenter.setText(comment1.commenter);
                secondary.addView(comment);
                secondary.addView(commenter);
                int orange = Color.parseColor("#81b73c");
                secondary.setBackgroundColor(orange);
                LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lay.setMargins(5,10,0,10);
                main.addView(secondary,lay);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase= FirebaseDatabase.getInstance().getReference();
        checkcomment();
        /*Button back=(Button)findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetails.this,MainScreen.class);
                startActivity(intent);
                finish();
            }
        });*/

    }
    private void checkcomment(){
        comment=(EditText)findViewById(R.id.postdetails_comment);
        comment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(comment);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        okbutton=(Button)findViewById(R.id.ok_postdetails);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation())
                    postcomment();
                else
                    Toast.makeText(PostDetails.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean checkValidation(){
        boolean ret=true;
        if (!Validation.hasText(comment)) ret = false;
        return ret;
    }
    public void postcomment(){
        String comment,poster,id;
        EditText com= (EditText)findViewById(R.id.postdetails_comment);
        comment = com.getText().toString();
        poster=email;
        DatabaseReference mD = FirebaseDatabase.getInstance().getReference("posts").child(statpost.key).child("comments");
        Log.d("here's the id", statpost.key);
        id=mD.push().getKey();
        CommentInfo comment1 = new CommentInfo(comment,poster,id);
        mD.child(id).setValue(comment1);
        Toast.makeText(PostDetails.this, R.string.post_success,
                Toast.LENGTH_SHORT).show();
        com.setText("");

    }
}
