package graphql.execution.preparsed;


import graphql.ExecutionInput;
import graphql.Internal;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Internal
public class NoOpPreparsedNormalizedDocumentProvider implements PreparsedNormalizedDocumentProvider {
    public static final NoOpPreparsedNormalizedDocumentProvider INSTANCE = new NoOpPreparsedNormalizedDocumentProvider();

    @Override
    public CompletableFuture<PreparsedNormalizedDocumentEntry> getNormalizedDocument(CreateNormalizedDocument creator) {
        return CompletableFuture.completedFuture(new PreparsedNormalizedDocumentEntry(creator.createNormalizedDocument()));
    }
}
