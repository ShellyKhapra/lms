package com.lms.datasource;


import java.net.URI;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

public class Configuration {
    private final Properties properties;
    private final String key;

    private Configuration(Properties properties, String key) {
        this.properties = new Properties();
        if (properties != null) {
            this.properties.putAll(properties);
        }

        this.key = key;
    }

    public Properties getProperties() {
        Properties result = new Properties();
        result.putAll(this.properties);
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Configuration that = (Configuration)o;
            return this.key.equals(that.key);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public interface SetPassword {
        Configuration.Builder setPassword(String var1);
    }

    public interface SetUser {
        Configuration.SetPassword setUser(String var1);
    }

    public interface SetJdbcUrl {
        Configuration.SetUser setJdbcUrl(String var1);

        Configuration.SetUser setJdbcUrl(String var1, String var2, String var3);
    }

    public static class Builder implements Configuration.SetJdbcUrl, Configuration.SetUser, Configuration.SetPassword {
        private final Properties properties = new Properties();
        private boolean isSchemaAllowed = true;

        private Builder() {
        }

        public static Configuration.SetJdbcUrl getInstance() {
            return new Configuration.Builder();
        }

        public Configuration.SetUser setJdbcUrl(String jdbcUrl) {
//            this.setCatalogAndSchema(jdbcUrl);
            this.properties.put("jdbcUrl", jdbcUrl);
            this.properties.put("driverClassName","com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return this;
        }

        public Configuration.SetUser setJdbcUrl(String host, String port, String catalog) {
            if (host != null && !host.trim().isEmpty()) {
                if (catalog != null && !catalog.trim().isEmpty()) {
                    StringBuilder sb = (new StringBuilder("jdbc:sqlserver://")).append(host);
                    if (port != null && !port.isEmpty()) {
                        sb.append(':').append(port);
                    }

                    sb.append("/").append(catalog);
                    this.properties.put("jdbcUrl", sb.toString());
                    this.properties.put("catalog", catalog);
                    return this;
                } else {
                    throw new IllegalArgumentException("Database name (catalog) must be provided for the JDBC connection");
                }
            } else {
                throw new IllegalArgumentException("Host must be provided for the JDBC connection");
            }
        }

        public String getCatalog() {
            return this.properties.getProperty("catalog");
        }

        public String getSchema() {
            return this.properties.getProperty("schema");
        }

        public Configuration.Builder setSchema(String schema) {
            if (!this.isSchemaAllowed) {
                throw new IllegalStateException("JDBC URL contains schema setting");
            } else {
                this.properties.put("schema", schema);
                return this;
            }
        }

        public String getUser() {
            return this.properties.getProperty("dataSource.user");
        }

        public Configuration.SetPassword setUser(String user) {
            this.properties.setProperty("dataSource.user", user);
            return this;
        }

        public String getPassword() {
            return this.properties.getProperty("dataSource.password");
        }

        public Configuration.Builder setPassword(String password) {
            this.properties.setProperty("dataSource.password", password);
            return this;
        }

        public Properties getProperties() {
            Properties result = new Properties();
            result.putAll(this.properties);
            return result;
        }

        public Configuration.Builder setProperties(Properties properties) {
            if (properties != null) {
                Iterator var2 = properties.entrySet().iterator();

                while(var2.hasNext()) {
                    Entry<Object, Object> key = (Entry)var2.next();
                    this.updateIfPossible(key.getKey(), key.getValue());
                }
            }

            return this;
        }

        public Configuration.Builder addProperty(String property, String value) {
            this.updateIfPossible(property, value);
            return this;
        }

        public Configuration build() {
            if (this.properties.getProperty("schema") == null) {
                this.properties.setProperty("schema", "public");
            }

            String poolName = this.properties.getProperty("poolName");
            if (poolName == null) {
                this.properties.setProperty("poolName", String.format("%s.%s", this.properties.getProperty("catalog"), this.properties.getProperty("schema")));
            }

            URI jdbcUri = URI.create(this.properties.getProperty("jdbcUrl").substring(5));
            String key = String.format("%s:%s:%s:%s", jdbcUri.getHost(), jdbcUri.getPort(), this.properties.getProperty("catalog"), this.properties.getProperty("schema"));
            return new Configuration(this.properties, key);
        }

        private void updateIfPossible(Object key, Object value) {
            if ("jdbcUrl".equals(key) || "catalog".equals(key) || !this.isSchemaAllowed && "schema".equals(key)) {
                throw new IllegalArgumentException(String.format("The following property cannot be set directly: %s", key));
            } else {
                this.properties.put(key, value);
            }
        }

        private void setCatalogAndSchema(String jdbcUrl) {
            if (jdbcUrl != null && !jdbcUrl.trim().isEmpty() && jdbcUrl.startsWith("jdbc:")) {
                URI jdbcUri = URI.create(jdbcUrl.substring(5));
                if (jdbcUri.getHost() == null) {
                    throw new IllegalArgumentException("Host is not specified");
                } else if (jdbcUri.getPath() != null && !jdbcUri.getPath().trim().isEmpty() && !jdbcUri.getPath().equals("/")) {
                    this.properties.setProperty("catalog", jdbcUri.getPath().substring(1));
                    String query = jdbcUri.getQuery();
                    if (query != null) {
                        String[] var4 = query.split("&");
                        int var5 = var4.length;

                        for(int var6 = 0; var6 < var5; ++var6) {
                            String parameter = var4[var6];
                            if (parameter.startsWith("currentSchema")) {
                                this.isSchemaAllowed = false;
                                this.properties.setProperty("schema", parameter.substring(parameter.indexOf("=") + 1));
                            }
                        }
                    }

                } else {
                    throw new IllegalArgumentException("Database name (catalog) is not specified");
                }
            } else {
                throw new IllegalArgumentException("jdbcUrl cannot be null or empty and must start with `jdbc:`");
            }
        }
    }
}

