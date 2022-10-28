package com.example.appnhatro.Models;

public class CommissionFeeModel {
    private String _id;
    private int _value;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get_value() {
        return _value;
    }

    public void set_value(int _value) {
        this._value = _value;
    }

    public CommissionFeeModel(String _id, int _value) {
        this._id = _id;
        this._value = _value;
    }
}
