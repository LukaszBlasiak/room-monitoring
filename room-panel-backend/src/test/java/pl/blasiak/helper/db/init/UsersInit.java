package pl.blasiak.helper.db.init;

import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Sql(scripts = "classpath:/db/sql/truncate_all_tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/db/sql/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public @interface UsersInit {
}
