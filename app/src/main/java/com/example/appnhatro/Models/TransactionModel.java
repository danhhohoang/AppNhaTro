package com.example.appnhatro.Models;

public class TransactionModel {

    private String _id;
    private String _userTenantID;
    private String _userLandlordID;
    private String _postID;
    private String _commissionFeeID;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_userTenantID() {
        return _userTenantID;
    }

    public void set_userTenantID(String _userTenantID) {
        this._userTenantID = _userTenantID;
    }

    public String get_userLandlordID() {
        return _userLandlordID;
    }

    public void set_userLandlordID(String _userLandlordID) {
        this._userLandlordID = _userLandlordID;
    }

    public String get_postID() {
        return _postID;
    }

    public void set_postID(String _postID) {
        this._postID = _postID;
    }

    public String get_commissionFeeID() {
        return _commissionFeeID;
    }

    public void set_commissionFeeID(String _commissionFeeID) {
        this._commissionFeeID = _commissionFeeID;
    }

    public TransactionModel(String _id, String _userTenantID, String _userLandlordID, String _postID, String _commissionFeeID) {
        this._id = _id;
        this._userTenantID = _userTenantID;
        this._userLandlordID = _userLandlordID;
        this._postID = _postID;
        this._commissionFeeID = _commissionFeeID;
    }
}
