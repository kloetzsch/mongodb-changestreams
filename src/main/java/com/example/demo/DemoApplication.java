package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoApplication
{

    public static void main(String[] args) throws InterruptedException
    {
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
        ReactiveMongoTemplate mongoTemplate = ctx.getBean(ReactiveMongoTemplate.class);
        mongoTemplate = ctx.getBean(ReactiveMongoTemplate.class);
        //any change
        Flux<ChangeStreamEvent<TestObject>> changeStream = mongoTemplate.changeStream(
            newAggregation(match(where("operationType").exists(true))),
            TestObject.class,
            ChangeStreamOptions.empty(),
            "testObject"
        );
        //only changes on "a"
//        Flux<ChangeStreamEvent<TestObject>> changeStream = mongoTemplate.changeStream(
//            newAggregation(
//                match(
//                    where("operationType").is("update")
//                        .and("updateDescription.updatedFields.a").exists(true)
//                )
//            ),
//            TestObject.class,
//            ChangeStreamOptions.empty(),
//            "testObject"
//        );
        changeStream.subscribe(System.out::println);
        TestService testService = ctx.getBean(TestService.class);
        testService.doReactiveStuff();
    }
}
