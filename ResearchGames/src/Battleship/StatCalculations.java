package Battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StatCalculations {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("5x5");
		calc("5");

		System.out.println("6x6");
		calc("6");
		
		System.out.println("8x8");
		calc("8");

		
		
	}

	private static void calc(String t) throws FileNotFoundException {
		ArrayList<Integer> random = new ArrayList<Integer>();
		ArrayList<Integer> set = new ArrayList<Integer>();
		ArrayList<Integer> emp = new ArrayList<Integer>();
		ArrayList<Integer> empK = new ArrayList<Integer>();
		ArrayList<Integer> empR = new ArrayList<Integer>();
		ArrayList<Integer> max = new ArrayList<Integer>();
		ArrayList<Integer> maxK = new ArrayList<Integer>();
		ArrayList<Integer> maxR = new ArrayList<Integer>();
		ArrayList<Integer> empTurns = new ArrayList<Integer>();
		ArrayList<Integer> empTurnsK = new ArrayList<Integer>();
		ArrayList<Integer> empTurnsR = new ArrayList<Integer>();
		ArrayList<Integer> maxTurns = new ArrayList<Integer>();
		ArrayList<Integer> maxTurnsK = new ArrayList<Integer>();
		ArrayList<Integer> maxTurnsR = new ArrayList<Integer>();
		
		Scanner scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\Turns"+t+".txt"));
		String[] next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			random.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			set.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			emp.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			max.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			empR.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			maxR.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			empK.add(Integer.valueOf(next[i]));
		}
		next = scan.nextLine().split(" ");
		for(int i = 0;i<next.length;i++){
			maxK.add(Integer.valueOf(next[i]));
		}
		scan.close();
		
		scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+t+"Emp.txt"));
		String line = scan.nextLine();
		next = line.split(" ");
		while(empTurns.size()<emp.size()){
			if(next.length==1){
				empTurns.add(emp.get(empTurns.size()));
			}else{
				empTurns.add(Integer.valueOf(next[0]));
			}
			if(scan.hasNext()){
				line = scan.nextLine();
				next = line.split(" ");
			}
		}
		scan.close();
		scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+t+"EmpK.txt"));
		line = scan.nextLine();
		next = line.split(" ");
		while(empTurnsK.size()<empK.size()){
			if(next.length==1){
				empTurnsK.add(empK.get(empTurnsK.size()));
			}else{
				empTurnsK.add(Integer.valueOf(next[0]));
			}
			if(scan.hasNext()){
				line = scan.nextLine();
				next = line.split(" ");
			}
		}
		scan.close();
		scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+t+"EmpR.txt"));
		line = scan.nextLine();
		next = line.split(" ");
		while(empTurnsR.size()<empR.size()){
			if(next.length==1){
				empTurnsR.add(empR.get(empTurnsR.size()));
			}else{
				empTurnsR.add(Integer.valueOf(next[0]));
			}
			if(scan.hasNext()){
				line = scan.nextLine();
				next = line.split(" ");
			}
		}
		scan.close();
		scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+t+"Max.txt"));
		line = scan.nextLine();
		next = line.split(" ");
		while(maxTurns.size()<max.size()){
			if(next.length==1){
				maxTurns.add(max.get(maxTurns.size()));
			}else{
				maxTurns.add(Integer.valueOf(next[0]));
			}
			if(scan.hasNext()){
				line = scan.nextLine();
				next = line.split(" ");
			}
		}
		scan.close();
		scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+t+"MaxR.txt"));
		line = scan.nextLine();
		next = line.split(" ");
		while(maxTurnsR.size()<maxR.size()){
			if(next.length==1){
				maxTurnsR.add(maxR.get(maxTurnsR.size()));
			}else{
				maxTurnsR.add(Integer.valueOf(next[0]));
			}
			if(scan.hasNext()){
				line = scan.nextLine();
				next = line.split(" ");
			}
		}
		scan.close();
		scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+t+"MaxK.txt"));
		line = scan.nextLine();
		next = line.split(" ");
		while(maxTurnsK.size()<maxK.size()){
			if(next.length==1){
				maxTurnsK.add(maxK.get(maxTurnsK.size()));
			}else{
				maxTurnsK.add(Integer.valueOf(next[0]));
			}
			if(scan.hasNext()){
				line = scan.nextLine();
				next = line.split(" ");
			}
		}
		scan.close();

		calcStuff(random, "random");
		calcStuff(set, "set");
		calcStuff(emp, "emp");
		calcStuff(empTurns, "emp turns");
		calcStuff(empK, "empK");
		calcStuff(empTurnsK, "empK turns");
		calcStuff(empR, "empR");
		calcStuff(empTurnsR, "empR turns");
		calcStuff(max, "max");
		calcStuff(maxTurns, "max turns");
		calcStuff(maxK, "maxK");
		calcStuff(maxTurnsK, "maxK turns");
		calcStuff(maxR, "maxR");
		calcStuff(maxTurnsR, "maxR turns");
		zTest(emp, max);
	}

	private static void zTest(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
		double total = 0;
		for(int i = 0;i<arr1.size();i++){
			total+=arr1.get(i)-arr2.get(i);
		}
		double average = total/((double) arr1.size());
		double s = 0;
		for(int i=0;i<arr1.size();i++){
			s+= (average-arr1.get(i)+arr2.get(i))*(average-arr1.get(i)+arr2.get(i))/((double) arr1.size()-1);
		}
		double SE = s/((double) arr1.size());
		double t = average/SE;
		System.out.println("The t test is "+t);
	}

	private static void calcStuff(ArrayList<Integer> arr, String s) {
		double total = 0;
		for(int i = 0;i<arr.size();i++){
			total+=arr.get(i);
		}
		double average = total/((double) arr.size());
		System.out.println(s +" average "+average);
		double variance = 0;
		for(int i = 0;i<arr.size();i++){
			variance += (arr.get(i)-average)*(arr.get(i)-average);
		}
		variance = variance/((double) arr.size());
		System.out.println(s+" std dev "+Math.sqrt(variance));
	}

}
