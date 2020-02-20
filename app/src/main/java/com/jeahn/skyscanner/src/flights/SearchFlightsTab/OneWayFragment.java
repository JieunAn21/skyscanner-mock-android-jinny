package com.jeahn.skyscanner.src.flights.SearchFlightsTab;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jeahn.skyscanner.R;
import com.jeahn.skyscanner.src.flights.SearchFlightsActivity;
import com.jeahn.skyscanner.src.flights.models.City;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneWayFragment extends Fragment implements View.OnClickListener {
    private static int START_SEARCH_FLIGHTS_ONE_WAY = 100;

    private SearchFlightsActivity mActivity;
    private City mOriginCity, mDestinationCity;
    private int mCabinClass = 0;

    private FloatingActionButton mFabSearch;
    private TextView mTvOrigin, mTvDestination, mTvCabinClass;
    private LinearLayout mLinearSeat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (SearchFlightsActivity)getActivity();

        View view = inflater.inflate(R.layout.fragment_one_way, container, false);

        mFabSearch = view.findViewById(R.id.one_way_floating_search);
        mTvOrigin = view.findViewById(R.id.one_way_tv_origin);
        mTvDestination = view.findViewById(R.id.one_way_tv_destination);
        mTvCabinClass = view.findViewById(R.id.one_way_tv_cabin_class);
        mLinearSeat = view.findViewById(R.id.one_way_seat_setting);

        mFabSearch.setOnClickListener(this);
        mTvOrigin.setOnClickListener(this);
        mTvDestination.setOnClickListener(this);
        mLinearSeat.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.one_way_floating_search: //검색 시작
                if(checkInputData()){
                    Intent intent = new Intent();
                    intent.putExtra("deAirPortCode", mOriginCity.getAirPortCode());
                    intent.putExtra("arAirPortCode", mDestinationCity.getAirPortCode());
                    intent.putExtra("cabinClass", mCabinClass);
                    getActivity().setResult(START_SEARCH_FLIGHTS_ONE_WAY, intent);
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
                }else{
                    Toast.makeText(getContext(), "검색 조건을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.one_way_tv_origin: //출발지 검색
                showCityDialog(true, mOriginCity);
                break;
            case R.id.one_way_tv_destination: //도착지 검색
                showCityDialog(false, mDestinationCity);
                break;
            case R.id.one_way_seat_setting: //인원 및 좌석 등급 선택
                SeatDialog seatDialog = new SeatDialog(getContext());
                seatDialog.showDialog(mCabinClass, mTvCabinClass);
                seatDialog.setDialogListener(new SeatDialog.SeatDialogListener() {
                    @Override
                    public void onApplyButtonClick(int cabinClass) {
                        mCabinClass = cabinClass;
                    }
                });
                break;
        }
    }

    //검색 조건 유효성 체크
    private boolean checkInputData() {
        if(mTvOrigin.getText().toString().matches("")
            || mTvDestination.getText().toString().matches("")){
            return false;
        }
        return true;
    }

    public void showCityDialog(boolean isOrigin, City curCity){
        CityDialog dialog = new CityDialog(isOrigin, curCity);
        dialog.show(mActivity.getSupportFragmentManager(), "TAG");
        mActivity.getSupportFragmentManager().executePendingTransactions();
        dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mActivity.setNavigationIconVisibility(true);
            }
        });
        dialog.setDialogListener(new CityDialog.CityDialogListener() {
            @Override
            public void onItemSelected(City city) {
                if (isOrigin) {
                    mOriginCity = city;
                    mTvOrigin.setText(city.getCityNameKr() + " (" + city.getAirPortCode() + ")");
                }else{
                    mDestinationCity = city;
                    mTvDestination.setText(city.getCityNameKr() + " (" + city.getAirPortCode() + ")");
                }
            }
        });

        mActivity.setNavigationIconVisibility(false);
    }
}
