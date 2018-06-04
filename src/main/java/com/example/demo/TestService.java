package com.example.demo;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * This looks like a description, but is none.
 *
 * @author Konrad Lötzsch
 */
@Component
public class TestService
{

    private final TestObjectRepository repo;
    private final ReactiveMongoTemplate mongoTemplate;

    public TestService(
        TestObjectRepository repo,
        ReactiveMongoTemplate mongoTemplate
    )
    {
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }

    public void doReactiveStuff()
    {
        TestObject object = new TestObject("va", "vb");
        repo.insert(object).subscribe();
        object = new TestObject("vc", "vd");
        repo.insert(object).subscribe();
        object = new TestObject("ve", "vf");
        repo.insert(object).subscribe();
        repo.findAll().flatMap(testObject -> this.updateObject(testObject)).subscribe();
    }

    private Mono<TestObject> updateObject(TestObject testObject)
    {
        TestObject newObject = new TestObject(testObject.getA(), "new");
        newObject.setId(testObject.getId());
        mongoTemplate.updateFirst(
            new Query().addCriteria(Criteria.where("_id").is(testObject.getId())),
            new Update().set("b", "b"),
            TestObject.class
        ).subscribe();
        mongoTemplate.updateFirst(
            new Query().addCriteria(Criteria.where("_id").is(testObject.getId())),
            new Update().set("a", "a"),
            TestObject.class
        ).subscribe();
        //this creates a replace in the change stream, will be filtered out.
        return repo.save(newObject);

    }

}
