package name.lamperi.aoc;

import org.apache.activemq.artemis.core.config.CoreQueueConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.QuerydslWebConfiguration;

@SpringBootApplication
public class AocMicrochipFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AocMicrochipFactoryApplication.class, args);
	}
	
	@Bean
	// There is a bug in Artemis
	// If we don't pre-create queues we get exceptions during run time
   public ArtemisConfigurationCustomizer artemisConfigurationCustomizer(JMSBasedDestinationStrategies destinationNames) {
       return configuration -> {
          for (int i = 0; i < 250; ++i) {
             CoreQueueConfiguration queueConf = createQueueConf(destinationNames.buildRobotQueueName(String.valueOf(i)));
             configuration.addQueueConfiguration(queueConf);
          }
          for (int i = 0; i < 25; ++i) {
             CoreQueueConfiguration queueConf = createQueueConf(destinationNames.buildRobotQueueName(String.valueOf(i)));
             configuration.addQueueConfiguration(queueConf);
          }
          {
             CoreQueueConfiguration queueConf = createQueueConf(destinationNames.getStatsQueueName());
             configuration.addQueueConfiguration(queueConf);
          }

       };
   }
	
	private CoreQueueConfiguration createQueueConf(String name) {
      String queueName = "jms.queue." + name;
      CoreQueueConfiguration queueConf = new CoreQueueConfiguration();
      queueConf.setName(queueName);
      queueConf.setAddress(queueName);
      return queueConf;
	}
}
