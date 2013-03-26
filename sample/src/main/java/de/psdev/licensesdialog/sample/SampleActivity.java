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

package de.psdev.licensesdialog.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import de.psdev.licensesdialog.Licenses;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.Notice;
import de.psdev.licensesdialog.SingleLicenseDialog;
import de.psdev.licensesdialog.licenses.License;

public class SampleActivity extends Activity {
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onSingleClick(final View view) {
        final String name = "Philip Schiffer";
        final String url = "http://psdev.de";
        final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
        final License license = Licenses.APACHE_SOFTWARE_LICENSE_2_0.getLicense();
        final Notice notice = new Notice(name, url, copyright, license);
        new SingleLicenseDialog(this, notice).show();
    }

    public void onMultipleClick(final View view) {
        new LicensesDialog(this, R.xml.notices).show();
    }
}