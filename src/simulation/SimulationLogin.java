package simulation;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.text.html.HTML;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
    
public class SimulationLogin {
	 private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',  
         '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
	 
	 
 private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',  
         '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
	
	public static void main (String args[]) throws Exception{
		String userName = "18826274794";
		String password = "5313916";
		/*userName = getEncodeUserName(userName);
		encodePassword(resolveJson(prelogin( userName)),password);*/
		login(userName,password);

		/* 
		String responseBody ="";
		CloseableHttpClient httpclient = HttpClients.createDefault();  
	        try {
	        	//点击用户名之后会发出一个请求
	            HttpGet httpget = new HttpGet("http://login.sina.com.cn/sso/prelogin.php"+"?"+
	            				"entry=weibo&callback=sinaSSOController.preloginCallBack&"
	            				+ "rsakt=mod&checkpin=1&client=ssologin.js(v1.4.18)&su="+userName+"&_="+System.currentTimeMillis());

	            System.out.println("Executing request " + httpget.getRequestLine());

	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

	                @Override
	                public String handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        return entity != null ? EntityUtils.toString(entity) : null;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }

	            };
	            responseBody = httpclient.execute(httpget, responseHandler);
	            System.out.println("----------------------------------------");
	            System.out.println(responseBody);
	        } finally {
	            httpclient.close();
	        }
	      //对password进行编码
	        int pos = responseBody.indexOf("(");
	        responseBody = responseBody.substring(pos+1);
	        responseBody = responseBody.substring(0, responseBody.length()-1);
	   	    JSONObject json_test = JSONObject.fromObject(responseBody); 
	   	    
	   	    Integer serverTime = (Integer) json_test.get("servertime");
	   	    String nonce = (String) json_test.get("nonce");
	     	String rsakv = (String) json_test.get("rsakv");
	     	String pubkey = (String) json_test.get("pubkey");
	   	    
	     	
	   	    System.out.println(json_test);
	   	    System.out.println("serverTime:"+serverTime+" nonce"+nonce);
	   	    
	   	    */
	    }	
	
    public static void login(String userName,String password) throws Exception {  
		userName = getEncodeUserName(userName);
		
        String url = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.19)&_=" +   
                System.currentTimeMillis();  
        String content = prelogin(userName);  
        Map<String,String> paramMap = resolveJson(content);  
        password =  encodePassword(paramMap,password);
        
        
        String servertime = paramMap.get("servertime").toString();  
        String nonce = paramMap.get("nonce").toString();  
        String rsakv = paramMap.get("rsakv").toString();  
          
        Map<String,Object> params = new HashMap<String,Object>();  
        params.put("entry", "account");  
        params.put("gateway", "1");  
        params.put("from", "");  
        params.put("savestate", "30");  
        params.put("qrcode_flag", "true");  
        params.put("useticket", "1");  
        params.put("pagerefer", "http://my.sina.com.cn/");  
        params.put("vsnf", "1");  
        params.put("su", userName);  
        params.put("service", "sso");  
        params.put("servertime", servertime);  
        params.put("nonce", nonce);  
        params.put("pwencode", "rsa2");  
        params.put("rsakv", rsakv);  
        params.put("sp",password);  
        params.put("sr","1366*768");  
        params.put("encoding", "UTF-8");  
        params.put("cdult", "3");  
        params.put("domain", "weibo.com");  
        params.put("prelt", "154");  
        params.put("url", "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");  
        params.put("domain", "sina.com.cn");  
        params.put("prelt", "1117");  
        params.put("returntype", "TEXT");  
          
      //1.获得一个httpclient对象
			CloseableHttpClient httpclient = HttpClients.createDefault();
			//2.生成一个get请求
			HttpPost post = new HttpPost(url);
			
			 List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	          
		        Set<String> keySet = params.keySet();  
		        for(String key : keySet) {  
		            nvps.add(new BasicNameValuePair(key, (String)params.get(key)));  
		        }  
		        try {  
		            post.setEntity(new UrlEncodedFormEntity(nvps));  
		        } catch (UnsupportedEncodingException e) {  
		            e.printStackTrace();  
		        }  
			
