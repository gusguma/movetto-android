package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movetto.R;

public class WalletDepositResultFragment extends Fragment {

    private View root;
    private Bundle bundle;
    private ImageView image;
    private TextView title;
    private TextView subtitle;
    private Button button;

    public WalletDepositResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout(inflater,container);
        setComponents();
        setListeners();
        return root;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_wallet_deposit_result
                , container, false);
    }

    private void setComponents(){
        bundle = getArguments();
        if (bundle != null){
            image = root.findViewById(R.id.wallet_deposit_result_img);
            image.setImageResource(bundle.getInt("image"));
            title = root.findViewById(R.id.wallet_deposit_result_title);
            title.setText(bundle.getString("title"));
            subtitle = root.findViewById(R.id.wallet_deposit_result_subtitle);
            subtitle.setText(bundle.getString("subtitle"));
        }
        button = root.findViewById(R.id.wallet_deposit_result_continue_button);
    }

    private void setListeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_wallet_deposit_result_to_nav_wallet);
            }
        });
    }
}
