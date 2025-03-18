package performance;

import graphql.execution.CoercedVariables;
import graphql.language.Document;
import graphql.normalized.ExecutableNormalizedOperation;
import graphql.normalized.ExecutableNormalizedOperationFactory;
import graphql.parser.Parser;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 3)
@Fork(3)
public class ENFExtraLargeVariablesPerformance {

    @State(Scope.Benchmark)
    public static class MyState {

        GraphQLSchema schema;
        Document document;

        CoercedVariables variables;

        @Setup
        public void setup() {
            try {
                String schemaString = PerformanceTestingUtils.loadResource("extra-large-schema-1.graphqls");
                schema = SchemaGenerator.createdMockedSchema(schemaString);

                String query = PerformanceTestingUtils.loadResource("extra-large-schema-1-variables-query.graphql");
                document = Parser.parse(query);
                variables = CoercedVariables.of(Map.of(
                        "issueKey", "GJ-1",
                        "cloudId", "abc123",
                        "fieldCount", 1000,
                        "childIssueCount", 1000,
                        "childIssueFieldIds", List.of("assignee ",
                                "created ",
                                "issuetype ",
                                "priority ",
                                "status ",
                                "summary ",
                                "timetracking ",
                                "updated "),
                        "issueLinksFieldIds", List.of("assignee ",
                                "issuetype ",
                                "priority ",
                                "status ",
                                "summary ")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchMarkAvgTime(MyState myState, Blackhole blackhole )  {
        runImpl(myState, blackhole);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void benchMarkThroughput(MyState myState, Blackhole blackhole )  {
        runImpl(myState, blackhole);
    }

    private void runImpl(MyState myState, Blackhole blackhole) {
        ExecutableNormalizedOperation executableNormalizedOperation = ExecutableNormalizedOperationFactory.createExecutableNormalizedOperation(myState.schema, myState.document, null, myState.variables);
        blackhole.consume(executableNormalizedOperation);
    }
}
