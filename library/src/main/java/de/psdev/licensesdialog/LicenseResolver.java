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

import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.ISCLicense;
import de.psdev.licensesdialog.licenses.License;
import org.simpleframework.xml.transform.Transform;

import java.util.HashMap;

public class LicenseResolver implements Transform<License> {

    private static HashMap<String, License> sLicenses = new HashMap<String, License>(5);

    static {
        registerLicense(new ApacheSoftwareLicense20());
        registerLicense(new ISCLicense());
    }

    /**
     * Register an additional license.
     *
     * @param license the license to register
     */
    public static void registerLicense(final License license) {
        sLicenses.put(license.getName(), license);
    }

    @Override
    public License read(final String license) throws IllegalStateException {
        if (sLicenses.containsKey(license)) {
            return sLicenses.get(license);
        } else {
            throw new IllegalStateException("no such license available: " + license + ", did you forget to register it?");
        }
    }

    @Override
    public String write(final License value) {
        return value.getName();
    }
}
