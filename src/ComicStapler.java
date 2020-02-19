import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ComicStapler {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ComicStapler utility booted.");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		String dir = "";
		
		while(!dir.toLowerCase().equals("exit")) {
			
			System.out.println("Please enter a directory of comic files to combine.");
			dir = keyboard.nextLine();
	
			try{
				staple(dir, keyboard);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		keyboard.close();
		System.exit(0);
	}
	
	public static void staple(String mergeDirectory, Scanner keyboard) throws IOException {

		//gather all the files in the given directory if able
		File[] files = null;
		try {
			files = FileUtility.fetchInnerFiles(mergeDirectory);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println("Pathname for merge was not a valid directory.");
			return;
		}
		
		//enforce extensions to be CBR or CBZ
		File[] zips = FileUtility.enforceExtensions(files);
		int numIssues = zips.length;
		System.out.println("System found " + numIssues + " valid comic file(s).");
		
		//create temp directory
		System.out.println("Please enter file name for merged file. Do not include a file extension.");
		String fileName = keyboard.nextLine();
		new File(mergeDirectory.toString() + "\\" + fileName).mkdirs();	
		String destination = mergeDirectory.toString() + "\\" + fileName;
		 
		ZipUtility zipper = new ZipUtility();
		RarUtility rarZipper = new RarUtility();
		
		//act on each file, unzipping and renaming its contents 
		for(int i=0; i<zips.length; i++) {
			
			System.out.println("Working on file " + (i+1) + " of " + zips.length);
			
			//create a directory for this specific issue to unzip to 
			String issueDir = destination + "\\issuetemp_" + Integer.toString(i); 
			new File(issueDir).mkdirs();
			
			//unzip the file
			String ext = FileUtility.getExtension(zips[i]);
			try {
				if(ext.equals("cbz") || ext.equals("zip")){			//zip archive condition
					zipper.unzip(zips[i].toString(), issueDir);
				}
				else if(ext.equals("cbr") || ext.equals("rar")) {						//rar archive condition
					rarZipper.unzip(zips[i].toString(), issueDir);
				}
				else {
					System.err.println("Error: Unknown file type encountered. Extension enforcement may have failed.");
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.err.println("Error unpacking an archive file.");
			} 

			//with the file unzipped to its own folder, now rename and relocate all pages
			FileUtility.renameAllFiles(issueDir, fileName, i);
		}
		
		//directories are destination\\issue_0, and so on; 
		//mergeDirectory\\fileName\\issue_0
		
		
		//pull all inner files into the master destination
		for(int i = 0; i<numIssues; i++) {	
			File[] pages = FileUtility.fetchInnerFiles(destination + "\\issuetemp_" + i);
			for(File f : pages) {
				FileUtility.pullUp(f.toString());
			}
			
			File temp = new File(destination + "\\issuetemp_" + i);
			temp.delete();
		}
		
		System.out.println("Zipping final file. This may take some time.");
		
		//zip the destination file into a cbz
		try {
			zipper.zip(destination, destination, fileName + ".cbz");
			FileUtility.pullUp(destination+"\\"+ fileName + ".cbz");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error zipping final file. Temporary files will be cleared.");
		}
		finally {
			//delete temp files
			File del = new File(destination);
			File[] delFiles = FileUtility.fetchInnerFiles(destination);
			for(File f : delFiles) {
				f.delete();
			}
			del.delete();
		}

		System.out.println(numIssues + " files were merged successfully into \"" + fileName + ".cbz\".");
		System.out.println("-------------------------------------------------------------");
	}
}
