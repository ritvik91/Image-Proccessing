import java.io.File;
import javax.swing.filechooser.FileFilter;


abstract class   JPEGImageFileFilter extends FileFilter implements java.io.FileFilter
   {
    
     public boolean accept(File f)
    {
    if (f.getName().toLowerCase().endsWith(".jpeg")) return true;
    if (f.getName().toLowerCase().endsWith(".jpg")) return true;
  return false;
    }
  public String getDescription()
    {
  return "JPEG files";
   }
    }
