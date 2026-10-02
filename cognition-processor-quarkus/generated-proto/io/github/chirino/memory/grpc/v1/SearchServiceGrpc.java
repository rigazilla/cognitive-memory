package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SearchServiceGrpc {

  private SearchServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.SearchService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SearchEntriesRequest,
      io.github.chirino.memory.grpc.v1.SearchEntriesResponse> getSearchConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SearchConversations",
      requestType = io.github.chirino.memory.grpc.v1.SearchEntriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.SearchEntriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SearchEntriesRequest,
      io.github.chirino.memory.grpc.v1.SearchEntriesResponse> getSearchConversationsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SearchEntriesRequest, io.github.chirino.memory.grpc.v1.SearchEntriesResponse> getSearchConversationsMethod;
    if ((getSearchConversationsMethod = SearchServiceGrpc.getSearchConversationsMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchConversationsMethod = SearchServiceGrpc.getSearchConversationsMethod) == null) {
          SearchServiceGrpc.getSearchConversationsMethod = getSearchConversationsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.SearchEntriesRequest, io.github.chirino.memory.grpc.v1.SearchEntriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SearchConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SearchEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SearchEntriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("SearchConversations"))
              .build();
        }
      }
    }
    return getSearchConversationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.IndexConversationsRequest,
      io.github.chirino.memory.grpc.v1.IndexConversationsResponse> getIndexConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "IndexConversations",
      requestType = io.github.chirino.memory.grpc.v1.IndexConversationsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.IndexConversationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.IndexConversationsRequest,
      io.github.chirino.memory.grpc.v1.IndexConversationsResponse> getIndexConversationsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.IndexConversationsRequest, io.github.chirino.memory.grpc.v1.IndexConversationsResponse> getIndexConversationsMethod;
    if ((getIndexConversationsMethod = SearchServiceGrpc.getIndexConversationsMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getIndexConversationsMethod = SearchServiceGrpc.getIndexConversationsMethod) == null) {
          SearchServiceGrpc.getIndexConversationsMethod = getIndexConversationsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.IndexConversationsRequest, io.github.chirino.memory.grpc.v1.IndexConversationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "IndexConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.IndexConversationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.IndexConversationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("IndexConversations"))
              .build();
        }
      }
    }
    return getIndexConversationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest,
      io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse> getListUnindexedEntriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListUnindexedEntries",
      requestType = io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest,
      io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse> getListUnindexedEntriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest, io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse> getListUnindexedEntriesMethod;
    if ((getListUnindexedEntriesMethod = SearchServiceGrpc.getListUnindexedEntriesMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getListUnindexedEntriesMethod = SearchServiceGrpc.getListUnindexedEntriesMethod) == null) {
          SearchServiceGrpc.getListUnindexedEntriesMethod = getListUnindexedEntriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest, io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListUnindexedEntries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("ListUnindexedEntries"))
              .build();
        }
      }
    }
    return getListUnindexedEntriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SearchServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SearchServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SearchServiceStub>() {
        @java.lang.Override
        public SearchServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SearchServiceStub(channel, callOptions);
        }
      };
    return SearchServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SearchServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SearchServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SearchServiceBlockingStub>() {
        @java.lang.Override
        public SearchServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SearchServiceBlockingStub(channel, callOptions);
        }
      };
    return SearchServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SearchServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SearchServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SearchServiceFutureStub>() {
        @java.lang.Override
        public SearchServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SearchServiceFutureStub(channel, callOptions);
        }
      };
    return SearchServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void searchConversations(io.github.chirino.memory.grpc.v1.SearchEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SearchEntriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchConversationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Index conversation entries for search. Requires indexer or admin role.
     * </pre>
     */
    default void indexConversations(io.github.chirino.memory.grpc.v1.IndexConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.IndexConversationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIndexConversationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * List entries needing indexing. Requires indexer or admin role.
     * </pre>
     */
    default void listUnindexedEntries(io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListUnindexedEntriesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SearchService.
   */
  public static abstract class SearchServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SearchServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SearchService.
   */
  public static final class SearchServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SearchServiceStub> {
    private SearchServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SearchServiceStub(channel, callOptions);
    }

    /**
     */
    public void searchConversations(io.github.chirino.memory.grpc.v1.SearchEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SearchEntriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchConversationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Index conversation entries for search. Requires indexer or admin role.
     * </pre>
     */
    public void indexConversations(io.github.chirino.memory.grpc.v1.IndexConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.IndexConversationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIndexConversationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List entries needing indexing. Requires indexer or admin role.
     * </pre>
     */
    public void listUnindexedEntries(io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListUnindexedEntriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SearchService.
   */
  public static final class SearchServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SearchServiceBlockingStub> {
    private SearchServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SearchServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.SearchEntriesResponse searchConversations(io.github.chirino.memory.grpc.v1.SearchEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchConversationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Index conversation entries for search. Requires indexer or admin role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.IndexConversationsResponse indexConversations(io.github.chirino.memory.grpc.v1.IndexConversationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIndexConversationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List entries needing indexing. Requires indexer or admin role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse listUnindexedEntries(io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListUnindexedEntriesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SearchService.
   */
  public static final class SearchServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SearchServiceFutureStub> {
    private SearchServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SearchServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.SearchEntriesResponse> searchConversations(
        io.github.chirino.memory.grpc.v1.SearchEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchConversationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Index conversation entries for search. Requires indexer or admin role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.IndexConversationsResponse> indexConversations(
        io.github.chirino.memory.grpc.v1.IndexConversationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIndexConversationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List entries needing indexing. Requires indexer or admin role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse> listUnindexedEntries(
        io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListUnindexedEntriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEARCH_CONVERSATIONS = 0;
  private static final int METHODID_INDEX_CONVERSATIONS = 1;
  private static final int METHODID_LIST_UNINDEXED_ENTRIES = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEARCH_CONVERSATIONS:
          serviceImpl.searchConversations((io.github.chirino.memory.grpc.v1.SearchEntriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SearchEntriesResponse>) responseObserver);
          break;
        case METHODID_INDEX_CONVERSATIONS:
          serviceImpl.indexConversations((io.github.chirino.memory.grpc.v1.IndexConversationsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.IndexConversationsResponse>) responseObserver);
          break;
        case METHODID_LIST_UNINDEXED_ENTRIES:
          serviceImpl.listUnindexedEntries((io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSearchConversationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.SearchEntriesRequest,
              io.github.chirino.memory.grpc.v1.SearchEntriesResponse>(
                service, METHODID_SEARCH_CONVERSATIONS)))
        .addMethod(
          getIndexConversationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.IndexConversationsRequest,
              io.github.chirino.memory.grpc.v1.IndexConversationsResponse>(
                service, METHODID_INDEX_CONVERSATIONS)))
        .addMethod(
          getListUnindexedEntriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListUnindexedEntriesRequest,
              io.github.chirino.memory.grpc.v1.ListUnindexedEntriesResponse>(
                service, METHODID_LIST_UNINDEXED_ENTRIES)))
        .build();
  }

  private static abstract class SearchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SearchServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SearchService");
    }
  }

  private static final class SearchServiceFileDescriptorSupplier
      extends SearchServiceBaseDescriptorSupplier {
    SearchServiceFileDescriptorSupplier() {}
  }

  private static final class SearchServiceMethodDescriptorSupplier
      extends SearchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SearchServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SearchServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SearchServiceFileDescriptorSupplier())
              .addMethod(getSearchConversationsMethod())
              .addMethod(getIndexConversationsMethod())
              .addMethod(getListUnindexedEntriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
