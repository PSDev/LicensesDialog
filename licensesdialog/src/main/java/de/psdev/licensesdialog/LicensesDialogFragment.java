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
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class LicensesDialogFragment extends DialogFragment {

    private static final String ARGUMENT_NOTICES = "ARGUMENT_NOTICES";
    private static final String ARGUMENT_NOTICES_XML_ID = "ARGUMENT_NOTICES_XML_ID";
    private static final String ARGUMENT_INCLUDE_OWN_LICENSE = "ARGUMENT_INCLUDE_OWN_LICENSE";
    private static final String ARGUMENT_FULL_LICENSE_TEXT = "ARGUMENT_FULL_LICENSE_TEXT";
    private static final String ARGUMENT_THEME_XML_ID = "ARGUMENT_THEME_XML_ID";
    private static final String ARGUMENT_DIVIDER_COLOR = "ARGUMENT_DIVIDER_COLOR";
    private static final String ARGUMENT_NOTICE_STYLE = "ARGUMENT_NOTICE_STYLE";
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

    // ==========================================================================================================================
    // Factory
    // ==========================================================================================================================

    private static LicensesDialogFragment newInstance(final Notices notices,
                                                      final boolean showFullLicenseText,
                                                      final boolean includeOwnLicense,
                                                      final String noticeStyle,
                                                      final int themeResourceId,
                                                      final int dividerColor) {
        final LicensesDialogFragment licensesDialogFragment = new LicensesDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_NOTICES, notices);
        args.putBoolean(ARGUMENT_FULL_LICENSE_TEXT, showFullLicenseText);
        args.putBoolean(ARGUMENT_INCLUDE_OWN_LICENSE, includeOwnLicense);
        args.putString(ARGUMENT_NOTICE_STYLE, noticeStyle);
        args.putInt(ARGUMENT_THEME_XML_ID, themeResourceId);
        args.putInt(ARGUMENT_DIVIDER_COLOR, dividerColor);
        licensesDialogFragment.setArguments(args);
        return licensesDialogFragment;
    }

    private static LicensesDialogFragment newInstance(final int rawNoticesResourceId,
                                                      final boolean showFullLicenseText,
                                                      final boolean includeOwnLicense,
                                                      final String noticeStyle,
                                                      final int themeResourceId,
                                                      final int dividerColor) {
        final LicensesDialogFragment licensesDialogFragment = new LicensesDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARGUMENT_NOTICES_XML_ID, rawNoticesResourceId);
        args.putBoolean(ARGUMENT_FULL_LICENSE_TEXT, showFullLicenseText);
        args.putBoolean(ARGUMENT_INCLUDE_OWN_LICENSE, includeOwnLicense);
        args.putString(ARGUMENT_NOTICE_STYLE, noticeStyle);
        args.putInt(ARGUMENT_THEME_XML_ID, themeResourceId);
        args.putInt(ARGUMENT_DIVIDER_COLOR, dividerColor);
        licensesDialogFragment.setArguments(args);
        return licensesDialogFragment;
    }

    // ==========================================================================================================================
    // Android Lifecycle
    // ==========================================================================================================================

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
                    String noticeStyle = arguments.getString(ARGUMENT_NOTICE_STYLE);
                    if (noticeStyle == null) {
                        noticeStyle = resources.getString(R.string.notices_default_style);
                    }
                    mLicensesText = NoticesHtmlBuilder.create(getActivity()).setNotices(notices).setShowFullLicenseText(showFullLicenseText).setStyle(noticeStyle).build();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final LicensesDialog.Builder builder = new LicensesDialog.Builder(requireContext())
            .setNotices(mLicensesText)
            .setTitle(mTitleText).setCloseText(mCloseButtonText)
            .setThemeResourceId(mThemeResourceId).setDividerColor(mDividerColor);
        final LicensesDialog licensesDialog = builder.build();
        return licensesDialog.create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    // ==========================================================================================================================
    // Public API
    // ==========================================================================================================================

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    // ==========================================================================================================================
    // Private API
    // ==========================================================================================================================

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

    // ==========================================================================================================================
    // Inner classes
    // ==========================================================================================================================

    public static class Builder {

        private final Context mContext;
        private Notices mNotices;
        private Integer mRawNoticesResourceId;
        private boolean mShowFullLicenseText;
        private boolean mIncludeOwnLicense;
        private String mNoticesStyle;
        private int mThemeResourceId;
        private int mDividerColor;

        // ==========================================================================================================================
        // Constructor
        // ==========================================================================================================================

        public Builder(@NonNull final Context context) {
            mContext = context;
            // Set default values
            mShowFullLicenseText = false;
            mIncludeOwnLicense = true;
            mNoticesStyle = context.getString(R.string.notices_default_style);
            mThemeResourceId = 0;
            mDividerColor = 0;
        }

        // ==========================================================================================================================
        // Public API
        // ==========================================================================================================================

        public Builder setNotice(final Notice notice) {
            mNotices = new Notices();
            mNotices.addNotice(notice);
            return this;
        }

        public Builder setNotices(final Notices notices) {
            mNotices = notices;
            return this;
        }

        public Builder setNotices(@RawRes final int rawNoticesResourceId) {
            mRawNoticesResourceId = rawNoticesResourceId;
            return this;
        }

        public Builder setShowFullLicenseText(final boolean showFullLicenseText) {
            mShowFullLicenseText = showFullLicenseText;
            return this;
        }

        public Builder setIncludeOwnLicense(final boolean includeOwnLicense) {
            mIncludeOwnLicense = includeOwnLicense;
            return this;
        }

        public Builder setNoticesCssStyle(@StringRes final int cssStyleTextId) {
            mNoticesStyle = mContext.getString(cssStyleTextId);
            return this;
        }

        public Builder setNoticesCssStyle(final String cssStyleText) {
            mNoticesStyle = cssStyleText;
            return this;
        }

        public Builder setThemeResourceId(@StyleRes final int themeResourceId) {
            mThemeResourceId = themeResourceId;
            return this;
        }

        public Builder setDividerColorRes(@ColorRes final int dividerColor) {
            mDividerColor = mContext.getResources().getColor(dividerColor);
            return this;
        }

        public Builder setDividerColor(@ColorInt final int dividerColor) {
            mDividerColor = dividerColor;
            return this;
        }

        public LicensesDialogFragment build() {
            if (mNotices != null) {
                return newInstance(mNotices, mShowFullLicenseText, mIncludeOwnLicense, mNoticesStyle, mThemeResourceId, mDividerColor);
            } else if (mRawNoticesResourceId != null) {
                return newInstance(mRawNoticesResourceId, mShowFullLicenseText, mIncludeOwnLicense, mNoticesStyle, mThemeResourceId, mDividerColor);
            } else {
                throw new IllegalStateException("Required parameter not set. You need to call setNotices.");
            }
        }

    }

}
