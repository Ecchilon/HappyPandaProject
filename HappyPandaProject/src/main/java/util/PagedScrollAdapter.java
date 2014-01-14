package util;

import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import sites.GalleryOverviewInterface;

/**
 * Created by Alex on 1/4/14.
 */
public abstract class PagedScrollAdapter<T> extends BaseAdapter implements AbsListView.OnScrollListener, GalleryOverviewInterface.GalleryPageCreatedCallback<T> {
    boolean mLoading = false;
    int mPreviousTotal = 0;
    int mCurrentPage = 0;

    private List<T> mData;

    @Override
    public long getItemId(int position) {
        return position;
    }

    public PagedScrollAdapter() {
        mData = new ArrayList<T>();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public final void onScroll(AbsListView view, int firstVisibleItem,
                               int visibleItemCount, int totalItemCount) {

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        if (!mLoading
                && ((firstVisibleItem + visibleItemCount) > totalItemCount - 1)) {
            mLoading = true;
            loadNewDataSet();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /***
     *
     * @param notify
     *            true if the adapter should notify its listeners after clearing
     *            its contents
     */
    public void clear(boolean notify) {
        if (mData.size() > 0) {
            mData.clear();

            mCurrentPage = 0;
            mPreviousTotal = 0;
            mLoading = false;
            if (notify)
                notifyDataSetChanged();
        }
    }

    /**
     * implement this function to retrieve any data you want add to the adapter.
     * Adding the data to the adapter should be done manually.
     */
    public abstract void loadNewDataSet();

    /**
     * general method to return information to after loadNewDataSet() has been
     * called.
     */
    @Override
    public final void GalleryOverviewPageCreated(List<T> pageItems) {
        mData.addAll(pageItems);
        notifyDataSetChanged();
        mCurrentPage++;
    }

    /**
     * Called when loading a page failed, either due to a network problem, or no more items could be found
     */
    @Override
    public abstract void PageCreationFailed();

    /**
     * Helper function to give more control over the loading.
     * @param loading The new loading state
     */
    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    /**
     * Helper function to retrieve the current page index.
     * @return The current page index
     */
    public int getCurrentPage() {
        return mCurrentPage;
    }
}
