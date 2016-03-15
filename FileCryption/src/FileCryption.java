import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileCryption {
	
	public static String EncryptCode(String input,String key){
		String strencryptRC4 = Rc4.encry_RC4_string(input, key);
		String output = Base64.encodeToString(Rc4.hexStr2Bytes(strencryptRC4), Base64.DEFAULT);
		return output;
	}
	
	public static String EncryptFileCode(String input,String key){
		byte[] encryptRC4 = Rc4.encry_RC4_byte(input, key);
		String output = Base64.encodeToString(encryptRC4, Base64.NO_WRAP);
		return output;
	}
	
	public static String DecryptCode(String input,String key){
		byte[] decode = Base64.decode(input, Base64.DEFAULT); 
		String strDecryptRC4 = Rc4.decry_RC4_string(Rc4.byte2HexStr(decode), key);
		return strDecryptRC4;
	}
	
	public static String EncryptXorCode(String input,String key){
		if (input == null || key == null) {
			return "";
		}
		byte[] data = input.getBytes();
		byte[] keyData = key.getBytes();
		int keyIndex = 0 ;
		for(int x = 0 ; x < input.length() ; x++) {
			data[x] = (byte)(data[x] ^ keyData[keyIndex]);
			if (++keyIndex == keyData.length){
				keyIndex = 0;
			}
		}
		String output = Base64.encodeToString(data, Base64.DEFAULT);
		return output;
	}
	
	public static String DecryptXorCode(String input,String key){
		byte[] decode = Base64.decode(input, Base64.DEFAULT);
		String decodeString = new String(decode);
		if (decodeString == null || key == null) {
			return "";
		}
		byte[] data = decodeString.getBytes();
		byte[] keyData = key.getBytes();
		int keyIndex = 0 ;
		for(int x = 0 ; x < decodeString.length() ; x++) {
			data[x] = (byte)(data[x] ^ keyData[keyIndex]);
			if (++keyIndex == keyData.length){
				keyIndex = 0;
			}
		}
		String output = null;
		output = new String(data);
		return output;
	}
	
	public static void main(String[] args) {
		File[] files = null;
		if (args.length < 2) {
			return;
		} else if (args.length == 2) {
			int sep = args[1].lastIndexOf(File.separator);
			if (sep >= 0) {
				files = FileDirectorySearch.getFiles(args[1].substring(0, sep+1), args[1].substring(sep+1));
			} else if (args[1].equalsIgnoreCase("alltxt")) {
				files = FileDirectorySearch.getFiles('.'+File.separator, "*.txt");
			} else {
				File file = new File('.'+File.separator, args[1]);
				files = new File[] {file};
			}
		} else {
			ArrayList<File> list = new ArrayList<File>();
			for (int i = 1; i < args.length; i++) {
				File file = new File('.'+File.separator, args[i]);
				list.add(file);
			}
			files = new File[list.size()];
		    list.toArray(files);
		}
		if (files == null || files.length <= 0) {
			return;
		}
		for (int i = 0; i < files.length; i++)  {
			String inputData = null;
			String outputData = null;
			try {
				inputData = new String(FileHelper.readFile(files[i].getPath()), "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] b = {-95, -94, -93, -92};
	    	String strCryptKey = Rc4.byte2HexStr(b);
	    	File distFile = null;
			switch (Integer.valueOf(args[0]).intValue())
			{
			case 1:		//¼ÓÃÜ
				outputData = EncryptCode(inputData, strCryptKey);
				distFile = FileHelper.newFile(files[i].getParent(), FileHelper.getFileNameNoEx(files[i].getName())+"_ENC.txt");
				break;

			case 2: 	//½âÃÜ
				outputData = DecryptCode(inputData, strCryptKey);
				distFile = FileHelper.newFile(files[i].getParent(), FileHelper.getFileNameNoEx(files[i].getName())+"_DEC.txt");
				break;
			default:
				return;
			}
			try {
				FileHelper.writeFile(distFile, outputData.getBytes(), 0, outputData.getBytes().length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
