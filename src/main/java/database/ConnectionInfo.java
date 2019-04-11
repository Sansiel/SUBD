package database;

import model.Sportsman;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class ConnectionInfo {
    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;
    private SessionFactory sessionFactory;

    public ConnectionInfo(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getConnectionURL() {
        return String.format("jdbc:postgresql://%s:%s/%s", this.getHost(), this.getPort(), this.getDatabase());
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.url", this.getConnectionURL())
                .setProperty("hibernate.connection.username", this.getUser())
                .setProperty("hibernate.connection.password", this.getPassword());
        configuration.configure();
        return configuration;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(Sportsman.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("postgresql://");
        if (!this.getUser().isEmpty()) {
            stringBuilder.append(this.getUser());
        }
        if (!this.getPassword().isEmpty()) {
            stringBuilder.append(":").append(this.getPassword());
        }
        if (!this.getUser().isEmpty() || !this.getPassword().isEmpty()) {
            stringBuilder.append("@");
        }
        stringBuilder.append(this.getHost()).append(":").append(this.getPort()).append("/").append(this.getDatabase());
        return stringBuilder.toString();
    }
}
