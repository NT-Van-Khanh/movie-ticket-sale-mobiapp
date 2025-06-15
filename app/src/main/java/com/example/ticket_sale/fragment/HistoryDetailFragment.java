package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.MainActivity;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Showtime;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryDetailFragment extends Fragment {
    private TextView txtGoBack;
    private TextView txtMovieTitle;
    private TextView txtMovieShowtime;
    private TextView txtMovieDate;
    private TextView txtTheaterAddress;
    private TextView txtTheaterScreen;
    private TextView txtTheaterSeats;
    private TextView txtFoods;
    private ImageView imgBookingQRCode;

    private Order order;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryDetailFragment newInstance(String param1, String param2) {
        HistoryDetailFragment fragment = new HistoryDetailFragment();
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
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).hideBottomNav();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) requireActivity()).showBottomNav();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history_detail, container, false);
        initViews(root);
        initData();
        setDataForViews();
        return root;
    }

    private void setDataForViews() {
        txtMovieTitle.setText(order.getMovie().getTitle());
        Showtime showtime = order.getShowtime();
        txtMovieShowtime.setText(String.format("%s  - %s ",showtime.getTimeStart(), showtime.getTimeEnd()));
        txtMovieDate.setText(showtime.getDate());
        Screen screen = order.getScreen();
        txtTheaterAddress.setText(screen.getTheater().getName());
        txtTheaterScreen.setText(screen.getName());
        txtTheaterSeats.setText(order.getSelectedSeatsString());
        txtFoods.setText(order.getSeletecFoodsString());
        Glide.with(requireContext())
                .load(order.getImageQRCodeLink())
                .placeholder(R.drawable.ic_qr_code)
                .error(R.drawable.ic_qr_code)
                .into(imgBookingQRCode);
    }

    private void initData() {
        if(getArguments()!=null){
            order = getArguments().getParcelable("order");
        }
    }

    private void initViews(View root) {
        imgBookingQRCode = root.findViewById(R.id.imgBookingQRCode);
        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtMovieTitle = root.findViewById(R.id.txtMovieTitle);
        txtMovieShowtime = root.findViewById(R.id.txtMovieShowtime);
        txtMovieDate = root.findViewById(R.id.txtMovieDate);
        txtTheaterScreen = root.findViewById(R.id.txtTheaterScreen);
        txtTheaterAddress = root.findViewById(R.id.txtTheaterAddress);
        txtTheaterSeats = root.findViewById(R.id.txtTheaterSeats);
        txtFoods = root.findViewById(R.id.txtFoods);

        txtGoBack.setOnClickListener( v ->{
            getParentFragmentManager().popBackStack();
        });
    }
}