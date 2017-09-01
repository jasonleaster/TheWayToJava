package org.jasonleaster.rbac.context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jasonleaster.utils.json.JsonUtils;

/**
 * 返回数据的json格式
 */
public class BaseReturn {

	/**
	 * 返回错误码
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String response(ErrorCode errorCode) {
		return returnJsonStr(errorCode, null).toString();
	}

	/**
	 * 返回一个对象,并返回成功
	 * 
	 * @param obj
	 * @return
	 */
	public static String response(Object obj) {
		return returnJsonStr(ErrorCode.SUCCESS, obj).toString();
	}

	/**
	 * 返回错误码和对象
	 * 
	 * @param errorCode
	 * @param obj
	 * @return
	 */
	public static String response(ErrorCode errorCode, Object obj) {
		return returnJsonStr(errorCode, obj).toString();
	}

	public static String returnJsonStr(ErrorCode errorCode, Object obj) {
		GsonObject gsonObj = new GsonObject();
		gsonObj.setCode(errorCode.getCode());
		gsonObj.setMessage(errorCode.getMessage());
		gsonObj.setData(obj);
		return JsonUtils.toJson(gsonObj);
	}

	public static class GsonObject {
		private String code;
		private String message;
		private Object data;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

	}

}
