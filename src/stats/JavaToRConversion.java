package stats;

import com.github.rcaller.rstuff.*;
import edu.ithaca.dragonlab.ckc.learningobject.LearningObject;

import java.util.List;

/**
 * This class creates an R readable 2D Array from a 2D array from Java
 * Created by bleblanc2 on 6/16/17.
 */
public class JavaToRConversion {


    /**
     * Takes a 2D array of doubles and sends it to R in the form of RCode.
     * The commented code is pieces that may be useful in adding column and row
     * headers by name to the R matrix. The headers are currently indices
     * which is provided by the provideDimnames command in the RCode.
     * @param aMatrix the 2D array of doubles from Java
     * @return RCode of a 2D array in the same format as the
     */
    public static RCode JavaToR(double[][] aMatrix){
        /**
        double[][] aMatrix = loMatrix.getStudentKnowledgeEstimates();
        String[] users = loMatrix.getUserIdList();
        ArrayList<LearningObject> learningObjects = loMatrix.getObjList();
        int objLength = learningObjects.size();

        //object list into string array

        int i = 0;
        for(LearningObject obj: learningObjects){
            objStr[i] = obj.toString();
            i++;
        }
*/

        RCode code = RCode.create();
        code.addDoubleMatrix("data", aMatrix);

        /**
        code.addStringArray("headers", objStr);
        code.addStringArray("users", users);
        int userlength = users.length;
        int objlength = objLength;
        code.addInt("usercount", userlength);
        code.addInt("objcount", objlength);
        */


        //These lines change the Java structure into the correct format for R
        code.addRCode("matrix <- (t(data))");
        code.addRCode("provideDimnames(matrix)");

        return code;

    }
}
