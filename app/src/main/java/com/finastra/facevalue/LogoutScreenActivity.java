package com.finastra.facevalue;

import android.content.Intent;
import android.media.FaceDetector;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.vision.text.Line;

public class LogoutScreenActivity extends AppCompatActivity {

    LinearLayout mLinearLayoutAccountSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_screen);

        LinearLayout mLinearLayoutAccountSignIn = (LinearLayout) findViewById(R.id.linearLayoutAccountSignIn);
        mLinearLayoutAccountSignIn.setOnClickListener(mLinearLayoutAccountSignInListener);
    }

    private View.OnClickListener mLinearLayoutAccountSignInListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(LogoutScreenActivity.this, FaceActivity.class);
            startActivity(intent);
        }
    };
}
