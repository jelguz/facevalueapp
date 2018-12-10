package com.finastra.facevalue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EnterPinActivity extends AppCompatActivity {

    private int mFaceGraphicScreenNum;
    private String mUsername;
    private String mUsernameTransferTo;
    private String mTransferToFrom;

    private Button mBtnEnterPin;
    private EditText mEtPin;
    private TextView mTvEnterPinName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFaceGraphicScreenNum = getIntent().getIntExtra("FaceGraphic", 0);
        mUsername = getIntent().getStringExtra("Username");
        mUsernameTransferTo = getIntent().getStringExtra("UsernameTransferTo");
        mTransferToFrom = getIntent().getStringExtra("TransferToFrom");

        mTvEnterPinName = (TextView) findViewById(R.id.tvEnterPinName);
        mTvEnterPinName.setText(mUsername);

        mBtnEnterPin = (Button) findViewById(R.id.btnEnterPin);
        mBtnEnterPin.setOnClickListener(mBtnEnterPinListener);
    }

    private View.OnClickListener mBtnEnterPinListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(mFaceGraphicScreenNum==FaceActivity.FIRST_SCREEN_FACE_GRAPHIC_SCREEN) {
                launchFaceActivity();
            } else if(mFaceGraphicScreenNum==FaceActivity.TRANSFER_TO_GRAPHIC_SCREEN) {
                launchTransactionResultActivity();
            } else if(mFaceGraphicScreenNum==FaceActivity.TRANSFER_FROM_GRAPHIC_SCREEN) {
                launchTransactionResultActivity_transferFrom();
            }
        }
    };

    private void launchFaceActivity() {
        Intent intent = new Intent(EnterPinActivity.this, FaceActivity.class);
        intent.putExtra("FaceGraphic", 1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void launchTransactionResultActivity() {
        Intent intent = new Intent(EnterPinActivity.this, TransactionResultActivity.class);
        intent.putExtra("UsernameTransferTo", mUsernameTransferTo);
        intent.putExtra("TransferToFrom", mTransferToFrom);
        intent.putExtra("FaceGraphic", FaceActivity.TRANSFER_TO_GRAPHIC_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void launchTransactionResultActivity_transferFrom() {
        Intent intent = new Intent(EnterPinActivity.this, TransactionResultActivity.class);
        intent.putExtra("UsernameTransferTo", mUsernameTransferTo);
        intent.putExtra("TransferToFrom", mTransferToFrom);
        intent.putExtra("FaceGraphic", FaceActivity.TRANSFER_FROM_GRAPHIC_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
