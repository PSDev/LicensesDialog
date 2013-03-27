/*
 * Copyright 2013 Philip Schiffer <admin@psdev.de>
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.webkit.WebView;
import de.psdev.licensesdialog.model.Notices;

public class LicensesDialog {
    private final Context mContext;
    private final String mTitleText;
    private final String mLicensesText;
    private final String mCloseText;

    //
    private DialogInterface.OnDismissListener mOnDismissListener;

    public LicensesDialog(final Context context, final int rawNoticesResourceId, final boolean showFullLicenseText) {
        mContext = context;
        // Load defaults
        final String style = context.getString(R.string.notices_default_style);
        mTitleText = context.getString(R.string.notices_title);
        try {
            final Resources resources = context.getResources();
            if ("raw".equals(resources.getResourceTypeName(rawNoticesResourceId))) {
                final Notices notices = NoticesXmlParser.parse(resources.openRawResource(rawNoticesResourceId));
                mLicensesText = NoticesHtmlBuilder.create(mContext).setShowFullLicenseText(showFullLicenseText).setNotices(notices).setStyle(style).build();
            } else {
                throw new IllegalStateException("not a raw resource");
            }
        } catch (Exception e) {
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

    public LicensesDialog setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    public Dialog create() {
        //Get resources
        final WebView webView = new WebView(mContext);
        webView.loadDataWithBaseURL(null, mLicensesText, "text/html", "utf-8", null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(mTitleText)
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
        create().show();
    }

    //


}
