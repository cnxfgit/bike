package bike.wallet.walletservce;

import java.util.List;
import java.util.Map;

import bike.wallet.walletdao.WalletDao;

public class WalletService {
	private WalletDao walletDao = new WalletDao();
	
	public int findbikestatus(String loginname) {
		try {
			return walletDao.findmoney(loginname);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Map<String, Object>> findrecord(String loginname) {
		try {
			return walletDao.findrecord(loginname);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void setmoney(String loginname,int fmoney) {
		try {
			walletDao.setmoney(loginname, fmoney);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void changeispay(String loginname,int ispay) {
		try {
			walletDao.changeispay(loginname, ispay);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void recharge(String loginname,int money) {
		try {
			walletDao.recharge(loginname, money);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
