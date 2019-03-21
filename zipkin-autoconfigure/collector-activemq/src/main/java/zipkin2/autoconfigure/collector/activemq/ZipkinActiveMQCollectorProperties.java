/*
 * Copyright 2015-2019 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin2.autoconfigure.collector.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import zipkin2.collector.activemq.ActiveMQCollector;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/** Properties for configuring and building a {@link zipkin2.collector.activemq.ActiveMQCollector}. */
@ConfigurationProperties("zipkin.collector.rabbitmq")
class ZipkinActiveMQCollectorProperties {
  static final URI EMPTY_URI = URI.create("");

  /** RabbitMQ server addresses in the form of a (comma-separated) list of host:port pairs */
  private String addresses;

  /** TCP connection timeout in milliseconds */
  private Integer connectionTimeout;
  /** RabbitMQ user password */
  private String password;
  /** RabbitMQ queue from which to collect the Zipkin spans */
  private String queue;
  /** RabbitMQ username */
  private String username;


  public String getAddresses() {
    return addresses;
  }

  public void setAddresses(String addresses) {
    this.addresses = addresses;
  }


  public Integer getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public ActiveMQCollector.Builder toBuilder()
      throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
    final ActiveMQCollector.Builder result = ActiveMQCollector.builder();
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    if (connectionTimeout != null) {
      connectionFactory.setConnectResponseTimeout(connectionTimeout);
    }
    if (queue != null) {
      result.queue(queue);
    }
    if (addresses != null) {
      result.addresses(addresses);
    }
    if (password != null) {
      connectionFactory.setPassword(password);
    }
    if (username != null) {
      connectionFactory.setUserName(username);
    }

    result.connectionFactory(connectionFactory);
    return result;
  }
}
