package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.MainActivity;
import com.example.ticket_sale.mapper.SeatMapper;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.SeatType;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.Ticket;
import com.example.ticket_sale.util.AuthGuard;
import com.example.ticket_sale.mapper.ScreenMapper;
import com.example.ticket_sale.util.ViLocaleUtil;
import com.example.ticket_sale.viewmodel.ChooseSeatViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChooseSeatFragment extends Fragment {
    private TextView txtTheaterName;
    private TextView txtScreen;
    private TextView txtTimeFrame;
    private TextView txtMovieTitle;
    private TextView txtSeatsSelected;
    private TextView txtTotalCost;
    private Button btnNext;
    private LinearLayout lnlSeatRowName;
    private GridLayout gdlSeats;
    private TextView txtGoBack;
    private ProgressBar pbLoadSeats;
    private View viewOverlay;


    private Screen screen;
    private List<Seat> seatClasses;
    private List<SeatPosition> selectedSeats;
    private Order order;
    private ChooseSeatViewModel chooseSeatViewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseSeatFragment() {
        // Required empty public constructor
    }

    public static ChooseSeatFragment newInstance(String param1, String param2) {
        ChooseSeatFragment fragment = new ChooseSeatFragment();
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
        View root = inflater.inflate(R.layout.fragment_choose_seat, container, false);
        initViews(root);
        initData();
//        setDataForViews();
        return root;
    }

    private void initData(){
        showLoadingUI();
        if(getArguments() == null) return;
        order = getArguments().getParcelable("order");
        if(order == null) return;

        chooseSeatViewModel  = new ChooseSeatViewModel();
        getScreenFromAPI();
//        getSeatClassesFromAPI();
    }

    private void getScreenFromAPI(){
        chooseSeatViewModel.getScreen(order.getShowtime().getScreenId()).observe(getViewLifecycleOwner(), response ->{
                if(response!=null && response.getStatusCode() == 200){
                    this.screen = ScreenMapper.toScreen(response.getData());
                    getSoldSeatFromAPI();
                }else{
                    Toast.makeText(getContext(),"Không thể lấy thông tin rạp.",Toast.LENGTH_SHORT).show();
                    screen = getExampleScreen();
                    Integer[][] seatPositions = screen.getSeatPositions();
                    if(seatPositions != null){
                        initSeatRowName(seatPositions.length);
                        initSeatViews(seatPositions, null);
                    }
                    setDataForViews();
                    hideLoadingUI();
                }
            });
    }

//    private void getSeatClassesFromAPI(){
//        chooseSeatViewModel.getSeatClasses().observe(getViewLifecycleOwner(), response ->{
//            if(response!=null && response.getStatusCode() == 200) {
//                seatClasses = response.getData()
//                        .stream().map(SeatMapper::toSeat)
//                        .collect(Collectors.toList());
//                getScreenFromAPI();
//            }else{
//                Toast.makeText(getContext(),"Không thể lấy hạng ghế.",Toast.LENGTH_SHORT).show();
//                hideLoadingUI();
//            }
//        });
//    }

    private void getSoldSeatFromAPI(){
        chooseSeatViewModel.getSoleSeats(this.order.getShowtime().getScreenId()).observe(getViewLifecycleOwner(),response ->{
            if(response!=null && response.getStatusCode() == 200){
                List<SeatPosition> soldSeats = response.getData().stream()
                        .map(SeatMapper::toSeatPosition)
                        .collect(Collectors.toList());
                Integer[][] seatPositions = screen.getSeatPositions();
                if(seatPositions != null){
                    initSeatRowName(seatPositions.length);
                    initSeatViews(seatPositions, soldSeats);
                }
            }
            setDataForViews();
            hideLoadingUI();
        });
    }

    private void setDataForViews() {
        if(screen == null) return;
        txtScreen.setText(screen.getName());

        Theater theater = screen.getTheater();
        if(theater == null) return;
        txtTheaterName.setText(theater.getName());

        Showtime showtime = order.getShowtime();
        if(showtime == null) return;
        txtTimeFrame.setText( String.format("Khung giờ: %s - %s",showtime.getTimeStart(), showtime.getTimeEnd()));

        Movie movie = order.getMovie();
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());

        if(order.getMovieFormat() == null) return;
        showSelectedSeats();
//        initSeatRowName(screen.getSeats().length);
//        initSeatViews(screen.getSeats());
    }

    private void initViews(View root){
        txtTheaterName = root.findViewById(R.id.txtTheaterName);
        txtScreen = root.findViewById(R.id.txtScreen);
        txtTimeFrame = root.findViewById(R.id.txtTimeFrame);
        txtMovieTitle = root.findViewById(R.id.txtMovieName);
        txtSeatsSelected = root.findViewById(R.id.txtSeatSelected);
        txtTotalCost = root.findViewById(R.id.txtTotalCost);
        lnlSeatRowName = root.findViewById(R.id.lnlSeatRowName);
        gdlSeats = root.findViewById(R.id.gdlSeats);
        pbLoadSeats = root.findViewById(R.id.pbLoadSeats);
        viewOverlay = root.findViewById(R.id.viewOverlay);
        selectedSeats = new ArrayList<>();
        btnNext = root.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            btnNextListener();
        });

        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v1 -> getParentFragmentManager().popBackStack());
        lnlSeatRowName.removeAllViews();
        gdlSeats.removeAllViews();
    }

    private void showSelectedSeats() {
        if(selectedSeats == null) {
            txtSeatsSelected.setText("");
            return;
        }
        String stringSeats = selectedSeats.stream()
                .map(seat -> " "+ seat.getTitle()).collect(Collectors.joining());
        txtSeatsSelected.setText(stringSeats);
    }

    private Screen getExampleScreen() {
        Screen s = new Screen("SC2","Phòng 2", new Theater("TH1","Rạp", "address", R.drawable.cinema2));
        Integer[][] seats = getSeats(10, 14);
        s.setSeatPositions(seats);
        return s;
    }


    private Integer[][] getSeats(int row, int column){
        Integer[][] seats = new Integer[row][column];
        for (int seatRow = 0; seatRow < seats.length; seatRow++) {
            for (int seatColumn = 0; seatColumn < seats[seatRow].length; seatColumn++) {
                seats[seatRow][seatColumn] = 1;
//                seats[seatRow][seatColumn] = new Seat("C1",
//                        String.format(ViLocaleUtil.localeVN,"%s%d",(char)('A'+ seatRow), seatColumn),
//                        "", 1, SeatType.STANDARD, 60000L);
            }
        }
        return seats;
    }
    private void initSeatRowName(int numberOfRows){
        lnlSeatRowName.removeAllViews();
        for( char rowCharacter = 'A'; rowCharacter < 'A' + numberOfRows; ++rowCharacter ){
            TextView txtRow = new TextView(getContext());
            txtRow.setText(String.valueOf(rowCharacter));
            txtRow.setLayoutParams( new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, dpToPx(24)
            ));
            txtRow.setTextAlignment( View.TEXT_ALIGNMENT_CENTER);
            lnlSeatRowName.addView(txtRow);
        }

    }

