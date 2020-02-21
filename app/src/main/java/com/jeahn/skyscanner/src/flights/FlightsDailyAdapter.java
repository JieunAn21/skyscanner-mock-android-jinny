package com.jeahn.skyscanner.src.flights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jeahn.skyscanner.R;
import com.jeahn.skyscanner.src.flights.models.AirLine;
import com.jeahn.skyscanner.src.flights.models.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlightsDailyAdapter extends RecyclerView.Adapter<FlightsDailyAdapter.ViewHolder>{
    private List<AirLine> mAirLineList;

    private int mItemCount;

    public FlightsDailyAdapter(List<AirLine> mAirLineList) {
        this.mAirLineList = mAirLineList;
        mItemCount = mAirLineList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flights_daily, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AirLine airLine = mAirLineList.get(position);
        Glide.with(holder.ivAirLine.getContext()).load(airLine.getAirLineImgUrl()).into(holder.ivAirLine);
        holder.tvAirLine.setText(airLine.getAirLineKr());
        holder.tvPrice.setText(airLine.getMinPrice());
        String times = "";
        for (Ticket ticket : airLine.getTicketList()) {
            try {
                SimpleDateFormat stringToTimeFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat amPmFormat = new SimpleDateFormat("a h:mm", Locale.KOREA);
                Date deTime = stringToTimeFormat.parse(ticket.getDeTime());
                String deTimeAmPm = amPmFormat.format(deTime);

                times += deTimeAmPm + "\t\t";
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        holder.tvTimes.setText(times.trim());
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setItemCount(int itemCount){
        mItemCount = itemCount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvAirLine, tvPrice, tvTimes;
        public ImageView ivAirLine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAirLine = itemView.findViewById(R.id.daily_flights_tv_airline);
            tvPrice = itemView.findViewById(R.id.daily_flights_tv_price);
            tvTimes = itemView.findViewById(R.id.flights_daily_tv_times);
            ivAirLine = itemView.findViewById(R.id.daily_flights_iv_airline);
        }
    }
}
