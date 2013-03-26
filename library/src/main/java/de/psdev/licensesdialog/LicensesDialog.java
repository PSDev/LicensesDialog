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
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.webkit.WebView;
import de.psdev.licensesdialog.licenses.License;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Locale;

public class LicensesDialog {
    private static final String TAG = "LicensesDialog";

    private final Context mContext;
    private final int mNoticesXml;


    private final String mStyle;
    private final String mNoticesForFiles;
    private boolean mShowFullLicenseText;
    private DialogInterface.OnDismissListener mOnDismissListener;

    public LicensesDialog(final Context context, final int noticesXml) {
        mContext = context;
        mNoticesXml = noticesXml;
        // Load defaults
        mStyle = context.getString(R.string.notices_default_style);
        mNoticesForFiles = context.getString(R.string.notices_for_files);
    }

    public LicensesDialog setShowFullLicenseText(final boolean showFullLicenseText) {
        mShowFullLicenseText = showFullLicenseText;
        return this;
    }

    public LicensesDialog setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    private String getNoticesHtml(final int resourceId, final Resources resources) {
        final StringBuilder noticesHtmlBuilder = new StringBuilder(500);
        appendNoticesContainerStart(noticesHtmlBuilder);
        final XmlResourceParser xml = resources.getXml(resourceId);
        try {
            int eventType = xml.getEventType();
            Notice currentNotice = null;
            while (XmlPullParser.END_DOCUMENT != eventType) {
                final String xmlName = xml.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("notice".equals(xmlName)) {
                            currentNotice = new Notice();
                        } else if ("name".equals(xmlName)) {
                            eventType = xml.next();
                            currentNotice.setName(xml.getText());
                        } else if ("url".equals(xmlName)) {
                            eventType = xml.next();
                            currentNotice.setUrl(xml.getText());
                        } else if ("copyright".equals(xmlName)) {
                            eventType = xml.next();
                            currentNotice.setCopyright(xml.getText());
                        } else if ("license".equals(xmlName)) {
                            eventType = xml.next();
                            currentNotice.setLicense(resolveLicense(xml.getText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("notice".equals(xmlName)) {
                            appendNoticeBlock(noticesHtmlBuilder, currentNotice);
                        }
                }
                eventType = xml.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        } finally {
            xml.close();
        }

        appendNoticesContainerEnd(noticesHtmlBuilder);

        return noticesHtmlBuilder.toString();
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
        noticesHtmlBuilder.append("<pre>");
        final String copyright = currentNotice.getCopyright();
        if(copyright != null) {
            noticesHtmlBuilder.append(copyright).append("<br/><br/>");
        }
        noticesHtmlBuilder.append(getLicenseText(currentNotice.getLicense(), mShowFullLicenseText)).append("</pre>");
    }

    protected String getLicenseText(final License license, final boolean showFullLicenseText) {
        if (license != null) {
            return showFullLicenseText ? license.getFullText(mContext) : license.getSummaryText(mContext);
        }

        return "";
    }

    private License resolveLicense(final String license) {
        if (license == null)
            return null;
        final String licenseName = license.toUpperCase(Locale.US).replaceAll(" ", "_").replaceAll("\\.", "_");
        License resolvedLicense = null;
        try {
            resolvedLicense = Licenses.valueOf(licenseName).getLicense();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage(), e);
            // TODO Implemented custom license resolver (mLicenseResolver.resolveCustomLicense(String name)
        }
        return resolvedLicense;
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

        final String licenseText = getNoticesHtml(mNoticesXml, mContext.getResources());

        final WebView webView = new WebView(mContext);
        webView.loadDataWithBaseURL(null, licenseText, "text/html", "utf-8", null);

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
