package com.example.appnhatro.Models;

public class AddressModel {
    private String _id;
    private String _postID;
    private String _addressDetails;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_postID() {
        return _postID;
    }

    public void set_postID(String _postID) {
        this._postID = _postID;
    }

    public String get_addressDetails() {
        return _addressDetails;
    }

    public void set_addressDetails(String _addressDetails) {
        this._addressDetails = _addressDetails;
    }

    public String get_distric() {
        return _distric;
    }

    public void set_distric(String _distric) {
        this._distric = _distric;
    }

    public AddressModel(String _id, String _postID, String _addressDetails, String _distric) {
        this._id = _id;
        this._postID = _postID;
        this._addressDetails = _addressDetails;
        this._distric = _distric;
    }

    private String _distric;
}
