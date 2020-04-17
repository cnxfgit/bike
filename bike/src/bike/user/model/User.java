package bike.user.model;

public class User {
	
	private String loginname;// 登录名
	private String loginpass;// 登录密码
	private String reloginpass;// 确认密码
	private String verifyCode;// 验证码
	private float money;// 余额
	private int status;// 租借状态 如果为0可租借如果不可租借显示已经租借的车辆id
	
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	
	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	
	@Override
	public String toString() {
		return "User [loginname=" + loginname + ", loginpass=" + loginpass + ", reloginpass=" + reloginpass
				+ ", verifyCode=" + verifyCode + ", money=" + money + ", status=" + status + "]";
	}
		
}
