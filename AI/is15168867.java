import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

class EvenOddRenderer implements TableCellRenderer {

	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color foreground, background;

		double val = Double.parseDouble(value.toString());
		val = 255 - 255 * ((double) val / (double) GA.max);
		Color col = new Color((int) val, 0, 0);
		foreground = Color.WHITE;
		background = col;

		renderer.setForeground(foreground);
		renderer.setBackground(background);
		return renderer;
	}
}

class GA {
	public static double max = 0;
	
	private static double[][] PopulateSimMatrix(Integer[]ordering, double[][] matrix){
			double [][] sim = new double [matrix.length][matrix.length];
			for (int j = 0; j < sim.length;j++){
				for (int k = 0; k < sim.length;k++){
					sim[j][k] = matrix[ordering[j]][ordering[k]];
				}
			}
			return sim;
	}
	
	public static ArrayList<Integer[]> Selection(ArrayList<Integer[]> orderings,double [][] matrix){
		ArrayList<Integer[]> results = orderings;
		for(int i = 0; i < results.size();i++){
		}
		results.sort((o1,o2)->{
			int result = 0;
			if(CalculateFitnessCost(o1,matrix)>CalculateFitnessCost(o2,matrix)){
				result = 1;
			}
			else if(CalculateFitnessCost(o1,matrix)<CalculateFitnessCost(o2,matrix)){
				result = -1;
			}
			return result;
		});
		int low = (int)((results.size() + 1) / 3.0) -1;
		int high = results.size() - low -1;
		for(int i = high; i < results.size();i++){
			results.set(i, results.get(i-high));
		}
		return results;		
	}
	public static void deepcopy(Integer[] ordering1, Integer[] ordering2){
		for(int i = 0; i < ordering2.length;i++){
			ordering1[i] = ordering2[i];
		}
		
	}
	
	public static Integer[] BestOrdering(ArrayList<Integer[]> orderings, double[][] matrix){
		
		orderings.sort((o1,o2)->{
			int result = 0;
			if(CalculateFitnessCost(o1,matrix)>CalculateFitnessCost(o2,matrix)){
				result = 1;
			}
			else if(CalculateFitnessCost(o1,matrix)<CalculateFitnessCost(o2,matrix)){
				result = -1;
			}
			return result;
		});
		return orderings.get(0);
		
		
	}
	public static ArrayList<Integer[]> Crossover(Integer[] Population1,Integer[] Population2, int N){
		
		int cp = 2 + (int)(Math.random() *((N-2)));
		
		Integer[] Cordering1 = new Integer[Population1.length];
		Integer[] Cordering2 = new Integer[Population2.length];
		
		
		for (int i = 0;i < cp; i++){
			Cordering1[i] = Population1[i];
		}
		
		for(int i = cp; i < N; i++ ){
			Cordering1[i] = Population2[i];
		}
		
		Integer[] temp = new Integer[Cordering1.length];
		Integer[] base = new Integer[Cordering1.length];
		
		for(int i = 0; i < temp.length;i++){
			temp[i] = 0;
		}
		
		for(int i = 0; i < temp.length;i++){
			base[i] = i;
		}
		
		for(int i = 0; i < temp.length;i++){
		for(int j = 0; j < temp.length;j++){
			if(Cordering1[i] == base[j]){
				temp[j]++;
			}
		}
		}
		
		
		for(int i = 0; i < temp.length;i++){
			for(int j = 0; j < temp.length;j++){
				if(temp[j] == 0){
					int num = 0;
					 num = base[j];
					temp[j]++;
					boolean isFound = false;
					for(int d = 0; d < temp.length && !isFound; d++){
						if(temp[d] == 2){
							for(int s = 0; s < temp.length && !isFound;s++){
								if(Cordering1[s] == base[d]){
									Cordering1[s] = num;
									temp[d]--;
									isFound = true;
								}
							}
					}
					}
					
				}
			}
				
		
			/*for (int index = 0; index < Cordering1.length; index++)
				System.out.print(Cordering1[index] + ", ");
			System.out.println("After config1");
			*/
			
		}
		
		Integer[] temp1 = new Integer[Cordering2.length];
		Integer[] base1 = new Integer[Cordering2.length];
		
		for (int i = 0;i < cp; i++){
			Cordering2[i] = Population2[i];
		}
		
		for(int i = cp; i < N; i++){
			Cordering2[i] = Population1[i];
		}
		
		/*for(int i = 0; i < Cordering2.length;i++){
			System.out.print(Cordering2[i]);
		}
		
		System.out.println("Codering2 chopped");
		*/
		
		for(int i = 0; i < temp.length;i++){
			temp1[i] = 0;
		}
		
		for(int i = 0; i < temp.length;i++){
			base1[i] = i;
		}
		
		for(int i = 0; i < temp1.length;i++){
			for(int j = 0; j < temp1.length;j++){
				if(Cordering2[i] == base1[j]){
					temp1[j]++;
				}
			}
			}
			
		
		for(int i = 0; i < temp1.length;i++){
			for(int j = 0; j < temp1.length;j++){
				if(temp1[j] == 0){
					int num = 0;
					 num = base1[j];
					temp1[j]++;
					boolean isFound = false;
					for(int d = 0; d < temp1.length && !isFound; d++){
						if(temp1[d] == 2){
							for(int s = 0; s < temp1.length && !isFound;s++){
								if(Cordering2[s] == base1[d]){
									Cordering2[s] = num;
									temp1[d]--;
									isFound = true;
								}
							}
					}
					}
					
				}
			}
		}
		
	
		for(int z = 0; z < Cordering2.length;z++){
			Population2[z] = Cordering2[z];
		}
		
		for(int z = 0; z < Cordering1.length;z++){
			Population1[z] = Cordering1[z];
		}
		ArrayList<Integer[]> arr = new ArrayList<Integer[]>();
		arr.add(Cordering1);
		arr.add(Cordering2);
		
		return arr;
	}
	
