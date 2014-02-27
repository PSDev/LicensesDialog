/*
 * Copyright 2013 Philip Schiffer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.psdev.licensesdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import de.psdev.licensesdialog.model.Notices;

public class LicensesDialogFragment extends DialogFragment {

    private static final String ARGUMENT_NOTICES = "ARGUMENT_NOTICES";
    private static final String ARGUMENT_NOTICES_XML_ID = "ARGUMENT_NOTICES_XML_ID";
    private static final String ARGUMENT_INCLUDE_OWN_LICENSE = "ARGUMENT_INCLUDE_OWN_LICENSE";
    private static final String ARGUMENT_FULL_LICENSE_TEXT = "ARGUMENT_FULL_LICENSE_TEXT";
    private static final String STATE_TITLE_TEXT = "title_text";
    private static final String STATE_LICENSES_TEXT = "licenses_text";
    private static final String STATE_CLOSE_TEXT = "close_text";

    //
    private String mTitleText;
    private String mCloseButtonText;
    private String mLicensesText;

    private DialogInterface.OnDismissListener mOnDismissListener;

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId, final boolean includeOwnLicense) {
        return newInstance(rawNoticesResourceId, false, includeOwnLicense);
    }

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId,
                                                     final boolean showFullLicenseText, final boolean includeOwnLicense) {
        final LicensesDialogFragment licensesDialogFragment = new LicensesDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARGUMENT_NOTICES_XML_ID, rawNoticesResourceId);
        args.putBoolean(ARGUMENT_FULL_LICENSE_TEXT, showFullLicenseText);
        args.putBoolean(ARGUMENT_INCLUDE_OWN_LICENSE, includeOwnLicense);
        licensesDialogFragment.setArguments(args);
        return licensesDialogFragment;
    }

    public static LicensesDialogFragment newInstance(final Notices notices, final boolean showFullLicenseText, final boolean includeOwnLicense) {
        final LicensesDialogFragment licensesDialogFragment = new LicensesDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_NOTICES, notices);
        args.putBoolean(ARGUMENT_FULL_LICENSE_TEXT, showFullLicenseText);
        args.putBoolean(ARGUMENT_INCLUDE_OWN_LICENSE, includeOwnLicense);
        licensesDialogFragment.setArguments(args);
        return licensesDialogFragment;
    }

    public LicensesDialogFragment() {
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Resources resources = getResources();

        if (savedInstanceState != null) {
            mTitleText = savedInstanceState.getString(STATE_TITLE_TEXT);
            mLicensesText = savedInstanceState.getString(STATE_LICENSES_TEXT);
            mCloseButtonText = savedInstanceState.getString(STATE_CLOSE_TEXT);
        } else {
            mTitleText = resources.getString(R.string.notices_title);
            mCloseButtonText = resources.getString(R.string.notices_close);
            try {
                final Notices notices;
                final Bundle arguments = getArguments();
                if (arguments != null) {
                    if (arguments.containsKey(ARGUMENT_NOTICES_XML_ID)) {
                        notices = NoticesXmlParser.parse(resources.openRawResource(getNoticesXmlResourceId()));
                    } else if (arguments.containsKey(ARGUMENT_NOTICES)) {
                        notices = arguments.getParcelable(ARGUMENT_NOTICES);
                    } else {
                        throw new IllegalStateException("Missing ARGUMENT_NOTICES_XML_ID / ARGUMENT_NOTICES");
                    }
                    if (arguments.getBoolean(ARGUMENT_INCLUDE_OWN_LICENSE, false)) {
                        notices.getNotices().add(LicensesDialog.LICENSES_DIALOG_NOTICE);
                    }
                    final boolean showFullLicenseText = arguments.getBoolean(ARGUMENT_FULL_LICENSE_TEXT, false);
                    mLicensesText = NoticesHtmlBuilder.create(getActivity()).setNotices(notices).setShowFullLicenseText(showFullLicenseText).build();
                } else {
                    throw new IllegalStateException("Missing arguments");
                }
            } catch (final Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE_TEXT, mTitleText);
        outState.putString(STATE_LICENSES_TEXT, mLicensesText);
        outState.putString(STATE_CLOSE_TEXT, mCloseButtonText);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new LicensesDialog(getActivity(), mTitleText, mLicensesText, mCloseButtonText).create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    //

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    //

    private int getNoticesXmlResourceId() {
        int resourceId = R.raw.notices;
        final Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARGUMENT_NOTICES_XML_ID)) {
            resourceId = arguments.getInt(ARGUMENT_NOTICES_XML_ID);
            if (!"raw".equalsIgnoreCase(getResources().getResourceTypeName(resourceId))) {
                throw new IllegalStateException("not a raw resource");
            }
        }

        return resourceId;
    }
}
