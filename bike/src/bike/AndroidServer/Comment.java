package bike.AndroidServer;

public class Comment {
	private String loginname;
    private String time;
    private String comment;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "loginname='" + loginname + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Comment(){

    }

    public Comment(String loginname,String time,String comment){
        this.loginname = loginname;
        this.time = time;
        this.comment = comment;
    }
}
