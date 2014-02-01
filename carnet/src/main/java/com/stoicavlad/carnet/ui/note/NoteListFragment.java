package com.stoicavlad.carnet.ui.note;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.squareup.otto.Subscribe;
import com.stoicavlad.carnet.CarnetApp;
import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.api.MateriiDatabase;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.otto.BusProvider;
import com.stoicavlad.carnet.data.otto.DataSetChangedEvent;

import javax.inject.Inject;

public class NoteListFragment extends Fragment implements AbsListView.OnItemClickListener,
        ComplexNoteAdapter.ComplexNoteAdapterInteractionListener {

    @Inject
    MateriiDatabase materiiDatabase;

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ComplexNoteAdapter mAdapter;

    public NoteListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarnetApp.get(getActivity()).inject(this);
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notefragment, container, false);
        setHasOptionsMenu(true);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.list);
        setAdapter();
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.showNotaDetailFragment(materiiDatabase.getMaterii()[position]);
        }
    }

    @Subscribe
    public void onDataSetChanged(DataSetChangedEvent event) {
        if (event.tag == DataSetChangedEvent.TAG_MATERIE) {
            Materie[] matrii = materiiDatabase.getMaterii();
            if (matrii != null) {

            }
        }
    }

    private void setAdapter() {
        mAdapter = new ComplexNoteAdapter(getActivity(), materiiDatabase.getMaterii());
        mAdapter.setmListener(this);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
    }

    @Override
    public void onDeleteMaterie(Materie materie) {
        if (materiiDatabase.deleteMaterie(materie)) {
            BusProvider.getInstance()
                    .post(new DataSetChangedEvent(DataSetChangedEvent.TAG_MATERIE));
        }
    }

    @Override
    public void onRenameMaterie(Materie materie) {

    }

    public interface OnFragmentInteractionListener {
        public void showNotaDetailFragment(Materie materie);
    }
}
