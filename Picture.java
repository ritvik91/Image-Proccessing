 import java.awt.*;
 import java.awt.Desktop;
 import java.awt.Color; 
 import javax.swing.*; 
 import javax.swing.border.*; 
 import javax.swing.event.*; 
 import java.io.File;
 import java.awt.event.*;
 import javax.imageio.ImageIO;
 import java.awt.image.BufferedImage;
 import java.io.*;
 import java.net.URL;
 import java.awt.image.Kernel;
 import java.awt.image.ConvolveOp;
 import java.awt.Graphics;
 import java.awt.image.BufferedImageOp;
 import java.awt.MenuBar;

public final class Picture implements ActionListener {
    private BufferedImage image;    // the rasterized image
    private JFrame frame;           // on-screen view

    // create a blank w-by-h image
    public Picture(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
    }

    // create an image by reading in the PNG, GIF, or JPEG from a filename
    public Picture(String filename) {

        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) { url = new URL(filename); }
                image = ImageIO.read(url);
            }
        }
        catch (IOException e) {
            // e.printStackTrace();
            
	JOptionPane.showMessageDialog(null,"Incompatible file format! Please select an image file.","ERROR !",JOptionPane.ERROR_MESSAGE);
	throw new RuntimeException("Could not open file: " + filename);
        }

        // check that image was read in
        if (image == null) {
            
	JOptionPane.showConfirmDialog(null, "Select correct image format file");	
	throw new RuntimeException("Invalid image file: " + filename);
        }
    }

    // create an image by reading in the PNG, GIF, or JPEG from a file
    public Picture(File file) {
        try { image = ImageIO.read(file); }
        catch (IOException e) {
            
	    JOptionPane.showMessageDialog(null,"Incompatible file format! Please select an image file.","ERROR !",JOptionPane.ERROR_MESSAGE);
	    e.printStackTrace();
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null) {
        JOptionPane.showMessageDialog(null,"Incompatible file format! Please select an image file.","ERROR !",JOptionPane.ERROR_MESSAGE); 
	throw new RuntimeException("Invalid image file: " + file);
        }
    }

    // to embed in a JPanel, JFrame or other GUI widget
    public JLabel getJLabel() {
        if (image == null) { return null; }         // no image available
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    public BufferedImage retImage()
    {
	return image;
    }
    

    // accessor methods
    public int height() { return image.getHeight(null); }
    public int width()  { return image.getWidth(null);  }

    // return Color of pixel (i, j)
    public Color get(int i, int j) {
	return new Color(image.getRGB(i, j));
    }

   // Method Called in NegativeListener
    public int getrgb(int i,int j)
   {
	return image.getRGB(i,j);
   }

    // change color of pixel (i, j) to c
    public void set(int i, int j, Color c) {
	image.setRGB(i, j, c.getRGB());
    }

   // Method called in NegativeListener

    public void set(int i,int j,int rgb)
    {
	image.setRGB(i,j,rgb);
    }		

    // save to given filename - suffix must be png, jpg, or gif
    public void save(String filename) 
      { save(new File(filename)); }

    // save to given filename - suffix must be png, jpg, or gif
    public void save(File file) {
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png")) {
            try { ImageIO.write(image, suffix, file); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else {
	    JOptionPane.showMessageDialog(null,"Incompatible file name extension! Please select a valid file name extension. (.jpg, .png, .bmp)","ERROR !",JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    // open a save dialog when the user selects "Save As" from the menu
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame,
                             "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }



    // test client: read in input file and display
    public static void main(String[] args) 
    {
        Picture pic = new Picture(args[0]);
        Picture pic1 = new Picture(args[0]);
            }

}
