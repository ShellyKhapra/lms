package com.lms.dal.entities;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {
    @Version
    private long version;
}
