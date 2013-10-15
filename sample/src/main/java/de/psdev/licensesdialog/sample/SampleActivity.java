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

package de.psdev.licensesdialog.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.SingleLicenseDialog;
import de.psdev.licensesdialog.SingleLicenseDialogFragment;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;

public class SampleActivity extends FragmentActivity {

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }

    public void onSingleClick(final View view) {
        final String name = "LicensesDialog";
        final String url = "http://psdev.de";
        final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);
        new SingleLicenseDialog(this, notice, false).show();
    }

    public void onMultipleClick(final View view) {
        new LicensesDialog(this, R.raw.notices, false, false).show();
    }

    public void onMultipleIncludeOwnClick(final View view) {
        new LicensesDialog(this, R.raw.notices, false, true).show();
    }

    public void onSingleFragmentClick(final View view) {
        final String name = "LicensesDialog";
        final String url = "http://psdev.de";
        final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);
        final SingleLicenseDialogFragment fragment = SingleLicenseDialogFragment.newInstance(notice);
        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleFragmentClick(final View view) {
        final LicensesDialogFragment fragment = LicensesDialogFragment.newInstance(R.raw.notices, false);
        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleIncludeOwnFragmentClick(final View view) {
        final LicensesDialogFragment fragment = LicensesDialogFragment.newInstance(R.raw.notices, true);
        fragment.show(getSupportFragmentManager(), null);
    }
}