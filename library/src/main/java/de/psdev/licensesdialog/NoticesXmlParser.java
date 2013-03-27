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

package de.psdev.licensesdialog;

import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notices;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

import java.io.InputStream;

public class NoticesXmlParser {

    public static Notices parse(final InputStream inputStream) throws Exception {
        final Serializer serializer = new Persister(new AnnotationStrategy(), new Matcher() {
            @Override
            public Transform match(final Class type) throws Exception {
                if (type.equals(License.class)) {
                    return new LicenseResolver();
                } else {
                    return null;
                }
            }
        });
        return serializer.read(Notices.class, inputStream);
    }
}
