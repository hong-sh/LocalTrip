package com.localtrip.jejulocaltrip.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.Reservation;

public class ReservationDetailFragment extends DialogFragment implements View.OnClickListener{

    private String reservationName ="";
    private String reservationEmail="";
    private String reservationPhone="";
    private String reservationDate="";
    private String reservationPerson="";
    private String reservationPrice="";
    private String reservationRequest="";

    private TextView textViewReservationName, textViewReservationEmail, textViewReservationPhone, textViewReservationDate, textViewReservationPerson, textViewReservationPrice, textViewReservationRequest;
    private Button buttonClose;


    public ReservationDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReservationDetailFragment newInstance(Reservation reservation) {
        ReservationDetailFragment fragment = new ReservationDetailFragment();
        Bundle args = new Bundle();
        args.putString("reservation_name", reservation.getUserName());
        args.putString("reservation_email", reservation.getUserEmail());
        args.putString("reservation_phone", reservation.getReservationPhone());
        args.putString("reservation_date", reservation.getReservationDate());
        args.putString("reservation_person", String.valueOf(reservation.getReservationPersonNumber()));
        args.putString("reservation_price", String.valueOf(reservation.getReservationPrice()));
        args.putString("reservation_request", reservation.getReservationRequest());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservationName = getArguments().getString("reservation_name");
            reservationEmail = getArguments().getString("reservation_email");
            reservationPhone = getArguments().getString("reservation_phone");
            reservationDate = getArguments().getString("reservation_date");
            reservationPerson = getArguments().getString("reservation_person");
            reservationPrice = getArguments().getString("reservation_price");
            reservationRequest = getArguments().getString("reservation_request");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reservation_detail, container, false);
        textViewReservationName = (TextView)rootView.findViewById(R.id.textView_reservationName);
        textViewReservationEmail = (TextView)rootView.findViewById(R.id.textView_reservationEmail);
        textViewReservationPhone = (TextView)rootView.findViewById(R.id.textView_reservationPhone);
        textViewReservationDate = (TextView)rootView.findViewById(R.id.textView_reservationDate);
        textViewReservationPerson = (TextView)rootView.findViewById(R.id.textView_reservationQuantity);
        textViewReservationPrice = (TextView)rootView.findViewById(R.id.textView_reservationPrice);
        textViewReservationRequest = (TextView)rootView.findViewById(R.id.textView_reservationRequest);

        textViewReservationName.setText(reservationName);
        textViewReservationEmail.setText(reservationEmail);
        textViewReservationPhone.setText(reservationPhone);
        textViewReservationDate.setText(reservationDate);
        textViewReservationPerson.setText(reservationPerson);
        textViewReservationPrice.setText(reservationPrice);
        textViewReservationRequest.setText(reservationRequest);


        buttonClose = (Button)rootView.findViewById(R.id.button_close);
        buttonClose.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
