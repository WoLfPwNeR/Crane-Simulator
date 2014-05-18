package model;
//import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;

public class BlockModel extends Object {
	
	public Rectangle r;
	
	public int cx,cy,w,h;
	
	public AffineTransform t;
	public int xs,ys,xe,ye,width;
	public boolean isArm;
	
	public BlockModel(int xs, int ys, int xe, int ye, int width, boolean isArm) {
		this.r = new Rectangle(xs-width, ys-width, (xe-xs)+2*width, (ye-ys)+2*width);
		this.xs=xs;
		this.ys=ys;
		this.xe=xe;
		this.ye=ye;
		this.width=width;
		this.isArm=isArm;
		
		this.t = new AffineTransform();
		//t.translate(100,100);
	}
	
	public BlockModel(int x, int y, int width, int height, boolean isArm) {
		this.r = new Rectangle(x,y,width,height);
		this.isArm=isArm;

		this.t = new AffineTransform();
		//t.translate(100,100);
	}
	
	public BlockModel(int x, int y, int width, int height, boolean isArm, int cx, int cy, int w, int h) {
		this.r = new Rectangle(x,y,width,height);
		this.isArm=isArm;
		
		this.cx=cx;
		this.cy=cy;
		this.w=w;
		this.h=h;
		
		this.t = new AffineTransform();
		//t.translate(100,100);
	}
}
