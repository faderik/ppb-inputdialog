package com.erik.inputdialog;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KontakListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class KontakListFragment extends Fragment {
    KontakAdapter kontakAdapter;
    ArrayList<Kontak> kontakList;
    private KontakDbHelper openDB;
    public ListView lvData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KontakListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KontakListFragment newInstance(String param1, String param2) {
        KontakListFragment fragment = new KontakListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public KontakListFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kontak_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        openDB = new KontakDbHelper(getContext());

        kontakList = new ArrayList<>();
        kontakList = openDB.getAllItems();

        lvData = getView().findViewById(R.id.lvData);

        kontakAdapter = new KontakAdapter(getContext(), R.layout.item_user, kontakList);

        lvData.setAdapter(kontakAdapter);
    }

    public void addKontak(Kontak kontak) {
        kontakAdapter.add(kontak);
    }

    public void updateKontak(Kontak kontak, String name, String number) {
//        kontakAdapter.remove(kontak);
//        Kontak kontakHolder = new Kontak(name, number);
//        kontakAdapter.add(kontakHolder);

        openDB.updateKontak(kontak, name, number);
        loadKontak();
    }

    public void deleteKontak(Kontak kontak) {
        kontakAdapter.remove(kontak);
    }

    public void searchKontak(String name){
        kontakList = openDB.getItemsByName(name);
        kontakAdapter.clear();
        kontakAdapter.addAll(kontakList);
    }

    public void loadKontak(){
        kontakList = openDB.getAllItems();
        kontakAdapter.clear();
        kontakAdapter.addAll(kontakList);

    }
}