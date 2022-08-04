package com.timizatechnologies.crm.models

import android.os.Parcel
import android.os.Parcelable

class Task() : Parcelable {
    var tid: String? = null
    var title: String? = null
    var tdate: String? = null
    var tdeadline: String? = null
    var tdesc: String? = null
    var tstatus: String? = null

    constructor(parcel: Parcel) : this() {
        tid = parcel.readString()
        title = parcel.readString()
        tdate = parcel.readString()
        tdeadline = parcel.readString()
        tdesc = parcel.readString()
        tstatus = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tid)
        parcel.writeString(title)
        parcel.writeString(tdate)
        parcel.writeString(tdeadline)
        parcel.writeString(tdesc)
        parcel.writeString(tstatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
