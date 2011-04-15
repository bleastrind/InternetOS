package cn.org.act.internetos.persist.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.IAppDAO;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SuperColumnQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

import org.apache.cassandra.thrift.Cassandra;  
import org.apache.cassandra.thrift.Column;  
import org.apache.cassandra.thrift.ColumnPath;  
import org.apache.cassandra.thrift.ConsistencyLevel;  
import org.apache.cassandra.thrift.InvalidRequestException;  
import org.apache.cassandra.thrift.NotFoundException;  
import org.apache.cassandra.thrift.TimedOutException;  
import org.apache.cassandra.thrift.UnavailableException;  
import org.apache.cassandra.utils.UUIDGen;
import org.apache.thrift.TException;  
import org.apache.thrift.protocol.TBinaryProtocol;  
import org.apache.thrift.transport.TSocket;  
import org.apache.thrift.transport.TTransport;  
import org.apache.thrift.transport.TTransportException;  

public class AppCassandraDAO implements IAppDAO{

	final static String KEYSPACE = "Keyspace1";
	final static String CF = "Applications";
	
	
	Keyspace keyspace;
	StringSerializer stringSerializer = new StringSerializer();
	public AppCassandraDAO(String server){
		Cluster cluster = HFactory.getOrCreateCluster("TestCluster",
			    new CassandraHostConfigurator(server));
		keyspace = HFactory.createKeyspace(KEYSPACE, cluster);
		
	}
	
	@Override
	public List<Application> getApps(String user) {
		ArrayList<Application> res= new ArrayList<Application>();
		
		
		SuperSliceQuery<String,String,String,String> query= HFactory.createSuperSliceQuery(keyspace, new StringSerializer(), new StringSerializer(), new StringSerializer(), new StringSerializer());
		String start="";
		String finish="";
		boolean reversed = false;
		int count =1000;
		query.setColumnFamily(CF).setKey(user)
		.setRange(start,finish, reversed, count);
		
		for(HSuperColumn<String, String, String> app: query.execute().get().getSuperColumns()){
			String config=null;
			String name=null;
			
			for(HColumn<String, String> attr: app.getColumns()){

				if( attr.getName().equals("name") )
					name = attr.getValue();
				else if( attr.getName().equals("config") )
					config = attr.getValue();
			}
			
			res.add(new Application(user,config));
		}
		
		return res;
	}

	@Override
	public String addApp(Application app) {
		Mutator<String> mutator = HFactory.createMutator(keyspace,stringSerializer);
		
		ArrayList<HColumn<String,String>> columns = new ArrayList<HColumn<String,String>>();
		
		columns.add(HFactory.createStringColumn("name", app.getName()));
		columns.add(HFactory.createStringColumn("config",app.getConfig()));
		String appID = UUID.randomUUID().toString();
		mutator.insert(app.getUser(), CF, HFactory.createSuperColumn(
				appID, columns, stringSerializer, stringSerializer, stringSerializer));
		return appID;
	}

}
