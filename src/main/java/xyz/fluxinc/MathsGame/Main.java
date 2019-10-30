package xyz.fluxinc.MathsGame;

import java.awt.*;									//Handles Window Management
import java.awt.event.ActionEvent;					//Handles Event Management
import java.awt.event.ActionListener;				//Handles Listening for Button
import java.awt.event.FocusEvent;					//Handles Performing Events on Focus on an Element
import java.awt.event.FocusListener;				//Handles Listening for Focus on an Element
import java.io.FileReader;							//Handles Reading Files
import java.io.IOException;							//Handles Input/Output Exceptions
import java.sql.*;									//Handles Database Structure
import java.text.SimpleDateFormat;					//Handles Generating a Date
import java.util.ArrayList;							//Handles Lists
import java.util.Map;								//Handles Mapping Document Elements to a Variable
import java.util.Random;							//Handles Random Number Generation
import java.util.Date;								//Handles Getting the Current Date

import javax.swing.ButtonGroup;						//Handles SWING Button Groups
import javax.swing.JButton;							//Handles SWING Buttons
import javax.swing.JFrame;							//Handles SWING Window Frames
import javax.swing.JLabel;							//Handles SWING Text Labels
import javax.swing.JRadioButton;					//Handles SWING Radio Buttons
import javax.swing.JTextField;						//Handles SWING Text Field
import com.esotericsoftware.yamlbeans.YamlReader;	//Handles Reading YAML Files (The Configuration)

//@SuppressWarnings("unused")
public class Main {
	

	
	/**
	 * Set's up Java Database Connectivity (JDBC) for use. These values are read from the .yml file included. 
	 */
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";	//Defines the location of the MySQL JDBC driver
    private static String DB_URL;										//Defines which database to connect to
    private static String DB_USER;										//Defines the username that will be used to connect to the database
    private static String DB_PASS;										//Defines the password that will be used to connect to the database
    private static String sql;											//This is the string value of the command that will be executed
    private static Connection conn = null;								//This is the connection
    private static Statement stmt = null;								//This is the converted statement to be executed
	/**
	 * This sets up the YAML reader for the config file
	 */
    private static YamlReader configReader;								//This is the YAML File Reader
    private static Object configRead;									//This is the file being read
	private static String usesql;								//This defines the string as to whether SQL will be used
	@SuppressWarnings("rawtypes")								//This supresses any errors that may occur from using the map datatype
    private static Map configMap;										//This is the map of the Config File
	/**
	 * Defines the global variables
	 */
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		//This allows me to get a formatted date when required
	private String mode;															//This sets a string value for the difficulty settings
	private ArrayList<String> operators = new ArrayList<>();					//This is a list of all available operators
	private int score;																//This is the users score
	private Random rand = new Random();												//Creates an instance of the random class, which can be called when required
	private int rand_op;															//The integer value of the required operator
	private float answer;															//This is the answer given by the player
	private float val1;																//The first value generated to be used in the question
	private float val2;																//The second value generated to be used in the question
	private Boolean isValidFloat;													//This is a True/False value as to whether the answer is a number
	
	/**
	 * Defines the Components of the GUI
	 */
	private JFrame frame;						//Creates a JFrame called frame
	private JRadioButton easy;					//Radio Button For Easy
	private JRadioButton normal;				//Radio Button For normal
	private JRadioButton hard;					//Radio Button For Hard
	private JTextField name_input;				//Creates a Text Box to enter a name
	private JLabel question;					//This is the question label
	private JLabel score_label;					//The label that shows the score
	private JTextField ans_input;				//This is the input box for the answer
	private JButton start_submit;				//This is the button, which starts the game
	private JButton ans_submit;					//The button that submits the answer
	private ButtonGroup difficulty;				//Groups the difficulty buttons, to only allow one to be selected
	private JButton nxt_question;				//The button to proceed to the next question
	private JButton restart;					//The button to restart the quiz after a loss

