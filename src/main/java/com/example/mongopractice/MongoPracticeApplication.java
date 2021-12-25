package com.example.mongopractice;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import com.example.mongopractice.model.Child;
import com.example.mongopractice.model.Person;
import com.mongodb.client.MongoClients;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.query.Query;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MongoPracticeApplication {
	private static final Log log = LogFactory.getLog(MongoPracticeApplication.class);
	public static void main(String[] args) throws Exception{
//		SpringApplication.run(MongopracticeApplication.class, args);
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "database"));
		mongoOps.dropCollection(Person.class);
		Child s = new Child("10","sam",5);
		Child h = new Child("20","Haam",4);
		Child k = new Child("30","Ken",6);
		Child a = new Child("40","Adn",7);
		Person m = new Person("1","Mano",33,20000.00);
		List<Child> mKids = new ArrayList<Child>();
		mKids.add(s);
		mKids.add(k);
		mKids.add(a);
		mKids.add(h);
		m.setChildren(mKids);
		Person ab = new Person("2","Ali", 25,28000.00);
//		insert first person
		mongoOps.insert(m);
		log.info("Insert: "+m);

//      insert second person
		mongoOps.insert(ab);
		log.info("Insert: "+ab);

//		find by id
		m=mongoOps.findById(m.getId(),Person.class);
		log.info("Found: "+m);

//		update mansoor's age
		mongoOps.updateFirst(query(where("name").is("Mano")), update("age",32),Person.class);
		m=mongoOps.findOne(query(where("name").is("Mansoor")),Person.class);
		log.info("Updated: "+m);

		List<Person> people=mongoOps.findAll(Person.class);
		System.out.println("People are initially : " + people);

//		delete
		mongoOps.remove(ab);
		List<Person> people2=mongoOps.findAll(Person.class);
		System.out.println("People are now : " + people2);

		log.info(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

		// upserting a person that in not on db
		mongoOps.update(Person.class)
				.matching(query(where("id").is("3").and("name").is("Tom").and("age").is(50)))
				.apply(update("assets",15000.00))
				.upsert();

//		adding a child for Mano
		Person temp = mongoOps.findOne(query(where("id").is("1")),Person.class);
		List<Child> lc = temp.getChildren();
		lc.add(new Child("70","mike",1));
		temp.setChildren(lc);
		mongoOps.save(temp);
	}

}
