
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogReg {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<Double> coins = new ArrayList<Double>();
		ArrayList<Double> corners = new ArrayList<Double>();
		ArrayList<Double> mobility = new ArrayList<Double>();
		ArrayList<Double> emp = new ArrayList<Double>();
		ArrayList<Integer> winner = new ArrayList<Integer>();
		for(int i = 1;i<1000;i++){
			double wLast = 0.0;
			double bLast = 0.0;
			Scanner scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\"+i+".txt"));
			scan.nextLine();
			String w = scan.nextLine();
			w = w.substring(0,w.length()-2);
			String[] ws = w.split(", ");
			for(int j = 0;j<ws.length;j++){
				coins.add(Double.parseDouble(ws[j]));
			}
			
			w = scan.nextLine();
			w = w.substring(0,w.length()-2);
			ws = w.split(", ");
			for(int j = 0;j<ws.length;j++){
				corners.add(Double.parseDouble(ws[j]));
			}
			
			w = scan.nextLine();
			w = w.substring(0,w.length()-2);
			ws = w.split(", ");
			for(int j = 0;j<ws.length;j++){
				mobility.add(Double.parseDouble(ws[j]));
			}
			
			w = scan.nextLine();
			w = w.substring(0,w.length()-2);
			ws = w.split(", ");
			for(int j = 0;j<ws.length;j++){
				emp.add(Double.parseDouble(ws[j]));
				wLast = Double.parseDouble(ws[j]);
			}
			
			scan.nextLine();
			String b = scan.nextLine();
			b = b.substring(0,b.length()-2);
			String[] bs = b.split(", ");
			for(int j = 0;j<bs.length;j++){
				coins.add(Double.parseDouble(bs[j]));
			}
			
			b = scan.nextLine();
			b = b.substring(0,b.length()-2);
			bs = b.split(", ");
			for(int j = 0;j<bs.length;j++){
				corners.add(Double.parseDouble(bs[j]));
			}
			
			b = scan.nextLine();
			b = b.substring(0,b.length()-2);
			bs = b.split(", ");
			for(int j = 0;j<bs.length;j++){
				mobility.add(Double.parseDouble(bs[j]));
			}
			
			b = scan.nextLine();
			b = b.substring(0,b.length()-2);
			bs = b.split(", ");
			for(int j = 0;j<bs.length;j++){
				emp.add(Double.parseDouble(bs[j]));
				bLast = Double.parseDouble(bs[j]);
			}
			
			for(int j = 0; j<61;j++){
				if(wLast>bLast){
					winner.add(1);
				}else if(wLast==bLast){
					winner.add(0);
				}else {
					winner.add(-1);
				}
			}
			for(int j = 0; j<61;j++){
				if(wLast<bLast){
					winner.add(1);
				}else if(wLast==bLast){
					winner.add(0);
				}else {
					winner.add(-1);
				}
			}
			scan.close();
		}
		
		Writer writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\emp.csv"));
		for(int i = 0;i<emp.size();i++){
			writer.write(emp.get(i)+"");
			if(i+1!=emp.size()){
				writer.write(", ");
			}
		}
		writer.flush();
		writer.close();
		
		writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\coins.csv"));
		for(int i = 0;i<coins.size();i++){
			writer.write(coins.get(i)+"");
			if(i+1!=coins.size()){
				writer.write(", ");
			}
		}
		writer.flush();
		writer.close();
		
		writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\corners.csv"));
		for(int i = 0;i<corners.size();i++){
			writer.write(corners.get(i)+"");
			if(i+1!=corners.size()){
				writer.write(", ");
			}
		}
		writer.flush();
		writer.close();
		
		writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\mobility.csv"));
		for(int i = 0;i<mobility.size();i++){
			writer.write(mobility.get(i)+"");
			if(i+1!=mobility.size()){
				writer.write(", ");
			}
		}
		writer.flush();
		writer.close();
		
		writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\winner.csv"));
		for(int i = 0;i<winner.size();i++){
			writer.write(winner.get(i)+"");
			if(i+1!=winner.size()){
				writer.write(", ");
			}
		}
		writer.flush();
		writer.close();
		
		System.out.println((new LogReg()).train(emp,winner));
	}

	/**
	 * Created with IntelliJ IDEA.
	 * User: tpeng
	 * Date: 6/22/12
	 * Time: 11:01 PM
	 * To change this template use File | Settings | File Templates.
	 */

		/** the learning rate */
		private double rate = 0.9;

		/** the weight to learn */
		private double weights=0;

		private double sigmoid(double z) {
			return 1 / (1 + Math.exp(-z));
		}

		public double train(List<Double> instances, List<Integer> labels) {
			double diff = 1.0;
			while(diff>.000001){
				double oldW = weights;
				for (int i=0; i<instances.size(); i++) {
					double x = instances.get(i);
					double predicted = sigmoid(x);
					int label = labels.get(i);
					weights = weights + rate * (label - predicted) * x;
				}
				rate = rate*.9;
				diff = Math.abs(oldW-weights);
			}
			System.out.println("done");
			return weights;
		}
	}
