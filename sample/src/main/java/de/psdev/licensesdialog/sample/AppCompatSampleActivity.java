package de.psdev.licensesdialog.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.GnuLesserGeneralPublicLicense21;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class AppCompatSampleActivity extends AppCompatActivity {

    // ==========================================================================================================================
    // Android Lifecycle
    // ==========================================================================================================================

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
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
            .showAppCompat();
    }

    public void onMultipleClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .build()
            .showAppCompat();
    }

    public void onMultipleIncludeOwnClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .setIncludeOwnLicense(true)
            .build()
            .showAppCompat();
    }

    public void onMultipleProgrammaticClick(final View view) {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("Test 1", "http://example.org", "Example Person", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Test 2", "http://example.org", "Example Person 2", new GnuLesserGeneralPublicLicense21()));

        new LicensesDialog.Builder(this)
            .setNotices(notices)
            .setIncludeOwnLicense(true)
            .build()
            .showAppCompat();
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
            .setUseAppCompat(true)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleFragmentClick(final View view) throws Exception {
        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .setUseAppCompat(true)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onMultipleIncludeOwnFragmentClick(final View view) throws Exception {
        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .setShowFullLicenseText(false)
            .setIncludeOwnLicense(true)
            .setUseAppCompat(true)
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
            .setUseAppCompat(true)
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
            .showAppCompat();
    }

    public void onCustomThemeFragmentClick(final View view) throws Exception {
        final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
            .setNotices(R.raw.notices)
            .setShowFullLicenseText(false)
            .setIncludeOwnLicense(true)
            .setThemeResourceId(R.style.custom_theme)
            .setDividerColorRes(R.color.custom_divider_color)
            .setUseAppCompat(true)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    public void onCustomCssStyleClick(final View view) {
        new LicensesDialog.Builder(this)
            .setNotices(R.raw.notices)
            .setNoticesCssStyle(R.string.custom_notices_default_style)
            .build()
            .showAppCompat();
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
            .setUseAppCompat(true)
            .build();

        fragment.show(getSupportFragmentManager(), null);
    }

    private String getRGBAString(@ColorInt int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        float alpha = ((float) Color.alpha(color) / 255);
        return String.format(getString(R.string.rgba_background_format), red, green, blue, alpha);
    }
}
