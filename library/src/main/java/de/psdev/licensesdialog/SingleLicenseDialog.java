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

import android.content.Context;
import de.psdev.licensesdialog.model.Notice;

public class SingleLicenseDialog extends LicensesDialog {

    public SingleLicenseDialog(final Context context, final Notice notice, final boolean showFullLicenseText) {
        super(context, getTitleText(context), getLicenseText(context, notice, showFullLicenseText), getCloseText(context));
    }

    public SingleLicenseDialog(final Context context, final String titleText, final String licensesText, final String closeText) {
        super(context, titleText, licensesText, closeText);
    }

    private static String getTitleText(final Context context) {
        return context.getString(R.string.notices_title);
    }

    private static String getLicenseText(final Context context, final Notice notice, final boolean showFullLicenseText) {
        final String defaultStyle = context.getString(R.string.notices_default_style);
        return NoticesHtmlBuilder.create(context).setNotice(notice).setShowFullLicenseText(showFullLicenseText).setStyle(defaultStyle).build();
    }

    private static String getCloseText(final Context context) {
        return context.getString(R.string.notices_close);
    }


}
