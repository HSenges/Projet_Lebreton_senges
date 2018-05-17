package terrain;

/**Objet permettant une manipulation plus
 * aisée qu'avec des cordonnées exprimée en 
 * [int X, int Y]
 */
public class Coordonnee {
	
	//Attributs
	private int x;
	private int y;
	
	//Accesseurs
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	//Mutateurs
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	//Constructeur
	public Coordonnee(int X, int Y) {
		this.x = X;
		this.y = Y;
	}
	
	//Méthodes
	
	@Override
	public String toString() {
		return "["+this.getX()+","+this.getY()+"]";
	}
}
