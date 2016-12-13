package name.lamperi.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Named;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.lamperi.aoc.parser.InputBaseListener;
import name.lamperi.aoc.parser.InputLexer;
import name.lamperi.aoc.parser.InputParser;
import name.lamperi.aoc.parser.InputParser.BotOutputContext;
import name.lamperi.aoc.parser.InputParser.BotRuleContext;
import name.lamperi.aoc.parser.InputParser.DataContext;
import name.lamperi.aoc.parser.InputParser.OutputContext;
import name.lamperi.aoc.parser.InputParser.OutputOutputContext;
import name.lamperi.aoc.parser.InputParser.ValueRuleContext;

@Named
public class InputParserBean {

   public Problem parseProblem(String input) {
      
      ANTLRInputStream inputStream = new ANTLRInputStream(input);
      InputLexer inputLexer = new InputLexer(inputStream);
      CommonTokenStream tokenStream = new CommonTokenStream(inputLexer);
      InputParser parser = new InputParser(tokenStream);
      DataContext dataContext = parser.data();
      ParseTreeWalker walker = new ParseTreeWalker();
      AntlrDataListener listener = new AntlrDataListener();
      walker.walk(listener, dataContext);
      
      return listener.buildProblem();
   }
   
   public static class AntlrDataListener extends InputBaseListener {

      private final Logger logger = LoggerFactory.getLogger(InputParserBean.class);

      private final List<RobotDefinition> robots = new ArrayList<>();
      private final List<ValueDefinition> values = new ArrayList<>();
      
      public Problem buildProblem() {
         logger.info("Problem has {} robots and {} input producers", robots.size(), values.size());
         return new Problem(new HashSet<>(values), new HashSet<>(robots)); 
      }
      
      @Override
      public void exitBotRule(BotRuleContext ctx) {
         TerminalNode botID = ctx.ID();
         OutputContext lowOutput = ctx.output(0);
         OutputContext highOutput = ctx.output(1);
         robots.add(new RobotDefinition(botID.getText(), buildOutput(lowOutput), buildOutput(highOutput)));
      }
      
      private OutputDefinition buildOutput(OutputContext ctx) {
         OutputOutputContext outputCtx = ctx.outputOutput();
         BotOutputContext botCtx = ctx.botOutput();
         if (outputCtx != null) {
            return new OutputOutputDefinition(outputCtx.ID().getText());
         } else if (botCtx != null) {
            return new BotOutputDefinition(botCtx.ID().getText());
         } else {
            throw new IllegalStateException();
         }
      }
      
      @Override
      public void exitValueRule(ValueRuleContext ctx) {
         TerminalNode inputValue = ctx.ID();
         OutputContext output = ctx.output();
         values.add(new ValueDefinition(inputValue.getText(), buildOutput(output)));
      }
   }

}

