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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import android.content.Context;

public abstract class License implements Serializable {

    private static final long serialVersionUID = 3100331505738956523L;

    public abstract String getName();

    public abstract String getSummaryText(final Context context);

    public abstract String getFullText(final Context context);

    public abstract String getVersion();

    public abstract String getUrl();

    //

    protected String getContent(final Context context, final int contentResourceId) {
        BufferedReader reader = null;
        try {
            final InputStream inputStream = context.getResources().openRawResource(contentResourceId);
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                return toString(reader);
            }
            throw new IOException("Error opening license file.");
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // Don't care.
                }
            }
        }
    }

    private String toString(final BufferedReader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append('\n');
        }
        return builder.toString();
    }

}
