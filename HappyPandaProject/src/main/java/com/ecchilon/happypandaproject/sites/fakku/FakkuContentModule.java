package com.ecchilon.happypandaproject.sites.fakku;

import com.ecchilon.happypandaproject.sites.StringContentAbstract;

/**
 * Created by Alex on 22-5-2014.
 */
public class FakkuContentModule extends StringContentAbstract {
	private String mBaseUrl;

	public FakkuContentModule(String baseUrl) {
		mBaseUrl = baseUrl;
	}

	@Override
	public String getUrl(int index) {
		return mBaseUrl + (index + 1);
	}
}
