package bike.bikes.model;

public class Bikes {

	private int bid;// 单车编号
	private String bikename;// 单车名
	private int status; // 单车状态1可租用0已被租用2损坏3正在维修
	private int zhan; //所属站点信息

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