			//3.执行get请求并返回结果
			CloseableHttpResponse response = httpclient.execute(post); 				
			HttpEntity entity = null;
			try {
				System.out.println("登陆login response:"+ response);
				 entity = response.getEntity();
				 
				Header[] headerArray = response.getAllHeaders();
				String str = "";
			        for(Header header : headerArray)
			        {
			        	str+=header.getValue()+";";
			        	if("Set-Cookie".equals(header.getName())) {
			        		HeaderElement[] headerElementArray = header.getElements();
			        		 for(HeaderElement headerElement : headerElementArray)
					            {

			        			// str+=headerElement.getName()+":"+headerElement.getValue()+";";
					               
					            }			        		
			        		
			        	}
			        	
			            System.out.println("--Header-----------------------------------------");
			            System.out.println("----Key: " + header.getName());
			            System.out.println("----RawValue: " + header.getValue());
			            HeaderElement[] headerElementArray = header.getElements();
			            for(HeaderElement headerElement : headerElementArray)
			            {
			                System.out.print("------Value: " + headerElement.getName());
			                if(null != headerElement.getValue())
			                {
			                    System.out.println("  <-|->  " + headerElement.getValue());
			                }
			                else
			                {
			                    System.out.println();
			                }
			                NameValuePair[] nameValuePairArray = headerElement.getParameters();
			                for(NameValuePair nameValuePair : nameValuePairArray)
			                {
			                    System.out.println("------Parameter: " + nameValuePair.getName() + "  <-|->  " + nameValuePair.getValue());
			                }
			            }
			        }
			        String sub = str.replaceAll(".*;SUB=(.*);SUBP=.*", "$1");  
			        
			        System.out.println("--str-----------------------------------------"+str);
		           System.out.println("--sub-----------------------------------------"+sub);
		            
		           
				String res = EntityUtils.toString(entity);
				System.out.println("登陆login res:"+ res);
				 String wenzhang = "http://weibo.com/wenzhang626";  
				 HttpGet request = new HttpGet(wenzhang);//这里发送get请求

			        request.setHeader("Cookie", str);

			     
			       response = httpclient.execute(request); 	
			       entity = response.getEntity();
			       res = EntityUtils.toString(entity);
			       FileOutputStream fis  =new FileOutputStream("d://test");
			       fis.write(res.getBytes());
			       fis.close();
			       System.out.println("登陆get res:"+ res);
			} finally {
			    response.close();
			} 				
        //System.out.println(json);  
    } 
	
	public static String encodePassword( Map<String,String> json,String password) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String message = json.get("servertime") + "\t" + json.get("nonce") + "\n" + password;  
		password = rsa(json.get("pubkey"),"10001",message);
		System.out.println("password:"+password);
		return password;
	}
	
	public static String rsa(String pubkey, String exponentHex, String pwd) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException  {  
        KeyFactory factory = KeyFactory.getInstance("RSA");  
  
        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(pubkey, 16), new BigInteger(exponentHex, 16));  
  
        //创建公钥  
        RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);  
        Cipher enc = Cipher.getInstance("RSA");  
        enc.init(Cipher.ENCRYPT_MODE, pub);  
  
        byte[] encryptedContentKey = enc.doFinal(pwd.getBytes("UTF-8"));
        return new String( encodeHex(encryptedContentKey));  
    }  
	
	public static Map<String,String> resolveJson(String response) {
		 	JSONObject json_test = JSONObject.fromObject(response); 	
		 	Map<String,String> map = new HashMap<String,String>();
		 	map.put("servertime", json_test.get("servertime").toString());
		 	map.put("nonce", json_test.get("nonce").toString());
		 	map.put("rsakv", json_test.get("rsakv").toString());
		 	map.put("pubkey", json_test.get("pubkey").toString());
		 	System.out.println("map:"+map.toString());	        
		 	return map;	     	
	}
	
	 public static String prelogin(String userName) throws Exception {  
		 String str = "http://login.sina.com.cn/sso/prelogin.php"+"?"+
 				"entry=weibo&callback=sinaSSOController.preloginCallBack&"
 				+ "rsakt=mod&checkpin=1&client=ssologin.js(v1.4.18)&su="+userName+"&_="+System.currentTimeMillis();
 				//1.获得一个httpclient对象
 				CloseableHttpClient httpclient = HttpClients.createDefault();
 				//2.生成一个get请求
 				HttpGet httpget = new HttpGet(str);
 				//3.执行get请求并返回结果
 				CloseableHttpResponse response = httpclient.execute(httpget); 				
 				HttpEntity entity = null;
 				String replace = null;
 				try {
 					 entity = response.getEntity();
 					String res = EntityUtils.toString(entity);
 					System.out.println("res:"+ res);
 	 				replace = res.replaceAll("sinaSSOController.preloginCallBack\\((.*)\\)", "$1");
 	 				System.out.println("replace:"+ replace);
 				} finally {
 				    response.close();
 				} 				
	        return replace;  
	    }  
	
	public static String getEncodeUserName(String userName) {
		try {
		    userName = Base64.encodeBase64String(URLEncoder.encode(userName,"UTF-8").getBytes()).toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("userName:"+userName);
		return userName;
	}
	
	 protected static char[] encodeHex(final byte[] data, final char[] toDigits) {  
	        final int l = data.length;  
	        final char[] out = new char[l << 1];  
	          
	        for (int i = 0, j = 0; i < l; i++) {  
	            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];  
	            out[j++] = toDigits[0x0F & data[i]];  
	        }  
	        return out;  
	    }  
	  
	    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {  
	        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);  
	    }  
	  
	    public static char[] encodeHex(final byte[] data) {  
	        return encodeHex(data, true);  
	    }  
}
//https://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=MTg4MjYyNzQ3OTQ%3D&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.19)&_=1507987864592
//sinaSSOController.preloginCallBack({"retcode":0,"servertime":1507987865,"pcid":"gz-0ede2965d8010dd3075087e26ed4502ad23c","nonce":"F1DHEL","pubkey":"EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6442443","rsakv":"1330428213","is_openlock":0,"lm":1,"smsurl":"https:\/\/login.sina.com.cn\/sso\/msglogin?entry=weibo&mobile=18826274794&s=b27f520589165f9d54f0361e8b0dd9fb","showpin":0,"exectime":115})
//http://rm.api.weibo.com/2/remind/push_count.json?trim_null=1&with_dm_group=0&with_settings=1&exclude_attitude=1&with_common_cmt=1&with_comment_attitude=1&with_common_attitude=1&with_moments=1&with_dm_unread=1&msgbox=true&with_page_group=1&with_chat_group=1&with_chat_group_notice=1&_pid=1&count=3&source=351354573&status_type=0&callback=STK_150798780519613
//https://captcha.weibo.com/api/pattern/get?ver=1.0.0&source=ssologin&usrname=18826274794&line=160&side=100&radius=30&_rnd=0.9852116950667196&callback=pl_cb
//https://passport.weibo.cn/sso/login

//https://passport.weibo.cn/js/signin/weibologin.js?v=20170223

