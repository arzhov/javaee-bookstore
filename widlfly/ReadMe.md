# Wildfly config

###  Copy mysql folder into  $WILDFLYHOME/modules/system/layers/base/com/ 

### Edit/Add $WILDFLY_HOME/standalone/configuration/standalone.xml file

#### 1. Add datasource
        
        <datasource jta="true" jndi-name="java:jboss/datasources/bookstore-ds" pool-name="bookstore" enabled="true" use-java-context="true" use-ccm="true">
            <connection-url>jdbc:mysql://localhost:33060/bookstore</connection-url>
            <driver>mysql</driver>
            <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
            <pool>
                <min-pool-size>10</min-pool-size>
                <max-pool-size>100</max-pool-size>
                <prefill>true</prefill>
            </pool>
            <security>
                <user-name>root</user-name>
                <password>123456</password>
            </security>
            <statement>
                <prepared-statement-cache-size>32</prepared-statement-cache-size>
                <share-prepared-statements>true</share-prepared-statements>
            </statement>
        </datasource>
        
#### 2. add in drivers
   
    <driver name="mysql" module="com.mysql">
       <xa-datasource-class>com.mysql.cj.jdbc.MysqlXADataSource</xa-datasource-class>
    </driver>
    
### Dont forget to inform to your persistence.xml the datasource name
    <jta-data-source>java:jboss/datasources/social_network-ds</jta-data-source>