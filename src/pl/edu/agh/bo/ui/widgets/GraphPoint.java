package pl.edu.agh.bo.ui.widgets;

public class GraphPoint {
	public GraphPoint( int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int GetX()	{ return x; }
	public int GetY()	{ return y; }
	
	private int x;
	private int y;
}
