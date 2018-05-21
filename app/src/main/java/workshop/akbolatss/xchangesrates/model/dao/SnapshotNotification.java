package workshop.akbolatss.xchangesrates.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "SNAPSHOT_NOTIFICATION".
 */
@Entity
public class SnapshotNotification {

    @Id(autoincrement = true)
    private Long id;
    private Boolean isActive;
    private String name;
    private Integer hour;
    private Integer minutes;
    private Integer ConditionIndex;
    private Integer ConditionValue;
    private Boolean isVibrateOn;
    private Boolean isSoundOn;
    private Boolean isLedOn;
    private long snapshotId;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated(hash = 1275427807)
    public SnapshotNotification() {
    }

    public SnapshotNotification(Long id) {
        this.id = id;
    }

    @Generated(hash = 1254476042)
    public SnapshotNotification(Long id, Boolean isActive, String name, Integer hour, Integer minutes, Integer ConditionIndex, Integer ConditionValue, Boolean isVibrateOn, Boolean isSoundOn, Boolean isLedOn, long snapshotId) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.hour = hour;
        this.minutes = minutes;
        this.ConditionIndex = ConditionIndex;
        this.ConditionValue = ConditionValue;
        this.isVibrateOn = isVibrateOn;
        this.isSoundOn = isSoundOn;
        this.isLedOn = isLedOn;
        this.snapshotId = snapshotId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getConditionIndex() {
        return ConditionIndex;
    }

    public void setConditionIndex(Integer ConditionIndex) {
        this.ConditionIndex = ConditionIndex;
    }

    public Integer getConditionValue() {
        return ConditionValue;
    }

    public void setConditionValue(Integer ConditionValue) {
        this.ConditionValue = ConditionValue;
    }

    public Boolean getIsVibrateOn() {
        return isVibrateOn;
    }

    public void setIsVibrateOn(Boolean isVibrateOn) {
        this.isVibrateOn = isVibrateOn;
    }

    public Boolean getIsSoundOn() {
        return isSoundOn;
    }

    public void setIsSoundOn(Boolean isSoundOn) {
        this.isSoundOn = isSoundOn;
    }

    public Boolean getIsLedOn() {
        return isLedOn;
    }

    public void setIsLedOn(Boolean isLedOn) {
        this.isLedOn = isLedOn;
    }

    public long getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(long snapshotId) {
        this.snapshotId = snapshotId;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
