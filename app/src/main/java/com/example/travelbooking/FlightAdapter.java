package com.example.travelbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlightAdapter extends
        RecyclerView.Adapter<FlightAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Flight> mFlights;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView m3lFrom;
        private TextView mFrom;
        private TextView m3lTo;
        private TextView mTo;
        private TextView mDate;
        private TextView mTime;
        private TextView mPrice;
        private TextView mNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m3lFrom = itemView.findViewById(R.id.from3Letters);
            mFrom = itemView.findViewById(R.id.fromFull);
            m3lTo = itemView.findViewById(R.id.to3Letters);
            mTo = itemView.findViewById(R.id.toFull);
            mDate = itemView.findViewById(R.id.dateText);
            mTime = itemView.findViewById(R.id.timeText);
            mPrice = itemView.findViewById(R.id.priceText);
            mNumber = itemView.findViewById(R.id.numberText);
        }

    }

    public FlightAdapter(Context mContext, ArrayList<Flight> mFlights) {
        this.mContext = mContext;
        this.mFlights = mFlights;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.flight_ticket, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flight flight = mFlights.get(position);
        holder.m3lFrom.setText(flight.getFlight3lFrom());
        holder.mFrom.setText(flight.getFlightFrom());
        holder.m3lTo.setText(flight.getFlight3lTo());
        holder.mTo.setText(flight.getFlightTo());
        holder.mDate.setText(flight.getFlightDate());
        holder.mTime.setText(flight.getFlightTime());
        holder.mPrice.setText(flight.getFlightPrice());
        holder.mNumber.setText(flight.getFlightID());
    }

    @Override
    public int getItemCount() {
        return mFlights.size();
    }


}
