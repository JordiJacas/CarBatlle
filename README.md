En la ruta "src/resources/card_collection.xml" se encuentra el xml con las cartas creadas, hay que subir ese 
archivo al exitsDB.

Una vez subido el xml, en el archivo "src/config/AccesDBExist.java" modificar las variables URI y 
collectionPath para que se ajusten a la ruta donde se ubica el xml subido a exist.

En el archivo "src/config/AccesDBMongo.java" modificar las variable mongoClient, database y collection para
poder connectarse a la base de datos y coleccion de mongoDB

Estado actual de las variables

AccesDBExist.java
URI  = AccesDBMongo.java
collectionPath = /db/Catalog

AccesDBMongo.java
mongoClient = "localhost", 27017
database = DeckBatlles
collection = decks


