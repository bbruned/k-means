import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.math.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Clusterisation c=new Clusterisation();
		c.LoadData("exemple2");
		//c.LoadDatabis("ListeDesMoyennes");
		int k1=2;
		c.Solve(k1,3);
		XYSeriesCollection dataset = new XYSeriesCollection();
		// Create a simple XY chart
		for(int i=0;i<k1;i++){
			XYSeries series = new XYSeries("Cluster"+" "+i);
			Cluster group=c.clusters.get(i);
			for(Point p:group.points){
				double a=p.valeur.get(0);
				double b=p.valeur.get(1);
				series.add(a, b);
			}
			dataset.addSeries(series);				
		}
		JFreeChart chart = ChartFactory.createScatterPlot(
				"Clustérisation 2D", // Title
				"x", // x-axis Label
				"y", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		//affichage de la fenêtre 
		ChartFrame frame=new ChartFrame("First",chart);
		    frame.pack();
		    frame.setVisible(true);
		//sauvegarde du graphique 		    
		 //ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 700, 500);
			
	}
	
	

}
