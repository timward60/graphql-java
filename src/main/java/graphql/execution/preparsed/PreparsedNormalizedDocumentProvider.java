package graphql.execution.preparsed;


import graphql.ExecutionInput;
import graphql.PublicSpi;
import graphql.language.Document;
import graphql.schema.GraphQLSchema;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Interface that allows clients to hook in Document caching and/or the whitelisting of queries.
 */
@PublicSpi
public interface PreparsedNormalizedDocumentProvider {
    CompletableFuture<PreparsedNormalizedDocumentEntry> getNormalizedDocument(CreateNormalizedDocument creator);
}


