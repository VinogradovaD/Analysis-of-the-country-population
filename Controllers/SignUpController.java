public class SignUpController {

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private Text text;
    @FXML
    private ImageView backButton;
    @FXML
    void initialize() {
        backButton.setOnMouseClicked ((MouseEvent e) -> {
            Stage stage = (Stage) SignUpButton.getScene ().getWindow ();
            stage.close ();
            Main.getStage ("SignIn.fxml");

        });
    }
    @FXML
    void SignUpAction() throws IOException {
        String firstname = firstName.getText ();
        String lastname = lastName.getText ();
        String loginText = login.getText ();
        String passwordText = password.getText ();

        if (!firstname.equals ("") && !lastname.equals ("") && !loginText.equals ("") && !passwordText.equals ("")) {
            {
                if (Pattern.matches ("\\w{6,16}", passwordText) && Pattern.matches ("\\w{6,16}", loginText)) {
                    DatabaseHandler dbHandler = new DatabaseHandler ();
                    ResultSet result = dbHandler.UnicUser (loginText);
                    int counter = 0;
                    try {
                        while (result.next ()) {
                            counter++;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace ();
                    }
                    if (counter == 0) {
                        dbHandler.SignUpUser (firstname, lastname, loginText, passwordText);
                        Alert alert = new Alert (Alert.AlertType.INFORMATION);
                        alert.setTitle ("Поздравляем!");
                        alert.setHeaderText (null);
                        alert.setContentText ("Вы успешно зарегистрировались");
                        alert.showAndWait ();
                        Stage stage = (Stage) SignUpButton.getScene ().getWindow ();
                        stage.close ();
                        Main.getStage ("SignIn.fxml");
                    } else {
                        text.setText ("Логин занят");
                    }
                } else text.setText ("Логин/пароль должен состоят не менее, чем из 6 символов");
            }
        } else text.setText ("Поля не должны быть пустыми");

    }
}
