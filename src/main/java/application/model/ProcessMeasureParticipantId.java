package application.model;

import java.io.Serializable;

/**
 * Created by alexiaborchgrevink on 5/17/18.
 */
public class ProcessMeasureParticipantId implements Serializable {

    private long indicatorFk;
    private String workProcessFk;
    private String processMeasureFk;
    private String userFk;


    public long getIndicatorFk() {
        return indicatorFk;
    }

    public void setIndicatorFk(long indicatorFk) {
        this.indicatorFk = indicatorFk;
    }


    public String getWorkProcessFk() {
        return workProcessFk;
    }

    public void setWorkProcessFk(String workProcessFk) {
        this.workProcessFk = workProcessFk;
    }


    public String getProcessMeasureFk() {
        return processMeasureFk;
    }

    public void setProcessMeasureFk(String processMeasureFk) {
        this.processMeasureFk = processMeasureFk;
    }


    public String getUserFk() {
        return userFk;
    }

    public void setUserFk(String userFk) {
        this.userFk = userFk;
    }
}
