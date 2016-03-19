package database;

import activity.SampleCVsFragment;

/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class PersonalInfo {
    //private variables
    int _id;
    String _fullname,_address,_dob,_contact,_email,_objective;

    //Empty constructor
    public PersonalInfo(){

    }

    public PersonalInfo(int id,String fname, String add,String dob,String cont,String email,String objective){
        this._id=id;
        this._fullname=fname;
        this._address=add;
        this._dob=dob;
        this._contact=cont;
        this._email=email;
        this._objective=objective;
    }

    public String get_objective() {
        return _objective;
    }

    public void set_objective(String _objective) {
        this._objective = _objective;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_fullname() {
        return _fullname;
    }

    public void set_fullname(String _fullname) {
        this._fullname = _fullname;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_dob() {
        return _dob;
    }

    public void set_dob(String _dob) {
        this._dob = _dob;
    }

    public String get_contact() {
        return _contact;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }
}
