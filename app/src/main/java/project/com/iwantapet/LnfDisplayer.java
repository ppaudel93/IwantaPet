package project.com.iwantapet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import static project.com.iwantapet.LandFmain.lnfpoststat;

public class LnfDisplayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lnf_displayer);
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

    }
}
