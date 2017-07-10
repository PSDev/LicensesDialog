/*
 * Copyright 2014 Peter Heisig
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

public class CreativeCommonsAttributionShareAlike30Unported extends License {

    private static final long serialVersionUID = -1221518691431383957L;

    @Override
    public String getName() {
        return "Creative Commons Attribution-Share Alike 3.0 Unported";
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return getContent(context, R.raw.ccbysa_30_summary);
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return getContent(context, R.raw.ccbysa_30_full);
    }

    @Override
    public String getVersion() {
        return "3.0";
    }

    @Override
    public String getUrl() {
        return "https://creativecommons.org/licenses/by-sa/3.0/";
    }

}
