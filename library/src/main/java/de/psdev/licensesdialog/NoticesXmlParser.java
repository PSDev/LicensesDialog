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

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public final class NoticesXmlParser {

    private NoticesXmlParser() {
    }

    public static Notices parse(final InputStream inputStream) throws Exception {
        try {
            final XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return parse(parser);
        } finally {
            inputStream.close();
        }
    }

    private static Notices parse(final XmlPullParser parser) throws IOException, XmlPullParserException {
        final Notices notices = new Notices();
        parser.require(XmlPullParser.START_TAG, null, "notices");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            final String name = parser.getName();
            // Starts by looking for the entry tag
            if ("notice".equals(name)) {
                notices.addNotice(readNotice(parser));
            } else {
                skip(parser);
            }
        }
        return notices;
    }

    private static Notice readNotice(final XmlPullParser parser) throws IOException,
        XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "notice");
        String name = null;
        String url = null;
        String copyright = null;
        License license = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            final String element = parser.getName();
            if ("name".equals(element)) {
                name = readName(parser);
            } else if ("url".equals(element)) {
                url = readUrl(parser);
            } else if ("copyright".equals(element)) {
                copyright = readCopyright(parser);
            } else if ("license".equals(element)) {
                license = readLicense(parser);
            } else {
                skip(parser);
            }
        }
        return new Notice(name, url, copyright, license);
    }

    private static String readName(final XmlPullParser parser) throws IOException, XmlPullParserException {
        return readTag(parser, "name");
    }

    private static String readUrl(final XmlPullParser parser) throws IOException, XmlPullParserException {
        return readTag(parser, "url");
    }

    private static String readCopyright(final XmlPullParser parser) throws IOException, XmlPullParserException {
        return readTag(parser, "copyright");
    }

    private static License readLicense(final XmlPullParser parser) throws IOException, XmlPullParserException {
        final String license = readTag(parser, "license");
        return LicenseResolver.read(license);
    }

    private static String readTag(final XmlPullParser parser, final String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tag);
        final String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tag);
        return title;
    }

    private static String readText(final XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(final XmlPullParser parser) {
    }
}
