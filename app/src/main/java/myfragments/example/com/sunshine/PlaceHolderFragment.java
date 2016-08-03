package myfragments.example.com.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

/**
 * Created by mpudota on 8/2/16.
 */
public class PlaceHolderFragment extends Fragment {

    public PlaceHolderFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = layoutInflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;

    }
}