/**
**(function (win, doc) {
    var ENTRY = 'mweibo';
    var PRELOGIN = 'https://login.sina.com.cn/sso/prelogin.php?checkpin=1&entry=' + ENTRY;
    var LOGIN = 'https://passport.weibo.cn/sso/login';
    var VERIFY_IMAGE = 'https://passport.weibo.cn/captcha/image';
    var STAT_URL = 'https://passport.weibo.cn/stat/s';
    var STAT_ONEKEY_CLICK = 1;
    var STAT_ONEKEY_FAIL = 2;
    var STAT_ACT_ONEKEY_APP_CONFIRM = 1;
    var STAT_ACT_ONEKEY_WEBVIEW_OPEN = 2;
    var STAT_ACT_ONEKEY_WEBVIEW_CONFIRM = 3;
    var ERROR_COUNT = 0;
    var ERROR_COUNT_Mobile = 0;
    var name;
    var pl;

    function parseJSON(str) {
        if (typeof(str) === 'object') {
            return str;
        } else {
            if (window.JSON) {
                return JSON.parse(str);
            } else {
                return eval('(' + str + ')');
            }
        }
    }
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    function encodeFormData(data) {
        var pairs = [], regexp = /%20/g;

        var value;
        for (var key in data) {
            value = data[key].toString();
            pairs.push(win.encodeURIComponent(key).replace(regexp, '+') +
                '=' + win.encodeURIComponent(value).replace(regexp, '+'));
        }

        return pairs.join('&');
    }

    function $(id) {
        return doc.getElementById(id);
    }

    var LOGIN_SUCCESS_ADDRESS = $('loginSuccessAddress').value;


    function hasClass(elem, cls) {
        var reg = new RegExp('(^|\\s)' + cls + '($|\\s)');
        return reg.test(elem.className);
    }

    function removeClass(elem, cls) {
        var reg = new RegExp('(^|\\s)' + cls + '($|\\s)', 'g');
        elem.className = elem.className.replace(reg, ' ');
    }

    function addClass(elem, cls) {
        if (!hasClass(elem, cls)) {
            elem.className += ' ' + cls;
        }
    }

    var addEvent = doc.addEventListener ?
        function (elem, type, fn) {
            elem.addEventListener(type, fn, false);
        } : function (elem, type, fn) {
        elem.attachEvent('on' + type, fn);
    };

    function bind(fn, context) {
        return function () {
            fn.call(context);
        };
    }

    function utf8_to_b64(str) {
        return win.btoa(win.encodeURIComponent(trim(str)));
    }

    function trim(str) {
        return (!str) ? '' : str.toString().replace(/^\s+|\s+$/g, '');
    }

    function objLength(obj) {
        var cnt = 0;
        if (typeof obj != "object") return 0;
        for (var k in obj) {
            if (obj.hasOwnProperty(k)) cnt++;
        }
        return cnt;
    }

    function compareVersion(baseVer, version) {
        var baseVer_arr = [],
            version_arr = [];
        if (version) {
            baseVer_arr = baseVer.split('.');
            version_arr = version.split('.');
            if (version_arr.length == 0) {
                version_arr.push(version.parseInt() || 0);
            }
            if (baseVer_arr.length == 0) {
                baseVer_arr.push(baseVer.parseInt() || 0);
            }

            for (var i = 0; i < 3; i++) {
                if (!version_arr[i]) {
                    version_arr[i] = 0;
                }
                if (!baseVer_arr[i]) {
                    baseVer_arr[i] = 0;
                }
                version_arr[i] = parseInt(version_arr[i]);
                baseVer_arr[i] = parseInt(baseVer_arr[i]);

                if (version_arr[i] == baseVer_arr[i]) {
                    if (i == 2) {
                        return true;
                    }
                    continue;
                }
                else if (version_arr[i] > baseVer_arr[i]) {
                    return true;
                }
                else {
                    return false;
                }
            }

        }

        return false;
    }

    function Login() {
        this.init();
    }


    Login.prototype = {
        mode: 0,
        countDown: 60,
        intervalCount: 0,
        lastLogin: $('lastLogin'),
        loginName: $('loginName'),
        loginPassword: $('loginPassword'),
        loginNamePassword: $('loginNamePassword'),
        dVerifyCode: $('dVerifyCodeWrapper'),
        weidunCode: $('loginDVCode'),
        needWeidun: false,
        verifyCode: $('loginVCode'),
        needVerifyCode: false,
        errorMsg: $('errorMsg'),
        errorDialogMsg: $('errorDialogMsg'),
        errorDialog: $('errorDialog'),
        loginNamePanel: $('loginNamePanel'),
        jumpName: $('loginName'),
        errorDialogBtnF: $('errorDialogBtnF'),
        errorDialogBtnT: $('errorDialogBtnT'),
        errorBtn: $('errorBtn'),
        mobileLogin: $('mobileLogin'),
        passwordLogin: $('passwordLogin'),
        forgetPassword: $('forgetPassword'),
        register: $('register'),
        loginAction: $('loginAction'),
        weiboLogin: $('weiboLogin'),
        loginWrapper: $('loginWrapper'),
        accountWrapper: $('accountWrapper'),
        uctext: $('uctext'),
        weibotext: $('weibotext'),
        logByAppAuth: $('logByAppAuth'),
        ucORweiboLogin: $('ucORweiboLogin'),
        changeLogin: $('changeLogin'),
        errorDialogPanel: $('errorDialogPanel'),
        oldUserName: $('oldUserName'),
        avatarWrapper: $('avatarWrapper'),
        loginnameclear: $('loginnameclear'),
        ucname: $('ucname'),
        ucavatar: $('ucavatar'),
        loginRF: $('loginRF'),
        postform: $('postform'),
        loginfrom: $('loginfrom'),
        clientId: $('client_id'),
        redirectUri: $('redirect_uri'),
        display: $('display'),
        offcialMobile: $('offcialMobile'),
        action: $('action'),
        quickAuth: $('quick_auth'),
        countDownKey: true,
        disabled: false,
        appsetInterval: null,
        featurecode: $('featurecode'),
        loginRFCAL: $('loginRFCAL'),
        hff: $('hff'),
        hfp: $('hfp'),

        init: function () {
            var that = this;
            that.adaptByUA();
            that.bindEvent();
            that.ucYORN();
        },
        ucYORN: function () {
            var that = this;
            var loginRFValue = that.loginRF.value;
            if (window.ucweb && window.ucweb.startRequest && loginRFValue != 1) {
                var ucCode = window.ucweb.startRequest('shell.comments.getToken', ['weibo', 'noauth']);
                var url = 'https://passport.weibo.cn/sso/uclogin';//php请求接口
                if (ucCode && trim(ucCode.length > 8)) {
                    ajax({
                        url: 'https://passport.weibo.cn/signin/ajuclogin',
                        data: {
                            token: ucCode
                        },
                        type: 'get',
                        onsuccess: function (ret) {
                            var result = parseJSON(ret);
                            if (result.retcode == 20000000) {
                                that.ucname.innerHTML = result.data.nick;
                                that.ucavatar.src = result.data.avatar;
                                that.loginWrapper.style.display = 'none';
                                that.accountWrapper.style.display = 'block';
                                that.uctext.style.display = 'block';
                                that.weibotext.style.display = 'none';
                                that.ucORweiboLogin.onclick = function () {
                                    ajax({
                                        url: 'https://passport.weibo.cn/sso/uclogin',
                                        type: 'post',
                                        data: {
                                            token: ucCode,
                                            entry: 'mweibo',
                                            r: LOGIN_SUCCESS_ADDRESS
                                        },
                                        onsuccess: function (ret) {
                                            var result = parseJSON(ret);
                                            if (result.retcode == 20000000) {
                                                that.addCookie(result.data, true);
                                            }
                                        }
                                    });
                                };
                            } else {
                                that.weiboAppYORN();
                            }
                            ;
                        }
                    });
                } else {
                    that.weiboAppYORN();
                    return;
                }
            } else {
                that.weiboAppYORN();
            }
        },
        adaptByUA: function () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.indexOf('android 4.4.4')) {
                this.loginWrapper.style.minHeight = 'initial';
            }
        },
        bindEvent: function () {
            var that = this;
            addEvent($('loginAction'), 'click', bind(this.doLogin, this));
            addEvent(this.loginName, 'blur', bind(this.checkVerify, this));
            addEvent(this.loginName, 'focus', bind(this.onInput, this));
            addEvent(this.loginPassword, 'focus', bind(this.onInput, this));
            addEvent(this.changeLogin, 'click', function () {
                that.accountWrapper.style.display = 'none';
                that.loginWrapper.style.display = 'block';
            });
            addEvent(this.weidunCode, 'focus', function () {
                that.weidunCode.type = "tel";
            });
            addEvent(this.weidunCode, 'blur', function () {
                that.weidunCode.type = "text";
            });
            addEvent(this.errorDialogBtnF, 'click', function () {
                that.errorDialog.style.display = "none";
            });
            addEvent(win, 'resize', function () {
                that.setErrorDialogPanelPosition();
            });
            addEvent(that.errorDialogBtnT, 'click', function () {
                var opt = {}, formdata = {}, formstr = '';
                opt.type = 'get';
                opt.url = 'https://passport.weibo.cn/signin/ajsu';
                opt.data = {};
                opt.data.entry = 'mweibo';
                var inputs = that.postform.getElementsByTagName('input');
                for (var i = 0; i < inputs.length; i++) {
                    formdata[inputs[i].id] = inputs[i].value;
                }
                formstr = encodeFormData(formdata);
                var loginRFValue = that.loginRF.value;
                if (that.mode == 0) {
                    opt.data.su = utf8_to_b64(trim(that.loginName.value));
                }
                opt.onsuccess = function (ret) {
                    var result = parseJSON(ret);
                    if (result.retcode == 20000000) {
                        var href = 'https://passport.weibo.cn/signin/loginsms?code=' + result.data.code + '&entry=' + fEntry + '&r=' + LOGIN_SUCCESS_ADDRESS + '&rf=' + loginRFValue + '&' + formstr;
                        if (that.featurecode.value || that.featurecode.value.trim().length !== 0) {
                            href = href + "&featurecode=" + that.featurecode.value;
                        }
                        win.location.href = href;
                    }
                }
                ajax(opt);
            });

            addEvent(that.loginnameclear, 'click', function () {
                that.mode = 0;
                that.loginName.value = '';
                that.loginPassword.value = '';
                that.verifyCode.value = '';
                that.weidunCode.value = '';
                that.avatarWrapper.innerHTML = '';
                that.dVerifyCode.style.display = 'none';
                that.onInput();
                that.loginnameclear.className = "input-clear hid";
            });
        },
        weiboAppYORN: function () {
            var that = this;
            var loginRFValue = that.loginRF.value;
            var timeout = window.setTimeout(function () {
                that.loginWrapper.style.display = 'block';
                that.accountWrapper.style.display = 'none';
            }, 1000);

            if (that.logByAppAuth) {

                var deviceType = that.logByAppAuth.getAttribute('weibo-data-os-name');
                var appScheme = that.logByAppAuth.getAttribute('weibo-data-onekey-param');
                var logByAppAuth_new = function () {
                    that.logByAppAuth.onclick = that.ucORweiboLogin.onclick = function () {
                        if (appScheme) {
                            that.openClient(appScheme);
                        }
                    };
                };

                if (deviceType && deviceType == 'ios') {
                    // ios: 新方法
                    logByAppAuth_new();
                }
                else {
                    //android: 根据版本号判断
                    jsonp({
                        url: 'http://127.0.0.1:9527/query?appid=com.sina.weibo',
                        onsuccess: function (ret) {
                            var result = parseJSON(ret);
                            if (result) {
                                that.logByAppAuth.onclick = that.ucORweiboLogin.onclick = function () {
                                    var baseVer = '5.3.0';
                                    var curVer = result.versionName || '0';
                                    var isUpperVer = compareVersion(baseVer, curVer);
                                    if (isUpperVer) {
                                        // Android5.3.0以上：新方法
                                        if (appScheme) {
                                            that.openClient(appScheme);
                                        }
                                    }
                                    else {
                                        // Android低版本 旧方法
                                        ajax({
                                            url: "https://passport.weibo.cn/sso/ajgetappt?entry=abc",
                                            type: 'get',
                                            onsuccess: function (ret) {
                                                var result = parseJSON(ret);
                                                if (result.retcode == 20000000) {
                                                    that.openClient("sinaweibo://browser?url=https%3A%2F%2Fpassport.weibo.cn%2Fwapclient%2Fconfirm%3Ff%3Dw%26token%3D" + result.data.token, STAT_ACT_ONEKEY_WEBVIEW_CONFIRM);
                                                    that.appsetInterval = window.setInterval(function () {
                                                        that.appLogin(result.data.token)
                                                    }, 3000);
                                                } else {
                                                    win.location.href = "https://passport.weibo.cn/wapclient/error?info=1";
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        },
                        ontimeout: function () {
                            logByAppAuth_new();
                        },
                        onerror: function () {
                            logByAppAuth_new();
                        }
                    });
                }
            }
        },

        openClient: function (scheme, action) {
            if (typeof action == 'undefined') {
                if (scheme.indexOf("sinaweibo://weblogin") === 0) {
                    action = STAT_ACT_ONEKEY_APP_CONFIRM;
                }
                if (scheme.indexOf("sinaweibo://browser") === 0) {
                    action = STAT_ACT_ONEKEY_WEBVIEW_OPEN;
                }
            }
            ajax({
                url: STAT_URL + '?' + 'entry=' + ENTRY + '&tag=apponekey&act=' + action + '&stat=' + STAT_ONEKEY_CLICK,
                type: 'get'
            });

            var that = this;
            var type = this.detectBrowser();
            if (type == "open") {
                var w = window.open(scheme, "_blank");
                setTimeout(function () {
                    w.close();
                }, 0);
            }
            else {
                var $iframe = document.createElement('iframe');
                $iframe.src = scheme;
                $iframe.style.display = 'none';
                document.body.appendChild($iframe);
            }

            var startTime = Date.now();
            var _TIME_OUT = 3000;
            var timer = setTimeout(function () {
                var endTime = Date.now();
                //如果装了app并跳到客户端后，endTime - startTime 一定> timeout + 200
                if (!startTime || endTime - startTime < _TIME_OUT + 200) {
                    that.logByAppAuth.innerHTML = '<span style="color:red;">呼起失败</span>';
                    that.showTips('登录失败，请使用其它方式登录');
                    ajax({
                        url: STAT_URL + '?' + 'entry=' + ENTRY + '&tag=apponekey&act=' + action + '&stat=' + STAT_ONEKEY_FAIL,
                        type: 'get'
                    });
                }
            }, _TIME_OUT);

            window.onblur = function () {
                if (type != "open") {
                    clearTimeout(timer);
                }
            }
        },

        detectBrowser: function () {
            var b = navigator.userAgent;
            var type;

            if (/android/i.test(b)) {
                if (b.match(/MQQBrowser|UCBrowser|360Browser|Firefox|baidubrowse|SogouMobileBrowser|LieBaoFast|XiaoMi\/MiuiBrowser|opr/i)) {
                    type = "iframe";
                } else if (b.match(/Chrome/i) && window.chrome) {
                    type = "open";
                } else {
                    type = "iframe";
                }
            } else {
                if (b.match(/360Browser|QHBrowser|MQQBrowser/i)) {
                    type = "open";
                }
                else {
                    type = "iframe";
                }
            }
            return type;
        },
        showTips: function (msg, CW, CH, notClear) {
            var h = 100,
                w = 100,
                t,
                l,
                pop = document.createElement("div"),
                scrollT = document.documentElement.scrollTop || document.body.scrollTop;

            if ($('J-showTip')) {
                return;
            }
            document.body.appendChild(pop);
            pop.id = "J-showTip";


            CH = CH || window.innerHeight || document.documentElement.clientHeight;
            CW = CW || window.innerWidth || document.documentElement.clientWidth;
            l = parseInt((CW - w) / 2);
            t = scrollT + parseInt((CH - h) / 2);

            pop.style.cssText = "width: 80px;height: 60px;position: absolute;text-align: center;border-radius: 8px;font-weight: bold;font-size: 12px;color: rgb(255, 255, 255);-webkit-transition: opacity 0.4s ease-out;transition: opacity 0.4s ease-out;background: rgba(0, 0, 0, 0.6);padding: 20px 10px;top:" + t + "px;left:" + l + "px;";
            pop.innerHTML = msg;

            if (!notClear) {
                setTimeout(function () {
                    pop.style.opacity = 0;
                }, 4000);
                setTimeout(function () {
                    document.body.removeChild(pop);
                }, 5000);
            }
            return pop;
        },
        doNeedWeidun: function () {
            this.needWeidun = true;
            this.dVerifyCode.style.display = 'block';
        },
        doNeedMobile: function (url) {
            this.needWeidun = false;
            this.needVerifyCode = false;
            this.needMobile = true;
            this.dVerifyCode.style.display = 'none';
            this.errorDialogBtnT.innerHTML = '验证码登录';
            this.errorDialogMsg.innerHTML = '您的账号是通过快速注册方式获得的，需通过验证码登陆方式登陆微博';
            this.errorDialog.style.display = 'block';
            this.setErrorDialogPanelPosition();
        },
        excuteLogin: function (that, id) {
            var fromValue = that.loginfrom.value;
            var loginRFValue = trim(that.loginRF.value);
            var loginRFCALValue = trim(that.loginRFCAL.value);
            var data;
            that.changeDisabled(true);
            if (that.mode == 0) {
                data = {
                    username: trim(that.loginName.value),
                    password: trim(that.loginPassword.value),
                    savestate: 1
                };
            } else {
                data = {
                    password: trim(that.loginPassword.value),
                    savestate: 1
                };
            }
            if (that.needWeidun) {
                data.vsn = trim(that.weidunCode.value);
            }
            if (that.oldUserName.vaule != that.loginName.value) {
                ERROR_COUNT = 0;
                ERROR_COUNT_Mobile = 0;
            }

            that.oldUserName.vaule = that.loginName.value;

            data.r = getQueryString("r") ? getQueryString("r") : "";


            data.ec = ERROR_COUNT;


            data.pagerefer = document.referrer;

            data.entry = fEntry;
            data.wentry = fWentry;

            loginRFValue ? data.rf = trim(loginRFValue) : null;
            loginRFCALValue ? data.rfcal = trim(loginRFCALValue) : null;

            data.loginfrom = fromValue;
            data.client_id = fClientid;
            data.code = fCode;
            data.qq = fQq;
            data.mainpageflag = 1;
            if (id != undefined) {
                data.vid = id;
            }
            if (that.featurecode.value || that.featurecode.value.trim().length !== 0) {
                data.featurecode = that.featurecode.value;
            }
            //支付免登录流程 红包飞新增
            data.hff = that.hff.value;
            data.hfp = that.hfp.value;

            //登录wifi
            if (typeof Zepto != "undefined") {
                Zepto.ajax({
                    url: LOGIN,
                    type: 'post',
                    data: data,
                    success: function (ret) {
                        var result = parseJSON(ret);
                        if (result.retcode == 20000000) {
                            that.changeDisabled(false);
                            that.addCookie(result.data);
                        } else {
                            that.dealLoginFail(result);
                        }
                    }
                });
            } else {
                ajax({
                    url: LOGIN,
                    type: 'post',
                    data: data,
                    onsuccess: function (ret) {
                        var result = parseJSON(ret);
                        if (result.retcode == 20000000) {
                            console.log('success');
                            that.changeDisabled(false);
                            that.addCookie(result.data);
                        } else {
                            that.dealLoginFail(result);
                        }
                    }
                });
            }
        },
        doLogin: function () {
            if (this.disabled) {
                return;
            }
            var that = this;
            if (that.validate()) {
                if (that.needVerifyCode) {
                    that.getVerifyImage(that, that.excuteLogin);
                } else {
                    that.excuteLogin(that);
                }
            }
        },
        callClientApp: function (options) {
            var conf = {
                //客户端APP呼起协议地址
                protocol: '',
                //客户端下载地址或者中间页地址
                url: '',
                //开始时间
                startTime: Date.now(),
                //呼起超时等待时间
                waiting: 800,
                //呼起操作限制时间
                callLimit: 50,
                //需要跳转到下载地址时
                onRedirect: function () {
                }
            };

            for (var attr in options) {
                if (conf.hasOwnProperty(attr)) {
                    conf[attr] = options[attr];
                }
            }
            var ua = navigator.userAgent.toLowerCase();
            var platform = navigator.platform.toLowerCase();
            var version, m;

            var numberify = function (s) {
                var c = 0;
                try {
                    var arr = s.split(/[_\.]/);
                    var main = arr.shift();
                    c = parseFloat(main + '.' + arr.join(''), 10);
                } catch (e) {
                }
                return c;
            };
            if ((m = ua.match(/\((ipad|iphone|ipod|itouch).*os\s([\d_\.]+)/)) && m[2]) {
                version = numberify(m[2]);
            }
            var wId;
            var isChrome = /(chrome|crios)\/([\d.]*)/.test(ua);
            if (isChrome) {
                // chrome下iframe无法唤起Android客户端，这里使用window.open
                // 另一个方案参考 https://developers.google.com/chrome/mobile/docs/intents
                var w = window.open(conf.protocol);
                wId = setInterval(function () {
                    if (typeof w === 'object') {
                        clearInterval(wId);
                        w.close();
                    }
                }, 10);
            } else if (version && version >= 9) {
                window.location = conf.protocol;
            } else {
                // 创建iframe
                var iframe = document.createElement('iframe');
                iframe.style.display = 'none';
                iframe.src = conf.protocol;
                document.body.appendChild(iframe);
            }

            setTimeout(function () {
                if (!isChrome) {
                    document.body.removeChild(iframe);
                } else {
                    clearInterval(wId);
                }

                //ios下，跳转到APP，页面JS会被阻止执行。
                //因此如果超时时间大大超过了预期时间范围，可断定APP已被打开。
                if (Date.now() - conf.startTime < conf.waiting + conf.callLimit) {
                    // 在一定时间内无法唤起客户端，跳转下载地址或到中间页
                    window.location = conf.url;

                    if (typeof conf.onRedirect === 'function') {
                        conf.onRedirect();
                    }
                }
            }, conf.waiting);
        },
        saveHandler: function () {
            var that = this;
            var html = '该账号已开启安全设备，<a href="javascript:;" id="openApp">请点此使用微博客户端登录</a>';
            that.errorMsg.innerHTML = html;
            that.errorMsg.style.display = 'block';
            addEvent($('openApp'), 'touchend', function () {
                that.callClientApp({
                    startTime: Date.now(),
                    waiting: 800,
                    callLimit: 50,
                    protocol: 'sinaweibo://splash',
                    url: 'http://weibo.cn/qr/download'
                });
            });
        },
        changeDisabled: function (control) {
            this.disabled = control;
        },
        errorDialogBtnHidden: function () {
            this.errorDialogBtnMsg.innerHTML = '';
            this.errorDialogBtn.style.display = 'none';
        },
        newRegister: function () {
            var href = this.register.href;
            this.errorDialogBtnHidden();
            win.location.href = href;
        },
        dealLoginFail: function (result) {
            //分成两种显示方式，一种是在顶导显示一种是用弹层显示
            var that = this;
            var loginRFValue = that.loginRF.value;
            if (result.retcode == 50011009 || result.retcode == 50011011 || result.retcode == 50011002 || result.retcode == 50011008 || result.retcode == 50011010 || result.retcode == 50011012) {
                //当rf为1的时候只提示错误
                if (result.retcode == 50011002 && loginRFValue != 1) {
                    if (ERROR_COUNT >= 2) {
                        var errorData = parseJSON(result.data);
                        if (errorData.er == 1) {
                            that.errorDialogBtnT.innerHTML = '确定';
                            addEvent(that.errorDialogBtnT, 'click', function () {
                                win.location.href = 'http://m.weibo.cn/forgotpwd/index';
                            });
                            that.errorDialogMsg.innerHTML = '登录失败，是否找回密码？';
                            that.errorDialog.style.display = 'block';
                            that.setErrorDialogPanelPosition();
                        }
                    } else if (result.data.im === 1) {//只有绑定手机的用户才在两次错误的时候出现使用手机验证码登录的流程

                        if (ERROR_COUNT_Mobile === 1) {

                            that.errorDialogBtnT.innerHTML = '验证码登录';
                            that.errorDialogMsg.innerHTML = '帐号或密码错误，你也可以选择短信验证码方式登录微博。';
                            that.errorDialog.style.display = 'block';
                            that.setErrorDialogPanelPosition();

                        } else {
                            that.errorMsg.innerHTML = result.msg;
                            that.errorMsg.style.display = 'block';
                        }
                        ERROR_COUNT++;
                        ERROR_COUNT_Mobile++;
                    } else {
                        ERROR_COUNT++;
                        ERROR_COUNT_Mobile = 0;
                        that.errorMsg.innerHTML = result.msg;
                        that.errorMsg.style.display = 'block';
                    }
                } else {
                    that.errorMsg.innerHTML = result.msg;
                    that.errorMsg.style.display = 'block';
                }

            } else if (result.retcode == 50030000) {
                that.saveHandler();
            } else if(result.retcode == 50050002) {
                that.errorMsg.innerHTML = "您已开启登录保护，请查收@微博安全中心发来的私信，按提示完成登录。" + "<a href='http://kefu.weibo.com/faqdetail?id=20200'>查看帮助</a>";
                that.errorMsg.style.display = 'block';
            }else if(result.retcode == 50050004){
                location.href = 'https://passport.weibo.cn/sso/loginprotectlogin?action=getcode';
            } else if(result.retcode == 50011005){
                //异地登录的用户需要输入验证码
                that.errorMsg.innerHTML = result.msg;
                that.errorMsg.style.display = 'block';
                that.getVerifyImage(that, that.excuteLogin);
            } else {
                that.errorMsg.innerHTML = result.msg;
                that.errorMsg.style.display = 'block';
                if (result.retcode == 50011003 || result.retcode == 50011004) {
                    that.needWeidun = true;
                    that.dVerifyCode.style.display = 'block';
                }
            }
            that.weidunCode.value = '';
            that.changeDisabled(false);
        },
        addCookie: function (obj, uc) {
            var that = this;

            setTimeout(function () {
                uc ? that.goToNextPageUC(obj) : that.goToNextPage(obj);
            }, 5000);
            var crossdomainlist = obj.crossdomainlist;
            var counter = objLength(crossdomainlist);
            if (counter == 0) {
                that.goToNextPage(obj);
            }
            for (var d in crossdomainlist) {
                if (!crossdomainlist.hasOwnProperty(d)) continue;
                jsonp({
                    url: crossdomainlist[d] + '&savestate=1',
                    onsuccess: function () {
                        counter--;
                        if (counter <= 0) {
                            uc ? that.goToNextPageUC(obj) : that.goToNextPage(obj);
                        }
                    }
                });
            }
        },
        goToNextPage: function (obj) {
            var that = this;
            if (obj['toauth'] != 1) {
                var href = obj['loginresulturl'] ? obj['loginresulturl'] + '&savestate=1&url=' + LOGIN_SUCCESS_ADDRESS : win.decodeURIComponent(LOGIN_SUCCESS_ADDRESS);
                win.location.href = href;
            } else {
                if (obj['ticket']) {
                    var ipt = document.createElement('input');
                    that.postform.appendChild(ipt);
                    ipt.id = 'ticket';
                    ipt.name = 'ticket';
                    ipt.type = 'hidden';
                    ipt.value = obj['ticket'];
                }
                that.postform.submit();
            }
        },
        goToNextPageUC: function (obj) {
            var that = this;
            if (obj['toauth'] != 1) {
                var href = obj['loginresulturl'] ? obj['loginresulturl'] : win.decodeURIComponent(LOGIN_SUCCESS_ADDRESS);
                win.location.href = href;
            } else {
                if (obj['ticket']) {
                    var ipt = document.createElement('input');
                    that.postform.appendChild(ipt);
                    ipt.id = 'ticket';
                    ipt.name = 'ticket';
                    ipt.type = 'hidden';
                    ipt.value = obj['ticket'];
                }
                that.postform.submit();
            }
        },
        validate: function () {
            var username = trim(this.loginName.value);
            var password = trim(this.loginPassword.value);
            if (this.mode == 0) {
                if (username.length == 0) {
                    this.errorMsg.innerHTML = '用户名不能为空';
                    this.errorMsg.style.display = 'block';
                    return false;
                } else if (password.length == 0 && !this.needMobile) {
                    this.errorMsg.innerHTML = '密码不能为空';
                    this.errorMsg.style.display = 'block';
                    return false;
                }
            } else {
                if (password.length == 0 && !this.needMobile) {
                    this.errorMsg.innerHTML = '密码不能为空';
                    this.errorMsg.style.display = 'block';
                    return false;
                }
            }
            if (this.needWeidun && trim(this.weidunCode.value).length == 0) {
                this.errorMsg.innerHTML = '请输入微盾动态码';
                this.errorMsg.style.display = 'block';
                return false;
            }
            return true;
        },
        checkVerify: function () {
            var that = this;
            var oldUserName = that.oldUserName.value, username = trim(that.loginName.value);
            if (that.mode != 0 && oldUserName === username) {
                return true;
            } else {
                //增加对于手机验证码登陆情况的判断，，增加一个超链，增加一个点击事件
                if (that.mode != 0) {
                    that.mode = 0;
                    that.avatarWrapper.innerHTML = '';
                }
                jsonp({
                    url: PRELOGIN + '&su=' + utf8_to_b64(username),
                    onsuccess: function (ret) {
                        if (ret.retcode === 0) {
                            if (ret.nopwd === 1 && ret.lm == 1) {
                                that.dVerifyCode.style.display = 'none';
                                that.errorDialogBtnT.innerHTML = '验证码登录';
                                that.errorDialogMsg.innerHTML = '您的账号是通过快速注册方式获得的，需通过验证码登陆方式登陆微博';
                                that.errorDialog.style.display = 'block';
                                that.setErrorDialogPanelPosition();
                            } else {
                                //添加
                                switch (ret.showpin) {
                                    case 1:
                                        that.dVerifyCode.style.display = 'none';
                                        that.dVerifyCode.style.display = 'none';
                                        if (that.loginName.value != '' && that.loginPassword.value != '') {
                                            that.needVerifyCode = true;
                                            that.needMobile = false;
                                            that.needWeidun = false;
                                            return;
                                        }
                                        that.needVerifyCode = true;
                                        that.needMobile = false;
                                        that.needWeidun = false;
                                        break;
                                    case 2:
                                        that.dVerifyCode.style.display = 'block';
                                        that.errorMsg.style.display = 'none';
                                        that.needWeidun = true;
                                        that.needMobile = false;
                                        that.needVerifyCode = false;
                                        break;
                                    default:
                                        that.dVerifyCode.style.display = 'none';
                                        that.errorMsg.style.display = 'none';
                                        that.needVerifyCode = false;
                                        that.needWeidun = false;
                                        that.needMobile = false;
                                        break;
                                }
                            }
                        }
                    }
                });
            }
        },
        onInput: function () {
            this.errorMsg.innerHTML = '';
            this.errorMsg.style.display = 'none';
        },
        showPic: function (that) {

        },
        getVerifyImage: function (that, callback) {
            name = that.loginName.value;
            var config = {
                margin: 20,
                radius: 30,
                usrname: name,
                source: "ssologin",
                onSuccess: function (id) {
                    callback(that, id);
                },
                onError: function () {
                    console.log("verfify load fail");
                },
                onReady: function () {
                }
            };
            if (pl == undefined) {
                pl = new PatternLock(config);
                pl.validate();
            } else {
                pl.validate();
            }
        },
        appLogin: function (token) {
            var that = this;
            if (that.intervalCount >= 200) {
                win.clearInterval(that.appsetInterval);
                that.intervalCount = 0;
                return;
            }
            that.intervalCount++;
            ajax({
                url: 'https://passport.weibo.cn/wapclient/check?token=' + token + '&r=' + LOGIN_SUCCESS_ADDRESS,
                type: 'get',
                onsuccess: function (ret) {
                    var json = parseJSON(ret);
                    if (json.retcode == 20000000) {
                        window.location.href = json.data;
                    }
                }
            })
        },
        setErrorDialogPanelPosition: function () {
            var that = this;
            that.setPosition(that.errorDialogPanel, 'center');
        },
        setPosition: function (dom, position) {
            var top, left, width = dom.offsetWidth, height = dom.offsetHeight,
                winWidth = doc.body.clientWidth, winHeight = doc.body.clientHeight,
                dd = doc.documentElement, db = doc.body,
                limitTop = top = Math.max(window.pageYOffset || 0, dd.scrollTop, db.scrollTop),
                limitLeft = left = Math.max(window.pageXOffset || 0, dd.scrollLeft, db.scrollLeft);
            if (position === 'center') {
                top += (winHeight - height) / 2;
                left += (winWidth - width) / 2;
                if (top < limitTop) top = limitTop;
                if (left < limitLeft) left = limitLeft;
            }
            dom.style.top = top + 'px';
            dom.style.left = left + 'px';
        }
    };

    win.loginApp = new Login();

    addEvent(document, 'keydown', function (e) {
        e = e || window.event;
        if (e.keyCode == 13) {
            loginApp.doLogin();
        }
    });
    var url = window.location.href;
})(window, document);
*/