package com.example.ahmed.eventschedule;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.eventschedule.Adapters.EventAdapter;
import com.example.ahmed.eventschedule.DataBase.ControlRealm;

import java.util.ArrayList;

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {
    private static final String PAGE_NUMBER = "section_number";
    public static ControlRealm controlRealm;
    int page;
    public static ArrayList<Event> upEvents, doneEvents;
    ArrayList<Event> allEvents;
    EventAdapter eventAdapter;
    TextView emptyListText;
    boolean isEmpty;
    RecyclerView recyclerView;
    SearchView searchView;

    public MainFragment() {
    }

    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        page = getArguments().getInt(PAGE_NUMBER);
        emptyListText = (TextView) rootView.findViewById(R.id.empty_list_text);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.events_list);
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

        eventAdapter = new EventAdapter(getActivity());
        switch (page) {
            case 0:
                eventAdapter.changeData(upEvents);
                break;
            case 1:
                eventAdapter.changeData(doneEvents);
                break;
        }

        recyclerView.setAdapter(eventAdapter);
        setEmptyListText();
        return rootView;
    }

    public void setEmptyListText() {
        if (eventAdapter.isEmpty()) {
            emptyListText.setText("      No Events Available \n Please Add Some Events");
            isEmpty = true;
        } else {
            emptyListText.setText("");
            isEmpty = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    searchView.onActionViewCollapsed();
                    controlRealm.clearEvents(page);
                    eventAdapter.removeAll();
                    setEmptyListText();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = alert.create();
            if (page == 0)
                dialog.setTitle("Are you sure you want to clear all Upcoming events ?!");
            else if (page == 1)
                dialog.setTitle("Are you sure you want to clear all Done events ?!");
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.trim().toLowerCase();
        if (page == 0)
            eventAdapter.setFilter(newText, upEvents);
        else if (page == 1)
            eventAdapter.setFilter(newText, doneEvents);
        return true;
    }
}