	public static void Mutation(Integer[] ordering){
		int rand =(int) (Math.random() * ordering.length);
		int rand2 = (int)( Math.random()* ordering.length);
		int temp = ordering[rand];
		ordering[rand] = ordering[rand2];
		ordering[rand2] = temp;
	}
	
	public static double CalculateFitnessCost(Integer[] ordering, double [][] matrix){
		double FitnessCost = 0;
		for ( int i = 0; i <matrix.length;i++ ){
			for(int j = 0; j <matrix.length;j++){
				FitnessCost += matrix[ordering[i]][ordering[j]] * (Math.abs(i-j));
			}
		}
		return FitnessCost;
	}

	public static void run() throws Exception{
		int N = 0;
		double[][] matrix;
		matrix = create2DArray("inputbig.txt");
		if (matrix != null){
			sortMatrix(matrix);
			N = matrix.length;
			
		}
		long NFac = 1;
		for (int i = 1;i <= N;i++){
			NFac = NFac * i;
		}
		int v = matrix.length; // The size of the data
		Integer[] ordering = new Integer [v];
		String message = "Error, enter an integer please!";
		
		for(int i = 0; i < matrix.length;i++){
			ordering[i] = i;

		}
		for (int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length;j++){
			if(matrix[i][j] > max){
				max = matrix[i][j];
			}
				
			}
		}
		
		TableCreation(matrix, matrix.length, ordering,"First-Gen");
		
