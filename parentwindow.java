
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

 class parentwindow extends JFrame implements ActionListener {
     
 JFrame frame;
 JPanel panel;
  JTextField tf = new JTextField(10);
   JButton BROWSE = new JButton("UPLOAD");
       JButton SEARCH= new JButton("SEARCH");
       JFileChooser fc=new JFileChooser();
       String path;
    public parentwindow(){
      frame = new JFrame("CONTENT BASED IMAGE RETREIVAL SYSTEM");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(400,300);
     
panel=new JPanel();

frame.getContentPane().add(BorderLayout.NORTH,panel);


       panel.add(tf);
       panel.add(BROWSE); 
       panel.add(SEARCH);
       frame.setVisible(true);
       BROWSE.addActionListener(this);
       SEARCH.addActionListener(this);
      panel.validate();
       
    
    
    
    }
    
       
       
        public void actionPerformed(ActionEvent e)
          {
              String command = e.getActionCommand();
                if (command.equals("UPLOAD"))
                   { 
                   
                     //if (e.getSource() == BROWSE) 
                      {
                      int returnVal = fc.showOpenDialog(parentwindow.this);
                      if (returnVal == JFileChooser.APPROVE_OPTION)
                         {
                            File file = fc.getSelectedFile();
                            path=file.getPath();
                        
                           tf.setText(file.getPath());
                         }   
              
                     }}
        int res = 0;
                  
              
                    if (command.equals("SEARCH"))
                    {path=fc.getSelectedFile().getAbsolutePath().toString();
                  /*  File file = new File(path);
                try {
                NaiveSimilarityFinder naiveSimilarityFinder = new NaiveSimilarityFinder(file);
                } catch (IOException ex) {
                    Logger.getLogger(NaiveSimilarityFinder.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }}
       }*/
        if (res == JFileChooser.APPROVE_OPTION)
       {
        File file = fc.getSelectedFile();
                try {
                    NaiveSimilarityFinder naiveSimilarityFinder = new NaiveSimilarityFinder(file);
                } catch (IOException ex) {
                    Logger.getLogger(parentwindow.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
      
      else
        {
        JOptionPane.showMessageDialog(null,
            "You must select one image to be the reference.", "Aborting...",
            JOptionPane.WARNING_MESSAGE);
        }}}      }