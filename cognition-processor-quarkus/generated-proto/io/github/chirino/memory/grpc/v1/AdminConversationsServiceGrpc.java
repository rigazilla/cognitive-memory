package io.github.chirino.memory.grpc.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: memory/v1/memory_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdminConversationsServiceGrpc {

  private AdminConversationsServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "memory.v1.AdminConversationsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetConversationRequest,
      io.github.chirino.memory.grpc.v1.AdminConversation> getGetConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConversation",
      requestType = io.github.chirino.memory.grpc.v1.AdminGetConversationRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminConversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetConversationRequest,
      io.github.chirino.memory.grpc.v1.AdminConversation> getGetConversationMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminGetConversationRequest, io.github.chirino.memory.grpc.v1.AdminConversation> getGetConversationMethod;
    if ((getGetConversationMethod = AdminConversationsServiceGrpc.getGetConversationMethod) == null) {
      synchronized (AdminConversationsServiceGrpc.class) {
        if ((getGetConversationMethod = AdminConversationsServiceGrpc.getGetConversationMethod) == null) {
          AdminConversationsServiceGrpc.getGetConversationMethod = getGetConversationMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminGetConversationRequest, io.github.chirino.memory.grpc.v1.AdminConversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminGetConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminConversation.getDefaultInstance()))
              .setSchemaDescriptor(new AdminConversationsServiceMethodDescriptorSupplier("GetConversation"))
              .build();
        }
      }
    }
    return getGetConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListConversationsRequest,
      io.github.chirino.memory.grpc.v1.AdminListConversationsResponse> getListConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConversations",
      requestType = io.github.chirino.memory.grpc.v1.AdminListConversationsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminListConversationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListConversationsRequest,
      io.github.chirino.memory.grpc.v1.AdminListConversationsResponse> getListConversationsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListConversationsRequest, io.github.chirino.memory.grpc.v1.AdminListConversationsResponse> getListConversationsMethod;
    if ((getListConversationsMethod = AdminConversationsServiceGrpc.getListConversationsMethod) == null) {
      synchronized (AdminConversationsServiceGrpc.class) {
        if ((getListConversationsMethod = AdminConversationsServiceGrpc.getListConversationsMethod) == null) {
          AdminConversationsServiceGrpc.getListConversationsMethod = getListConversationsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListConversationsRequest, io.github.chirino.memory.grpc.v1.AdminListConversationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListConversationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListConversationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminConversationsServiceMethodDescriptorSupplier("ListConversations"))
              .build();
        }
      }
    }
    return getListConversationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest,
      io.github.chirino.memory.grpc.v1.AdminConversation> getUpdateConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateConversation",
      requestType = io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminConversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest,
      io.github.chirino.memory.grpc.v1.AdminConversation> getUpdateConversationMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest, io.github.chirino.memory.grpc.v1.AdminConversation> getUpdateConversationMethod;
    if ((getUpdateConversationMethod = AdminConversationsServiceGrpc.getUpdateConversationMethod) == null) {
      synchronized (AdminConversationsServiceGrpc.class) {
        if ((getUpdateConversationMethod = AdminConversationsServiceGrpc.getUpdateConversationMethod) == null) {
          AdminConversationsServiceGrpc.getUpdateConversationMethod = getUpdateConversationMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest, io.github.chirino.memory.grpc.v1.AdminConversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminConversation.getDefaultInstance()))
              .setSchemaDescriptor(new AdminConversationsServiceMethodDescriptorSupplier("UpdateConversation"))
              .build();
        }
      }
    }
    return getUpdateConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest,
      io.github.chirino.memory.grpc.v1.ListMembershipsResponse> getListMembershipsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListMemberships",
      requestType = io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.ListMembershipsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest,
      io.github.chirino.memory.grpc.v1.ListMembershipsResponse> getListMembershipsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest, io.github.chirino.memory.grpc.v1.ListMembershipsResponse> getListMembershipsMethod;
    if ((getListMembershipsMethod = AdminConversationsServiceGrpc.getListMembershipsMethod) == null) {
      synchronized (AdminConversationsServiceGrpc.class) {
        if ((getListMembershipsMethod = AdminConversationsServiceGrpc.getListMembershipsMethod) == null) {
          AdminConversationsServiceGrpc.getListMembershipsMethod = getListMembershipsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest, io.github.chirino.memory.grpc.v1.ListMembershipsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListMemberships"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.ListMembershipsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminConversationsServiceMethodDescriptorSupplier("ListMemberships"))
              .build();
        }
      }
    }
    return getListMembershipsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListForksRequest,
      io.github.chirino.memory.grpc.v1.AdminListForksResponse> getListForksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListForks",
      requestType = io.github.chirino.memory.grpc.v1.AdminListForksRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminListForksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListForksRequest,
      io.github.chirino.memory.grpc.v1.AdminListForksResponse> getListForksMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListForksRequest, io.github.chirino.memory.grpc.v1.AdminListForksResponse> getListForksMethod;
    if ((getListForksMethod = AdminConversationsServiceGrpc.getListForksMethod) == null) {
      synchronized (AdminConversationsServiceGrpc.class) {
        if ((getListForksMethod = AdminConversationsServiceGrpc.getListForksMethod) == null) {
          AdminConversationsServiceGrpc.getListForksMethod = getListForksMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListForksRequest, io.github.chirino.memory.grpc.v1.AdminListForksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListForks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListForksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListForksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminConversationsServiceMethodDescriptorSupplier("ListForks"))
              .build();
        }
      }
    }
    return getListForksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest,
      io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse> getListChildConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListChildConversations",
      requestType = io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest.class,
      responseType = io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest,
      io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse> getListChildConversationsMethod() {
    io.grpc.MethodDescriptor<io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest, io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse> getListChildConversationsMethod;
    if ((getListChildConversationsMethod = AdminConversationsServiceGrpc.getListChildConversationsMethod) == null) {
      synchronized (AdminConversationsServiceGrpc.class) {
        if ((getListChildConversationsMethod = AdminConversationsServiceGrpc.getListChildConversationsMethod) == null) {
          AdminConversationsServiceGrpc.getListChildConversationsMethod = getListChildConversationsMethod =
              io.grpc.MethodDescriptor.<io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest, io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListChildConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AdminConversationsServiceMethodDescriptorSupplier("ListChildConversations"))
              .build();
        }
      }
    }
    return getListChildConversationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminConversationsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminConversationsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminConversationsServiceStub>() {
        @java.lang.Override
        public AdminConversationsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminConversationsServiceStub(channel, callOptions);
        }
      };
    return AdminConversationsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminConversationsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminConversationsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminConversationsServiceBlockingStub>() {
        @java.lang.Override
        public AdminConversationsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminConversationsServiceBlockingStub(channel, callOptions);
        }
      };
    return AdminConversationsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminConversationsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminConversationsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminConversationsServiceFutureStub>() {
        @java.lang.Override
        public AdminConversationsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminConversationsServiceFutureStub(channel, callOptions);
        }
      };
    return AdminConversationsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Get any conversation by ID (bypasses membership check).
     * Requires admin or auditor role.
     * </pre>
     */
    default void getConversation(io.github.chirino.memory.grpc.v1.AdminGetConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminConversation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConversationMethod(), responseObserver);
    }

    /**
     * <pre>
     * List conversations with admin filters (cross-user access).
     * Requires admin or auditor role.
     * </pre>
     */
    default void listConversations(io.github.chirino.memory.grpc.v1.AdminListConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListConversationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListConversationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update conversation archive state.
     * Requires admin role (not auditor - write operation).
     * </pre>
     */
    default void updateConversation(io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminConversation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateConversationMethod(), responseObserver);
    }

    /**
     * <pre>
     * List conversation memberships (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    default void listMemberships(io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMembershipsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMembershipsMethod(), responseObserver);
    }

    /**
     * <pre>
     * List conversation forks (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    default void listForks(io.github.chirino.memory.grpc.v1.AdminListForksRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListForksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListForksMethod(), responseObserver);
    }

    /**
     * <pre>
     * List child conversations (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    default void listChildConversations(io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListChildConversationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AdminConversationsService.
   */
  public static abstract class AdminConversationsServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AdminConversationsServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AdminConversationsService.
   */
  public static final class AdminConversationsServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AdminConversationsServiceStub> {
    private AdminConversationsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminConversationsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminConversationsServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get any conversation by ID (bypasses membership check).
     * Requires admin or auditor role.
     * </pre>
     */
    public void getConversation(io.github.chirino.memory.grpc.v1.AdminGetConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminConversation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List conversations with admin filters (cross-user access).
     * Requires admin or auditor role.
     * </pre>
     */
    public void listConversations(io.github.chirino.memory.grpc.v1.AdminListConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListConversationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListConversationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update conversation archive state.
     * Requires admin role (not auditor - write operation).
     * </pre>
     */
    public void updateConversation(io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminConversation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List conversation memberships (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public void listMemberships(io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMembershipsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMembershipsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List conversation forks (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public void listForks(io.github.chirino.memory.grpc.v1.AdminListForksRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListForksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListForksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List child conversations (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public void listChildConversations(io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest request,
        io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListChildConversationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AdminConversationsService.
   */
  public static final class AdminConversationsServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AdminConversationsServiceBlockingStub> {
    private AdminConversationsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminConversationsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminConversationsServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get any conversation by ID (bypasses membership check).
     * Requires admin or auditor role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.AdminConversation getConversation(io.github.chirino.memory.grpc.v1.AdminGetConversationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConversationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List conversations with admin filters (cross-user access).
     * Requires admin or auditor role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.AdminListConversationsResponse listConversations(io.github.chirino.memory.grpc.v1.AdminListConversationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListConversationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update conversation archive state.
     * Requires admin role (not auditor - write operation).
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.AdminConversation updateConversation(io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateConversationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List conversation memberships (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.ListMembershipsResponse listMemberships(io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMembershipsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List conversation forks (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.AdminListForksResponse listForks(io.github.chirino.memory.grpc.v1.AdminListForksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListForksMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List child conversations (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse listChildConversations(io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListChildConversationsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AdminConversationsService.
   */
  public static final class AdminConversationsServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AdminConversationsServiceFutureStub> {
    private AdminConversationsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminConversationsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminConversationsServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get any conversation by ID (bypasses membership check).
     * Requires admin or auditor role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminConversation> getConversation(
        io.github.chirino.memory.grpc.v1.AdminGetConversationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConversationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List conversations with admin filters (cross-user access).
     * Requires admin or auditor role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminListConversationsResponse> listConversations(
        io.github.chirino.memory.grpc.v1.AdminListConversationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListConversationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update conversation archive state.
     * Requires admin role (not auditor - write operation).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminConversation> updateConversation(
        io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateConversationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List conversation memberships (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.ListMembershipsResponse> listMemberships(
        io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMembershipsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List conversation forks (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminListForksResponse> listForks(
        io.github.chirino.memory.grpc.v1.AdminListForksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListForksMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List child conversations (any conversation).
     * Requires admin or auditor role.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse> listChildConversations(
        io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListChildConversationsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CONVERSATION = 0;
  private static final int METHODID_LIST_CONVERSATIONS = 1;
  private static final int METHODID_UPDATE_CONVERSATION = 2;
  private static final int METHODID_LIST_MEMBERSHIPS = 3;
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
        case METHODID_GET_CONVERSATION:
          serviceImpl.getConversation((io.github.chirino.memory.grpc.v1.AdminGetConversationRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminConversation>) responseObserver);
          break;
        case METHODID_LIST_CONVERSATIONS:
          serviceImpl.listConversations((io.github.chirino.memory.grpc.v1.AdminListConversationsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListConversationsResponse>) responseObserver);
          break;
        case METHODID_UPDATE_CONVERSATION:
          serviceImpl.updateConversation((io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminConversation>) responseObserver);
          break;
        case METHODID_LIST_MEMBERSHIPS:
          serviceImpl.listMemberships((io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.ListMembershipsResponse>) responseObserver);
          break;
        case METHODID_LIST_FORKS:
          serviceImpl.listForks((io.github.chirino.memory.grpc.v1.AdminListForksRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListForksResponse>) responseObserver);
          break;
        case METHODID_LIST_CHILD_CONVERSATIONS:
          serviceImpl.listChildConversations((io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest) request,
              (io.grpc.stub.StreamObserver<io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse>) responseObserver);
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
          getGetConversationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminGetConversationRequest,
              io.github.chirino.memory.grpc.v1.AdminConversation>(
                service, METHODID_GET_CONVERSATION)))
        .addMethod(
          getListConversationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListConversationsRequest,
              io.github.chirino.memory.grpc.v1.AdminListConversationsResponse>(
                service, METHODID_LIST_CONVERSATIONS)))
        .addMethod(
          getUpdateConversationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminUpdateConversationRequest,
              io.github.chirino.memory.grpc.v1.AdminConversation>(
                service, METHODID_UPDATE_CONVERSATION)))
        .addMethod(
          getListMembershipsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListMembershipsRequest,
              io.github.chirino.memory.grpc.v1.ListMembershipsResponse>(
                service, METHODID_LIST_MEMBERSHIPS)))
        .addMethod(
          getListForksMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListForksRequest,
              io.github.chirino.memory.grpc.v1.AdminListForksResponse>(
                service, METHODID_LIST_FORKS)))
        .addMethod(
          getListChildConversationsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              io.github.chirino.memory.grpc.v1.AdminListChildConversationsRequest,
              io.github.chirino.memory.grpc.v1.AdminListChildConversationsResponse>(
                service, METHODID_LIST_CHILD_CONVERSATIONS)))
        .build();
  }

  private static abstract class AdminConversationsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminConversationsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.chirino.memory.grpc.v1.MemoryService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminConversationsService");
    }
  }

  private static final class AdminConversationsServiceFileDescriptorSupplier
      extends AdminConversationsServiceBaseDescriptorSupplier {
    AdminConversationsServiceFileDescriptorSupplier() {}
  }

  private static final class AdminConversationsServiceMethodDescriptorSupplier
      extends AdminConversationsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AdminConversationsServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AdminConversationsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminConversationsServiceFileDescriptorSupplier())
              .addMethod(getGetConversationMethod())
              .addMethod(getListConversationsMethod())
              .addMethod(getUpdateConversationMethod())
              .addMethod(getListMembershipsMethod())
              .addMethod(getListForksMethod())
              .addMethod(getListChildConversationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
