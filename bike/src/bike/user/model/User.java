package bike.user.model;

public class User {
	
	private String loginname;// ��¼��
	private String loginpass;// ��¼����
	private String reloginpass;// ȷ������
	private String verifyCode;// ��֤��
	private float money;// ���
	private int status;// ���״̬ ���Ϊ0�����������������ʾ�Ѿ����ĳ���id
	
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
