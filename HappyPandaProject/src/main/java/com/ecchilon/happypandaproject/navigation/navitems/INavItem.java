package com.ecchilon.happypandaproject.navigation.navitems;

import android.os.Parcelable;
import com.ecchilon.happypandaproject.navigation.INavVisitor;

/**
 * Created by Alex on 11-4-2014.
 */
public interface INavItem extends Parcelable {
	<T> T visit(INavVisitor<T> visitor);
}
