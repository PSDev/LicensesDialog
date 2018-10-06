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

import java.util.ArrayList;
import java.util.List;

public class Notices implements Parcelable {

    private final List<Notice> mNotices;

    public Notices() {
        mNotices = new ArrayList<>();
    }

    // Setter / Getter

    public void addNotice(final Notice notice) {
        mNotices.add(notice);
    }

    public List<Notice> getNotices() {
        return mNotices;
    }

    // Parcelable

    protected Notices(final Parcel in) {
        mNotices = in.createTypedArrayList(Notice.CREATOR);
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mNotices);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notices> CREATOR = new Creator<Notices>() {
        @Override
        public Notices createFromParcel(final Parcel in) {
            return new Notices(in);
        }

        @Override
        public Notices[] newArray(final int size) {
            return new Notices[size];
        }
    };

}
