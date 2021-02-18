package main;

public class EditorMain {

	public static void main(String[] args) {

		try {
			// Uncomment the line below to open the editor with no input file
			new EditorDisplay();

			// Uncomment the line below to open the editor with an input file that has a
			// single line of text
//			new EditorDisplay("single_line_file.txt");

			// Uncomment the line below to open the editor with an input file that has
			// multiple lines of text
//			new EditorDisplay("multi_line_file.txt");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
