package name.lamperi.aoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@Named
@Singleton
public class AnswerConsumer implements Runnable {

   private Logger logger = LoggerFactory.getLogger(AnswerConsumer.class);

   @PostConstruct
   public void postConstruct() {
      ForkJoinPool.commonPool().execute(this);
   }
   
   public void run() {
      // Give system time to start
      try {
         Thread.sleep(2000);
      } catch (InterruptedException e) {
         // I don't think this matter
      }
      
      String request;
      try {
         request = new String(Files.readAllBytes(Paths.get("input.txt")), StandardCharsets.UTF_8);
      } catch (IOException ex) {
         logger.error("Could not read input.txt - cannot answer.");
         return;
      }
      logger.info("Performing request using: {}", request);
      
      RestTemplate restTemplate = new RestTemplate();
      Integer answer = restTemplate.postForObject("http://localhost:8080/aoc/2016/10/part1", request, Integer.class);
      
      logger.info("Bot {} is responsible for comparing value-67 and value-17 microchips.", answer);
      
      answer = restTemplate.postForObject("http://localhost:8080/aoc/2016/10/part2", request, Integer.class);
      
      logger.info("The product of output 1,2,3 is {}.", answer);
      
      System.exit(0);

   }
}
