package com.finastra.facevalue;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {


    private TextView mTvConfirmUsernameTransferTo;
    private Button mBtnConfirm;

    private int mFaceGraphicScreenNum;
    private String mUsername;
    private String mUsernameTransferTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        mFaceGraphicScreenNum = getIntent().getIntExtra("FaceGraphic", 0);
        mUsername = intent.getStringExtra("Username");
        mUsernameTransferTo = intent.getStringExtra("UsernameTransferTo");

        mTvConfirmUsernameTransferTo = (TextView) findViewById(R.id.tvConfirmUsernameTransferTo);
        mTvConfirmUsernameTransferTo.setText(mUsernameTransferTo);

        mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
        mBtnConfirm.setOnClickListener(mBtnConfirmListener);
    }

    private View.OnClickListener mBtnConfirmListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(ConfirmationActivity.this, EnterPinActivity.class);
            intent.putExtra("Username", mUsername);
            intent.putExtra("UsernameTransferTo", mUsernameTransferTo);
            intent.putExtra("TransferToFrom", "transfered to");
            intent.putExtra("FaceGraphic", mFaceGraphicScreenNum);
            startActivity(intent);
        }
    };
}
