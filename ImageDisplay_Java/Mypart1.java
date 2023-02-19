
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


public class Mypart1 {

	JFrame frame;
	JLabel lbIm1;
	JLabel lbIm2;
	BufferedImage img;
	BufferedImage imgright;
	int width = 512;
	int height = 512;

	static int n;
	static float s;
	static int aliasing_boolean;

	// Draws a black line on the given buffered image from the pixel defined by (x1, y1) to (x2, y2)
	public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2) {
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		g.drawLine(x1, y1, x2, y2);
		g.drawImage(image, 0, 0, null);
	}

	public void scale(int width, int height, BufferedImage img,float s,int width1,int height1){
		int oldx;
		int oldy;

		for(int y=0 ;y < height1;y++){
			for(int x=0; x < width1;x++){
				oldy = (int) (y * (1/s));
				oldx = (int) (x * (1/s));
				if(oldx < width && oldy < height) {
					if(aliasing_boolean == 0)
						imgright.setRGB(x,y,img.getRGB(oldx,oldy));
					else if(aliasing_boolean == 1)
					imgright.setRGB(x,y,antialiasedimg(oldx,oldy,img));
				}
			}
		}
	}

	private int antialiasedimg(int oldx, int oldy, BufferedImage img) {
		//appply the 3x3 averaging filter
		int pix = img.getRGB(oldx, oldy);

		int filterdir[][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}}; //all 3x3 directions to be checked
		int r = (pix>>16)&0xff;
		int g = (pix>>8)&0xff;
		int b = (pix&0xff);

		int redhere = r;
		int greenhere = g;
		int bluehere = b;
		
		int count = 1;

		for (int i=0;i<8;i++){
			int newx = oldx + filterdir[i][0];
			int newy = oldy + filterdir[i][1];
			if(newx >= 0 && newx < width && newy >= 0 && newy < height){
				int pix1 = img.getRGB(newx, newy);
				int r1 = (pix1>>16)&0xff;
				int g1 = (pix1>>8)&0xff;
				int b1 = (pix1&0xff);
				redhere += r1;
				greenhere += g1;
				bluehere += b1;
				count++;
			}
		}
		//get average of all the pixels in rgb
		int redavg = redhere/count;
		int greenavg = greenhere/count;
		int blueavg = bluehere/count;
		return 0xff000000 | ((redavg & 0xff) << 16) | ((greenavg & 0xff) << 8) | (blueavg & 0xff);
	}

	public void showIms(String[] args){

		// Read a parameter from command line
		n = Integer.parseInt(args[0]);	//number of diagonals
		s = Float.parseFloat(args[1]);	//scale factor
		aliasing_boolean = Integer.parseInt(args[2]);	//aliasing boolean
		//System.out.println("The first parameter was: " + param0);

		//Get scaled width and height
		int width1 = (int) Math.ceil(width * s);
		int height1 = (int) Math.ceil(height * s);

		// Initialize a plain white image
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imgright = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);

		int ind = 0;
		for(int y = 0; y < height; y++){

			for(int x = 0; x < width; x++){

				// byte a = (byte) 255;
				byte r = (byte) 255;
				byte g = (byte) 255;
				byte b = (byte) 255;

				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				img.setRGB(x,y,pix);
				ind++;
			}
		}

		drawLine(img, 0, 0, width-1, 0);				// top edge
		drawLine(img, 0, 0, 0, height-1);				// left edge
		drawLine(img, 0, height-1, width-1, height-1);	// bottom edge
		drawLine(img, width-1, height-1, width-1, 0); 	// right edge
		//drawLine(img, 0, 0, width-1, height-1);			// diagonal line

		//draw an the diagonals using n

		int startingX = width/2;
		int startingY = height/2;
		double angle = 2 * Math.PI / n;
		for (int i = 0; i < n; i++) {
			int x = (int) (startingX + width * Math.cos(i * angle));
			int y = (int) (startingY + height * Math.sin(i * angle));
			drawLine(img, startingX, startingY, x, y);
		}

		//scale the image
		scale(width,height,img,s,width,height);

		// Use labels to display the images
		frame = new JFrame();
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);

		JLabel lbText1 = new JLabel("Original image (Left)");
		lbText1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lbText2 = new JLabel("Image after modification (Right)");
		lbText2.setHorizontalAlignment(SwingConstants.CENTER);
		lbIm1 = new JLabel(new ImageIcon(img));
		lbIm2 = new JLabel(new ImageIcon(imgright));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(lbText1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		frame.getContentPane().add(lbText2, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.getContentPane().add(lbIm1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		frame.getContentPane().add(lbIm2, c);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Mypart1 ren = new Mypart1();
		ren.showIms(args);
	}

}