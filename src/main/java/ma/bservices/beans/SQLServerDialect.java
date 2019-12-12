package ma.bservices.beans;

import java.sql.Types;

import org.hibernate.type.StandardBasicTypes;

public class SQLServerDialect extends org.hibernate.dialect.SQLServerDialect {

    /**
     * Initializes a new instance of the {@link SQLServerDialect} class.
     */
    public SQLServerDialect() {
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
    }

}
