//import org.apache.cassandra.thrift.Cassandra;  
//import org.apache.cassandra.thrift.Column;  
//import org.apache.cassandra.thrift.ColumnPath;  
//import org.apache.cassandra.thrift.ConsistencyLevel;  
//import org.apache.cassandra.thrift.InvalidRequestException;  
//import org.apache.cassandra.thrift.NotFoundException;  
//import org.apache.cassandra.thrift.TimedOutException;  
//import org.apache.cassandra.thrift.UnavailableException;  
//import org.apache.thrift.TException;  
//import org.apache.thrift.protocol.TBinaryProtocol;  
//import org.apache.thrift.transport.TSocket;  
//import org.apache.thrift.transport.TTransport;  
//import org.apache.thrift.transport.TTransportException;  
//public class CassandraTest {    
//	static Cassandra.Client cassandraClient;    
//    static TTransport socket;  
//    
//    public static void main(String[] args) throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException {    
//         /* 初始化连接 */    
//         init();    
//     
//     
//         /* 选择需要操作的Keyspaces， 可以理解成数据库的表 */    
//         String keyspace= "Keyspace1";    
//         String row = "employee";    
//     
//         /* 创建一个Table Name */    
//         String tableName = "Standard2";  
//           
//         /* 插入一条记录 */  
//         insertOrUpdate(keyspace,tableName,row,"name","happy birthday!",System.currentTimeMillis());  
//         /* 删除一条记录 */  
//         //delete(keyspace,tableName,row,"name",System.currentTimeMillis());  
//         /* 获取一条记录 (由于插入和删除是同一条记录,有可能会检索不到哦!请大家主意!*/  
//         Column column = getByColumn(keyspace,tableName,row,"name", System.currentTimeMillis()); 
//
//         System.out.println("read row " + row);    
//         System.out.println("column name " + ":" + column.name);    
//         System.out.println("column value" + ":" + column.value);    
//         System.out.println("column timestamp" + ":" + (column.timestamp));    
//           
//         close();  
//     }  
//	public static void init() throws TTransportException{  
//		String server = "localhost";  
//        int port = 9160;    
//		socket = new TSocket(server,port);
//		System.out.println(" connected to " + server + ":" + port + ".");    
//     
//     
//         /* 指定通信协议为二进制流协议 */    
//         TBinaryProtocol binaryProtocol = new TBinaryProtocol(socket, false, false);    
//         cassandraClient = new Cassandra.Client(binaryProtocol);    
//     
//     
//         /* 建立通信连接 */    
//         socket.open();    
//	}/** 
//      * 插入记录 
//      */  
//     public static void insertOrUpdate(String tableSpace,String tableName, String rowParam,String ColumnName,String ColumnValue,long timeStamp)    
//        throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException{  
//         /* 选择需要操作的Keyspaces， 存放数据表所在的空间位置 */    
//         String keyspace= tableSpace;  
//         /* 数据所在的行标 */  
//         String row = rowParam;    
//     
//         /* 创建一个column path */    
//         ColumnPath col = new ColumnPath(tableName);    
//         col.setColumn(ColumnName.getBytes());   
//           
//         /* 执行插入操作，指定keysapce, row, col, 和数据内容， 后面两个参数一个是timestamp， 另外一个是consistency_level  
//          * timestamp是用来做数据一致性保证的， 而consistency_level是用来控制数据分布的策略，前者的理论依据是bigtable, 后者的理论依据是dynamo  
//          */    
//        cassandraClient.insert(keyspace, row, col,"i don't know".getBytes(), System.currentTimeMillis(), ConsistencyLevel.ONE);  
//     }  
//       
//     /** 
//      * 删除记录 
//      */  
//     public static void delete(String tableSpace,String tableName, String rowParam,String ColumnName,long timeStamp)   
//        throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException{  
//         /* 选择需要操作的Keyspaces， 存放数据表所在的空间位置 */    
//         String keyspace= tableSpace;  
//         /* 数据所在的行标 */  
//         String row = rowParam;    
//     
//         /* 创建一个column path */    
//         ColumnPath col = new ColumnPath(tableName);    
//         col.setColumn(ColumnName.getBytes());   
//           
//         /* 执行删除操作，指定keysapce, row, col， 后面两个参数一个是timestamp， 另外一个是consistency_level  
//          * timestamp是用来做数据一致性保证的， 而consistency_level是用来控制数据分布的策略，前者的理论依据是bigtable, 后者的理论依据是dynamo  
//          */    
//        cassandraClient.remove(keyspace, row, col, System.currentTimeMillis(), ConsistencyLevel.ONE);  
//     }  
//       
//     /**  
//      * 获取数据  
//      */  
//     public static Column getByColumn(String tableSpace,String tableName, String rowParam,String ColumnName,long timeStamp)   
//    throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException{  
//      /* 选择需要操作的Keyspaces， 存放数据表所在的空间位置 */    
//      String keyspace= tableSpace;   
//      /* 数据所在的行标 */  
//      String row = rowParam;    
//  
//      /* 创建一个column path */    
//      ColumnPath col = new ColumnPath(tableName);    
//      col.setColumn(ColumnName.getBytes());   
//        
//      /* 执行查询操作，指定keysapce, row, col， timestamp  
//       * timestamp是用来做数据一致性保证的， 而consistency_level是用来控制数据分布的策略，前者的理论依据是bigtable, 后者的理论依据是dynamo  
//       */    
//      Column column = cassandraClient.get(keyspace, row, col, ConsistencyLevel.ONE).column;    
//      cassandraClient.get(new ByteBuffer(keyspace.getBytes(), col, ConsistencyLevel.ONE);
//      return column;  
//     }  
//       
//       
//     /** 
//      * 关闭当前的远程访问连接 
//      */  
//     public static void close() {  
//         socket.close();  
//    }  
//}
