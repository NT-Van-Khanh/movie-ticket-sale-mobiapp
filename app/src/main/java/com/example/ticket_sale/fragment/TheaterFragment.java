package com.example.ticket_sale.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.TheaterAdapter;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.mapper.TheaterMapper;
import com.example.ticket_sale.viewmodel.TheaterViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TheaterFragment extends Fragment {
    private Button btnFindNearlyTheater;
    private Button btnChooseProvince;
    private RecyclerView rcViewMovieTheaters;
    private TheaterAdapter theaterAdapter;

    private ProgressBar pbLoadTheaters;
    private View viewOverlay;
    private List<Theater> movieTheaters;
    private TheaterViewModel theaterViewModel;
    private FusedLocationProviderClient fusedLocationClient;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TheaterFragment() {
    }


    public static TheaterFragment newInstance(String param1, String param2) {
        TheaterFragment fragment = new TheaterFragment();
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
        View root = inflater.inflate(R.layout.fragment_theater,container,false);
        init(root);
        initData();
        return root;
    }

    private void initData() {
        showLoadingUI();
        theaterViewModel = new TheaterViewModel();
        getMovieTheatersFromAPI();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
    }

    private void getMovieTheatersFromAPI() {
        theaterViewModel.getTheaters().observe(getViewLifecycleOwner(), resultData ->{
            if(resultData == null || resultData.getStatusCode() != 200){
                Log.e("MovieAPI", "Failed to get theaters: " + resultData);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu các rạp.", Toast.LENGTH_SHORT).show();
                movieTheaters = getExampleMovieTheaters();
            }else{
                movieTheaters = resultData.getData().stream().map(TheaterMapper::toTheater).collect(Collectors.toList());
            }
            theaterAdapter.setTheaters(movieTheaters);
            hideLoadingUI();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    void init(View root){
        rcViewMovieTheaters = root.findViewById(R.id.rcViewMovieTheaters);
        btnChooseProvince = root.findViewById(R.id.btnChooseProvince);
        rcViewMovieTheaters.setLayoutManager(new GridLayoutManager(getContext(), 1));
        pbLoadTheaters  = root.findViewById(R.id.pbLoadTheaters);
        viewOverlay = root.findViewById(R.id.viewOverlay);
        btnFindNearlyTheater = root.findViewById(R.id.btnFindNearlyTheater);

        initBtnChooseProvince();
        initBtnFindNearlyTheater();
        movieTheaters = new ArrayList<>();
        theaterAdapter = new TheaterAdapter(movieTheaters, this::onItemClick);
        rcViewMovieTheaters.setAdapter(theaterAdapter);
    }

    private void initBtnFindNearlyTheater() {
        btnFindNearlyTheater.setOnClickListener(v -> {
            showLoadingUI();
            navigationToTheaterLocation();
//            showNearestLocation();
            hideLoadingUI();
        });
    }

    private void showNearestLocation() {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            Theater nearest = findNearestTheater(location.getLatitude(), location.getLongitude());
                            if (nearest != null) {
                                new AlertDialog.Builder(requireContext())
                                        .setTitle("Rạp gần bạn nhất")
                                        .setMessage("Bạn có muốn chọn rạp \"" + nearest.getName() + "\" không?")
                                        .setPositiveButton("Có", (dialog, which) -> {
                                            navigationToTheaterShowtime(nearest);
                                        })
                                        .setNegativeButton("Không", (dialog, which) -> {
                                            dialog.dismiss();
                                        })
                                        .show();
                                Toast.makeText(requireContext(), "Rạp gần nhất: " + nearest.getName(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
    }

    private void navigationToTheaterLocation() {
        TheaterMapFragment theaterLocationFragment = new TheaterMapFragment();
        Bundle bundle  = new Bundle();
        bundle.putParcelableArrayList("theaters",new ArrayList<>(movieTheaters));
        theaterLocationFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,theaterLocationFragment)
                .addToBackStack(null)
                .commit();
    }

    private Theater findNearestTheater(double userLat, double userLng) {
        double minDistance = Double.MAX_VALUE;
        Theater nearest = null;

        for (Theater theater : movieTheaters) {
            LatLng theaterLatLng = getLocationFromAddress(theater.getAddress());
            if (theaterLatLng == null) continue;
            float[] results = new float[1];
            Location.distanceBetween(userLat, userLng,
                                        theaterLatLng.latitude,
                                        theaterLatLng.longitude,
                                        results);
            if (results[0] < minDistance) {
                minDistance = results[0];
                nearest = theater;
            }
        }

        return nearest;
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(requireContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.isEmpty()) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            Log.e("getLocationFromAddress",String.valueOf(ex));
        }

        return p1;
    }

    private void initBtnChooseProvince(){
        String[] provinces = getResources().getStringArray(R.array.provinces);
        btnChooseProvince.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Chọn Tỉnh thành")
                    .setItems(provinces,(dialog, which) -> {
                        String selectedProvince = provinces[which];
                        btnChooseProvince.setText(selectedProvince);
                        filterTheatersByProvince(selectedProvince);
                    }).show();
        });
    }

    private void filterTheatersByProvince(String province) {
        if(province.equals("Tất cả tỉnh thành")){
            theaterAdapter.setTheaters(movieTheaters);
        }else{
            List<Theater> filteredTheater = new ArrayList<>();
            for (Theater theater : movieTheaters) {
                if (theater.getAddress().contains(province)) {
                        filteredTheater.add(theater);
                }
            }
            theaterAdapter.setTheaters(filteredTheater);
        }
    }

    private void onItemClick(int position) {
        Theater movieTheater = movieTheaters.get(position);
        navigationToTheaterShowtime(movieTheater);
    }

    private void navigationToTheaterShowtime(Theater movieTheater) {
        TheaterShowtimeFragment movieShowtimeFragment = new TheaterShowtimeFragment();
        Bundle bundle  = new Bundle();
        bundle.putParcelable("theater",movieTheater);
        movieShowtimeFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,movieShowtimeFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showLoadingUI(){
        pbLoadTheaters.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadTheaters.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }

    private List<Theater> getExampleMovieTheaters(){
        Theater mt1 = new Theater("MT1","Rạp Linh Trung, Thủ Đức","Rạp Linh Trung, Hồ Chí Minh",R.drawable.cinema1);
        Theater mt2 = new Theater("MT2","Rạp Thảo Điền, Thủ Đức","Xuân Thủy, Thảo Điền, Thủ Đức, Hồ Chí Minh",R.drawable.cinema2);
        Theater mt3 = new Theater("MT3","Rạp Bến Tre","Nguyễn Huệ, Phường 4, Bến Tre",R.drawable.cinema3);
        Theater mt4 = new Theater("MT4","Rạp Mỹ Tho","Lý Thường Kiệt, Phường 5, Mỹ Tho, Tiền Giang",R.drawable.cinema4);
        Theater mt5 = new Theater("MT5","Rạp Bến Nghé","Đinh Tiên Hoàng, Bến Nghé, Quận 1, Hồ Chí Minh",R.drawable.cinema5);
        Theater mt6 = new Theater("MT6","Rạp Trương Định","Trương Định, Phường Võ Thị Sáu, Quận 3, Hồ Chí Minh",R.drawable.cinema6);
        return Arrays.asList(mt1,mt2,mt3,mt4,mt5,mt6);
    }
}