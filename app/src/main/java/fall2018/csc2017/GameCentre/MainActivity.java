package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    /**
     * This is a HashMap where the key is a String representing the user's username and
     * the value is an object of type User, which holds the user's password, security question,
     * and last GameState
     */
    private Map<String, User> logins = new HashMap<>();

    /**
     * Invoked as soon as the app is run. This will load the login screen, read the HashMap
     * from a serialized file, and initialize all the buttons on the screen
     */
    private FileManager fm = new FileManager();

    /**
     * Called when the screen is launched
     *
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.logins = fm.readObject();

        addLoginButtonListener();
        addSignUpButtonListener();
        addForgotPasswordButtonListener();
        setSecurityQuestions();
    }

    /**
     * This method will capture the user-entered text from the TextView widgets, and
     * invoke the authenticate method upon click.
     * return null
     */

    public void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView fieldUsername = findViewById(R.id.field_username);
                final String usernameToAuthenticate = fieldUsername.getText().toString().trim();
                TextView fieldPassword = findViewById(R.id.field_password);
                final String passwordToAuthenticate = fieldPassword.getText().toString().trim();
                //authenticate(usernameToAuthenticate, passwordToAuthenticate);
                LoginManager lm = new LoginManager();
                if (lm.authenticate(usernameToAuthenticate, passwordToAuthenticate)) {
                    gotoGameList();
                }
            }
        });
    }

    /**
     * This method initializes the dropdown displaying the different security questions, and
     * allows the user to choose
     * return null
     */

    public void setSecurityQuestions() {
        Spinner securityQuestions = findViewById(R.id.securityquestions);
        String[] itemsForDropdown = new String[]{"What is your city of birth?",
                "Name of your High School?", "Favorite Teacher?", "First Pet's Name?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        securityQuestions.setAdapter(adapter);
    }

    /**
     * This method initializes the 'Forgot Password?' button, captures the user-entered text from
     * the TextFields and invokes the forgotPassword() method upon click
     * return null
     */

    public void addForgotPasswordButtonListener() {
        Button forgotButton = findViewById(R.id.btn_forgot_password);

        forgotButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView fieldUsername = findViewById(R.id.field_username);
                try {
                    final String userToGetPassword = fieldUsername.getText().toString().trim();
                    TextView answerGiven = findViewById(R.id.field_answer);
                    final String answer = answerGiven.getText().toString().trim();
                    if (answer.equals("")) {
                        Toast.makeText(GlobalApplication.getAppContext(), "Please fill in your username in the login section and fill in the answer to the appropriate question, then press this button to obtain password!", Toast.LENGTH_LONG).show();
                    } else {
                        forgotPassword(userToGetPassword, answer);
                    }

                } catch (NullPointerException e) {//This exception catches if the username entered does not exist
                    makeToast("You must enter in a valid username!");
                }
            }
        });
    }

    /**
     * This method initializes the 'Sign Up' button, and invokes the create() method upon click
     * return null
     */

    public void addSignUpButtonListener() {

        Button signupButton = findViewById(R.id.btn_su);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView fieldUsername = findViewById(R.id.field_username_su);
                final String usernameToAdd = fieldUsername.getText().toString().trim();
                TextView fieldPassword = findViewById(R.id.field_password_su);
                final String passwordToAdd = fieldPassword.getText().toString().trim();
                TextView fieldConfirmPassword = findViewById(R.id.field_confirmpassword_su);
                final String confirmPassword = fieldConfirmPassword.getText().toString().trim();

                TextView answerField = findViewById(R.id.field_answer);
                final String securityAnswer = answerField.getText().toString().trim();

                Spinner securityQuestions = findViewById(R.id.securityquestions);
                String selectedQuestion = securityQuestions.getSelectedItem().toString();

                LoginManager lm = new LoginManager();
                if (lm.create(usernameToAdd, passwordToAdd, confirmPassword, selectedQuestion, securityAnswer)) {
                    gotoGameList();
                }
            }
        });
    }

    /**
     * This method instantiates an Intent object to switch to the GameList
     * return null
     */

    public void gotoGameList() {
        Intent tmp = new Intent(this, GameListActivity.class);
        startActivity(tmp);
    }

    /**
     * This method toasts the user's password on the screen, if the answer entered is correct.
     * Otherwise, it toasts an error message on the screen
     *
     * @param username: represents the username of the person for whom the password is
     *                  to be fetched. answer: represents the answer to the user's security question.
     *                  return none
     */
    public void forgotPassword(String username, String answer) {
        Spinner sec = findViewById(R.id.securityquestions);
        String selq = sec.getSelectedItem().toString();
        if (Objects.requireNonNull(this.logins.get(username)).getSecurityQuestion().equals(selq) &&
                Objects.requireNonNull(this.logins.get(username)).getAnswer().equals(answer)) {
            String password = Objects.requireNonNull(this.logins.get(username)).getPassword();
            makeToast(password);
        } else
            makeToast("Wrong Answer!");
    }

    /**
     * This method makes a Toast/displays text-based information to the user
     *
     * @param textToDisplay: the text to be displayed
     *                       return null
     */

    public void makeToast(String textToDisplay) {
        Toast.makeText(getApplicationContext(), textToDisplay, Toast.LENGTH_LONG).show();
    }

}
