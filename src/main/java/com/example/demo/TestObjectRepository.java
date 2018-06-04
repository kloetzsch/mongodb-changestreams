package com.example.demo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * This looks like a description, but is none.
 *
 * @author Konrad Lötzsch
 */
public interface TestObjectRepository extends ReactiveMongoRepository<TestObject, ObjectId>
{

}
