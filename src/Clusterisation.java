import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.math.*;

public class Clusterisation  {
	
	// attributs
	public Vector<Cluster> clusters;
	public Vector<Point> points;
	public Vector<String> variables;
	public double inertie;
	public  int dist;
	public int k;
			
	// constructeurs
	public Clusterisation(){		
		clusters = new Vector<Cluster>() ;
		points = new Vector<Point>() ;
		variables=new Vector<String>();
		inertie = 100000000;
		dist=-1;
		k=0;
	}
	
	//imorter les notes des élèves
public void LoadDatabis(String texte) throws IOException{	
		
		// Lecture du fichier texte
		try{
			Reader reader = new FileReader(texte+".txt");
			//double res =Math.;
			// Prise en compte d'une ligne
			BufferedReader in = new BufferedReader(reader);
			//traitement de la première ligne
			String ligne = in.readLine();
			String[] temp=ligne.split("\\t" );
			 for (int i=0;i<temp.length; i++){ 
		        	variables.add(temp[i]);
		        	System.out.print(variables.lastElement()+" ");
		     }
			System.out.print("\n");
			ligne = in.readLine();
			while (ligne != null)	{
				// traitement de la ligne courante: decouper les mots separes par des delimiteurs
				Point p =new Point();
				temp=ligne.split("\\t" );         
		        for (int i=1; i<temp.length; i++){ 
		        	double nombre = Double.parseDouble(temp[i]);
		        	p.valeur.add(nombre);
					System.out.print(nombre+" ");
		        }
			// Lecture de la prochaine ligne 
		        points.add(p);
				System.out.print("\n");
				ligne = in.readLine();
			}
			
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
			
	// Importer les données 2D simple
	public void LoadData(String texte) throws IOException{	
		
		// Lecture du fichier texte
		try{
			Reader reader = new FileReader(texte+".txt");
			//double res =Math.;
			// Prise en compte d'une ligne
			BufferedReader in = new BufferedReader(reader);
			String ligne = in.readLine();
			while (ligne != null)	{
				// traitement de la ligne courante: decouper les mots separes par des delimiteurs
				Point p =new Point();
				String[] temp=ligne.split("\\t" );         
		        for (int i=0; i<temp.length; i++){ 
		        	double nombre = Double.parseDouble(temp[i]);
		        	p.valeur.add(nombre);
					System.out.print(nombre+" ");
		        }
			// Lecture de la prochaine ligne 
		        points.add(p);
				System.out.print("\n");
				ligne = in.readLine();
			}
			
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void Solve(int k,int dist){
		this.k=k;
		this.dist=dist;
		Initialisation();
		int itermax=100;
		int i=0;
		while (i<itermax){
			Iteration();
			System.out.println(inertie);
			i++;
		}
	}
	
	//initialisation de l'algorithme
	public void Initialisation(){
		//tirage au hasard des centres de gravité
		for(int i=0;i<k;i++){
			int r=(int) Math.floor(Math.random()*points.size());
			Point grav=new Point(points.get(r));
			Cluster c=new Cluster(grav);
			clusters.add(c);
		}
		for(int j=0;j<points.size();j++){
			clusters.get(0).points.add(points.get(j));
		}
	}
			
	// Effectuer une iteration
	public void Iteration(){
		//génération des nouveaux clusters et calcul de l'inertie intra-gourp
		setClusters();	
		//calcul des nouveaux centres de gravité		
		for(int i=0;i<clusters.size();i++){
			Cluster c=clusters.get(i);
			c.SetGravite();
			System.out.println(c.gravite.valeur.get(0)+" "+c.gravite.valeur.get(1));
		}
	}		
			
	// Générer les clusters
	public void setClusters(){
		inertie=0;
		for(int i=0;i<points.size();i++){
			Point p=points.get(i);
			double min=Distance(p,clusters.get(0).gravite);
			int clus=0;
			for(int j=1;j<clusters.size();j++){
				double d=Distance(p,clusters.get(j).gravite);
				if (min>d){
					clus=j;
					min=d;
				}
			}
			clusters.get(p.cluster).points.removeElement(p);
			p.cluster=clus;
			clusters.get(p.cluster).points.add(p);
			//calcul de l'intertie intra-groupe totale
			inertie+=min;
		}
	}
	//Calcul de la distance à un point
	public double Distance(Point p1,Point p2){
		double result=0;
		switch (dist) {
			case 0 : result=Euclidean(p1,p2);break;
			case 1 : result=Norme1(p1,p2);break;
			case 2 : result=SimCosinus(p1,p2);break;
			case 3 : result=SimTanimoto(p1,p2);break;
		}
		return result;
			
	}
	private double Euclidean(Point p1, Point p2){
		double result=0;
		for(int i=0;i<p1.valeur.size();i++){
			result+=Math.pow(p1.valeur.get(i)-p2.valeur.get(i),2);
		}
		result=result/p1.valeur.size();
		return result;
	}
	
	private double Norme1 (Point p1,Point p2){
		double result=0;
		for(int i=0;i<p1.valeur.size();i++){
			result+=Math.abs(p1.valeur.get(i)-p2.valeur.get(i));
		}
		result=result/p1.valeur.size();
		return result;
	}
	
	private double SimCosinus(Point p1,Point p2){
		double result=0;
		double prod=0,carre1=0,carre2=0;
		for(int i=0;i<p1.valeur.size();i++){
			double a=p1.valeur.get(i);
			double b=p2.valeur.get(i);
			prod+=a*b;
			carre1+=a*a;
			carre2+=b*b;
		}
		result=prod/(Math.sqrt(carre1)*Math.sqrt(carre2));
		return result;
	}
	
	private double SimTanimoto(Point p1,Point p2){
		double result=0;
		double prod=0,carre1=0,carre2=0;
		for(int i=0;i<p1.valeur.size();i++){
			double a=p1.valeur.get(i);
			double b=p2.valeur.get(i);
			prod+=a*b;
			carre1+=a*a;
			carre2+=b*b;
		}
		result=prod/(carre1+carre2-prod);
		return result;
	}
	

	
}
