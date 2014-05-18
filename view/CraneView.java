package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A view of a right triangle that displays the triangle graphically and allows
 * the user to change the size by dragging the image with a mouse.
 */
public class CraneView extends JComponent {
	
	public CraneModel model;
	public BlockModel selected;
	public boolean suck=false;
	//private double scale = 1.0; // how much should the triangle be scaled?

	//private boolean selected = false; // did the user select the triangle to
	// resize it?
	
	// To format numbers consistently in the text fields.
	//private static final NumberFormat formatter = NumberFormat.getNumberInstance();

	public CraneView(CraneModel aModel) {
		super();
		this.model = aModel;
		this.layoutView();
		this.registerControllers();
		this.model.addView(new IView() {

			/** The model changed. Ask the system to repaint the triangle. */
			public void updateView() {
				repaint();
			}

		});
	}

	/** How should it look on the screen? */
	private void layoutView() {
		this.setPreferredSize(new Dimension(800, 600));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	/** Register event Controllers for mouse clicks and motion. */
	private void registerControllers() {
		MouseInputListener mil = new MController();
		this.addMouseListener(mil);
		this.addMouseMotionListener(mil);
	}

	/** Paint the triangle, and "handles" for resizing if it was selected. */
	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		super.paintComponent(g);
		
		try {                
			g.drawImage(ImageIO.read(new File("m2.jpg")), 0, 0, null);
		} catch (IOException ex) {}
		
		
		//g.setColor(Color.black);
		//g.fillRect(0,model.gl,800,600-model.gl);
		
		for(int i=0;i<model.block.size();++i){
			//System.out.println(i);
			//
			//g.setColor(Color.yellow);
			//g.fillRect(model.crane.get(i).r.x,model.crane.get(i).r.y,model.crane.get(i).r.width,model.crane.get(i).r.height);
			g.setTransform(model.block.get(i).t);
			g.setColor(Color.cyan);
			g.fillRect(model.block.get(i).r.x,model.block.get(i).r.y,model.block.get(i).r.width,model.block.get(i).r.height);
			g.setColor(Color.black);
			g.drawRect(model.block.get(i).r.x,model.block.get(i).r.y,model.block.get(i).r.width,model.block.get(i).r.height);
		}g.setTransform(new AffineTransform());
		// Draw the crane
		for(int i=0;i<model.crane.size();++i){
			//System.out.println(i);
			//
			//g.setColor(Color.yellow);
			//g.fillRect(model.crane.get(i).r.x,model.crane.get(i).r.y,model.crane.get(i).r.width,model.crane.get(i).r.height);
			AffineTransform temp=new AffineTransform();
			for(int j=0;j<=i;++j){
				temp.concatenate(model.crane.get(j).t);
			}
			g.setTransform(temp);
			
			if(i==0){
				g.setColor(Color.blue);
				g.fillOval(model.crane.get(i).cx,model.crane.get(i).cy,model.crane.get(i).w,model.crane.get(i).h);
				g.setColor(Color.black);//bound
				g.drawOval(model.crane.get(i).cx,model.crane.get(i).cy,model.crane.get(i).w,model.crane.get(i).h);
				
				g.setColor(Color.blue);
				g.fillOval(model.crane.get(i).cx+100,model.crane.get(i).cy,model.crane.get(i).w,model.crane.get(i).h);
				g.setColor(Color.black);//bound
				g.drawOval(model.crane.get(i).cx+100,model.crane.get(i).cy,model.crane.get(i).w,model.crane.get(i).h);
			}
			
			if(i==6&&suck){
				g.setColor(Color.red);
			}else if(i==6&&(!suck)){
				g.setColor(Color.green);
			}else{
				g.setColor(Color.yellow);
			}
			//fill
			g.fillRect(model.crane.get(i).r.x,model.crane.get(i).r.y,model.crane.get(i).r.width,model.crane.get(i).r.height);
			g.setColor(Color.black);//bound
			g.drawRect(model.crane.get(i).r.x,model.crane.get(i).r.y,model.crane.get(i).r.width,model.crane.get(i).r.height);
			if(model.crane.get(i).isArm){//pivot
				g.setColor(Color.black);
				g.fillOval(model.crane.get(i).xs-3,model.crane.get(i).ys-3,6,6);
				g.fillOval(model.crane.get(i).xe-3,model.crane.get(i).ye-3,6,6);
			}
			if(selected==model.crane.get(i)){//selected rect
				g.setColor(Color.black);
				g.fillRect(model.crane.get(i).r.x-3,model.crane.get(i).r.y-3,6,6);
				g.fillRect(model.crane.get(i).r.x+model.crane.get(i).r.width-3,model.crane.get(i).r.y-3,6,6);
				g.fillRect(model.crane.get(i).r.x-3,model.crane.get(i).r.y+model.crane.get(i).r.height-3,6,6);
				g.fillRect(model.crane.get(i).r.x+model.crane.get(i).r.width-3,model.crane.get(i).r.y+model.crane.get(i).r.height-3,6,6);
			}else{}
		}g.setTransform(new AffineTransform());

		
		
	}

