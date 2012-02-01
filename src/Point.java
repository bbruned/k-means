import java.util.*;
public class Point {
	
	// attributs
	public Vector<Double> valeur;
	public int cluster;	
	
	// constructeurs
	public Point(){
		valeur = new Vector<Double>();
		cluster = 0;
	}
	
	//copie profonde d'un point
	public Point(Point p){
		Vector<Double> m=new Vector<Double>();
		for(int i=0;i<p.valeur.size();i++){
			m.add(p.valeur.get(i));
		}
		this.valeur=m;
		this.cluster=p.cluster;
	}
		
}
