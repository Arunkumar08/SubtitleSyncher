package arun.works.subsync.gui;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import arun.works.subsync.core.MyTime;
import arun.works.subsync.core.SubtitleDetail;
import arun.works.subsync.core.SubtitleSyncher;
import arun.works.subsync.gui.events.SubmitEventHandler;

public class SubSyncherHomePage extends Application {
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage homeStage) throws Exception {
		SubtitleSyncher syncher = new SubtitleSyncher();
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		Text titleText = new Text("Subtitle Syncher");
		titleText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		gridPane.add(titleText, 0, 0, 4, 1);		
		
		Label filePath = new Label("Select Subtitle File");
		TextField inputFilePath = new TextField();
		Button browseButton = new Button("Browse");
		browseButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
			   FileChooser chooser = new FileChooser();
			   FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Subtitle Files (*srt)", "*.srt");
			   chooser.getExtensionFilters().add(extFilter);
			   File file = chooser.showOpenDialog(null);
			   inputFilePath.setText(file.getPath());
			}
		});
		
		gridPane.add(filePath, 0, 1);
		gridPane.add(inputFilePath, 1, 1);
		gridPane.add(browseButton, 2, 1);
		
		Label syncWord = new Label("Word/Sentence in the File");
		TextField wordOrSentenceInFile = new TextField();
		gridPane.add(syncWord, 0, 2);
		gridPane.add(wordOrSentenceInFile, 1, 2);
		
		Label expectedTimeForWord = new Label("Expected Time For the Word/Sentence");
		TextField expectedTime = new TextField();
		expectedTime.setPromptText("HH:MM:SS");
		gridPane.add(expectedTimeForWord, 0, 3);
		gridPane.add(expectedTime, 1, 3);
		
		Text resultPath = new Text();
		gridPane.add(resultPath, 0, 5, 4, 1);
		
		Button button = new Button();
		button.setText("SyncSubtitle");
		button.setOnAction(new SubmitEventHandler());
		button.setAlignment(Pos.BOTTOM_RIGHT);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SubtitleDetail subtitleDetail = SubtitleDetail.getInstance();
				subtitleDetail.setInputFilePath(inputFilePath.getText());
				subtitleDetail.setWordOrSentenceToStartSync(wordOrSentenceInFile.getText());
				subtitleDetail.setExpectedStartTime(new MyTime(expectedTime.getText()));
				try {
					SubtitleDetail detail = syncher.syncMySubtitle(subtitleDetail);
					resultPath.setFill(Color.FIREBRICK);
					resultPath.setText(detail.getOuputFilePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
		});
		HBox hBox = new HBox(10);
		hBox.getChildren().add(button);
		gridPane.add(hBox, 1, 4);
	
		
		homeStage.setScene(new Scene(gridPane, 600, 500));
		homeStage.show();
	}
}
