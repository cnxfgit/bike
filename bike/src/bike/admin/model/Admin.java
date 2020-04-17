package bike.admin.model;

public class Admin {

	private String adminname;
	private String adminpass;

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getAdminpass() {
		return adminpass;
	}

	public void setAdminpass(String adminpass) {
		this.adminpass = adminpass;
	}

	@Override
	public String toString() {
		return "Admin [adminname=" + adminname + ", adminpass=" + adminpass + "]";
	}
}
