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

public class MITLicense extends License {

    private static final long serialVersionUID = 5673599951781482594L;

	@Override
	public String getName() {
		return "MIT License";
	}

    @Override
    public String getSummaryText(final Context context) {
        return getContent(context, R.raw.mit_summary);
    }

    @Override
    public String getFullText(final Context context) {
        return getContent(context, R.raw.mit_full);
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public String getUrl() {
        return "http://opensource.org/licenses/MIT";
    }

}
