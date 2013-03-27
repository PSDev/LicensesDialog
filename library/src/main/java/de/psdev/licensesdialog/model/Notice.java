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

package de.psdev.licensesdialog.model;

import de.psdev.licensesdialog.licenses.License;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root
public class Notice implements Serializable {
    private static final long serialVersionUID = -6257913944601445939L;

    @Element(name = "name")
    private String mName;
    @Element(name = "url", required = false)
    private String mUrl;
    @Element(name = "copyright", required = false)
    private String mCopyright;
    @Element(name = "license")
    private License mLicense;

    //

    public Notice() {
    }

    public Notice(final String name, final String url, final String copyright, final License license) {
        mName = name;
        mUrl = url;
        mCopyright = copyright;
        mLicense = license;
    }

    // Setter / Getter

    public void setName(final String name) {
        mName = name;
    }

    public void setUrl(final String url) {
        mUrl = url;
    }

    public void setCopyright(final String copyright) {
        mCopyright = copyright;
    }

    public void setLicense(final License license) {
        mLicense = license;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public License getLicense() {
        return mLicense;
    }
}
