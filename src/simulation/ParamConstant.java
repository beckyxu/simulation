package simulation;

public enum ParamConstant {
	entry("weibo"),
	callback("sinaSSOController.preloginCallBack"),
	rsakt("mod"),
	checkpin("1"),
	client("ssologin.js(v1.4.18)");
	private String vaule;
	private ParamConstant(String value) {
		this.vaule = value;
	}
	public String getVaule() {
		return vaule;
	}
	public void setVaule(String vaule) {
		this.vaule = vaule;
	}
	public static  String allToString() {
		StringBuffer sb = new StringBuffer();
		for(ParamConstant param:ParamConstant.values()) {
			sb.append(param.name()+"="+param.getVaule()+"&");
		}
		return sb.toString();
	}
}
