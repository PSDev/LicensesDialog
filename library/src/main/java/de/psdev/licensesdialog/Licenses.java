package de.psdev.licensesdialog;

import de.psdev.licensesdialog.licenses.ApacheLicense2;
import de.psdev.licensesdialog.licenses.ISCLicense;
import de.psdev.licensesdialog.licenses.License;

public enum Licenses {
    APACHE_SOFTWARE_LICENSE_2_0(new ApacheLicense2()),
    ISC_LICENSE(new ISCLicense());


    private final License mLicense;

    Licenses(final License license) {
        mLicense = license;
    }

    public License getLicense() {
        return mLicense;
    }
}