//    private ImageView initImgSeatView(Seat seat) {
//        ImageView imgSeatIcon = new ImageView(getContext());
//        imgSeatIcon.setImageResource(R.drawable.ic_chair);
//        imgSeatIcon.setColorFilter(seat.getSeatType().getTypeOfSeat(getContext()));
//        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//        param.width = dpToPx(24);
//        param.height = dpToPx(24);
//        param.rowSpec = GridLayout.spec(seat.getRow());
//        param.columnSpec = GridLayout.spec(seat.getColumn());
//        imgSeatIcon.setLayoutParams(param);
//        imgSeatIcon.setTag(seat);
//        return imgSeatIcon;
//    }

    private ImageView initSeatImageIcon(SeatPosition seatPosition) {
        if(seatPosition == null) return null;
        ImageView imgSeatIcon = new ImageView(getContext());
        imgSeatIcon.setImageResource(R.drawable.ic_chair);
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.width = dpToPx(24);
        param.height = dpToPx(24);
        param.rowSpec = GridLayout.spec(seatPosition.getRow());
        param.columnSpec = GridLayout.spec(seatPosition.getColumn());
        imgSeatIcon.setLayoutParams(param);
        imgSeatIcon.setTag(seatPosition);

        SeatType seatType = seatPosition.getSeatType();
        imgSeatIcon.setColorFilter(seatType.getTypeOfSeat(getContext()));
        if(seatType != SeatType.SOLD && seatType != SeatType.NONE){
            imgSeatIcon.setOnClickListener(v -> {
                SeatPosition clickedSeat = (SeatPosition) v.getTag();
                if (selectedSeats.contains(clickedSeat)) {
                    selectedSeats.remove(clickedSeat);
                    imgSeatIcon.setColorFilter(clickedSeat.getSeatType().getTypeOfSeat(getContext()));
                } else {
                    selectedSeats.add(clickedSeat);
                    imgSeatIcon.setColorFilter(SeatType.SELECTED.getTypeOfSeat(getContext()));
                }
                showSelectedSeats();
                showTotalCost();
            });
        }
        return imgSeatIcon;
    }

    private Space intSeatSpace(int row, int column){
        Space space = new Space(getContext());
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.width = dpToPx(24);
        param.height = dpToPx(24);
        param.rowSpec = GridLayout.spec(row);
        param.columnSpec = GridLayout.spec(column);
        space.setLayoutParams(param);
        return space;
    }

    private void initSeatViews(Integer[][] seatPositions, List<SeatPosition> soldSeats){
        if(soldSeats != null){
            for(SeatPosition position : soldSeats){
                seatPositions[position.getRow()][position.getColumn()] = -1;
            }
        }
        for(int row = 0; row <  seatPositions.length; ++ row) {
            for(int column = 0; column <  seatPositions[row].length; ++ column) {
                Integer seatTypeId =  seatPositions[row][column];
                if(seatTypeId == null) continue;
                if(seatTypeId == 0){
                    Space space = intSeatSpace(row, column);
                    gdlSeats.addView(space);
                    continue;
                }
                SeatPosition seatPosition = new SeatPosition(row, column, String.valueOf(seatTypeId));
                ImageView imgSeatIcon = initSeatImageIcon(seatPosition);
                gdlSeats.addView(imgSeatIcon);
            }
        }
    }

    private void  btnNextListener(){
        if(selectedSeats == null || selectedSeats.size()<1){
            Toast.makeText(getContext(),"Vui lòng Chọn vị trí ghế ngồi!",Toast.LENGTH_LONG).show();
            return;
        }
        if(AuthGuard.checkLogin(requireActivity())) {
            ChooseFoodFragment chooseFoodFragment = new ChooseFoodFragment();
            Bundle b = new Bundle();
            order.setScreen(screen);
            order.setSeats(selectedSeats);
            b.putParcelable("order", order);
            chooseFoodFragment.setArguments(b);
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //hieu ung chuyen fragment
                    .add(R.id.fragment_container, chooseFoodFragment)
                    .hide(Objects.requireNonNull(getParentFragmentManager().findFragmentById(R.id.fragment_container)))
                    .addToBackStack(null)
                    .commit();
        }
    }

