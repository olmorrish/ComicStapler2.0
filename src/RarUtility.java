import java.io.File;
import java.io.FileOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;

/*
 * Code is based on:
 * @author ikka -- https://gist.github.com/ioikka/c19465d30c0b08d6894b
 * Note that archiving a rar is not required by the program
 */
public class RarUtility {

	public void unzip(String src, String destination) throws Exception {
		
		File archiveFile = new File(src);
		File destDir = new File(destination);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
		
		@SuppressWarnings("resource")
		Archive archive = new Archive(new FileVolumeManager(archiveFile));
	    FileHeader fileHeader = archive.nextFileHeader();
	    
	    while (fileHeader != null) {
			String extractedFileName = fileHeader.getFileNameString().trim();
			FileOutputStream fileOutputStream = new FileOutputStream(destination + "\\" + extractedFileName);
			archive.extractFile(fileHeader, fileOutputStream);
			fileOutputStream.close();
			fileHeader = archive.nextFileHeader();
	    }
	}
	

}
