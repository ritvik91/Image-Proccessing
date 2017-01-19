 import java.awt.*;
 import java.awt.Color; 
 import javax.swing.*; 
 import javax.swing.border.*; 
 import java.awt.event.*;
 import java.io.*;
 import java.awt.image.Kernel;
 import java.awt.image.ConvolveOp;
 import java.awt.image.BufferedImageOp;

 public final class ImagePro extends JFrame 
 { 
 	// Variables declaration 
 	private JLabel jLabel4; 
 	 private JLabel jLabel5; 
 	public JSplitPane jSplitPane2; 
 	public JPanel contentPane; 
 	public JFileChooser chooser = new JFileChooser();
	public Picture pic = new Picture(256,256);
	public Picture pic1 = new Picture(256,256);
    public Picture pic3 = new Picture(256,256);
    public String path=null;
	// End of variables declaration
  
 	public ImagePro() 
 	{ 
 		super(); 
 		initializeComponent(); 
 		this.setJMenuBar(createMenuBar());
 		this.setVisible(true); 
 	}

    public ImagePro(String ob) {
                super(); 
 		initializeComponent(); 
 		this.setJMenuBar(createMenuBar());
 		this.setVisible(true); 
                this.path=ob;
                System.out.println(path);
                pic  = new Picture(path);
		pic1 = new Picture(path);
		pic3 = new Picture(path);
		jSplitPane2.setLeftComponent(pic.getJLabel()); 
 		jSplitPane2.setRightComponent(pic1.getJLabel());
                		pack();
    }
  
 	private void initializeComponent() 
 	{ 
	//	ImageIcon image = new ImageIcon("edited.png");
 //		jLabel4 = new JLabel("Input",image,JLabel.CENTER); 
		jLabel4 = new JLabel();
 		jLabel5 = new JLabel(); 
 		jSplitPane2 = new JSplitPane(); 
 		contentPane = (JPanel)this.getContentPane(); 
 
 		jLabel4.setHorizontalAlignment(SwingConstants.CENTER); 
 		jLabel4.setText("input"); 
 		
 		// 
 		// jLabel5 
 		// 
 		jLabel5.setHorizontalAlignment(SwingConstants.CENTER); 
 		jLabel5.setText("output"); 
 		// 
 		// jSplitPane2 
 		// 
 		jSplitPane2.setLeftComponent(jLabel4); 
 		jSplitPane2.setRightComponent(jLabel5); 
 		jSplitPane2.setDividerLocation(250); 
 		// 
 		// contentPane 
 		// 
 		contentPane.setLayout(new GridLayout(1,1)); 
 		contentPane.add(jSplitPane2); 
 		contentPane.setBorder(new TitledBorder("")); 
 		contentPane.setBackground(new Color(255,100,100)); 
 		contentPane.setForeground(new Color(255,100,100)); 
 		contentPane.setFocusable(false); 
 		// 
 		// ImagePro 
 		// 
 		this.setTitle("ImagePro "); 
 		this.setLocation(new Point(15, 15)); 
 		this.setSize(new Dimension(530,300)); 
 		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
				
 	} 
          
	public JMenuBar createMenuBar() 
	{
        	JMenu menu,submenu;
        	JMenuItem menuItem;

        	// create the menu bar
        	JMenuBar menuBar = new JMenuBar();

        	// build the File menu
        	menu = new JMenu("File");
        	menu.setMnemonic(KeyEvent.VK_F);      // only needed for Alt-f keyboard shortcut
        	menuBar.add(menu);

        	menuItem = new JMenuItem("Open File...");
        	menuItem.addActionListener(new OpenFileListener());
			menuItem.setToolTipText("Open an Image File...");
        	menu.add(menuItem);

        	menuItem = new JMenuItem("Save As...");
        	menuItem.addActionListener(new SaveAsListener());
			menuItem.setToolTipText("Save an Image File...");
        	menu.add(menuItem);

        	// build the Process menu
        	menu = new JMenu("Process");
        	menu.setMnemonic(KeyEvent.VK_P);      // only needed for Alt-p keyboard shortcut
        	menuBar.add(menu);

			menuItem = new JMenuItem("Brighten");
       	 	menuItem.addActionListener(new BrightenListener());
			menuItem.setToolTipText("Brighten the image...");
        	menu.add(menuItem);
			
			menuItem = new JMenuItem("Darken");
       	 	menuItem.addActionListener(new DarkenListener());
			menuItem.setToolTipText("Darken the image...");
        	menu.add(menuItem);

		menuItem = new JMenuItem("Sharpen");
        	menuItem.addActionListener(new SharpenListener());
			menuItem.setToolTipText("Sharpen the image...");
        	menu.add(menuItem);

			menuItem = new JMenuItem("Grayscale");
       		menuItem.addActionListener(new GrayscaleListener());
			menuItem.setToolTipText("Turn the image into black-n-white image...");
        	menu.add(menuItem);

			menuItem = new JMenuItem("Blur");
        	menuItem.addActionListener(new BlurListener());
			menuItem.setToolTipText("Blur the image...");
        	menu.add(menuItem);
		
		// build the Distortion menu
        	menu = new JMenu("Distortion");
        	menu.setMnemonic(KeyEvent.VK_D);      // only needed for Alt-d keyboard shortcut
        	menuBar.add(menu);

        	menuItem = new JMenuItem("Flip Horizontal");
        	menuItem.addActionListener(new FlipHorizontalListener());
			menuItem.setToolTipText("Get the mirror image of the original picture...");
        	menu.add(menuItem);

			menuItem = new JMenuItem("Glass");
        	menuItem.addActionListener(new GlassListener());
			menuItem.setToolTipText("Makes image look like it's being seen through glass...");
        	menu.add(menuItem);

		//build help menu
		menu = new JMenu("Help");
        	menu.setMnemonic(KeyEvent.VK_H);      // only needed for Alt-h keyboard shortcut
        	menuBar.add(menu);

		menuItem = new JMenuItem("File - Help");
        	menuItem.addActionListener(new HelpListener());
			menuItem.setToolTipText("Get the help about file menu items ...");
        	menu.add(menuItem);

		menuItem = new JMenuItem("Process - Help");
        	menuItem.addActionListener(new HelpListener());
			menuItem.setToolTipText("Get the help about image processes...");
        	menu.add(menuItem);
	
		menuItem = new JMenuItem("Distortion - Help");
        	menuItem.addActionListener(new HelpListener());
			menuItem.setToolTipText("Get the help about image distortion options ...");
        	menu.add(menuItem);

return menuBar;
    	}

   	private class OpenFileListener implements ActionListener 
   	{
        	public void actionPerformed(ActionEvent e) 
		{
            		if (chooser.showOpenDialog(ImagePro.this) == JFileChooser.APPROVE_OPTION) 
			{
                		File file = chooser.getSelectedFile();
                		pic  = new Picture(file);
				pic1 = new Picture(file);
				pic3 = new Picture(file);
				jSplitPane2.setLeftComponent(pic.getJLabel()); 
 				jSplitPane2.setRightComponent(pic1.getJLabel());
                		pack();
            		}
        	}
    	}	

	// open a save dialog when the user selects "Save As" from the menu
    	private class SaveAsListener implements ActionListener 
    	{
        	public void actionPerformed(ActionEvent e) 
		{
            		if (chooser.showSaveDialog(ImagePro.this) == JFileChooser.APPROVE_OPTION) 
			{
                		File file = chooser.getSelectedFile();
                		pic1.save(file);
            		}
        	}
    	}


   	private class BrightenListener implements ActionListener
    	{
		public void actionPerformed(ActionEvent e) 
		{
			int w = pic1.width();
        		int h = pic1.height();

	       		float [] sharpenKernel =
	        	{	

            			0.0f, 0.0f,  0.0f,

          			0.0f,  1.2f, 0.0f,

            			0.0f, 0.0f,  0.0f

        		};
		
	        ConvolveOp sharpenOp = new ConvolveOp (new Kernel (3, 3, sharpenKernel), ConvolveOp.EDGE_NO_OP, null);
        	String ans;
	        ans = JOptionPane.showInputDialog(null, "on the original image??");
		if(ans.equals("yes"))
			{sharpenOp.filter(pic.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
            				{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
			}
		else
			{sharpenOp.filter(pic1.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
			}
		repaint();
	    	}
	}
 		
	private class DarkenListener implements ActionListener
    	{
		public void actionPerformed(ActionEvent e) 
		{
			int w = pic1.width();
        		int h = pic1.height();

	       		float [] sharpenKernel =
	        	{	

            			0.0f, 0.0f,  0.0f,

          			0.0f,  0.8f, 0.0f,

            			0.0f, 0.0f,  0.0f

        		};
		
	        ConvolveOp sharpenOp = new ConvolveOp (new Kernel (3, 3, sharpenKernel), ConvolveOp.EDGE_NO_OP, null);
        	String ans;
	        ans = JOptionPane.showInputDialog(null, "on the original image??");
		if(ans.equals("yes"))
			{sharpenOp.filter(pic.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
			}
		else
			{sharpenOp.filter(pic1.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
			}
		repaint();
	    	}
	}

private class GrayscaleListener implements ActionListener 
    	{
        	public void actionPerformed(ActionEvent e) 
		{
			int r, g, b,gray;
			String ans;
	        	ans = JOptionPane.showInputDialog(null, "on the original image??");
			if(ans.equals("yes"))
				{for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic.get(x, y);
						r = c.getRed(); g = c.getGreen(); b = c.getBlue();
						gray = (r+g+b)/3;
						Color c1 = new Color(gray,gray,gray);
						pic1.set(x,y,c1);
					}
				}
				}
			else
				{for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic1.get(x, y);
						r = c.getRed(); g = c.getGreen(); b = c.getBlue();
						gray = (r+g+b)/3;
						Color c1 = new Color(gray,gray,gray);
						pic1.set(x,y,c1);
					}
				}
				}
			repaint();
		}
    	}


private class BlurListener implements ActionListener 
    	{
        	public void actionPerformed(ActionEvent e) 
		{
			int w = pic1.width();
        		int h = pic1.height();
			float ninth = 1.0f / 9.0f;
        		float [] blurKernel =
        		{
           			ninth, ninth, ninth,
           			ninth, ninth, ninth,
           			ninth, ninth, ninth
        		};

        		BufferedImageOp blurOp = new ConvolveOp (new Kernel (3, 3, blurKernel));       			
			String ans;
	        	ans = JOptionPane.showInputDialog(null, "on the original image??");
			if(ans.equals("yes"))
				{blurOp.filter(pic.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
				}
			else
				{blurOp.filter(pic1.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
				}
			repaint();
		}
    	} 
private class SharpenListener implements ActionListener 
    	{
        	public void actionPerformed(ActionEvent e) 
		{
			int w = pic1.width();
        		int h = pic1.height();
			float [] sharpenKernel =
	        	{

            			0.0f, -1.0f,  0.0f,

          			-1.0f,  5.0f, -1.0f,

            			0.0f, -1.0f,  0.0f

        		};
		
	        ConvolveOp sharpenOp = new ConvolveOp (new Kernel (3, 3, sharpenKernel), ConvolveOp.EDGE_NO_OP, null);
        	String ans;
	        	ans = JOptionPane.showInputDialog(null, "on the original image??");
			if(ans.equals("yes"))
				{sharpenOp.filter(pic.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
				}	
			else
				{sharpenOp.filter(pic1.retImage(), pic3.retImage());
				for (int x = 0; x < pic1.width(); x++)
				{
					for (int y = 0; y < pic1.height(); y++) 
	            			{                    
						Color c = pic3.get(x, y);
						pic1.set(x,y,c);
					}
				}
				}	
			repaint();
		}
    	} 
// flip the image horizontally
    	private class FlipHorizontalListener implements ActionListener 
    	{
        	public void actionPerformed(ActionEvent e) 
		{
            		int width  = pic1.width();
            		int height = pic1.height();
            		String ans;
	        	ans = JOptionPane.showInputDialog(null, "on the original image??");
			if(ans.equals("yes"))
				{for (int y = 0; y < height; y++) 
				{   
	                		for (int x = 0; x < width / 2; x++) 
					{
	                    			Color c1 = pic.get(x, y);
	          				Color c2 = pic.get(width - x - 1, y);	
	                    			pic1.set(x, y, c2);
	                    			pic1.set(width - x - 1, y, c1);
	                		}
	            		}
				}
			else
				{for (int y = 0; y < height; y++) 
				{   
	                		for (int x = 0; x < width / 2; x++) 
					{
	                    			Color c1 = pic1.get(x, y);
	          				Color c2 = pic1.get(width - x - 1, y);	
	                    			pic1.set(x, y, c2);
	                    			pic1.set(width - x - 1, y, c1);
	                		}
	            		}
				}
            	repaint();
        	}
    	}
private class GlassListener implements ActionListener 
    	{
		public int random(int a, int b) 
		{
        		return a + (int) (Math.random() * (b-a+1));
		}
        	public void actionPerformed(ActionEvent e) 
		{
			int width  = pic1.width();
   			int height = pic1.height();
			String ans;
	        	ans = JOptionPane.showInputDialog(null, "on the original image??");
			if(ans.equals("yes"))
				{for (int i = 0; i < width; i++) 
				{
					for (int j = 0; j < height; j++) 
					{
	               			int ii = (width  + i + random(-5, 5)) % width;
	               			int jj = (height + j + random(-5, 5)) % height;
	               			Color c = pic.get(ii, jj);
	               			pic1.set(i, j, c);
	       				}
	       			}
				}
			else
				{for (int i = 0; i < width; i++) 
				{
					for (int j = 0; j < height; j++) 
					{
	               			int ii = (width  + i + random(-5, 5)) % width;
	               			int jj = (height + j + random(-5, 5)) % height;
	               			Color c = pic1.get(ii, jj);
	               			pic1.set(i, j, c);
	       				}
	       			}
				}
			repaint();
		}
    	}


private class HelpListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{ 
			Object ob= e.getSource();
			String s = e.getActionCommand();
			if(s.equals("File - Help"))
			{	
				JOptionPane.showMessageDialog(null, "UPLOAD AN IMAGE-\nThe user can upload any compatible image format for further processing of the image.\n\nSAVING AN IMAGE-\nThe processed image can be saved to any directory on the pc. The image will be saved in the same format as it was uploaded.");
			}
			if(s.equals("Process - Help"))
			{	
				JOptionPane.showMessageDialog(null, "IMAGE BRIGHTEN-\nThe main aim of this function is to enhance colour of every pixel present in the image, so as to brighten up the image.\n\nIMAGE DARKEN-\nThe main aim of this function is to decrease colour of every pixel present in the image, so as to darken up the image.\n\nIMAGE SHARPENING-\nThe main aim in image sharpening is to highlight fine detail in the image, or to enhance detail that has been blurred (perhaps due to noise or other effects, such as motion).\n\nIMAGE GRAYSCALE-\nThe filter converts an image to a Grayscale image. To do this it finds the brightness of each pixel and sets the red, green and blue of the output to the brightness value.\n\nIMAGE BLURRING-\nBlurring is the thing that happens when your camera is out of focus, and the image captured gets dizzy or hazy.");
			}
			if(s.equals("Distortion - Help"))
			{	
				JOptionPane.showMessageDialog(null, "FLIP HORIZONTAL-\nThe purpose of this function is to laterally invert the image, that is, rotate it by 180 degrees.\n\nGLASS-\nThis function gives an illusion that a glass has been put in front of the image.");
			}
		}
	}

public static void main(String[] args) 
 	{ 
 		JFrame.setDefaultLookAndFeelDecorated(true); 
 		JDialog.setDefaultLookAndFeelDecorated(true); 
 		
 		new ImagePro(); 
 	} 
 }