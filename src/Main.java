import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main extends Application {

    XYChart.Series series = new XYChart.Series();

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage){
        primaryStage.setTitle("Laboratoire 7 : The force awakens");
        primaryStage.setResizable(true);
        primaryStage.setMaximized(false);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);

        BorderPane borderPane = new BorderPane();

        Menu importer = new Menu("Importer");
        Menu exporter = new Menu("Exporter");
        MenuItem ligne = new MenuItem("Lignes");
        MenuItem region = new MenuItem("Régions");
        MenuItem barre = new MenuItem("Barres");
        MenuItem png = new MenuItem("PNG");
        MenuItem gif = new MenuItem("GIF");

        importer.getItems().addAll(ligne,region,barre);
        exporter.getItems().addAll(png,gif);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(importer,exporter);

        borderPane.setTop(menuBar);


        ligne.setOnAction(event -> {
            File fichier = open(primaryStage);
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Mois");
            yAxis.setLabel("Temperature");

            final LineChart<String,Number> chart = new LineChart<String, Number>(xAxis,yAxis);
            chart.setTitle("Températures moyennes");
            try{

                List<String> toutesLigne = Files.readAllLines(fichier.toPath());
                series.setName("Données");

                String[] mois = toutesLigne.get(0).split(",");
                String[] data = toutesLigne.get(1).split(",");

                for (int i=0;i<mois.length;i++){
                    series.getData().add(new XYChart.Data(mois[i],Integer.parseInt(data[i].trim())));
                }
            }
            catch(Exception exception){
                System.out.println("Impossible de charger le fichier");
            }
            chart.getData().addAll(series);
            borderPane.setCenter(chart);
        });
        region.setOnAction(event -> {
            File fichier = open(primaryStage);
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Mois");
            yAxis.setLabel("Temperature");

            final AreaChart<String,Number> chart = new AreaChart<String, Number>(xAxis,yAxis);
            chart.setTitle("Températures moyennes");
            try{

                List<String> toutesLigne = Files.readAllLines(fichier.toPath());
                series.setName("Données");

                String[] mois = toutesLigne.get(0).split(",");
                String[] data = toutesLigne.get(1).split(",");

                for (int i=0;i<mois.length;i++){
                    series.getData().add(new XYChart.Data(mois[i],Integer.parseInt(data[i].trim())));
                }
            }
            catch(Exception exception){
                System.out.println("Impossible de charger le fichier");
            }
            chart.getData().addAll(series);
            borderPane.setCenter(chart);
        });
        barre.setOnAction(event -> {
            File fichier = open(primaryStage);
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Mois");
            yAxis.setLabel("Temperature");

            final BarChart<String,Number> chart = new BarChart<String, Number>(xAxis,yAxis);
            chart.setTitle("Températures moyennes");
            try{

                List<String> toutesLigne = Files.readAllLines(fichier.toPath());
                series.setName("Données");

                String[] mois = toutesLigne.get(0).split(",");
                String[] data = toutesLigne.get(1).split(",");

                for (int i=0;i<mois.length;i++){
                    series.getData().add(new XYChart.Data(mois[i],Integer.parseInt(data[i].trim())));
                }
            }
            catch(Exception exception){
                System.out.println("Impossible de charger le fichier");
            }
            chart.getData().addAll(series);
            borderPane.setCenter(chart);
        });
        png.setOnAction(event -> {
            saveAsPNG(borderPane.getCenter(),primaryStage);
        });
        gif.setOnAction(event -> {
            saveAsGIF(borderPane.getCenter(),primaryStage);
        });
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public File open(Stage stage){
        FileChooser fc = new FileChooser();
        fc.setTitle("Veuillez sélectionner un fichier");

        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Fichier .dat","*.dat"
                )
        );
        File fichier = fc.showOpenDialog(stage);
        return fichier;
    }
    public void saveAsPNG(Node chart, Stage stage) {
        if (chart!=null){
            WritableImage image = chart.snapshot(new SnapshotParameters(), null);
            FileChooser fc = new FileChooser();
            fc.setTitle("Enregistrez sous");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PNG","*.png"));
            File fichier = fc.showSaveDialog(stage);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fichier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveAsGIF(Node chart, Stage stage) {
        if (chart!=null){
            WritableImage image = chart.snapshot(new SnapshotParameters(), null);
            FileChooser fc = new FileChooser();
            fc.setTitle("Enregistrez sous");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier GIF","*.gif"));
            File fichier = fc.showSaveDialog(stage);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "gif", fichier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
