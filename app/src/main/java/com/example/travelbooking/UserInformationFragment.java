package com.example.travelbooking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserInformationFragment extends Fragment {

    MyDatabaseHelper DB;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri imageUri;
    EditText firstName, lastName, phone, email;
    Button saveButton;
    ImageView profilePicture;
    ImageButton backButton, addImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            imageUri = data.getData();
                            profilePicture.setImageURI(imageUri);
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);

        firstName = view.findViewById(R.id.firstname_Infor);
        lastName = view.findViewById(R.id.lastname_Infor);
        phone = view.findViewById(R.id.phone_Infor);
        email = view.findViewById(R.id.email_Infor);
        saveButton = view.findViewById(R.id.save_button_Infor);
        profilePicture = view.findViewById(R.id.profile_pic_Infor);
        backButton = view.findViewById(R.id.back_button_Infor);
        addImage = view.findViewById(R.id.add_image_Infor);
        DB = new MyDatabaseHelper(getActivity());

        firstName.setText(((MainActivity) getActivity()).user.getFirstName());
        lastName.setText(((MainActivity) getActivity()).user.getLastName());
        phone.setText(((MainActivity) getActivity()).user.getPhone());
        email.setText(((MainActivity) getActivity()).user.getEmail());

        byte[] img = ((MainActivity) getActivity()).user.getImage();
        if (img != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0,img.length);
            profilePicture.setImageBitmap(bitmap);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        backButton.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
        });

        addImage.setOnClickListener(v -> {
            ImagePicker.with(this).cropSquare().compress(600).maxResultSize(600,600)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });
        saveButton.setOnClickListener(v -> {
            //Save the user information
            String fName = firstName.getText().toString();
            String lName = lastName.getText().toString();
            String ph = phone.getText().toString();
            String em = email.getText().toString();

            Bitmap curBitmap = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            curBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageInByte = stream.toByteArray();

            ((MainActivity) getActivity()).user.setFirstName(fName);
            ((MainActivity) getActivity()).user.setLastName(lName);
            ((MainActivity) getActivity()).user.setPhone(ph);
            ((MainActivity) getActivity()).user.setEmail(em);
            ((MainActivity) getActivity()).user.setImage(imageInByte);

            ((MainActivity) getActivity()).db.updateData(fName, lName, ph, em, ((MainActivity) getActivity()).username,imageInByte);
            ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
        });

    }

}