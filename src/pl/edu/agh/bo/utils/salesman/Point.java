package pl.edu.agh.bo.utils.salesman;

/**
 * Klasa reprezentujaca jeden z punktow podrozy dla problemu komiwojazera
 * @author marpiech, askaw
 */
public class Point {
	Double x;
	Double y;
	String name;
	
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 */
	public Point (Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 * @param name
	 */
	public Point (Double x, Double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	

	
	/**
	 * liczy odleglosc euklidesowa miedzy punktami
	 * @param point punkt do ktorego ma byc liczona odleglosc
	 * @return odleglosc pomiedzy punktami
	 */
	public Double distance(Point point) {
		return Math.sqrt((this.x-point.x)*(this.x-point.x) + (this.y-point.y)*(this.y-point.y));
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
