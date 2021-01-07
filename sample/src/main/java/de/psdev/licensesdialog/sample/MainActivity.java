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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.GnuLesserGeneralPublicLicense21;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class MainActivity extends AppCompatActivity {

    // ==========================================================================================================================
    // Android Lifecycle
    // ==========================================================================================================================

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // ==========================================================================================================================
    // Public API
    // ==========================================================================================================================

    public void onSingleClick(final View view) {
        final String name = "LicensesDialog";
        final String url = "http://psdev.de";
        final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);
        new LicensesDialog.Builder(this)
            .setNotices(notice)
            .build()
            .show();
    }

    public void onMultipleClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .build()
            .show();
    }

    public void onMultipleIncludeOwnClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .setIncludeOwnLicense(true)
            .build()
            .show();
    }

    public void onMultipleProgrammaticClick(final View view) {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("Test 1", "http://example.org", "Example Person", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Test 2", "http://example.org", "Example Person 2", new GnuLesserGeneralPublicLicense21()));

        new LicensesDialog.Builder(this)
            .setNotices(notices)
            .setIncludeOwnLicense(true)
            .build()
            .show();
    }

    public void onSingleFragmentClick(final View view) {
        final String name = "LicensesDialog";
        final String url = "http://psdev.de";
        final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);

        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotice(notice)
            .setIncludeOwnLicense(false)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleFragmentClick(final View view) throws Exception {
        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleIncludeOwnFragmentClick(final View view) throws Exception {
        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .setShowFullLicenseText(false)
            .setIncludeOwnLicense(true)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleProgrammaticFragmentClick(final View view) {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("Test 1", "http://example.org", "Example Person", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Test 2", "http://example.org", "Example Person 2", new GnuLesserGeneralPublicLicense21()));

        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(notices)
            .setShowFullLicenseText(false)
            .setIncludeOwnLicense(true)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onCustomThemeClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .setIncludeOwnLicense(true)
            .setThemeResourceId(R.style.custom_theme)
            .setDividerColorId(R.color.custom_divider_color)
            .build()
            .show();
    }

    public void onCustomThemeFragmentClick(final View view) throws Exception {
        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .setShowFullLicenseText(false)
            .setIncludeOwnLicense(true)
            .setThemeResourceId(R.style.custom_theme)
            .setDividerColorRes(R.color.custom_divider_color)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onCustomCssStyleClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .setNoticesCssStyle(R.string.custom_notices_default_style)
            .build()
            .show();
    }

    public void onCustomCssStyleFragmentClick(final View view) throws Exception {
        String formatString = getString(R.string.custom_notices_format_style);
        String pBg = getRGBAString(Color.parseColor("#9E9E9E"));
        String bodyBg = getRGBAString(Color.parseColor("#424242"));
        String preBg = getRGBAString(Color.parseColor("#BDBDBD"));
        String liColor = "color: #ffffff";
        String linkColor = "color: #1976D2";

        String style = String.format(formatString, pBg, bodyBg, preBg, liColor, linkColor);

        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .setNoticesCssStyle(style)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onSingleDisableDarkModeClick(final View view) {
        final String name = "LicensesDialog";
        final String url = "http://psdev.de";
        final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
        final License license = new ApacheSoftwareLicense20();
        final Notice notice = new Notice(name, url, copyright, license);
        new LicensesDialog.Builder(this)
                .setNotices(notice)
                .setEnableDarkMode(false)
                .build()
                .show();
    }

    private String getRGBAString(@ColorInt int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        float alpha = ((float) Color.alpha(color) / 255);
        return String.format(getString(R.string.rgba_background_format), red, green, blue, alpha);
    }
}