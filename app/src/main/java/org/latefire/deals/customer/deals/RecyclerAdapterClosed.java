package org.latefire.deals.customer.deals;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import org.latefire.deals.R;
import org.latefire.deals.database.DatabaseManager;
import org.latefire.deals.database.Deal;
import org.latefire.deals.database.DealAcquired;
import org.latefire.deals.ui.FormatManager;
import org.latefire.deals.utils.StringUtils;

/**
 * FirebaseRecyclerAdapter for creating AbsModel items from a list of AbsModel IDs (key = ID,
 * value = boolean). These IDs are denormalised relations of 1-n and n-n relationships, e.g. a
 * list of deal IDs saved in a business object. This adapter listens on these IDs and creates
 * list item views from the corresponding real AbsModel objects.
 */
class RecyclerAdapterClosed extends FirebaseRecyclerAdapter<DealAcquired, DealViewHolderClosed> {

  private Context mContext;
  private boolean mIsReverse;
  private ItemFilter mItemFilter;
  private int mItemHeight;

  public RecyclerAdapterClosed(Context c, String customerId, boolean isReverse, ItemFilter filter) {
    super(DealAcquired.class, R.layout.item_deal, DealViewHolderClosed.class, DatabaseManager
        .getInstance().getDealsOfCustomer(customerId));
    mContext = c;
    mIsReverse = isReverse;
    mItemFilter = filter;
  }

  // Return items in natural or reversed order
  @Override public DealAcquired getItem(int position) {
    if (mIsReverse) position = getItemCount()-1-position;
    return super.getItem(position);
  }


  @Override protected void populateViewHolder(DealViewHolderClosed holder, DealAcquired dealAcquired, int position) {
    hideItem(holder.itemView);
    if (mItemFilter.isDisplayItem(dealAcquired)) {
      showItem(holder.itemView);
      Deal deal = dealAcquired.getDeal();
      // Title
      holder.tvDealTitle.setText(deal.getTitle());
      // Photo
      Glide.with(mContext)
          .load(deal.getPhoto())
          .asBitmap()
          .placeholder(R.drawable.placeholder)
          .error(R.drawable.image_not_found)
          .centerCrop()
          .into(holder.imgDealPhoto);
      // Deal price and regular price
      String regularPrice = String.valueOf(deal.getRegularPrice());
      String dealPrice = String.valueOf(deal.getDealPrice());
      SpannableStringBuilder price = StringUtils.makePriceText(mContext, regularPrice, dealPrice);
      holder.tvDealPrice.setText(price, TextView.BufferType.EDITABLE);
      String beginValidity = FormatManager.getInstance().formatTimestamp(deal.getBeginValidity());
      String endValidity = FormatManager.getInstance().formatTimestamp(deal.getEndValidity());
      holder.tvDealDate.setText(beginValidity + " - " + endValidity);
      holder.tvDealLocation.setText(deal.getLocationName());
    }
  }

  private void hideItem(View item) {
    ViewGroup.LayoutParams params = item.getLayoutParams();
    mItemHeight = params.height;
    params.height = 0;
    item.setLayoutParams(params);
  }

  private void showItem(View item) {
    ViewGroup.LayoutParams params = item.getLayoutParams();
    params.height = mItemHeight;
    item.setLayoutParams(params);
  }


}
