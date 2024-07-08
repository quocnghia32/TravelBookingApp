package com.example.travelbooking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    MyDatabaseHelper db = new MyDatabaseHelper(getContext());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();

        ImageView accountImage = view.findViewById(R.id.account_image_Account);
        TextView accountName = view.findViewById(R.id.account_name_Account);
        TextView information = view.findViewById(R.id.personal_info_Account);
        TextView payment = view.findViewById(R.id.payment_Account);
        TextView history = view.findViewById(R.id.history_Account);
        TextView setting = view.findViewById(R.id.settings_Account);
        TextView saved = view.findViewById(R.id.save_Account);

        User user = ((MainActivity) getActivity()).user;

        // Set the image and name of the account
        byte[] image = user.getImage();
        if (image != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            accountImage.setImageBitmap(bitmap);
        }
        accountName.setText(user.getFirstName() + " " + user.getLastName());


        // Set the onClickListener for each TextView
        information.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Main, new UserInformationFragment()).addToBackStack(null).commit();
        });
        payment.setOnClickListener(v -> {
            Toast.makeText(getContext(), "This function will be developed", Toast.LENGTH_SHORT).show();
        });
        history.setOnClickListener(v -> {
            Toast.makeText(getContext(), "This function will be developed", Toast.LENGTH_SHORT).show();
        });
        setting.setOnClickListener(v -> {
            Toast.makeText(getContext(), "This function will be developed", Toast.LENGTH_SHORT).show();
        });
        saved.setOnClickListener(v -> {
            Toast.makeText(getContext(), "This function will be developed", Toast.LENGTH_SHORT).show();
        });

        // Set the onClickListener for the end button
        Button end = view.findViewById(R.id.end_button_Account);
        end.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), WelcomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            ((MainActivity) getActivity()).finish();
        });

    }
}