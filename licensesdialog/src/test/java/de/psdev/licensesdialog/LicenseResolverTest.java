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

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import de.psdev.licensesdialog.licenses.License;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LicenseResolverTest {

    private static final String TEST_LICENSE_NAME = "TestLicense";

    @Before
    public void setUp() {
        LicenseResolver.registerDefaultLicenses();
    }

    @Test
    public void testRegisterLicense() {
        LicenseResolver.registerLicense(new TestLicense());
        final License license = LicenseResolver.read(TEST_LICENSE_NAME);
        assertNotNull(license);
        assertEquals(TEST_LICENSE_NAME, license.getName());
    }

    @Test(expected = IllegalStateException.class)
    public void testReadUnknownLicense() {
        LicenseResolver.read(TEST_LICENSE_NAME);
    }

    @Test
    public void testReadKnownLicense() {
        final License license = LicenseResolver.read("MIT License");
        assertNotNull(license);
        assertEquals("MIT License", license.getName());
    }

    // Inner classes

    private static class TestLicense extends License {

        @Override
        public String getName() {
            return TEST_LICENSE_NAME;
        }

        @Override
        public String readSummaryTextFromResources(final Context context) {
            return "Testing license";
        }

        @Override
        public String readFullTextFromResources(final Context context) {
            return "Full testing license";
        }

        @Override
        public String getVersion() {
            return "1.0";
        }

        @Override
        public String getUrl() {
            return "https://example.org/";
        }
    }
}