	public double getAngleInRad(double xcentre,double ycentre,double x,double y){
		double dx=x-xcentre;
		double dy=y-ycentre;
		double r=Math.abs(dy/dx);
		double a;
		if(dx==0){
			a=Math.PI/2;
		}else{
			a=Math.atan(r);
		}
		if(dx>0&&dy>0){
			return a;
		}else if(dx<0&&dy>0){
			return Math.PI-a;
		}else if(dx>0&&dy<0){
			return 2*Math.PI-a;
		}else if(dx<0&&dy<0){
			return Math.PI+a;
		}
		return Math.atan(dy/dx);
	}

	
	private class MController extends MouseInputAdapter {
		
		int tempX,tempY;
		double tempa;
		
		public void mousePressed(MouseEvent e) {
			System.out.println("pressed");
			//("xs:"+model.crane.get(0).xs+" ys:"+model.crane.get(0).ys);
			tempX=e.getX();
			
			//tempa=getAngleInRad(model.crane.get(1).xe,model.crane.get(1).ye,e.getX(),e.getY());
			//System.out.println("tempa="+Math.toDegrees(tempa));
			selected=null;
			if(e.getButton()==1){
				for(int i=0;i<model.crane.size();++i){//for all arms
					AffineTransform temp=new AffineTransform();
					for(int j=0;j<=i;++j){
						temp.concatenate(model.crane.get(j).t);
					}
					Point pt=new Point(e.getX(), e.getY());
					try {
						temp.inverseTransform(pt, pt);
					} catch (NoninvertibleTransformException e1) {}
					if(model.crane.get(i).r.contains(pt.getX(), pt.getY())){
						selected=model.crane.get(i);
						//System.out.println("selected "+i);
					}
				}
				if(selected==model.crane.get(1)){
					AffineTransform temp=new AffineTransform();
					for(int j=0;j<=1;++j){
						temp.concatenate(model.crane.get(j).t);
					}
					Point pt=new Point(model.crane.get(1).xe, model.crane.get(1).ye);
					temp.transform(pt, pt);
					//selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,pt.getX(),pt.getY());
					tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
				}else if(selected==model.crane.get(2)){
					AffineTransform temp=new AffineTransform();
					for(int j=0;j<=2;++j){
						temp.concatenate(model.crane.get(j).t);
					}
					Point pt=new Point(model.crane.get(2).xs, model.crane.get(2).ys);
					temp.transform(pt, pt);
					//selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(2).xs, model.crane.get(2).ys);
					tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
				}else if(selected==model.crane.get(3)){
					AffineTransform temp=new AffineTransform();
					for(int j=0;j<=3;++j){
						temp.concatenate(model.crane.get(j).t);
					}
					Point pt=new Point(model.crane.get(3).xs, model.crane.get(3).ys);
					temp.transform(pt, pt);
					//selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(3).xs, model.crane.get(3).ys);
					tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
				}else if(selected==model.crane.get(4)){
					AffineTransform temp=new AffineTransform();
					for(int j=0;j<=4;++j){
						temp.concatenate(model.crane.get(j).t);
					}
					Point pt=new Point(model.crane.get(4).xs, model.crane.get(4).ys);
					temp.transform(pt, pt);
					//selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(4).xs, model.crane.get(4).ys);
					tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
				}else if(selected==model.crane.get(5)){
					AffineTransform temp=new AffineTransform();
					for(int j=0;j<=5;++j){
						temp.concatenate(model.crane.get(j).t);
					}
					Point pt=new Point(model.crane.get(5).xs, model.crane.get(5).ys);
					temp.transform(pt, pt);
					//selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(5).xs, model.crane.get(5).ys);
					tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
				}else if(selected==model.crane.get(6)){
					AffineTransform origt=new AffineTransform();;
					if(!suck){//pickup
						AffineTransform temp1=new AffineTransform();
						for(int i=0;i<model.crane.size();++i){
							temp1.concatenate(model.crane.get(i).t);
						}
						Point pt=new Point(model.crane.get(6).r.x, model.crane.get(6).r.y+model.crane.get(6).r.height);//bottomleft
						Point pt2=new Point(model.crane.get(6).r.x+model.crane.get(6).r.width, model.crane.get(6).r.y+model.crane.get(6).r.height);//bottomright
						Point pt3=new Point(model.crane.get(6).r.x, model.crane.get(6).r.y);//topleft
						Point pt4=new Point(model.crane.get(6).r.x+model.crane.get(6).r.width/2, model.crane.get(6).r.y+model.crane.get(6).r.height);//bottomcentre
						temp1.transform(pt, pt);
						temp1.transform(pt2, pt2);
						temp1.transform(pt3, pt3);
						temp1.transform(pt4, pt4);
						if((Math.abs(pt.y-pt2.y)<3 && pt3.y<pt.y && pt3.y<pt2.y)){
							double min=800;
							int index=0;
							for(int i=0;i<model.block.size();++i){
								AffineTransform temp=model.block.get(i).t;
								Point pt5=new Point(model.block.get(i).r.x+model.block.get(i).r.width/2, model.block.get(i).r.y);//topcentre
								temp.transform(pt5, pt5);
								if((pt5.y-pt4.y>0 && pt5.y-pt4.y<4) && pt4.distance(pt5)<min){
									min=pt4.distance(pt5);
									index=i;
									origt=model.block.get(i).t;
								}
							}
							if(min<5){
								model.crane.add(model.block.remove(index));//remove first from block and put in crane last
								
								AffineTransform temp=new AffineTransform();
								for(int i=0;i<model.crane.size()-1;++i){
									temp.concatenate(model.crane.get(i).t);//sum until not including magnet
								}
								try {
									model.crane.get(model.crane.size()-1).t.preConcatenate(temp.createInverse());//inversetransform block
									//model.crane.get(model.crane.size()-1).t=temp.createInverse();
								} catch (NoninvertibleTransformException e1) {}
								suck=!suck;
								
							}
						}
					}else{//drop
						AffineTransform temp1=new AffineTransform();
						for(int i=0;i<model.crane.size();++i){
							temp1.concatenate(model.crane.get(i).t);
						}
						//box
						Point pt=new Point(model.crane.get(7).r.x, model.crane.get(7).r.y+model.crane.get(7).r.height);//bottomleft
						Point pt2=new Point(model.crane.get(7).r.x+model.crane.get(7).r.width, model.crane.get(7).r.y+model.crane.get(7).r.height);//bottomright
						Point pt3=new Point(model.crane.get(7).r.x+model.crane.get(7).r.width/2, model.crane.get(7).r.y+model.crane.get(7).r.height);//bottomcentre
						
						int gy=model.gl;
						temp1.transform(pt, pt);
						temp1.transform(pt2, pt2);
						temp1.transform(pt3, pt3);
						
						boolean flag=false;
						for(int i=0;i<model.block.size();++i){
							AffineTransform temp=model.block.get(i).t;
							Point pt4=new Point(model.block.get(i).r.x+model.block.get(i).r.width/2, model.block.get(i).r.y);//topcentre of groundblock
							temp.transform(pt4, pt4);
							if((pt4.y-pt3.y>=0 && pt4.y-pt3.y<4) && pt3.distance(pt4)<model.block.get(i).r.width/2){
								flag=true;
							}
						}
						
						boolean flag2=false;
						for(int i=0;i<model.block.size();++i){
							for(int j=0;j<model.block.size();++j){
								if(i!=j){
									AffineTransform temp2=model.block.get(i).t;
									AffineTransform temp3=model.block.get(j).t;
									Point pt4=new Point(model.block.get(i).r.x+model.block.get(i).r.width, model.block.get(i).r.y);//topright of left
									Point pt5=new Point(model.block.get(j).r.x, model.block.get(j).r.y);//topleft of right
									temp2.transform(pt4, pt4);
									temp3.transform(pt5, pt5);
									if(Math.abs(pt4.y-pt5.y)<5 && pt4.distance(pt5)<50){
										if(pt.x<pt4.x && pt2.x>pt5.x && (pt4.y>pt.y && pt4.y-pt.y<3) && (pt5.y>pt2.y && pt5.y-pt2.y<3)){
											System.out.println("asdasd");
											flag2=true;
										}
									}
								}
							}
						}
						
						boolean flag3=true;
						
						if( (Math.abs(pt.y-pt2.y)<3 && Math.abs(gy-pt3.y)<3) || flag || flag2 ){//touches ground||stack on top of another 
							model.block.add(model.crane.remove(model.crane.size()-1));//remove crane last and put in block last
							AffineTransform temp=new AffineTransform();
							for(int i=0;i<model.crane.size();++i){
								temp.concatenate(model.crane.get(i).t);
							}
							model.block.get(model.block.size()-1).t.preConcatenate(temp);
							suck=!suck;
						}else{
							System.out.println(pt.y+" "+pt2.y+" "+gy+" "+pt3.y);
							model.block.add(model.crane.remove(model.crane.size()-1));//remove crane last and put in block last
							model.block.get(model.block.size()-1).t=origt;
							suck=!suck;
						}
					}
				}
			}else if(e.getButton()==3){
				if(model.crane.size()>7){
					model.block.add(model.crane.remove(model.crane.size()-1));//remove crane last and put in block last
				}
				for(int i=0;i<model.crane.size();++i){
					/*try {
						model.crane.get(i).t.concatenate(model.crane.get(i).t.createInverse());
					} catch (NoninvertibleTransformException e1) {}*/
					model.crane.get(i).t=new AffineTransform();
				}
				for(int i=0;i<model.block.size();++i){
					/*try {
						model.block.get(i).t.concatenate(model.block.get(i).t.createInverse());
					} catch (NoninvertibleTransformException e1) {}*/
					model.block.get(i).t=new AffineTransform();
				}
				suck=false;
			}
			
			repaint();
		}


