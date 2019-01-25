package com.lab.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.myapplication.MainActivity;
import com.lab.myapplication.R;
import com.lab.myapplication.converter_entities.RetrieveCurrencyTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BasicTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BasicTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicTaskFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView tw;
    String[] names = {"USD", "RUB", "PHP"};
    MyResponseGetter responseGetter;

    private class MyResponseGetter implements RetrieveCurrencyTask.AsyncResponse {
        @Override
        public void processFinish(String output) {
            tw.setText(output);
        }
    }



    private OnFragmentInteractionListener mListener;

    public BasicTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicTaskFragment newInstance(String param1, String param2) {
        BasicTaskFragment fragment = new BasicTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        responseGetter = new MyResponseGetter();
        // адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        final Spinner spinnerFrom = (Spinner) findViewById(R.id.spinner_from);
//        final Spinner spinnerTo = (Spinner) findViewById(R.id.spinner_to);
//        spinnerFrom.setAdapter(adapter);
//        spinnerTo.setAdapter(adapter);
//        // заголовок
//        spinnerFrom.setPrompt("Из");
//        spinnerTo.setPrompt("в");
//        // устанавливаем обработчик нажатия
//        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                // показываем позиция нажатого элемента
//                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//
//        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                // показываем позиция нажатого элемента
//                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//
//
//        final Button button = findViewById(R.id.button);
//        tw = findViewById(R.id.my_text_view);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                //setContentView(R.layout.fragment_additional_task);
//                final RetrieveCurrencyTask currencyTask = new RetrieveCurrencyTask(responseGetter);
//                currencyTask.execute(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString());
//                //tw.setText();
//            }
//        });
//
//
//

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_task, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
}