//    private void initSeatViews(Seat[][] seats){
//        for(int row = 0; row < seats.length; ++ row) {
//            for(int column = 0; column < seats[row].length; ++ column) {
//                Seat seat = seats[row][column];
//                if(seat == null) continue;
//                ImageView imgSeatView = initImgSeatView(seat);
//                imgSeatView.setOnClickListener(v -> {
//                    Seat clickedSeat = (Seat) v.getTag();
//                    if (selectedSeats.contains(clickedSeat)) {
//                        selectedSeats.remove(clickedSeat);
//                        imgSeatView.setColorFilter(clickedSeat.getSeatType().getTypeOfSeat(getContext()));
//                    } else {
//                        selectedSeats.add(clickedSeat);
//                        imgSeatView.setColorFilter(SeatType.SELECTED.getTypeOfSeat(getContext()));
//                    }
//                    showSelectedSeats();
//                    showTotalCost();
//                });
//                gdlSeats.addView(imgSeatView);
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((MainActivity) requireActivity()).hideBottomNav();
//    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void showTotalCost(){
        if(order == null || selectedSeats == null) return;
//        Long totalCost = 0L;
//        for(Seat s : selectedSeats){
//            totalCost += s.getPrice();
//        }
        Long totalCost = selectedSeats.size() * order.getSelectedTicket().getPrice();
        txtTotalCost.setText(ViLocaleUtil.formatLocalCurrency(totalCost));
    }

    private void showLoadingUI(){
        pbLoadSeats.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadSeats.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }

}

