package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConversationsServiceGrpc {

  private ConversationsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.ConversationsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListConversationsRequest,
      io.github.chirino.memory.grpc.v1.ListConversationsResponse> getListConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConversations",
      requestType = io.github.chirino.memory.grpc.v1.ListConversationsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListConversationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListConversationsRequest,
      io.github.chirino.memory.grpc.v1.ListConversationsResponse> getListConversationsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListConversationsRequest, io.github.chirino.memory.grpc.v1.ListConversationsResponse> getListConversationsMethod;
    if ((getListConversationsMethod = ConversationsServiceGrpc.getListConversationsMethod) == null) {
      synchronized (ConversationsServiceGrpc.class) {
        if ((getListConversationsMethod = ConversationsServiceGrpc.getListConversationsMethod) == null) {
          ConversationsServiceGrpc.getListConversationsMethod = getListConversationsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListConversationsRequest, io.github.chirino.memory.grpc.v1.ListConversationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListConversationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListConversationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationsServiceMethodDescriptorSupplier("ListConversations"))
              .build();
        }
      }
    }
    return getListConversationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CreateConversationRequest,
      io.github.chirino.memory.grpc.v1.Conversation> getCreateConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateConversation",
      requestType = io.github.chirino.memory.grpc.v1.CreateConversationRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.Conversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CreateConversationRequest,
      io.github.chirino.memory.grpc.v1.Conversation> getCreateConversationMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CreateConversationRequest, io.github.chirino.memory.grpc.v1.Conversation> getCreateConversationMethod;
    if ((getCreateConversationMethod = ConversationsServiceGrpc.getCreateConversationMethod) == null) {
      synchronized (ConversationsServiceGrpc.class) {
        if ((getCreateConversationMethod = ConversationsServiceGrpc.getCreateConversationMethod) == null) {
          ConversationsServiceGrpc.getCreateConversationMethod = getCreateConversationMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.CreateConversationRequest, io.github.chirino.memory.grpc.v1.Conversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CreateConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.Conversation.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationsServiceMethodDescriptorSupplier("CreateConversation"))
              .build();
        }
      }
    }
    return getCreateConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetConversationRequest,
      io.github.chirino.memory.grpc.v1.Conversation> getGetConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConversation",
      requestType = io.github.chirino.memory.grpc.v1.GetConversationRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.Conversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetConversationRequest,
      io.github.chirino.memory.grpc.v1.Conversation> getGetConversationMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetConversationRequest, io.github.chirino.memory.grpc.v1.Conversation> getGetConversationMethod;
    if ((getGetConversationMethod = ConversationsServiceGrpc.getGetConversationMethod) == null) {
      synchronized (ConversationsServiceGrpc.class) {
        if ((getGetConversationMethod = ConversationsServiceGrpc.getGetConversationMethod) == null) {
          ConversationsServiceGrpc.getGetConversationMethod = getGetConversationMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.GetConversationRequest, io.github.chirino.memory.grpc.v1.Conversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.GetConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.Conversation.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationsServiceMethodDescriptorSupplier("GetConversation"))
              .build();
        }
      }
    }
    return getGetConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateConversationRequest,
      io.github.chirino.memory.grpc.v1.Conversation> getUpdateConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateConversation",
      requestType = io.github.chirino.memory.grpc.v1.UpdateConversationRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.Conversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateConversationRequest,
      io.github.chirino.memory.grpc.v1.Conversation> getUpdateConversationMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateConversationRequest, io.github.chirino.memory.grpc.v1.Conversation> getUpdateConversationMethod;
    if ((getUpdateConversationMethod = ConversationsServiceGrpc.getUpdateConversationMethod) == null) {
      synchronized (ConversationsServiceGrpc.class) {
        if ((getUpdateConversationMethod = ConversationsServiceGrpc.getUpdateConversationMethod) == null) {
          ConversationsServiceGrpc.getUpdateConversationMethod = getUpdateConversationMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.UpdateConversationRequest, io.github.chirino.memory.grpc.v1.Conversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.UpdateConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.Conversation.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationsServiceMethodDescriptorSupplier("UpdateConversation"))
              .build();
        }
      }
    }
    return getUpdateConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListForksRequest,
      io.github.chirino.memory.grpc.v1.ListForksResponse> getListForksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListForks",
      requestType = io.github.chirino.memory.grpc.v1.ListForksRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListForksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListForksRequest,
      io.github.chirino.memory.grpc.v1.ListForksResponse> getListForksMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListForksRequest, io.github.chirino.memory.grpc.v1.ListForksResponse> getListForksMethod;
    if ((getListForksMethod = ConversationsServiceGrpc.getListForksMethod) == null) {
      synchronized (ConversationsServiceGrpc.class) {
        if ((getListForksMethod = ConversationsServiceGrpc.getListForksMethod) == null) {
          ConversationsServiceGrpc.getListForksMethod = getListForksMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListForksRequest, io.github.chirino.memory.grpc.v1.ListForksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListForks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListForksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListForksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationsServiceMethodDescriptorSupplier("ListForks"))
              .build();
        }
      }
    }
    return getListForksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListChildConversationsRequest,
      io.github.chirino.memory.grpc.v1.ListChildConversationsResponse> getListChildConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListChildConversations",
      requestType = io.github.chirino.memory.grpc.v1.ListChildConversationsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListChildConversationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListChildConversationsRequest,
      io.github.chirino.memory.grpc.v1.ListChildConversationsResponse> getListChildConversationsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListChildConversationsRequest, io.github.chirino.memory.grpc.v1.ListChildConversationsResponse> getListChildConversationsMethod;
    if ((getListChildConversationsMethod = ConversationsServiceGrpc.getListChildConversationsMethod) == null) {
      synchronized (ConversationsServiceGrpc.class) {
        if ((getListChildConversationsMethod = ConversationsServiceGrpc.getListChildConversationsMethod) == null) {
          ConversationsServiceGrpc.getListChildConversationsMethod = getListChildConversationsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListChildConversationsRequest, io.github.chirino.memory.grpc.v1.ListChildConversationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListChildConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListChildConversationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListChildConversationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConversationsServiceMethodDescriptorSupplier("ListChildConversations"))
              .build();
        }
      }
    }
    return getListChildConversationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConversationsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConversationsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConversationsServiceStub>() {
        @java.lang.Override
        public ConversationsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConversationsServiceStub(channel, callOptions);
        }
      };
    return ConversationsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConversationsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConversationsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConversationsServiceBlockingStub>() {
        @java.lang.Override
        public ConversationsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConversationsServiceBlockingStub(channel, callOptions);
        }
      };
    return ConversationsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConversationsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConversationsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConversationsServiceFutureStub>() {
        @java.lang.Override
        public ConversationsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConversationsServiceFutureStub(channel, callOptions);
        }
      };
    return ConversationsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * ListConversations returns conversations the user has access to.
     * The mode parameter controls which conversations from each fork tree are returned.
     * See ConversationListMode for available modes.
     * </pre>
     */
    default void listConversations(io.github.chirino.memory.grpc.v1.ListConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListConversationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListConversationsMethod(), responseObserver);
    }

    /**
     */
    default void createConversation(io.github.chirino.memory.grpc.v1.CreateConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateConversationMethod(), responseObserver);
    }

    /**
     */
    default void getConversation(io.github.chirino.memory.grpc.v1.GetConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConversationMethod(), responseObserver);
    }

    /**
     */
    default void updateConversation(io.github.chirino.memory.grpc.v1.UpdateConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateConversationMethod(), responseObserver);
    }

    /**
     */
    default void listForks(io.github.chirino.memory.grpc.v1.ListForksRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListForksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListForksMethod(), responseObserver);
    }

    /**
     */
    default void listChildConversations(io.github.chirino.memory.grpc.v1.ListChildConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListChildConversationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListChildConversationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ConversationsService.
   */
  public static abstract class ConversationsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ConversationsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ConversationsService.
   */
  public static final class ConversationsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ConversationsServiceStub> {
    private ConversationsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConversationsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConversationsServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * ListConversations returns conversations the user has access to.
     * The mode parameter controls which conversations from each fork tree are returned.
     * See ConversationListMode for available modes.
     * </pre>
     */
    public void listConversations(io.github.chirino.memory.grpc.v1.ListConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListConversationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListConversationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createConversation(io.github.chirino.memory.grpc.v1.CreateConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConversation(io.github.chirino.memory.grpc.v1.GetConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateConversation(io.github.chirino.memory.grpc.v1.UpdateConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listForks(io.github.chirino.memory.grpc.v1.ListForksRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListForksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListForksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listChildConversations(io.github.chirino.memory.grpc.v1.ListChildConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListChildConversationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListChildConversationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ConversationsService.
   */
  public static final class ConversationsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ConversationsServiceBlockingStub> {
    private ConversationsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConversationsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConversationsServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * ListConversations returns conversations the user has access to.
     * The mode parameter controls which conversations from each fork tree are returned.
     * See ConversationListMode for available modes.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.ListConversationsResponse listConversations(io.github.chirino.memory.grpc.v1.ListConversationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListConversationsMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.Conversation createConversation(io.github.chirino.memory.grpc.v1.CreateConversationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateConversationMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.Conversation getConversation(io.github.chirino.memory.grpc.v1.GetConversationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConversationMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.Conversation updateConversation(io.github.chirino.memory.grpc.v1.UpdateConversationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateConversationMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListForksResponse listForks(io.github.chirino.memory.grpc.v1.ListForksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListForksMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListChildConversationsResponse listChildConversations(io.github.chirino.memory.grpc.v1.ListChildConversationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListChildConversationsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ConversationsService.
   */
  public static final class ConversationsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ConversationsServiceFutureStub> {
    private ConversationsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConversationsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConversationsServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * ListConversations returns conversations the user has access to.
     * The mode parameter controls which conversations from each fork tree are returned.
     * See ConversationListMode for available modes.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListConversationsResponse> listConversations(
        io.github.chirino.memory.grpc.v1.ListConversationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListConversationsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.Conversation> createConversation(
        io.github.chirino.memory.grpc.v1.CreateConversationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateConversationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.Conversation> getConversation(
        io.github.chirino.memory.grpc.v1.GetConversationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConversationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.Conversation> updateConversation(
        io.github.chirino.memory.grpc.v1.UpdateConversationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateConversationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListForksResponse> listForks(
        io.github.chirino.memory.grpc.v1.ListForksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListForksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListChildConversationsResponse> listChildConversations(
        io.github.chirino.memory.grpc.v1.ListChildConversationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListChildConversationsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_CONVERSATIONS = 0;
  private static final int METHODID_CREATE_CONVERSATION = 1;
  private static final int METHODID_GET_CONVERSATION = 2;
  private static final int METHODID_UPDATE_CONVERSATION = 3;
  private static final int METHODID_LIST_FORKS = 4;
  private static final int METHODID_LIST_CHILD_CONVERSATIONS = 5;

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
        case METHODID_LIST_CONVERSATIONS:
          serviceImpl.listConversations((io.github.chirino.memory.grpc.v1.ListConversationsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListConversationsResponse>) responseObserver);
          break;
        case METHODID_CREATE_CONVERSATION:
          serviceImpl.createConversation((io.github.chirino.memory.grpc.v1.CreateConversationRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation>) responseObserver);
          break;
        case METHODID_GET_CONVERSATION:
          serviceImpl.getConversation((io.github.chirino.memory.grpc.v1.GetConversationRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation>) responseObserver);
          break;
        case METHODID_UPDATE_CONVERSATION:
          serviceImpl.updateConversation((io.github.chirino.memory.grpc.v1.UpdateConversationRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Conversation>) responseObserver);
          break;
        case METHODID_LIST_FORKS:
          serviceImpl.listForks((io.github.chirino.memory.grpc.v1.ListForksRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListForksResponse>) responseObserver);
          break;
        case METHODID_LIST_CHILD_CONVERSATIONS:
          serviceImpl.listChildConversations((io.github.chirino.memory.grpc.v1.ListChildConversationsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListChildConversationsResponse>) responseObserver);
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
          getListConversationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListConversationsRequest,
              io.github.chirino.memory.grpc.v1.ListConversationsResponse>(
                service, METHODID_LIST_CONVERSATIONS)))
        .addMethod(
          getCreateConversationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.CreateConversationRequest,
              io.github.chirino.memory.grpc.v1.Conversation>(
                service, METHODID_CREATE_CONVERSATION)))
        .addMethod(
          getGetConversationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.GetConversationRequest,
              io.github.chirino.memory.grpc.v1.Conversation>(
                service, METHODID_GET_CONVERSATION)))
        .addMethod(
          getUpdateConversationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.UpdateConversationRequest,
              io.github.chirino.memory.grpc.v1.Conversation>(
                service, METHODID_UPDATE_CONVERSATION)))
        .addMethod(
          getListForksMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListForksRequest,
              io.github.chirino.memory.grpc.v1.ListForksResponse>(
                service, METHODID_LIST_FORKS)))
        .addMethod(
          getListChildConversationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListChildConversationsRequest,
              io.github.chirino.memory.grpc.v1.ListChildConversationsResponse>(
                service, METHODID_LIST_CHILD_CONVERSATIONS)))
        .build();
  }

  private static abstract class ConversationsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConversationsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConversationsService");
    }
  }

  private static final class ConversationsServiceFileDescriptorSupplier
      extends ConversationsServiceBaseDescriptorSupplier {
    ConversationsServiceFileDescriptorSupplier() {}
  }

  private static final class ConversationsServiceMethodDescriptorSupplier
      extends ConversationsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ConversationsServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ConversationsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConversationsServiceFileDescriptorSupplier())
              .addMethod(getListConversationsMethod())
              .addMethod(getCreateConversationMethod())
              .addMethod(getGetConversationMethod())
              .addMethod(getUpdateConversationMethod())
              .addMethod(getListForksMethod())
              .addMethod(getListChildConversationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
