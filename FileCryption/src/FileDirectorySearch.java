import java.io.*; 
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FileDirectorySearch {
	/**
	 * ��ѯָ��Ŀ¼�µ������ļ�
	 * @param args
	 */ 
//	public static void main(String[] args) { 
	   // TODO Auto-generated method stub
//		FileDirectorySearch star=new FileDirectorySearch(); 
//	   String path="d:"; 
//	   File war=new File(path); 
//	   int count=star.findTxtFileCount(war,"exe"); 
//	   System.out.println("----: " + count);
//		
//		File[] files = star.getFiles("d:/share", "*");
//		for(int i=0;i<files.length;i++){
//			System.out.println(files[i]);
//		}
//		
//	} 
	/**
	 * ������ѯָ��Ŀ¼���ļ��ķ���
	 * 
	 * @param allList
	 *            ָ��Ŀ¼
	 * @param endName
	 *            ָ���ԡ�����β���ļ�
	 * @return �õ����ļ���Ŀ
	 */ 
	public int findTxtFileCount(File allList,String endName){ 
	   // 
	   int textCount=0; 
	// ����fileArray���ֵ�����
	File[] fileArray= allList.listFiles(); 
	// ���������һ�����ļ���Ϊ�����allList ����0
	if(null==fileArray){ 
	     return 0; 
	    } 
	// ƫ��Ŀ¼�µ��ļ�
	for(int i=0;i<fileArray.length;i++){ 
	   // ����Ǹ�Ŀ¼
	       if(fileArray[i].isDirectory()){ 
	     // System.out.println("Ŀ¼: "+fileArray[i].getAbsolutePath());
	        // �ݹ����
	        textCount+=findTxtFileCount(fileArray[i].getAbsoluteFile(),endName); 
	     // ������ļ�
	      }else if(fileArray[i].isFile()){ 
	     // ������ԡ�����β���ļ�
	     if(fileArray[i].getName().endsWith(endName)){ 
	        // չʾ�ļ�
	        System.out.println("exe�ļ�: "+fileArray[i].getAbsolutePath()); 
	        // �����ԡ�����β���ļ����
	        textCount++; 
	     } 
	     } 
	} 
	return textCount; 
} 
	
	/**
	    * �ڱ��ļ����²���
	    * @param s String �ļ���
	    * @return File[] �ҵ����ļ�
	    */
	   public static File[] getFiles(String s)
	   {
	     return getFiles("./",s);
	   }
	  
	   /**
	    * ��ȡ�ļ�
	    * ���Ը���������ʽ����
	    * @param dir String �ļ�������
	    * @param s String �����ļ������ɴ�*.?����ģ����ѯ
	    * @return File[] �ҵ����ļ�
	    */
	   public static File[] getFiles(String dir,String s) {
	     //��ʼ���ļ���
	     File file = new File(dir);

	     s = s.replace('.', '#');
	     s = s.replaceAll("#", "\\\\.");
	     s = s.replace('*', '#');
	     s = s.replaceAll("#", ".*");
	     s = s.replace('?', '#');
	     s = s.replaceAll("#", ".?");
	     s = "^" + s + "$";

//	     System.out.println(s);
	     Pattern p = Pattern.compile(s);
	     ArrayList<File> list = filePattern(file, p);

	     File[] rtn = new File[list.size()];
	     list.toArray(rtn);
	     return rtn;
	   }

	   /**
	    * @param file File ��ʼ�ļ���
	    * @param p Pattern ƥ������
	    * @return ArrayList ���ļ����µ��ļ���
	    */

	   public static ArrayList<File> filePattern(File file, Pattern p) {
	     if (file == null) {
	       return null;
	     }
	     else if (file.isFile()) {
	       Matcher fMatcher = p.matcher(file.getName());
	       if (fMatcher.matches()) {
	         ArrayList<File> list = new ArrayList<File>();
	         list.add(file);
	         return list;
	       }
	     }
	     else if (file.isDirectory()) {
	       File[] files = file.listFiles();
	       if (files != null && files.length > 0) {
	         ArrayList<File> list = new ArrayList<File>();
	         for (int i = 0; i < files.length; i++) {
	           ArrayList<File> rlist = filePattern(files[i], p);
	           if (rlist != null) {
	             list.addAll(rlist);
	           }
	         }
	         return list;
	       }
	     }
	     return null;
	   }



}

