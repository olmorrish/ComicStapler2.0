import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
 * Code is based on:
 * @author https://www.codejava.net/java-se/file-io/programmatically-extract-a-zip-file-using-java
 * 		   https://www.codejava.net/java-se/file-io/how-to-compress-files-in-zip-format-in-java
 */
public class ZipUtility {

	private static final int BUFFER_SIZE = 4096;
	
	
	public void unzip(String src, String destination) throws IOException {
		File destDir = new File(destination);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(src));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destination + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
	}

	/*
	 * 
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
	
	/*
	 * 
	 */
	public void zip(String src, String dest, String fileName) throws IOException {
		
		File[] filePaths = FileUtility.fetchInnerFiles(src);
		
		try {
            //File firstFile = filePaths[0];
            String zipFileName = fileName;
 
            FileOutputStream fos = new FileOutputStream(dest + "\\" + zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
 
            for (File aFile : filePaths) {
                zos.putNextEntry(new ZipEntry(aFile.getName()));
 
                byte[] bytes = Files.readAllBytes(Paths.get(aFile.toString()));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }
 
            zos.close();
 
        } catch (FileNotFoundException ex) {
            System.err.println("A file does not exist: " + ex);
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }
	}
}
