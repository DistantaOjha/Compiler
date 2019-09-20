import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class MiniIDE extends Application 
{
	public static final int WIN_WIDTH = 1200;
	public static final int WIN_HEIGHT = 700;
	private TextArea screenl; 
	private Pane screenr; 
	private Button buttonReg,buttonRegDfa,buttonStackParse;

	@Override
	public void start(Stage primaryStage) 
	{
		screenl = new TextArea(); 
		screenr = new Pane();
		screenr.setMinHeight(600);
		screenr.setMinWidth(600);

		screenl.setMinHeight(600);
		screenl.setPrefColumnCount(10);
		screenl.setMinWidth(600);

		// create the buttons
		buttonReg = new Button("RegEx");
		buttonReg.setStyle("-fx-background-color: red; -fx-pref-width: 200");
		buttonRegDfa = new Button("RegEx+DFA");
		buttonRegDfa.setStyle("-fx-background-color: orange; -fx-pref-width: 200");
	
		buttonStackParse = new Button("LL(1) Stack Parse");
		buttonStackParse.setStyle("-fx-background-color: yellow; -fx-pref-width: 200");
		
		
		// attach a handler to process button clicks    
		buttonReg.setOnAction(new RegButtonHandler());      
		buttonRegDfa.setOnAction(new dfaRegButtonHandler());
		buttonStackParse.setOnAction(new stackParseHandler());

		SplitPane splitPane = new SplitPane(screenl, screenr);
		splitPane.setMinSize(WIN_WIDTH, WIN_HEIGHT-100);

		GridPane buttonPane= new GridPane();
		buttonPane.add(buttonReg,0,0);
		buttonPane.add(buttonRegDfa,1,0);
		buttonPane.add(buttonStackParse,2,0);

		SplitPane mainPane = new SplitPane(buttonPane, splitPane);
		mainPane.setOrientation(Orientation.VERTICAL);
		// set up the scene
		Scene scene = new Scene(mainPane); 

		primaryStage.setTitle("MiniIDE");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Handler for processing the button clicks 
	private class RegButtonHandler implements EventHandler<ActionEvent>
	{        
		@Override 
		public void handle(ActionEvent e) 

		{
			screenr.getChildren().clear();
			//python C:\Users\Distanta\Desktop\python\re_test.py "(a|b)*aba" "a" "b" "aba" "abaaba" "abab"

			String filePath = "/Accounts/turing/students/s22/ojhadi01/cs341/proj3/re_test.py";

			List<String> command= new LinkedList<>();
			command.add("python3");
			command.add(filePath);

			String[] textLeftPanel= screenl.getText().split("\\r?\\n");
			command.addAll(Arrays.asList(textLeftPanel));
	    

			ProcessBuilder builder = new ProcessBuilder(command);
			try {
				Process process = builder.start();
				TextFlow nn =new TextFlow();
				InputStream inputStream = process.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
					nn.getChildren().add(new Text(line));
					nn.getChildren().add(new Text("\n"));
				}
				screenr.getChildren().add(nn);
				inputStream.close();
				bufferedReader.close();

			} catch (IOException e1) {

				e1.printStackTrace();
			}      
		} 
	}

	private class dfaRegButtonHandler implements EventHandler<ActionEvent>
	{        
		@Override 
		public void handle(ActionEvent e) 
		{
			screenr.getChildren().clear();
			//python C:\Users\Distanta\Desktop\python\re_test.py "(a|b)*aba" "a" "b" "aba" "abaaba" "abab"

			String filePath = "/Accounts/turing/students/s22/ojhadi01/cs341/proj3/re_dfa_test.py";

			List<String> command= new LinkedList<>();
			command.add("python3");
			command.add(filePath);

			String[] textLeftPanel= screenl.getText().split("\\r?\\n");
			command.addAll(Arrays.asList(textLeftPanel));
		

			ProcessBuilder builder = new ProcessBuilder(command);
			try {
				Process process = builder.start();
				TextFlow nn =new TextFlow();
				InputStream inputStream = process.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
					nn.getChildren().add(new Text(line));
					nn.getChildren().add(new Text("\n"));
				}
				screenr.getChildren().add(nn);
				inputStream.close();
				bufferedReader.close();

			} catch (IOException e1) {

				e1.printStackTrace();
			}      
		} 
	}
	
	
	private class stackParseHandler implements EventHandler<ActionEvent>
	{        
		@Override 
		public void handle(ActionEvent e) 
		{
			screenr.getChildren().clear();


			String filePath = "/Accounts/turing/students/s22/ojhadi01/cs341/proj3/parse_stack_test.py";
			List<String> command= new LinkedList<>();
			command.add("python3");
			command.add(filePath);
			ProcessBuilder builder = new ProcessBuilder(command);
			String[] textLeftPanel= screenl.getText().replaceAll("\\s+"," ").split("\\r?\\n");
		
			try {
				Process process = builder.start();
				OutputStream oStream = process.getOutputStream();
				Writer oStreamWriter = new OutputStreamWriter(oStream);
				oStreamWriter.write(String.join(" ", textLeftPanel));
				oStreamWriter.close();
				oStream.close();
				TextFlow nn =new TextFlow();
				InputStream inputStream = process.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
					nn.getChildren().add(new Text(line));
					nn.getChildren().add(new Text("\n"));
				}
				screenr.getChildren().add(nn);
				inputStream.close();
				bufferedReader.close();

			} catch (IOException e1) {

				e1.printStackTrace();
			}
			
		} 
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}

