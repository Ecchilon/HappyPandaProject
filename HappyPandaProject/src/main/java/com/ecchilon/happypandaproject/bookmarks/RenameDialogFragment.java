package com.ecchilon.happypandaproject.bookmarks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.ecchilon.happypandaproject.R;
import com.ecchilon.happypandaproject.navigation.NavDrawerItem;

/**
 * Dialog which allows user to rename their bookmarks. Created by Alex on 5/6/2014.
 */
public class RenameDialogFragment extends DialogFragment implements TextWatcher {

	public interface RenameListener {
		public void onNewNameSet(NavDrawerItem item, String name);
	}

	private RenameListener mListener;
	private AlertDialog mDialog;
	private NavDrawerItem mItem;
	private EditText mEditText;

	/**
	 * Set the listener which gets called on completion of renaming
	 *
	 * @param listener
	 */
	public void setRenameListener(RenameListener listener) {
		mListener = listener;
	}

	/**
	 * Sets the item which is to be renamed
	 *
	 * @param item
	 */
	public void setItem(NavDrawerItem item) {
		mItem = item;
	}

	/**
	 * Creates a dialog for renaming the bookmark
	 *
	 * @param savedInstanceState
	 * @return
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		mEditText = new EditText(getActivity());
		mEditText.setFocusable(true);
		mEditText.setHint(R.string.rename_hint);
		if (mItem != null) {
			mEditText.setText(mItem.getTitle());
		}
		mEditText.addTextChangedListener(this);

		builder.setView(mEditText);
		builder.setTitle(R.string.rename_title);

		builder.setPositiveButton(R.string.rename_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if (mListener != null) {
					mListener.onNewNameSet(mItem, mEditText.getText().toString());
				}

				dialogInterface.dismiss();
			}
		});

		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});

		mDialog = builder.create();

		return mDialog;
	}

	/**
	 * Dialog is only available now for disabling the button if no text has been set
	 */
	@Override
	public void onStart() {
		super.onStart();

		if (mEditText.getText().length() == 0) {
			mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		}
	}

	/**
	 * Enables or disables the accept button based on the name in the dialog
	 *
	 * @param editable
	 */
	@Override
	public void afterTextChanged(Editable editable) {
		if (editable.length() > 0) {
			mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
		}
		else {
			mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		}
	}


	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
		//MY CODE! THE FUNCTIONS THEY DO NOTHING!
	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
		//MY CODE! THE FUNCTIONS THEY DO NOTHING!
	}
}
