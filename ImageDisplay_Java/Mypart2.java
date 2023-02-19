import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class Mypart2 extends JPanel{
    private final double lvidFPS = 40;
    private float rvidFPS;
    final double lvidDelay = (1000/lvidFPS);
    private double rvidDelay;
    private int width = 512;
    private int height = 512;
    private int lines;
    private double rotations;
    private double lcounter = 0, rotangle;
    private double rcounter = 0, rotangle_right;
    JLabel lpane, rpane;
    private Timer ltimer, rtimer;
    private ImageIcon leftimg;
    public Mypart2(String[] args){
        lines = Integer.parseInt(args[0]);
        rotations = Float.parseFloat(args[1]);
        rvidFPS = Float.parseFloat(args[2]);
        rvidDelay = (1000/rvidFPS);
        
        rotangle = (2*Math.PI*rotations)/lvidFPS;
        rotangle_right = (2*Math.PI*rotations)/rvidFPS;

        lpane = new JLabel (imageGen(lcounter,rotangle));
        rpane = new JLabel (imageGen(rcounter,rotangle_right));
        this.add(lpane);
        this.add(rpane);

        ltimer = new Timer((int) lvidDelay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent E){
                updateLeftVid();
            }
        });
        ltimer.start();

        rtimer = new Timer((int) rvidDelay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent E){
                updateRightVid();
            }
        });
        rtimer.start();
    }
    private void vidDisplay (){
        JFrame frame = new JFrame("Temporal Aliasing Illustration");
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void updateLeftVid(){
        lcounter = (lcounter + 1);
        leftimg = imageGen(lcounter,rotangle);
        lpane.setIcon(leftimg);
        lpane.revalidate();
        lpane.repaint();
    }
    public void updateRightVid(){
        rcounter = (rcounter + 1);
        ImageIcon rightimg = imageGen(rcounter,rotangle_right);
        rpane.setIcon(rightimg);
        rpane.revalidate();
        rpane.repaint();
    }
    public ImageIcon imageGen(double framenum, double rotangle){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img = setUpRGB(img,width,height);
        int startingX = width/2;
		int startingY = height/2;
		double angle = 2 * Math.PI / lines;
		for (int i = 0; i < lines; i++) {
			int x = (int) (startingX + width * Math.cos((i * angle)+rotangle*framenum));
			int y = (int) (startingY + height * Math.sin((i * angle)+rotangle*framenum));
			drawLine(img, startingX, startingY, x, y,i);
		}

        return (new ImageIcon(img));
    }
    public BufferedImage setUpRGB(BufferedImage img,int width,int height){

        for(int y = 0; y < height; y++){

            for(int x = 0; x < width; x++){

                // byte a = (byte) 255;
                byte r = (byte) 255;
                byte g = (byte) 255;
                byte b = (byte) 255;

                int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                //int pix = ((a << 24) + (r << 16) + (g << 8) + b);
                img.setRGB(x,y,pix);
            }
        }
        return img;
    }
    public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2,int first) {
		Graphics2D g = image.createGraphics();
		if(first==0){
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(2));
			g.drawLine(x1, y1, x2, y2);}
		else{	
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		g.drawLine(x1, y1, x2, y2);
		g.drawImage(image, 0, 0, null);}
	}
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Mypart2(args).vidDisplay();
            }
        });
    }
}


