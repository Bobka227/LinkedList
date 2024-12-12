/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package GUI;

import Generator.Generator;
import Lists.AbstrDoubleList;
import Lists.IAbstrDoubleList;
import Obec.Obec;
import Obec.Obyvatel;
import Obec.enumKraje;
import Obec.enumPozice;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class ProgObyvatele extends Application {

    private Obyvatel obyvatele = new Obyvatel();  // Объект для работы с населёнными пунктами
    private AbstrDoubleList<Obec> seznamObce = new AbstrDoubleList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Správa obyvatel");

        // Создаем интерфейс
        Label label = new Label("Správa obcí");

        // Поля для ввода данных
        TextField inputObec = new TextField();
        inputObec.setPromptText("Název obce");
        TextField inputPSC = new TextField();
        inputPSC.setPromptText("PSC");
        TextField inputPocetMuzu = new TextField();
        inputPocetMuzu.setPromptText("Počet mužů");
        TextField inputPocetZen = new TextField();
        inputPocetZen.setPromptText("Počet žen");

        // Добавляем ChoiceBox для выбора края
        ChoiceBox<enumKraje> choiceBoxKraj = new ChoiceBox<>();
        choiceBoxKraj.getItems().addAll(enumKraje.values());
        choiceBoxKraj.setValue(enumKraje.HLAVNIMESTOPRAHA);  // Устанавливаем значение по умолчанию

        // Кнопки для операций
        Button btnAddFromKeyboard = new Button("Přidat z klávesnice");
        Button btnLoadFromFile = new Button("Načíst ze souboru");
        Button btnSaveToFile = new Button("Uložit do souboru");
        Button btnGenerateData = new Button("Generovat data");
        Button btnDeleteObec = new Button("Odebrat obec");
        Button btnShowObce = new Button("Zobrazit obce");
        Button btnShowPrumer = new Button("Zobrazit průměr");
        Button btnAccessObec = new Button("Zpřístupnit obec");
        Button btnDeleteObceByKraj = new Button("Vymazat obce podle kraje");
        Button btnShowAboveAverage = new Button("Zobrazit obce nad průměr");

        // Поле для отображения результатов
        TextArea outputArea = new TextArea();
        ListView<Obec> outputing = new ListView<>();

        outputArea.setEditable(false);

        btnAddFromKeyboard.setOnAction(e -> {
            // Создаем модальное окно для выбора позиции
            Stage dialog = new Stage();
            dialog.setTitle("Vyberte pozici pro novou obec");

            // Выбор позиции
            ChoiceBox<enumPozice> choiceBoxPozice = new ChoiceBox<>(FXCollections.observableArrayList(enumPozice.values()));
            choiceBoxPozice.setValue(enumPozice.POSLEDNI);  // Значение по умолчанию

            Button btnConfirm = new Button("Potvrdit");
            btnConfirm.setOnAction(event -> {
                try {
                    // Получаем данные для новой Obec
                    String obecNazev = inputObec.getText();
                    int PSC = Integer.parseInt(inputPSC.getText());
                    int pocetMuzu = Integer.parseInt(inputPocetMuzu.getText());
                    int pocetZen = Integer.parseInt(inputPocetZen.getText());

                    enumKraje kraj = choiceBoxKraj.getValue();  // Получаем выбранный край
                    enumPozice pozice = choiceBoxPozice.getValue();  // Получаем выбранную позицию

                    // Создаем новый объект Obec
                    Obec novaObec = new Obec(obecNazev, kraj, PSC, pocetMuzu, pocetZen);

                    obyvatele.vlozObec(novaObec, pozice, kraj);

                    outputArea.appendText("Obec přidána: " + obecNazev + " do kraje " + kraj + " na pozici: " + pozice + "\n");

                    dialog.close();
                } catch (NumberFormatException ex) {
                    outputArea.appendText("Chyba: Neplatná čísla\n");
                } catch (Exception ex) {
                    outputArea.appendText("Chyba při přidávání obce: " + ex.getMessage() + "\n");
                }
            });

            VBox vbox = new VBox(10, new Label("Vyberte pozici:"), choiceBoxPozice, btnConfirm);
            vbox.setPadding(new Insets(15));

            Scene scene = new Scene(vbox, 500, 300);
            dialog.setScene(scene);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.show();
        });



        // Import
        btnLoadFromFile.setOnAction(event -> {
            String file = "kraje.csv";

            try {
                int imported = obyvatele.importData(file);
                outputArea.appendText("Úspěšně importováno " + imported + " záznamů.\n");
            } catch (IOException e) {
                showAlert("Chyba", "Chyba při načítání dat ze souboru: " + e.getMessage());
            }
        });

        //Generator
        btnGenerateData.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();

            dialog.setTitle("Random");
            dialog.setContentText("Enter the number to generate");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int pocet = Integer.parseInt(dialog.getEditor().getText());
                String seznamGenerace = Generator.generateRandomObec(pocet, obyvatele);
                outputArea.appendText(seznamGenerace);
//            AbstrDoubleList<Obec> seznamObce = obyvatele.getSeznamObce();
//            
//
//            
//            Iterator<Obec> iterator = seznamObce.iterator();
//
//            
//            while (iterator.hasNext()) {
//                Obec obec = iterator.next();
//                outputArea.appendText(seznamGenerace);
//            }
            }
        });

        // Nacteni
        btnSaveToFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File fileToSave = fileChooser.showSaveDialog(primaryStage);
            if (fileToSave != null) {
                try (FileWriter writer = new FileWriter(fileToSave)) {
                    for (enumKraje kraj : enumKraje.values()) {
                        AbstrDoubleList<Obec> seznamObce = obyvatele.krajeObceArray(kraj);
                        if (!seznamObce.jePrazdny()) {
                            Iterator<Obec> iterator = seznamObce.iterator();
                            while (iterator.hasNext()) {
                                Obec obec = iterator.next();
                                writer.write(obec.toString() + "\n");
                            }
                        }
                    }
                    writer.close();
                    outputArea.appendText("Data uložena do souboru\n");
                } catch (IOException ex) {
                    outputArea.appendText("Chyba při ukládání souboru\n");
                }
            }
        });

        // Odeber
        btnDeleteObec.setOnAction(e -> {
            // Диалог для выбора позиции и края
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Odeber Obec");

            ComboBox<enumPozice> poziceComboBox = new ComboBox<>();
            poziceComboBox.getItems().addAll(enumPozice.values());

            ComboBox<enumKraje> krajComboBox = new ComboBox<>();
            krajComboBox.getItems().addAll(enumKraje.values());

            VBox dialogVBox = new VBox();
            dialogVBox.setSpacing(10);
            dialogVBox.getChildren().addAll(
                    new Label("Vyberte pozici:"),
                    poziceComboBox,
                    new Label("Vyberte kraj:"),
                    krajComboBox
            );

            dialog.getDialogPane().setContent(dialogVBox);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    enumPozice selectedPozice = poziceComboBox.getValue();
                    enumKraje selectedKraj = krajComboBox.getValue();

                    if (selectedPozice != null || selectedKraj != null) {
                        Obec odebranaObec = obyvatele.odeberObec(selectedPozice, selectedKraj);

                        if (odebranaObec != null) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Výsledek");
                            alert.setHeaderText(null);
                            alert.setContentText("Obec byla odstraněna: " + odebranaObec.getNazev());
                            alert.showAndWait();

                            outputArea.setText(outputArea.getText().replace(odebranaObec.toString() + "\n", ""));
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Chyba");
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("Nebyla nalezena žádná obec k odstranění.");
                            errorAlert.showAndWait();
                        }
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Chyba");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Pozice nebo kraj nejsou vybrány.");
                        errorAlert.showAndWait();
                    }
                }
                return null;
            });

            dialog.showAndWait();
        });

        // Zobraz
        btnShowObce.setOnAction(e -> {
            try {
                enumKraje kraj = choiceBoxKraj.getValue();

                outputArea.clear();

                StringBuilder output = new StringBuilder();
                obyvatele.zobrazObce(kraj, output);

                outputArea.setText(output.toString());
            } catch (Exception ex) {
                outputArea.appendText("Chyba při zobrazování obcí: " + ex.getMessage() + "\n");
            }
        });

        ChoiceBox<enumKraje> choiceBoxPrumer = new ChoiceBox<>();
        choiceBoxPrumer.getItems().addAll(enumKraje.values());
        choiceBoxPrumer.setPrefWidth(200);

        //Prumer
        btnShowPrumer.setOnAction(e -> {
            Stage dialog = new Stage();
            dialog.setTitle("Zobrazit průměr");

            Button btnCalculatePrumer = new Button("Vypočítat průměr");
            TextArea outputAreaDialog = new TextArea();
            outputAreaDialog.setEditable(false);

            btnCalculatePrumer.setOnAction(event -> {
                try {
                    enumKraje selectedKraj = choiceBoxPrumer.getValue();

                    if (selectedKraj != null) {
                        float prumer = obyvatele.zjistiPrumer(selectedKraj);
                        outputAreaDialog.appendText("Průměr pro kraj " + selectedKraj + ": " + prumer + "\n");
                    } else {
                        float prumer = obyvatele.zjistiPrumer(null);
                        outputAreaDialog.appendText("Průměr pro všechny kraje: " + prumer + "\n");
                    }
                } catch (Exception ex) {
                    outputAreaDialog.appendText("Chyba při výpočtu průměru: " + ex.getMessage() + "\n");
                }
            });

            VBox vbox = new VBox(10, choiceBoxPrumer, btnCalculatePrumer, outputAreaDialog);
            vbox.setPadding(new Insets(15));

            Scene scene = new Scene(vbox, 300, 200);
            dialog.setScene(scene);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.show();
        });

        //Zpristupni
        btnAccessObec.setOnAction(e -> {
            try {
                Stage dialog = new Stage();
                dialog.setTitle("Vyberte pozici a kraj");

                ChoiceBox<enumPozice> choiceBoxPozice = new ChoiceBox<>(FXCollections.observableArrayList(enumPozice.values()));
                choiceBoxPozice.setValue(enumPozice.ACTUALNI);

                ChoiceBox<enumKraje> choiceBoxKrajPozice = new ChoiceBox<>(FXCollections.observableArrayList(enumKraje.values()));
                choiceBoxKrajPozice.setValue(enumKraje.values()[0]);

                Button btnConfirm = new Button("Potvrdit");
                btnConfirm.setOnAction(event -> {
                    try {
                        enumPozice pozice = choiceBoxPozice.getValue();
                        enumKraje kraj = choiceBoxKrajPozice.getValue();

                        Obec obec = obyvatele.zpristupniObec(pozice, kraj);

                        if (obec != null) {
                            outputArea.appendText("Obec z kraje " + kraj.name() + " na pozici " + pozice.name() + ": "
                                    + obec.getNazev()
                                    + ", PSC: " + obec.getPSC()
                                    + ", Muži: " + obec.getPocetMuzu()
                                    + ", Ženy: " + obec.getPocetZen()
                                    + ", Celkem: " + obec.getCelkem() + "\n");
                        } else {
                            outputArea.appendText("Obec na zadané pozici v kraji " + kraj.name() + " není k dispozici.\n");
                        }
                    } catch (Exception ex) {
                        outputArea.appendText("Chyba při zpřístupňování obce: " + ex.getMessage() + "\n");
                    }
                    dialog.close();
                });

                VBox vbox = new VBox(10, new Label("Vyberte pozici:"), choiceBoxPozice, new Label("Vyberte kraj:"), choiceBoxKrajPozice, btnConfirm);
                vbox.setPadding(new Insets(15));

                Scene scene = new Scene(vbox, 300, 200);
                dialog.setScene(scene);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.show();
            } catch (Exception ex) {
                outputArea.appendText("Chyba: " + ex.getMessage() + "\n");
            }
        });

        ChoiceBox<enumKraje> choiceBoxKrajDelete = new ChoiceBox<>(FXCollections.observableArrayList(enumKraje.values()));
        choiceBoxKrajDelete.setValue(null);

        //Zruseni obce podle kraje
        btnDeleteObceByKraj.setOnAction(e -> {
            enumKraje selectedKraj = choiceBoxKrajDelete.getValue();
//
//            if (selectedKraj == null) {
//                outputArea.appendText("Odstraňování všech obcí...\n");
//            } else {
//                outputArea.appendText("Odstraňování obcí v kraji: " + selectedKraj + "\n");
//            }

            try {
                if (selectedKraj == null) {
                    outputArea.appendText("Všechny obce byly úspěšně odstraněny.\n");
                } else {
                    obyvatele.zrus(selectedKraj);
                    outputArea.clear();
                    outputArea.appendText("Obce v kraji " + selectedKraj + " byly úspěšně odstraněny.\n");
                }
            } catch (Exception ex) {
                outputArea.appendText("Chyba při mazání obcí: " + ex.getMessage() + "\n");
            }
        });

        btnShowAboveAverage.setOnAction(e -> {
            try {
                enumKraje selectedKraj = choiceBoxKraj.getValue();
                outputArea.clear();
                PrintStream oldOut = System.out;
                PrintStream taOut = new PrintStream(new OutputStream() {
                    @Override
                    public void write(int b) {
                        outputArea.appendText(String.valueOf((char) b));
                    }
                });
                System.setOut(taOut);

                obyvatele.zobrazObceNadPrumer(selectedKraj);

                System.setOut(oldOut);

            } catch (Exception ex) {
                outputArea.appendText("Chyba při zobrazeni obcí nad průměr: " + ex.getMessage() + "\n");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, inputObec, inputPSC,
                inputPocetMuzu, inputPocetZen, choiceBoxKraj,
                btnAddFromKeyboard, btnLoadFromFile, btnGenerateData, choiceBoxKrajDelete, btnDeleteObceByKraj,
                btnSaveToFile, btnAccessObec, btnDeleteObec, btnShowObce,
                btnShowPrumer, btnShowAboveAverage, outputArea);

        Scene scene = new Scene(layout, 700, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
