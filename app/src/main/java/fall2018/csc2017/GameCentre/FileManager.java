package fall2018.csc2017.GameCentre;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FileManager implements Serializable {

    /**
     * The file manager constructor, constructs
     * a file manager, enabling reading of the
     * serialized hash map
     */
    public FileManager() {
        Map<String, User> HMfromfile = readObject();
        if (HMfromfile == null) {
            saveObject(new HashMap<String, User>());
        }
    }

    /**
     * Reads the serialized file and returns the read
     * hash map
     *
     * @return the read hash map from the .ser file
     */
    Map<String, User> readObject() {
        FileInputStream fis;
        ObjectInputStream objectIn;
        try {
            fis = GlobalApplication.getAppContext().openFileInput("testFile2.ser");
            objectIn = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            Map<String, User> mapFromFile = (Map<String, User>) objectIn.readObject();

            objectIn.close();
            return mapFromFile;
        } catch (NullPointerException e) {
            return new HashMap<>();
        } catch (ClassCastException ca) {
            System.out.println("unable to Cast");
        } catch (ClassNotFoundException c) {
            System.out.println("CLASS NOT FOUND WHILE READING FROM SERIALIZED FILE");
        } catch (FileNotFoundException f) {
            System.out.println("FILE NOT FOUND WHILE READING FROM SERIALIZED FILE");
        } catch (IOException e) {
            System.out.println("IO EXCEPTION WHILE READING FROM SERIALIZED FILE");
        }
        return null;

    }

    /**
     * This method saves a user to the Map and writes the Map to the file
     *
     * @param user,     the User object to add
     * @param username, a String representing the username of the user to add
     */

    void saveUser(User user, String username) {
        Map<String, User> HMfromfile = readObject();
        HMfromfile.put(username, user);
        saveObject(HMfromfile);
    }

    /**
     * Returns the user object corresponding to the username passed in
     *
     * @param username the username of the desired user object
     * @return the User corresponding to the username String
     */
    User getUser(String username) {
        Map<String, User> users = readObject();
        assert users != null;
        return users.get(username);
    }

    /**
     * Returns the stack of the user for the specified game
     *
     * @param username  the username of the user
     * @param gameIndex the identity of the game, for which the
     *                  stack is desired
     * @return the stack corresponding to the user and the
     * specified game
     */
    Stack<Board> getStack(String username, int gameIndex) {
        return getUser(username).getGameStack(gameIndex);
    }

    /**
     * Saves the given hashmap to the serialized file, thus saving
     * the users and their games
     *
     * @param map the hashmap to be saved onto the .ser file
     */
    void saveObject(Map<String, User> map) {
        FileOutputStream fos;
        ObjectOutputStream objectOut;

        try {
            fos = GlobalApplication.getAppContext().openFileOutput("testFile2.ser", Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fos);
            objectOut.writeObject(map);
            objectOut.close();

        } catch (NullPointerException e2) {
            e2.printStackTrace();
            System.out.println("CONTEXT IS NULL WHILE SAVING");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            System.out.println("FILE NOT FOUND WHILE SAVING TO SERIALIZED FILE");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO EXCEPTION WHILE SAVING TO SERIALIZED FILE");
        }

    }
}
