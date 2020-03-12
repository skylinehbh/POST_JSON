package com.skyline.post_json;

public class Data_code {
    private String id_;
    private String code_;
    private String time_;
    public Data_code() {

    }
    public Data_code (String id, String code_, String time_) {
        this.id_ = id ;
        this.code_=code_;
        this.time_=time_ ;
    }
    public String getid_() {
        return id_;
    }

    public void setid_(String id) {
        this.id_ = id_;
    }

    public String code_() {
        return code_;
    }

    public void code_(String code_) {
        this.code_ = code_;
    }

    public String time_() {
        return time_;
    }

    public void time_(String time_) {
        this.time_ = time_;
    }
}
