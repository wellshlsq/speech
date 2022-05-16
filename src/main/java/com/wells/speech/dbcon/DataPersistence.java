package com.wells.speech.dbcon;

import com.yugabyte.largeobject.LargeObject;
import com.yugabyte.largeobject.LargeObjectManager;
//import org.postgresql.largeobject.LargeObject;
//import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.transaction.Transactional;

@Component
public class DataPersistence {

    @Autowired
    BasicConnectionPool connPool;

    public DataPersistence() {}
    @Transactional
    public void save(String userId, byte[] soundBytes) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            /*
            // Get the Large Object Manager to perform operations with
            //LargeObjectManager lobj = conn.unwrap( com.yugabyte.largeobject.LargeObjectManager.class);
            LargeObjectManager lobj = ((com.yugabyte.jdbc.PgConnection)conn).getLargeObjectAPI();

            // Create a new large object
            long oid = lobj.createLO(LargeObjectManager.WRITE);

            // Open the large object for writing
            LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);
            obj.write(soundBytes, 0, soundBytes.length);
            obj.close();
*/
            ps = conn.prepareStatement("UPDATE emp SET useCustomSound = true, nameSound = ? WHERE userId = ?");
            ps.setBytes(1, soundBytes);
            //ps.setLong(1, oid);
            ps.setString(2, userId);
            ps.executeUpdate();

            // Finally, commit the transaction.
            conn.commit();
        } finally {
            if(ps != null)
                ps.close();
            if(conn != null)
                connPool.releaseConnection(conn);
        }
    }

    @Transactional
    public byte[] load(String userId) throws SQLException {
        byte[] soundBytes = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = connPool.getConnection();

            /*
            // Get the Large Object Manager to perform operations with
            //LargeObjectManager lobj = conn.unwrap( com.yugabyte.largeobject.LargeObjectManager.class);
            LargeObjectManager lobj = ((com.yugabyte.jdbc.PgConnection)conn).getLargeObjectAPI();

            // Create a new large object
            long oid = lobj.createLO(LargeObjectManager.WRITE);

            // Open the large object for writing
            LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);
            obj.write(soundBytes, 0, soundBytes.length);
            obj.close();
*/
            ps = conn.prepareStatement("SELECT nameSound FROM emp WHERE userId = ?");
            //ps.setLong(1, oid);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                soundBytes = rs.getBytes(1);
            }
        } finally {
            if(ps != null)
                ps.close();
            if(conn != null)
                connPool.releaseConnection(conn);
        }
        return soundBytes;
    }
}
