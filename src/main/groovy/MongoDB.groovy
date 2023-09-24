import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.bson.conversions.Bson

class MongoDB {
    private connectString;
    private dbName;
    private collectionName;
    private collection;
    private db;

    void setDb(db) {
        this.db = db
    }

    def getCollectionName() {
        return collectionName
    }

    def getDb() {
        return db
    }

    void setCollection(collection) {
        this.collection = collection
    }

    void setCollectionName(collectionName) {
        this.collectionName = collectionName
    }

    MongoDB(connectString, dbName, collectionName) {
        this.connectString = connectString
        this.dbName = dbName
        this.collectionName = collectionName
    }

    MongoCollection<Document> connect() {
        MongoClient mongoClient = MongoClients.create(connectString);
        MongoDatabase db = mongoClient.getDatabase(dbName);
        setCollection(db.getCollection(collectionName));
        setDb(db);
    }

    void changeCollection(String collectionName) {
        setCollectionName(collectionName);
        setCollection(db.getCollection(collectionName))
    }

    void insert(Document newDocument) {
        collection.insertOne(newDocument);
    }

    void update(Document updateDocument, Bson updates) {
        collection.updateOne(updateDocument, updates,
                new UpdateOptions().upsert(true));
    }

    void printCollection() {
        for (Document doc : collection.find()) {
            println();
            printToJson(doc);
        }
    }

    private printToJson(Document document) {
        print(document.toJson());
    }

    void dropCollection() { collection.drop(); }

    void dropDB() { dbName.dropDatabase(); }
}
