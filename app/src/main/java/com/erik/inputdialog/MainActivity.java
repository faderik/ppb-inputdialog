package com.erik.inputdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private KontakDbHelper openDB;
    private ArrayList<Kontak> kontakList;
    private KontakListFragment kontakListFragment;
    private Kontak activeKontak = null;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB = new KontakDbHelper(this);

        kontakList = new ArrayList<>();
        kontakList = openDB.getAllItems();

        etSearch = findViewById(R.id.etSearch);
        ImageView btnAdd = findViewById(R.id.btnAdd);
        ImageView btnDelete = findViewById(R.id.btnDelete);
        ImageView btnSearch = findViewById(R.id.btnSearch);
        ImageView btnEdit = findViewById(R.id.btnEdit);
        ImageView btnCall = findViewById(R.id.btnCall);

        btnAdd.setOnClickListener(op);
        btnDelete.setOnClickListener(op);
        btnSearch.setOnClickListener(op);
        btnEdit.setOnClickListener(op);
        btnCall.setOnClickListener(op);

        kontakListFragment = (KontakListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentKontakList);

        kontakListFragment.lvData.setOnItemClickListener((parent, view, position, id) -> {
            activeKontak = (Kontak) parent.getItemAtPosition(position);
            Toast.makeText(this, "Anda memilih " + activeKontak.getName(), Toast.LENGTH_LONG).show();

        });
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    showAddDialog();
                    break;
                case R.id.btnDelete:
                    showDeleteDialog();
                    break;
                case R.id.btnSearch:
                    searchKontak();
                    break;
                case R.id.btnCall:
                    callKontak();
                    break;
                case R.id.btnEdit:
                    showEditDialog();
                    break;
            }
        }
    };

    private void searchKontak(){
        kontakListFragment.searchKontak(etSearch.getText().toString());
    }

    private void updateKontak(String name, String number){
        kontakListFragment.updateKontak(activeKontak, name, number);

    }

    private void callKontak(){
        if(activeKontak != null) {
            Uri number = Uri.parse("tel:" + activeKontak.getNumber());
            Intent callNumber = new Intent(Intent.ACTION_DIAL, number);

            startActivity(callNumber);
        }
    }

    private void addKontak(String name, String number) {
        Kontak kontak = new Kontak(name, number);
        long id = openDB.addKontak(kontak);
        kontakListFragment.addKontak(kontak);
    }

    private void deleteKontak(Kontak kontak) {
        openDB.deleteKontak(kontak.getName());
        kontakListFragment.deleteKontak(kontak);
    }

    private void showAddDialog() {
        AlertDialog.Builder dlAddKontak = new AlertDialog.Builder(this);
        dlAddKontak.setTitle("Add Kontak");
        View vAdd = LayoutInflater.from(this).inflate(R.layout.add_kontak, null);

        final EditText nm = (EditText) vAdd.findViewById(R.id.etName);
        final EditText hp = (EditText) vAdd.findViewById(R.id.etNumber);

        dlAddKontak.setView(vAdd);
        dlAddKontak.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addKontak(nm.getText().toString(), hp.getText().toString());

                        Toast.makeText(getBaseContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                    }
                });

        dlAddKontak.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        dlAddKontak.show();

    }

    private void showEditDialog() {
        AlertDialog.Builder dlEditKontak = new AlertDialog.Builder(this);
        dlEditKontak.setTitle("Edit Kontak");
        View vEdit = LayoutInflater.from(this).inflate(R.layout.add_kontak, null);

        final EditText nm = (EditText) vEdit.findViewById(R.id.etName);
        nm.setText(activeKontak.getName());
//        nm.setEnabled(false);
        final EditText hp = (EditText) vEdit.findViewById(R.id.etNumber);
        hp.setText(activeKontak.getNumber());

        dlEditKontak.setView(vEdit);
        dlEditKontak.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateKontak(nm.getText().toString(), hp.getText().toString());

                        Toast.makeText(getBaseContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                    }
                });

        dlEditKontak.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        dlEditKontak.show();

    }

    private void showDeleteDialog() {
        AlertDialog.Builder dlDeleteKontak = new AlertDialog.Builder(this);
        dlDeleteKontak.setTitle("Delete Kontak");
        View vDelete = LayoutInflater.from(this).inflate(R.layout.delete_kontak, null);

        final TextView tvDelete = vDelete.findViewById(R.id.tvDelete);
        tvDelete.setText("Are you sure to delete " + activeKontak.getName() + " ?");

        dlDeleteKontak.setView(vDelete);
        dlDeleteKontak.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(activeKontak != null) {
                            deleteKontak(activeKontak);
                            Toast.makeText(getBaseContext(), "Data Terhapus", Toast.LENGTH_LONG).show();
                            activeKontak = null;
                        }

                        dialog.dismiss();
                    }
                });

        dlDeleteKontak.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        dlDeleteKontak.show();

    }

}