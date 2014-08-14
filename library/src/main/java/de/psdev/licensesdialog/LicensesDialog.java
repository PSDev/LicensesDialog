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

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.WebView;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class LicensesDialog {
    public static final Notice LICENSES_DIALOG_NOTICE = new Notice("LicensesDialog", "http://psdev.de/LicensesDialog",
        "Copyright 2013 Philip Schiffer",
        new ApacheSoftwareLicense20());

    private final Context mContext;
    private final String mTitleText;
    private final String mLicensesText;
    private final String mCloseText;

    //
    private DialogInterface.OnDismissListener mOnDismissListener;
    private int mThemeResourceId;
    private int mDividerColor;

    public LicensesDialog(final Context context, final int titleResourceId, final int rawNoticesResourceId, final int closeResourceId,
                          final boolean showFullLicenseText, final boolean includeOwnLicense,
                          final int themeResourceId, final int dividerColor) {
        mContext = context;
        // Load defaults
        final String style = context.getString(R.string.notices_default_style);
        mTitleText = context.getString(titleResourceId);
        try {
            final Resources resources = context.getResources();
            if ("raw".equals(resources.getResourceTypeName(rawNoticesResourceId))) {
                final Notices notices = NoticesXmlParser.parse(resources.openRawResource(rawNoticesResourceId));
                if (includeOwnLicense) {
                    final List<Notice> noticeList = notices.getNotices();
                    noticeList.add(LICENSES_DIALOG_NOTICE);
                }
                mLicensesText = NoticesHtmlBuilder.create(mContext).setShowFullLicenseText(showFullLicenseText).setNotices(notices).setStyle(style)
                    .build();
            } else {
                throw new IllegalStateException("not a raw resource");
            }
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
        mCloseText = context.getString(closeResourceId);
        mThemeResourceId = themeResourceId;
        mDividerColor = dividerColor;
    }

    public LicensesDialog(final Context context, final int titleResourceId, final int rawNoticesResourceId, final int closeResourceId,
                          final boolean showFullLicenseText, final boolean includeOwnLicense,
                          final int themeResourceId) {
        this(context, titleResourceId, rawNoticesResourceId, closeResourceId, showFullLicenseText,
                includeOwnLicense, themeResourceId, 0);
    }

    public LicensesDialog(final Context context, final int titleResourceId, final int rawNoticesResourceId, final int closeResourceId,
                          final boolean showFullLicenseText, final boolean includeOwnLicense) {
        this(context, titleResourceId, rawNoticesResourceId, closeResourceId, showFullLicenseText,
                includeOwnLicense, 0, 0);
    }

    public LicensesDialog(final Context context, final int rawNoticesResourceId, final boolean showFullLicenseText, final boolean includeOwnLicense) {
        this(context, R.string.notices_title, rawNoticesResourceId, R.string.notices_close, showFullLicenseText, includeOwnLicense, 0, 0);
    }

    public LicensesDialog(final Context context, final Notices notices, final boolean showFullLicenseText, final boolean includeOwnLicense) {
        mContext = context;
        // Load defaults
        final String style = context.getString(R.string.notices_default_style);
        mTitleText = context.getString(R.string.notices_title);
        try {
            if (includeOwnLicense) {
                final List<Notice> noticeList = notices.getNotices();
                noticeList.add(LICENSES_DIALOG_NOTICE);
            }
            mLicensesText = NoticesHtmlBuilder.create(mContext).setShowFullLicenseText(showFullLicenseText).setNotices(notices).setStyle(style)
                .build();
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
        mCloseText = context.getString(R.string.notices_close);
    }

    public LicensesDialog(final Context context, final String titleText, final String licensesText, final String closeText) {
        mContext = context;
        mTitleText = titleText;
        mLicensesText = licensesText;
        mCloseText = closeText;
    }

    public LicensesDialog(final Context context, final String titleText, final String licensesText, final String closeText,
                          final int themeResourceId) {
        mContext = context;
        mTitleText = titleText;
        mLicensesText = licensesText;
        mCloseText = closeText;
        mThemeResourceId = themeResourceId;
    }

    public LicensesDialog(final Context context, final String titleText, final String licensesText, final String closeText,
                          final int themeResourceId, final int dividerColor) {
        mContext = context;
        mTitleText = titleText;
        mLicensesText = licensesText;
        mCloseText = closeText;
        mThemeResourceId = themeResourceId;
        mDividerColor = dividerColor;
    }

    public Dialog create() {
        //Get resources
        final WebView webView = new WebView(mContext);
        webView.loadDataWithBaseURL(null, mLicensesText, "text/html", "utf-8", null);
		final AlertDialog.Builder builder;
        if (mThemeResourceId != 0) {
			builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, mThemeResourceId));
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setTitle(mTitleText)
               .setView(webView)
               .setPositiveButton(mCloseText, new Dialog.OnClickListener() {
                   public void onClick(final DialogInterface dialogInterface, final int i) {
                       dialogInterface.dismiss();
                   }
               });
	    final AlertDialog dialog = builder.create();
	    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
	        @Override
	        public void onDismiss(final DialogInterface dialog) {
	            if (mOnDismissListener != null) {
	                mOnDismissListener.onDismiss(dialog);
	            }
	        }
	    });
	    return dialog;
	}

    public void show() {
        Dialog dialog = create();
        show(dialog);
    }

    public void show(Dialog dialog) {
        dialog.show();
        if (mDividerColor != 0) {
            // Set title divider color
            int titleDividerId = mContext.getResources().getIdentifier("titleDivider", "id", "android");
            View titleDivider = dialog.findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(mDividerColor);
            }
        }
    }

    public Dialog createAndShow() {
        Dialog dialog = create();
        show(dialog);
        return dialog;
    }
	
    //


}
