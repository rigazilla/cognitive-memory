package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdminEntriesServiceGrpc {

  private AdminEntriesServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.AdminEntriesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListEntriesRequest,
      io.github.chirino.memory.grpc.v1.ListEntriesResponse> getListEntriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListEntries",
      requestType = io.github.chirino.memory.grpc.v1.AdminListEntriesRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListEntriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListEntriesRequest,
      io.github.chirino.memory.grpc.v1.ListEntriesResponse> getListEntriesMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListEntriesRequest, io.github.chirino.memory.grpc.v1.ListEntriesResponse> getListEntriesMethod;
    if ((getListEntriesMethod = AdminEntriesServiceGrpc.getListEntriesMethod) == null) {
      synchronized (AdminEntriesServiceGrpc.class) {
        if ((getListEntriesMethod = AdminEntriesServiceGrpc.getListEntriesMethod) == null) {
          AdminEntriesServiceGrpc.getListEntriesMethod = getListEntriesMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListEntriesRequest, io.github.chirino.memory.grpc.v1.ListEntriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListEntries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListEntriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminEntriesServiceMethodDescriptorSupplier("ListEntries"))
              .build();
        }
      }
    }
    return getListEntriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetEntryRequest,
      io.github.chirino.memory.grpc.v1.Entry> getGetEntryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEntry",
      requestType = io.github.chirino.memory.grpc.v1.AdminGetEntryRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.Entry.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetEntryRequest,
      io.github.chirino.memory.grpc.v1.Entry> getGetEntryMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetEntryRequest, io.github.chirino.memory.grpc.v1.Entry> getGetEntryMethod;
    if ((getGetEntryMethod = AdminEntriesServiceGrpc.getGetEntryMethod) == null) {
      synchronized (AdminEntriesServiceGrpc.class) {
        if ((getGetEntryMethod = AdminEntriesServiceGrpc.getGetEntryMethod) == null) {
          AdminEntriesServiceGrpc.getGetEntryMethod = getGetEntryMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminGetEntryRequest, io.github.chirino.memory.grpc.v1.Entry>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEntry"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminGetEntryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.Entry.getDefaultInstance()))
              .setSchemaDescriptor(new AdminEntriesServiceMethodDescriptorSupplier("GetEntry"))
              .build();
        }
      }
    }
    return getGetEntryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminEntriesServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminEntriesServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminEntriesServiceStub>() {
        @java.lang.Override
        public AdminEntriesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminEntriesServiceStub(channel, callOptions);
        }
      };
    return AdminEntriesServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminEntriesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminEntriesServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminEntriesServiceBlockingStub>() {
        @java.lang.Override
        public AdminEntriesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminEntriesServiceBlockingStub(channel, callOptions);
        }
      };
    return AdminEntriesServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminEntriesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminEntriesServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminEntriesServiceFutureStub>() {
        @java.lang.Override
        public AdminEntriesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminEntriesServiceFutureStub(channel, callOptions);
        }
      };
    return AdminEntriesServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listEntries(io.github.chirino.memory.grpc.v1.AdminListEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListEntriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListEntriesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get any entry by ID (admin/auditor).
     * Retrieves a conversation entry by its ID, including entries from archived conversations.
     * Requires admin or auditor role.
     * </pre>
     */
    default void getEntry(io.github.chirino.memory.grpc.v1.AdminGetEntryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Entry> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEntryMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AdminEntriesService.
   */
  public static abstract class AdminEntriesServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AdminEntriesServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AdminEntriesService.
   */
  public static final class AdminEntriesServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AdminEntriesServiceStub> {
    private AdminEntriesServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminEntriesServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminEntriesServiceStub(channel, callOptions);
    }

    /**
     */
    public void listEntries(io.github.chirino.memory.grpc.v1.AdminListEntriesRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListEntriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListEntriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get any entry by ID (admin/auditor).
     * Retrieves a conversation entry by its ID, including entries from archived conversations.
     * Requires admin or auditor role.
     * </pre>
     */
    public void getEntry(io.github.chirino.memory.grpc.v1.AdminGetEntryRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Entry> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEntryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AdminEntriesService.
   */
  public static final class AdminEntriesServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AdminEntriesServiceBlockingStub> {
    private AdminEntriesServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminEntriesServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminEntriesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.chirino.memory.grpc.v1.ListEntriesResponse listEntries(io.github.chirino.memory.grpc.v1.AdminListEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListEntriesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get any entry by ID (admin/auditor).
     * Retrieves a conversation entry by its ID, including entries from archived conversations.
     * Requires admin or auditor role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.Entry getEntry(io.github.chirino.memory.grpc.v1.AdminGetEntryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEntryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AdminEntriesService.
   */
  public static final class AdminEntriesServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AdminEntriesServiceFutureStub> {
    private AdminEntriesServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminEntriesServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminEntriesServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListEntriesResponse> listEntries(
        io.github.chirino.memory.grpc.v1.AdminListEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListEntriesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get any entry by ID (admin/auditor).
     * Retrieves a conversation entry by its ID, including entries from archived conversations.
     * Requires admin or auditor role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.Entry> getEntry(
        io.github.chirino.memory.grpc.v1.AdminGetEntryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEntryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_ENTRIES = 0;
  private static final int METHODID_GET_ENTRY = 1;

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
          serviceImpl.listEntries((io.github.chirino.memory.grpc.v1.AdminListEntriesRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListEntriesResponse>) responseObserver);
          break;
        case METHODID_GET_ENTRY:
          serviceImpl.getEntry((io.github.chirino.memory.grpc.v1.AdminGetEntryRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.Entry>) responseObserver);
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
              io.github.chirino.memory.grpc.v1.AdminListEntriesRequest,
              io.github.chirino.memory.grpc.v1.ListEntriesResponse>(
                service, METHODID_LIST_ENTRIES)))
        .addMethod(
          getGetEntryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminGetEntryRequest,
              io.github.chirino.memory.grpc.v1.Entry>(
                service, METHODID_GET_ENTRY)))
        .build();
  }

  private static abstract class AdminEntriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminEntriesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminEntriesService");
    }
  }

  private static final class AdminEntriesServiceFileDescriptorSupplier
      extends AdminEntriesServiceBaseDescriptorSupplier {
    AdminEntriesServiceFileDescriptorSupplier() {}
  }

  private static final class AdminEntriesServiceMethodDescriptorSupplier
      extends AdminEntriesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AdminEntriesServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AdminEntriesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminEntriesServiceFileDescriptorSupplier())
              .addMethod(getListEntriesMethod())
              .addMethod(getGetEntryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
