package database;

import activity.SampleCVsFragment;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class CurrInfo {
    //private variables
    int _id,_currid;
    String _name;

    //Empty constructor
    public CurrInfo(){

    }


    public CurrInfo(int id, String name) {
        this._id = id;
//        this._currid = currid;
        this._name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_currid() {
        return _currid;
    }

    public void set_currid(int _currid) {
        this._currid = _currid;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
