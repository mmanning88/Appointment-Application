
package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*
@author Matthew Manning
*/
public class DBQuery {
    
    private static PreparedStatement statement; // Statement reference

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
    
}
