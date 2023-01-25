import org.neo4j.driver.v1.*;
import java.util.Random;

public class Neo4jExample {
    String name = generateRandomName();
    String query = "CREATE (c:Company {id: " + id + ", name: \"" + name + "\"})";
    session.run(query);
    
    public static void main(String[] args) {
        // Connect to Neo4j database
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        Session session = driver.session();

        // Create random number generator
        Random rand = new Random();

        // Create companies
        for (int i = 0; i < 125; i++) {
            int id = i + 1;
            String name = "Company " + id;
            String query = "CREATE (c:Company {id: " + id + ", name: \"" + name + "\"})";
            session.run(query);
        }

        // Create humans
        for (int i = 0; i < 125; i++) {
            int id = i + 1;
            String name = "Human " + id;
            String query = "CREATE (h:Human {id: " + id + ", name: \"" + name + "\"})";
            session.run(query);
        }

        // Create houses
        for (int i = 0; i < 125; i++) {
            int id = i + 1;
            String[] types = {"full_house", "apartments", "condos", "town_house"};
            String type = types[rand.nextInt(types.length)];
            String query = "CREATE (h:House {id: " + id + ", type: \"" + type + "\"})";
            session.run(query);
        }

        // Create places
        for (int i = 0; i < 125; i++) {
            int id = i + 1;
            String name = "Place " + id;
            String query = "CREATE (p:Place {id: " + id + ", name: \"" + name + "\"})";
            session.run(query);
        }

        // Create relationships
        for (int i = 0; i < 125; i++) {
            // Humans -[work_for]-> company
            int salary = rand.nextInt(10000);
            String query = "MATCH (h:Human),(c:Company) WHERE h.id = " + (i + 1) + " AND c.id = " + (i + 1) + " CREATE (h)-[:WORK_FOR {salary: " + salary + "}]->(c)";
            session.run(query);

                        // Humans -[bought]-> house
            int price = rand.nextInt(400000) + 100000;
            query = "MATCH (h:Human),(ho:House) WHERE h.id = " + (i + 1) + " AND ho.id = " + (i + 1) + " CREATE (h)-[:BOUGHT {price: " + price + "}]->(ho)";
            session.run(query);

            // Companies -[owns]-> house
            query = "MATCH (c:Company),(ho:House) WHERE c.id = " + (i + 1) + " AND ho.id = " + (i + 1) + " CREATE (c)-[:OWNS]->(ho)";
            session.run(query);

            // Companies -[based_at]-> place
            query = "MATCH (c:Company),(p:Place) WHERE c.id = " + (i + 1) + " AND p.id = " + (i + 1) + " CREATE (c)-[:BASED_AT]->(p)";
            session.run(query);

            // Houses -[located_at]-> place
            query = "MATCH (ho:House),(p:Place) WHERE ho.id = " + (i + 1) + " AND p.id = " + (i + 1) + " CREATE (ho)-[:LOCATED_AT]->(p)";
            session.run(query);
        }

        // Close session and driver
        session.close();
        driver.close();
    }
}