//    private void initSeatViews(int[][] seatMatrix, List<Seat> seats){
//        for(int row =0; row < seatMatrix.length; ++row){
//            for(int column = 0; column < seatMatrix[row].length; ++ column){
//                int seatId = seatMatrix[row][column];
//                Seat seat = findSeatById(String.valueOf(seatId), seats);
//                if (seat == null) continue;
//                SeatPosition seatPosition = new SeatPosition(row, column, SeatType.fromInt(seatId));
//                ImageView imgSeatIcon = initImgSeatView(seatPosition);
//                imgSeatIcon.setOnClickListener(v -> {
//                    SeatPosition clickedSeat = (SeatPosition) v.getTag();
//                    if (selectedSeats.contains(clickedSeat)) {
//                        selectedSeats.remove(clickedSeat);
//                        imgSeatIcon.setColorFilter(clickedSeat.getSeatType().getTypeOfSeat(getContext()));
//                    } else {
//                        selectedSeats.add(clickedSeat);
//                        imgSeatIcon.setColorFilter(SeatType.SELECTED.getTypeOfSeat(getContext()));
//                    }
//                    showSelectedSeats();
//                    showTotalCost();
//                });
//
//                gdlSeats.addView(imgSeatIcon);
//            }
//        }
//    }
//    private Seat findSeatById(String id, List<Seat> allSeats) {
//        for (Seat seat : allSeats) {
//            if (seat.getId().equals(id)) {
//                return seat;
//            }
//        }
//        return null;
//    }


//    private ColorStateList getTypeOfSeat(SeatTypeDTO seat){
//        if(seat == null) return null;
//        SeatType seatType = seat.getSeatType();
//        if(seatType == null) return null;
//        return seatType.getTypeOfSeat(getContext());
//    }


//    private void initSeats(List<List<SeatTypeDTO>> seats){
//        for(int row = 0; row < seats.size(); ++row) {
//            List<SeatTypeDTO> seatRow = seats.get(row);
//            for(int column = 0; column < seatRow.size(); ++column) {
//                SeatTypeDTO seat = seatRow.get(column);
//                ImageView seatView = new ImageView(getContext());
//                seatView.setImageResource(R.drawable.ic_chair);
//                seatView.setTag(seat);
//
//                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//                param.rowSpec = GridLayout.spec(row);     // row là số nguyên
//                param.columnSpec = GridLayout.spec(column); // column cũng là số nguyên
//                param.width = dpToPx(24);
//                param.height = dpToPx(24);
//                seatView.setLayoutParams(param);
//
//
//                seatView.setOnClickListener(v -> {
//                    SeatTypeDTO clickedSeat = (SeatTypeDTO) v.getTag();
//                    if (selectedSeats.contains(clickedSeat)) {
//                        selectedSeats.remove(clickedSeat);
//                        seatView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_standard)));
//                    } else {
//                        selectedSeats.add(clickedSeat);
//                        seatView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_standard))); // Đổi màu/ảnh ghế
//                    }
//                });
//
//                gdlSeats.addView(seatView);
//            }
//        }
//    }