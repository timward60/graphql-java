package graphql.execution.preparsed;

import graphql.normalized.nf.NormalizedDocument;

@FunctionalInterface
public interface CreateNormalizedDocument {
    NormalizedDocument createNormalizedDocument();
}
