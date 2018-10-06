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

public class SILOpenFontLicense11 extends License {

    @Override
    public String getName() {
        return "SIL Open Font License v1.1";
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return getContent(context, R.raw.sil_ofl_11_summary);
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return getContent(context, R.raw.sil_ofl_11_full);
    }

    @Override
    public String getVersion() {
        return "1.1";
    }

    @Override
    public String getUrl() {
        return "https://opensource.org/licenses/OFL-1.1";
    }

}
