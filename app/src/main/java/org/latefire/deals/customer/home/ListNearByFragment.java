package org.latefire.deals.customer.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import org.latefire.deals.R;
import org.latefire.deals.base.BaseFrament;
import org.latefire.deals.database.DatabaseManager;
import org.latefire.deals.database.Deal;

/**
 * Created by phongnguyen on 3/19/17.
 */
public class ListNearByFragment extends BaseFrament {

  private static final String TAG = ListNearByFragment.class.getSimpleName();

  @BindView(R.id.rv_deal_list) public RecyclerView rvDealList;

  public static ListNearByFragment newInstance() {
    ListNearByFragment fragment = new ListNearByFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Change the null parameter in {@code inflater.inflate()}
   * to a layout resource {@code R.layout.example}
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_near_by_list, container, false);
    ButterKnife.bind(this, rootView);

    DatabaseManager mgr = DatabaseManager.getInstance();
    rvDealList.setLayoutManager(new LinearLayoutManager(getContext()));
    rvDealList.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    //rvDealList.setAdapter(new DealItemAdapter(getContext(), mgr.getDealsOfBusiness("dummy-business")));
    //DenormFirebaseRecyclerAdapter<Deal, DealItemViewHolder> adapter =
    //    new DenormFirebaseRecyclerAdapter<>(mgr.getDealIdsOfBusiness(AuthManager.getInstance().getCurrentUserId()),
    //        mgr.getDealsRef(), Deal.class, R.layout.item_deal, DealItemViewHolder.class,
    //        getContext());
    //rvDealList.setAdapter(adapter);
    //
    rvDealList.setAdapter(
        new FirebaseRecyclerAdapter<Deal, DealItemViewHolder>(Deal.class, R.layout.item_deal, DealItemViewHolder.class, mgr.getDealsRef()) {
          @Override protected void populateViewHolder(DealItemViewHolder viewHolder, Deal model, int position) {
            viewHolder.setViewHolderFields(model, getActivity());
          }
        });
    //
    return rootView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }
}