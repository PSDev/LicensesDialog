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

package de.psdev.licensesdialog.licenses;

import android.content.Context;
import de.psdev.licensesdialog.R;

public class ISCLicense extends License {
    private static final long serialVersionUID = -4636435634132169860L;

    private String cachedSummaryText = null;
    private String cachedFullText = null;

    @Override
    public String getName() {
        return "ISC License";
    }

    @Override
    public String getSummaryText(final Context context) {
        if (cachedSummaryText == null) {
            cachedSummaryText = getContent(context, R.raw.isc_summary);
        }

        return cachedSummaryText;
    }

    @Override
    public String getFullText(final Context context) {
        if (cachedFullText == null) {
            cachedFullText = getContent(context, R.raw.isc_full);
        }
        return cachedFullText;
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public String getUrl() {
        return "http://opensource.org/licenses/isc-license.txt";
    }
}
