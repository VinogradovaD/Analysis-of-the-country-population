public class SignInController {
    static int id;
    @FXML
    private TextField Login;
    @FXML
    private PasswordField Password;
    @FXML
    private Button SignInButton;
   @FXML
    private Button SignUpButton;
    @FXML
    private Text text;
    @FXML
    void initialize() {
        SignInButton.setOnAction (event -> {
            String loginText = Login.getText ();
            String passText = Password.getText ();
            if (!loginText.equals ("") && !passText.equals ("")) {
                loginUser (loginText, passText);
            } else {
                text.setText ("Поля не должны быть пустыми");
            }
        });

        SignUpButton.setOnAction (event -> {
            Stage stage = (Stage) SignUpButton.getScene ().getWindow ();
            stage.close ();
            Main.getStage ("SignUp.fxml");
        });
    }
    private void loginUser(String loginText, String passText) {
        DatabaseHandler dbHandler = new DatabaseHandler ();
        ResultSet result = dbHandler.getUser (loginText, passText);
        id=dbHandler.idUser (loginText, passText);
        int counter = 0;
        try {
            while (result.next ()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        if (counter >= 1) {
           Stage stage = (Stage) SignUpButton.getScene ().getWindow ();
           stage.close ();
           Main.getStage ("Main.fxml");

        } else {
            text.setText ("Пользователь не найден:(");
        }
    }
}
