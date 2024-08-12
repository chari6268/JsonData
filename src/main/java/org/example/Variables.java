package org.example;

public class Variables {
    private String subjectname,total,absent,percentage;

    public Variables() {
    }

    public Variables(String subjectname, String total, String absent, String percentage) {
        this.subjectname = subjectname;
        this.total = total;
        this.absent = absent;
        this.percentage = percentage;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
