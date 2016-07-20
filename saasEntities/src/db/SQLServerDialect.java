/**
 * 
 */
package db;

import java.sql.Types;

import org.hibernate.Hibernate;

/**
 * @author tooooolazy
 * 
 */
public class SQLServerDialect extends org.hibernate.dialect.SQLServerDialect {
	/**
	 * Initializes a new instance of the {@link SQLServerDialect} class.
	 */
	public SQLServerDialect() {
		super();
		registerHibernateType( Types.NVARCHAR, String.class.getName() );
		registerColumnType(Types.BIGINT, "bigint");
		registerColumnType(Types.BIT, "bit");
		registerColumnType(Types.CHAR, "nchar(1)");
		registerColumnType(Types.VARCHAR, 4000, "nvarchar($l)");
		registerColumnType(Types.VARCHAR, "nvarchar(max)");
		registerColumnType(Types.VARBINARY, 4000, "varbinary($1)");
		registerColumnType(Types.VARBINARY, "varbinary(max)");
		registerColumnType(Types.BLOB, "varbinary(max)");
		registerColumnType(Types.CLOB, "nvarchar(max)");
		registerColumnType(Types.NVARCHAR, 8000, "nvarchar($1)");
		registerColumnType(Types.NVARCHAR, "nvarchar(255)");
	}

}
