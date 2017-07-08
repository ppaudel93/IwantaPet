package project.com.iwantapet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import static project.com.iwantapet.LoginScreen.email;

public class DiscussionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        Button creatediscuss = (Button)findViewById(R.id.create_forum);
        creatediscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodiscussfiller=new Intent(DiscussionActivity.this,DiscussFiller.class);
                startActivity(gotodiscussfiller);
            }
        });
    }
}
