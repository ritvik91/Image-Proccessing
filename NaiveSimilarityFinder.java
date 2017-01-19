import com.sun.media.jai.widget.DisplayJAI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

 class NaiveSimilarityFinder extends JFrame
  {
   
		private Picture pic = new Picture(256,256);
		private Picture pic1 = new Picture(256,256);
	    private Picture pic3 = new Picture(256,256);
  private Color[][] signature;
  private static final int baseSize = 300;
  private static final String basePath = "D:\\sachin\\major project";
  
  NaiveSimilarityFinder(File reference) throws IOException
  {
	parentwindow pw= new parentwindow();
    
 //   super("Naive Similarity Finder");
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    RenderedImage ref = rescale(ImageIO.read(reference));
    cp.add(new DisplayJAI(ref), BorderLayout.WEST);//this class extends Jpanel in order to support layout managment
    signature = calcSignature(ref);
    File[] others = getOtherImageFiles(reference);
    JPanel otherPanel = new JPanel(new GridLayout(others.length, 2));//for right side panel images
    cp.add(new JScrollPane(otherPanel), BorderLayout.CENTER);
      
    RenderedImage[] rothers = new RenderedImage[others.length];//to convert others into raster images
    double[] distances = new double[others.length];
    for (int o = 0; o < others.length; o++)
      {
        rothers[o] = rescale(ImageIO.read(others[o]));
        distances[o] = calcDistance(rothers[o]);
       }
       
       for (int p1 = 0; p1 < others.length - 1; p1++)
         for (int p2 = p1 + 1; p2 < others.length; p2++)
          {
          if (distances[p1] > distances[p2])
            {
            double tempDist = distances[p1];
            distances[p1] = distances[p2];
            distances[p2] = tempDist;
            RenderedImage tempR = rothers[p1];
            rothers[p1] = rothers[p2];
            rothers[p2] = tempR;
            File tempF = others[p1];
            others[p1] = others[p2];
            others[p2] = tempF;
           }
         }
    
     for (int o = 0; o < others.length; o++)
         {
    	 if(distances[o]<2400d)
    	 {
             ImageIcon abc = new ImageIcon(ImageIO.read(others[o]));
             JButton selectButton=new JButton(abc);
             selectButton.setPreferredSize(new Dimension(300,300));
       selectButton.addActionListener(new ButtonListener("C:/Users/bhavya/Documents/image.orig/"+others[o].getName()));
       otherPanel.add(selectButton);
       //otherPanel.add(new DisplayJAI(rothers[o]));
       JLabel ldist = new JLabel("<html>" + others[o].getName() + "<br>"
       + String.format("% 13.3f", distances[o]) + "</html>");
       ldist.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 36));
       System.out.printf("<td class=\"simpletable legend\"> "+
       "<img src=\"MiscResources/ImageSimilarity/icons/miniicon_%s\" "+
       "alt=\"Similarity result\"><br>% 13.3f</td>\n", others[o].getName(),distances[o]);
       otherPanel.add(ldist);
      // ldist.addComponentListener((ComponentListener) new CallImagePro());
       
      
    	 }
    	 }
   
     pack();
     setVisible(true);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }

 
  
  
    private RenderedImage rescale(RenderedImage i)
      {
    float scaleW = ((float) baseSize) / i.getWidth();
    float scaleH = ((float) baseSize) / i.getHeight();
  
    ParameterBlock pb = new ParameterBlock();
    pb.addSource(i);//source image
    pb.add(scaleW);//xscale
    pb.add(scaleH);//yscale
    pb.add(0.0F);//x translation
    pb.add(0.0F);//y translation
    pb.add(new InterpolationNearest());
   
     return JAI.create("scale", pb);//write newly resized image to an output
    }
  
 
   private Color[][] calcSignature(RenderedImage i)
    {
 
    Color[][] sig = new Color[5][5];
    float[] prop = new float[]
       {1f / 10f, 3f / 10f, 5f / 10f, 7f / 10f, 9f / 10f};//float literals 
     for (int x = 0; x < 5; x++)
       for (int y = 0; y < 5; y++)
         sig[x][y] = averageAround(i, prop[x], prop[y]);
    return sig;
      }
  
   
   private Color averageAround(RenderedImage i, double px, double py)
     {
   
     RandomIter iterator = RandomIterFactory.create(i, null);//randomiter is an iterator that allows read only access
     //to any sample within its bounding rectangle.This flexibilty will genrally exact a corresponding price in speed n setup overhead
     //an instance of randomiter may be obtained by means of RandomIterfactory.create() method,which returns an opaque object
     //implementing this interface.
     //RacndomIterFactory is a factory class to instantiate instances of the RandomIter and WriteableRandomIter interfaces
     //on sources of type raster,renderedImage and WirtablerenderedImage.
    
     double[] pixel = new double[3];
     double[] accum = new double[3];
     int sampleSize = 15;
     int numPixels = 0;
    
     for (double x = px * baseSize - sampleSize; x < px * baseSize + sampleSize; x++)
       {
       for (double y = py * baseSize - sampleSize; y < py * baseSize + sampleSize; y++)
         {
          iterator.getPixel((int) x, (int) y, pixel);//returns the samples of the specified pixel from the image in an array.
          accum[0] += pixel[0];
        accum[1] += pixel[1];
          accum[2] += pixel[2];
          numPixels++;
          }
        }
     
      accum[0] /= numPixels;
     accum[1] /= numPixels;
      accum[2] /= numPixels;
      return new Color((int) accum[0], (int) accum[1], (int) accum[2]);
     }
  
  
   private double calcDistance(RenderedImage other)
    {
     
      Color[][] sigOther = calcSignature(other);
      
      double dist = 0;
      for (int x = 0; x < 5; x++)
      for (int y = 0; y < 5; y++)
         {
       int r1 = signature[x][y].getRed();
         int g1 = signature[x][y].getGreen();
         int b1 = signature[x][y].getBlue();
       int r2 = sigOther[x][y].getRed();
         int g2 = sigOther[x][y].getGreen();
          int b2 = sigOther[x][y].getBlue();
        double tempDist = Math.sqrt((r1 - r2) * (r1 - r2) + (g1 - g2)
             * (g1 - g2) + (b1 - b2) * (b1 - b2));
        dist += tempDist;
         }
     return dist;
    }
   
    private File[] getOtherImageFiles(File reference)
      {
    File dir = new File(reference.getParent());
    File[] others = dir.listFiles(new JPEGImageFileFilter() {});//returns an array of abstract pathnames denoting
    //the files and directories in the directorydenoted by this abstract pathname that specify the specified filter.
     return others;
      }
  
    
    
    private class ButtonListener implements ActionListener{
private String path;
        private ButtonListener(String path) {
            System.out.println(path);
            this.path=path;
        }
    
    	public void actionPerformed(ActionEvent e)
    	{
            System.out.println(path);
            
            ImagePro imagePro = new ImagePro(path);
    	    
    	 
   
  
        
    	}
    
    }
    
    
    
    
   public static void main(String[] args) throws IOException
      {
    
	   parentwindow pw= new parentwindow();
  /*   JFileChooser fc = new JFileChooser(basePath);
     fc.setFileFilter(new JPEGImageFileFilter() {});
      int res = fc.showOpenDialog(null);
     
     if (res == JFileChooser.APPROVE_OPTION)
       {
        File file = fc.getSelectedFile();
        new NaiveSimilarityFinder(file);
        }
      
      else
        {
        JOptionPane.showMessageDialog(null,
            "You must select one image to be the reference.", "Aborting...",
            JOptionPane.WARNING_MESSAGE);
        }
  */    
   
      }
  
  } 