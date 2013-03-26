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
import de.psdev.licensesdialog.licenses.License;

public class SingleLicenseDialog {
    private static final String TAG = "SingleLicenseDialog";

    private final Context mContext;
    private final Notice mNotice;

    // Settings
    private boolean mShowFullLicenseText;
    private final String mStyle;
    private final String mNoticesForFiles;
    private DialogInterface.OnDismissListener mOnDismissListener;


    public SingleLicenseDialog(final Context context, final Notice notice) {
        mContext = context;
        mNotice = notice;
        // Load defaults
        mStyle = context.getString(R.string.notices_default_style);
        mNoticesForFiles = context.getString(R.string.notices_for_files);
    }

    public SingleLicenseDialog setShowFullLicenseText(final boolean showFullLicenseText) {
        mShowFullLicenseText = showFullLicenseText;
        return this;
    }

    public SingleLicenseDialog setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    protected void appendNoticesContainerEnd(final StringBuilder noticesHtmlBuilder) {
        noticesHtmlBuilder.append("</body></html>");
    }

    protected void appendNoticesContainerStart(final StringBuilder noticesHtmlBuilder) {
        noticesHtmlBuilder.append("<!DOCTYPE html><html><head>").append(getStyle()).append("</head><body>").append("<h3>").append(mNoticesForFiles).append("</h3>");
    }

    protected void appendNoticeBlock(final StringBuilder noticesHtmlBuilder, final Notice currentNotice) {
        noticesHtmlBuilder.append("<ul><li>").append(currentNotice.getName());
        final String currentNoticeUrl = currentNotice.getUrl();
        if (currentNoticeUrl != null && currentNoticeUrl.length() > 0) {
            noticesHtmlBuilder.append(" (<a href=\"").append(currentNoticeUrl).append("\">").append(currentNoticeUrl).append("</a>)");
        }
        noticesHtmlBuilder.append("</li></ul>");
        noticesHtmlBuilder.append("<pre>").append(currentNotice.getCopyright()).append("\n\n");
        noticesHtmlBuilder.append(getLicenseText(currentNotice.getLicense(), mShowFullLicenseText)).append("</pre>");
    }

    protected String getLicenseText(final License license, final boolean showFullLicenseText) {
        if (license != null) {
            return showFullLicenseText ? license.getFullText(mContext) : license.getSummaryText(mContext);
        }

        return "";
    }

    protected String getStyle() {
        return String.format("<style type=\"text/css\">%s</style>", mStyle);
    }

    public void show() {
        //Get resources
        final Resources resources = mContext.getResources();

        final String titleString = resources.getString(R.string.notices_title);

        //Get button strings
        final String closeString = resources.getString(R.string.notices_close);

        final StringBuilder noticeHtml = new StringBuilder(400);
        appendNoticesContainerStart(noticeHtml);
        appendNoticeBlock(noticeHtml, mNotice);
        appendNoticesContainerEnd(noticeHtml);

        final WebView webView = new WebView(mContext);
        webView.loadDataWithBaseURL(null, noticeHtml.toString(), "text/html", "utf-8", null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(titleString)
                .setView(webView)
                .setPositiveButton(closeString, new Dialog.OnClickListener() {
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
        dialog.show();
    }

}
