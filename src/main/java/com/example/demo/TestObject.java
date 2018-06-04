package com.example.demo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This looks like a description, but is none.
 *
 * @author Konrad Lötzsch
 */
@Document
public class TestObject
{

    @Id
    private ObjectId id;
    private final String a;
    private final String b;

    public TestObject(String a, String b)
    {
        this.a = a;
        this.b = b;
    }

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getA()
    {
        return a;
    }

    public String getB()
    {
        return b;
    }

}
