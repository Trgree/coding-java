import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L
 * @date 2018/3/7
 */
public class TestMongo {
    public static void main( String args[] ){
        try{
            ServerAddress serverAddress = new ServerAddress("dds-bp15b5e61a284f341651-pub.mongodb.rds.aliyuncs.com",3717);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            MongoCredential credential = MongoCredential.createScramSha1Credential("root", "admin", "Water2017".toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);



            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( addrs , credentials );
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb");
            System.out.println("Connect to database successfully"+ mongoDatabase);

            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            System.out.println("集合 test 选择成功");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
