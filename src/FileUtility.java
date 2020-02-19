import java.io.File;

public abstract class FileUtility {

	/*
	 * 
	 */
	public static File[] fetchInnerFiles(String mergeDirectory) throws IllegalArgumentException, NullPointerException {
		File merge = new File(mergeDirectory);
		
		if (!merge.isDirectory()) {
			throw (new IllegalArgumentException());
		}
		
		else {
			return merge.listFiles();
		}	
	}
	
	
	/*
	 * removes all invalid file types and notify the user
	 */
	public static File[] enforceExtensions(File[] files) {
		
		//check extensions and mark invalids as null
		for(int i =0; i<files.length; i++) {
			String ext = files[i].toString(); 		
			ext = getExtension(files[i]);
			//ext.toLowerCase();
			if(!ext.equals("cbr") && !ext.equals("cbz") && !ext.equals("zip") && !ext.equals("rar")) {
				System.out.println("NOTE: File \"" 
						+ files[i].toString().substring(files[i].toString().lastIndexOf('\\') + 1) 
						+ "\" was not gathered. Invalid extension \"" + ext + "\".");
				files[i] = null;
			}
		}
		
		//create return array from remaining files, first counting the number of non-nulls
		int retLen = 0;
		
		for(int i = 0; i<files.length; i++) {
			if(files[i]!=null) {
				retLen++;
			}
		}

		//copy file references
		File[] ret = new File[retLen];
		int i=0, j=0;
		while(i<files.length) {
			if(files[i]!=null) {
				ret[j] = files[i];
				j++;
			}
			i++;
		}
		
		return ret;
	}
	
	/*
	 * 
	 */
	public static void renameAllFiles(String directory, String fileNameBase, int issueNumber) {
		File[] toRename = fetchInnerFiles(directory);
		
		//pad the issue number with zeroes
		String issueNumStr = Integer.toString(issueNumber);
		while(issueNumStr.length()<3) {
			issueNumStr = "0" + issueNumStr;
		}
		
		int pgNum = 0;
		for(File f : toRename) {
			
			f.renameTo(new File(directory + "//" 
					+ fileNameBase + "_" + issueNumStr + "_" + pgNum 
					+ "." + FileUtility.getExtension(f)));
			pgNum++;
		}
	}
	
	/*
	 * 
	 */
	public static String getExtension(File file) {
		return file.toString().substring(file.toString().lastIndexOf('.') + 1);
	}
	
	/*
	 * pull file up one directory
	 */
	public static void pullUp (String dir) {
		String endName = dir.substring(dir.lastIndexOf("\\"));
		
		//String priorName = dir.substring(0, dir.lastIndexOf("\\"));
		//priorName = priorName.substring(0, dir.lastIndexOf("\\"));
		
		File file = new File(dir);
		File parent = file.getParentFile().getParentFile();
		file.renameTo(new File(parent.toString() + "\\" + endName));
	}
	
}
