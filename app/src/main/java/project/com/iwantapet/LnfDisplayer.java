package project.com.iwantapet;

import android.graphics.Color;
import android.net.Uri;
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


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static project.com.iwantapet.DiscussionActivity.discpost;
import static project.com.iwantapet.LandFmain.lnfpoststat;
import static project.com.iwantapet.LoginScreen.email;
import static project.com.iwantapet.MainScreen.statpost;

public class LnfDisplayer extends AppCompatActivity {
    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    EditText comment;
    Button okbutton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lnf_displayer);
        myRef.keepSynced(true);
        TextView status,type,name,poster,desc;
        status=(TextView)findViewById(R.id.statusview);
        type=(TextView)findViewById(R.id.typeview);
        name=(TextView)findViewById(R.id.nameview);
        poster=(TextView)findViewById(R.id.posterview);
        desc=(TextView)findViewById(R.id.descview);
        status.setText(lnfpoststat.status);
        type.setText(lnfpoststat.type);
        name.setText(lnfpoststat.name);
        poster.setText(lnfpoststat.poster);
        desc.setText(lnfpoststat.description);
        final ImageView image = (ImageView)findViewById(R.id.image_lnf_display);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference lnfref=mStorageRef.child("lnf/"+lnfpoststat.poster+lnfpoststat.key);
        lnfref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(LnfDisplayer.this).load(uri).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FAILED", "I REPEAT. FAILED");

            }
        });
        myRef.child("lnf").child(lnfpoststat.key).child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final CommentInfo comment1 = dataSnapshot.getValue(CommentInfo.class);
                LinearLayout main = (LinearLayout)findViewById(R.id.lnf_main_vertical);
                LinearLayout secondary=new LinearLayout(LnfDisplayer.this);
                secondary.setOrientation(LinearLayout.VERTICAL);
                TextView comment = new TextView(LnfDisplayer.this);
                TextView commenter = new TextView(LnfDisplayer.this);
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



    }
    private void checkcomment(){
        comment=(EditText)findViewById(R.id.lnf_comment);
        comment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(comment);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        okbutton=(Button)findViewById(R.id.lnf_ok);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation())
                    postcomment();
                else
                    Toast.makeText(LnfDisplayer.this, "Form contains error", Toast.LENGTH_LONG).show();
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
        EditText com= (EditText)findViewById(R.id.lnf_comment);
        comment = com.getText().toString();
        poster=email;
        DatabaseReference mD = FirebaseDatabase.getInstance().getReference("lnf").child(lnfpoststat.key).child("comments");
        Log.d("here's the id", lnfpoststat.key);
        id=mD.push().getKey();
        CommentInfo comment1 = new CommentInfo(comment,poster,id);
        mD.child(id).setValue(comment1);
        Toast.makeText(LnfDisplayer.this, R.string.post_success,
                Toast.LENGTH_SHORT).show();
        com.setText("");

    }
}
