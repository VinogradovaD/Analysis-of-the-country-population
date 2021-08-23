public class MnkController {

    @FXML
    private LineChart lineChart;
    @FXML
    NumberAxis yAxis;
    @FXML
    private ImageView backimage, saveimage;
    @FXML
    private Text result;
    @FXML
    private TextField birth, death, peopleIn, peopleOut, peopleAll;


    @FXML
    void initialize() {
        LineChart ();
        ComeBack ();}   

    @FXML
    void LineChart() {
        XYChart.Series trueValues = new XYChart.Series ();
        trueValues.setName ("Истинные значения");

        yAxis = (NumberAxis) lineChart.getYAxis ();
        yAxis.setAutoRanging (false);
        yAxis.setLowerBound (140000000);
        yAxis.setTickUnit (1000000);
        yAxis.setUpperBound (150000000);
        for (int i = 0; i < MainController.list.size (); i++) {
            trueValues.getData ().add (new XYChart.Data (MainController.list.get (i).getYear (), MainController.list.get (i).getPeopleAll ()));}        
        lineChart.getData ().add (trueValues);
        trueValues.getNode ().setStyle ("-fx-stroke: #003333; ");

        XYChart.Series expValues = new XYChart.Series ();
        expValues.setName ("Экспериментальные значения");
        double[] coef = MNK.coefMnk ().clone ();
        for (int i = 0; i < MainController.list.size () - 1; i++) {
            expValues.getData ().add (new XYChart.Data (MainController.list.get (i + 1).getYear (), MNK.mnkValues.get (i)));
        }
        lineChart.getData ().add (expValues);
        expValues.getNode ().setStyle ("-fx-stroke: #FF0000; ");}

    @FXML
    void ComeBack() {
        backimage.setOnMouseClicked ((MouseEvent e) -> {
            Stage stage = (Stage) lineChart.getScene ().getWindow ();
            stage.close ();  });  }

    @FXML
    void progButton() {
        double[] coef = MNK.coefMnk ().clone ();
        int birthInt = Integer.parseInt (birth.getText ().replaceAll ("\\s+", ""));
        int deathInt = Integer.parseInt (death.getText ().replaceAll ("\\s+", ""));
        int peopleInInt = Integer.parseInt (peopleIn.getText ().replaceAll ("\\s+", ""));
        int peopleOutInt = Integer.parseInt (peopleOut.getText ().replaceAll ("\\s+", ""));
        int peopleAllInt = Integer.parseInt (peopleAll.getText ().replaceAll ("\\s+", ""));
        int res = (int) (coef[0] * peopleAllInt + coef[1] * birthInt + coef[2] * deathInt + coef[3] * peopleInInt + coef[4] * peopleOutInt);
        String r = String.valueOf (res);
        result.setText (r);
        //добавляю в базу данных
        DatabaseHandler dbHandler = new DatabaseHandler ();
        dbHandler.addDataUser (birth.getText (), death.getText (), peopleIn.getText (), peopleOut.getText (), r, SignInController.id);
        //сохраняю файл с прогнозом
        saveimage.setOnMouseClicked ((MouseEvent e) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser ();
            directoryChooser.setTitle ("Сохранение");
            File file = directoryChooser.showDialog (null);
            String path = file.toString ();
            File fileout = new File (path, "Прогноз.txt");
            try {
                FileWriter fileWriter = new FileWriter (fileout);
                String string = "Население за прошлый год: " + peopleAllInt + "\n" + "Рождаемость: " + birthInt + "\n" + "Смертность: " + deathInt + "\n" + "Иммиграция: " + peopleInInt + "\n" + "Эмиграция: " + peopleOutInt + "\n" + "Прогноз населения: " + res + "";
                fileWriter.write (string);
                fileWriter.flush ();
            } catch (IOException ex) {
                ex.printStackTrace ();
            } 
	});
}
}

