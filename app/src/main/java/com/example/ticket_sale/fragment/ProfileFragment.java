package com.example.ticket_sale.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ticket_sale.activity.LoginActivity;
import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.RegisterActivity;
import com.example.ticket_sale.adapter.SettingAdapter;
import com.example.ticket_sale.model.SettingItem;
import com.example.ticket_sale.util.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    Button btnRedirectRegister;
    Button btnRedirectLogin;
    RecyclerView rcViewSettings;
    RecyclerView rcViewContacts;
    SettingAdapter settingAdapter;
    List<SettingItem> settingItems;
    List<SettingItem> contactItems;
    LinearLayout lnRequireUserLogin;
    LinearLayout lnPersonalInfo;
    Button btnLogout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        init(v);
        return v;
    }

    void init(View v){
        btnRedirectLogin = v.findViewById(R.id.btnRedirectLogin);
        btnRedirectRegister = v.findViewById(R.id.btnRedirectRegister);
        rcViewSettings = v.findViewById(R.id.rcViewSettings);
        rcViewContacts = v.findViewById(R.id.rcViewContacts);
        settingItems = initSettingsItem();
        contactItems = initContactsItem();
        settingAdapter = new SettingAdapter(settingItems, getContext());
        lnRequireUserLogin = v.findViewById(R.id.lnRequireUserLogin);
        lnPersonalInfo = v.findViewById(R.id.lnPersonalInfo);
        btnLogout = v.findViewById(R.id.btnLogout);
        if(TokenManager.isLoggedIn()){
            lnRequireUserLogin.setVisibility(View.VISIBLE);
            lnPersonalInfo.setVisibility(View.GONE);
        }else{
            lnRequireUserLogin.setVisibility(View.GONE);
            lnPersonalInfo.setVisibility(View.VISIBLE);
        }

        btnRedirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRedirectRegister.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rcViewSettings.setAdapter(settingAdapter);
        rcViewSettings.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        rcViewContacts.setAdapter(new SettingAdapter(contactItems, getContext()));
        rcViewContacts.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private List<SettingItem> initSettingsItem(){
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(SettingItem.TYPE_FRAGMENT,"Hồ sơ cá nhân","DetailProfileFragment",R.drawable.ic_profile ));
        settingItems.add(new SettingItem(SettingItem.TYPE_FRAGMENT,"Lịch sử mua hàng","HistoryFragment",R.drawable.ic_history ));
        return settingItems;
    }
    private List<SettingItem> initContactsItem(){
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(SettingItem.TYPE_PHONE,"Liên hệ qua số điện thoại: 0123456789","0123456789",R.drawable.ic_call));
        settingItems.add(new SettingItem(SettingItem.TYPE_EMAIL, "Liên hệ qua email: testmail@gmail.com","testmail@gmail.com",R.drawable.ic_email));
        settingItems.add(new SettingItem(SettingItem.TYPE_WEB, "Thông tin về chúng tôi","https://www.galaxycine.vn/ve-chung-toi/",R.drawable.ic_store));
        settingItems.add(new SettingItem(SettingItem.TYPE_WEBVIEW, "Điều khoản sử dụng","https://vnexpress.net/",R.drawable.ic_article));
        settingItems.add(new SettingItem(SettingItem.TYPE_WEBVIEW, "Chính sách bảo mật","https://vnexpress.net/",R.drawable.ic_encrypted));
        settingItems.add(new SettingItem(SettingItem.TYPE_WEBVIEW, "Chính sách thanh toán","https://vnexpress.net/",R.drawable.ic_card));
        settingItems.add(new SettingItem(SettingItem.TYPE_WEBVIEW, "Câu hỏi thường gặp","https://vnexpress.net/",R.drawable.ic_help));
        return settingItems;
    }
}