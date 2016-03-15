import java.io.UnsupportedEncodingException;

public class Rc4 {
	
	public static String decry_RC4_byte(byte[] data, String key) {  
        if (data == null || key == null) {  
            return null;  
        } 
        return asString(RC4Base(data, key));  
    }  
  
    public static String decry_RC4_string(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        String out = null;
        try {
			out = new String(RC4Base(HexString2Bytes(data), key), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return out;
    }  
  
    public static byte[] encry_RC4_byte(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        byte b_data[] = null;
		try {
			b_data = data.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return RC4Base(b_data, key);  
    }  
  
    public static String encry_RC4_string(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return toHexString(asString(encry_RC4_byte(data, key)));  
    }  
  
    private static String asString(byte[] buf) {  
        StringBuffer strbuf = new StringBuffer(buf.length);  
        for (int i = 0; i < buf.length; i++) {  
            strbuf.append((char) buf[i]);  
        }  
        return strbuf.toString();  
    }  
  
    private static byte[] initKey(String aKey) {  
//      byte[] b_key = aKey.getBytes();
    	byte[] b_key = HexString2Bytes(aKey);
        byte state[] = new byte[256];  
  
        for (int i = 0; i < 256; i++) {  
            state[i] = (byte) i;  
        }  
        int index1 = 0;  
        int index2 = 0;  
        if (b_key == null || b_key.length == 0) {  
            return null;  
        }  
        for (int i = 0; i < 256; i++) {  
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;  
            byte tmp = state[i];  
            state[i] = state[index2];  
            state[index2] = tmp;  
            index1 = (index1 + 1) % b_key.length;  
        }  
        return state;  
    }  
  
    private static String toHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch & 0xFF);  
            if (s4.length() == 1) {  
                s4 = '0' + s4;  
            }  
            str = str + s4;  
        }  
        return str;// 0x表示十六进制  
    }  
  
    public static byte[] HexString2Bytes(String src) {  
        int size = src.length();  
        byte[] ret = new byte[size / 2];  
        byte[] tmp = src.getBytes();  
        for (int i = 0; i < size / 2; i++) {  
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);  
        }  
        return ret;  
    }  
  
    private static byte uniteBytes(byte src0, byte src1) {  
        char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();  
        _b0 = (char) (_b0 << 4);  
        char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();  
        byte ret = (byte) (_b0 ^ _b1);  
        return ret;  
    }  
  
    public static byte[] RC4Base(byte[] input, String mKkey) {  
        int x = 0;  
        int y = 0;  
        byte key[] = initKey(mKkey);  
        int xorIndex;  
        byte[] result = new byte[input.length];  
  
        for (int i = 0; i < input.length; i++) {  
            x = (x + 1) & 0xff;  
            y = ((key[x] & 0xff) + y) & 0xff;  
            byte tmp = key[x];  
            key[x] = key[y];  
            key[y] = tmp;  
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;  
            result[i] = (byte) (input[i] ^ key[xorIndex]);  
        }  
        return result;  
    }
	
    /** 
     * 字符串转换成十六进制字符串 
     */  
      
    public static String str2HexStr(String str) {  
      
        char[] chars = "0123456789ABCDEF".toCharArray();  
        StringBuilder sb = new StringBuilder("");  
        byte[] bs = str.getBytes();  
        int bit;  
        for (int i = 0; i < bs.length; i++) {  
            bit = (bs[i] & 0x0f0) >> 4;  
            sb.append(chars[bit]);  
            bit = bs[i] & 0x0f;  
            sb.append(chars[bit]);  
        }  
        return sb.toString();  
    }  
      
    /** 
     *  
     * 十六进制转换字符串 
     */  
      
    public static String hexStr2Str(String hexStr) {  
        String str = "0123456789ABCDEF";  
        char[] hexs = hexStr.toCharArray();  
        byte[] bytes = new byte[hexStr.length() / 2];  
        int n;  
        for (int i = 0; i < bytes.length; i++) {  
            n = str.indexOf(hexs[2 * i]) * 16;  
            n += str.indexOf(hexs[2 * i + 1]);  
            bytes[i] = (byte) (n & 0xff);  
        }  
        return new String(bytes);  
    }  
      
    /** 
     * bytes转换成十六进制字符串 
     */  
    public static String byte2HexStr(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1)  
                hs = hs + "0" + stmp;  
            else  
                hs = hs + stmp;  
            // if (n<b.length-1) hs=hs+":";  
        }  
        return hs.toUpperCase();  
    }  
      
    private static byte uniteBytes(String src0, String src1) {  
        byte b0 = Byte.decode("0x" + src0).byteValue();  
        b0 = (byte) (b0 << 4);  
        byte b1 = Byte.decode("0x" + src1).byteValue();  
        byte ret = (byte) (b0 | b1);  
        return ret;  
    }  
      
    /** 
     * 十六进制字符串转换成bytes 
     */  
    public static byte[] hexStr2Bytes(String src) {  
        int m = 0, n = 0;  
        int l = src.length() / 2;  
        System.out.println(l);  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            m = i * 2 + 1;  
            n = m + 1;  
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));  
        }  
        return ret;  
    }  
      
    /** 
    * 将字符串转成unicode 
    * @param str 待转字符串 
    * @return unicode字符串 
    */ 
    public static String str2Unicode(String str) 
    { 
	    str = (str == null ? "" : str); 
	    String tmp; 
	    StringBuffer sb = new StringBuffer(1000); 
	    char c; 
	    int i, j; 
	    sb.setLength(0); 
	    for (i = 0; i < str.length(); i++) 
	    { 
		    c = str.charAt(i); 
		    sb.append("\\u"); 
		    j = (c >>>8); //取出高8位 
		    tmp = Integer.toHexString(j); 
		    if (tmp.length() == 1) 
		    sb.append("0"); 
		    sb.append(tmp); 
		    j = (c & 0xFF); //取出低8位 
		    tmp = Integer.toHexString(j); 
		    if (tmp.length() == 1) 
		    sb.append("0"); 
		    sb.append(tmp); 
	    } 
	    return (new String(sb)); 
    }  
      
    /** 
    * 将unicode 字符串 
    * @param str 待转字符串 
    * @return 普通字符串 
    */ 
    public static String unicode2Str(String str) 
    { 
	    str = (str == null ? "" : str); 
	    if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回 
	    	return str; 
	    StringBuffer sb = new StringBuffer(1000); 
	    for (int i = 0; i < str.length() - 6;) 
	    { 
		    String strTemp = str.substring(i, i + 6); 
		    String value = strTemp.substring(2); 
		    int c = 0; 
		    for (int j = 0; j < value.length(); j++) 
		    { 
			    char tempChar = value.charAt(j); 
			    int t = 0; 
			    switch (tempChar) 
			    { 
			    case 'a': 
				    t = 10; 
				    break; 
			    case 'b': 
				    t = 11; 
				    break; 
			    case 'c': 
				    t = 12; 
				    break; 
			    case 'd': 
				    t = 13; 
				    break; 
			    case 'e': 
				    t = 14; 
				    break; 
			    case 'f': 
				    t = 15; 
				    break; 
			    default: 
				    t = tempChar - 48; 
				    break; 
			    } 
			    c += t * ((int) Math.pow(16, (value.length() - j - 1))); 
			} 
			sb.append((char) c); 
			i = i + 6; 
	    } 
	    return sb.toString(); 
	}   
}
