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

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Notices implements Parcelable {

    private final List<Notice> mNotices;

    public Notices() {
        mNotices = new ArrayList<Notice>();
    }

    // Setter / Getter

    public void addNotice(final Notice notice) {
        mNotices.add(notice);
    }

    public List<Notice> getNotices() {
        return mNotices;
    }

    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeList(this.mNotices);
    }

    protected Notices(final Parcel in) {
        mNotices = new ArrayList<Notice>();
        in.readList(this.mNotices, Notice.class.getClassLoader());
    }

    public static Creator<Notices> CREATOR = new Creator<Notices>() {
        public Notices createFromParcel(final Parcel source) {
            return new Notices(source);
        }

        public Notices[] newArray(final int size) {
            return new Notices[size];
        }
    };
}
