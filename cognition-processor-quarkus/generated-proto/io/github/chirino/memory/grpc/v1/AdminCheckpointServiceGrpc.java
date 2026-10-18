package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdminCheckpointServiceGrpc {

  private AdminCheckpointServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.AdminCheckpointService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetCheckpointRequest,
      io.github.chirino.memory.grpc.v1.AdminCheckpoint> getGetCheckpointMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCheckpoint",
      requestType = io.github.chirino.memory.grpc.v1.GetCheckpointRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminCheckpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetCheckpointRequest,
      io.github.chirino.memory.grpc.v1.AdminCheckpoint> getGetCheckpointMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.GetCheckpointRequest, io.github.chirino.memory.grpc.v1.AdminCheckpoint> getGetCheckpointMethod;
    if ((getGetCheckpointMethod = AdminCheckpointServiceGrpc.getGetCheckpointMethod) == null) {
      synchronized (AdminCheckpointServiceGrpc.class) {
        if ((getGetCheckpointMethod = AdminCheckpointServiceGrpc.getGetCheckpointMethod) == null) {
          AdminCheckpointServiceGrpc.getGetCheckpointMethod = getGetCheckpointMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.GetCheckpointRequest, io.github.chirino.memory.grpc.v1.AdminCheckpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCheckpoint"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.GetCheckpointRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminCheckpoint.getDefaultInstance()))
              .setSchemaDescriptor(new AdminCheckpointServiceMethodDescriptorSupplier("GetCheckpoint"))
              .build();
        }
      }
    }
    return getGetCheckpointMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.PutCheckpointRequest,
      io.github.chirino.memory.grpc.v1.AdminCheckpoint> getPutCheckpointMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutCheckpoint",
      requestType = io.github.chirino.memory.grpc.v1.PutCheckpointRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminCheckpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.PutCheckpointRequest,
      io.github.chirino.memory.grpc.v1.AdminCheckpoint> getPutCheckpointMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.PutCheckpointRequest, io.github.chirino.memory.grpc.v1.AdminCheckpoint> getPutCheckpointMethod;
    if ((getPutCheckpointMethod = AdminCheckpointServiceGrpc.getPutCheckpointMethod) == null) {
      synchronized (AdminCheckpointServiceGrpc.class) {
        if ((getPutCheckpointMethod = AdminCheckpointServiceGrpc.getPutCheckpointMethod) == null) {
          AdminCheckpointServiceGrpc.getPutCheckpointMethod = getPutCheckpointMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.PutCheckpointRequest, io.github.chirino.memory.grpc.v1.AdminCheckpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutCheckpoint"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.PutCheckpointRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminCheckpoint.getDefaultInstance()))
              .setSchemaDescriptor(new AdminCheckpointServiceMethodDescriptorSupplier("PutCheckpoint"))
              .build();
        }
      }
    }
    return getPutCheckpointMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminCheckpointServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminCheckpointServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminCheckpointServiceStub>() {
        @java.lang.Override
        public AdminCheckpointServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminCheckpointServiceStub(channel, callOptions);
        }
      };
    return AdminCheckpointServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminCheckpointServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminCheckpointServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminCheckpointServiceBlockingStub>() {
        @java.lang.Override
        public AdminCheckpointServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminCheckpointServiceBlockingStub(channel, callOptions);
        }
      };
    return AdminCheckpointServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminCheckpointServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminCheckpointServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminCheckpointServiceFutureStub>() {
        @java.lang.Override
        public AdminCheckpointServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminCheckpointServiceFutureStub(channel, callOptions);
        }
      };
    return AdminCheckpointServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getCheckpoint(io.github.chirino.memory.grpc.v1.GetCheckpointRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminCheckpoint> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCheckpointMethod(), responseObserver);
    }

    /**
     */
    default void putCheckpoint(io.github.chirino.memory.grpc.v1.PutCheckpointRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminCheckpoint> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutCheckpointMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AdminCheckpointService.
   */
  public static abstract class AdminCheckpointServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AdminCheckpointServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AdminCheckpointService.
   */
  public static final class AdminCheckpointServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AdminCheckpointServiceStub> {
    private AdminCheckpointServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminCheckpointServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminCheckpointServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCheckpoint(io.github.chirino.memory.grpc.v1.GetCheckpointRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminCheckpoint> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCheckpointMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void putCheckpoint(io.github.chirino.memory.grpc.v1.PutCheckpointRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminCheckpoint> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutCheckpointMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AdminCheckpointService.
   */
  public static final class AdminCheckpointServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AdminCheckpointServiceBlockingStub> {
    private AdminCheckpointServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminCheckpointServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminCheckpointServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.AdminCheckpoint getCheckpoint(io.github.chirino.memory.grpc.v1.GetCheckpointRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCheckpointMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.AdminCheckpoint putCheckpoint(io.github.chirino.memory.grpc.v1.PutCheckpointRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutCheckpointMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AdminCheckpointService.
   */
  public static final class AdminCheckpointServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AdminCheckpointServiceFutureStub> {
    private AdminCheckpointServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminCheckpointServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminCheckpointServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminCheckpoint> getCheckpoint(
        io.github.chirino.memory.grpc.v1.GetCheckpointRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCheckpointMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminCheckpoint> putCheckpoint(
        io.github.chirino.memory.grpc.v1.PutCheckpointRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutCheckpointMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CHECKPOINT = 0;
  private static final int METHODID_PUT_CHECKPOINT = 1;

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
        case METHODID_GET_CHECKPOINT:
          serviceImpl.getCheckpoint((io.github.chirino.memory.grpc.v1.GetCheckpointRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminCheckpoint>) responseObserver);
          break;
        case METHODID_PUT_CHECKPOINT:
          serviceImpl.putCheckpoint((io.github.chirino.memory.grpc.v1.PutCheckpointRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminCheckpoint>) responseObserver);
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
          getGetCheckpointMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.GetCheckpointRequest,
              io.github.chirino.memory.grpc.v1.AdminCheckpoint>(
                service, METHODID_GET_CHECKPOINT)))
        .addMethod(
          getPutCheckpointMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.PutCheckpointRequest,
              io.github.chirino.memory.grpc.v1.AdminCheckpoint>(
                service, METHODID_PUT_CHECKPOINT)))
        .build();
  }

  private static abstract class AdminCheckpointServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminCheckpointServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminCheckpointService");
    }
  }

  private static final class AdminCheckpointServiceFileDescriptorSupplier
      extends AdminCheckpointServiceBaseDescriptorSupplier {
    AdminCheckpointServiceFileDescriptorSupplier() {}
  }

  private static final class AdminCheckpointServiceMethodDescriptorSupplier
      extends AdminCheckpointServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AdminCheckpointServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AdminCheckpointServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminCheckpointServiceFileDescriptorSupplier())
              .addMethod(getGetCheckpointMethod())
              .addMethod(getPutCheckpointMethod())
              .build();
        }
      }
    }
    return result;
  }
}
