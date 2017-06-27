package com.localtrip.jejulocaltrip.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.localtrip.jejulocaltrip.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchMapFragment extends Fragment implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;
    private Button buttonJeju, buttonWudo, buttonSugypo;

    public SearchMapFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchMapFragment newInstance() {
        SearchMapFragment fragment = new SearchMapFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_map, container, false);
        buttonWudo = (Button)rootView.findViewById(R.id.button_wudo);
        buttonSugypo = (Button)rootView.findViewById(R.id.button_sugyupo);
        buttonJeju = (Button)rootView.findViewById(R.id.button_jeju);

        buttonWudo.setOnClickListener(this);
        buttonSugypo.setOnClickListener(this);
        buttonJeju.setOnClickListener(this);

        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String tagString) {
        Log.d("MyTag", "enter button press");
        if (mListener != null) {
            Log.d("MyTag", "enter button press1");
            mListener.onFragmentInteraction(tagString);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Button pressedButton = (Button)v;
        onButtonPressed(pressedButton.getText().toString());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tagString);
    }
}
