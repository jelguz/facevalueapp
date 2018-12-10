package com.finastra.facevalue;

import android.content.Intent;
import android.media.FaceDetector;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EnterAmountActivity extends AppCompatActivity {

    private Button mOkPurchaseAmountButton;
    private EditText mPurchaseAmount;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_amount);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOkPurchaseAmountButton = (Button) findViewById(R.id.btnOkPurchaseAmount);
        mPurchaseAmount = (EditText) findViewById(R.id.etPurchaseAmount);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutEnterAmount);

        mOkPurchaseAmountButton.setOnClickListener(mOkPurchaseAmountButtonListener);
    }
    private View.OnClickListener mOkPurchaseAmountButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(isEmpty(mPurchaseAmount)) {
                Snackbar snackbar = Snackbar.make(mLinearLayout, "Please enter purchase amount", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                Intent intent = new Intent(EnterAmountActivity.this, FaceActivity.class);
                intent.putExtra("FaceGraphic", FaceActivity.TRANSFER_FROM_GRAPHIC_SCREEN);
                startActivity(intent);
            }
        }
    };

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private boolean isEmpty(EditText et){
        return et.getText().toString().trim().length()==0;
    }
}

