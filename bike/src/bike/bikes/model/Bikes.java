package bike.bikes.model;

public class Bikes {

	private int bid;// �������
	private String bikename;// ������
	private int status; // ����״̬1������0�ѱ�����2��3����ά��
	private int zhan; //����վ����Ϣ

	public int getZhan() {
		return zhan;
	}

	public void setZhan(int zhan) {
		this.zhan = zhan;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getBikename() {
		return bikename;
	}

	public void setBikename(String bikename) {
		this.bikename = bikename;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Bikes [bid=" + bid + ", bikename=" + bikename + ", status=" + status + ", zhan=" + zhan + "]";
	}

}
