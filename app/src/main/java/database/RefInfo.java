package database;

import java.sql.Ref;

import activity.SampleCVsFragment;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class RefInfo {
    //private variables
    int _id, _refid;
    String _rname, _pos, _contact, _org;

    //Empty constructor
    public RefInfo() {

    }

    public RefInfo(int id,  String rname, String pos, String contact,String org) {
        this._id = id;
//        this._refid = refid;
        this._rname = rname;
        this._pos = pos;
        this._contact = contact;
        this._org=org;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_refid() {
        return _refid;
    }

    public void set_refid(int _refid) {
        this._refid = _refid;
    }

    public String get_rname() {
        return _rname;
    }

    public void set_rname(String _rname) {
        this._rname = _rname;
    }

    public String get_pos() {
        return _pos;
    }

    public void set_pos(String _pos) {
        this._pos = _pos;
    }

    public String get_contact() {
        return _contact;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;
    }

    public String get_org() {
        return _org;
    }

    public void set_org(String _org) {
        this._org = _org;
    }
}


