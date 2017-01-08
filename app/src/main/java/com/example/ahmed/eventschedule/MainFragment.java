package com.example.ahmed.eventschedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.eventschedule.DataBase.ControlRealm;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    static ControlRealm controlRealm;
    static ArrayList<Event> upEvents, doneEvents, allEvents;
    static EventAdapter eventAdapter;
    TextView emptyListText;
    boolean isEmpty;

    public MainFragment() {
    }

    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        emptyListText = (TextView) rootView.findViewById(R.id.empty_list_text);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.events_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        controlRealm = new ControlRealm(getActivity());
        allEvents = controlRealm.getAllEvents();
        upEvents = new ArrayList<>();
        doneEvents = new ArrayList<>();
        for (Event e : allEvents) {
            if (e.isDone())
                doneEvents.add(e);
            else
                upEvents.add(e);
        }
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 0:
                eventAdapter = new EventAdapter(getActivity(), upEvents);
                break;
            case 1:
                eventAdapter = new EventAdapter(getActivity(), doneEvents);
                break;
        }

        recyclerView.setAdapter(eventAdapter);
        setEmptyListText();
        return rootView;
    }

    private void setEmptyListText() {
        if (eventAdapter.isEmpty()) {
            emptyListText.setText("      No Events Available \n Please Add Some Events");
            isEmpty = true;
        } else {
            emptyListText.setText("");
            isEmpty = false;
        }
    }
}
