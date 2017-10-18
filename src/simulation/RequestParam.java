package simulation;

public class RequestParam {
	private String entry;
	private String callback;
	private String rsakt;
	private String checkpin;
	private String su;
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getRsakt() {
		return rsakt;
	}
	public void setRsakt(String rsakt) {
		this.rsakt = rsakt;
	}
	public String getCheckpin() {
		return checkpin;
	}
	public void setCheckpin(String checkpin) {
		this.checkpin = checkpin;
	}
	public String getSu() {
		return su;
	}
	public void setSu(String su) {
		this.su = su;
	}
	@Override
	public String toString() {
		return "entry=" + entry + "&callback=" + callback
				+ "&rsakt=" + rsakt + "&checkpin=" + checkpin + "&su=" + su;
	}
	
}
