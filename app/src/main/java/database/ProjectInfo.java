package database;

import activity.SampleCVsFragment;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class ProjectInfo {
    //private variables
    int _id, _proid;
    String _title,_location,_time,_desig,_desc;

    //Empty constructor
    public ProjectInfo(){

    }

    public ProjectInfo(int id, String title, String location, String time, String desig, String desc) {
//        this._proid = proid;
        this._id = id;
        this._title = title;
        this._location = location;
        this._time = time;
        this._desc = desc;
        this._desig = desig;
    }

    public ProjectInfo(int id,int proid, String title, String location, String time, String desig, String desc) {
        this._proid = proid;
        this._id = id;
        this._title = title;
        this._location = location;
        this._time = time;
        this._desc = desc;
        this._desig = desig;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_proid() {
        return _proid;
    }

    public void set_proid(int _proid) {
        this._proid = _proid;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_desig() {
        return _desig;
    }

    public void set_desig(String _desig) {
        this._desig = _desig;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }
}
