package com.lmt.data.unstructured.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author MT-Lin
 * @date 2018/1/1 23:27
 */
@MappedSuperclass
public class BaseEntity extends BaseToString implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid2" )
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
    @Column(name="id", nullable = false, length = 36)
    private String id;

    @Transient
    private String tokenId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
