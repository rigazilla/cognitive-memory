package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SystemServiceGrpc {

  private SystemServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.SystemService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.chirino.memory.grpc.v1.HealthResponse> getGetHealthMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetHealth",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.chirino.memory.grpc.v1.HealthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.chirino.memory.grpc.v1.HealthResponse> getGetHealthMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.chirino.memory.grpc.v1.HealthResponse> getGetHealthMethod;
    if ((getGetHealthMethod = SystemServiceGrpc.getGetHealthMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetHealthMethod = SystemServiceGrpc.getGetHealthMethod) == null) {
          SystemServiceGrpc.getGetHealthMethod = getGetHealthMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.chirino.memory.grpc.v1.HealthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetHealth"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.HealthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("GetHealth"))
              .build();
        }
      }
    }
    return getGetHealthMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.chirino.memory.grpc.v1.CapabilitiesResponse> getGetCapabilitiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCapabilities",
      requestType = com.google.protobuf.Empty.class,
      responseType = io.github.chirino.memory.grpc.v1.CapabilitiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      io.github.chirino.memory.grpc.v1.CapabilitiesResponse> getGetCapabilitiesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, io.github.chirino.memory.grpc.v1.CapabilitiesResponse> getGetCapabilitiesMethod;
    if ((getGetCapabilitiesMethod = SystemServiceGrpc.getGetCapabilitiesMethod) == null) {
      synchronized (SystemServiceGrpc.class) {
        if ((getGetCapabilitiesMethod = SystemServiceGrpc.getGetCapabilitiesMethod) == null) {
          SystemServiceGrpc.getGetCapabilitiesMethod = getGetCapabilitiesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, io.github.chirino.memory.grpc.v1.CapabilitiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCapabilities"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.CapabilitiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SystemServiceMethodDescriptorSupplier("GetCapabilities"))
              .build();
        }
      }
    }
    return getGetCapabilitiesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SystemServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceStub>() {
        @java.lang.Override
        public SystemServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceStub(channel, callOptions);
        }
      };
    return SystemServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SystemServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceBlockingStub>() {
        @java.lang.Override
        public SystemServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceBlockingStub(channel, callOptions);
        }
      };
    return SystemServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SystemServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemServiceFutureStub>() {
        @java.lang.Override
        public SystemServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemServiceFutureStub(channel, callOptions);
        }
      };
    return SystemServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getHealth(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.HealthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetHealthMethod(), responseObserver);
    }

    /**
     */
    default void getCapabilities(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CapabilitiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCapabilitiesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SystemService.
   */
  public static abstract class SystemServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SystemServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SystemService.
   */
  public static final class SystemServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SystemServiceStub> {
    private SystemServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceStub(channel, callOptions);
    }

    /**
     */
    public void getHealth(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.HealthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetHealthMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCapabilities(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CapabilitiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCapabilitiesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SystemService.
   */
  public static final class SystemServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SystemServiceBlockingStub> {
    private SystemServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.HealthResponse getHealth(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetHealthMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.CapabilitiesResponse getCapabilities(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCapabilitiesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SystemService.
   */
  public static final class SystemServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SystemServiceFutureStub> {
    private SystemServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.HealthResponse> getHealth(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetHealthMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.CapabilitiesResponse> getCapabilities(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCapabilitiesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_HEALTH = 0;
  private static final int METHODID_GET_CAPABILITIES = 1;

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
        case METHODID_GET_HEALTH:
          serviceImpl.getHealth((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.HealthResponse>) responseObserver);
          break;
        case METHODID_GET_CAPABILITIES:
          serviceImpl.getCapabilities((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.CapabilitiesResponse>) responseObserver);
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
          getGetHealthMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.chirino.memory.grpc.v1.HealthResponse>(
                service, METHODID_GET_HEALTH)))
        .addMethod(
          getGetCapabilitiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              io.github.chirino.memory.grpc.v1.CapabilitiesResponse>(
                service, METHODID_GET_CAPABILITIES)))
        .build();
  }

  private static abstract class SystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SystemServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SystemService");
    }
  }

  private static final class SystemServiceFileDescriptorSupplier
      extends SystemServiceBaseDescriptorSupplier {
    SystemServiceFileDescriptorSupplier() {}
  }

  private static final class SystemServiceMethodDescriptorSupplier
      extends SystemServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SystemServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SystemServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SystemServiceFileDescriptorSupplier())
              .addMethod(getGetHealthMethod())
              .addMethod(getGetCapabilitiesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
