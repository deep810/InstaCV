package database;


/**
 * Created by vishwashrisairm on 16/3/16.
 */
public class SkillsInfo {
    //private variables
    int _id,_skillid;
    String _nameofskill, _prof;

    //Empty constructor
    public SkillsInfo() {

    }

    public SkillsInfo(int id, String prof, String nameofskill) {
        this._id = id;
//        this._skillid = skillid;
        this._prof = prof;
        this._nameofskill = nameofskill;
    }

    public SkillsInfo(int id,int skillid, String prof, String nameofskill) {
        this._id = id;
        this._skillid = skillid;
        this._prof = prof;
        this._nameofskill = nameofskill;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_skillid() {
        return _skillid;
    }

    public void set_skillid(int _skillid) {
        this._skillid = _skillid;
    }

    public String get_nameofskill() {
        return _nameofskill;
    }

    public void set_nameofskill(String _nameofskill) {
        this._nameofskill = _nameofskill;
    }

    public String get_prof() {
        return _prof;
    }

    public void set_prof(String _prof) {
        this._prof = _prof;
    }
}