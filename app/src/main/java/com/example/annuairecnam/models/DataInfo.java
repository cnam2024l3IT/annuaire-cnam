package com.example.annuairecnam.models;

import android.os.Parcelable;

public abstract class DataInfo implements Parcelable {

    protected long _id;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

}
