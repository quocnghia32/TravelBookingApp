package com.example.travelbooking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInformationFragment newInstance(String param1, String param2) {
        UserInformationFragment fragment = new UserInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MyDatabaseHelper DB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_information, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageButton backButton = getView().findViewById(R.id.back_button_account);
        backButton.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
        });

        EditText firstName = getView().findViewById(R.id.firstname);
        EditText lastName = getView().findViewById(R.id.lastname);
        EditText phone = getView().findViewById(R.id.phone);
        EditText email = getView().findViewById(R.id.email);
        Button saveButton = getView().findViewById(R.id.save_button);
        ImageView profilePicture = getView().findViewById(R.id.profile_pic);
        DB = new MyDatabaseHelper(getActivity());

        firstName.setText(((MainActivity) getActivity()).user.getFirstName());
        lastName.setText(((MainActivity) getActivity()).user.getLastName());
        phone.setText(((MainActivity) getActivity()).user.getPhone());
        email.setText(((MainActivity) getActivity()).user.getEmail());

        byte[] img = ((MainActivity) getActivity()).user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0,img.length);
        profilePicture.setImageBitmap(bitmap);


        saveButton.setOnClickListener(v -> {
            //Save the user information
            String fName = firstName.getText().toString();
            String lname = lastName.getText().toString();
            String ph = phone.getText().toString();
            String em = email.getText().toString();
            ((MainActivity) getActivity()).user.setFirstName(fName);
            ((MainActivity) getActivity()).user.setLastName(lname);
            ((MainActivity) getActivity()).user.setPhone(ph);
            ((MainActivity) getActivity()).user.setEmail(em);
            ((MainActivity) getActivity()).db.updateData(fName, lname, ph, em, ((MainActivity) getActivity()).username);
        });
    }
}