package searchengine;

/**
 * Created by Denislav on 4/19/2019.
 */
public class DebugHelper {

    //Show debugging logs?
    private static boolean isDebugging = true;

    //Method to show debugging log if 'isDebugging'

    /**
     * The method that prints the object to String if 'isDebugging' is set to true
     * @param o Object
     */
    public static void log(Object o){
        if(DebugHelper.isDebugging) {
            System.out.println(o.toString());
        }
    }

}
