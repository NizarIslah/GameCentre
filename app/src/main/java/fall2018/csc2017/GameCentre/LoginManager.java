package fall2018.csc2017.GameCentre;

import android.widget.Toast;

import java.util.Map;
import java.util.Objects;

public class LoginManager {

    /**
     * Represents the person logged in
     */
    private String personLoggedIn = null;
    /**
     * Instance of file manager to help in accessing
     * saved data and saving objects to the .ser file
     */
    private FileManager fm;

    /**
     * The login manager constructor, helps in logging people in
     * and manages their data
     */
    public LoginManager() {
        this.fm = new FileManager();
        for (User user : fm.readObject().values()) {
            if (user.getLoggedIn()) {
                assert personLoggedIn == null; //we should only be setting this once
                personLoggedIn = user.getUsername();
            }
        }
    }

    /**
     * Returns the username of the person currently logged in
     *
     * @return a String representing the username of the person currently logged in
     */
    public String getPersonLoggedIn() {
        return this.personLoggedIn;
    }

    /**
     * This method instantiates a new object of type User and adds it to the Map.
     * The Map is then saved in the serialized file.
     *
     * @param username: the username of this new account
     *                  password: the password of this new account
     *                  password2: the confirm password of this new account
     *                  selQ: the security question of this new account
     *                  ans: the answer to the user's security question
     */

    boolean create(String username, String password, String password2, String selQ, String ans) {
        if (username.equals("") || password.equals("") || ans.equals(""))
            makeToast("You must enter username, password, and security answer!");
        else if (!password.equals(password2))
            makeToast("Passwords do not match!");
        else if (fm.readObject().containsKey(username))
            makeToast("User Already Exists!");
        else {
            User newUser = new User(username, password, selQ, ans);
            Map<String, User> hm = fm.readObject();
            hm.put(username, newUser);

            fm.saveObject(hm);

            fm.saveObject(setUsersLoggedOut(fm.readObject()));
            setLoggedInTrueAndSave(newUser);

            makeToast("Success!");
            return true;
        }
        return false;
    }

    /**
     * Returns true if the User whose username is username is present in the Map
     * and the password entered is correct, false otherwise
     *
     * @param username, A String representing the username of the User.
     * @param password, A String representing the password entered by the user
     * @return true if password is correct and user exists, false otherwise
     */

    boolean authenticate(String username, String password) {
        if (!fm.readObject().containsKey(username))
            makeToast("User Does Not Exist!");
        else if (!Objects.requireNonNull(fm.readObject().get(username)).getPassword().equals(password))
            makeToast("Password Rejected!");
        else {
            fm.saveObject(setUsersLoggedOut(fm.readObject()));
            setLoggedInTrueAndSave(Objects.requireNonNull(fm.readObject().get(username)));
            personLoggedIn = username;
            makeToast("Logging in...");
            fm.saveObject(fm.readObject());
            return true;
        }
        return false;
    }

    /**
     * Returns true if the User whose username is username is present in the Map
     * and the password entered is correct, false otherwise
     *
     * @param username, A String representing the username of the User
     * @param password, A String representing the password of the User
     * @return true if User exists and password is correct, false otherwise
     */

    boolean authenticateP2(String username, String password) {
        if (!fm.readObject().containsKey(username)) {
            makeToast("User Does Not Exist!");
            return false;
        } else if (!Objects.requireNonNull(fm.readObject().get(username)).getPassword().equals(password)) {
            makeToast("Password Rejected!");
            return false;
        } else {
            return true;
        }
    }

    /**
     * This function iterates through the Map and logs all Users out
     * by changing the isLoggedIn attribute of all Users to false
     *
     * @param UsersMap, the Map mapping username to User object
     * @return UsersMap, a Map where all the Users have been logged out
     */
    private Map<String, User> setUsersLoggedOut(Map<String, User> UsersMap) {
        if (personLoggedIn != null) {
            for (User user : UsersMap.values()) {
                user.setLoggedIn(false);
            }
        }
        return UsersMap;
    }

    /**
     * This method sets the User represented by user to logged in, by changing this
     * user's isLoggedIn attribute to true
     *
     * @param user, a User object representing the current user
     */

    private void setLoggedInTrueAndSave(User user) {
        Map<String, User> hm = fm.readObject();
        Objects.requireNonNull(hm.get(user.getUsername())).setLoggedIn(true);
        fm.saveObject(hm);
    }

    /**
     * This method takes a String and makes a Toast to display the String on the screen
     *
     * @param textToDisplay, a String representing the text that must be toasted to the screen
     */

    private void makeToast(String textToDisplay) {
        try {
            Toast.makeText(GlobalApplication.getAppContext(), textToDisplay, Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

