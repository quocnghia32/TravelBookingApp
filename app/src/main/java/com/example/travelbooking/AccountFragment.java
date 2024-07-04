package com.example.travelbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        TextView information = view.findViewById(R.id.infor);
        TextView payment = view.findViewById(R.id.payment);
        TextView history = view.findViewById(R.id.history);
        TextView setting = view.findViewById(R.id.settings);
        TextView saved = view.findViewById(R.id.saved);

        information.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new UserInformationFragment()).addToBackStack(null).commit();
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

        Button end = view.findViewById(R.id.end_button);
        end.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), WelcomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            ((MainActivity) getActivity()).finish();
        });

    }
}