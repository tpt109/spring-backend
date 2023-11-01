package com.figpop.backend.fgcore.fgutils.common;

import com.alibaba.fastjson.JSONArray;
import com.figpop.backend.fgcore.fgutils.contants.CommonConstant;
import com.figpop.backend.fgcore.fgutils.contants.SymbolConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ConvertUtils {
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return (true);
		}
		if ("".equals(object)) {
			return (true);
		}
		if (CommonConstant.STRING_NULL.equals(object)) {
			return (true);
		}
		return (false);
	}
	
	public static boolean isNotEmpty(Object object) {
		if (object != null && !"".equals(object) && !object.equals(CommonConstant.STRING_NULL)) {
			return (true);
		}
		return (false);
	}

	public static String decode(String strIn, String sourceCode, String targetCode) {
		return code2code(strIn, sourceCode, targetCode);
	}

    public static String strToUTF(String strIn, String sourceCode, String targetCode) {
		strIn = "";
		try {
			strIn = new String(strIn.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strIn;

	}

	private static String code2code(String strIn, String sourceCode, String targetCode) {
		String strOut = null;
		if (strIn == null || "".equals(strIn.trim())) {
			return strIn;
		}
		try {
			byte[] b = strIn.getBytes(sourceCode);
			for (int i = 0; i < b.length; i++) {
				System.out.print(b[i] + "  ");
			}
			strOut = new String(b, targetCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return strOut;
	}

	public static int getInt(String s, int defval) {
		if (s == null || "".equals(s)) {
			return (defval);
		}
		try {
			return (Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return (defval);
		}
	}

	public static int getInt(String s) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		try {
			return (Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static int getInt(String s, Integer df) {
		if (s == null || "".equals(s)) {
			return df;
		}
		try {
			return (Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static Integer[] getInts(String[] s) {
		if (s == null) {
			return null;
		}
		Integer[] integer = new Integer[s.length];
		for (int i = 0; i < s.length; i++) {
			integer[i] = Integer.parseInt(s[i]);
		}
		return integer;

	}

	public static double getDouble(String s, double defval) {
		if (s == null || "".equals(s)) {
			return (defval);
		}
		try {
			return (Double.parseDouble(s));
		} catch (NumberFormatException e) {
			return (defval);
		}
	}

	public static double getDou(Double s, double defval) {
		if (s == null) {
			return (defval);
		}
		return s;
	}

	/*public static Short getShort(String s) {
		if (StringUtil.isNotEmpty(s)) {
			return (Short.parseShort(s));
		} else {
			return null;
		}
	}*/

	public static int getInt(Object object, int defval) {
		if (isEmpty(object)) {
			return (defval);
		}
		try {
			return (Integer.parseInt(object.toString()));
		} catch (NumberFormatException e) {
			return (defval);
		}
	}
	
	public static Integer getInteger(Object object, Integer defval) {
		if (isEmpty(object)) {
			return (defval);
		}
		try {
			return (Integer.parseInt(object.toString()));
		} catch (NumberFormatException e) {
			return (defval);
		}
	}
	
	public static Integer getInt(Object object) {
		if (isEmpty(object)) {
			return null;
		}
		try {
			return (Integer.parseInt(object.toString()));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static int getInt(BigDecimal s, int defval) {
		if (s == null) {
			return (defval);
		}
		return s.intValue();
	}

//	public static Integer[] getIntegerArry(String[] object) {
//		int len = object.length;
//		Integer[] result = new Integer[len];
//		try {
//			for (int i = 0; i < len; i++) {
//				result[i] = new Integer(object[i].trim());
//			}
//			return result;
//		} catch (NumberFormatException e) {
//			return null;
//		}
//	}

	public static String getString(String s) {
		return (getString(s, ""));
	}

	public static String getString(Object object) {
		if (isEmpty(object)) {
			return "";
		}
		return (object.toString().trim());
	}

	public static String getString(int i) {
		return (String.valueOf(i));
	}

	public static String getString(float i) {
		return (String.valueOf(i));
	}

	public static String getString(String s, String defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s.trim());
	}

	public static String getString(Object s, String defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s.toString().trim());
	}

//	public static long stringToLong(String str) {
//		Long test = new Long(0);
//		try {
//			test = Long.valueOf(str);
//		} catch (Exception e) {
//		}
//		return test.longValue();
//	}

	/**
	 * 获取本机IP
	 */
	public static String getIp() {
		String ip = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ip = address.getHostAddress();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	private static boolean isBaseDataType(Class clazz) throws Exception {
		return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.isPrimitive());
	}

	public static String getIpAddrByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getRealIp() throws SocketException {

		String localip = null;
		String netip = null;

		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;

		boolean finded = false;
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();

				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {

				    localip = ip.getHostAddress();
				}
			}
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
		    String reg = "\\s*|\t|\r|\n";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;

	}

	public static boolean isIn(String child, String[] all) {
		if (all == null || all.length == 0) {
			return false;
		}
		for (int i = 0; i < all.length; i++) {
			String aSource = all[i];
			if (aSource.equals(child)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isArrayIn(String[] childArray, String[] all) {
		if (all == null || all.length == 0) {
			return false;
		}
		for (String v : childArray) {
			if (!isIn(v, all)) {
				return false;
			}
		}
		return true;
	}

//	public static boolean isJsonArrayIn(JSONArray childArray, String[] all) {
//		if (all == null || all.length == 0) {
//			return false;
//		}
//
//		String[] childs = childArray.toArray(new String[]{});
//		for (String v : childs) {
//			if (!isIn(v, all)) {
//				return false;
//			}
//		}
//		return true;
//	}

	/**
	 * 获取Map对象
	 */
	public static Map<Object, Object> getHashMap() {
		return new HashMap<>(5);
	}

	public static Map<Object, Object> setToMap(Set<Object> setobj) {
		Map<Object, Object> map = getHashMap();
		for (Iterator iterator = setobj.iterator(); iterator.hasNext();) {
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
			map.put(entry.getKey().toString(), entry.getValue() == null ? "" : entry.getValue().toString().trim());
		}
		return map;

	}

	public static boolean isInnerIp(String ipAddress) {
		boolean isInnerIp = false;
		long ipNum = getIpNum(ipAddress);
		/**
		 * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
		 **/
		long aBegin = getIpNum("10.0.0.0");
		long aEnd = getIpNum("10.255.255.255");
		long bBegin = getIpNum("172.16.0.0");
		long bEnd = getIpNum("172.31.255.255");
		long cBegin = getIpNum("192.168.0.0");
		long cEnd = getIpNum("192.168.255.255");
		String localIp = "127.0.0.1";
		isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || localIp.equals(ipAddress);
		return isInnerIp;
	}

	private static long getIpNum(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);

		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	private static boolean isInner(long userIp, long begin, long end) {
		return (userIp >= begin) && (userIp <= end);
	}

	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// quick check
		if (name == null || name.isEmpty()) {
			// No need to convert
			return "";
		} else if (!name.contains(SymbolConstant.UNDERLINE)) {
			return name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();
		}
		// Split the original string with underscores
		String[] camels = name.split("_");
		for (String camel : camels) {
			// Skip the leading and trailing underscores or double underscores in the original string
			if (camel.isEmpty()) {
				continue;
			}
			// Handle real camel case fragments
			if (result.length() == 0) {
				// The first camel case fragment, all letters are lowercase
				result.append(camel.toLowerCase());
			} else {
				// Other camel case fragments, the first letter is capitalized
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	public static String camelNames(String names) {
		if(names==null||"".equals(names)){
			return null;
		}
		StringBuffer sf = new StringBuffer();
		String[] fs = names.split(",");
		for (String field : fs) {
			field = camelName(field);
			sf.append(field + ",");
		}
		String result = sf.toString();
		return result.substring(0, result.length() - 1);
	}

	public static String camelNameCapFirst(String name) {
		StringBuilder result = new StringBuilder();
		// quick check
		if (name == null || name.isEmpty()) {
			// No need to convert
			return "";
		} else if (!name.contains(SymbolConstant.UNDERLINE)) {
			// No underscores, just lowercase the first letter
			return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		}
		// Split the original string with underscores
		String[] camels = name.split("_");
		for (String camel : camels) {
			// Skip the leading and trailing underscores or double underscores in the original string
			if (camel.isEmpty()) {
				continue;
			}
			// Other camel case fragments, the first letter is capitalized
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	public static String camelToUnderline(String para){
	    int length = 3;
        if(para.length()<length){
        	return para.toLowerCase(); 
        }
        StringBuilder sb=new StringBuilder(para);

        int temp=0;

        for(int i=2;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toLowerCase(); 
	}

	public static String randomGen(int place) {
		String base = "qwertyuioplkjhgfdsazxcvbnmQAZWSXEDCRFVTGBYHNUJMIKLOP0123456789";
		StringBuffer sb = new StringBuffer();
		Random rd = new Random();
		for(int i=0;i<place;i++) {
			sb.append(base.charAt(rd.nextInt(base.length())));
		}
		return sb.toString();
	}

	public static Field[] getAllFields(Object object) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	public static List<Map<String, Object>> toLowerCasePageList(List<Map<String, Object>> list){
		List<Map<String, Object>> select = new ArrayList<>();
		for (Map<String, Object> row : list) {
			 Map<String, Object> resultMap = new HashMap<>(5);
			 Set<String> keySet = row.keySet(); 
			 for (String key : keySet) { 
				 String newKey = key.toLowerCase(); 
				 resultMap.put(newKey, row.get(key)); 
			 }
			 select.add(resultMap);
		}
		return select;
	}

	public static<F,T> List<T> entityListToModelList(List<F> fromList, Class<T> tClass){
		if(fromList == null || fromList.isEmpty()){
			return null;
		}
		List<T> tList = new ArrayList<>();
		for(F f : fromList){
			T t = entityToModel(f, tClass);
			tList.add(t);
		}
		return tList;
	}

	public static<F,T> T entityToModel(F entity, Class<T> modelClass) {
		LOGGER.debug("entityToModel : Entity属性的值赋值到Model");
		Object model = null;
		if (entity == null || modelClass ==null) {
			return null;
		}

		try {
			model = modelClass.newInstance();
		} catch (InstantiationException e) {
			LOGGER.error("entityToModel : 实例化异常", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("entityToModel : 安全权限异常", e);
		}
		BeanUtils.copyProperties(entity, model);
		return (T)model;
	}

	public static boolean listIsEmpty(Collection list) {
		return (list == null || list.size() == 0);
	}

	public static boolean isEqual(Object oldVal, Object newVal) {
		if (oldVal != null && newVal != null) {
			if (isArray(oldVal)) {
				return equalityOfArrays((Object[]) oldVal, (Object[]) newVal);
			}else if(oldVal instanceof JSONArray){
				if(newVal instanceof JSONArray){
					return equalityOfJSONArray((JSONArray) oldVal, (JSONArray) newVal);
				}else{
					if (isEmpty(newVal) && (oldVal == null || ((JSONArray) oldVal).size() == 0)) {
						return true;
					}
					List<Object> arrayStr = Arrays.asList(newVal.toString().split(","));
					JSONArray newValArray = new JSONArray(arrayStr);
					return equalityOfJSONArray((JSONArray) oldVal, newValArray);
				}
			}else{
				return oldVal.equals(newVal);
			}
			
		} else {
			if (oldVal == null && newVal == null) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean isArray(Object obj) {
		if (obj == null) {
			return false;
		}
		return obj.getClass().isArray();
	}

	public static boolean equalityOfJSONArray(JSONArray oldVal, JSONArray newVal) {
		if (oldVal != null && newVal != null) {
			Object[] oldValArray = oldVal.toArray();
			Object[] newValArray = newVal.toArray();
			return equalityOfArrays(oldValArray,newValArray);
		} else {
			if ((oldVal == null || oldVal.size() == 0) && (newVal == null || newVal.size() == 0)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static boolean equalityOfStringArrays(String oldVal, String newVal) {
		if(oldVal.equals(newVal)){
			return true;
		}
		if(oldVal.indexOf(",")>=0 && newVal.indexOf(",")>=0){
			String[] arr1 = oldVal.split(",");
			String[] arr2 = newVal.split(",");
			if(arr1.length == arr2.length){
				boolean flag = true;
				Map<String, Integer> map = new HashMap<>();
				for(String s1: arr1){
					map.put(s1, 1);
				}
				for(String s2: arr2){
					if(map.get(s2) == null){
						flag = false;
						break;
					}
				}
				return flag;
			}
		}
		return false;
	}

	public static boolean equalityOfArrays(Object[] oldVal, Object newVal[]) {
		if (oldVal != null && newVal != null) {
			Arrays.sort(oldVal);
			Arrays.sort(newVal);
			return Arrays.equals(oldVal, newVal);
		} else {
			if ((oldVal == null || oldVal.length == 0) && (newVal == null || newVal.length == 0)) {
				return true;
			} else {
				return false;
			}
		}
	}

//	public static void main(String[] args) {
////		String[] a = new String[]{"1", "2"};
////		String[] b = new String[]{"2", "1"};
//		Integer a = null;
//		Integer b = 1;
//		System.out.println(oConvertUtils.isEqual(a, b));
//	}

	public static boolean listIsNotEmpty(Collection list) {
		return !listIsEmpty(list);
	}

	public static String readStatic(String url) {
		String json = "";
		try {
			//换个写法，解决springboot读取jar包中文件的问题
			InputStream stream = ConvertUtils.class.getClassLoader().getResourceAsStream(url.replace("classpath:", ""));
			json = IOUtils.toString(stream,"UTF-8");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 将List 转成 JSONArray
	 * @return
	 */
	public static JSONArray list2JSONArray(List<String> list){
		if(list==null || list.size()==0){
			return null;
		}
		JSONArray array = new JSONArray();
		for(String str: list){
			array.add(str);
		}
		return array;
	}

	public static boolean isEqList(List<String> list1, List<String> list2){
		if(list1.size() != list2.size()){
			return false;
		}
		for(String str1: list1){
			boolean flag = false;
			for(String str2: list2){
				if(str1.equals(str2)){
					flag = true;
					break;
				}
			}
			if(!flag){
				return false;
			}
		}
		return true;
	}

	public static boolean isInList(List<String> list1, List<String> list2){
		for(String str1: list1){
			boolean flag = false;
			for(String str2: list2){
				if(str1.equals(str2)){
					flag = true;
					break;
				}
			}
			if(flag){
				return true;
			}
		}
		return false;
	}

	public static Double calculateFileSizeToMb(Long uploadCount){
		double count = 0.0;
		if(uploadCount>0) {
			BigDecimal bigDecimal = new BigDecimal(uploadCount);
			//换算成MB
			BigDecimal divide = bigDecimal.divide(new BigDecimal(1048576));
			count = divide.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return count;
		}
		return count;
	}
	
}
