package io.github.rigazilla.memory.cognition.grpc;

import io.github.chirino.memory.grpc.v1.AdminConversationsServiceGrpc;
import io.github.chirino.memory.grpc.v1.AdminGetConversationRequest;
import io.github.rigazilla.memory.cognition.config.MemoryServiceConfig;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/**
 * Resolves the owner user ID for a conversation via AdminConversationsService.
 * Shared by any process that needs to determine the user namespace for memory writes.
 */
@ApplicationScoped
public class ConversationOwnerResolver {

    private static final Logger LOG = Logger.getLogger(ConversationOwnerResolver.class);

    @Inject
    MemoryServiceConfig memoryService;

    ManagedChannel channel;
    AdminConversationsServiceGrpc.AdminConversationsServiceBlockingStub conversationsStub;

    @PostConstruct
    void init() {
        LOG.infof("Initializing ConversationOwnerResolver: %s:%d",
                memoryService.grpc().host(), memoryService.grpc().port());
        channel = GrpcChannelFactory.create(
                memoryService.grpc().host(), memoryService.grpc().port(),
                memoryService.apiKey(), memoryService.clientId());
        conversationsStub = AdminConversationsServiceGrpc.newBlockingStub(channel);
        LOG.info("ConversationOwnerResolver initialized successfully");
    }

    @PreDestroy
    void cleanup() {
        if (channel != null && !channel.isShutdown()) {
            LOG.info("Shutting down ConversationOwnerResolver gRPC channel");
            channel.shutdown();
        }
    }

    /**
     * Returns the owner user ID for the given conversation.
     *
     * @param conversationId conversation UUID string
     * @return owner user ID
     * @throws ConversationOwnerException if the conversation metadata cannot be loaded
     */
    public String resolve(String conversationId) {
        try {
            AdminGetConversationRequest request = AdminGetConversationRequest.newBuilder()
                    .setConversationId(conversationId)
                    .build();
            String ownerId = conversationsStub.getConversation(request).getOwnerUserId();
            LOG.debugf("Resolved conversation %s owner: %s", conversationId, ownerId);
            return ownerId;
        } catch (StatusRuntimeException e) {
            Status status = e.getStatus();
            LOG.errorf(e, "Failed to resolve conversation owner for %s: %s", conversationId, status);
            throw new ConversationOwnerException(
                    "Failed to resolve conversation owner for " + conversationId, e);
        } catch (Exception e) {
            LOG.errorf(e, "Unexpected error resolving conversation owner for %s", conversationId);
            throw new ConversationOwnerException(
                    "Failed to resolve conversation owner for " + conversationId, e);
        }
    }

    /**
     * Thrown when conversation owner metadata cannot be loaded.
     */
    public static class ConversationOwnerException extends RuntimeException {
        public ConversationOwnerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
