package com.example.travelbooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
            m3lFrom = itemView.findViewById(R.id.from3Letters_Ticket);
            mFrom = itemView.findViewById(R.id.fromFull_Ticket);
            m3lTo = itemView.findViewById(R.id.to3Letters_Ticket);
            mTo = itemView.findViewById(R.id.toFull_Ticket);
            mDate = itemView.findViewById(R.id.dateText_Ticket);
            mTime = itemView.findViewById(R.id.timeText_Ticket);
            mPrice = itemView.findViewById(R.id.priceText_Ticket);
            mNumber = itemView.findViewById(R.id.numberText_Ticket);
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
        View flightView = inflater.inflate(R.layout.flight_ticket, parent, false);
        ViewHolder viewHolder = new ViewHolder(flightView);
        flightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Flight Selected is " + viewHolder.mNumber.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, SelectSeatActivity.class);
                intent.putExtra("flightID", viewHolder.mNumber.getText());
                intent.putExtra("flightNumber", viewHolder.mNumber.getText());
                intent.putExtra("from3Letters", viewHolder.m3lFrom.getText());
                intent.putExtra("from", viewHolder.mFrom.getText());
                intent.putExtra("to3Letters", viewHolder.m3lTo.getText());
                intent.putExtra("to", viewHolder.mTo.getText());
                intent.putExtra("date", viewHolder.mDate.getText());
                intent.putExtra("time", viewHolder.mTime.getText());
                intent.putExtra("price", viewHolder.mPrice.getText());
                String classType = ((TransportFlightsActivity) mContext).getIntent().getStringExtra("class");
                intent.putExtra("class",classType);
                intent.putExtra("numAdults", ((TransportFlightsActivity) mContext).getIntent().getIntExtra("numAdults", 1));
                mContext.startActivity(intent);
            }
        });
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
