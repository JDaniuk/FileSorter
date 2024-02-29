package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javafx.scene.layout.VBox;

import javax.swing.filechooser.FileSystemView;

public class HelloApplication extends Application {

    HashSet<Path> allFiles = new HashSet<>();
    HashSet<String> TextFileTypes = new HashSet<>();

    Path workingDir;
    Path TextFilesDir = Paths.get("./TextFiles");
    HashSet<String> ImageFileTypes = new HashSet<>();
    HashSet<String> pdfFileTypes = new HashSet<>();
    HashSet<String> ArchiveFileTypes = new HashSet<>();
    HashSet<String> OtherFileTypes = new HashSet<>();
    HashMap<String, Boolean> FileOptions = new HashMap<String, Boolean>();

    Button button = new Button("Sort Files");
    CheckBox textFilesCheckBox = new CheckBox("Text Files, Documents, etc.");

    CheckBox imagesCheckBox = new CheckBox("JPG's, PNG's, etc.");

    CheckBox pdfCheckBox = new CheckBox("PDF's");

    CheckBox archivesCheckBox = new CheckBox(".rar's, .zip's, etc.");

    CheckBox otherFilesCheckBox = new CheckBox("Other Files");

    static VBox root = new VBox(10);
    @Override
    public void start(Stage stage) throws IOException {
        initialize();






        Scene scene = new Scene(root, 800, 600);
        button.setOnAction( event->{
            getFiles();
            allFiles.stream().forEach(this::sortFile);
        });


        textFilesCheckBox.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                FileOptions.put("text",t1);
            }
        }));

        imagesCheckBox.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                FileOptions.put("images",t1);
            }
        }));


        pdfCheckBox.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                FileOptions.put("pdf",t1);
            }
        }));


        archivesCheckBox.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                FileOptions.put("archives",t1);
            }
        }));

        otherFilesCheckBox.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                FileOptions.put("others",t1);
            }
        }));

        root.getChildren().addAll(textFilesCheckBox,imagesCheckBox,pdfCheckBox,archivesCheckBox,otherFilesCheckBox,button);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    private void sortFiles(){
        allFiles.stream().forEach(this::sortFile);
    }

    private void sortFile(Path path){
        String extension = getFileTypeGroup(path);
        if(extension.equals(".lnk") || extension.equals(".url")){
            return;
        }else{
            if(FileOptions.get("text")){
                if(TextFileTypes.contains(getFileTypeGroup(path)))
                System.out.println(path);
             }
            if(FileOptions.get("images")){
                if(ImageFileTypes.contains(getFileTypeGroup(path)))
                    System.out.println(path);
            }
            if(FileOptions.get("pdf")){
                if(pdfFileTypes.contains(getFileTypeGroup(path)))
                    System.out.println(path);
            }
            if(FileOptions.get("archives")){
                if(ArchiveFileTypes.contains(getFileTypeGroup(path)))
                    System.out.println(path);
            }
            if(FileOptions.get("others")){
                if(OtherFileTypes.contains(getFileTypeGroup(path)))
                    System.out.println(path);
            }
        }
    }

    private String getFileTypeGroup(Path path){
    String name = path.getFileName().toString();
    int index = name.lastIndexOf(".");
    String extension =name.substring(index);
    return extension;
    }

    boolean getFiles(){

        try {
            Files.list(Paths.get(workingDir.toString())).filter(Files::isRegularFile).forEach(path -> allFiles.add(path));

        }catch (IOException exception){
            return false;
        }
        return true;

    }

    void initialize(){
        FileSystemView view = FileSystemView.getFileSystemView();

        File file = view.getHomeDirectory();
        String desktopPath = file.getPath();
        workingDir = file.toPath();
        try{

        Files.createDirectory(workingDir.resolve(TextFilesDir.toString()));}
        catch (Exception e){
            System.out.println(e);
        }
        FileOptions.put("text",false);
        FileOptions.put("images",false);
        FileOptions.put("pdf",false);
        FileOptions.put("archives",false);
        FileOptions.put("others",false);
        TextFileTypes.add(".txt");
        TextFileTypes.add(".docx");
        ImageFileTypes.add(".png");
        ImageFileTypes.add(".jpg");
        pdfFileTypes.add(".pdf");
        ArchiveFileTypes.add(".rar");
    }
}

/*
TODO:
1. Ogarnąć sety typów pliku
2. dodać funkcjonalność tego że sortujemy pilk danego typu jeśli jest odpowieni checkox
3. rzeczywiście zaimplementować sortowanie
4. ogarnąć front

 */