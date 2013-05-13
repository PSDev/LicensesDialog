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

import java.util.Arrays;

import android.content.Context;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public final class NoticesHtmlBuilder {

    private final Context mContext;
    private Notices mNotices;
    private Notice mNotice;
    private String mStyle;
    private boolean mShowFullLicenseText;
    private String mHtmlHeader;
    private String[] mBeginLicencesTitles;
    private String mDividerHtml;

    public static NoticesHtmlBuilder create(final Context context) {
        return new NoticesHtmlBuilder(context);
    }

    private NoticesHtmlBuilder(final Context context) {
        mContext = context;
        mStyle = context.getResources().getString(R.string.notices_default_style);
        mShowFullLicenseText = false;
    }

    public NoticesHtmlBuilder setNotices(final Notices notices) {
        mNotices = notices;
        mNotice = null;
        return this;
    }

    public NoticesHtmlBuilder setNotice(final Notice notice) {
        mNotice = notice;
        mNotices = null;
        return this;
    }

    public NoticesHtmlBuilder setStyle(final String style) {
        mStyle = style;
        return this;
    }

    public NoticesHtmlBuilder setShowFullLicenseText(final boolean showFullLicenseText) {
        mShowFullLicenseText = showFullLicenseText;
        return this;
    }

    public NoticesHtmlBuilder setHtmlHeader(final String header) {
        mHtmlHeader = header;
        return this;
    }

    public NoticesHtmlBuilder setBeginLicencesTitle(String title) {
        mBeginLicencesTitles = new String[0];
        Arrays.fill(mBeginLicencesTitles, title);
        return this;
    }

    public NoticesHtmlBuilder setBeginLicencesTitles(String[] titles) {
        mBeginLicencesTitles = titles;
        return this;
    }

    public NoticesHtmlBuilder setDividerHtml(String dividerHtml) {
        mDividerHtml = dividerHtml;
        return this;
    }

    public String build() {
        final StringBuilder noticesHtmlBuilder = new StringBuilder(500);
        appendNoticesContainerStart(noticesHtmlBuilder);
        appendHtmlHeaderIfNotNull(noticesHtmlBuilder);
        if (mBeginLicencesTitles != null) {
            for (String title : mBeginLicencesTitles) {
                noticesHtmlBuilder.append("<h3>").append(title).append("</h3>");
            }
        }
        if (mNotice != null) {
            appendNoticeBlock(noticesHtmlBuilder, mNotice);
        } else if (mNotices != null) {
            for (final Notice notice : mNotices.getNotices()) {
                appendDividerHtmlIfNotNull(noticesHtmlBuilder);
                appendNoticeBlock(noticesHtmlBuilder, notice);
            }
        } else {
            throw new IllegalStateException("no notice(s) set");
        }
        appendNoticesContainerEnd(noticesHtmlBuilder);
        return noticesHtmlBuilder.toString();
    }

    private void appendHtmlHeaderIfNotNull(StringBuilder noticesHtmlBuilder) {
        if (mHtmlHeader != null) {
            noticesHtmlBuilder.append(mHtmlHeader);
        }
    }

    //

    private void appendNoticesContainerStart(final StringBuilder noticesHtmlBuilder) {
        noticesHtmlBuilder.append("<!DOCTYPE html><html><head>")
                .append("<style type=\"text/css\">").append(mStyle).append("</style>")
                .append("</head><body>");
    }

    private void appendNoticeBlock(final StringBuilder noticesHtmlBuilder, final Notice notice) {
        noticesHtmlBuilder.append("<ul><li>").append(notice.getName());
        final String currentNoticeUrl = notice.getUrl();
        if (currentNoticeUrl != null && currentNoticeUrl.length() > 0) {
            noticesHtmlBuilder.append(" (<a href=\"").append(currentNoticeUrl).append("\">").append(currentNoticeUrl).append("</a>)");
        }
        noticesHtmlBuilder.append("</li></ul>");
        noticesHtmlBuilder.append("<pre>");
        final String copyright = notice.getCopyright();
        if (copyright != null) {
            noticesHtmlBuilder.append(copyright).append("<br/><br/>");
        }
        noticesHtmlBuilder.append(getLicenseText(notice.getLicense())).append("</pre>");
    }

    private void appendDividerHtmlIfNotNull(final StringBuilder noticesHtmlBuilder) {
        if (mDividerHtml != null) {
            noticesHtmlBuilder.append(mDividerHtml);
        }
    }

    private void appendNoticesContainerEnd(final StringBuilder noticesHtmlBuilder) {
        noticesHtmlBuilder.append("</body></html>");
    }

    private String getLicenseText(final License license) {
        if (license != null) {
            return mShowFullLicenseText ? license.getFullText(mContext) : license.getSummaryText(mContext);
        }
        return "";
    }
}