		long p = 0;
		boolean val1 = false;
		while (val1 == false){
		String str = JOptionPane.showInputDialog(null, "Please enter the size of population(must be equal or less than : " + N + "!)");
		try{p = Long.parseLong(str);
		}catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(null, message);
			continue;
		}
		if((p > NFac)){
		JOptionPane.showMessageDialog(null, "Enter a valid input for P");
		}
		else val1 = true;
		}
		int gen = 0;
		boolean val2 = false;
		while(val2 == false){
		String str1 = JOptionPane.showInputDialog(null, "Please enter the number of generations");
		try{gen = Integer.parseInt(str1);
		}catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(null, message);
			continue;
		}
		if(gen < 0){
			JOptionPane.showMessageDialog(null, "Enter a valid number of gen");
		}
		else val2 = true;
		}
		boolean val3 = false;
		int Cr = 0;
		while(val3 == false){
			String str2 = JOptionPane.showInputDialog(null, "Please enter a probability cross-over ranging from 1-100 and where the probability cross-over and the number of generations, when added are less than 100");
			try{Cr = Integer.parseInt(str2);
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null, message);
				continue;
			}
			if ((Cr < 1) || (Cr > 100) ){
				JOptionPane.showMessageDialog(null, "Enter a valid probability cross-over");
			}
			else val3 = true;
		}
		
		boolean val4 = false;
		int Mu = 0;
		while(val4 == false){
			String str3 = JOptionPane.showInputDialog(null, "Please enter a probability for mutation ranging from 1 -100 where both probabilitys when added are less than 100");
			try{Mu = Integer.parseInt(str3);
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null, message);
				continue;
			}
			if((Mu < 1) || (Mu > 100) || (Mu+Cr) > 100 ){
				JOptionPane.showMessageDialog(null, "Enter a valid input!");
			}
			else val4 = true;
		}
		
		boolean val5 = false;
		int H = 0;
		while (val5 == false){
			String st4 = JOptionPane.showInputDialog(null, "Please enter positive generation number where its less than the number of generations");
			try{H = Integer.parseInt(st4);
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null, message);
				continue;
			}
			if(H>=gen){
				JOptionPane.showMessageDialog(null, "Please enter an appropriate generation number");			
			}
			else val5 = true;
		}
		ArrayList<Integer[]> array = new ArrayList<Integer[]>();
		ArrayList<Integer[]> arrayz = new ArrayList<Integer[]>();
		array.add(ordering);
		arrayz.add(ordering);
		
		
		boolean check = false;
		for(int i = 0; i < p-1;){
			check = false;
			Integer[] orderingz = new Integer [v];
			for(int z = 0; z < ordering.length;z++){
				orderingz[z] = ordering[z];
			}
			List<Integer> list = Arrays.asList(orderingz);
			Collections.shuffle(list);
			list.toArray(orderingz);
		
				for(int j = 0; j < array.size();j++){
					if(arraysEqual(orderingz,array.get(j)))
					 check = true;
				}
				
				if (check == false){
					i++;
					Integer[] ordering2 = new Integer [orderingz.length];
					for(int z = 0; z < ordering.length;z++){
						ordering2[z] = orderingz[z];
					}
					array.add(ordering2);
					arrayz.add(ordering2);
					
				}
				
		}
		
		for(int g = 0; g< gen;g++){
			array = Selection(array,matrix);
			ArrayList<Integer[]> NewPopulation = new ArrayList<Integer[]>((int)p);
			
				while(!(array.isEmpty())){
				int rand = 1 + (int)(Math.random() * 100-1);
				if(rand < Cr && array.size()>=2){
					int rand1 = (int)(Math.random() * array.size());
					Integer[] ordering1 = array.get(rand1);
					array.remove(rand1);
					int rand2 = (int)(Math.random() * array.size());
					Integer[] ordering2 = array.get(rand2);
					array.remove(rand2);
					ArrayList<Integer[]> arr = Crossover(ordering1,ordering2,N);
					ordering1 = arr.get(0);
					ordering2 = arr.get(1);
					NewPopulation.add(ordering1);
					NewPopulation.add(ordering2);
				} else if(rand >= Cr && rand <(Cr+Mu)){
					int rand1 =  (int)(Math.random() * array.size());
					Integer[] ordering1 = array.get(rand1);
					Mutation(ordering1);
					array.remove(rand1);
					NewPopulation.add(ordering1);
				}
				else{
					int rand1 =  (int)(Math.random() * array.size());
					Integer[] ordering1 = array.get(rand1);
					array.remove(rand1);
					NewPopulation.add(ordering1);
				}
				
			}
			array = NewPopulation;
			if(g == H){
				Integer[] bestOrder =BestOrdering(array,matrix);
				double[][] simMatrix = PopulateSimMatrix(bestOrder,matrix);
				TableCreation(simMatrix, matrix.length, bestOrder, "Mid-Gen");
			}
		}
		Integer[] bestOrders =BestOrdering(array,matrix);
		double[][] simMatrix = PopulateSimMatrix(bestOrders,matrix);
		TableCreation(simMatrix, simMatrix.length, bestOrders,"Last Gen");
		
		
		PrintWriter writer = new PrintWriter(new FileWriter("AI17.txt",false));
		
		for(int i = 0;i< arrayz.size();i++){
			writer.write("Ordering " + (i+1) + "{");
			for(int j = 0;j<arrayz.get(i).length;j++){
				writer.write(arrayz.get(i)[j] + " ");
			}
			writer.write("}");
			writer.println("");
		}
		writer.close();
	}
	
	
	public static boolean arraysEqual(Integer[] arr1,Integer[]arr2) {
	    if(arr1.length != arr2.length)
	        return false;
	    for(int i = 0; i < arr1.length;i++) {
	        if(arr1[i] != arr2[i])
	            return false;
	    }

	    return true;
	}
	
	public static double[][] create2DArray(String filename) throws Exception{
		double[][] matrix = null;
		
		BufferedReader buff = new BufferedReader(new FileReader(filename));
		
		String line;
		int row = 0;
		int size = 0;
		
		while ((line = buff.readLine()) != null){
			String[] vals = line.trim().split("\\s+");
			
			if (matrix == null){
				size = vals.length;
				matrix = new double [size][size];
			}
			
			for (int col = 0; col < size;col++){
				matrix[row][col] = Double.parseDouble(vals[col]);
			}
			row++;
		}
		
		for (int col = 0; col < size; col++){
			if (matrix[col][col] != 0){
				JOptionPane.showMessageDialog(null, "The diagonal value of the matrix are not 0");
				return null;
			}
		}
		if (row-size != 0){
			JOptionPane.showMessageDialog(null, "Rows and columns not equal!");
			return null;
		}
		
		else
		return matrix;
	}
	
	public static void sortMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length;i++ ){
			double rowAdded = 0;
			int properColumns = 0;
			for (int j = 0; j < matrix.length;j++){
				if(matrix[i][j] !=0){
					properColumns++;
				}
				rowAdded  += matrix[i][j];	
			}
			matrix[i][i] = (rowAdded / properColumns);
		}
		
	}

	public static void TableCreation(double[][] sim, int v, Integer[] ord,String heading) {

		final Object rowData[][] = new Object[v][v];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				rowData[i][j] = sim[i][j];
			}
		}
		final String columnNames[] = new String[v];
		for (int i = 0; i < v; i++) {
			columnNames[i] = ord[i] + "";
		}
		final JTable table = new JTable(rowData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		JFrame frame = new JFrame(heading);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(50*v, 50*v);
		frame.setVisible(true);
	}

}

public class is15168867 {
private static double[][] matrix;
private static int N;

	public static void main(String[] args) throws Exception {
		GA.run();
	}
	
}