	/**
	 *  Default values for various labels and input boxes
	 */
	private String name_default = "Enter your Name";			//Default text for the input name box
	private String ans_default = "What is your Answer?";		//Default text for the Answer input box
	
	/**
	 * Run when JAR file is executed
	 * @throws SQLException 
	 * @throws IOException 
	 */	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws SQLException, IOException {
		
		try{
			YamlReader reader = new YamlReader(new FileReader("./settings.yml"));	//Tells the reader which file to read
			configRead = reader.read();												//Sets up the reader.
			configMap = (Map)configRead;											//Creates a map of the config file
			usesql = configMap.get("use-storage").toString().trim().toLowerCase();	//Checks whether MySQL is configured
		} catch (IOException e){
			usesql = "false";		//Disables MySQL if the config file is not found
		}

		//Queues the Event to be run
		EventQueue.invokeLater(() -> {									//Creates the class to be run in the event
			try {  											//Attempts to create the window
				Main window = new Main();					//Creates a window
				window.frame.setVisible(true);  			//Makes the window visible
			} catch (Exception e) {							//If fails to make window
				e.printStackTrace();						//Prints the error to the console
			}
		});
		
	}
	
	/**
	 *	The script I use to execute MySQL code. 
	 */
	private boolean sqlExecuter(String URL, String USER, String PASSWORD,  String sqlStatement){
		try{
			Class.forName(JDBC_DRIVER);									//This registers the MySQL driver
			conn = DriverManager.getConnection(URL,USER,PASSWORD); 		//This opens a connection to the DB
			stmt = conn.createStatement();								//Creates a blank MySQL statement
			stmt.execute(sqlStatement);									//Executes the required script
			conn.close();												//Closes the connection to the database
		} catch (ClassNotFoundException e) {	//Runs if the MySQL Driver class is not found
			e.printStackTrace();	//Outputs the Error to the Console			
			usesql = "false";		//Disables the MySQL
			return false;
		} catch (SQLException e1){				//Runs if the MySQL execution failed
			e1.printStackTrace();	//Outputs the Error to the Console
			usesql = "false";		//Disables the MySQL
			return false;
		}	
		return true;
	}
		
	
	public Main(){							//Callable Constructor
		initialise();						//Executes initialise when the constructor is called
	}
	
