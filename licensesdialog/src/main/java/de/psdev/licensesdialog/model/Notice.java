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

package de.psdev.licensesdialog.model;

import android.os.Parcel;
import android.os.Parcelable;
import de.psdev.licensesdialog.licenses.License;

public class Notice implements Parcelable {

    private String mName;
    private String mUrl;
    private String mCopyright;
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

    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mName);
        dest.writeString(mUrl);
        dest.writeString(mCopyright);
        dest.writeSerializable(mLicense);
    }

    private Notice(final Parcel in) {
        mName = in.readString();
        mUrl = in.readString();
        mCopyright = in.readString();
        mLicense = (License) in.readSerializable();
    }

    public static Creator<Notice> CREATOR = new Creator<Notice>() {
        public Notice createFromParcel(final Parcel source) {
            return new Notice(source);
        }

        public Notice[] newArray(final int size) {
            return new Notice[size];
        }
    };
}
