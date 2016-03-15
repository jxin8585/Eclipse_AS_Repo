import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class FileHelper {
	
//	private static final String TAG = "FILEHELPER";
	
	/**
	 * 判断指定的文件是否存在。
	 * @param fileName 要判断的文件的文件名
	 * @return 存在时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean isFileExist(String fileName) {
	     return new File(fileName).isFile();
	   }
	
	public static File newFile(String fileDir, String fileName) {
		File file = null;
		try {
			file = new File(fileDir, fileName);
//			file = new File(FILEPATH, fileName);
			File parent = file.getParentFile(); 
			if(parent!=null && parent.exists()==false){ 
				parent.mkdirs(); 
			} 
			file.delete();
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	public static void writeFile(File file, byte[] data, int offset, int count)
			throws IOException {

		FileOutputStream fos = new FileOutputStream(file, true);
		fos.write(data, offset, count);
		fos.flush();
		fos.close();
	}
	
	public static byte[] readFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			int leng = bis.available();
			byte[] b = new byte[(int)file.length()];
//			bis.read(b, 0, leng);
			fis.read(b);
			fis.close();
			return b;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("");
			return sb.toString().getBytes();
		}
/*		StringBuffer sb = new StringBuffer();		
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			int leng = bis.available();
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader bufReader = new BufferedReader(isr);
	     
		    String lineSeparator = System.getProperty("line.separator");
		    String line = "";
		    while( ( line = bufReader.readLine() ) != null)
		    {
		        sb.append(line);
		        sb.append(lineSeparator);
		    }
		    bufReader.close();
			fis.close();
		} else {
			sb.append("");
		}
		return sb.toString();*/
	}
	
	public static byte[] readFile(File file) throws IOException {
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			int leng = bis.available();
			byte[] b = new byte[(int)file.length()];
//			bis.read(b, 0, leng);
			fis.read(b);
			fis.close();
			return b;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("File is not exist...");
			return sb.toString().getBytes();
		}
	}
	
	public static String readFileContentStr(File readFile)
    {
        String readOutStr = null;
         
        try {
//          //BufferedReader bufReader = new BufferedReader(new FileReader(fullFilename));
//          InputStreamReader isr = new InputStreamReader(new FileInputStream(fullFilename), "UTF-8");
//          BufferedReader bufReader = new BufferedReader(isr);
//         
//          String lineSeparator = System.getProperty("line.separator");
//         
//          String line = "";
//          while( ( line = bufReader.readLine() ) != null)
//          {
//              readOutStr += line + lineSeparator;
//          }
//          bufReader.close();
         
            DataInputStream dis = new DataInputStream(new FileInputStream(readFile));
            try {
                long len = readFile.length();
                if (len > Integer.MAX_VALUE) throw new IOException("File is too large, was "+len+" bytes.");
                byte[] bytes = new byte[(int) len];
                dis.readFully(bytes);
                readOutStr = new String(bytes, "UTF-8");
//              readOutStr = new String(bytes, "GB2312");
//              readOutStr = new String(bytes, "Big5");
            } finally {
                dis.close();
            }
        } catch (IOException e) {
            readOutStr = null;
 
            //e.printStackTrace();
        }
        return readOutStr;
    }
	
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {

		}
	}

	public static void serialize(Object obj, File file) throws Exception {
//		Log.d(TAG, Thread.currentThread().getName() + "---->Serialize Begin...");
		ObjectOutputStream oos = null;
		OutputStream out = new FileOutputStream(file);
		oos = new ObjectOutputStream(out);
		oos.writeObject(obj);
		oos.close();
//		Log.d(TAG, Thread.currentThread().getName() + "---->Serialize Completed!");
	}
	
	public static String convertStreamToString(InputStream is) {      
        /*  
          * To convert the InputStream to String we use the BufferedReader.readLine()  
          * method. We iterate until the BufferedReader return null which means  
          * there's no more data to read. Each line will appended to a StringBuilder  
          * and returned as String.  
          */     
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
     
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
        return sb.toString();      
	}
	
	public static final byte[] input2ByteArray(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[4096];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
	
	/**
	 * 基于arraycopy合并两个byte[] 数组
	 * @param byte[]  bytes1
	 * @param byte[]  bytes2
	 * @return   byte[]   bytes3 
	 */
	public static byte[] combineTwoBytes(byte[] bytes1, byte[] bytes2) {
		byte[] bytes3 = new byte[bytes1.length + bytes2.length];
		System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
		System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);
		return bytes3;
	}
	
    public static String getFileNameNoEx(String filename) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int dot = filename.lastIndexOf('.'); 
            int sep = filename.lastIndexOf(File.separator);
            int start = 0;
            if (sep > 0) {
				start = sep;
			}
            if ((dot >-1) && (dot < (filename.length()))) { 
                return filename.substring(start, dot); 
            } 
        } 
        return filename; 
    } 
}