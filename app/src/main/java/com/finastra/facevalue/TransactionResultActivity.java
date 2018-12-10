package com.finastra.facevalue;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TransactionResultActivity extends AppCompatActivity {

    private Button mFinishButton;
    private TextView mTvTransferToUsername;
    private TextView mTvTransferedToFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_result);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        String transferToUsername = intent.getStringExtra("UsernameTransferTo");
        String transferedToFrom = intent.getStringExtra("TransferToFrom");

        mTvTransferToUsername = (TextView) findViewById(R.id.tvTransferToUsername);
        mTvTransferToUsername.setText(transferToUsername);

        mTvTransferedToFrom = (TextView) findViewById(R.id.tvTransferedToFrom);
        mTvTransferedToFrom.setText(transferedToFrom);

        mFinishButton = (Button) findViewById(R.id.btnFinish);
        mFinishButton.setOnClickListener(mFinishButtonListener);
    }

    private View.OnClickListener mFinishButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(TransactionResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    };

}
