package com.example.ticket_sale.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.activity.LoginActivity;
import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.RegisterActivity;
import com.example.ticket_sale.adapter.SettingAdapter;
import com.example.ticket_sale.model.SettingItem;
import com.example.ticket_sale.model.User;
import com.example.ticket_sale.util.JwtUtil;
import com.example.ticket_sale.util.ThemeManager;
import com.example.ticket_sale.util.TokenManager;
import com.example.ticket_sale.util.UserUtil;
import com.example.ticket_sale.mapper.UserMapper;
import com.example.ticket_sale.viewmodel.ProfileViewModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private Button btnRedirectRegister;
    private Button btnRedirectLogin;
    private Button btnLogout;
    private TextView txtUserName;
    private TextView txtUserEmail;
    private TextView txtUserPhone;
    private ProgressBar pbLoadUser;
    private View viewOverlay;
    private RecyclerView rcViewSettings;
    private RecyclerView rcViewContacts;
    private LinearLayout lnRequireUserLogin;
    private LinearLayout lnPersonalInfo;
    private SwitchCompat swDarkMode;

    private SettingAdapter settingAdapter;
    private SettingAdapter contactAdapter;

    private List<SettingItem> settingItems;
    private List<SettingItem> contactItems;
    private User user;
    private ProfileViewModel profileViewModel;
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
        View root =  inflater.inflate(R.layout.fragment_profile, container, false);
        init(root);
        initData();
        setDataForViews();
        return root;
    }

    private void setDataForViews(){
        contactAdapter = new SettingAdapter(contactItems, getContext());
        rcViewContacts.setAdapter(contactAdapter);

        if(!TokenManager.isLoggedIn()) return;
        settingAdapter = new SettingAdapter(settingItems, getContext());
        rcViewSettings.setAdapter(settingAdapter);
    }

    private void initData() {
        contactItems = initContactsItem();
        if(TokenManager.isLoggedIn()){
            getUserFromAPI();
            settingItems = initSettingsItem();
        }else{
            pbLoadUser.setVisibility(View.GONE);
            viewOverlay.setVisibility(View.GONE);
        }
    }

    private void init(View v){
        txtUserName = v.findViewById(R.id.txtUserFullName);
        txtUserEmail = v.findViewById(R.id.txtUserEmail);
        txtUserPhone = v.findViewById(R.id.txtUserPhone);
        btnRedirectLogin = v.findViewById(R.id.btnRedirectLogin);
        btnRedirectRegister = v.findViewById(R.id.btnRedirectRegister);
        rcViewSettings = v.findViewById(R.id.rcViewSettings);
        rcViewContacts = v.findViewById(R.id.rcViewContacts);
        lnRequireUserLogin = v.findViewById(R.id.lnRequireUserLogin);
        lnPersonalInfo = v.findViewById(R.id.lnPersonalInfo);
        btnLogout = v.findViewById(R.id.btnLogout);
        pbLoadUser = v.findViewById(R.id.pbloadUser);
        viewOverlay = v.findViewById(R.id.viewOverlay);

        swDarkMode = v.findViewById(R.id.swDarkMode);
        swDarkMode.setChecked(ThemeManager.isDarkMode(requireContext()));
        swDarkMode.setOnCheckedChangeListener(this::changeThemeMode);

        rcViewSettings.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcViewContacts.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        lnPersonalInfo.setVisibility(View.GONE);

        btnRedirectLogin.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        btnRedirectRegister.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v1 -> logout());
    }

    private void changeThemeMode(CompoundButton buttonView, boolean isChecked) {
        ThemeManager.setDarkMode(requireContext(), isChecked);
    }

    private void getUserFromAPI() {
        if(profileViewModel == null) profileViewModel = new ProfileViewModel();
        JSONObject payload = JwtUtil.decodeJWT(TokenManager.getToken());
        String id ="";
        if (payload != null) {
            id = payload.optString("id");
            String name = payload.optString("name");
            String email = payload.optString("email");
            String phone = payload.optString("phone");
            Log.d("JWT", "Name: " + name + ", Email: " + email + ", Phone: " + phone);
        }
        profileViewModel.getUser(id).observe(getViewLifecycleOwner(), response ->{
            if(response == null){
                Log.e("getUserFromAPI","response is null");
                Toast.makeText(getContext(),"Không thể lấy dữ liệu tài khoản.",Toast.LENGTH_SHORT).show();
                user = getExampleUser();
            }else if(response.getStatusCode() == 200){
                user = UserMapper.toUser(response.getData());
                showUserInfo(user);
                lnRequireUserLogin.setVisibility(View.GONE);
                lnPersonalInfo.setVisibility(View.VISIBLE);
            }else if(response.getStatusCode() == 401){
                lnRequireUserLogin.setVisibility(View.VISIBLE);
                lnPersonalInfo.setVisibility(View.GONE);
            }else {
                Log.e("getUserFromAPI", "Lỗi không xác định: " + response.getStatusCode());
                Toast.makeText(getContext(), "Đã xảy ra lỗi khi lấy thông tin người dùng.", Toast.LENGTH_SHORT).show();
            }
            showUserInfo(user);
            pbLoadUser.setVisibility(View.GONE);
            viewOverlay.setVisibility(View.GONE);

        });
    }

    private void logout(){
        if(profileViewModel == null) profileViewModel = new ProfileViewModel();
        profileViewModel.logout().observe(getViewLifecycleOwner(), response ->{
            if(response == null) {
                Log.e("logout","Urror logout");
                Toast.makeText(getContext(),"Có lỗi xẩy ra trong quá trình đăng xuất.",Toast.LENGTH_SHORT).show();
                return;
            }
            if (response.getStatusCode()!=200) {
                Log.e("Logout", "Đăng xuất thất bại: " + response.getMessage());
                Toast.makeText(getContext(), "Đăng xuất thất bại. ", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }




    private User getExampleUser() {
        return new User("US1","username1","Nguyễn Bình Quốc An","nbq***123@gmail.com","0938328288");
    }

    private List<SettingItem> initSettingsItem(){
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(SettingItem.TYPE_FRAGMENT,"Đổi mật khẩu",new ChangePasswordFragment(),R.drawable.ic_profile ));
        settingItems.add(new SettingItem(SettingItem.TYPE_FRAGMENT,"Chỉnh sửa thông tin cá nhân",new ProfileDetailFragment(),R.drawable.ic_profile ));
        settingItems.add(new SettingItem(SettingItem.TYPE_FRAGMENT,"Lịch sử mua hàng",new HistoryFragment(),R.drawable.ic_history ));
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


    private void showUserInfo(User user) {
        if(user == null) return;
        txtUserName.setText(user.getName());
        txtUserEmail.setText(String.format("Email: %s", UserUtil.maskEmail(user.getEmail())));
        txtUserPhone.setText(String.format("Số điện thoại: %s", UserUtil.maskPhone(user.getPhone())));
    }
}