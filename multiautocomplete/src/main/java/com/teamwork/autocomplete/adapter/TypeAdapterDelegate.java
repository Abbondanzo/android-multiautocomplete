package com.teamwork.autocomplete.adapter;

import android.database.DataSetObserver;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.teamwork.autocomplete.filter.TokenFilter;

import java.util.List;

/**
 * Internal use interface for a type adapter.
 * <p>
 * Used by {@link com.teamwork.autocomplete.MultiAutoComplete}'s main adapter to delegate filtering to one of the
 * registered type adapters.
 */
public interface TypeAdapterDelegate<M> {

    //region adapter items

    void registerDataSetObserver(@NonNull DataSetObserver observer);

    void unregisterDataSetObserver(@NonNull DataSetObserver observer);

    /**
     * Set the list of currently filtered items into the type adapter.
     * This will be called by the {@link Filter#performFiltering(CharSequence)} method.
     *
     * @param items A list of items to set in the adapter.
     */
    @MainThread
    void setFilteredItems(@NonNull List<M> items);

    /**
     * Get the current count of filtered items in the type adapter.
     */
    @MainThread
    int getCount();

    /**
     * Return the item at the passed position in the type adapter.
     *
     * @param position The position in the adapter.
     * @return The item.
     */
    @MainThread
    @NonNull M getItem(int position);

    /**
     * Return the item identifier as specified by {@link android.widget.BaseAdapter#getItemId(int)}.
     *
     * @param position The position of the item.
     * @return The numeric identifier of the item at the passed position.
     */
    @MainThread
    long getItemId(int position);

    //endregion

    //region layout and view binding

    /**
     * Retrieve the view for the given position using the inflater or the view holder associated to the view itself,
     * and binds the data to it.
     *
     * @param inflater    The {@link LayoutInflater} for inflating the view.
     * @param position    The adapter position of the view to retrieve.
     * @param convertView The convert view passed by the adapter.
     * @param parent      The view parent for view inflation.
     * @param constraint  The current text constraint to pass the view binder.
     * @return The view for the element at the passed position.
     * @see {@link android.widget.BaseAdapter#getView(int, View, ViewGroup)}
     */
    @NonNull View getView(@NonNull LayoutInflater inflater,
                          int position,
                          @Nullable View convertView,
                          @NonNull ViewGroup parent,
                          @Nullable CharSequence constraint);

    //endregion

    //region tokens, filters and constraints

    /**
     * Return the typed {@link TokenFilter} for this type adapter.
     */
    @NonNull TokenFilter<M> getFilter();

    /**
     * Called by {@link Filter#performFiltering(CharSequence)} off the main thread to filter the list in this type
     * adapter based on the passed constraint.
     *
     * @param constraint A text constraint to filter the adapter elements.
     * @return A List of filtered items from this adapter.
     */
    @WorkerThread
    @NonNull List<M> performFiltering(@NonNull CharSequence constraint);

    /**
     * Called by the {@link android.widget.MultiAutoCompleteTextView} when the text typed by the user has changed.
     *
     * @param text The text currently present in the editable text view.
     */
    @MainThread
    void onTextChanged(@NonNull CharSequence text);

    //endregion

}