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
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import de.psdev.licensesdialog.model.Notices;

public class LicensesDialogFragment extends DialogFragment {

    private static final String ARGUMENT_NOTICES = "ARGUMENT_NOTICES";
    private static final String ARGUMENT_NOTICES_XML_ID = "ARGUMENT_NOTICES_XML_ID";
    private static final String ARGUMENT_INCLUDE_OWN_LICENSE = "ARGUMENT_INCLUDE_OWN_LICENSE";
    private static final String ARGUMENT_FULL_LICENSE_TEXT = "ARGUMENT_FULL_LICENSE_TEXT";
    private static final String ARGUMENT_THEME_XML_ID = "ARGUMENT_THEME_XML_ID";
    private static final String ARGUMENT_DIVIDER_COLOR = "ARGUMENT_DIVIDER_COLOR";
    private static final String STATE_TITLE_TEXT = "title_text";
    private static final String STATE_LICENSES_TEXT = "licenses_text";
    private static final String STATE_CLOSE_TEXT = "close_text";
    private static final String STATE_THEME_XML_ID = "theme_xml_id";
    private static final String STATE_DIVIDER_COLOR = "divider_color";

    //
    private String mTitleText;
    private String mCloseButtonText;
    private String mLicensesText;
    private int mThemeResourceId;
    private int mDividerColor;

    private DialogInterface.OnDismissListener mOnDismissListener;

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId) {
        return newInstance(rawNoticesResourceId, false);
    }

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId, final boolean showFullLicenseText) {
        return newInstance(rawNoticesResourceId, showFullLicenseText, false);
    }

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId, final boolean showFullLicenseText, final boolean includeOwnLicense) {
        return newInstance(rawNoticesResourceId, showFullLicenseText, includeOwnLicense, 0);
    }

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId, final boolean showFullLicenseText, final boolean includeOwnLicense,
                                                     final int themeResourceId) {
        return newInstance(rawNoticesResourceId, showFullLicenseText, includeOwnLicense, themeResourceId, 0);
    }

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId, final boolean showFullLicenseText, final boolean includeOwnLicense,
                                                     final int themeResourceId, final int dividerColor) {
        return newInstance(null, rawNoticesResourceId, showFullLicenseText, includeOwnLicense, themeResourceId, dividerColor);
    }

    public static LicensesDialogFragment newInstance(final int rawNoticesResourceId, final boolean showFullLicenseText, final boolean includeOwnLicense,
                                                     final int themeResourceId, final int dividerColorId, final Context context) {
        return newInstance(null, rawNoticesResourceId, showFullLicenseText, includeOwnLicense, themeResourceId, getColor(dividerColorId, context));
    }

    public static LicensesDialogFragment newInstance(final Notices notices, final boolean showFullLicenseText, final boolean includeOwnLicense) {
        return newInstance(notices, showFullLicenseText, includeOwnLicense, 0);
    }

    public static LicensesDialogFragment newInstance(final Notices notices, final boolean showFullLicenseText, final boolean includeOwnLicense,
                                                     final int themeResourceId) {
        return newInstance(notices, showFullLicenseText, includeOwnLicense, themeResourceId, 0);
    }

    public static LicensesDialogFragment newInstance(final Notices notices, final boolean showFullLicenseText, final boolean includeOwnLicense,
                                                     final int themeResourceId, final int dividerColor) {
        return newInstance(notices, -1, showFullLicenseText, includeOwnLicense, themeResourceId, dividerColor);
    }

    public static LicensesDialogFragment newInstance(final Notices notices, final boolean showFullLicenseText, final boolean includeOwnLicense,
                                                     final int themeResourceId, final int dividerColorId, final Context context) {
        return newInstance(notices, -1, showFullLicenseText, includeOwnLicense, themeResourceId, getColor(dividerColorId, context));
    }

    private static LicensesDialogFragment newInstance(final Notices notices, final int rawNoticesResourceId, final boolean showFullLicenseText,
                                                      final boolean includeOwnLicense, final int themeResourceId, final int dividerColor) {
        final LicensesDialogFragment licensesDialogFragment = new LicensesDialogFragment();
        final Bundle args = new Bundle();
        if (notices != null) {
            args.putParcelable(ARGUMENT_NOTICES, notices);
        } else {
            args.putInt(ARGUMENT_NOTICES_XML_ID, rawNoticesResourceId);
        }
        args.putBoolean(ARGUMENT_FULL_LICENSE_TEXT, showFullLicenseText);
        args.putBoolean(ARGUMENT_INCLUDE_OWN_LICENSE, includeOwnLicense);
        args.putInt(ARGUMENT_THEME_XML_ID, themeResourceId);
        args.putInt(ARGUMENT_DIVIDER_COLOR, dividerColor);
        licensesDialogFragment.setArguments(args);
        return licensesDialogFragment;
    }

    private static int getColor(final int dividerColorId, final Context context) {
        return context.getResources().getColor(dividerColorId);
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
            if (savedInstanceState.containsKey(STATE_THEME_XML_ID)) {
                mThemeResourceId = savedInstanceState.getInt(STATE_THEME_XML_ID);
            }
            if (savedInstanceState.containsKey(STATE_DIVIDER_COLOR)) {
                mDividerColor = savedInstanceState.getInt(STATE_DIVIDER_COLOR);
            }
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
                    if (arguments.containsKey(ARGUMENT_THEME_XML_ID)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            mThemeResourceId = arguments.getInt(ARGUMENT_THEME_XML_ID, android.R.style.Theme_DeviceDefault_Light_Dialog);
                        } else {
                            mThemeResourceId = arguments.getInt(ARGUMENT_THEME_XML_ID);
                        }
                    }
                    if (arguments.containsKey(ARGUMENT_DIVIDER_COLOR)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            mDividerColor = arguments.getInt(ARGUMENT_DIVIDER_COLOR, android.R.color.holo_blue_light);
                        } else {
                            mDividerColor = arguments.getInt(ARGUMENT_DIVIDER_COLOR);
                        }
                    }
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
        if (mThemeResourceId != 0) {
            outState.putInt(STATE_THEME_XML_ID, mThemeResourceId);
        }
        if (mDividerColor != 0) {
            outState.putInt(STATE_DIVIDER_COLOR, mDividerColor);
        }
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new LicensesDialog.Builder(getActivity())
            .setNotices(mLicensesText)
            .setTitle(mTitleText).setCloseText(mCloseButtonText)
            .setThemeResourceId(mThemeResourceId).setDividerColor(mDividerColor)
            .build().create();
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
