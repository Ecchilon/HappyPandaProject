package com.ecchilon.happypandaproject.navigation;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecchilon.happypandaproject.R;

/**
 * Created by Alex on 6-4-2014.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

	public interface IDrawerVisitor<T> {
		T execute(SectionDrawerItem sectionItem, View convertView, ViewGroup group);
		T execute(NavDrawerItem navDrawerItem, View convertView, ViewGroup group);
		T execute(EditableSectionDrawerItem editableSectionDrawerItem, View convertView, ViewGroup group);
	}

	private List<IDrawerItem> mNavItems;
	private NavDrawerVisitor mNavVisitor;

	public NavigationDrawerAdapter(List<IDrawerItem> items) {
		mNavItems = items;
		mNavVisitor = new NavDrawerVisitor();
	}

	@Override
	public int getCount() {
		return mNavItems.size();
	}

	@Override
	public IDrawerItem getItem(int i) {
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
		return 3;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return getItem(position).getViewType() == 1;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return getItem(i).visit(mNavVisitor, view, viewGroup);
	}

	/**
	 * Visitor of drawer items
	 */
	private class NavDrawerVisitor implements IDrawerVisitor<View> {

		private View inflateView(int id, View convertView, ViewGroup group) {
			if(convertView != null)
				return convertView;
			else {
				return LayoutInflater.from(group.getContext()).inflate(id, group, false);
			}
		}

		@Override
		public View execute(SectionDrawerItem sectionNavItem, View convertView, ViewGroup group) {
			convertView = inflateView(R.layout.section_nav_item, convertView, group);

			((TextView)convertView.findViewById(R.id.section_title)).setText(sectionNavItem.getTitle());

			ImageView icon = ((ImageView) convertView.findViewById(R.id.section_icon));
			if(sectionNavItem.getSectionIconResID() != 0) {
				icon.setVisibility(View.VISIBLE);
				icon.setImageResource(sectionNavItem.getSectionIconResID());
			}
			else {
				icon.setVisibility(View.GONE);
			}

			return convertView;
		}

		@Override
		public View execute(NavDrawerItem navDrawerItem, View convertView, ViewGroup group) {
			convertView = inflateView(R.layout.simple_nav_item, convertView, group);

			((TextView)convertView.findViewById(R.id.nav_item_title)).setText(navDrawerItem.getTitle());

			return convertView;
		}

		@Override
		public View execute(EditableSectionDrawerItem editableSectionDrawerItem, View convertView, ViewGroup group) {
			convertView = inflateView(R.layout.editable_section_item, convertView, group);

			((TextView)convertView.findViewById(R.id.section_title)).setText(editableSectionDrawerItem.getTitle());

			ImageView icon = ((ImageView) convertView.findViewById(R.id.section_icon));
			if (editableSectionDrawerItem.getSectionIconResID() != 0) {
				icon.setVisibility(View.VISIBLE);
				icon.setImageResource(editableSectionDrawerItem.getSectionIconResID());
			}
			else {
				icon.setVisibility(View.GONE);
			}

			convertView.findViewById(R.id.edit_section).setOnClickListener(editableSectionDrawerItem.getListener());

			return convertView;
		}
	}
}
