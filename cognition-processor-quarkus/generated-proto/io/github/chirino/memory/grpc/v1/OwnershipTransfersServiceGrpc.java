package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OwnershipTransfersServiceGrpc {

  private OwnershipTransfersServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.OwnershipTransfersService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest,
      io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse> getListOwnershipTransfersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListOwnershipTransfers",
      requestType = io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest,
      io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse> getListOwnershipTransfersMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest, io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse> getListOwnershipTransfersMethod;
    if ((getListOwnershipTransfersMethod = OwnershipTransfersServiceGrpc.getListOwnershipTransfersMethod) == null) {
      synchronized (OwnershipTransfersServiceGrpc.class) {
        if ((getListOwnershipTransfersMethod = OwnershipTransfersServiceGrpc.getListOwnershipTransfersMethod) == null) {
          OwnershipTransfersServiceGrpc.getListOwnershipTransfersMethod = getListOwnershipTransfersMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest, io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListOwnershipTransfers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipTransfersServiceMethodDescriptorSupplier("ListOwnershipTransfers"))
              .build();
        }
      }
    }
    return getListOwnershipTransfersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest,
      io.github.chirino.memory.grpc.v1.OwnershipTransfer> getGetOwnershipTransferMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOwnershipTransfer",
      requestType = io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.OwnershipTransfer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest,
      io.github.chirino.memory.grpc.v1.OwnershipTransfer> getGetOwnershipTransferMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest, io.github.chirino.memory.grpc.v1.OwnershipTransfer> getGetOwnershipTransferMethod;
    if ((getGetOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getGetOwnershipTransferMethod) == null) {
      synchronized (OwnershipTransfersServiceGrpc.class) {
        if ((getGetOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getGetOwnershipTransferMethod) == null) {
          OwnershipTransfersServiceGrpc.getGetOwnershipTransferMethod = getGetOwnershipTransferMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest, io.github.chirino.memory.grpc.v1.OwnershipTransfer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOwnershipTransfer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.OwnershipTransfer.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipTransfersServiceMethodDescriptorSupplier("GetOwnershipTransfer"))
              .build();
        }
      }
    }
    return getGetOwnershipTransferMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest,
      io.github.chirino.memory.grpc.v1.OwnershipTransfer> getCreateOwnershipTransferMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateOwnershipTransfer",
      requestType = io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.OwnershipTransfer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest,
      io.github.chirino.memory.grpc.v1.OwnershipTransfer> getCreateOwnershipTransferMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest, io.github.chirino.memory.grpc.v1.OwnershipTransfer> getCreateOwnershipTransferMethod;
    if ((getCreateOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getCreateOwnershipTransferMethod) == null) {
      synchronized (OwnershipTransfersServiceGrpc.class) {
        if ((getCreateOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getCreateOwnershipTransferMethod) == null) {
          OwnershipTransfersServiceGrpc.getCreateOwnershipTransferMethod = getCreateOwnershipTransferMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest, io.github.chirino.memory.grpc.v1.OwnershipTransfer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateOwnershipTransfer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.OwnershipTransfer.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipTransfersServiceMethodDescriptorSupplier("CreateOwnershipTransfer"))
              .build();
        }
      }
    }
    return getCreateOwnershipTransferMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest,
      com.google.protobuf.Empty> getAcceptOwnershipTransferMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AcceptOwnershipTransfer",
      requestType = io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest,
      com.google.protobuf.Empty> getAcceptOwnershipTransferMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest, com.google.protobuf.Empty> getAcceptOwnershipTransferMethod;
    if ((getAcceptOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getAcceptOwnershipTransferMethod) == null) {
      synchronized (OwnershipTransfersServiceGrpc.class) {
        if ((getAcceptOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getAcceptOwnershipTransferMethod) == null) {
          OwnershipTransfersServiceGrpc.getAcceptOwnershipTransferMethod = getAcceptOwnershipTransferMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AcceptOwnershipTransfer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipTransfersServiceMethodDescriptorSupplier("AcceptOwnershipTransfer"))
              .build();
        }
      }
    }
    return getAcceptOwnershipTransferMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest,
      com.google.protobuf.Empty> getDeleteOwnershipTransferMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteOwnershipTransfer",
      requestType = io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest,
      com.google.protobuf.Empty> getDeleteOwnershipTransferMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest, com.google.protobuf.Empty> getDeleteOwnershipTransferMethod;
    if ((getDeleteOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getDeleteOwnershipTransferMethod) == null) {
      synchronized (OwnershipTransfersServiceGrpc.class) {
        if ((getDeleteOwnershipTransferMethod = OwnershipTransfersServiceGrpc.getDeleteOwnershipTransferMethod) == null) {
          OwnershipTransfersServiceGrpc.getDeleteOwnershipTransferMethod = getDeleteOwnershipTransferMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteOwnershipTransfer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new OwnershipTransfersServiceMethodDescriptorSupplier("DeleteOwnershipTransfer"))
              .build();
        }
      }
    }
    return getDeleteOwnershipTransferMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OwnershipTransfersServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OwnershipTransfersServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OwnershipTransfersServiceStub>() {
        @java.lang.Override
        public OwnershipTransfersServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OwnershipTransfersServiceStub(channel, callOptions);
        }
      };
    return OwnershipTransfersServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OwnershipTransfersServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OwnershipTransfersServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OwnershipTransfersServiceBlockingStub>() {
        @java.lang.Override
        public OwnershipTransfersServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OwnershipTransfersServiceBlockingStub(channel, callOptions);
        }
      };
    return OwnershipTransfersServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OwnershipTransfersServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OwnershipTransfersServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OwnershipTransfersServiceFutureStub>() {
        @java.lang.Override
        public OwnershipTransfersServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OwnershipTransfersServiceFutureStub(channel, callOptions);
        }
      };
    return OwnershipTransfersServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * List pending ownership transfers for the current user
     * </pre>
     */
    default void listOwnershipTransfers(io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListOwnershipTransfersMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get a specific ownership transfer
     * </pre>
     */
    default void getOwnershipTransfer(io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.OwnershipTransfer> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOwnershipTransferMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create a new ownership transfer (initiates transfer to another user)
     * </pre>
     */
    default void createOwnershipTransfer(io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.OwnershipTransfer> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateOwnershipTransferMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept an ownership transfer (recipient becomes owner, sender becomes manager)
     * </pre>
     */
    default void acceptOwnershipTransfer(io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAcceptOwnershipTransferMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete/cancel an ownership transfer
     * </pre>
     */
    default void deleteOwnershipTransfer(io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteOwnershipTransferMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OwnershipTransfersService.
   */
  public static abstract class OwnershipTransfersServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return OwnershipTransfersServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OwnershipTransfersService.
   */
  public static final class OwnershipTransfersServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OwnershipTransfersServiceStub> {
    private OwnershipTransfersServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OwnershipTransfersServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OwnershipTransfersServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * List pending ownership transfers for the current user
     * </pre>
     */
    public void listOwnershipTransfers(io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListOwnershipTransfersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get a specific ownership transfer
     * </pre>
     */
    public void getOwnershipTransfer(io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.OwnershipTransfer> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOwnershipTransferMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create a new ownership transfer (initiates transfer to another user)
     * </pre>
     */
    public void createOwnershipTransfer(io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.OwnershipTransfer> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateOwnershipTransferMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Accept an ownership transfer (recipient becomes owner, sender becomes manager)
     * </pre>
     */
    public void acceptOwnershipTransfer(io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAcceptOwnershipTransferMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete/cancel an ownership transfer
     * </pre>
     */
    public void deleteOwnershipTransfer(io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteOwnershipTransferMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OwnershipTransfersService.
   */
  public static final class OwnershipTransfersServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OwnershipTransfersServiceBlockingStub> {
    private OwnershipTransfersServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OwnershipTransfersServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OwnershipTransfersServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * List pending ownership transfers for the current user
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse listOwnershipTransfers(io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListOwnershipTransfersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get a specific ownership transfer
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.OwnershipTransfer getOwnershipTransfer(io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOwnershipTransferMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create a new ownership transfer (initiates transfer to another user)
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.OwnershipTransfer createOwnershipTransfer(io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateOwnershipTransferMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Accept an ownership transfer (recipient becomes owner, sender becomes manager)
     * </pre>
     */
    public com.google.protobuf.Empty acceptOwnershipTransfer(io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAcceptOwnershipTransferMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete/cancel an ownership transfer
     * </pre>
     */
    public com.google.protobuf.Empty deleteOwnershipTransfer(io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteOwnershipTransferMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OwnershipTransfersService.
   */
  public static final class OwnershipTransfersServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OwnershipTransfersServiceFutureStub> {
    private OwnershipTransfersServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OwnershipTransfersServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OwnershipTransfersServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * List pending ownership transfers for the current user
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse> listOwnershipTransfers(
        io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListOwnershipTransfersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get a specific ownership transfer
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.OwnershipTransfer> getOwnershipTransfer(
        io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOwnershipTransferMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create a new ownership transfer (initiates transfer to another user)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.OwnershipTransfer> createOwnershipTransfer(
        io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateOwnershipTransferMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Accept an ownership transfer (recipient becomes owner, sender becomes manager)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> acceptOwnershipTransfer(
        io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAcceptOwnershipTransferMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete/cancel an ownership transfer
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteOwnershipTransfer(
        io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteOwnershipTransferMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_OWNERSHIP_TRANSFERS = 0;
  private static final int METHODID_GET_OWNERSHIP_TRANSFER = 1;
  private static final int METHODID_CREATE_OWNERSHIP_TRANSFER = 2;
  private static final int METHODID_ACCEPT_OWNERSHIP_TRANSFER = 3;
  private static final int METHODID_DELETE_OWNERSHIP_TRANSFER = 4;

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
        case METHODID_LIST_OWNERSHIP_TRANSFERS:
          serviceImpl.listOwnershipTransfers((io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse>) responseObserver);
          break;
        case METHODID_GET_OWNERSHIP_TRANSFER:
          serviceImpl.getOwnershipTransfer((io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.OwnershipTransfer>) responseObserver);
          break;
        case METHODID_CREATE_OWNERSHIP_TRANSFER:
          serviceImpl.createOwnershipTransfer((io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.OwnershipTransfer>) responseObserver);
          break;
        case METHODID_ACCEPT_OWNERSHIP_TRANSFER:
          serviceImpl.acceptOwnershipTransfer((io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_DELETE_OWNERSHIP_TRANSFER:
          serviceImpl.deleteOwnershipTransfer((io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
          getListOwnershipTransfersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListOwnershipTransfersRequest,
              io.github.chirino.memory.grpc.v1.ListOwnershipTransfersResponse>(
                service, METHODID_LIST_OWNERSHIP_TRANSFERS)))
        .addMethod(
          getGetOwnershipTransferMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.GetOwnershipTransferRequest,
              io.github.chirino.memory.grpc.v1.OwnershipTransfer>(
                service, METHODID_GET_OWNERSHIP_TRANSFER)))
        .addMethod(
          getCreateOwnershipTransferMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.CreateOwnershipTransferRequest,
              io.github.chirino.memory.grpc.v1.OwnershipTransfer>(
                service, METHODID_CREATE_OWNERSHIP_TRANSFER)))
        .addMethod(
          getAcceptOwnershipTransferMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AcceptOwnershipTransferRequest,
              com.google.protobuf.Empty>(
                service, METHODID_ACCEPT_OWNERSHIP_TRANSFER)))
        .addMethod(
          getDeleteOwnershipTransferMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.DeleteOwnershipTransferRequest,
              com.google.protobuf.Empty>(
                service, METHODID_DELETE_OWNERSHIP_TRANSFER)))
        .build();
  }

  private static abstract class OwnershipTransfersServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OwnershipTransfersServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OwnershipTransfersService");
    }
  }

  private static final class OwnershipTransfersServiceFileDescriptorSupplier
      extends OwnershipTransfersServiceBaseDescriptorSupplier {
    OwnershipTransfersServiceFileDescriptorSupplier() {}
  }

  private static final class OwnershipTransfersServiceMethodDescriptorSupplier
      extends OwnershipTransfersServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    OwnershipTransfersServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (OwnershipTransfersServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OwnershipTransfersServiceFileDescriptorSupplier())
              .addMethod(getListOwnershipTransfersMethod())
              .addMethod(getGetOwnershipTransferMethod())
              .addMethod(getCreateOwnershipTransferMethod())
              .addMethod(getAcceptOwnershipTransferMethod())
              .addMethod(getDeleteOwnershipTransferMethod())
              .build();
        }
      }
    }
    return result;
  }
}
