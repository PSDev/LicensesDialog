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

package de.psdev.licensesdialog.licenses;

import android.content.Context;
import android.util.Log;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public abstract class License implements Serializable {

    private static final long serialVersionUID = 3100331505738956523L;

    public abstract String getName();

    public abstract String getSummaryText(final Context context);

    public abstract String getFullText(final Context context);

    public abstract String getVersion();

    public abstract String getUrl();

    //

    protected String getContent(final Context context, final int contentResourceId) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(contentResourceId);
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            Log.e("LicenseDialog", e.getMessage(), e);
            return "";
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