	private void initialise() {									//Defines the method initialise
		
		/*
		 * Adds all of the operators
		 */
		operators.add(" + ");	//Adds the addition operator
	    operators.add(" - ");	//Adds the subtraction operator
		operators.add(" x ");	//Adds the multiplication operator
		operators.add(" � ");	//Adds the division operator
		/*
		 * Creates the frame
		 */
		frame = new JFrame("Maths Quiz");						//Changes the title of the window
		frame.setBounds(100, 100, 450, 300);					//Decides the size of the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Causes the window to close when exited
		/*
		 * Defines the box to enter a name
		 */
		name_input = new JTextField(name_default);				//Creates the Box
		/*
		 * Sets the default text for the window, then listens for it gaining focus.
		 * When it does, if it is still the default text, clear it.
		 * Once it loses focus, it will check if it is empty and, if it is, put the default text in
		 */
		name_input.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {			//Runs if the text box gains focus
				if (name_input.getText().equals(name_default)){		//Checks if the box contains the default text
					name_input.setText("");		//Clears the box if only the default text is present
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {		//Runs if focus is lost
				if (name_input.getText().isEmpty()){	//Checks if the box is empty
					name_input.setText(name_default);  //Restores the default text if the box is empty
				}
				
			}
			
		});
		
		/**
		 * Defines the Difficulty Selection
		 */
		easy = new JRadioButton("Easy Mode");					//Registers Easy Mode
		easy.setActionCommand("easy");							//Used to read input
		
		normal = new JRadioButton("Normal Mode");				//Registers Normal Mode
		normal.setActionCommand("normal");						//Used to read input
		
		hard = new JRadioButton("Hard Mode");					//Registers Hard Mode
		hard.setActionCommand("hard");							//Used to read input
		
		/**
		 * Group the Radio Buttons to stop people selecting more than one
		 */
		difficulty = new ButtonGroup();
		difficulty.add(easy);
		difficulty.add(normal);
		difficulty.add(hard);
		difficulty.setSelected(easy.getModel(), true);
		
		/**
		 * Defines the Starting Submit Button
		 */
		start_submit = new JButton("Submit");		//Creates the start button, and sets the text to submit
		start_submit.setActionCommand("start");		//Tells the ActionListener what command is being run
		start_submit.addActionListener(new ButtonClickListener()); 		//Sets the action Listener
		
		/**
		 * Defines the Question Label
		 */
		question = new JLabel(" ", JLabel.CENTER);				//Creates the label
		question.setFont(new Font("Serif", Font.PLAIN, 30));	//Sets the font on the label
		
		/**
		 * Defines the Answer input box
		 */
		ans_input = new JTextField();			//Creates a blank text field
		ans_input.addActionListener(new ButtonClickListener()); //Sets the button click listener
		ans_input.setActionCommand("answer");			//Sets the command to be executed
		
		ans_input.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if (ans_input.getText().equals(ans_default)){
					ans_input.setText("");		//Clears the box if only the default text is present
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (ans_input.getText().isEmpty()){
					ans_input.setText(ans_default);  //Restores the default text if the box is empty
				}
				
			}
			
		});
			
		score_label = new JLabel(" ", JLabel.CENTER);				//Creates a blank label
		score_label.setFont(new Font("Serif", Font.PLAIN, 30));		//Sets the font and text size
		
		ans_submit = new JButton("Submit");							//Creates a button with the text "Submit"
		ans_submit.addActionListener(new ButtonClickListener());	//Sets the code to execute when the button is pressed
		ans_submit.setActionCommand("answer");						//Sets the command to be executed
		
		nxt_question = new JButton("Next Question");				//Creates a button with the text "Next Question"
		nxt_question.addActionListener(new ButtonClickListener());	//Sets the code to execute when the button is pressed
		nxt_question.setActionCommand("next");						//Sets the command to be executed
		
		restart = new JButton("Restart");						//Creates a button with the text "Restart"
		restart.addActionListener(new ButtonClickListener());	//Sets the code to execute when the button is pressed
		restart.setActionCommand("restart");					//Sets the command to be executed
		/**
		 * Defines the SQL information, taken from the config
		 */
		if(usesql.equals("true")){
			DB_URL = "jdbc:mysql://" + configMap.get("address") + ":" + configMap.get("port") + "/" + configMap.get("database");	
			DB_USER = (String) configMap.get("user");	//Looks for a value in the config map, namely the username to login to the database
			DB_PASS = (String) configMap.get("pass");	//Looks for a value in the config map, namely the password to login to the database	
			sql = "CREATE TABLE IF NOT EXISTS scores (id int PRIMARY KEY NOT NULL AUTO_INCREMENT, user varchar(255), score int, date varchar(255));";
			Boolean x = sqlExecuter(DB_URL, DB_USER, DB_PASS, sql);	//Uses the sqlExecuter class to start the SQL
			if (x){
				main_menu();
			} else {
				noSave();
			}
		} else {
			noSave();
		}	
		
	}
	
	private class ButtonClickListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();	//Gets the string value of the command
			
			
			if (command.equals("confirm-save")){
				main_menu();	//Opens the main menu
			}
			
			/**
			 * Handles the start button
			 */
			if (command.equals("start")){
				frame.getContentPane().removeAll();		//Clears the window
				
				if (difficulty.getSelection().getActionCommand().equals("easy")){
					mode = "easy";	//Sets the string mode to equal easy
					score = 0;		//Makes sure that the score is reset
					easy();			//Calls the Easy Function
				}
				else if (difficulty.getSelection().getActionCommand().equals("normal")){
					mode = "normal";	//Sets the string mode to equal Normal
					score = 0;			//Makes sure that the score is reset
					normal();			//Calls the Normal Function
				}
				else if (difficulty.getSelection().getActionCommand().equals("hard")){
					mode = "hard";	//Sets the string mode to equal hard
					score = 0;		//Makes sure that the score is reset
					hard();			//Calls the hard Function
					
				}
				else{
					frame.repaint();	//Redraws the window
				}
				
			}
			
			/**
			 * Handles the submit answer button
			 */
			if (command.equals("answer")){
				try {
					Float.parseFloat(ans_input.getText());	//Checks if the answer is a valid number
					isValidFloat = true;	//If so, it sets the check value to true
				} catch(Exception e1){
					isValidFloat = false;	//If not, it sets the check value to false
					
				}
				
				if (isValidFloat){
					if (answer == Float.parseFloat(ans_input.getText())){
						frame.getContentPane().removeAll();	//This clears the window
						score_label.setText("Correct Answer!");	//Sets the score label text to be "Correct Answer!"
						score += 1;	//Increments the score
						frame.setLayout(new GridLayout(2,1));	//Changes the layout of the screen
						frame.add(score_label);		//Adds the score label
						frame.add(nxt_question);	//Adds the next question button
						frame.getRootPane().setDefaultButton(nxt_question);	//Sets the default button
					} 
					
					else if (answer != Float.parseFloat(ans_input.getText())){
						if (usesql.equals("true") && score >= 1){
						
							try{
								Class.forName(JDBC_DRIVER);									//This registers the MySQL driver
								conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS); 		//This opens a connection to the DB
								/**
								 * Prepares a MySQL statement
								 * Since MySQL is vunerable to SQL injections, it is possible
								 * for malicious code to be put into the input box. Thus, 
								 * it is important that the code be sanitised
								 */
								PreparedStatement stmt = conn.prepareStatement("INSERT INTO scores (user,score,date) VALUES (?,?,?)");
								stmt.setString(1, name_input.getText());					//Adds the Name
								stmt.setInt(2, score);										//Adds the Score		
								stmt.setString(3, df.format(new Date()));					//Adds the Third Value, being the date
								//The statement is now Prepared
								stmt.execute();												//Executes the required script
								conn.close();												//Closes the connection to the database
							} catch (ClassNotFoundException e1) {	//Runs if the MySQL Driver class is not found
								e1.printStackTrace();	//Outputs the Error to the Console			
								usesql = "false";		//Disables the MySQL
							} catch (SQLException e1){				//Runs if the MySQL execution failed
								e1.printStackTrace();	//Outputs the Error to the Console
								usesql = "false";		//Disables the MySQL
							}	
						}
						
						frame.getContentPane().removeAll();		//Clears the window
						frame.setLayout(new GridLayout(2,1));	//Changes the layout
						score_label.setText("<html><center>Incorrect Answer!<br>Correct Answer: " + Float.toString(answer).replace(".0","") + "<br>Final Score: "+ score +"</center></html>");
						frame.add(score_label);	//Adds the score label
						frame.add(restart);		//Adds the restart button
						frame.getRootPane().setDefaultButton(restart);	//Sets the default button
					}
					
					else{
						score_label.setText("403: Unhandled Exception in Code"); 
						//Displays if something has gone horribly wrong with the code
					}
					ans_input.setText("");	//Clears the answer box
					frame.repaint();		//Redraws the window
					frame.setVisible(true);	//Sets everything to be visible
				} 
				
				else if (!isValidFloat){
					score_label.setText("That's not a number");	//Sets the score label text
					frame.repaint();	//Redraws the windows
					frame.setVisible(true);	//Sets everything to be visible
				} 
				
				else{
					score_label.setText("405: Unhandled Exception in Code");
					//Displays if something has gone horribly wrong with the code
					frame.repaint();	//Redraws the windows
					frame.setVisible(true);	//Sets everything to be visible
				}

			
			}
			
			if (command.equals("restart")){
				main_menu();	//Opens the main menu
				
			}
			
			if (command.equals("next")){
				/**
				  * This section checks what difficulty you are using
				  * then generates the next question at that difficulty
				  */ 
				if (mode == "easy"){
					easy();		//Calls the easy function
				}
				 if (mode == "normal"){
					normal();	//Calls the normal function
				}
				
				if (mode == "hard"){
					hard();		//Calls the hard function
				}
			}
			
		}

	}
	
	private void main_menu() {

		score = 0;		//Resets the score
		frame.getContentPane().removeAll(); //Clears the frame
		frame.setLayout(new GridLayout(6,1)); //Changes the layout to be a 6x1 grid
		frame.add(new JLabel("Enter Your Name")); //Creates and adds a new label with the text "Enter Your Name"
		frame.add(name_input);	//Adds the name input box
		frame.add(easy);		//Adds the easy radio button
		frame.add(normal);		//Adds the normal radio button
		frame.add(hard);		//Adds the hard radio button
		frame.add(start_submit); //Adds the start button
		frame.getRootPane().setDefaultButton(start_submit);	//Sets the default button to be the start button
		frame.repaint();		//Redraws the frame
		frame.setVisible(true);	//Sets everything to be visible
	}
		
	private void easy() {
		frame.getContentPane().removeAll();
		frame.setLayout(new GridLayout(4,1));	//Changes the windows layout
		rand_op = rand.nextInt(3);				//Sets a random Operator
		val1 = rand.nextInt(10)+1;	//Generates a random number between 1 and 10
		val2 = rand.nextInt(10)+1;	//Generates another random number between 1 and 10
		String str_val1 = Float.toString(val1).replace(".0", "");	//Creates a human readable number for val1
		String str_val2 = Float.toString(val2).replace(".0", "");	//Creates a human readable number for val2
		if (rand_op == 0) {
			answer = val1 + val2; //Sets the answer
			question.setText(str_val1 + operators.get(rand_op) + str_val2);	//Sets the question text
		}
		if (rand_op == 1) {
			if (val1 < val2){
				answer = val2 - val1; //Sets the answer
				question.setText(str_val2 + operators.get(rand_op) + str_val1);	//Sets the question text
			} else {
				answer = val1 - val2; //Sets the answer
			question.setText(str_val1 + operators.get(rand_op) + str_val2);	//Sets the question text
			}
		}
		if (rand_op == 2){
			answer = val1 * val2; //Sets the answer
			question.setText(str_val1 + operators.get(rand_op) + str_val2);	//Sets the question text
			
		}
		score_label.setText("Score: " + Integer.toString(score));	//Sets the score label text
		frame.add(question);		//Adds the question label
		frame.add(ans_input);		//Adds the answer input box
		frame.add(ans_submit);		//Adds the submit button
		frame.add(score_label);		//Adds the score label
		ans_input.requestFocus();	//Puts the cursor in the answer box
		frame.getRootPane().setDefaultButton(ans_submit);	//Sets the default button
		frame.setVisible(true);		//Makes everything visible
	}
	
	private void normal() {
		frame.getContentPane().removeAll();		//Clears the window
		frame.setLayout(new GridLayout(4,1));	//Changes the layout to be 4x1
		ArrayList<String> div = new ArrayList<String>();	//Creates a new list, called div
		div.add("5");		//Adds 5 to the div list
		div.add("2");		//Adds 2 to the div list
		div.add("4");		//Adds 4 to the div list
		rand_op = rand.nextInt(4);	//Selects a random operator
		if (rand_op == 3){
			val1 = rand.nextInt(11)+1;		//Selects a random number between 1 and 10
			val2 = Integer.parseInt(div.get(rand.nextInt(div.size())));		//Gets a value from the div list
			answer = (float) (Math.round((val1 / val2)*100.0)/100.0);		//Rounds the answer to 2 decimal places
		} else if (rand_op == 0) {		
			val1 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			val2 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			answer = val1 + val2;			//Sets the answer
		} else if (rand_op == 1) {
			val1 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			val2 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			answer = val1 - val2;			//Sets the answer
		} else if (rand_op == 2){
			val1 = rand.nextInt(12)+1;		//Gets a random number between 1 and 11
			val2 = rand.nextInt(12)+1;		//Gets a random number between 1 and 11	
			answer = val1 * val2;			//Sets the answer
		}		
		String str_val1 = Float.toString(val1).replace(".0", "");	//Makes the number human readable
		String str_val2 = Float.toString(val2).replace(".0", "");	//Makes the number human readable
		question.setText(str_val1 + operators.get(rand_op) + str_val2);		//Sets the question label
		score_label.setText("Score: " + Integer.toString(score));	//Sets the score label text
		frame.add(question);		//Adds the question label
		frame.add(ans_input);		//Adds the answer input box
		frame.add(ans_submit);		//Adds the submit button
		frame.add(score_label);		//Adds the score label
		ans_input.requestFocus();	//Puts the cursor in the answer box
		frame.getRootPane().setDefaultButton(ans_submit);	//Sets the default button
		frame.setVisible(true);		//Makes everything visible
		
	}
	
	private void hard() {
		frame.getContentPane().removeAll();		//Clears the window
		frame.setLayout(new GridLayout(4,1));	//Changes the layout to be 4x1
		ArrayList<String> div = new ArrayList<String>();	//Creates a new list, called div
		div.add("5");		//Adds 5 to the div list
		div.add("2");		//Adds 2 to the div list
		div.add("4");		//Adds 4 to the div list
		div.add("3");		//Adds 3 to the div list
		div.add("8");		//Adds 8 to the div list
		rand_op = rand.nextInt(4);		//Selects a random operator
		if (rand_op == 3){	
			val1 = rand.nextInt(21)+1;	//Generates a random number between 1 and 20
			val2 = Integer.parseInt(div.get(rand.nextInt(div.size())));	//Selects a value from the div list
			if (val2 == 3){
				answer = (float) (Math.round((val1 / val2)*10.0)/10.0);	//Rounds to 1 d.p.
			} else if (val2 == 8){
				answer = (float) (Math.round((val1 / val2)*1000.0)/1000.0);	//Rounds to 3 d.p.
			} else {
				answer = (float) (Math.round((val1 / val2)*100.0)/100.0);	//Rounds to 2 d.p.
			}
		} else if (rand_op == 0) {		
			val1 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			val2 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			answer = val1 + val2;			//Sets the answer
		} else if (rand_op == 1) {
			val1 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			val2 = rand.nextInt(21)+1;		//Gets a random number between 1 and 20
			answer = val1 - val2;			//Sets the answer
		} else if (rand_op == 2){
			val1 = rand.nextInt(12)+1;		//Gets a random number between 1 and 11
			val2 = rand.nextInt(12)+1;		//Gets a random number between 1 and 11	
			answer = val1 * val2;			//Sets the answer
		}		
		String str_val1 = Float.toString(val1).replace(".0", "");	//Makes the number human readable
		String str_val2 = Float.toString(val2).replace(".0", "");	//Makes the number human readable
		question.setText(str_val1 + operators.get(rand_op) + str_val2);		//Sets the question label
		score_label.setText("Score: " + Integer.toString(score));	//Sets the score label text
		frame.add(question);		//Adds the question label
		frame.add(ans_input);		//Adds the answer input box
		frame.add(ans_submit);		//Adds the submit button
		frame.add(score_label);		//Adds the score label
		ans_input.requestFocus();	//Puts the cursor in the answer box
		frame.getRootPane().setDefaultButton(ans_submit);	//Sets the default button
		frame.setVisible(true);		//Makes everything visible
		
	}
	
	private void noSave(){
		frame.setLayout(new GridLayout(2,1));	//Sets the layout for the window.
		frame.add(new JLabel("<html><center>WARNING:<br>Your scores will not be saved this session</center></html>", JLabel.CENTER));	//Creates a label
		JButton btnConfirmSave = new JButton("OK");	//Creates a new button
		btnConfirmSave.setActionCommand("confirm-save");	//Sets the command
		btnConfirmSave.addActionListener(new ButtonClickListener());	//Sets the listener
		frame.add(btnConfirmSave);	//Adds the button to the window
	}
}


