package autocadDrawingChecker.data.surveyData;

import autocadDrawingChecker.data.core.Record;

/**
 *
 * @author Matt
 */
public class SurveyDataRecord extends Record {
    public static final String POINT_ID_HEADER = "POINT";
    public static final String X_HEADER = "X.*";
    public static final String Y_HEADER = "Y.*";
    public static final String Z_HEADER = "Z.*";
    
    public static final String[] REQ_COLS = new String[]{
        POINT_ID_HEADER,
        X_HEADER,
        Y_HEADER,
        Z_HEADER
    };
    SurveyDataRecord(){
        super();
    }
    
    public final String getPointId(){
        return getAttributeString(POINT_ID_HEADER);
    }
    
    public final Double getX(){
        return getAttributeDouble(X_HEADER);
    }
    
    public final Double getY(){
        return getAttributeDouble(Y_HEADER);
    }
    
    public final Double getZ(){
        return getAttributeDouble(Z_HEADER);
    }
    
    @Override
    public String toString(){
        return String.format("Survey Point %s (%f, %f, %f)", getPointId(), getX(), getY(), getZ());
    }
}
