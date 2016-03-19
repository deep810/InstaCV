package database;

import activity.SampleCVsFragment;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class EduInfo {
    //private variables
    int _id,_eduid;
    String _degree,_yop,_cgpa,_institute;

    //Empty constructor
    public EduInfo(){

    }

    public EduInfo(int id,int eduid, String degree,String yop,String cgpa,String institute){
        this._id=id;
        this._eduid=eduid;
        this._degree=degree;
        this._yop=yop;
        this._cgpa=cgpa;
        this._institute=institute;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_eduid() {
        return _eduid;
    }

    public void set_eduid(int _eduid) {
        this._eduid = _eduid;
    }

    public String get_degree() {
        return _degree;
    }

    public void set_degree(String _degree) {
        this._degree = _degree;
    }

    public String get_yop() {
        return _yop;
    }

    public void set_yop(String _yop) {
        this._yop = _yop;
    }

    public String get_cgpa() {
        return _cgpa;
    }

    public void set_cgpa(String _cgpa) {
        this._cgpa = _cgpa;
    }

    public String get_institute() {
        return _institute;
    }

    public void set_institute(String _institute) {
        this._institute = _institute;
    }
}
