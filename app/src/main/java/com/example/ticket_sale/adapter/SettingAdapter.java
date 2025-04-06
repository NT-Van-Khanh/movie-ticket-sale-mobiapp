package com.example.ticket_sale.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.WebViewActivity;
import com.example.ticket_sale.model.SettingItem;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {
    private Context context;
    List<SettingItem> settings;

    public SettingAdapter(List<SettingItem> settings,Context context) {
        this.settings = settings;
        this.context = context;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting, parent, false);
        return  new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        SettingItem item = settings.get(position);
        holder.txtSettingName.setText(item.getTitle());
        holder.imgSettingIcon.setImageResource(item.getIconResId());

        holder.itemView.setOnClickListener(v -> {
            switch (item.getType()){
                case SettingItem.TYPE_FRAGMENT:
                    openFragment(item.getData());
                    break;
                case SettingItem.TYPE_ACTIVITY:
                    openActivity(item.getData());
                    break;
                case SettingItem.TYPE_PHONE:
                    openCallPhone(item.getData());
                    break;
                case SettingItem.TYPE_EMAIL:
                    openEmail(item.getData());
                    break;
                case SettingItem.TYPE_WEB:
                    openBrowser(item.getData());
                    break;
                case SettingItem.TYPE_WEBVIEW:
                    openWebView(item.getData());
                    break;
            }
        });
    }


    @Override
    public int getItemCount() {
        return settings.size();
    }


    public class SettingViewHolder extends RecyclerView.ViewHolder {
        TextView txtSettingName;
        ImageView imgSettingIcon;
        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSettingIcon = itemView.findViewById(R.id.imgSettingIcon);
            txtSettingName = itemView.findViewById(R.id.txtSettingName);
        }
    }


    private void openActivity(String activityName) {
        try {
            Class<?> activityClass = Class.forName(activityName);
            Intent intent = new Intent(context, activityClass);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openFragment(String fragmentName) {//Nên chuyển sang switch case, tránh dùng Reflection cải thiện hiệu suất
        try{
            Class<?> fragmentClass = Class.forName(fragmentName);

            if(Fragment.class.isAssignableFrom(fragmentClass)){
                Fragment fragment = (Fragment) fragmentClass.getDeclaredConstructor().newInstance();
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }else{
                Log.e("openFragment", "Class này không là một Fragment: " + fragmentName);
            }
        }catch  (Exception e) {
            e.printStackTrace();
            Log.e("openFragment", "Không thể khởi tạo Fragment từ tên Class: " + fragmentName);
        }
    }


    private void openCallPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    private void openEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Phản hồi dịch vụ");
        context.startActivity(intent);
    }

    private void openBrowser(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    private void openWebView(String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("URL", url);
        context.startActivity(intent);
    }
}
