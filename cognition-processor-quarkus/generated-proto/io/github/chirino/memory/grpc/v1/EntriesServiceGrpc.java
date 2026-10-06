package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EntriesServiceGrpc {

  private EntriesServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.EntriesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListEntriesRequest,
      io.github.chirino.memory.grpc.v1.ListEntriesResponse> getListEntriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListEntries",
      requestType = io.github.chirino.memory.grpc.v1.ListEntriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListEntriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListEntriesRequest,
      io.github.chirino.memory.grpc.v1.ListEntriesResponse> getListEntriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.ListEntriesRequest, io.github.chirino.memory.grpc.v1.ListEntriesResponse> getListEntriesMethod;
    if ((getListEntriesMethod = EntriesServiceGrpc.getListEntriesMethod) == null) {
      synchronized (EntriesServiceGrpc.class) {
        if ((getListEntriesMethod = EntriesServiceGrpc.getListEntriesMethod) == null) {
          EntriesServiceGrpc.getListEntriesMethod = getListEntriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.ListEntriesRequest, io.github.chirino.memory.grpc.v1.ListEntriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListEntries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListEntriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EntriesServiceMethodDescriptorSupplier("ListEntries"))
              .build();
        }
      }
    }
    return getListEntriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AppendEntryRequest,
      io.github.chirino.memory.grpc.v1.Entry> getAppendEntryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AppendEntry",
      requestType = io.github.chirino.memory.grpc.v1.AppendEntryRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.Entry.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AppendEntryRequest,
      io.github.chirino.memory.grpc.v1.Entry> getAppendEntryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AppendEntryRequest, io.github.chirino.memory.grpc.v1.Entry> getAppendEntryMethod;
    if ((getAppendEntryMethod = EntriesServiceGrpc.getAppendEntryMethod) == null) {
      synchronized (EntriesServiceGrpc.class) {
        if ((getAppendEntryMethod = EntriesServiceGrpc.getAppendEntryMethod) == null) {
          EntriesServiceGrpc.getAppendEntryMethod = getAppendEntryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AppendEntryRequest, io.github.chirino.memory.grpc.v1.Entry>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AppendEntry"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AppendEntryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.Entry.getDefaultInstance()))
              .setSchemaDescriptor(new EntriesServiceMethodDescriptorSupplier("AppendEntry"))
              .build();
        }
      }
    }
    return getAppendEntryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SyncEntriesRequest,
      io.github.chirino.memory.grpc.v1.SyncEntriesResponse> getSyncEntriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SyncEntries",
      requestType = io.github.chirino.memory.grpc.v1.SyncEntriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.SyncEntriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SyncEntriesRequest,
      io.github.chirino.memory.grpc.v1.SyncEntriesResponse> getSyncEntriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.SyncEntriesRequest, io.github.chirino.memory.grpc.v1.SyncEntriesResponse> getSyncEntriesMethod;
    if ((getSyncEntriesMethod = EntriesServiceGrpc.getSyncEntriesMethod) == null) {
      synchronized (EntriesServiceGrpc.class) {
        if ((getSyncEntriesMethod = EntriesServiceGrpc.getSyncEntriesMethod) == null) {
          EntriesServiceGrpc.getSyncEntriesMethod = getSyncEntriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.SyncEntriesRequest, io.github.chirino.memory.grpc.v1.SyncEntriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SyncEntries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SyncEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.SyncEntriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EntriesServiceMethodDescriptorSupplier("SyncEntries"))
              .build();
        }
      }
    }
    return getSyncEntriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EntriesServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EntriesServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EntriesServiceStub>() {
        @java.lang.Override
        public EntriesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EntriesServiceStub(channel, callOptions);
        }
      };
    return EntriesServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EntriesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EntriesServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EntriesServiceBlockingStub>() {
        @java.lang.Override
        public EntriesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EntriesServiceBlockingStub(channel, callOptions);
        }
      };
    return EntriesServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EntriesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EntriesServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EntriesServiceFutureStub>() {
        @java.lang.Override
        public EntriesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EntriesServiceFutureStub(channel, callOptions);
        }
      };
    return EntriesServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listEntries(io.github.chirino.memory.grpc.v1.ListEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListEntriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListEntriesMethod(), responseObserver);
    }

    /**
     */
    default void appendEntry(io.github.chirino.memory.grpc.v1.AppendEntryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Entry> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAppendEntryMethod(), responseObserver);
    }

    /**
     */
    default void syncEntries(io.github.chirino.memory.grpc.v1.SyncEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SyncEntriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSyncEntriesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EntriesService.
   */
  public static abstract class EntriesServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EntriesServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EntriesService.
   */
  public static final class EntriesServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EntriesServiceStub> {
    private EntriesServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EntriesServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EntriesServiceStub(channel, callOptions);
    }

    /**
     */
    public void listEntries(io.github.chirino.memory.grpc.v1.ListEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListEntriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListEntriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void appendEntry(io.github.chirino.memory.grpc.v1.AppendEntryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Entry> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAppendEntryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void syncEntries(io.github.chirino.memory.grpc.v1.SyncEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SyncEntriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSyncEntriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EntriesService.
   */
  public static final class EntriesServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EntriesServiceBlockingStub> {
    private EntriesServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EntriesServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EntriesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListEntriesResponse listEntries(io.github.chirino.memory.grpc.v1.ListEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListEntriesMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.Entry appendEntry(io.github.chirino.memory.grpc.v1.AppendEntryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAppendEntryMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.SyncEntriesResponse syncEntries(io.github.chirino.memory.grpc.v1.SyncEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSyncEntriesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EntriesService.
   */
  public static final class EntriesServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EntriesServiceFutureStub> {
    private EntriesServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EntriesServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EntriesServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListEntriesResponse> listEntries(
        io.github.chirino.memory.grpc.v1.ListEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListEntriesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.Entry> appendEntry(
        io.github.chirino.memory.grpc.v1.AppendEntryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAppendEntryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.SyncEntriesResponse> syncEntries(
        io.github.chirino.memory.grpc.v1.SyncEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSyncEntriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_ENTRIES = 0;
  private static final int METHODID_APPEND_ENTRY = 1;
  private static final int METHODID_SYNC_ENTRIES = 2;

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
        case METHODID_LIST_ENTRIES:
          serviceImpl.listEntries((io.github.chirino.memory.grpc.v1.ListEntriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListEntriesResponse>) responseObserver);
          break;
        case METHODID_APPEND_ENTRY:
          serviceImpl.appendEntry((io.github.chirino.memory.grpc.v1.AppendEntryRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Entry>) responseObserver);
          break;
        case METHODID_SYNC_ENTRIES:
          serviceImpl.syncEntries((io.github.chirino.memory.grpc.v1.SyncEntriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.SyncEntriesResponse>) responseObserver);
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
          getListEntriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.ListEntriesRequest,
              io.github.chirino.memory.grpc.v1.ListEntriesResponse>(
                service, METHODID_LIST_ENTRIES)))
        .addMethod(
          getAppendEntryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AppendEntryRequest,
              io.github.chirino.memory.grpc.v1.Entry>(
                service, METHODID_APPEND_ENTRY)))
        .addMethod(
          getSyncEntriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.SyncEntriesRequest,
              io.github.chirino.memory.grpc.v1.SyncEntriesResponse>(
                service, METHODID_SYNC_ENTRIES)))
        .build();
  }

  private static abstract class EntriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EntriesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EntriesService");
    }
  }

  private static final class EntriesServiceFileDescriptorSupplier
      extends EntriesServiceBaseDescriptorSupplier {
    EntriesServiceFileDescriptorSupplier() {}
  }

  private static final class EntriesServiceMethodDescriptorSupplier
      extends EntriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EntriesServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (EntriesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EntriesServiceFileDescriptorSupplier())
              .addMethod(getListEntriesMethod())
              .addMethod(getAppendEntryMethod())
              .addMethod(getSyncEntriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
