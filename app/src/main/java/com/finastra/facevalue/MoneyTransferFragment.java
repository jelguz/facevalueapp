package com.finastra.facevalue;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.finastra.facevalue.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyTransferFragment extends Fragment {

    private ImageButton mBtnMoneyTransferCamera;
    private Spinner mSpinnerTransferFrom;

    private static final String[] paths = {"0987654321"};

    public MoneyTransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_transfer, container, false);

        mBtnMoneyTransferCamera = (ImageButton) view.findViewById(R.id.btnMoneyTransferCamera);
        mBtnMoneyTransferCamera.setOnClickListener(mBtnMoneyTransferCameraListener);

        mSpinnerTransferFrom = (Spinner)view.findViewById(R.id.spinnerTransferFrom);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTransferFrom.setAdapter(adapter);


        return view;
    }

    private View.OnClickListener mBtnMoneyTransferCameraListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), FaceActivity.class);
            intent.putExtra("FaceGraphic", 2);
            startActivity(intent);
        }
    };

}
