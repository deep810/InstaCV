package database;

/**
 * Created by vishwashrisairm on 19/3/16.
 */
public class ItemStatus {
    int _item_id,_personalstatus,_edustatus,_prostatus,_skillstatus,_refstatus,_excurstatus;
    String title;

    public ItemStatus(){

    }

    public ItemStatus(String title,int ps,int es,int prs,int ss,int ref,int exc){
       // this._item_id=id;
        this.title=title;
        this._personalstatus=ps;
        this._edustatus=es;
        this._prostatus=ps;
        this._skillstatus=ss;
        this._refstatus=ref;
        this._excurstatus=exc;

    }
    public ItemStatus(int id,String title,int ps,int es,int prs,int ss,int ref,int exc){
        this._item_id=id;
        this.title=title;
        this._personalstatus=ps;
        this._edustatus=es;
        this._prostatus=ps;
        this._skillstatus=ss;
        this._refstatus=ref;
        this._excurstatus=exc;

    }

    public int get_item_id() {
        return _item_id;
    }

    public void set_item_id(int _item_id) {
        this._item_id = _item_id;
    }

    public int get_personalstatus() {
        return _personalstatus;
    }

    public void set_personalstatus(int _personalstatus) {
        this._personalstatus = _personalstatus;
    }

    public int get_edustatus() {
        return _edustatus;
    }

    public void set_edustatus(int _edustatus) {
        this._edustatus = _edustatus;
    }

    public int get_prostatus() {
        return _prostatus;
    }

    public void set_prostatus(int _prostatus) {
        this._prostatus = _prostatus;
    }

    public int get_skillstatus() {
        return _skillstatus;
    }

    public void set_skillstatus(int _skillstatus) {
        this._skillstatus = _skillstatus;
    }

    public int get_refstatus() {
        return _refstatus;
    }

    public void set_refstatus(int _refstatus) {
        this._refstatus = _refstatus;
    }

    public int get_excurstatus() {
        return _excurstatus;
    }

    public void set_excurstatus(int _excurstatus) {
        this._excurstatus = _excurstatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
