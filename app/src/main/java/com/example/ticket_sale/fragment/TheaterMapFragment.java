package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import android.Manifest;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Theater;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheaterMapFragment extends Fragment {
    private GoogleMap mMap;
    private ProgressBar pgbLoadTheaterAddress;
    private View viewOverlay;
    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapTheater;
    private final Map<Marker, Theater> markerTheaterMap = new HashMap<>();
    private List<Theater> theaters;
    private double userLat;
    private double userLng;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View txtGoBack;

    public TheaterMapFragment() {
        // Required empty public constructor
    }

    public static TheaterMapFragment newInstance(String param1, String param2) {
        TheaterMapFragment fragment = new TheaterMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        mapTheater.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapTheater.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapTheater.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapTheater.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapTheater.onSaveInstanceState(outState);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initTheaterLocation(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_theater_map, container, false);
        initViews(root);
        initData();
        initTheaterLocation(savedInstanceState);
        return root;

    }

    private void initTheaterLocation(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mapTheater.onCreate(savedInstanceState);
        mapTheater.getMapAsync(googleMap -> {
            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            userLat = location.getLatitude();
                            userLng = location.getLongitude();
                            Log.e("latLng",String.format("%f - %f",userLat,userLng));
                            LatLng userLocation = new LatLng(userLat, userLng);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                            if (theaters != null && !theaters.isEmpty()) {
                                displayTheaters(theaters);
                            }
                        }
                    });
        });
    }

    private void initData() {
        if (getArguments() !=null){
            theaters = getArguments().getParcelableArrayList("theaters");

        }
    }

    private void initViews(View root) {
        this.txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v-> getParentFragmentManager().popBackStack());
        mapTheater = root.findViewById(R.id.mapTheater);
        pgbLoadTheaterAddress = root.findViewById(R.id.pbLoadTheaterAddress);
        viewOverlay = root.findViewById(R.id.viewOverlay);
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

    private void displayTheaters(List<Theater> theaters) {
        Theater nearest = null;
        float minDistance = Float.MAX_VALUE;

        for (Theater theater : theaters) {
            LatLng latLng = getLocationFromAddress(theater.getAddress());
            if (latLng == null) continue;
            Log.e("latLng",String.format("%f - %f",latLng.latitude,latLng.longitude));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(theater.getName())
                    .snippet(theater.getAddress()));

            markerTheaterMap.put(marker, theater);

            float[] result = new float[1];
            Location.distanceBetween(userLat, userLng, latLng.latitude, latLng.longitude, result);
            if (result[0] < minDistance) {
                minDistance = result[0];
                nearest = theater;
            }
        }

        if (nearest != null) {
            LatLng nearestLatLng = getLocationFromAddress(nearest.getAddress());
            assert nearestLatLng != null;
            mMap.addMarker(new MarkerOptions()
                    .position(nearestLatLng)
                    .title("🎯 Rạp gần bạn nhất: " + nearest.getName())
                    .snippet(nearest.getAddress())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestLatLng, 15));
        }

        mMap.setOnMarkerClickListener(marker -> {
            Theater selectedTheater = markerTheaterMap.get(marker); // lấy Theater từ marker

            if (selectedTheater != null) {
                navigationToTheaterShowtime(selectedTheater);
            }
            return true; // trả về true để chặn Google Maps xử lý mặc định
        });
    }
    private void navigationToTheaterShowtime(Theater movieTheater) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Rạp gần bạn nhất")
                .setMessage("Bạn có muốn chọn rạp \"" + movieTheater.getName() + "\" không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    TheaterShowtimeFragment movieShowtimeFragment = new TheaterShowtimeFragment();
                    Bundle bundle  = new Bundle();
                    bundle.putParcelable("theater",movieTheater);
                    movieShowtimeFragment.setArguments(bundle);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,movieShowtimeFragment)
                            .addToBackStack(null)
                            .commit();
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();

    }
}