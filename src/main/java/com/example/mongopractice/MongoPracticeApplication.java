package com.example.mongopractice;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

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

import java.util.List;

@SpringBootApplication
public class MongopracticeApplication {
	private static final Log log = LogFactory.getLog(MongopracticeApplication.class);
	public static void main(String[] args) throws Exception{
//		SpringApplication.run(MongopracticeApplication.class, args);
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "database"));
		Person m = new Person("Mansoor", 33);
		Person a = new Person("abdo",30);

//		insert first person
		mongoOps.insert(m);
		log.info("Insert: "+m);

//      insert second person
		mongoOps.insert(a);
		log.info("Insert: "+a);

//		find by id
		m=mongoOps.findById(m.getId(),Person.class);
		log.info("Found: "+m);

//		update
		mongoOps.updateFirst(query(where("name").is("Mansoor")), update("age",37),Person.class);
		m=mongoOps.findOne(query(where("name").is("Mansoor")),Person.class);
		log.info("Updated: "+m);

		List<Person> people=mongoOps.findAll(Person.class);
		System.out.println("People are initially : " + people);

//		delete
		mongoOps.remove(a);
		List<Person> people2=mongoOps.findAll(Person.class);
		System.out.println("People are now : " + people2);

		log.info(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));


	}

}
