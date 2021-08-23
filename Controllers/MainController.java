public class MainController {

    public static ObservableList<Data> list = FXCollections.observableArrayList ();
    @FXML
    private LineChart lineChart;
    @FXML
    NumberAxis yAxis;
    @FXML
    private BarChart barChart;
    @FXML
    private Button chooseButton;
    @FXML
    private ComboBox cBoxLinechart, cBoxBarchart;
    @FXML
    private TableView<Data> tableView;
    @FXML
    private TableColumn<Data, String> yearColumn;
    @FXML
    private TableColumn<Data, Integer> birthColumn, deathColumn, peopleInColumn, peopleOutColumn, peopleAllColumn;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    ImageView exitImage;


    @FXML
    void initialize() {
        Exit ();

    }


    @FXML
    void OpenAndReadTxt() throws NullPointerException {
        String fname;
        FileChooser fileChooser = new FileChooser ();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle ("Выберите файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter ("TXT files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters ().add (extFilter);
        File file = fileChooser.showOpenDialog (null);//Указываем текущую сцену CodeNote.mainStage
        fname = file.toString ();
        try (BufferedReader br = new BufferedReader (new FileReader (fname))) {
            int i = 0;
            String s;
            String[] split;
            while ((s = br.readLine ()) != null) {
                split = s.split (",");
                list.add (new Data (split[0], Integer.parseInt (split[1].replaceAll ("\\s+", "")), Integer.parseInt (split[2].replaceAll ("\\s+", "")),
                        Integer.parseInt (split[3].replaceAll ("\\s+", "")), Integer.parseInt (split[4]), Integer.parseInt (split[5].replaceAll ("\\s+", ""))));
            }
        } catch (IOException | NumberFormatException e) {
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setTitle ("Ошибка!");
            alert.setHeaderText (null);
            alert.setContentText ("Неверный формат файла");
            alert.showAndWait ();
        }
        Table ();
        BarChartCBoxItems ();
    }

    @FXML
    void Table() {
        yearColumn.setCellValueFactory (new PropertyValueFactory<Data, String> ("year"));
        peopleAllColumn.setCellValueFactory (new PropertyValueFactory<Data, Integer> ("peopleAll"));
        birthColumn.setCellValueFactory (new PropertyValueFactory<Data, Integer> ("birth"));
        deathColumn.setCellValueFactory (new PropertyValueFactory<Data, Integer> ("death"));
        peopleInColumn.setCellValueFactory (new PropertyValueFactory<Data, Integer> ("peopleIn"));
        peopleOutColumn.setCellValueFactory (new PropertyValueFactory<Data, Integer> ("peopleOut"));
        tableView.setItems (list);
    }

    @FXML
    void LineChart() {
        lineChart.getData ().removeAll (Collections.singleton (lineChart.getData ().setAll ()));//очищаем лайнчарт
        XYChart.Series series = new XYChart.Series ();
        String boxValue = (String) cBoxLinechart.getValue ();
        yAxis = (NumberAxis) lineChart.getYAxis ();
        for (int i = 0; i < list.size (); i++) {
            if (boxValue.equals ("Рост населения")) {
                series.getData ().add (new XYChart.Data (list.get (i).getYear (), list.get (i).getPeopleAll ()));
                lineChart.getYAxis ().setAutoRanging (false);
                yAxis.setLowerBound (140000000);
                yAxis.setTickUnit (500000);
                yAxis.setUpperBound (150000000);
            }
            if (boxValue.equals ("Рост рождаемости")) {
                series.getData ().add (new XYChart.Data (list.get (i).getYear (), list.get (i).getBirth ()));
                lineChart.getYAxis ().setAutoRanging (true);
            }

            if (boxValue.equals ("Рост смертности")) {
                series.getData ().add (new XYChart.Data (list.get (i).getYear (), list.get (i).getDeath ()));
                lineChart.getYAxis ().setAutoRanging (true);
            }
            if (boxValue.equals ("Рост иммиграции")) {
                series.getData ().add (new XYChart.Data (list.get (i).getYear (), list.get (i).getPeopleIn ()));
                lineChart.getYAxis ().setAutoRanging (true);
            }
            if (boxValue.equals ("Рост эмиграции")) {
                series.getData ().add (new XYChart.Data (list.get (i).getYear (), list.get (i).getPeopleOut ()));
                lineChart.getYAxis ().setAutoRanging (true);
            }
        }
        String color = String.valueOf (colorPicker.getValue ()).substring (2, 8);
        lineChart.getData ().add (series);
        series.getNode ().setStyle ("-fx-stroke: #" + color + "; ");
    }

    @FXML
    void BarChartCBoxItems() {
        ObservableList<String> years = FXCollections.observableArrayList ();
        for (int i = 0; i < list.size (); i++) {
            years.add (list.get (i).getYear ());
        }
        cBoxBarchart.setItems (years);
    }

    @FXML
    void BarChart() {
        barChart.getData ().removeAll (Collections.singleton (barChart.getData ().setAll ()));
        String boxValue = (String) cBoxBarchart.getValue ();
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<String, Integer> ();

        for (int i = 0; i < list.size (); i++) {
            if (list.get (i).getYear ().equals (boxValue)) {
                dataSeries.getData ().add (new XYChart.Data<String, Integer> ("Рождаемость", list.get (i).getBirth ()));
                dataSeries.getData ().add (new XYChart.Data<String, Integer> ("Смертность", list.get (i).getDeath ()));
                dataSeries.getData ().add (new XYChart.Data<String, Integer> ("Иммиграция", list.get (i).getPeopleIn ()));
                dataSeries.getData ().add (new XYChart.Data<String, Integer> ("Эмиграция", list.get (i).getPeopleOut ()));
            }
        }
        barChart.getData ().add (dataSeries);
    }

    @FXML
    void Exit() {
        exitImage.setOnMouseClicked ((MouseEvent e) -> {
            Stage stage = (Stage) chooseButton.getScene ().getWindow ();
            stage.close ();
            Main.getStage ("SignIn.fxml");
        });
    }

    @FXML
    void MNK() {
        Main.getStage ("Main2.fxml");}} 
