package com.galou.moodtracker.controllers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galou.moodtracker.R;
import com.galou.moodtracker.models.Mood;
import com.galou.moodtracker.views.MoodViewHolder;

import java.util.List;

/**
 * <b>
 *     Represent a Mood Adapter
 * </b>
 * <p>
 *     Bind the RecyclerView from History Fragment with the MoodViewHolder
 *
 *     A RecyclerView.Adapter<MoodViewHolder> subclass
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see MoodViewHolder
 * @see com.galou.moodtracker.controllers.fragments.HistoryFragment
 *
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodViewHolder> {

    //----------------------//
    //INTERFACE CALLBACK
    //----------------------//

    /**
     * Callback interface used in the fragment to know when the user click on a element of the view
     *
     * @see com.galou.moodtracker.controllers.fragments.HistoryFragment#onClickCommentButton(int)
     */
    public interface Listener{
        /**
         * Callback for the Comment Button
         * @param position
         *      position of the item clicked
         */
        void onClickCommentButton(int position);
        /**
         * Callback for the Share Button
         * @param position
         *      position of the item clicked
         */
        void onClickShareButton(int position);
    }

    // FOR DATA
    /**
     * List of mood to display in the RecyclerView
     * @see #MoodAdapter(List, float, Listener, Context)
     */
    private List<Mood> moodList;
    /**
     * Width of the screen
     * <p>
     *     Used to set the Mood width
     * </p>
     * @see #MoodAdapter(List, float, Listener, Context)
     * @see com.galou.moodtracker.views.MoodViewHolder#setSizeCardViewForMood(int)
     */
    private float widthScreen;
    /**
     * Callback Listener used to send action happening on th Recycler View to the Fragment
     *
     * @see #MoodAdapter(List, float, Listener, Context)
     */
    private final Listener callback;
    /**
     * Context in which the Recycler View is
     *
     * @see #MoodAdapter(List, float, Listener, Context)
     */
    private final Context context;


    /**
     * Constructor, gets all the data from the fragment
     *
     * @param moodList
     *      List of Mood to be displayed on the Recycler View
     * @param widthScreen
     *      width of the screen
     * @param callback
     *      callback to fragment
     * @param context
     *      Activity in which the Recycler View is located
     *
     * @see MoodAdapter#moodList
     * @see MoodAdapter#widthScreen
     * @see MoodAdapter#callback
     * @see MoodAdapter#context
     */
    public MoodAdapter(List<Mood> moodList, float widthScreen, Listener callback, Context context) {
        this.moodList = moodList;
        this.widthScreen = widthScreen;
        this.callback = callback;
        this.context = context;
    }

    /**
     * Create View Holder and inflate it with its layout XML
     *
     * @param parent
     *      parent of the Recycler View
     * @param viewType
     *      type of view
     * @return
     *      new View Holder to display the moods
     *
     * @see MoodViewHolder
     */
    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_history_item, parent, false);

        return new MoodViewHolder(view, widthScreen);
    }

    /**
     * Update the ViewHolder with the list of Mood
     * @param holder
     *      View holder of the Recycler View item
     * @param position
     *      position in the list of the item
     *
     * @see MoodViewHolder
     * @see MoodViewHolder#updateWithMood(Mood, Listener, Context)
     */
    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
        holder.updateWithMood(this.moodList.get(position), this.callback, this.context);

    }

    /**
     * Return the total count of items in the list
     * @return
     *      total count of items
     *
     * @see MoodAdapter#moodList
     */
    @Override
    public int getItemCount() {
        return this.moodList.size();

    }
}
