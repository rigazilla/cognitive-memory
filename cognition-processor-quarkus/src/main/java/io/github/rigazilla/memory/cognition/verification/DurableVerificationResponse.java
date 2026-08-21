package io.github.rigazilla.memory.cognition.verification;

import io.github.rigazilla.memory.cognition.extraction.MemoryCandidate;

import java.util.List;

/**
 * Response from the durable memory verifier.
 * Contains verified candidates (with valid citations) and rejected candidates.
 */
public record DurableVerificationResponse(
    List<MemoryCandidate> verified,
    List<RejectedCandidate> rejected
) {
    
    public DurableVerificationResponse {
        verified = verified != null ? verified : List.of();
        rejected = rejected != null ? rejected : List.of();
    }
    
    /**
     * A candidate that was rejected during verification.
     */
    public record RejectedCandidate(
        MemoryCandidate candidate,
        String reason
    ) {
       @Override
       public String toString() {
          String content = candidate.content().length() > 30 ?
                candidate.content().substring(0, 27) + "..." :
                candidate.content();
          return "RejectedCandidate{" +
                 "type=" + candidate.type() +
                 ", reason='" + reason + '\'' +
                 ", content='" + content + '\'' +
                 '}';
       }
    }

   @Override
   public String toString() {
      return "DurableVerificationResponse{" +
             "verified=" + verified.size()+
             ", rejected=" + rejected.size() +
             '}';
   }
}
