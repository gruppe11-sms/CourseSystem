package dk.group11.coursesystem

import dk.group11.coursesystem.clients.RoleClient
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.jdbc.datasource.AbstractDataSource
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource


@Component
class Startup(private val roleClient: RoleClient) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        roleClient.ensureRole(COURSE_MANAGER_ROLE, "Course Manager", "Allows the user to create, edit and delete courses")
    }


}

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class RetryableDataSourceBeanPostProcessor : BeanPostProcessor {
    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        var bean = bean
        if (bean is DataSource) {
            bean = RetryableDataSource(bean)
        }
        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        return bean
    }
}

open internal class RetryableDataSource(private val delegate: DataSource) : AbstractDataSource() {

    @Retryable(maxAttempts = 10, backoff = Backoff(multiplier = 2.3, maxDelay = 30000))
    @Throws(SQLException::class)
    override fun getConnection(): Connection {
        return delegate.connection
    }

    @Retryable(maxAttempts = 10, backoff = Backoff(multiplier = 2.3, maxDelay = 30000))
    @Throws(SQLException::class)
    override fun getConnection(username: String, password: String): Connection {
        return delegate.getConnection(username, password)
    }

}