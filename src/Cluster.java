import java.math.*;
import java.util.*;
public class Cluster {
	Point gravite;
	Vector<Point> points;
	double inertie;
	public Cluster() {
		super();
	}
		
	public Cluster(Point gravite) {
		super();
		this.gravite = gravite;
		this.points = new Vector<Point>();
		this.inertie = 0;
	}


	//calcul du centre de gravite
	public void SetGravite(){
		Vector<Double>coord=gravite.valeur;
		for(int i=0;i<coord.size();i++){
			coord.set(i,(double) 0);
		}
		for (Point p:points){
			for(int  i=0;i<coord.size();i++){
				double a=coord.get(i);
				double b=p.valeur.get(i)/points.size();
				coord.set(i,a+b);
			}
		}
	}
	
}
