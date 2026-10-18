package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MemoriesServiceGrpc {

  private MemoriesServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.MemoriesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.PutMemoryRequest,
      io.github.chirino.memory.grpc.v1.MemoryWriteResult> getPutMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutMemory",
      requestType = io.github.chirino.memory.grpc.v1.PutMemoryRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.MemoryWriteResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.PutMemoryRequest,
      io.github.chirino.memory.grpc.v1.MemoryWriteResult> getPutMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.PutMemoryRequest, io.github.chirino.memory.grpc.v1.MemoryWriteResult> getPutMemoryMethod;
    if ((getPutMemoryMethod = MemoriesServiceGrpc.getPutMemoryMethod) == null) {
      synchronized (MemoriesServiceGrpc.class) {
        if ((getPutMemoryMethod = MemoriesServiceGrpc.getPutMemoryMethod) == null) {
          MemoriesServiceGrpc.getPutMemoryMethod = getPutMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.PutMemoryRequest, io.github.chirino.memory.grpc.v1.MemoryWriteResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.PutMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.MemoryWriteResult.getDefaultInstance()))
              .setSchemaDescriptor(new MemoriesServiceMethodDescriptorSupplier("PutMemory"))
              .build();
        }
      }
    }
    return getPutMemoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetMemoryRequest,
      io.github.chirino.memory.grpc.v1.MemoryItem> getGetMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMemory",
      requestType = io.github.chirino.memory.grpc.v1.GetMemoryRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.MemoryItem.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetMemoryRequest,
      io.github.chirino.memory.grpc.v1.MemoryItem> getGetMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetMemoryRequest, io.github.chirino.memory.grpc.v1.MemoryItem> getGetMemoryMethod;
    if ((getGetMemoryMethod = MemoriesServiceGrpc.getGetMemoryMethod) == null) {
      synchronized (MemoriesServiceGrpc.class) {
        if ((getGetMemoryMethod = MemoriesServiceGrpc.getGetMemoryMethod) == null) {
          MemoriesServiceGrpc.getGetMemoryMethod = getGetMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.GetMemoryRequest, io.github.chirino.memory.grpc.v1.MemoryItem>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.GetMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.MemoryItem.getDefaultInstance()))
              .setSchemaDescriptor(new MemoriesServiceMethodDescriptorSupplier("GetMemory"))
              .build();
        }
      }
    }
    return getGetMemoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateMemoryRequest,
      com.google.protobuf.Empty> getUpdateMemoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateMemory",
      requestType = io.github.chirino.memory.grpc.v1.UpdateMemoryRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateMemoryRequest,
      com.google.protobuf.Empty> getUpdateMemoryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.UpdateMemoryRequest, com.google.protobuf.Empty> getUpdateMemoryMethod;
    if ((getUpdateMemoryMethod = MemoriesServiceGrpc.getUpdateMemoryMethod) == null) {
      synchronized (MemoriesServiceGrpc.class) {
        if ((getUpdateMemoryMethod = MemoriesServiceGrpc.getUpdateMemoryMethod) == null) {
          MemoriesServiceGrpc.getUpdateMemoryMethod = getUpdateMemoryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.UpdateMemoryRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateMemory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.UpdateMemoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new MemoriesServiceMethodDescriptorSupplier("UpdateMemory"))
              .build();
        }
      }
    }
    return getUpdateMemoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SearchMemoriesRequest,
      io.github.chirino.memory.grpc.v1.SearchMemoriesResponse> getSearchMemoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SearchMemories",
      requestType = io.github.chirino.memory.grpc.v1.SearchMemoriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.SearchMemoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SearchMemoriesRequest,
      io.github.chirino.memory.grpc.v1.SearchMemoriesResponse> getSearchMemoriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SearchMemoriesRequest, io.github.chirino.memory.grpc.v1.SearchMemoriesResponse> getSearchMemoriesMethod;
    if ((getSearchMemoriesMethod = MemoriesServiceGrpc.getSearchMemoriesMethod) == null) {
      synchronized (MemoriesServiceGrpc.class) {
        if ((getSearchMemoriesMethod = MemoriesServiceGrpc.getSearchMemoriesMethod) == null) {
          MemoriesServiceGrpc.getSearchMemoriesMethod = getSearchMemoriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.SearchMemoriesRequest, io.github.chirino.memory.grpc.v1.SearchMemoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SearchMemories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SearchMemoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SearchMemoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MemoriesServiceMethodDescriptorSupplier("SearchMemories"))
              .build();
        }
      }
    }
    return getSearchMemoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest,
      io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse> getListMemoryNamespacesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListMemoryNamespaces",
      requestType = io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest,
      io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse> getListMemoryNamespacesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest, io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse> getListMemoryNamespacesMethod;
    if ((getListMemoryNamespacesMethod = MemoriesServiceGrpc.getListMemoryNamespacesMethod) == null) {
      synchronized (MemoriesServiceGrpc.class) {
        if ((getListMemoryNamespacesMethod = MemoriesServiceGrpc.getListMemoryNamespacesMethod) == null) {
          MemoriesServiceGrpc.getListMemoryNamespacesMethod = getListMemoryNamespacesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest, io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListMemoryNamespaces"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MemoriesServiceMethodDescriptorSupplier("ListMemoryNamespaces"))
              .build();
        }
      }
    }
    return getListMemoryNamespacesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MemoriesServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MemoriesServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MemoriesServiceStub>() {
        @java.lang.Override
        public MemoriesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MemoriesServiceStub(channel, callOptions);
        }
      };
    return MemoriesServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MemoriesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MemoriesServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MemoriesServiceBlockingStub>() {
        @java.lang.Override
        public MemoriesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MemoriesServiceBlockingStub(channel, callOptions);
        }
      };
    return MemoriesServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MemoriesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MemoriesServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MemoriesServiceFutureStub>() {
        @java.lang.Override
        public MemoriesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MemoriesServiceFutureStub(channel, callOptions);
        }
      };
    return MemoriesServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void putMemory(io.github.chirino.memory.grpc.v1.PutMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryWriteResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutMemoryMethod(), responseObserver);
    }

    /**
     */
    default void getMemory(io.github.chirino.memory.grpc.v1.GetMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryItem> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMemoryMethod(), responseObserver);
    }

    /**
     */
    default void updateMemory(io.github.chirino.memory.grpc.v1.UpdateMemoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMemoryMethod(), responseObserver);
    }

    /**
     */
    default void searchMemories(io.github.chirino.memory.grpc.v1.SearchMemoriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SearchMemoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchMemoriesMethod(), responseObserver);
    }

    /**
     */
    default void listMemoryNamespaces(io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMemoryNamespacesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MemoriesService.
   */
  public static abstract class MemoriesServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MemoriesServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MemoriesService.
   */
  public static final class MemoriesServiceStub
      extends io.grpc.stub.AbstractAsyncStub<MemoriesServiceStub> {
    private MemoriesServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemoriesServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MemoriesServiceStub(channel, callOptions);
    }

    /**
     */
    public void putMemory(io.github.chirino.memory.grpc.v1.PutMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryWriteResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutMemoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMemory(io.github.chirino.memory.grpc.v1.GetMemoryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryItem> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMemoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateMemory(io.github.chirino.memory.grpc.v1.UpdateMemoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMemoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchMemories(io.github.chirino.memory.grpc.v1.SearchMemoriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SearchMemoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchMemoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listMemoryNamespaces(io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMemoryNamespacesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MemoriesService.
   */
  public static final class MemoriesServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MemoriesServiceBlockingStub> {
    private MemoriesServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemoriesServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MemoriesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.MemoryWriteResult putMemory(io.github.chirino.memory.grpc.v1.PutMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutMemoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.MemoryItem getMemory(io.github.chirino.memory.grpc.v1.GetMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMemoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateMemory(io.github.chirino.memory.grpc.v1.UpdateMemoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMemoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.SearchMemoriesResponse searchMemories(io.github.chirino.memory.grpc.v1.SearchMemoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchMemoriesMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse listMemoryNamespaces(io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMemoryNamespacesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MemoriesService.
   */
  public static final class MemoriesServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<MemoriesServiceFutureStub> {
    private MemoriesServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemoriesServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MemoriesServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.MemoryWriteResult> putMemory(
        io.github.chirino.memory.grpc.v1.PutMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutMemoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.MemoryItem> getMemory(
        io.github.chirino.memory.grpc.v1.GetMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMemoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateMemory(
        io.github.chirino.memory.grpc.v1.UpdateMemoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMemoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.SearchMemoriesResponse> searchMemories(
        io.github.chirino.memory.grpc.v1.SearchMemoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchMemoriesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse> listMemoryNamespaces(
        io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMemoryNamespacesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUT_MEMORY = 0;
  private static final int METHODID_GET_MEMORY = 1;
  private static final int METHODID_UPDATE_MEMORY = 2;
  private static final int METHODID_SEARCH_MEMORIES = 3;
  private static final int METHODID_LIST_MEMORY_NAMESPACES = 4;

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
        case METHODID_PUT_MEMORY:
          serviceImpl.putMemory((io.github.chirino.memory.grpc.v1.PutMemoryRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryWriteResult>) responseObserver);
          break;
        case METHODID_GET_MEMORY:
          serviceImpl.getMemory((io.github.chirino.memory.grpc.v1.GetMemoryRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.MemoryItem>) responseObserver);
          break;
        case METHODID_UPDATE_MEMORY:
          serviceImpl.updateMemory((io.github.chirino.memory.grpc.v1.UpdateMemoryRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_SEARCH_MEMORIES:
          serviceImpl.searchMemories((io.github.chirino.memory.grpc.v1.SearchMemoriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SearchMemoriesResponse>) responseObserver);
          break;
        case METHODID_LIST_MEMORY_NAMESPACES:
          serviceImpl.listMemoryNamespaces((io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse>) responseObserver);
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
          getPutMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.PutMemoryRequest,
              io.github.chirino.memory.grpc.v1.MemoryWriteResult>(
                service, METHODID_PUT_MEMORY)))
        .addMethod(
          getGetMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.GetMemoryRequest,
              io.github.chirino.memory.grpc.v1.MemoryItem>(
                service, METHODID_GET_MEMORY)))
        .addMethod(
          getUpdateMemoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.UpdateMemoryRequest,
              com.google.protobuf.Empty>(
                service, METHODID_UPDATE_MEMORY)))
        .addMethod(
          getSearchMemoriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.SearchMemoriesRequest,
              io.github.chirino.memory.grpc.v1.SearchMemoriesResponse>(
                service, METHODID_SEARCH_MEMORIES)))
        .addMethod(
          getListMemoryNamespacesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListMemoryNamespacesRequest,
              io.github.chirino.memory.grpc.v1.ListMemoryNamespacesResponse>(
                service, METHODID_LIST_MEMORY_NAMESPACES)))
        .build();
  }

  private static abstract class MemoriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MemoriesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MemoriesService");
    }
  }

  private static final class MemoriesServiceFileDescriptorSupplier
      extends MemoriesServiceBaseDescriptorSupplier {
    MemoriesServiceFileDescriptorSupplier() {}
  }

  private static final class MemoriesServiceMethodDescriptorSupplier
      extends MemoriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MemoriesServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (MemoriesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MemoriesServiceFileDescriptorSupplier())
              .addMethod(getPutMemoryMethod())
              .addMethod(getGetMemoryMethod())
              .addMethod(getUpdateMemoryMethod())
              .addMethod(getSearchMemoriesMethod())
              .addMethod(getListMemoryNamespacesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
