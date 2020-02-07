package group13.ntphat.evernote.ui.share;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import group13.ntphat.evernote.MainActivity;
import group13.ntphat.evernote.Model.DATA;
import group13.ntphat.evernote.Model.LocationUpdateListener;
import group13.ntphat.evernote.Model.NOTE;
import group13.ntphat.evernote.Model.USER;
import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.notes.ViewNoteActivity;

public class ShareFragment extends Fragment {

    private ListView listView;
    private ArrayList<NOTE> listSharedNotes;
    private static SharedNoteAdapter sharedNoteAdapter;
    private ImageButton syncBtn;
    private static TextView address;
    private static boolean isNewAddress;
    private boolean isClickSyncBtn;
    private int NOTE_ACTIVITY_RESULT = 1;
    private View root;
    private static Double currentLat;
    private static Double currentLng;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_share, container, false);
        initComponent();

        return root;
    }

    //----------------------------------------------------------------------
    // update current device's location
    public static void updateLocation(Double lat, Double lng){
        currentLat = lat;
        currentLng = lng;
    }

    //----------------------------------------------------------------------
    // Load list of local shared notes
//    private void loadListNotes(){
//        //listSharedNotes = DATA.getNoteByGPS(getContext(), USER.getInstance().getUserID(), currentLng, currentLat);
//        listSharedNotes = USER.getInstance().getNotesByGPS();
//    }

    //----------------------------------------------------------------------
    // update listview and reload fragment
    private void updateListSharedNotes(){
        sharedNoteAdapter.notifyDataSetChanged();
        MainActivity.loadLastFragment();
    }

    // ------------------------------------------------------------
    // Update address textview after LocationListener catch new location
    public static void updateAddressTextview(String newAddress){
        address.setText(newAddress);
        isNewAddress = true;
    }

    // ------------------------------------------------------------
    // load list shared notes
    private void loadListSharedNotes(){
        USER.getInstance().updateNoteByGPS(getContext(),
                USER.getInstance().getCurrentLong(),
                USER.getInstance().getCurrentLat());

        listSharedNotes = USER.getInstance().getNotesByGPS();
    }

    // ------------------------------------------------------------
    // Get address given lat, long
    private String getAddress(Double lat, Double lng){
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(lat, lng, 1);
            if (addressList.size() > 0){
                String address = addressList.get(0).getAddressLine(0);
                return address;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Sorry, we can't locate you";
    }

    // ------------------------------------------------------------
    private void initComponent(){
        syncBtn = root.findViewById(R.id.btn_sync_shared_notes);
        address = root.findViewById(R.id.share_address_tv);
        address.setText(getAddress(USER.getInstance().getCurrentLat(), USER.getInstance().getCurrentLong()));

        isNewAddress = false;
        //String currentAddress = gpsTracker.getAddress();
        //address.setText(gpsTracker.getAddress());
        syncBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if (isNewAddress){
                    //loadListNotes();
                    address.setText(getAddress(USER.getInstance().getCurrentLat(), USER.getInstance().getCurrentLong()));
                    //loadListSharedNotes();
                    updateListSharedNotes();
                    //ArrayList<NOTE> listNotes = USER.getInstance().getNotesByGPS();
                    isNewAddress = false;
                //}
            }
        });

        // init list of shared notes
        listView = root.findViewById(R.id.list_shared_notes);
        //loadListSharedNotes();
//        sharedNoteAdapter = new SharedNoteAdapter(getContext(),
//                R.layout.list_shared_note_item,
//                listSharedNotes);
        sharedNoteAdapter = new SharedNoteAdapter(getContext(),
                R.layout.list_shared_note_item,
                USER.getInstance().getNotesByGPS());

        listView.setAdapter(sharedNoteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(getContext(), ViewNoteActivity.class);
                NOTE chosenNote = listSharedNotes.get(position);
                // make note become ViewOnly: haven't coded it yet
            }
        });
    }

}