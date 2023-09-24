static void main(String[] args) {
    String path2File = "E:\\на диск Д\\VLSU\\Учеба\\2023-2024\\ТРВП\\Progect";
    String fileName = "3328496339_40702810010000001390_002_0519"
    String fileExtension = "txt";
    String connectString = "mongodb://localhost:27017";
    String dbName = "base";
    String collectionName = "bils";

    Time time = new Time();


    ConverterMongoDB convertMongo = new ConverterMongoDB();

    MongoDB mongo = new MongoDB(connectString, dbName, collectionName);
    mongo.connect();

    mongo.dropCollection()
    println("Старая Коллекция удалена");

    println("Система подключена к БД");


    WorkingWithFiles wwf = new WorkingWithFiles(path2File + "\\" + fileName + "." + fileExtension);
    wwf.readFile();

    println("Началось парсирование в БД Mongo")
    time.startTime()
    wwf.getArrayList().each {
        mongo.insert(
                convertMongo.getDocument(it))
    }
    time.finishTime()
    println("Выполнен перенос, время затронутое для парсинга в БД Mongo = " + time.getLeadTime() / 1000000 + " cек.")



    String postgreeURL = "jdbc:postgresql://localhost:5432";
    String usernameSQL = "postgres";
    String passwordSQL = "root";

    PostgreDB postgreDB = new PostgreDB(postgreeURL, usernameSQL, passwordSQL, "test1", collectionName)

    println("Началось парсирование в БД Postgre")
    time.startTime()
    postgreDB.createTable()
    time.finishTime()
    println("Выполнен перенос, время затронутое для парсинга в БД Postgree = " + time.getLeadTime() / 1000000 + " cек.")
    //   if (postgreDB.connect()) println("Подключение к БД произедено успешно!")
    // else println("Подключение к БД провалено!")
//TODO перезаписать файл, если присутствует символ ' перезаписать на символ " и уже работать с данным файлом, по окончанию работы файл можно
// улалить (при необходимости)

    postgreDB.insert(wwf.getArrayList())
    //wwf.getArrayList().each {
    //    postgreDB.insert(it)
    // }

    /* try (Connection connection = DriverManager.getConnection(
             urlSQL, usernameSQL, passwordSQL
     )) {
         if (connection != null) println("Connected to the databases!")
         else println("Failed to make connection!")
         String table = "CREATE TABLE " + dbName + " (first_name VARCHAR(255) NOT NULL," +
                 " last_name VARCHAR(255), email VARCHAR(100))";

         String sql = "INSERT INTO "+dbName+" (first_name, last_name, email)" +
                 " VALUES ('Ravi', 'Kumar','ravi.kumar2020@gmail.com')";
         Statement statement = connection.createStatement();
         int rows = statement.executeUpdate(table);
         statement.executeUpdate(sql);
         if (rows > 0) {
             println("A new contact has been inserted");
         }
         connection.close();
     }
     catch (SQLException e) {
         println("SQL State: %s\n%s", e.getSQLState(), e.getMessage())
     }
     catch (Exception e) {
         e.printStackTrace()
     }*/


}
