static void main(String[] args) {
    String path2File = "C:\\Users\\Andrew\\Downloads";
    String fileName = "3328496339_40702810010000001390_002_0519"
    String fileExtension = "txt";

    String mongoURL = "mongodb://localhost:27017";
    String dbName = "base";
    String collectionName = "bils";

    String postgreeURL = "jdbc:postgresql://localhost:5432";
    String usernameSQL = "postgres";
    String passwordSQL = "root";

    Time time = new Time();

    WorkingWithFiles wwf = new WorkingWithFiles(path2File + "\\" + fileName + "." + fileExtension);
    wwf.readFile();
    println("Данные с файла считаны")

    ConverterMongoDB convertMongo = new ConverterMongoDB();

    MongoDB mongo = new MongoDB(mongoURL, dbName, collectionName);
    mongo.connect();
    println("Система подключена к БД Mongo");

    mongo.dropCollection()
    println("Старая Коллекция удалена");

    println("Началось парсирование в БД Mongo")
    time.startTime()
    wwf.getArrayList().each {
        mongo.insert(
                convertMongo.getDocument(it))
    }
    time.finishTime()
    println("Выполнен перенос, время затронутое для парсинга в БД Mongo = " + time.getLeadTime() / 1000000 + " миллисекунд")


    PostgreDB postgreDB = new PostgreDB(postgreeURL, usernameSQL, passwordSQL, "test1", collectionName)
    println("Система подключена к БД Postgre");

    println("Началось парсирование в БД Postgre")
    postgreDB.dropTable()
    postgreDB.createTable()

    time.startTime()
    postgreDB.insert(wwf.getArrayList())
    time.finishTime()
    println("Выполнен перенос, время затронутое для парсинга в БД Postgree = " + time.getLeadTime() / 1000000 + " миллисекунд")
}