		/** The user is dragging the mouse. Resize appropriately. */
		public void mouseDragged(MouseEvent e) {
			//System.out.println("dragged");
			//System.out.println("xs:"+model.crane.get(0).xs+" ys:"+model.crane.get(0).ys);

			if(selected==model.crane.get(0)){
				AffineTransform temp=model.crane.get(0).t;
				Point pt=new Point(model.crane.get(0).r.x,model.crane.get(0).r.y);
				Point pt2=new Point(model.crane.get(0).r.x+model.crane.get(0).r.width,model.crane.get(0).r.y+model.crane.get(0).r.height);
				temp.transform(pt,pt);
				temp.transform(pt2,pt2);
				if(pt.x<0){
					selected.t.translate(0-pt.x, 0);
				}else if(pt2.x>800){
					selected.t.translate(800-pt2.x, 0);
				}
				selected.t.translate(e.getX()-tempX, 0);//update all children
				tempX=e.getX();
			}else if(selected==model.crane.get(1)){
				AffineTransform temp=new AffineTransform();
				for(int j=0;j<=1;++j){
					temp.concatenate(model.crane.get(j).t);
				}
				Point pt=new Point(model.crane.get(1).xe, model.crane.get(1).ye);
				temp.transform(pt, pt);
				selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(1).xe, model.crane.get(1).ye);
				tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
			}else if(selected==model.crane.get(2)){
				AffineTransform temp=new AffineTransform();
				for(int j=0;j<=2;++j){
					temp.concatenate(model.crane.get(j).t);
				}
				Point pt=new Point(model.crane.get(2).xs, model.crane.get(2).ys);
				temp.transform(pt, pt);
				selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(2).xs, model.crane.get(2).ys);
				tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
			}else if(selected==model.crane.get(3)){
				AffineTransform temp=new AffineTransform();
				for(int j=0;j<=3;++j){
					temp.concatenate(model.crane.get(j).t);
				}
				Point pt=new Point(model.crane.get(3).xs, model.crane.get(3).ys);
				temp.transform(pt, pt);
				selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(3).xs, model.crane.get(3).ys);
				tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
			}else if(selected==model.crane.get(4)){
				AffineTransform temp=new AffineTransform();
				for(int j=0;j<=4;++j){
					temp.concatenate(model.crane.get(j).t);
				}
				Point pt=new Point(model.crane.get(4).xs, model.crane.get(4).ys);
				temp.transform(pt, pt);
				selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(4).xs, model.crane.get(4).ys);
				tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
			}else if(selected==model.crane.get(5)){
				AffineTransform temp=new AffineTransform();
				for(int j=0;j<=5;++j){
					temp.concatenate(model.crane.get(j).t);
				}
				Point pt=new Point(model.crane.get(5).xs, model.crane.get(5).ys);
				temp.transform(pt, pt);
				selected.t.rotate(getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY())-tempa,model.crane.get(5).xs, model.crane.get(5).ys);
				tempa=getAngleInRad(pt.getX(),pt.getY(),e.getX(),e.getY());
			}
			
			repaint();
		} // mouseDragged
		
		/*public void mouseReleased(MouseEvent e) {
			System.out.println("released");
			//selected=null;
			repaint();
		}*/
	} // MController
	
} // GraphicalView

