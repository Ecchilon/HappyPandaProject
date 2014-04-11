package com.ecchilon.happypandaproject.navigation;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.navitems.BookmarkedNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.INavItem;
import com.ecchilon.happypandaproject.navigation.navitems.OverviewNavItem;
import com.ecchilon.happypandaproject.navigation.navitems.SectionItem;

/**
 * Created by Alex on 6-4-2014.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

	public interface INavVisitor<T> {
		T execute(SectionItem sectionNavItem);
		T execute(BookmarkedNavItem simpleNavItem);
		T execute(OverviewNavItem overviewNavItem);
	}

	private List<INavItem> mNavItems;
	private NavVisitor mNavVisitor;

	public NavigationDrawerAdapter(List<INavItem> items) {
		mNavItems = items;
		mNavVisitor = new NavVisitor();
	}

	@Override
	public int getCount() {
		return mNavItems.size();
	}

	@Override
	public INavItem getItem(int i) {
		return mNavItems.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).getViewType();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return getItem(position).getViewType() != 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		INavItem item = getItem(i);
		mNavVisitor.setView(view, viewGroup);

		return item.visit(mNavVisitor);
	}

	/**
	 * Visitor of navigation items
	 */
	private class NavVisitor implements INavVisitor<View>{
		private View mConvertView;
		private ViewGroup mViewGroup;

		public void setView(View convertView, ViewGroup viewGroup){
			mConvertView = convertView;
			mViewGroup = viewGroup;
		}

		private View inflateView(int id, View convertView, ViewGroup group) {
			if(convertView != null)
				return convertView;
			else {
				return LayoutInflater.from(group.getContext()).inflate(id, group, false);
			}
		}

		@Override
		public View execute(SectionItem sectionNavItem) {
			mConvertView = inflateView(R.layout.section_nav_item, mConvertView, mViewGroup);

			((TextView)mConvertView.findViewById(R.id.section_title)).setText(sectionNavItem.getTitle());

			ImageView icon = ((ImageView) mConvertView.findViewById(R.id.section_icon));
			if(sectionNavItem.getSectionIconResID() != 0) {
				icon.setVisibility(View.VISIBLE);
				icon.setImageResource(sectionNavItem.getSectionIconResID());
			}
			else {
				icon.setVisibility(View.GONE);
			}

			return mConvertView;
		}

		@Override
		public View execute(BookmarkedNavItem simpleNavItem) {
			mConvertView = inflateView(R.layout.simple_nav_item, mConvertView, mViewGroup);

			((TextView)mConvertView.findViewById(R.id.nav_item_title)).setText(simpleNavItem.getTitle());

			return mConvertView;
		}

		@Override
		public View execute(OverviewNavItem overviewNavItem) {
			mConvertView = inflateView(R.layout.simple_nav_item, mConvertView, mViewGroup);

			((TextView)mConvertView.findViewById(R.id.nav_item_title)).setText(overviewNavItem.getTitle());

			return mConvertView;
		}
	}
}
