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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public abstract class License implements Serializable {

    private static final long serialVersionUID = 3100331505738956523L;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private String mCachedSummaryText = null;
    private String mCachedFullText = null;

    public abstract String getName();

    public abstract String readSummaryTextFromResources(final Context context);

    public abstract String readFullTextFromResources(final Context context);

    public abstract String getVersion();

    public abstract String getUrl();

    //

    public final String getSummaryText(final Context context) {
        if (mCachedSummaryText == null) {
            mCachedSummaryText = readSummaryTextFromResources(context);
        }

        return mCachedSummaryText;
    }

    public final String getFullText(final Context context) {
        if (mCachedFullText == null) {
            mCachedFullText = readFullTextFromResources(context);
        }

        return mCachedFullText;
    }

    protected String getContent(final Context context, final int contentResourceId) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(contentResourceId), "UTF-8"))) {
            return toString(reader);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toString(final BufferedReader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(LINE_SEPARATOR);
        }
        return builder.toString();
    }

}
