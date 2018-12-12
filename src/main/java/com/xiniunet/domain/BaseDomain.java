package com.xiniunet.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseDomain implements Serializable {
    private static final long serialVersionUID = 1l;

    private Boolean isDeleted;
    private Long rowVersion;
    private Date creationTime;
    private Date lastUpdateTime;

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
