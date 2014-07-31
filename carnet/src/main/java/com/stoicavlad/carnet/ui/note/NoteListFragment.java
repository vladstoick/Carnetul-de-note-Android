package com.stoicavlad.carnet.ui.note;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.stoicavlad.carnet.R;
import com.stoicavlad.carnet.data.model.Materie;
import com.stoicavlad.carnet.data.provider.CarnetContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoteListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public interface OnFragmentInteractionListener {
        public void showNotaDetailFragment(Materie materie);
    }
    private OnFragmentInteractionListener mListener;

    @InjectView(R.id.list) ListView mListView;
    CursorAdapter mCursorAdapter;

    private static final int MATERIE_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notefragment, container, false);
        ButterKnife.inject(this,view);
        // Set the adapter
        mCursorAdapter = new ComplexNoteAdapter(getActivity(),null,0);
        mListView.setAdapter(mCursorAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mListener != null){
                    //TODO
                    //mListener.showNotaDetailFragment(materiiDatabase.getMaterii()[position]);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MATERIE_LOADER, null, this);
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
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CarnetContract.MaterieEntry.CONTENT_URI,
                CarnetContract.MaterieEntry.COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursorAdapter.swapCursor(null);
    }

}
