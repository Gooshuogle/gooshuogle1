package cn.ac.imoe.finalexp_contact;

import java.util.Objects;

public class contacts {
    String dphone;
    String dname;
    String academy;
    String bestfriend;
    String gender;
    String owner;
    int id;

    public String getDphone() {
        return dphone;
    }

    public void setDphone(String dphone) {
        this.dphone = dphone;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getBestfriend() {
        return bestfriend;
    }

    public void setBestfriend(String bestfriend) {
        this.bestfriend = bestfriend;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        contacts contacts = (contacts) o;
        return id == contacts.id &&
                Objects.equals(dphone, contacts.dphone) &&
                Objects.equals(dname, contacts.dname) &&
                Objects.equals(academy, contacts.academy) &&
                Objects.equals(bestfriend, contacts.bestfriend) &&
                Objects.equals(gender, contacts.gender) &&
                Objects.equals(owner, contacts.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dphone, dname, academy, bestfriend, gender, owner, id);
    }

    @Override
    public String toString() {
        return "contacts{" +
                "dphone='" + dphone + '\'' +
                ", dname='" + dname + '\'' +
                ", academy='" + academy + '\'' +
                ", bestfriend='" + bestfriend + '\'' +
                ", gender='" + gender + '\'' +
                ", owner='" + owner + '\'' +
                ", id=" + id +
                '}';
    }
}

